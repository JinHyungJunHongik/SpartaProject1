package com.example.spartateamproject

class Post(val id : Member, val txt : String) {
    var _id : String = ""
    var _txt : String = ""
    var img : Int = 0
    var like : Int = 0
    var commentList = mutableListOf<Comment>()

    init{
        _id = id._id
        _txt = txt
        img = id._img
    }

    fun addComment(comment: Comment){
        this.commentList.add(comment)
    }
}