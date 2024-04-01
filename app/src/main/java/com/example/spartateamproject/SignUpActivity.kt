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
    lateinit var shownAuthent : String

    lateinit var btn_checkId : Button
    lateinit var btn_createAuthent : Button
    lateinit var btn_checkAuthent : Button
    lateinit var btn_join : Button

    var isIdOk = false
    var isAuthentOk = false


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
        shownAuthent = tv_authent.text.toString()

        btn_checkId = findViewById<Button>(R.id.btn_checkId)
        btn_createAuthent = findViewById<Button>(R.id.btn_newAuthent)
        btn_checkAuthent = findViewById<Button>(R.id.btn_checkAuthent)
        btn_join = findViewById<Button>(R.id.btn_join)

        var newName = et_userName.text.toString()
        var newId = et_userName.text.toString()
        var newPw = et_userName.text.toString()
        var checkPw = et_userName.text.toString()
        var newEmail = et_userName.text.toString()
        var newAuthent = et_userName.text.toString()



        btn_checkId.setOnClickListener {
            checkId(newId)
        }

        shownAuthent = generateAuthent(6)
        tv_authent.text = shownAuthent

        btn_createAuthent.setOnClickListener {
            shownAuthent = generateAuthent(6)
            tv_authent.text = shownAuthent
        }

        btn_checkAuthent.setOnClickListener {
            if(shownAuthent==newAuthent){
                isAuthentOk = true
                Toast.makeText(this,getString(R.string.authent_correct), Toast.LENGTH_SHORT).show()
                btn_checkAuthent.text = getString(R.string.authent_correct)
                //버튼 글자 바뀌게 했는데... 이게 작동할지는 모르겠어요
            }else{
                isAuthentOk = false
                Toast.makeText(this,getString(R.string.authent_not_correct), Toast.LENGTH_SHORT).show()
            }
        }

        btn_join.setOnClickListener {
            checkAndReg(newName, newId, newPw, checkPw, newEmail, newAuthent)
        }


    }

    fun checkId(newId : String){
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

    private fun generateAuthent(length: Int): String{
        val charList : List<Char> = ('a'..'z')+('A'..'Z')+('0'..'9')
        return(1..length).map{charList.random()}.joinToString {""}
    }

    private fun checkAndReg(newName: String, newId: String, newPw: String, checkPw: String, newEmail: String, newAuthent: String) {
        if(newName.isEmpty()||newId.isEmpty()||newPw.isEmpty()||checkPw.isEmpty()||newEmail.isEmpty()||newAuthent.isEmpty()){
            Toast.makeText(this,getString(R.string.empty_in_et), Toast.LENGTH_SHORT).show()
            return
        }
        if(newPw!=checkPw){
            Toast.makeText(this,getString(R.string.pw_not_same),Toast.LENGTH_SHORT).show()
            return
        }
        if(!isIdOk){
            Toast.makeText(this,getString(R.string.id_not_available),Toast.LENGTH_SHORT).show()
            return
        }
        if(!newEmail.contains('@')){
            Toast.makeText(this,getString(R.string.email_not_correct),Toast.LENGTH_SHORT).show()
            return
        }
        if(newAuthent!=shownAuthent){
            Toast.makeText(this,getString(R.string.authent_not_correct),Toast.LENGTH_SHORT).show()
            return
        }

        val objUserInfo: MutableMap<String, String> = mutableMapOf()
        objUserInfo["name"]=newName
        objUserInfo["id"]=newId
        objUserInfo["pw"]=newPw
        objUserInfo["email"]=newEmail

        UserDataList.userDataList.add(objUserInfo)

    }


}