package com.example.spartateamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

//data class 새로 만들까요? 아니면 그냥 제가 원래 했던대로 할까요...?
object UserDataList {
    var userDataList = mutableListOf<Map<String, String>>()
}

class SignUpActivity : AppCompatActivity() {

    //아이디 중복체크 위한 리스트
    val idList = UserDataList.userDataList.flatMap { it["id"]?.split(",") ?: emptyList() }

    lateinit var iv_image: ImageView

    lateinit var et_userName: EditText
    lateinit var et_userId: EditText
    lateinit var et_userPw: EditText
    lateinit var et_checkPw: EditText
    lateinit var et_userEmail: EditText
    lateinit var et_userAuthent: EditText

    lateinit var tv_authent: TextView
    var generatedAuthent = ""

    lateinit var btn_checkId: Button
    lateinit var btn_createAuthent: Button
    lateinit var btn_checkAuthent: Button
    lateinit var btn_join: Button

    var isIdOk = false
    var isAuthentOk = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        TextToSpeech()

        Toast.makeText(this,"$idList",Toast.LENGTH_SHORT).show()

        iv_image = findViewById<ImageView>(R.id.iv_signUpLogo)

        et_userName = findViewById<EditText>(R.id.et_name)
        et_userId = findViewById<EditText>(R.id.et_id)
        et_userPw = findViewById<EditText>(R.id.et_pw)
        et_checkPw = findViewById<EditText>(R.id.et_pwCheck)
        et_userEmail = findViewById<EditText>(R.id.et_email)
        et_userAuthent = findViewById<EditText>(R.id.et_authent)

        tv_authent = findViewById<TextView>(R.id.tv_authent)

        btn_checkId = findViewById<Button>(R.id.btn_checkId)
        btn_createAuthent = findViewById<Button>(R.id.btn_newAuthent)
        btn_checkAuthent = findViewById<Button>(R.id.btn_checkAuthent)
        btn_join = findViewById<Button>(R.id.btn_join)

        lateinit var newName: String
        lateinit var newId: String
        lateinit var newPw: String
        lateinit var checkPw: String
        lateinit var newEmail: String
        lateinit var newAuthent: String


        //이미지 선택 버튼 코드 작성

        //

        //

        //

        //

        btn_checkId.setOnClickListener {
            newId = et_userId.text.toString()
            checkId(newId)
        }

        generateAuthent()

        btn_createAuthent.setOnClickListener {
            generateAuthent()
            btn_checkAuthent.text = getString(R.string.btn_checkAuthent)
            btn_checkAuthent.visibility = View.VISIBLE
        }

        btn_checkAuthent.setOnClickListener {
            newAuthent = et_userAuthent.text.toString()
            if (generatedAuthent == newAuthent) {
                isAuthentOk = true
                Toast.makeText(this, getString(R.string.authent_correct), Toast.LENGTH_SHORT).show()
                btn_checkAuthent.text = getString(R.string.authent_correct)
                btn_checkAuthent.visibility = View.GONE
            } else {
                isAuthentOk = false
                Toast.makeText(this, getString(R.string.authent_not_correct), Toast.LENGTH_SHORT)
                    .show()
                generateAuthent()
            }
        }

        btn_join.setOnClickListener {
            newName = et_userName.text.toString()
            newId = et_userId.text.toString()
            newPw = et_userPw.text.toString()
            checkPw = et_checkPw.text.toString()
            newEmail = et_userEmail.text.toString()
            newAuthent = et_userAuthent.text.toString()
            checkAndReg(newName, newId, newPw, checkPw, newEmail, newAuthent)
//            Toast.makeText(
//                this,
//                "${newName.isEmpty()} , ${newId.isEmpty()} , ${newPw.isEmpty()} , ${checkPw.isEmpty()} , ${newEmail.isEmpty()} , ${newAuthent.isEmpty()}",
//                Toast.LENGTH_SHORT
//            ).show()

        }


    }

    fun checkId(newId: String) {
        if (idList.isNotEmpty()) {
            if (idList.contains(newId)) {
                isIdOk = false
                //             Toast.makeText(this,"11", Toast.LENGTH_SHORT).show()
            } else {
                isIdOk = true
                //             Toast.makeText(this,"22", Toast.LENGTH_SHORT).show()
            }
        } else {
            isIdOk = true
            //         Toast.makeText(this,"33", Toast.LENGTH_SHORT).show()
        }
        if (isIdOk && strongID(newId)) {
            Toast.makeText(this, getText(R.string.available_id), Toast.LENGTH_SHORT).show()
        } else {
            if (!isIdOk) {
                Toast.makeText(this, getText(R.string.nonavailable_id), Toast.LENGTH_SHORT).show()
            }else if(!strongID(newId)) {
                Toast.makeText(this, getText(R.string.id_not_strong), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun generateAuthent() {
        generatedAuthent = generateRandom()
        //Log.d("SignUpActivity", "$generatedAuthent")
        tv_authent.text = generatedAuthent
        et_userAuthent.text.clear()
    }

    private fun generateRandom(): String {
        val characters = ('a'..'z') + ('A'..'H')+('J'..'Z') + ('1'..'9')
        val length = 6
        return (1..length).map { characters.random() }.joinToString("")
    }

    private fun checkEmail(newEmail: String): Boolean {
//        val emailSample = "[a-zA-Z0-9._-]+@[a-z]+||.+[a-z]+"
        val emailSample = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"

        return if (!newEmail.matches(emailSample.toRegex())) {
            false
        } else {
            true
        }
    }

    private fun strongID(newId: String): Boolean {
        if (newId.length >= 5) {
            return true
        } else {
            return false
        }
    }

    //비밀번호 강도 부분... 수정중입니다.
//    private fun checkPW(newPw: String):Boolean{
//        val characters =
//
//    }

    private fun checkAndReg(
        newName: String,
        newId: String,
        newPw: String,
        checkPw: String,
        newEmail: String,
        newAuthent: String
    ) {
        if (newName.isEmpty() || newId.isEmpty() || newPw.isEmpty() || checkPw.isEmpty() || newEmail.isEmpty() || newAuthent.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_in_et), Toast.LENGTH_SHORT).show()
            return
        }
        if (!strongID(newId)) {
            Toast.makeText(this, getString(R.string.id_not_strong), Toast.LENGTH_SHORT).show()
            return
        }
        if (newPw != checkPw) {
            Toast.makeText(this, getString(R.string.pw_not_same), Toast.LENGTH_SHORT).show()
            return
        }
        //비밀번호 강도 부분 수정중입니다.
//        if(){
//
//        }
        if (!isIdOk) {
            Toast.makeText(this, getString(R.string.id_not_available), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (!checkEmail(newEmail)) {
            Toast.makeText(this, getString(R.string.email_not_correct), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (newAuthent != generatedAuthent) {
            Toast.makeText(this, getString(R.string.authent_not_correct), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if(!isAuthentOk){
            Toast.makeText(this,getString(R.string.authent_check_required),Toast.LENGTH_SHORT).show()
        }

        val objUserInfo: MutableMap<String, String> = mutableMapOf()
        objUserInfo["name"] = newName
        objUserInfo["id"] = newId
        objUserInfo["pw"] = newPw
        objUserInfo["email"] = newEmail

        UserDataList.userDataList.add(objUserInfo)

        val returnIntent = Intent(this, SignInActivity::class.java)
        returnIntent.putExtra("userID", newId)
        returnIntent.putExtra("userPW", newPw)
        setResult(RESULT_OK, returnIntent)

        if (!isFinishing) finish()


    }


}