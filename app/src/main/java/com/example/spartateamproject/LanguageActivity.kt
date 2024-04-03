package com.example.spartateamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class LanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val languageBackButton = findViewById<ImageButton>(R.id.imgBtn_languageBack)

        languageBackButton.setOnClickListener {
            finish()
        }

    }
}