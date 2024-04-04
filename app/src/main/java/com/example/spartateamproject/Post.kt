package com.example.spartateamproject

// 메인 화면 출력용으로 만든 포스트 클래스
class Post(val id : Member, val txt : String) {
    var _id : String = ""
    var _txt : String = ""
    var img : Int = 0
    var like : Int = 0
    //각 포스트에 달린 댓글 리스트들
    var commentList = mutableListOf<Comment>()

    init{
        _id = id._id
        _txt = txt
        img = id._img
    }
    //해당 포스트에 댓글 추가하는 메소드
    fun addComment(comment: Comment){
        this.commentList.add(comment)
    }
}