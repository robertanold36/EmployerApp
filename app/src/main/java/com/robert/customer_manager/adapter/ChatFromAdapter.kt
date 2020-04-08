package com.robert.customer_manager.adapter

import android.content.Context
import com.robert.customer_manager.R
import com.robert.customer_manager.model.ChatModel
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.messege_from.view.*

class ChatFromAdapter(private val text:String):Item<ViewHolder>() {

    override fun getLayout(): Int {

        return R.layout.messege_from
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
         viewHolder.itemView.msg_from.text=text
    }


}