package com.example.nb201803m079.testapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        Realm.init(this)


        textViewName2.text = userName + "さんの投稿一覧"

        buttonToCreate.setOnClickListener {
            val intent = Intent(this@ListActivity, EditActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onResume() {
        super.onResume()

        realm = Realm.getDefaultInstance()

        //adapterの使い方
        //中身(配列)を用意する
        //adapterインスタンスを作成する
        //引数はcontext、リストのデザイン、どの配列を渡すか
        //ビューのListViewに指定したID.adapter = adapter
        //val items = Array(20, { i -> "Title-$i" })
        //val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        //ListViewTweet.adapter = adapter

        val tweetDB = realm.where(tweetDB::class.java).findAll()
        val adapter = ArrayAdapter<tweetDB>(this,android.R.layout.simple_list_item_1, tweetDB)
        ListViewTweet.adapter = adapter


        buttonBackMain.setOnClickListener {
            finish()
        }


    }
}
