package com.example.guru2

import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord.createMime
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class idcard : Activity(), NfcAdapter.CreateNdefMessageCallback {

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

        // Register callback
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

    //뒤로가기 할 경우 정보가 사라지는 것을 막기 위해 데이터 전달
    override fun onBackPressed() {
        var getId = intent.getStringExtra("getId").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()
        val intent = Intent(this,login_first::class.java)
        intent.putExtra("getId", getId)
        intent.putExtra("getName", getName)
        intent.putExtra("getMajor", getMajor)
        intent.putExtra("getDepart", getDepart)
        intent.putExtra("getProfile", getProfile)
        startActivity(intent)
    }

}