package com.example.spartateamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        var btn_join = findViewById<Button>(R.id.btn_signup)

        btn_join.setOnClickListener {
            val changeIntent = Intent(this,SignUpActivity::class.java)
            startActivity(changeIntent)
        }
    }


}