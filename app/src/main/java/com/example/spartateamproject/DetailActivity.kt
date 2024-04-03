package com.example.spartateamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 기본 이메일 앱 연결
        val email_link = findViewById<TextView>(R.id.tx_detail_email)
        email_link.movementMethod = LinkMovementMethod.getInstance()
    }
}