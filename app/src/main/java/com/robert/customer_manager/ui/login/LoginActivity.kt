package com.robert.customer_manager.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.robert.customer_manager.R
import com.robert.customer_manager.databinding.ActivityLoginBinding
import com.robert.customer_manager.session.Session
import com.robert.customer_manager.ui.HomeActivity
import com.robert.customer_manager.ui.registration.RegisterStaff
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein


class LoginActivity : AppCompatActivity(),Session, KodeinAware {


    override val kodein by kodein()
    private val factory: UserViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding

    lateinit var viewModel: UserViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)

        viewModel= ViewModelProviders.of(this,factory).get(UserViewModel::class.java)

        if(viewModel.user!=null){

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginViewModel=viewModel

        viewModel.listener=this


        binding.register.setOnClickListener {
            val intent=Intent(this,RegisterStaff::class.java)
            startActivity(intent)
        }

    }

    override fun onStarted() {
        binding.progressBar.visibility=View.VISIBLE
    }

    override fun onSuccess() {
        binding.progressBar.visibility=View.GONE
        val intent= Intent(this@LoginActivity,
        HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onEmpty() {
        binding.progressBar.visibility=View.GONE
        binding.email.error = "input are empty"
    }

    override fun onFail() {
        binding.progressBar.visibility=View.GONE
        binding.email.error = "wrong username and password"

    }

    override fun onLogout() {

    }


}
