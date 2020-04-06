package com.robert.customer_manager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robert.customer_manager.database.FirebaseSource
import com.robert.customer_manager.model.ChatModel
import com.robert.customer_manager.model.UserModel

class ChatViewModel(var firebaseSource: FirebaseSource):ViewModel() {

    fun loadMsg(userModel: UserModel)=firebaseSource.loadMsg(userModel)


}