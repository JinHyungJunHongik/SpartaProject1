package com.example.spartateamproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val myPageBackButton = findViewById<ImageButton>(R.id.imgBtn_myPageBack)

        myPageBackButton.setOnClickListener {
            finish()
        }
    }
}