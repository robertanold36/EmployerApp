package com.robert.customer_manager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.robert.customer_manager.R
import com.robert.customer_manager.ui.home.HomeActivity

class Congratulation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congratulation)

        Handler().postDelayed(
            {
                startActivity(Intent(this@Congratulation,
                    HomeActivity::class.java))
                finish()
            },5000L
        )
    }
}
