package com.example.spartateamproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val data = mutableListOf<String>(
)
class MainActivity : AppCompatActivity() {
    lateinit var post : LinearLayout
    var iconList = mutableListOf<MaterialCardView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        data.forEach {
            val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_post, null)
            val itemText = item.findViewById<TextView>(R.id.tx_postText)
            itemText.text = it
            post.addView(item)
        }

    }
    private fun init() {
        post = findViewById(R.id.linear_mainPost)
        var num: Int = 9
        for(i in 0..8){
            data.add("TIL 작성 ${i+1}")
        }
        iconList.add(findViewById(R.id.card_main_member1))
        iconList.add(findViewById(R.id.card_main_member2))
        iconList.add(findViewById(R.id.card_main_member3))
        iconList.add(findViewById(R.id.card_main_member4))
        for(i in 0..iconList.size-1){
            // 클래스가 없으므로 인텐트 이동만
            iconList[i].setOnClickListener {
                iconList[i].setStrokeColor(Color.rgb(0,0,0))
                val intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}