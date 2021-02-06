package com.example.guru2

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import java.lang.Exception

class idcard : AppCompatActivity() {

    lateinit var tvId : TextView
    lateinit var tvMajor : TextView
    lateinit var tvName : TextView
    lateinit var tvDepart : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle(" ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idcard)
        this.tvDepart = findViewById(R.id.tvDepart)
        this.tvMajor = findViewById(R.id.tvMajor)
        this.tvName = findViewById(R.id.tvName)
        this.tvId = findViewById(R.id.tvId)

        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()



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
                var getId = intent.getStringExtra("getId").toString()
                var getPwd = intent.getStringExtra("getPwd").toString()
                var getAuth = intent.getStringExtra("getAuth").toString()
                var getName = intent.getStringExtra("getName").toString()
                var getDepart = intent.getStringExtra("getDepart").toString()
                var getMajor = intent.getStringExtra("getMajor").toString()
                val intent = Intent(this,idcard::class.java)
                intent.putExtra("getId",getId)
                intent.putExtra("getPwd",getPwd)
                intent.putExtra("getAuth",getAuth)
                intent.putExtra("getName",getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart",getDepart)
                startActivity(intent)
                return true
            }
            R.id.record -> {
                val intent = Intent(this,access_record::class.java)
                startActivity(intent)
                return true
            }
            R.id.pay -> {
                if (isInstalledApp("com.nhnent.payapp"))
                {
                    openApp("com.nhnent.payapp")
                }else{
                    val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhnent.payapp")) // 설치 링크를 인텐트에 담아
                    startActivity(intentPlayStore) // 플레이스토어로 이동시켜 설치유도.
                }

            }
            R.id.Logout-> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun Context.isInstalledApp(packageName: String): Boolean {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return intent != null
    }
    fun Context.openApp(packageName: String) { // 특정 앱을 실행하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }

}