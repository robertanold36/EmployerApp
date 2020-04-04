package com.robert.customer_manager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.model.UserModel

class GetAllEmployerViewModel(private val firebaseSource: FirebaseSource):ViewModel() {
    fun getAllEmployer(department:String):LiveData<MutableList<UserModel>> {
        val mutableData=MutableLiveData<MutableList<UserModel>>()
        firebaseSource.getAllEmployee(department).observeForever{
            mutableData.value=it
        }

        return mutableData
    }
}