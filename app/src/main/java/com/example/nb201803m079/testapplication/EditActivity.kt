package com.example.nb201803m079.testapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm

class EditActivity : AppCompatActivity() {
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        realm = Realm.getDefaultInstance()
    }


    override fun onResume() {
        super.onResume()

        realm.beginTransaction()


        realm.commitTransaction()
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }
}
