package com.robert.customer_manager.adapter

import com.robert.customer_manager.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.messege_to.view.*

class ChatToAdapter(private val text:String):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.messege_to
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
          viewHolder.itemView.msg_to.text=text
    }
}