package com.robert.customer_manager.repository

import com.google.firebase.auth.FirebaseAuth
import com.robert.customer_manager.database.FirebaseSource
import io.reactivex.Completable

class UserRepository(private val firebase:FirebaseSource) {
    fun login(email:String,password:String)=firebase.login(email,password)
    fun signUp(email: String,password: String)=firebase.signUp(email,password)
    fun logout()=firebase.signOut()
    fun currentUser()=firebase.currentUser()
}