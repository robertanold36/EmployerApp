package com.robert.customer_manager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.robert.customer_manager.R
import com.robert.customer_manager.adapter.ChatFromAdapter
import com.robert.customer_manager.adapter.ChatToAdapter
import com.robert.customer_manager.databinding.ActivityChatBinding
import com.robert.customer_manager.model.ChatModel
import com.robert.customer_manager.model.UserModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import java.util.*
import kotlin.collections.HashMap


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "CAST_NEVER_SUCCEEDS")
class ChatActivity : AppCompatActivity(){
    private lateinit var binding:ActivityChatBinding
    private val collectionPath:String="chats"
    private val msgID:String="chat ID"
    private val sender:String="Sender ID"
    private val receiver:String="Receiver ID"
    private val message:String="message"
    private var firebaseStore:FirebaseFirestore?=null
    private  val senderID=FirebaseAuth.getInstance().uid?:""



    companion object{
        var user:UserModel?=null
        var chat:ChatModel?=null
    }

    private val adapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_chat)
        firebaseStore= FirebaseFirestore.getInstance()


        user=intent.getParcelableExtra("USER_KEY")
        supportActionBar?.title=user!!.fullName

        binding.chatRecyclerview.adapter=adapter

        binding.send.setOnClickListener {
            adapter.add(ChatToAdapter(binding.editText.text.toString()))
             sendMessage(binding.editText.text.toString())
             binding.editText.text.clear()
        }

        loadSendMsg(user!!)
        loadMsgReceived(user!!)

    }

    private fun sendMessage(text:String){


           val chatID=UUID.randomUUID().toString()
           val receiverID=user?.uid

           val hashMap=HashMap<String,Any>()
               hashMap[msgID]=chatID
               hashMap[sender]= senderID
               hashMap[receiver]=receiverID!!
               hashMap[message]= text

            firebaseStore?.collection(collectionPath)?.
            document(senderID)?.
            collection(message)?.
            document(chatID)?.
            set(hashMap)?.
            addOnSuccessListener {

            }
    }


    private fun loadSendMsg(userModel: UserModel){

        firebaseStore?.collection(collectionPath)?.
        document(senderID)?.
        collection(message)?.
        whereEqualTo(receiver,userModel.uid)?.get()?.addOnSuccessListener {

            for(document in it){

                val  chatID: String? =document.getString(msgID)
                val  senderID: String? =document.getString(sender)
                val  receiver: String? =document.getString(receiver)
                val  msg: String? =document.getString(message)
                val chatModel=ChatModel(chatID!!,senderID!!,receiver!!,msg!!)

                adapter.add(ChatToAdapter(chatModel.msg))

            }

        }
    }

    private fun loadMsgReceived(userModel: UserModel){

        firebaseStore?.collection(collectionPath)?.
        document(userModel.uid)?.
        collection(message)?.
        whereEqualTo(receiver,senderID)?.get()?.addOnSuccessListener {
            for (document in it){

                val  chatID: String? =document.getString(msgID)
                val  senderID: String? =document.getString(sender)
                val  receiver: String? =document.getString(receiver)
                val  msg: String? =document.getString(message)
                val chatModel=ChatModel(chatID!!,senderID!!,receiver!!,msg!!)

                adapter.add(ChatFromAdapter(chatModel.msg))
            }
        }

    }

}

