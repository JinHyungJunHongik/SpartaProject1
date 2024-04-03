package com.example.spartateamproject

class Member(val id: String, val password: String, val name: String, val img: Int) {
    var _id : String = ""
    var _password : String = ""
    var _name : String = ""
    var postList : MutableList<Post> = mutableListOf()
    var _img : Int = 0

    init{
        _id = id
        _password = password
        _name = name
        _img = img
    }

    fun addPost(post : Post){
        this.postList.add(post)
    }

}