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
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

//SettingActivity에서 "name" 및 기타 정보에 활용
//"name" 키를 통해 접근한 뒤 게시글 등을 함께 저장 가능
object UserDataList {
    var userDataList = mutableListOf<Map<String, String>>()
}

class SignUpActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //아이디 중복체크 위한 리스트
    val idList = UserDataList.userDataList.flatMap { it["id"]?.split(",") ?: emptyList() }

    lateinit var iv_image: ImageView
    lateinit var rg_icon: RadioGroup
    private val imageSet = mapOf(
        R.id.rb_1 to R.drawable.img_01,
        R.id.rb_2 to R.drawable.img_02,
        R.id.rb_3 to R.drawable.img_03,
        R.id.rb_4 to R.drawable.img_04,
        R.id.rb_5 to R.drawable.img_05
    )
    lateinit var im_resString : String


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
    lateinit var btn_playTts: Button
    lateinit var btn_checkAuthent: Button
    lateinit var btn_join: Button

    var isImageOk = false
    var isIdOk = false
    var isAuthentOk = false

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


//        Toast.makeText(this,"$idList",Toast.LENGTH_SHORT).show()

        iv_image = findViewById<ImageView>(R.id.iv_signUpLogo)

        rg_icon = findViewById<RadioGroup>(R.id.rg_selectIcon)

        et_userName = findViewById<EditText>(R.id.et_name)
        et_userId = findViewById<EditText>(R.id.et_id)
        et_userPw = findViewById<EditText>(R.id.et_pw)
        et_checkPw = findViewById<EditText>(R.id.et_pwCheck)
        et_userEmail = findViewById<EditText>(R.id.et_email)
        et_userAuthent = findViewById<EditText>(R.id.et_authent)

        tv_authent = findViewById<TextView>(R.id.tv_authent)

        btn_checkId = findViewById<Button>(R.id.btn_checkId)
        btn_createAuthent = findViewById<Button>(R.id.btn_newAuthent)
        btn_playTts = findViewById<Button>(R.id.btn_tts)
        btn_checkAuthent = findViewById<Button>(R.id.btn_checkAuthent)
        btn_join = findViewById<Button>(R.id.btn_join)

        lateinit var newName: String
        lateinit var newId: String
        lateinit var newPw: String
        lateinit var checkPw: String
        lateinit var newEmail: String
        lateinit var newAuthent: String


        //이미지 선택 버튼 코드 작성. 선택한 것에 따라 바로 반영되게 함
        //_ 는 사용되지 않는 매개변수를 의미
        rg_icon.setOnCheckedChangeListener { _, checkedId ->
            val selectedResId = imageSet[checkedId]
            selectedResId?.let {
                iv_image.setImageResource(it)
                //map에 string, string 으로 담아야 해서 이렇게 작성함
                //intent로 넘긴 뒤에는
                //val prevImage = it.data?.getStringExtra("userImage")?.toInt()
                //image.setImageResource(prevImage!!)
                //로 사용하기
                im_resString = selectedResId.toString()
            }
            isImageOk = true
        }

        //아이디가 5자리 이상인지, 중복되지 않는지 확인
        btn_checkId.setOnClickListener {
            newId = et_userId.text.toString()
            checkId(newId)
        }

        //보안문자 생성
        generateAuthent()

        //새로운 보안문자 생성하는 버튼
        btn_createAuthent.setOnClickListener {
            generateAuthent()
            btn_checkAuthent.text = getString(R.string.btn_checkAuthent)
            btn_checkAuthent.visibility = View.VISIBLE
        }

        //tts 관련 코드
        textToSpeech = TextToSpeech(this, this)

        //tts 재생 버튼
        btn_playTts.setOnClickListener {
            readAuthent(generatedAuthent)
        }

        //보안문자 확인 버튼
        btn_checkAuthent.setOnClickListener {
            newAuthent = et_userAuthent.text.toString()
            if (generatedAuthent == newAuthent) {
                isAuthentOk = true
                Toast.makeText(this, getString(R.string.authent_correct), Toast.LENGTH_SHORT).show()
                //토스트를 띄운 뒤, 버튼을 없앰
                btn_checkAuthent.text = getString(R.string.authent_correct)
                btn_checkAuthent.visibility = View.GONE
            } else {
                isAuthentOk = false
                Toast.makeText(this, getString(R.string.authent_not_correct), Toast.LENGTH_SHORT).show()
                //틀렸으니 새로운 보안문자 자동 생성
                generateAuthent()
            }
        }

        //회원가입 버튼 누른 경우
        btn_join.setOnClickListener {
            newName = et_userName.text.toString()
            newId = et_userId.text.toString()
            newPw = et_userPw.text.toString()
            checkPw = et_checkPw.text.toString()
            newEmail = et_userEmail.text.toString()
            newAuthent = et_userAuthent.text.toString()
            checkAndReg(newName, newId, newPw, checkPw, newEmail, newAuthent)
        }
    }

    //tts 관련 코드
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, getString(R.string.lang_not_supported), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.init_failed), Toast.LENGTH_SHORT).show()
        }
    }

    //보안문자열을 문자 하나씩 읽도록
    private fun readAuthent(generatedAuthent: String) {
        for (char in generatedAuthent) {
            textToSpeech.speak(char.toString(), TextToSpeech.QUEUE_ADD, null, null)
            //중간에 텀 두고 읽도록
            Thread.sleep(100)
        }
    }

    fun checkId(newId: String) {
        if (idList.isNotEmpty()) {
            if (idList.contains(newId)) {
                isIdOk = false
            } else {
                isIdOk = true
            }
        } else {
            isIdOk = true
        }
        //5자리 이상인지도 확인
        if (isIdOk && strongID(newId)) {
            Toast.makeText(this, getText(R.string.available_id), Toast.LENGTH_SHORT).show()
        } else {
            if (!isIdOk) {
                Toast.makeText(this, getText(R.string.nonavailable_id), Toast.LENGTH_SHORT).show()
            } else if (!strongID(newId)) {
                Toast.makeText(this, getText(R.string.id_not_strong), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //보안문자 생성, generatedAuthent에 저장, 기존에 사용자가 입력한 보안문자는 삭제
    private fun generateAuthent() {
        generatedAuthent = generateRandom()
        tv_authent.text = generatedAuthent
        et_userAuthent.text.clear()
    }

    //랜덤 문자 생성
    private fun generateRandom(): String {
        val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val length = 6
        return (1..length).map { characters.random() }.joinToString("")
    }

    //이메일 형식 확인
    private fun checkEmail(newEmail: String): Boolean {
        val emailSample = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"

        return if (!newEmail.matches(emailSample.toRegex())) {
            false
        } else {
            true
        }
    }

    //아이디가 5자리 이상인지
    private fun strongID(newId: String): Boolean {
        if (newId.length >= 5) {
            return true
        } else {
            return false
        }
    }

    //비밀번호 강도 체크
    private fun checkPW(newPw: String):Boolean{
        val hasUpperCase = newPw.any{ it.isUpperCase()}
        val hasLowerCase = newPw.any{ it.isLowerCase()}
        val hasNum = newPw.any{it.isDigit()}
        val hasSpeChar = newPw.any{!it.isLetterOrDigit()}
        val isLengthOk = newPw.length>=8

        return hasUpperCase && hasLowerCase && hasNum && hasSpeChar && isLengthOk
    }

    //내용들이 올바르게 입력되었는지 확인 후 데이터 저장
    private fun checkAndReg(
        newName: String,
        newId: String,
        newPw: String,
        checkPw: String,
        newEmail: String,
        newAuthent: String
    ) {
        if (!isImageOk || newName.isEmpty() || newId.isEmpty() || newPw.isEmpty() || checkPw.isEmpty() || newEmail.isEmpty() || newAuthent.isEmpty()) {
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
        if(!checkPW(newPw)){
            Toast.makeText(this, getString(R.string.pw_not_strong), Toast.LENGTH_SHORT).show()
            return
        }
        if (!isIdOk) {
            Toast.makeText(this, getString(R.string.id_not_available), Toast.LENGTH_SHORT).show()
            return
        }
        if (!checkEmail(newEmail)) {
            Toast.makeText(this, getString(R.string.email_not_correct), Toast.LENGTH_SHORT).show()
            return
        }
        if (!isAuthentOk) {
            Toast.makeText(this, getString(R.string.authent_check_required), Toast.LENGTH_SHORT).show()
            return
        }

        //데이터 추가
        val objUserInfo: MutableMap<String, String> = mutableMapOf()
        objUserInfo["image"] = im_resString
        objUserInfo["name"] = newName
        objUserInfo["id"] = newId
        objUserInfo["pw"] = newPw
        objUserInfo["email"] = newEmail
        UserDataList.userDataList.add(objUserInfo)

        // Member 객체 추가 및 UserData풀에 저장
        var newMember = Member(objUserInfo["id"]!!, objUserInfo["pw"]!!, objUserInfo["name"]!!, objUserInfo["image"]!!.toInt(),objUserInfo["email"]!!)
        UserdataPull.add(newMember)
        Log.d("데이터 인풋 확인", "${UserdataPull}")
        //프로필과 아이디, 비밀번호 자동완성되게 전달
        val returnIntent = Intent(this, SignInActivity::class.java)
        returnIntent.putExtra("userImage",im_resString)
        //string, string으로 map을 만들어서 toString 한 상태니까
        // intent로 넘긴 뒤에는
        //val prevImage = it.data?.getStringExtra("userImage")?.toInt()
        //image.setImageResource(prevImage!!)
        //처럼 int로 바꾼 뒤에 사용하기
        returnIntent.putExtra("userID", newId)
        returnIntent.putExtra("userPW", newPw)
        setResult(RESULT_OK, returnIntent)

        if (!isFinishing) finish()
        //
    }


    //tts 관련 코드입니다.
    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }
}