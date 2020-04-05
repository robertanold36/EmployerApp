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
import com.robert.customer_manager.model.UserModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import java.util.*
import kotlin.collections.HashMap

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    private val collectionPath:String="chats"
    private val msgID:String="chat ID"
    private val sender:String="Sender ID"
    private val receiver:String="Receiver ID"
    private val cTime:String="Time"
    private val message:String="message"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_chat)

        val user=intent.getParcelableExtra<UserModel>("USER_KEY")
        supportActionBar?.title=user.fullName

        val adapter=GroupAdapter<ViewHolder>()

        binding.chatRecyclerview.adapter=adapter

        binding.send.setOnClickListener {
             adapter.add(ChatToAdapter(binding.editText.text.toString()))
             sendMessage(binding.editText.text.toString())
             binding.editText.text.clear()

        }

    }

    private fun sendMessage(text:String){

        val user=intent.getParcelableExtra<UserModel>("USER_KEY")

        val chatID=UUID.randomUUID().toString()
        val senderID=FirebaseAuth.getInstance().uid?:""
        val receiverID=user.uid
        val time=System.currentTimeMillis()/1000

        val firebaseStore=FirebaseFirestore.getInstance()
        val hashData=HashMap<String,Any>()
            hashData[msgID]=chatID
            hashData[sender]=senderID
            hashData[receiver]=receiverID
            hashData[cTime]=time
            hashData[message]=text
            firebaseStore.collection(collectionPath).document(user.uid).set(hashData).addOnSuccessListener {

            }
    }
}
