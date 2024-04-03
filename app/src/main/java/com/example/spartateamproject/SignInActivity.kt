package com.example.spartateamproject

<<<<<<< HEAD
import android.app.Activity
=======
>>>>>>> 90c8441c247cd1edb36c66300ef0c10155aa712a
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
<<<<<<< HEAD
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
=======
>>>>>>> 90c8441c247cd1edb36c66300ef0c10155aa712a
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
<<<<<<< HEAD

=======
>>>>>>> 90c8441c247cd1edb36c66300ef0c10155aa712a

class SignInActivity : AppCompatActivity() {
    lateinit var id : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var signup: Button
<<<<<<< HEAD

    //이전에 회원가입한 적이 있는지 확인
    var isFirst = true

    //직전에 회원가입한 경우, 해당 계정 정보 가져옴
    var prevInfo : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if(it.resultCode == Activity.RESULT_OK){
            val prevId = it.data?.getStringExtra("userID")
            val prevPw = it.data?.getStringExtra("userPW")
            id.setText(prevId)
            password.setText(prevPw)

            isFirst = false
        }
    }

=======
>>>>>>> 90c8441c247cd1edb36c66300ef0c10155aa712a
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
<<<<<<< HEAD
=======
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
>>>>>>> 90c8441c247cd1edb36c66300ef0c10155aa712a
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
            prevInfo.launch(intent)
        }
    }
}
