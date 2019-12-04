package com.example.kimali.compensation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.kimali.*
import com.example.kimali.Login.Loginactivity

class compensation_firstActivity : AppCompatActivity() {
    lateinit var text: String
    lateinit var who: String
    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_first)

        if (intent.hasExtra("selectedString")) {
            text = intent.getStringExtra("selectedString")
            who = intent.getStringExtra("who")
            name = intent.getStringExtra("name")
            setTitle(name)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        var money_btn = findViewById(R.id.money_btn) as Button
        var pc_btn = findViewById(R.id.pc_btn) as Button

        money_btn.setOnClickListener {
            val intent = Intent(this, compensation_money::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            intent.putExtra("name", name)
            this.startActivity(intent)
        }

        pc_btn.setOnClickListener {
            val intent = Intent(this, compensation_pc::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            intent.putExtra("name", name)
            this.startActivity(intent)
        }




    }

}
