package com.example.spartateamproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var id : TextView
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var post: TextView
    lateinit var data : Member
    lateinit var setting: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val detail_id = intent.getStringExtra("id")
        data = Member("id", "password", "name", 0, "email")
        UserdataPull.forEach {
            if(it._id == detail_id){
                data = it
            }
        }
        init()
        isSettingActivate()
        // 기본 이메일 앱 연결
        val email_link = findViewById<TextView>(R.id.tx_detail_email)
        email_link.movementMethod = LinkMovementMethod.getInstance()

        // 블로그 링크 연결
        val blog_link = findViewById<TextView>(R.id.tx_detail_morepost)
        blog_link.setOnClickListener {
            val url = "https://nochfm0513.tistory.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val btn_back = findViewById<ImageButton>(R.id.btn_detail_back)
        btn_back.setOnClickListener {
            finish()
        }
    }
    private fun init(){
        id = findViewById(R.id.tx_detail_id)
        name = findViewById(R.id.tx_detail_name)
        email = findViewById(R.id.tx_detail_email)
        post = findViewById(R.id.tx_detail_post)
        setting = findViewById(R.id.btn_detail_setting)
        setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            intent.putExtra("id", currentLoginUser._id)
            startActivity(intent)
        }
        id.text = data._id
        name.text = data._name
        email.text = data._email

        if(data.postList.size < 1){
            post.text = "게시글 없음"
        }
        else
            post.text = data.postList[2]._txt
    }
    private fun isSettingActivate(){
       if (data._id == currentLoginUser._id)
           setting.visibility = View.VISIBLE
        else
            setting.visibility = View.GONE
    }
}
