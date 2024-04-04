package com.example.spartateamproject


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class SignInActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var id: EditText
    lateinit var password: EditText
    lateinit var login: Button
    lateinit var signup: Button

    //이전에 회원가입한 적이 있는지 확인
    var isFirst = true

    //직전에 회원가입한 경우, 해당 계정 정보 가져옴
    var prevInfo: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val prevImage = it.data?.getStringExtra("userImage")?.toInt()
                val prevId = it.data?.getStringExtra("userID")
                val prevPw = it.data?.getStringExtra("userPW")
                image.setImageResource(prevImage!!)
                id.setText(prevId)
                password.setText(prevPw)

                isFirst = false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init() //1
        goToMain() //2
        goToSignUp() //3
        lifecycleScope.launch {
            whenStarted {
                while(true){
                    isLoginAvailable()
                    delay(500)
                }
            } //순서상관없이 돌아가는 코드
        }
    }//


    private fun init() {
        id = findViewById(R.id.edit_sign_in_id)
        password = findViewById(R.id.edit_sign_in_pwd)
        login = findViewById(R.id.btn_login)
        signup = findViewById(R.id.btn_signup)
    }

    private fun isLoginAvailable() {
        var checkId = false
        var checkPwd = false
        if (id.text.length >= 5)
            checkId = true
        else
            checkId = false
        if (password.text.length >= 8)
            checkPwd = true
        else
            checkPwd = false
        login.isEnabled = checkId && checkPwd
    }
    
    private fun goToMain() {
        login.setOnClickListener {

            if (id.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.et_no_id), Toast.LENGTH_SHORT).show()
            }
            if (password.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.et_no_pw), Toast.LENGTH_SHORT).show()
            }

            if (id.text.isNotEmpty() && password.text.isNotEmpty()) {
                var idText = id.text.toString()
                var pwText = password.text.toString()
                var isMatched = 0

                UserDataList.userDataList.forEach { user ->
                    var objID = user["id"]
                    var objPW = user["pw"]

                    if (objID == idText) {
                        if (objPW == pwText) {
                            isMatched = 2
                            Toast.makeText(this, getString(R.string.login_ok), Toast.LENGTH_SHORT).show()
                            val loginIntent = Intent(this@SignInActivity, MainActivity::class.java)
                            //id 넘기기
                            loginIntent.putExtra("id", idText)
                            startActivity(loginIntent)
                        } else {
                        }
                    } else {
                    }
                }
                if (isMatched == 0) {
                    Toast.makeText(this, getString(R.string.et_check_idpw), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToSignUp() {
        signup.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            prevInfo.launch(intent)

        }
    }
}
