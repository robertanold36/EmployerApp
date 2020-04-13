package com.robert.customer_manager.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.robert.customer_manager.R
import com.robert.customer_manager.adapter.LatestMessageAdapter
import com.robert.customer_manager.ui.allEmployee.AllEmployees
import com.robert.customer_manager.databinding.ActivityLatestMessageBinding
import com.robert.customer_manager.model.ChatModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class LatestMessage : AppCompatActivity() {
    private var category:String?=null
    private lateinit var binding:ActivityLatestMessageBinding
    private val adapter=GroupAdapter<ViewHolder>()
    private  val senderId= FirebaseAuth.getInstance().uid?:""
    private val latestMessageMap=HashMap<String,ChatModel>()

    private val firebaseDatabase:FirebaseDatabase by lazy{
        FirebaseDatabase.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_latest_message)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.latestMsgRecyclerview.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        category=intent.getStringExtra("category")
        binding.latestMsgRecyclerview.adapter=adapter
        listenLatestMsg()

    }

    private fun refreshRecyclerView(){
        adapter.clear()
        latestMessageMap.values.forEach {
            adapter.add(LatestMessageAdapter(it,this))
        }
    }



    private fun listenLatestMsg(){


       val reference=firebaseDatabase.getReference("/latest_message/${senderId}")

        reference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatModel=p0.getValue(ChatModel::class.java)
                latestMessageMap[p0.key!!]=chatModel!!
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatModel=p0.getValue(ChatModel::class.java)
                latestMessageMap[p0.key!!]=chatModel!!
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_users ->{
                val intent= Intent(this, AllEmployees::class.java)
                intent.putExtra("category",category)
                startActivity(intent)
            }

            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
