package com.robert.customer_manager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robert.customer_manager.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val userRepository: UserRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository) as T
    }
}