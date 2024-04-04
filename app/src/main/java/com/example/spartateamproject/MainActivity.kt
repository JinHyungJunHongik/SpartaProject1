package com.example.spartateamproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val data = mutableListOf<Member>(
    Member("dummy1", "qwe12345", "형준", R.drawable.man_icon1),
    Member("dummy2", "qwe12345", "지원", R.drawable.man_icon2),
    Member("dummy3", "qwe12345", "연수", R.drawable.man_icon3),
    Member("dummy4", "qwe12345", "태준", R.drawable.man_icon4),
)
val dummyText = mutableListOf<String>()
val postList = mutableListOf<Post>()
class MainActivity : AppCompatActivity() {
    lateinit var post : LinearLayout
    var iconList = mutableListOf<MaterialCardView>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        makeComment()
        //메인 화면에 뜨는 게시글 리스트 출력을 위한 addView 관련 코드 입니다
        postList.forEach {
            val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_post, null)
            val itemText = item.findViewById<TextView>(R.id.tx_postText)
            val itemImg = item.findViewById<ImageView>(R.id.img_postImg)
            val itemId = item.findViewById<TextView>(R.id.tx_postID)
            val openComment = item.findViewById<TextView>(R.id.tx_open_comment)
            val commentContainer = item.findViewById<LinearLayout>(R.id.linear_comment_container)
//            val editComment = item.findViewById<EditText>(R.id.edit_inputcomment)
            val heart = item.findViewById<ImageView>(R.id.img_heart)
            val like = item.findViewById<TextView>(R.id.tx_like)
            var numberOfLike = it.like
            like.text = "좋아요 ${numberOfLike}개"
            addCommentToContainer(commentContainer, it.commentList)
            itemText.text = it._txt
            itemId.text = it._id
            itemImg.setImageResource(it.img)
            openComment.setOnClickListener {
                commentContainer.visibility = View.VISIBLE
//                editComment.visibility = View.VISIBLE
                openComment.visibility = View.GONE
            }
            heart.setOnClickListener {
                heart.setImageResource(R.drawable.icon_colorheart)
                numberOfLike++
                like.text = "좋아요 ${numberOfLike}개"
            }
            it.like = numberOfLike
            post.addView(item)
        }

    }
    private fun init() {
        post = findViewById(R.id.linear_mainPost)
        iconList.add(findViewById(R.id.card_main_member1))
        iconList.add(findViewById(R.id.card_main_member2))
        iconList.add(findViewById(R.id.card_main_member3))
        iconList.add(findViewById(R.id.card_main_member4))

        //더미 데이터 테스트용입니다. 테스트 출력 용도
        for (i in 0..11){
            dummyText.add("${i+1}번째 테스트용 TIL 입니다")
        }
        for(i in 0.. 11){
            if(i in 0..2){
                data[0].addPost(Post(data[0], dummyText[i]))
            }
            else if(i in 3..5){
                data[1].addPost(Post(data[1], dummyText[i]))
            }
            else if(i in 6..8){
                data[2].addPost(Post(data[2], dummyText[i]))
            }
            else if(i in 9..11){
                data[3].addPost(Post(data[3], dummyText[i]))
            }
        }
        for(i in 0.. data.size-1){
            data[i].postList.forEach {
                postList.add(it)
            }
        }

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

    //댓글란 만들어보기중..
    private fun addCommentToContainer(container : LinearLayout, commentList: MutableList<Comment>){
        commentList.forEach {
          val mComment = LayoutInflater.from(this).inflate(R.layout.item_comment, null)
          val text = mComment.findViewById<TextView>(R.id.tx_commentText)
          val id = mComment.findViewById<TextView>(R.id.tx_commentID)
          val img = mComment.findViewById<ImageView>(R.id.img_inputIcon)
          text.text = it._txt
          id.text = it._id
          img.setImageResource(it._img)
          container.addView(mComment)
        }
    }
    //더미데이터 중 댓글 생성하기
    private fun makeComment(){
        for(i in 0.. data[0].postList.size-1){
            data[0].postList[i].addComment(Comment(data[3], "잘 보고 갑니다~"))
        }
        for(i in 0.. data[1].postList.size-1){
            data[1].postList[i].addComment(Comment(data[0], "멋지네요!"))
        }
        for(i in 0.. data[2].postList.size-1){
            data[2].postList[i].addComment(Comment(data[1], "좋은 글입니다 :)"))
        }
        for(i in 0.. data[3].postList.size-1){
            data[3].postList[i].addComment(Comment(data[2], "도움이 많이 되었어요!"))
        }
    }

}