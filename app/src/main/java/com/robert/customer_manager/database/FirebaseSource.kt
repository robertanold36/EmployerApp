package com.robert.customer_manager.database

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.robert.customer_manager.model.UserModel
import io.reactivex.Completable
import java.util.*
import kotlin.collections.HashMap

class FirebaseSource{

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val storage=FirebaseStorage.getInstance()
    private val uid=FirebaseAuth.getInstance().uid?:""
    private val refDatabase=FirebaseDatabase.getInstance().getReference("/users/$uid")
    private val ref=storage.getReference("image/$uid")
    private val firebaseStore=FirebaseFirestore.getInstance()

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

     fun signUp(email:String,full_name: String,job: String,department: String,imageUri: Uri,endYear: String)
             = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email,full_name).addOnCompleteListener {
            if (!emitter.isDisposed)

                if(it.isSuccessful){

                    ref.putFile(imageUri).addOnCompleteListener {

                        ref.downloadUrl.addOnSuccessListener {url->
                            val hashData=HashMap<String,Any>()
                            hashData["uid"]=uid
                            hashData["fullName"]=full_name
                            hashData["email"]=email
                            hashData["job"]=job
                            hashData["department"]=department
                            hashData["imageUrl"]=url.toString()
                            hashData["endYear"]=endYear

                            firebaseStore.collection("employer").document(uid).set(hashData).addOnSuccessListener {
                                emitter.onComplete()
                            }
                        }
                    }
                }

                else {
                    emitter.onError(it.exception!!)
                }
        }
    }



    fun signOut()=firebaseAuth.signOut()

    fun currentUser()=firebaseAuth.currentUser
}
