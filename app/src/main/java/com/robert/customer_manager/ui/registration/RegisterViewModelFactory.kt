package com.robert.customer_manager.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robert.customer_manager.database.FirebaseSource

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(private val firebaseSource: FirebaseSource):ViewModelProvider.NewInstanceFactory() {
       override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         return RegisterViewModel(
             firebaseSource
         ) as T
    }
}