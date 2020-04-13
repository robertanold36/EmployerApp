package com.robert.customer_manager.ui.allEmployee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.model.UserModel

class GetAllEmployerViewModel(application: Application):AndroidViewModel(application) {

    private val firebaseSource: FirebaseSource by lazy {
        FirebaseSource()
    }

    fun getAllEmployer(department:String):LiveData<MutableList<UserModel>> {
        val mutableData=MutableLiveData<MutableList<UserModel>>()
        firebaseSource.getAllEmployee(department).observeForever{
            mutableData.value=it
        }

        return mutableData
    }
}