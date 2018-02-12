package com.ipoondev.android.psugo.model


class Mission {
    var id: Int = 0
    var title: String? = null
    var image: String? = null
    var teacher: String? = null
    var subject: String? = null
    var state: String? = null
    var items: List<Item>? = emptyList()

    constructor() {}

    constructor(id: Int, title: String, image: String, teacher: String, subject: String, state: String, items: List<Item>) {
        this.id = id
        this.title = title
        this.image = image
        this.teacher = teacher
        this.subject = subject
        this.state = state
        this.items = items
    }
}

