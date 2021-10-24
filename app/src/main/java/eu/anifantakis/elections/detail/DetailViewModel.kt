package eu.anifantakis.elections.detail

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import eu.anifantakis.elections.ElectionResult
import eu.anifantakis.elections.PartyNote
import eu.anifantakis.elections.repository.ElectionsRepository
import kotlinx.coroutines.launch

class DetailViewModel(var electionsRepository: ElectionsRepository, var electionResult: ElectionResult): ViewModel() {

    private lateinit var partyNote: String
    val partyNoteText = MutableLiveData<String>()

    init{
        viewModelScope.launch {
            partyNote = electionsRepository.getPartyNote(electionResult.idparty)
            partyNoteText.value = partyNote
        }
    }

    fun commitChanges(v: View){
        viewModelScope.launch {
            if (partyNoteText.value?.trim()==""){
                electionsRepository.removePartyNoteWithId(electionResult.idparty)
            }
            else {
                electionsRepository.submitPartyNote(PartyNote(electionResult.idparty, partyNoteText.value?:""))
            }
            navigateUp(v)
        }
    }

    fun navigateUp(v: View){
        v.findNavController().navigateUp()
    }
}