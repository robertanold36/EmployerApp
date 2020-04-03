package com.robert.customer_manager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robert.customer_manager.database.FirebaseSource

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val firebaseSource: FirebaseSource):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(firebaseSource) as T
    }


}