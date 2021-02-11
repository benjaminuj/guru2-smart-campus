package com.example.guru2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord.createMime
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import kotlin.system.exitProcess

class idcard : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {

    lateinit var tvId : TextView
    lateinit var tvMajor : TextView
    lateinit var tvName : TextView
    lateinit var tvDepart : TextView
    lateinit var imgProfile: ImageView
    private var nfcAdapter: NfcAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {


        setTitle(" ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idcard)
        this.tvDepart = findViewById(R.id.tvDepart)
        this.tvMajor = findViewById(R.id.tvMajor)
        this.tvName = findViewById(R.id.tvName)
        this.tvId = findViewById(R.id.tvId)
        this.imgProfile = findViewById(R.id.imgProfile)




        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC 사용이 불가합니다. NFC 상태를 확인해주세요.", Toast.LENGTH_LONG).show()
            finish()
            return
        }else if(!nfcAdapter!!.isEnabled){
            Toast.makeText(this, "NFC 사용이 불가합니다. NFC 상태를 확인해주세요.", Toast.LENGTH_LONG).show()
            finish()
        }
        //메인 액티비티에서 받아온 변수 연결
        var getId = intent.getStringExtra("getId").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()



        //받아온 값으로 아이디카드 화면에 보여지는 텍스트뷰 설정
        tvDepart.setText(getDepart)
        tvMajor.setText(getMajor)
        tvName.setText(getName)
        tvId.setText(getId)

        //db에서 받아온 이미지파일 정보가 없으면 기본 사진을 보여주고 있으면 서버 storage에 저장된 사진을 보여줌
        if(getProfile !="")
            Glide.with(this).load(getProfile).into(this.imgProfile)
        else
            imgProfile.setImageResource(R.drawable.person)


        nfcAdapter?.setNdefPushMessageCallback(this, this)


    }

    //받아온 id(학번)값을 ndef메시지로 만듦
    override fun createNdefMessage(event: NfcEvent): NdefMessage {
        var getId = intent.getStringExtra("getId").toString()
        return NdefMessage(
            arrayOf(
                createMime("application/vnd.com.example.android.beam",getId.toByteArray())
            )
        )
    }


    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.idcard_menu,menu)

        //액션바 커스터마이징 허용
        supportActionBar?.setDisplayShowCustomEnabled(true)

        //기존 액션바 요소 숨김
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        var actionView =layoutInflater.inflate(R.layout.custom_actionbar,null)
        supportActionBar?.customView=actionView
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.Logout-> { // 로그아웃 선택 시
                val intent = Intent(this,MainActivity::class.java) // 로그인 화면으로 이동
                startActivity(intent)
                return true
            }
            R.id.pay-> { // 결제버튼 선택시
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
        }

        return super.onOptionsItemSelected(item)

    }


    fun Context.isInstalledApp(packageName: String): Boolean { //앱이 설치되었는지 확인하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return intent != null
    }
    fun Context.openApp(packageName: String) { // 특정 앱을 실행하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }


}