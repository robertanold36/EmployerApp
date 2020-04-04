package com.robert.customer_manager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robert.customer_manager.database.FirebaseSource

@Suppress("UNCHECKED_CAST")
class GetAllEmployerViewFactory(private val firebaseSource: FirebaseSource):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GetAllEmployerViewModel(firebaseSource) as T
    }
}