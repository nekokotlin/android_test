package com.example.nb201803m079.testapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        Realm.init(this)


    }


    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()


        realm.beginTransaction()


        realm.commitTransaction()
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }
}
