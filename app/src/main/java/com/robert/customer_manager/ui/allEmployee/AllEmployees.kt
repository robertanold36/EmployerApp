package com.robert.customer_manager.ui.allEmployee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.robert.customer_manager.R
import com.robert.customer_manager.adapter.AllEmployeeAdapter
import com.robert.customer_manager.databinding.ActivityAllEmployeesBinding


class AllEmployees : AppCompatActivity() {


    private lateinit var viewModel: GetAllEmployerViewModel

    private var category:String?=null

    private lateinit var adapter:AllEmployeeAdapter
    private lateinit var binding:ActivityAllEmployeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,
            R.layout.activity_all_employees
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        category=intent.getStringExtra("category")

        viewModel= ViewModelProvider(this).get(GetAllEmployerViewModel::class.java)

        adapter= AllEmployeeAdapter(this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        )


        viewModel.getAllEmployer(category!!).observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
