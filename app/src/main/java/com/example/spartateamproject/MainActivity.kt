package com.example.spartateamproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


// 포스트 안에 임의로 넣을 텍스트 뭉텅이들
val dummyText = mutableListOf<String>()
// 포스트를 담을 컨테이너 뷰 안에 넣을 포스트 리스트
val totalpostList = mutableListOf<Post>()
var iconList = mutableListOf<ImageView>()
var iconCardViewList = mutableListOf<MaterialCardView>()
var isCheck = false
lateinit var currentLoginUser : Member

class MainActivity : AppCompatActivity() {
    lateinit var post : LinearLayout
    lateinit var MyImg : ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(isCheck == false) {
            initDataSetting()
            currentUser()
            makeComment()
            isCheck = true
        }
        init()
        //메인 화면에 뜨는 게시글 리스트 출력을 위한 addView 관련 코드 입니다
            totalpostList.forEach {
                val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_post, null)
                val itemText = item.findViewById<TextView>(R.id.tx_postText)
                val itemImg = item.findViewById<ImageView>(R.id.img_postImg)
                val itemId = item.findViewById<TextView>(R.id.tx_postID)
                val openComment = item.findViewById<TextView>(R.id.tx_open_comment)
                val commentContainer =
                    item.findViewById<LinearLayout>(R.id.linear_comment_container)
                val editComment = item.findViewById<EditText>(R.id.edit_comment)
                val heart = item.findViewById<ImageView>(R.id.img_heart)
                val like = item.findViewById<TextView>(R.id.tx_like)
                val commentInputButton = item.findViewById<ImageView>(R.id.img_commentInput)
                var numberOfLike = it.like
                like.text = "좋아요 ${numberOfLike}개"
                addCommentToContainer(commentContainer, it.commentList)
                itemText.text = it._txt
                itemId.text = it._id
                itemImg.setImageResource(it.img)
                openComment.setOnClickListener {
                    commentContainer.visibility = View.VISIBLE
                    openComment.visibility = View.GONE
                    editComment.visibility = View.VISIBLE
                    commentInputButton.visibility = View.VISIBLE
                }
                addComment(commentInputButton, commentContainer, it.commentList, editComment)
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
        MyImg = findViewById(R.id.img_main_loginImg)
        MyImg.setImageResource(currentLoginUser._img)
        iconList.add(findViewById(R.id.img_main_member1))
        iconList.add(findViewById(R.id.img_main_member2))
        iconList.add(findViewById(R.id.img_main_member3))
        iconList.add(findViewById(R.id.img_main_member4))
        for (i in 0..3) {
            iconList[i].setImageResource(UserdataPull[i]._img)
        }
        iconCardViewList.add(findViewById(R.id.card_main_member1))
        iconCardViewList.add(findViewById(R.id.card_main_member2))
        iconCardViewList.add(findViewById(R.id.card_main_member3))
        iconCardViewList.add(findViewById(R.id.card_main_member4))
        for(i in 0..3){
            iconCardViewList[i].setOnClickListener {
                iconCardViewList[i].setStrokeColor(Color.rgb(0,0,0))
                val intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
                intent.putExtra("key", i)
                startActivity(intent)
            }
        }
        MyImg.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", currentLoginUser._id)
            startActivity(intent)
        }
    }
    private fun initDataSetting(){
        //더미 데이터 테스트용입니다. 테스트 출력 용도
        for (i in 0..11){
            dummyText.add("${i+1}번째 테스트용 TIL 입니다")
        }
        for(i in 0.. 11){
            if(i in 0..2){
                UserdataPull[0].addPost(Post(UserdataPull[0], dummyText[i]))
            }
            else if(i in 3..5){
                UserdataPull[1].addPost(Post(UserdataPull[1], dummyText[i]))
            }
            else if(i in 6..8){
                UserdataPull[2].addPost(Post(UserdataPull[2], dummyText[i]))
            }
            else if(i in 9..11){
                UserdataPull[3].addPost(Post(UserdataPull[3], dummyText[i]))
            }
        }
        for(i in 0.. UserdataPull.size-1){
            UserdataPull[i].postList.forEach {
                totalpostList.add(it)
            }
        }

    }

    //댓글 컨테이너 관련 코드, 댓글은 게시글 당 한 줄씩 더미 데이터를 삽입했음
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
    //댓글 삽입
    private fun addComment(btn: ImageView, container : LinearLayout, commentList: MutableList<Comment>, edit: EditText){
        btn.setOnClickListener {
            var newComment = Comment(currentLoginUser, edit.text.toString())
            val mComment = LayoutInflater.from(this).inflate(R.layout.item_comment, null)
            val text = mComment.findViewById<TextView>(R.id.tx_commentText)
            val id = mComment.findViewById<TextView>(R.id.tx_commentID)
            val img = mComment.findViewById<ImageView>(R.id.img_inputIcon)
            text.text = edit.text
            id.text = currentLoginUser._id
            img.setImageResource(currentLoginUser._img)
            commentList.add(newComment)
            container.addView(mComment)
            edit.text.clear()
        }
    }
    //더미데이터 중 댓글 생성하기
    private fun makeComment(){
        for(i in 0.. UserdataPull[0].postList.size-1){
            UserdataPull[0].postList[i].addComment(Comment(UserdataPull[3], "잘 보고 갑니다~"))
        }
        for(i in 0.. UserdataPull[1].postList.size-1){
            UserdataPull[1].postList[i].addComment(Comment(UserdataPull[0], "멋지네요!"))
        }
        for(i in 0.. UserdataPull[2].postList.size-1){
            UserdataPull[2].postList[i].addComment(Comment(UserdataPull[1], "좋은 글입니다 :)"))
        }
        for(i in 0.. UserdataPull[3].postList.size-1){
            UserdataPull[3].postList[i].addComment(Comment(UserdataPull[2], "도움이 많이 되었어요!"))
        }
    }

    //로그인 하면서 받아온 id를 통해 현재 로그인된 아이디 저장해두는 메소드
    private fun currentUser() {
        val id = intent.getStringExtra("id")
        var member = Member("id", "password", "name", 0, "email")
        for(i in 0..UserdataPull.size-1){
            if(id == UserdataPull[i]._id){
                member = UserdataPull[i]
                break
            }
        }
        currentLoginUser = member
    }

}