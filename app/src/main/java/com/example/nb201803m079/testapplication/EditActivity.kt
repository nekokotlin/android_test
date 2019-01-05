package com.example.nb201803m079.testapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.support.design.widget.Snackbar;
import com.example.nb201803m079.testapplication.R.id.editTextTweet
import io.realm.Realm


class EditActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var realm: Realm
    var tweetId: Int = 0
    var date: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        Realm.init(this)
        if(intent.extras != null){
            val bundle = intent.extras
            STATUS_EDIT = bundle.getString(getString(R.string.intent_key_status)).toInt()
            editTextTweet.setText(bundle.getString(getString(R.string.intent_key_content)))
        }

        textViewName.text = userName

        val formatTemplate = SimpleDateFormat("yyyy/MM/dd HH:mm")
        textViewTime.text = formatTemplate.format(date)

        buttonSubmit.setOnClickListener(this)
        buttonBack.setOnClickListener { finish() }

    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
    }

    override fun onClick(v: View?) {

        if (editTextTweet.text.toString() == "") {
            AlertDialog.Builder(this)
                .setTitle("内容の確認")
                .setMessage("内容を入力してください")
                .setPositiveButton("OK") { dialog, which -> }
                .show()
        } else {

            AlertDialog.Builder(this)
                .setTitle("投稿の確認")
                .setMessage("投稿しますか？")
                .setPositiveButton("OK") { dialog, which ->

                    if (STATUS_EDIT == 1) {
                        val bundle = intent.extras
                        val p2 = bundle.getInt(getString(R.string.intent_key_position))

                        realm.beginTransaction()
                        val tweets = realm.where(tweetDB::class.java).findAll()
                        val selectedTweetDB = tweets[p2]!!
                        selectedTweetDB.content = editTextTweet.text.toString()
                        selectedTweetDB.name = userName
                        selectedTweetDB.date = Date()
                        realm.commitTransaction()
                    } else {
                        realm.beginTransaction()
                        val tweetDB = realm.createObject(tweetDB::class.java)
                        //論理削除でなければ意味がないので消す
                        //tweetId = (realm.where(tweetDB::class.java).count().toInt()) + 1
                        //いらなさそう
                        tweetDB.name = userName
                        tweetDB.content = editTextTweet.text.toString()
                        tweetDB.id = tweetId
                        tweetDB.date = Date()
                        realm.commitTransaction()
                    }

                    Snackbar.make(v!!, "投稿が完了しました", Snackbar.LENGTH_SHORT).show()
                    editTextTweet.setText("")
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }


}
