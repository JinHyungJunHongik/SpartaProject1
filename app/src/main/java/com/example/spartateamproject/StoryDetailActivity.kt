package com.example.spartateamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StoryDetailActivity : AppCompatActivity() {
    lateinit var progress: ProgressBar
    lateinit var icon: MaterialCardView
    lateinit var img: ImageView
    lateinit var post: TextView
    lateinit var id: TextView
    lateinit var storyData: Member
    var iconClick: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
        var key = intent.getIntExtra("key",0)
        storyData = UserdataPull[key]
        init()
        storyLine()
    }

    private fun init() {
        progress = findViewById(R.id.progress)
        icon = findViewById(R.id.card_storyIcon)
        img = findViewById(R.id.img_storyPostImg)
        post = findViewById(R.id.tx_storyPost)
        id = findViewById(R.id.tx_storyID)
        id.text = storyData._id
        img.setImageResource(storyData._img)
        post.text = storyData.postList[2]._txt
        icon.setOnClickListener {
            iconClick = true
            val intent = Intent(this@StoryDetailActivity, DetailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun storyLine() {
        var time: Int = 0
        var value: Int = 0
        lifecycleScope.launch {
            whenStarted {
                while(time <= 5){
                    if(iconClick == true){
                        break
                    }
                    time++
                    delay(1000)
                    if(time == 5){
                        val intent = Intent(this@StoryDetailActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        lifecycleScope.launch {
            whenStarted {
                while(value != 10000){
                    delay(50)
                    value += 1
                    progress.progress = value
                }
            }
        }
    }
}