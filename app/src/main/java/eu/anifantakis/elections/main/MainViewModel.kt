package eu.anifantakis.elections.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import eu.anifantakis.elections.ElectionResult
import eu.anifantakis.elections.network.NETWORK_STATUS
import eu.anifantakis.elections.repository.ElectionsRepository

class MainViewModel(private val electionsRepository: ElectionsRepository): ViewModel() {

    // expose the election results of the repository
    val electionsList = electionsRepository.electionResults

    // log the status of the repository
    val networkStatus = electionsRepository.status
    val notifyAboutStatus = electionsRepository.notifyAboutStatus

    fun onNetworkStatusNotified(){
        electionsRepository.notifyAboutStatus.value = false
    }


    // navigate to the detail screen when clicking a party
    private val _navigateToPartyDetail = MutableLiveData<ElectionResult>()
    val navigateToPartyDetail
        get() = _navigateToPartyDetail

    fun onPartyItemClicked(elecResult: ElectionResult) {
        _navigateToPartyDetail.value = elecResult
    }

    fun onPartyDetailNavigated() {
        _navigateToPartyDetail.value = null
    }
}