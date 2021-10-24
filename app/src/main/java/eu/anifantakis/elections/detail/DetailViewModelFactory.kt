package eu.anifantakis.elections.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.anifantakis.elections.ElectionResult
import eu.anifantakis.elections.repository.ElectionsRepository

class DetailViewModelFactory(
    private val electionsRepository: ElectionsRepository,
    private val elecResult: ElectionResult
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(electionsRepository, elecResult) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}