package com.ipoondev.android.psugo.model


class Mission {
    var name: String? = null
    var statement: String? = null
    var categories: String? = null
    var selectedItems: List<String>? = null

    constructor() {}

    constructor(name: String?, statement: String?, categories: String?, selectedItems: ArrayList<String>?) {
        this.name = name
        this.statement = statement
        this.categories = categories
        this.selectedItems = selectedItems
    }

}

