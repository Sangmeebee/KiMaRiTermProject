package com.example.kimali.compensation

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kimali.Login.InitMoney_DB
import com.example.kimali.Login.InitPcTime_DB
import com.example.kimali.Login.Loginactivity
import com.example.kimali.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_compensation_money.*
import kotlinx.android.synthetic.main.activity_detail_mission.*
import java.util.HashMap


class compensation_money : AppCompatActivity() {
    var money_m = 0
    lateinit var money_s: String
    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_money)
        database = FirebaseDatabase.getInstance().reference


        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        money_s = intent.getStringExtra("total_money")
        money_m = money_s.toInt()
        setTitle(name)

        if(who == "보호자"){
            use_money.setEnabled(false);
            use_money.setVisibility(Button.INVISIBLE);
            use_money_btn.setEnabled(false);
            use_money_btn.setVisibility(Button.INVISIBLE);
        }else {
            use_money.setEnabled(true);
            use_money.setVisibility(Button.VISIBLE);
            use_money_btn.setEnabled(true);
            use_money_btn.setVisibility(Button.VISIBLE);
        }



        var money_text = findViewById(R.id.money_text) as TextView
        var use_money = findViewById(R.id.use_money) as EditText
        var use_money_btn = findViewById(R.id.use_money_btn) as Button
        var confirm_money_btn = findViewById(R.id.confirm_money_btn) as Button

        money_text.setText("총 보상 금액 : " + money_m + " 원")



        use_money_btn.setOnClickListener {
            var i : Int
            if(!TextUtils.isEmpty(use_money.text.toString())) {
                i = Integer.parseInt(use_money.text.toString())
            }
            else
                i = 0
            use_money(i)
        }

        confirm_money_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("who",who)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
            finish()
        }
    }
    // editText에 입력 후 버튼 누르면 결제 될지 안될지 정해주는 창
    fun use_money(i : Int) {

        Log.d("chano",i.toString())
        if (i == 0) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            //tilte 부분 xml
            builder.setTitle("알림")
            builder.setMessage("금액을 입력해주세요")
            //확인버튼
            builder.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                })
            builder.show()
        } else if (money_m >= i) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            //tilte 부분 xml
            builder.setTitle("알림")
            builder.setMessage("총 보상금액에서 " + i + " 원 사용하시겠습니까?")
            //확인버튼
            builder.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    money_m = money_m - i  // 여기서 다시 파이어베이스에 저장해줘야함
                    writeChild()
                    money_text.setText("총 보상 금액 : " + money_m + " 원")
                    use_money.setText("")
                })

            builder.setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, which -> })
            builder.show()
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            //tilte 부분 xml
            builder.setTitle("알림")
            builder.setMessage("입력한 금액이 총 금액보다 많습니다.")
            //확인버튼
            builder.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    use_money.setText("")
                })
            builder.show()
        }
    }

    private fun writeChild() {
        val post = ModifyMoney_DB(money_m.toString())
        val postValues = post.toMap()
        val childUpdates = HashMap<String, Any>()
        childUpdates["/mission/$topic/total_money"] = postValues

        database.updateChildren(childUpdates)
    }

}
