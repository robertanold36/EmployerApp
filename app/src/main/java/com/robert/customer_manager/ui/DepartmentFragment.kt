package com.robert.customer_manager.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.robert.customer_manager.R
import com.robert.customer_manager.databinding.FragmentDepartmentBinding


class DepartmentFragment : Fragment() {
    private lateinit var binding:FragmentDepartmentBinding
    private val business:String="ACCOUNTANT"
    private val hospital="HEALTH CENTER"
    private val driver="DRIVERS"
    private val ict="IT SUPPORT"
    private val callCenter="CALL CENTER"
    private val student="EDUCATION"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_department, container, false)

        binding.apply {
            accountant.setOnClickListener {
               viewEmployee(business)
            }

            itCenter.setOnClickListener {
                viewEmployee(ict)
            }

            drivers.setOnClickListener {
                viewEmployee(driver)

            }

            call.setOnClickListener {
                viewEmployee(callCenter)

            }

            health.setOnClickListener {
                viewEmployee(hospital)
            }

            education.setOnClickListener {
                viewEmployee(student)
            }
        }



       return binding.root
    }

    private fun viewEmployee(category:String){
        val intent= Intent(activity, LatestMessage::class.java)
        intent.putExtra("category",category)
        startActivity(intent)
    }
}
