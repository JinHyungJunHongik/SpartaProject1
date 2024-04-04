package com.example.spartateamproject


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class SettingActivity : AppCompatActivity() {

    lateinit var mpId: TextView
    lateinit var mpName: TextView
    lateinit var mpEmail: TextView

    //EditProfileActivity 에서 넘어온 정보를 받음
    private var resultLanuncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {


            val etnName =result.data?.getStringExtra("etName")
            val etnEmail =result.data?.getStringExtra("etEmail")


            mpName.setText(etnName)
            mpEmail.setText(etnEmail)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //SignupActivity에서 유저정보 받아서 SettingActivity의 edittext에 계정(ID), 이름, Email 표시
        mpId = findViewById(R.id.tv_mp_user_id)
        mpName = findViewById(R.id.tv_mp_user_name)
        mpEmail = findViewById(R.id.tv_mp_user_email)

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
            val intent = Intent(this, EditProfileActivity::class.java)
            resultLanuncher.launch(intent)

        }
    }






}