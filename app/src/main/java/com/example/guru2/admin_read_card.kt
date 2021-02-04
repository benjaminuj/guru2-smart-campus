package com.example.guru2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class admin_read_card : AppCompatActivity() {

    lateinit var tvId1 : TextView
    lateinit var tvMajor1 : TextView
    lateinit var tvName1 : TextView
    lateinit var tvDepart1 : TextView
    lateinit var tvParticipate1 : TextView
    lateinit var tvMoney1 : TextView
    /*
        private val paycoPackage = "com.nhnent.payapp" // 페이코 앱의 패키지 주소
        private val intentPayco = packageManager.getLaunchIntentForPackage(paycoPackage) // 인텐트에 패키지 주소 저장
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle(" ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.tvDepart1 = findViewById(R.id.tvDepart)
        this.tvMajor1 = findViewById(R.id.tvMajor)
        this.tvName1 = findViewById(R.id.tvName)
        this.tvId1 = findViewById(R.id.tvId)
        this.tvParticipate1 = findViewById(R.id.participate)
        this.tvMoney1 = findViewById(R.id.money)

        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getParticipate = intent.getStringExtra("getParticipate").toString()
        var getMoney = intent.getStringExtra("getMoney").toString()


        tvDepart1.setText(getDepart)
        tvMajor1.setText(getMajor)
        tvName1.setText(getName)
        tvId1.setText(getId)
        tvParticipate1.setText(getParticipate)
        tvMoney1.setText(getMoney)


    }


}