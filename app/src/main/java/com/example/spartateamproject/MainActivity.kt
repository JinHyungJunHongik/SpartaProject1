package com.example.spartateamproject

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// 포스트 안에 임의로 넣을 텍스트 뭉텅이들
val dummyText = mutableListOf<String>()
// 포스트를 담을 컨테이너 뷰 안에 넣을 포스트 리스트
val totalpostList = mutableListOf<Post>()
var isCheck = false
var postCheck = false
var currentLoginUser = Member("id", "password", "name", 0, "email")

class MainActivity : AppCompatActivity() {
    lateinit var post : LinearLayout
    lateinit var welcomeText: TextView
    lateinit var MyImg : ImageView
    lateinit var icon1 : ImageView
    lateinit var icon2 : ImageView
    lateinit var icon3 : ImageView
    lateinit var icon4 : ImageView
    lateinit var card1 : MaterialCardView
    lateinit var card2 : MaterialCardView
    lateinit var card3 : MaterialCardView
    lateinit var card4 : MaterialCardView

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isCheck == false) {
            initDataSetting()
            currentUser()
            makeComment()
            isCheck = true
        }
        init()
        //메인 화면에 뜨는 게시글 리스트 출력을 위한 addView 관련 코드 입니다
    }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun init() {
        Log.d("초기설정", "초기 설정 완료")
        post = findViewById(R.id.linear_mainPost)
        MyImg = findViewById(R.id.img_main_loginImg)
        welcomeText = findViewById(R.id.tv_welcomeText)
        icon1 = findViewById(R.id.img_main_member1)
        icon2 = findViewById(R.id.img_main_member2)
        icon3 = findViewById(R.id.img_main_member3)
        icon4 = findViewById(R.id.img_main_member4)
        card1 = findViewById(R.id.card_main_member1)
        card2 = findViewById(R.id.card_main_member2)
        card3 = findViewById(R.id.card_main_member3)
        card4 = findViewById(R.id.card_main_member4)
        MyImg.setImageResource(currentLoginUser._img)
        icon1.setImageResource(UserdataPull[0]._img)
        icon2.setImageResource(UserdataPull[1]._img)
        icon3.setImageResource(UserdataPull[2]._img)
        icon4.setImageResource(UserdataPull[3]._img)
        // 스토리라인 클릭 시에는 메인 액티비티 finish처리 안함, 스토리라인에서 5초 후 다시 돌아오기 때문에
        icon1.setOnClickListener {
            card1.setStrokeColor(Color.rgb(0,0,0))
            val intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
            intent.putExtra("key", 0)
            startActivity(intent)
        }
        icon2.setOnClickListener {
            card2.setStrokeColor(Color.rgb(0,0,0))
            val intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
            intent.putExtra("key", 1)
            startActivity(intent)
        }
        icon3.setOnClickListener {
            card3.setStrokeColor(Color.rgb(0,0,0))
            val intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
            intent.putExtra("key", 2)
            startActivity(intent)
        }
        icon4.setOnClickListener {
            card4.setStrokeColor(Color.rgb(0,0,0))
            val intent = Intent(this@MainActivity, StoryDetailActivity::class.java)
            intent.putExtra("key", 3)
            startActivity(intent)
        }

        MyImg.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", currentLoginUser._id)
            startActivity(intent)
            overridePendingTransition(R.anim.login_to_main,R.anim.login_to_main_none)
            finish()
        }
        welcomeText.text = "${currentLoginUser._name}님 환영합니다!"
        totalpostList.forEach {
            if (it != null) {
                val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_post, null)
                val itemText = item.findViewById<TextView>(R.id.tx_postText)
                val itemViewMore = item.findViewById<TextView>(R.id.tv_post_view_more)
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
                postIconClick(itemImg, it)
                openComment.setOnClickListener {
                    commentContainer.visibility = View.VISIBLE
                    openComment.visibility = View.GONE
                    editComment.visibility = View.VISIBLE
                    commentInputButton.visibility = View.VISIBLE
                }
                postViewMore(itemText, itemViewMore)
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
    }
    private fun initDataSetting(){
        Log.d("초기데이터설정", "초기 데이터 설정 완료")
        //더미 데이터 테스트용입니다. 테스트 출력 용도
        val text1 = getText(R.string.TIL1).toString()
        val text2 = getText(R.string.TIL2).toString()
        val text3 = getText(R.string.TIL3).toString()
        val text4 = getText(R.string.TIL4).toString()
        dummyText.add(text1)
        dummyText.add(text2)
        dummyText.add(text3)
        dummyText.add(text4)
        UserdataPull[0].addPost(Post(UserdataPull[0], dummyText[0]))
        UserdataPull[1].addPost(Post(UserdataPull[1], dummyText[1]))
        UserdataPull[2].addPost(Post(UserdataPull[2], dummyText[2]))
        UserdataPull[3].addPost(Post(UserdataPull[3], dummyText[3]))
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
          commentIconClick(img, it._id)
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
            commentIconClick(img, currentLoginUser._id)
            commentList.add(newComment)
            container.addView(mComment)
            edit.text.clear()
        }
    }
    //더미데이터 중 댓글 생성하기
    private fun makeComment(){
        Log.d("초기설정", "댓글 더미 설정 완료")
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
        Log.d("초기설정", "현재 로그인 유저 등록 완료")
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
    private fun postIconClick(img: ImageView, post: Post){

        img.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", post._id)
            startActivity(intent)
            finish()
        }
    }
    private fun commentIconClick(img: ImageView, id: String){
        img.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }
    }
    // 포스트 별 더보기 버튼 메소드, 현재 포스트의 maxLine = 6이며,
    // 한 화면에 담을 수 없는 문자 수가 0보다 클 경우(post.layout.getEllipsisCount(lineNumber-1) > 0)
    // 더보기 텍스트 버튼이 활성화 되며, 누르면 maxLine = 6에서 최대치로 늘고, 더보기 버튼이 gone 처리
    //
    private fun postViewMore(post: TextView, viewMore: TextView) {
            post.post {
                val lineNumber = post.layout.lineCount
                if (lineNumber > 0) {
                    if (post.layout.getEllipsisCount(lineNumber - 1) > 0) {
                        viewMore.visibility = View.VISIBLE
                        viewMore.setOnClickListener {
                            post.maxLines = Int.MAX_VALUE
                            viewMore.visibility = View.GONE
                        }
                    }
                }
            }
        }
}