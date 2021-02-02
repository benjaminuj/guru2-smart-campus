package com.example.guru2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class admin_login_first : AppCompatActivity() {
    lateinit var btnReader :Button
    lateinit var btnAttend : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login_first)
        btnReader = findViewById(R.id.btnReader) //리더기 버튼
        btnAttend = findViewById(R.id.btnAttend) //출결확인 버튼
        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()


        //출결확인 버튼
        btnAttend.setOnClickListener {
            if(getAuth =="1"){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("출결 확인 권한이 없습니다.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }
            /*
            var intent = Intent(this,액티비티명::class.java)
            intent.putExtra("getId",getId)
            intent.putExtra("getPwd",getPwd)
            intent.putExtra("getAuth",getAuth)
            intent.putExtra("getName",getName)
            startActivity(intent)

             */

        }


        //리더기 버튼
        btnReader.setOnClickListener {
            /*
           var intent = Intent(this,액티비티명::class.java)
           intent.putExtra("getId",getId)
           intent.putExtra("getPwd",getPwd)
           intent.putExtra("getAuth",getAuth)
           intent.putExtra("getName",getName)
           startActivity(intent)
            */
        }



    }
}