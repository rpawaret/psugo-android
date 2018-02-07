package com.ipoondev.android.psugo.services

import com.google.firebase.firestore.GeoPoint
import com.ipoondev.android.psugo.model.Item
import com.ipoondev.android.psugo.model.Lesson

object DataService {


    val items1 = listOf(
            Item(1, "Item 1", "My Home", GeoPoint(8.0805092, 99.8910302), 1, 50F),
            Item(2, "Item 2", "สิทธิผล ก่อสร้าง", GeoPoint(8.0797751, 99.8890173), 1, 50F),
            Item(3, "Item 3", "กาแฟยิ้ม", GeoPoint(8.0797751, 99.8890173), 1, 50F),
            Item(4, "Item 4", "PTT", GeoPoint(8.0800697, 99.8886712), 1, 50F)
    )

    val items2 = listOf(
            Item(1, "Item 1", "COE", GeoPoint(7.0067125, 100.4980793), 1, 50F),
            Item(2, "Item 2", "คณะวิทยาศาสตร์", GeoPoint(7.0074073, 100.4978433), 1, 50F),
            Item(3, "Item 3", "ศูนย์คอมพิวเตอร์", GeoPoint(7.0089354, 100.4979452), 1, 50F),
            Item(4, "Item 4", "Songklanagarind Hospital", GeoPoint(7.007323, 100.493765), 1, 50F),
            Item(5, "Item 5", "Faculty of Liberal Arts", GeoPoint(7.0117396, 100.4966684), 1, 50F)
    )

    val lessons = listOf(
            Lesson(1, "Lesson 1", "shirtimage","Pawares Rukkumnerd", items1),
            Lesson(2, "Lesson 2", "hoodieimage","IBOT", items2)

    )
}