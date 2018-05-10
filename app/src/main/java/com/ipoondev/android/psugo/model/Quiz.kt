package com.ipoondev.android.psugo.model

class Quiz {

    var question : String? = null
    var opt_1: String? = null
    var opt_2: String? = null
    var opt_3: String? = null
    var opt_4: String? = null
    var answer : String? = null

    constructor() {}

    constructor(question: String?, opt_1: String?, opt_2: String?, opt_3: String?, opt_4: String?, answer: String?) {
        this.question = question
        this.opt_1 = opt_1
        this.opt_2 = opt_2
        this.opt_3 = opt_3
        this.opt_4 = opt_4
        this.answer = answer
    }


}