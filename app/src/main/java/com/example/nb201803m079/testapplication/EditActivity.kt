package com.example.nb201803m079.testapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.support.design.widget.Snackbar;


class EditActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var realm: Realm
    var tweetId: Int = 0
    var date: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        Realm.init(this)
        val bundle = intent.extras
        if(STATUS_EDIT == 1){
            Log.d("ああああ", STATUS_EDIT.toString())
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
                .setPositiveButton("OK"){dialog, which ->  }
                .show()
        } else {

            AlertDialog.Builder(this)
                .setTitle("投稿の確認")
                .setMessage("投稿しますか？")
                .setPositiveButton("OK") { dialog, which ->
                    realm.beginTransaction()
                    val tweetDB = realm.createObject(tweetDB::class.java)

                    //いらなさそう
                    tweetDB.date = Date()
                    tweetDB.name = userName
                    tweetDB.content = editTextTweet.text.toString()
                    tweetDB.id = tweetId

                    realm.commitTransaction()
                    tweetId++

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
