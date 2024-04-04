package com.example.spartateamproject


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etImage = intent.getStringExtra("mpImage")
        val etId = intent.getStringExtra("mpId")
        val tv_dtId = findViewById<TextView>(R.id.tv_ep_user_id)

        if (tv_dtId.text.isBlank()) {
            tv_dtId.text = "$etId"
        }

        val etImageView = findViewById<ImageView>(R.id.img_profile)
        if (etImage != null) {
            etImageView.setImageURI(Uri.parse(etImage))
        }



        val editName = findViewById<EditText>(R.id.et_ep_name)
        val editEmail = findViewById<EditText>(R.id.et_ep_email)
        val btnSave = findViewById<AppCompatButton>(R.id.tv_ep_save)




        btnSave.setOnClickListener {

            when{
                editName.text.isEmpty() -> {
                    Toast.makeText(this, "이름을 작성해주세요", Toast.LENGTH_SHORT).show()
                }
                editEmail.text.isEmpty() -> {
                    Toast.makeText(this, "이메일을 작성해주세요", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    intent.putExtra("etName", editName.text.toString())
                    intent.putExtra("etEmail", editEmail.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }

        }


        editProfileBack()


    }

    private fun editProfileBack() { // 뒤로가기
        val myPageBackButton = findViewById<ImageButton>(R.id.imgBtn_etProfileBack)

        myPageBackButton.setOnClickListener {
            finish()
        }
    }








}