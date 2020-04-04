package com.robert.customer_manager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.robert.customer_manager.R
import com.robert.customer_manager.adapter.AllEmployeeAdapter
import com.robert.customer_manager.databinding.ActivityAllEmployeesBinding
import com.robert.customer_manager.viewModel.GetAllEmployerViewFactory
import com.robert.customer_manager.viewModel.GetAllEmployerViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein


class AllEmployees : AppCompatActivity(),KodeinAware {

    override val kodein by kodein()
    private val factory:GetAllEmployerViewFactory by instance()
    lateinit var viewModel: GetAllEmployerViewModel

    private lateinit var adapter:AllEmployeeAdapter
    private lateinit var binding:ActivityAllEmployeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,
            R.layout.activity_all_employees
        )

        viewModel=ViewModelProviders.of(this,factory).get(GetAllEmployerViewModel::class.java)

        adapter= AllEmployeeAdapter(this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter


        viewModel.getAllEmployer("IT SUPPORT").observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}
