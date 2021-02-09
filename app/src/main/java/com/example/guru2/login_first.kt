package com.example.guru2

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class login_first : AppCompatActivity() {
    lateinit var btnCard :Button
    lateinit var btnPay :Button
    lateinit var btnLogout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_first)
        btnCard = findViewById(R.id.btnCard) //리더기 버튼
        btnPay= findViewById(R.id.btnPay) //출결확인 버튼
        btnLogout = findViewById(R.id.btnLogout)
        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()
        var getDue = intent.getStringExtra("getDue").toString()
        var getReceive = intent.getStringExtra("getReceive").toString()



        //출결확인 버튼
        btnCard.setOnClickListener {

                var intent = Intent(this, idcard::class.java)
                intent.putExtra("getId", getId)
                intent.putExtra("getPwd", getPwd)
                intent.putExtra("getAuth", getAuth)
                intent.putExtra("getName", getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart", getDepart)
                intent.putExtra("getDue",getDue)
                intent.putExtra("getReceive",getReceive)
                intent.putExtra("getProfile",getProfile)
                startActivity(intent)


        }

        btnPay.setOnClickListener {
            if (isInstalledApp("com.nhnent.payapp"))
            {
                openApp("com.nhnent.payapp")
            }else{
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhnent.payapp")) // 설치 링크를 인텐트에 담아
                startActivity(intentPlayStore) // 플레이스토어로 이동시켜 설치유도.
            }
            fun onBackPressed() {
                var intent = Intent(this, this::class.java)
                intent.putExtra("getId", getId)
                intent.putExtra("getPwd", getPwd)
                intent.putExtra("getAuth", getAuth)
                intent.putExtra("getName", getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart", getDepart)
                intent.putExtra("getDue",getDue)
                intent.putExtra("getReceive",getReceive)
                intent.putExtra("getProfile",getProfile)
            }
        }


        //리더기 버튼
        btnLogout.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_login_first,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.Logout-> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
*/
    fun Context.isInstalledApp(packageName: String): Boolean {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return intent != null
    }
    fun Context.openApp(packageName: String) { // 특정 앱을 실행하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}