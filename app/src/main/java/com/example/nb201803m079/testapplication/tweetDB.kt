package com.example.nb201803m079.testapplication

import io.realm.RealmObject
import java.util.*

open class tweetDB: RealmObject(){

    var id: Int = 0
    var name: String = ""
    var content: String = ""
    var date: Date = Date()
}