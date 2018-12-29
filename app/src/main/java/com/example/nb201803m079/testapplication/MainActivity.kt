package com.example.nb201803m079.testapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            textView.text = editText.text.toString() + "さん、ようこそ"
        }
        button2.setOnClickListener {
//            val imageView = convertView.findViewById(R.id.image) as ImageView

            Picasso.with(this)
                .load(R.drawable.cat)
                .fit()
                .centerInside()
                .into(imageView);

        }

        button3.setOnClickListener {
            constraintLayout.setBackgroundResource(R.color.back01)
        }

        button4.setOnClickListener {
            val intent = Intent(MainActivity@this, EditActivity::class.java)
        }


    }

}
