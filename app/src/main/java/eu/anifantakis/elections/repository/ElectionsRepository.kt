package eu.anifantakis.elections.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import eu.anifantakis.elections.*
import eu.anifantakis.elections.database.ElectionsDatabase
import eu.anifantakis.elections.network.NETWORK_STATUS
import eu.anifantakis.elections.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElectionsRepository(private val database: ElectionsDatabase) {

    // always serve the live data of Election Results retrieved by the database as Domain Model
    val electionResults: LiveData<List<ElectionResult>> =
        Transformations.map(database.electionsDao.getElecResults()) { it?.asDomainModel() }

    var status = MutableLiveData<NETWORK_STATUS>(NETWORK_STATUS.INITIALIZING)
    var notifyAboutStatus = MutableLiveData<Boolean>(false)

    suspend fun getPartyNote(party_id: Long): String{
        var result = ""
        withContext(Dispatchers.IO) {
            result = database.electionsDao.getPartyNote(party_id) ?: ""
        }
        return result
    }

    suspend fun submitPartyNote(partyNote: PartyNote){
        withContext(Dispatchers.IO) {
            database.electionsDao.addNote(partyNote)
        }
    }

    suspend fun removePartyNoteWithId(party_id: Long){
        withContext(Dispatchers.IO){
            database.electionsDao.removeNote(party_id)
        }
    }

    suspend fun refreshElectionResults(){
        withContext(Dispatchers.Main){
            try {
                notifyAboutStatus.value = false;
                status.value = NETWORK_STATUS.INITIALIZING

                val electionResultsList = Network.retrofitService.getElecResults(
                    get         = Constants.WORKING_LAYER,
                    where       = Constants.WORKING_LAYER,
                    has_value   = "1",
                    format      = Constants.FORMAT,
                    elecid      = Constants.ELEC_ID,
                    apiKey      = Constants.API_KEY) as ArrayList<NetworkElecResult>

                val networkResults = NetworkElecResultContainer(electionResultsList)

                // push the fetched results to the database
                withContext(Dispatchers.IO) {
                    database.electionsDao.insertAllResults(*networkResults.asDatabaseModel())
                }

                status.value = NETWORK_STATUS.CONNECTED
            }
            catch(e: Exception){
                // Network Error (no internet)
                notifyAboutStatus.value = true;
                status.value = NETWORK_STATUS.DISCONNECTED
            }
        }
    }
}