package com.robert.customer_manager.database

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.robert.customer_manager.model.ChatModel
import com.robert.customer_manager.model.UserModel
import com.robert.customer_manager.ui.ChatActivity
import io.reactivex.Completable
import java.util.*
import kotlin.collections.HashMap

class FirebaseSource{

    private val eDepartment:String="Department"
    private val eImageUrl:String="ImageUrl"
    private val eJob:String="Job"
    private val eFullName:String="FullName"
    private val eEndYear:String="EndYear"
    private val eMail:String="Email"
    private val userID:String="User_id"
    private val collectionPath="employer"
    private val collectionPathChat:String="chats"
    private val message:String="message"
    private val receiver:String="Receiver ID"


    private val msgID:String="chat ID"
    private val sender:String="Sender ID"
    private val cTime:String="Time"




    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private var employees:MutableLiveData<MutableList<UserModel>> = MutableLiveData()
    private var chatToText:MutableLiveData<MutableList<ChatModel>> = MutableLiveData()



    private val storage=FirebaseStorage.getInstance()
    private val randomUid=UUID.randomUUID().toString()
    private val ref=storage.getReference("image/$randomUid")
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

                    ref.putFile(imageUri).addOnSuccessListener {

                        val uid=FirebaseAuth.getInstance().uid?:""
                        ref.downloadUrl.addOnSuccessListener {url->
                            val hashData=HashMap<String,Any>()
                            hashData[userID]=uid
                            hashData[eFullName]=full_name
                            hashData[eMail]=email
                            hashData[eJob]=job
                            hashData[eDepartment]=department
                            hashData[eImageUrl]=url.toString()
                            hashData[eEndYear]=endYear

                            firebaseStore.collection(collectionPath)
                                .document(uid)
                                .set(hashData)
                                .addOnCompleteListener {
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

    fun getAllEmployee(department: String):LiveData<MutableList<UserModel>>{
        val set= mutableListOf<UserModel>()
        firebaseStore.collection(collectionPath).whereEqualTo(eDepartment,department).get().addOnSuccessListener {

                    for (document in it) {

                        val name: String? = document.getString(eFullName)
                        val job: String? = document.getString(eJob)
                        val imageUrl: String? = document.getString(eImageUrl)
                        val departments: String? = document.getString(eDepartment)
                        val uid: String? = document.getString(userID)
                        val endYear: String? = document.getString(eEndYear)
                        val email: String? = document.getString(eMail)

                        val userModel = UserModel(
                            uid!!,
                            email!!,
                            name!!,
                            job!!,
                            departments!!,
                            imageUrl!!,
                            endYear!!
                        )

                        if(userModel.uid!=FirebaseAuth.getInstance().currentUser?.uid){
                            set.add(userModel)
                        }
                    }
                    employees.value=set


         }.addOnFailureListener {
            //
        }
        return employees

    }

}
