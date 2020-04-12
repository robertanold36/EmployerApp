package com.robert.customer_manager.adapter

import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.robert.customer_manager.R
import com.robert.customer_manager.model.ChatModel
import com.robert.customer_manager.model.UserModel
import com.robert.customer_manager.ui.ChatActivity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_latest_message.view.*

class LatestMessageAdapter(private val chatModel: ChatModel,private val context: Context):Item<ViewHolder>() {

    private val collectionPath:String="employer"
    private val userID:String="User_id"
    private val eDepartment:String="Department"
    private val eImageUrl:String="ImageUrl"
    private val eJob:String="Job"
    private val eFullName:String="FullName"
    private val eEndYear:String="EndYear"
    private val eMail:String="Email"


    private val firebaseStore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun getLayout(): Int {
        return R.layout.layout_latest_message
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        firebaseStore.collection(collectionPath).document(chatModel.receiverID).get().
        addOnSuccessListener{
            val uid: String = it.getString(userID)!!
            val email: String =it.getString(eMail)!!
            val fullName: String =it.getString(eFullName)!!
            val job: String =it.getString(eJob)!!
            val department: String =it.getString(eDepartment)!!
            val imageUrl: String =it.getString(eImageUrl)!!
            val endYear: String =it.getString(eEndYear)!!

            val userModel = UserModel(uid,email,fullName,job,department,imageUrl,endYear)

            viewHolder.itemView.latest_msg.text=chatModel.msg
            viewHolder.itemView.latest_msg_username.text=userModel.fullName
            Glide.with(context).load(userModel.imageUrl).into(viewHolder.itemView.latest_msg_profile)

            viewHolder.itemView.setOnClickListener {
                val intent= Intent(context,
                    ChatActivity::class.java)
                intent.putExtra("USER_KEY",userModel)
                context.startActivity(intent)
            }
         }


    }
}