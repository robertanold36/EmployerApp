package com.robert.customer_manager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.robert.customer_manager.R
import com.robert.customer_manager.adapter.ChatFromAdapter
import com.robert.customer_manager.adapter.ChatToAdapter
import com.robert.customer_manager.databinding.ActivityChatBinding
import com.robert.customer_manager.model.ChatModel
import com.robert.customer_manager.model.UserModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import java.util.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "CAST_NEVER_SUCCEEDS")
class ChatActivity : AppCompatActivity(){
    private lateinit var binding:ActivityChatBinding
    private  val senderId=FirebaseAuth.getInstance().uid?:""


    companion object{
        var user:UserModel?=null
        var chat:ChatModel?=null
    }

    private val adapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_chat)


        user=intent.getParcelableExtra("USER_KEY")
        supportActionBar?.title=user!!.fullName

        binding.chatRecyclerview.adapter=adapter

        loadMsg()

        binding.send.setOnClickListener {
             sendMessage(binding.editText.text.toString())
             binding.editText.text.clear()
            binding.chatRecyclerview.scrollToPosition(adapter.itemCount-1)
        }


    }

    private fun sendMessage(text: String) {

        val receiverId= user?.uid

        val chatThings=ChatModel(senderId,receiverId!!,text,Calendar.getInstance().time.toString())
        val reference=FirebaseDatabase.getInstance().getReference("/user/${senderId}/${receiverId}").push()
        val toReference=FirebaseDatabase.getInstance().getReference("/user/${receiverId}/${senderId}").push()

        reference.setValue(chatThings).addOnSuccessListener {
            Log.d("ChatActivity","message saved")
        }

        toReference.setValue(chatThings)

    }

    private fun loadMsg(){

        val receiverId= user?.uid

        val reference=FirebaseDatabase.getInstance().getReference("/user/${senderId}/${receiverId}")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatModel=p0.getValue(ChatModel::class.java)
                Log.d("ChatActivity",chatModel!!.msg)
                if (chatModel.senderID!=FirebaseAuth.getInstance().uid){
                    adapter.add(ChatFromAdapter(chatModel.msg))

                }
                else {
                    adapter.add(ChatToAdapter(chatModel.msg))
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }

}

