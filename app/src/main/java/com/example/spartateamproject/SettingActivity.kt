package com.example.spartateamproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate


class SettingActivity : AppCompatActivity() {

        lateinit var mpId: TextView
        lateinit var mpName: TextView
        lateinit var mpEmail: TextView
        lateinit var mpImage: ImageView
        lateinit var postCount: TextView
        //EditProfileActivity 에서 넘어온 정보를 받음
        private var resultLanuncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {


                val etnName =result.data?.getStringExtra("etName")
                val etnEmail =result.data?.getStringExtra("etEmail")


                mpName.setText(etnName)
                mpEmail.setText(etnEmail)

            }
        }

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_setting)

            //다크모드 스위치
            val switchDark = findViewById<Switch>(R.id.switch_dark_mode)
            switchDark.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                }
            }



            //intent로 "name"에 해당하는 value를 받아온 경우
            val idIntent = intent.getStringExtra("id")!!

//            objSearch(idIntent)


            //SignupActivity에서 유저정보 받아서 SettingActivity에 계정(ID), 이름, Email 표시
            mpId = findViewById(R.id.tv_mp_user_id)
            mpName = findViewById(R.id.tv_mp_user_name)
            mpEmail = findViewById(R.id.tv_mp_user_email)
            mpImage = findViewById(R.id.img_profile)
            postCount = findViewById(R.id.tv_postCount_number)

            myPageBack()
            editProfile()
            objSearch(idIntent)
            countingPost()

        }


        private fun objSearch(idIntent: String) { //유저 정보 찾아서 셋팅하기

            // (이전과 동일하게 진행) "name"의 값이 "sample"인 맵을 찾은 다음에
            val targetMap = UserDataList.userDataList.firstOrNull { it["id"] == idIntent }

            //해당하는 맵을 찾았을 경우에 다른 키 값들을 가져와 변수에 저장하고
            if (targetMap != null) {
                val icon = targetMap["image"]?.toInt()
                val id = targetMap["id"]
                val pw = targetMap["pw"]
                val email = targetMap["email"]
                val name = targetMap["name"]
                mpImage.setImageResource(icon!!)
                mpId.setText(id)
                mpName.setText(name)
                mpEmail.setText(email)


            } else { //여긴 못찾은경우
                println("User with name 'sample' not found")
            }
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
                intent.putExtra("mpId",mpId.text.toString())
                intent.putExtra("mpImage",mpImage.toString())
                resultLanuncher.launch(intent)

            }
        }
        private fun countingPost(){
            var numberOfPost = currentLoginUser.postList.size
            postCount.text = numberOfPost.toString()
        }
    }
