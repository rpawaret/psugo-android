package com.ipoondev.android.psugo.services

import com.google.firebase.auth.FirebaseAuth
import com.ipoondev.android.psugo.model.Player
import java.util.*

object DataService {

    val user = FirebaseAuth.getInstance().currentUser

//    val items1 = listOf(
//            Item(1, "Item 1", "My Home", GeoPoint(8.0805092, 99.8910302), 1, 50F),
//            Item(2, "Item 2", "สิทธิผล ก่อสร้าง", GeoPoint(8.0797751, 99.8890173), 1, 50F),
//            Item(3, "Item 3", "กาแฟยิ้ม", GeoPoint(8.0797751, 99.8890173), 1, 50F),
//            Item(4, "Item 4", "PTT", GeoPoint(8.0800697, 99.8886712), 1, 50F)
//            Item("My Home", GeoPoint(8.0805092, 99.8910302), 1, 50F, Date(), 0),
//            Item("กาแฟยิ้ม", GeoPoint(8.0797751, 99.8890173), 1, 50F, Date(), 0)
//    )

//    val items2 = listOf(
//            Item(1, "Item 1", "COE", GeoPoint(7.0067125, 100.4980793), 1, 50F),
//            Item(2, "Item 2", "คณะวิทยาศาสตร์", GeoPoint(7.0074073, 100.4978433), 1, 50F),
//            Item(3, "Item 3", "ศูนย์คอมพิวเตอร์", GeoPoint(7.0093895, 100.4981548), 1, 100F),
//            Item(4, "Item 4", "Songklanagarind Hospital", GeoPoint(7.007323, 100.493765), 1, 50F),
//            Item(5, "Item 5", "Faculty of Liberal Arts", GeoPoint(7.0117396, 100.4966684), 1, 50F)
//    )

//    val missions = listOf(
////            Mission(1, "Mission 1", "shirtimage","Pawares Rukkumnerd", "Math", "Playing", items1),
////            Mission(2, "มารู้จัก มอ กันเถอะ", "hoodieimage","IBOT","English","", items2)
////            Mission(user!!, "My Homee", "ลองหาดูสิว่ามีอะไรน่าสนใจบ้าง", "Math",
////                    "Not Playing", "shirtimage", 0, Date()),
////            Mission(user!!, "มารู้จัก มอ กันเถอะ", "ออกตามหา items อยู่ภายในมหาวิทยาลัยสงขลาครินทร์ วิทยาเขตหาดใหญ่ และตอบคำถามของแต่ละไอเท็มให้สำเร็จ",
////                    "Science", "Not Playing", "hodieimage", 0, Date())
//
//    )

    val player = listOf(
            Player(user!!,0, "", Date())
    )
}