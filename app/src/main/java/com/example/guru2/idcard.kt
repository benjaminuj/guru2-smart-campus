package com.example.guru2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class idcard : AppCompatActivity() {

    lateinit var tvId : TextView
    lateinit var tvMajor : TextView
    lateinit var tvName : TextView
    lateinit var tvDepart : TextView
    /*
        private val paycoPackage = "com.nhnent.payapp" // 페이코 앱의 패키지 주소
        private val intentPayco = packageManager.getLaunchIntentForPackage(paycoPackage) // 인텐트에 패키지 주소 저장
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle(" ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idcard)
        this.tvDepart = findViewById(R.id.tvDepart)
        this.tvMajor = findViewById(R.id.tvDepart)
        this.tvName = findViewById(R.id.tvName)
        this.tvId = findViewById(R.id.tvDepart)

        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()


        tvDepart.setText(getDepart)
        tvMajor.setText(getMajor)
        tvName.setText(getName)
        tvId.setText(getId)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.idcard_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_home -> {
                val intent = Intent(this,idcard::class.java)
                startActivity(intent)
                return true
            }
            R.id.record -> {
                val intent = Intent(this,access_record::class.java)
                startActivity(intent)
                return true
            }/*
            R.id.pay -> {
                try {
                    startActivity(intentPayco) // 페이코 앱을 실행해본다.
                } catch (e: Exception) {  // 만약 실행이 안된다면 (앱이 없다면)
                    val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + paycoPackage)) // 설치 링크를 인텐트에 담아
                    startActivity(intentPlayStore) // 플레이스토어로 이동시켜 설치유도.
                    return true
                }
            }*/
            R.id.Logout-> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}