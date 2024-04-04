package com.example.spartateamproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ImageButton
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detail_id = intent.getStringExtra("id")
        //UserDataPull 이용해서 member 데이터 받아오기
        //if 어쩌구 저쩌구 * 4
        //detail_profile.setimage(_img)
        //detail_name.settext(_name)
        //detail_email.settext(_email)
        //detail_MBTI.settext("@string/GITJ)

        //setting액티비티로 이동 버튼
        //val btn_setting = findViewById<ImageButton>(R.id.btn_detail_setting)
        //btn_setting.setOnClickListener {
        // val intent = Intent(this@DetailActivity, SettingActivity::class.java)
        // intent.putExtra("id", detail_id)
        // startActivity(intent)

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

        val btn_back = findViewById<ImageButton>(R.id.btn_detail_back)
        btn_back.setOnClickListener {
            finish()
        }
    }
}