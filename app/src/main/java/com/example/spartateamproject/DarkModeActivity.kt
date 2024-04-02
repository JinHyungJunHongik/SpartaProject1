package com.example.spartateamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class DarkModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_darkmode)

        val languageBackButton = findViewById<ImageButton>(R.id.imgBtn_darkModeBack)

        languageBackButton.setOnClickListener {
            finish()
        }

    }
}