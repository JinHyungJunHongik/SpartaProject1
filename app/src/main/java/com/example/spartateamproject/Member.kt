package com.example.spartateamproject

//메인 페이지 출력을 위해 만든 멤버 클래스
class Member(val id: String, val password: String, val name: String, val img: Int, val email: String) {
    var _id : String = ""
    var _password : String = ""
    var _name : String = ""
    // 멤버 별 작성한 글을 저장하는 리스트
    var postList : MutableList<Post> = mutableListOf()
    var _img : Int = 0
    var _email : String = ""

    init{
        _id = id
        _password = password
        _name = name
        _img = img
        _email = email
    }
    //멤머 별 작성 글 리스트에 저장하는 메소드
    fun addPost(post : Post){
        this.postList.add(post)
    }

}