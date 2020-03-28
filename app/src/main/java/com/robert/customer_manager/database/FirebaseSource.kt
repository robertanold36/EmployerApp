package com.robert.customer_manager.database

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(email:String,password:String)= Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(!emitter.isDisposed){
                if(it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun signUp(email:String,password:String)= Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (!emitter.isDisposed)
                if(it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
        }
    }

    fun signOut()=firebaseAuth.signOut()

    fun currentUser()=firebaseAuth.currentUser
}