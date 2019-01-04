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
import kotlin.collections.ArrayList

//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User

var STATUS_EDIT: Int = 0

class ListActivity: AppCompatActivity(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    lateinit var realm: Realm
    lateinit var tweets: RealmResults<tweetDB>
    lateinit var adapter: ArrayAdapter<String>
    lateinit var tweet_list: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        Realm.init(this)
        textViewName2.text = userName + "さんの投稿一覧"
    }

    override fun onResume() {
        super.onResume()

        realm = Realm.getDefaultInstance()
        tweets = realm.where(tweetDB::class.java).findAll()
        tweet_list = ArrayList<String>()

        tweets.forEach {
            tweet_list.add(it.content)
        }

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tweet_list)
        ListViewTweet.adapter = adapter

        buttonBackMain.setOnClickListener {
            finish()
        }
        ListViewTweet.onItemClickListener = this
        ListViewTweet.onItemLongClickListener = this

    }

    override fun onPause() {
        super.onPause()

        realm.close()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val selectedTweetDB = tweets[p2]!!
        val content = selectedTweetDB.content
        val date = Date()

        val intent = Intent(this@ListActivity, EditActivity::class.java)
            .apply {
                putExtra(getString(R.string.intent_key_content), content)
                putExtra(getString(R.string.intent_key_date), date)
                putExtra(getString(R.string.intent_key_position), p2)
                putExtra(getString(R.string.intent_key_status), "1")
            }
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
                tweet_list.removeAt(position)
                ListViewTweet.adapter = adapter
            }
            setNegativeButton("NO"){ dialog, which ->  }
            show()
        }
        return true
    }
}





