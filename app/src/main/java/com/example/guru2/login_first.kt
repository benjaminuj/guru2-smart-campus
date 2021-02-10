package com.example.guru2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

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

        //변수 받아옴
        var getId = intent.getStringExtra("getId").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()



        //아이디카드 버튼
        btnCard.setOnClickListener {

                //메인 액티비티에서 받아온 값 그대로 전달
                var intent = Intent(this, idcard::class.java)
                intent.putExtra("getId", getId)
                intent.putExtra("getName", getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart", getDepart)
                intent.putExtra("getProfile", getProfile)
                startActivity(intent)

        }

        btnPay.setOnClickListener { //결제버튼 클릭시
            if (isInstalledApp("com.nhnent.payapp"))//외부어플(페이코)이 설치되었는지 확인
            {
                openApp("com.nhnent.payapp")
            }else{                                              //설치되어있지 않으면 플레이스토어로 이동시켜 설치유도
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhnent.payapp")) // 설치 링크를 인텐트에 담아
                startActivity(intentPlayStore)
            }
            //외부어플 실행 후 어플 종료
            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }


        //로그아웃버튼
        btnLogout.setOnClickListener { //로그아웃 기능 - 로그인 화면으로
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
    }

    fun Context.isInstalledApp(packageName: String): Boolean { //앱이 설치되었는지 확인하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return intent != null
    }
    fun Context.openApp(packageName: String) { // 특정 앱을 실행하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }

    override fun onBackPressed() { //뒤로가기 기능 - 로그인 화면으로
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}