package com.robert.customer_manager.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.auth.User
import com.robert.customer_manager.ui.ChatActivity
import com.robert.customer_manager.R
import com.robert.customer_manager.model.UserModel
import kotlinx.android.synthetic.main.all_employees.view.*


class AllEmployeeAdapter (var context:Context):RecyclerView.Adapter<AllEmployeeAdapter.AllEmployeeViewHolder>(){

    private var employeeDetails= mutableListOf<UserModel>()


    fun setList(data:MutableList<UserModel>){
        employeeDetails=data
    }

    inner class AllEmployeeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        fun bindView(userModel: UserModel){
            itemView.name.text=userModel.fullName
            itemView.job.text=userModel.job
            Glide.with(context).load(userModel.imageUrl).into(itemView.profile)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllEmployeeViewHolder {
           val view=LayoutInflater.from(parent.context).inflate(R.layout.all_employees,parent,false)
           return AllEmployeeViewHolder(view)

    }

    override fun getItemCount(): Int {
        return if (employeeDetails.size>0){
            employeeDetails.size
        } else{
            0
        }
    }


    override fun onBindViewHolder(holder: AllEmployeeViewHolder, position: Int) {

        val userModel:UserModel=employeeDetails[position]
        holder.bindView(userModel)

        holder.itemView.name.setOnClickListener {
            val intent=Intent(context,
            ChatActivity::class.java)
            intent.putExtra("USER_KEY",userModel)
            context.startActivity(intent)
        }

    }

}