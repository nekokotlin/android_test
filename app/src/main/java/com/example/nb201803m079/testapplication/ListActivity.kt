package com.example.nb201803m079.testapplication

import android.app.ProgressDialog.show
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User




class ListActivity: AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    lateinit var realm: Realm
    lateinit var tweets: RealmResults<tweetDB>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        Realm.init(this)
        textViewName2.text = userName + "さんの投稿一覧"
        
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

        tweets = realm.where(tweetDB::class.java).findAll()

//        val adapter = ArrayAdapter<tweetDB>(this,android.R.layout.two_line_list_item, tweets)

//        val sAdapter = SimpleAdapter

//        ListViewTweet.adapter = adapter

//            new String[]{"right", "main", "sub"}, new int[]{R.id.item_right, R.id.item_main, R.id.item_sub});

        val tweet_list = ArrayList<String>()

        tweets.forEach {
            tweet_list.add(it.id.toString() + ":" + it.content)
        }

        val adapter_tweet = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tweet_list)
        ListViewTweet.adapter = adapter_tweet

        ListViewTweet.setOnItemClickListener { parent: AdapterView<*>?, view:View?, position: Int, id:Long ->
            startActivity((Intent(this, EditActivity::class.java)))
    }

        buttonBackMain.setOnClickListener {
            finish()
        }

    }

    override fun onPause() {
        super.onPause()

        realm.close()
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("ここ", "nnn")
        val selectedTweetDB = tweets[position]!!
        val content = selectedTweetDB.content
        val date = Date()
        val status = 0

        val intent = Intent(this@ListActivity, EditActivity::class.java)
//            .apply{
//            putExtra(getString(R.string.intent_key_content), content)
//            putExtra(getString(R.string.intent_key_date), date)
//            putExtra(getString(R.string.intent_key_position), position)
//            putExtra(getString(R.string.intent_key_status), status)
//        }
        startActivity(intent)

    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val selectedTweetDB = tweets[position]!!
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("投稿の削除")
            setMessage("削除してもいいですか？")
            setPositiveButton("YES"){ dialog, which ->
                realm.beginTransaction()
                selectedTweetDB.deleteFromRealm()
                realm.commitTransaction()
//                word_list.removeAt(p2)
            }
            setNegativeButton("NOニャー"){ dialog, which ->  }
            show()
        }

        return true

    }




}
