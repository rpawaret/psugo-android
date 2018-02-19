package com.ipoondev.android.psugo.model

class Quiz {

    var question : String? = null
    var options : List<String>? = emptyList<String>()
    var answer : String? = null
    var point : Int = 0

    constructor() {}

    constructor(question: String?, options: List<String>?, answer: String?, point: Int) {
        this.question = question
        this.options = options
        this.answer = answer
        this.point = point
    }
}