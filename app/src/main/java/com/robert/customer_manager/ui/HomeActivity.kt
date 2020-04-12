package com.robert.customer_manager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.robert.customer_manager.R
import com.robert.customer_manager.databinding.ActivityHomeBinding
import com.robert.customer_manager.session.Session
import com.robert.customer_manager.ui.login.LoginActivity
import com.robert.customer_manager.ui.login.UserViewModel
import com.robert.customer_manager.ui.login.UserViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class HomeActivity : AppCompatActivity(),KodeinAware, Session {

    override val kodein by kodein()
    private lateinit var viewModel: UserViewModel
    private val factory: UserViewModelFactory by instance()

    private val newsFragment=NewsFragment()
    private val departmentFragment=DepartmentFragment()
    private val messageFragment= LatestMessageFragment()

    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_home)

        viewModel=ViewModelProviders.of(this,factory).get(UserViewModel::class.java)

        viewModel.listener=this

        fragmentChange(newsFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.update->{
                    fragmentChange(newsFragment)
                }

                R.id.msg->{
                    fragmentChange(messageFragment)
                }

                else->{
                    fragmentChange(departmentFragment)
                }
            }

            true
        }

  }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.logout->{
                 viewModel.logout()
            }

            else->Toast.makeText(this,"hellow thank for using  our app",Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStarted() {
    }

    override fun onSuccess() {
    }

    override fun onEmpty() {
    }

    override fun onFail() {
    }

    override fun onLogout() {

        val intent=Intent(this@HomeActivity,
        LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun fragmentChange(fragment: Fragment){
         supportFragmentManager.beginTransaction().apply {
             replace(R.id.fragment,fragment)
             commit()
         }
    }

}



