package com.example.spartateamproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    lateinit var img: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var mbti: TextView
    private lateinit var personality: TextView
    private lateinit var tmi: TextView
    private lateinit var morepost: TextView
    private lateinit var url: String

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

        val email_link = findViewById<TextView>(R.id.tx_detail_email)
        email_link.movementMethod = LinkMovementMethod.getInstance()

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
        img = findViewById(R.id.img_detail_profile)
        mbti = findViewById(R.id.tx_detail_MBTI)
        personality = findViewById(R.id.tx_detail_personality)
        tmi = findViewById(R.id.tx_detail_TMI)
        morepost = findViewById(R.id.tx_detail_morepost)
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

        if(data._name == "태준") {
            mbti.text = getText(R.string.mbti1)
            personality.text = getText(R.string.personality1)
            tmi.text = getText(R.string.tmi1)
            img.setImageResource(R.drawable.img_04)
            morepost.setOnClickListener {
                url = getText(R.string.link1).toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        if(data._name == "형준") {
            mbti.text = getText(R.string.mbti2)
            personality.text = getText(R.string.personality2)
            tmi.text = getText(R.string.tmi2)
            img.setImageResource(R.drawable.img_01)
            morepost.setOnClickListener {
                url = getText(R.string.link2).toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        if(data._name == "지원") {
            mbti.text = getText(R.string.mbti3)
            personality.text = getText(R.string.personality3)
            tmi.text = getText(R.string.tmi3)
            img.setImageResource(R.drawable.img_02)
            morepost.setOnClickListener {
                url = getText(R.string.link3).toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        if(data._name == "연수") {
            mbti.text = getText(R.string.mbti4)
            personality.text = getText(R.string.personality4)
            tmi.text = getText(R.string.tmi4)
            img.setImageResource(R.drawable.img_03)
            morepost.setOnClickListener {
                url = getText(R.string.link4).toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

    }
    private fun isSettingActivate(){
       if (data._id == currentLoginUser._id)
           setting.visibility = View.VISIBLE
        else
            setting.visibility = View.GONE
    }
}
