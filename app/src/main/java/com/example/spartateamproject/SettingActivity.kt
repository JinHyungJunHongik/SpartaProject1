package com.example.spartateamproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        myPageBack()
        editProfile()


    }

    private fun myPageBack() { // 뒤로가기
        val myPageBackButton = findViewById<ImageButton>(R.id.imgBtn_myPageBack)

        myPageBackButton.setOnClickListener {
            finish()
        }
    }

    private fun editProfile() { // 프로필 편집으로 이동
        val editProfile = findViewById<TextView>(R.id.tv_editProfile)

        editProfile.setOnClickListener {

        }
    }






}