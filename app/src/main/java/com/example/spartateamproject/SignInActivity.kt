package com.example.spartateamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    lateinit var id : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
        goToMain()
        goToSignUp()
        lifecycleScope.launch {
            whenStarted {
                while(true){
                    isLoginAvailable()
                    delay(500)
                }
            }
        }
    }



    private fun init() {
        id = findViewById(R.id.edit_sign_in_id)
        password = findViewById(R.id.edit_sign_in_pwd)
        login = findViewById(R.id.btn_login)
        signup = findViewById(R.id.btn_signup)
    }
    private fun isLoginAvailable() {
        var checkId = false
        var checkPwd = false
        if(id.text.length >= 5)
            checkId = true
        else
            checkId = false
        if(password.text.length >= 8)
            checkPwd = true
        else
            checkPwd = false
        login.isEnabled = checkId && checkPwd
    }
    private fun goToMain() {
        login.setOnClickListener {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun goToSignUp() {
        signup.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}