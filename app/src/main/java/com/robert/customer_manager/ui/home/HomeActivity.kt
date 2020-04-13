package com.robert.customer_manager.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.robert.customer_manager.R
import com.robert.customer_manager.databinding.ActivityHomeBinding
import com.robert.customer_manager.session.Session
import com.robert.customer_manager.ui.login.LoginActivity
import com.robert.customer_manager.ui.login.UserViewModel


class HomeActivity : AppCompatActivity(),Session {

    private lateinit var viewModel: UserViewModel

    private val newsFragment= NewsFragment()
    private val departmentFragment=
        DepartmentFragment()
    private val messageFragment=
        ProfileFragment()

    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_home)

        viewModel= ViewModelProvider(this).get(UserViewModel::class.java)

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



