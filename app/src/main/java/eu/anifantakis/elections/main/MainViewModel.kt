package eu.anifantakis.elections.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.elections.ElectionResult
import eu.anifantakis.elections.database.getDatabase
import eu.anifantakis.elections.repository.ElectionsRepository
import kotlinx.coroutines.launch

class MainViewModel(val electionsRepository: ElectionsRepository): ViewModel() {

    // expose the election results of the repository
    val electionsList = electionsRepository.electionResults

    // log the status of the repository
    val networkStatus =  electionsRepository.status

    // navigate to the detail screen when clicking a party
    private val _navigateToPartyDetail = MutableLiveData<ElectionResult>()
    val navigateToPartyDetail
        get() = _navigateToPartyDetail

    fun onPartyItemClicked(asteroid: ElectionResult) {
        _navigateToPartyDetail.value = asteroid
    }

    fun onPartyDetailNavigated() {
        _navigateToPartyDetail.value = null
    }
}