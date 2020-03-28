package com.robert.customer_manager.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.robert.customer_manager.R
import com.robert.customer_manager.databinding.ActivityLoginBinding
import com.robert.customer_manager.session.Session
import com.robert.customer_manager.viewModel.UserViewModel
import com.robert.customer_manager.viewModel.UserViewModelFactory
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(),Session,KodeinAware {

    override val kodein by kodein()

    private lateinit var binding: ActivityLoginBinding
    private val viewModelFactory:UserViewModelFactory by instance()
    private lateinit var viewModel: UserViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)

        viewModel=ViewModelProviders.of(this,viewModelFactory).get(UserViewModel::class.java)
        binding.loginViewModel=viewModel

        viewModel.listener=this



    }

    override fun onStarted() {
        binding.progressBar.visibility=View.VISIBLE
    }

    override fun onSuccess() {
        binding.progressBar.visibility=View.GONE
        val intent= Intent(this@LoginActivity,
        HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onEmpty() {
        binding.progressBar.visibility=View.GONE
        binding.email.error = "input are empty"
    }

    override fun onFail() {
        binding.progressBar.visibility=View.GONE
        binding.email.error = "wrong username and password"

    }
}
