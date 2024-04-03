package com.example.spartateamproject

import org.w3c.dom.Text

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