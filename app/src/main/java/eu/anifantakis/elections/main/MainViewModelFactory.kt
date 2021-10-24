package eu.anifantakis.elections.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.anifantakis.elections.repository.ElectionsRepository

class MainViewModelFactory(
    private val electionsRepository: ElectionsRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(electionsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}