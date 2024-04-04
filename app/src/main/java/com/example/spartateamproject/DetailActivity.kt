package com.example.spartateamproject

import android.content.Intent
import android.net.Uri
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

        // 블로그 링크 연결
        val blog_link = findViewById<TextView>(R.id.tx_detail_morepost)
        blog_link.setOnClickListener {
            val url = "https://nochfm0513.tistory.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}