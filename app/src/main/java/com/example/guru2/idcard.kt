package com.example.guru2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class idcard : AppCompatActivity() {

    private val paycoPackage = "com.nhnent.payapp" // 페이코 앱의 패키지 주소
    private val intentPayco = packageManager.getLaunchIntentForPackage(paycoPackage) // 인텐트에 패키지 주소 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            }
            R.id.pay -> {
                try {
                    startActivity(intentPayco) // 페이코 앱을 실행해본다.
                } catch (e: Exception) {  // 만약 실행이 안된다면 (앱이 없다면)
                    val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + paycoPackage)) // 설치 링크를 인텐트에 담아
                    startActivity(intentPlayStore) // 플레이스토어로 이동시켜 설치유도.
                    return true
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

}