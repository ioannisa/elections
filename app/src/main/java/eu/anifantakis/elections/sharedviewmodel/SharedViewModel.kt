package eu.anifantakis.elections.sharedviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.elections.database.getDatabase
import eu.anifantakis.elections.repository.ElectionsRepository
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)

    // non-private repository, allows it to be exposed to more ViewModels
    val electionsRepository = ElectionsRepository(database)

    init{
        refreshData()
    }

    fun refreshData(){
        viewModelScope.launch {
            electionsRepository.apply {
                refreshElectionResults()
            }
        }
    }
}