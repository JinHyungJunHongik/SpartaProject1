package com.example.spartateamproject

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
//메인에 출력 용으로 넣을 더미 데이터 멤버 타입
val UserdataPull = mutableListOf<Member>(
    Member("HyungJun0123", "qwe12345", "형준", R.drawable.img_01, "nsisn@naver.com"),
    Member("Ji__Won", "qwe12345", "지원", R.drawable.img_02,"jicircle@gmail.com"),
    Member("y__su", "qwe12345", "연수", R.drawable.img_03, "yeonsu1@gmail.com"),
    Member("taeJun_2", "qwe12345", "태준", R.drawable.img_04, "tj12@gmail.com"),
)
class SignInActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var id : EditText
    lateinit var password : EditText
    lateinit var login : Button
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




    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
        inputUserObject()
        goToMain()
        goToSignUp()
        lifecycleScope.launch {
            whenStarted {
                while (true) {
                    isLoginAvailable()
                    delay(500)
                }
            }
        }
    }


    private fun init() {
        image = findViewById(R.id.img_sign_in_logo)
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

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
                            val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, login, "trans_all")
                            //id 넘기기
                            loginIntent.putExtra("id", idText)
                            startActivity(loginIntent)
                            finish()
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
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, signup, "trans_all")
            prevInfo.launch(intent, options)
        }
    }

    //유저 데이터리스트에 넣기 =>임시
    private fun inputUserObject() {
        UserdataPull.forEach {
            var objUserInfo: MutableMap<String, String> = mutableMapOf()
            objUserInfo["id"] = it._id
            objUserInfo["pw"] = it._password
            objUserInfo["name"] = it._name
            objUserInfo["image"] = it._img.toString()
            objUserInfo["email"] = it._email
            UserDataList.userDataList.add(objUserInfo)
        }
    }

}