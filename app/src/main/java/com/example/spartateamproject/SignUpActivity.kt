package com.example.spartateamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

//data class 새로 만들까요? 아니면 그냥 제가 원래 했던대로 할까요...?
object UserDataList{
    var userDataList = mutableListOf<Map<String, String>>()
}

class SignUpActivity : AppCompatActivity() {

    //아이디 중복체크 위한 리스트
    val idList = UserDataList.userDataList.flatMap { it["id"]?.split(",")?: emptyList() }

    lateinit var et_userName : EditText
    lateinit var et_userId : EditText
    lateinit var et_userPw : EditText
    lateinit var et_checkPw : EditText
    lateinit var et_userEmail : EditText
    lateinit var et_userAuthent : EditText

    lateinit var tv_authent : TextView

    lateinit var btn_checkId : Button
    lateinit var btn_join : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        et_userName = findViewById<EditText>(R.id.et_name)
        et_userId = findViewById<EditText>(R.id.et_id)
        et_userPw = findViewById<EditText>(R.id.et_pw)
        et_checkPw = findViewById<EditText>(R.id.et_pwCheck)
        et_userEmail = findViewById<EditText>(R.id.et_email)
        et_userAuthent = findViewById<EditText>(R.id.et_authent)

        tv_authent = findViewById<TextView>(R.id.tv_authent)

        btn_checkId = findViewById<Button>(R.id.btn_checkId)
        btn_join = findViewById<Button>(R.id.btn_join)

        var newName = et_userName.text.toString()
        var newId = et_userName.text.toString()
        var newPw = et_userName.text.toString()
        var checkPw = et_userName.text.toString()
        var newEmail = et_userName.text.toString()
        var newAuthent = et_userName.text.toString()

        var isIdOk = false


        btn_checkId.setOnClickListener {
            if(idList.isNotEmpty()){
                if(idList.contains(newId)){
                    isIdOk=false
                }else{
                    isIdOk=true
                }
            }else{
                isIdOk = true
            }
            if(isIdOk){
                Toast.makeText(this,getText(R.string.available_id), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,getText(R.string.nonavailable_id), Toast.LENGTH_SHORT).show()
            }
        }



    }







}