package com.kingsmwai.pizza_order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kingsmwai.pizza_order.Controller.SignUpActivityy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start_order.setOnClickListener {
            val loginIntent = Intent(this, SignUpActivityy::class.java)
            startActivity(loginIntent)
        }

    }

}
