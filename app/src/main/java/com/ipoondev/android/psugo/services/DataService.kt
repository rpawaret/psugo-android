package com.ipoondev.android.psugo.services

import com.google.firebase.firestore.GeoPoint
import com.ipoondev.android.psugo.model.Item
import com.ipoondev.android.psugo.model.Lesson

object DataService {

//    val categories = listOf(
//            Category("SHIRT", "shirtimage"),
//            Category("HOODIES", "hoodieimage"),
//            Category("HATS", "hatimage"),
//            Category("DIGITAL", "digitalgoodsimage")
//    )
//
//    val hats = listOf(
//            Product("Devslopes Graphic Beanie", "$18", "hat1"),
//            Product("Devslopes Hat Black", "$20", "hat2"),
//            Product("Devslopes Hat White", "$28", "hat3"),
//            Product("Devslopes Hat Snapback", "$18", "hat4")
//    )
//
//    val hoodies = listOf(
//            Product("Devslopes Hoodie Gray", "$28", "hoodie1"),
//            Product("Devslopes Hoodie Red", "$32", "hoodie2"),
//            Product("Devslopes Gray Hoodie", "$28", "hoodie3"),
//            Product("Devslopes Black Hoodie", "$32", "hoodie4")
//    )
//
//    val shirts = listOf(
//            Product("Devslopes Shirt Gray", "$28", "shirt1"),
//            Product("Devslopes Badge Light Gray", "$20", "shirt2"),
//            Product("Devslopes Logo Shirt Red", "$22", "shirt3"),
//            Product("Devslopes Hustle", "$22", "shirt4"),
//            Product("Kickflip Studios", "$18", "shirt5")
//
//    )

    val items1 = listOf(
            Item(1, "Item 1", "My Home", GeoPoint(8.0805092, 99.8910302), 1, 50),
            Item(2, "Item 2", "สิทธิผล ก่อสร้าง", GeoPoint(8.0797751, 99.8890173), 1, 50),
            Item(3, "Item 3", "กาแฟยิ้ม", GeoPoint(8.0797751, 99.8890173), 1, 50),
            Item(4, "Item 4", "PTT", GeoPoint(8.0800697, 99.8886712), 1, 50)
    )

    val items2 = listOf(
            Item(1, "Item 1", "COE", GeoPoint(7.0067125, 100.4980793), 1, 50),
            Item(2, "Item 2", "คณะวิทยาศาสตร์", GeoPoint(7.0074073, 100.4978433), 1, 50),
            Item(3, "Item 3", "ศูนย์คอมพิวเตอร์", GeoPoint(7.0089354, 100.4979452), 1, 50),
            Item(4, "Item 4", "Songklanagarind Hospital", GeoPoint(7.007323, 100.493765), 1, 50),
            Item(5, "Item 5", "Faculty of Liberal Arts", GeoPoint(7.0117396, 100.4966684), 1, 50)
    )

    val lessons = listOf(
            Lesson(1, "Lesson 1", "shirtimage","Pawares Rukkumnerd", items1),
            Lesson(2, "Lesson 2", "hoodieimage","IBOT", items2)

    )
}