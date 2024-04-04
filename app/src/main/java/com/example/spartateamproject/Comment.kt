package com.example.spartateamproject

import org.w3c.dom.Text
//메인에 출력할 용도로 만든 댓글 클래스, 댓글 작성자의 멤버 데이터를 받아 댓글에 아이디와 아이콘을 노출시킴
class Comment(val id: Member, val text: String) {
    var _txt : String = ""
    var _id : String = ""
    var _img : Int = 0
    init{
        _txt = text
        _id = id._id
        _img = id._img
    }
}