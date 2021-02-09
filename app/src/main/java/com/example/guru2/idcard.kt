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

        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()
        var getDue = intent.getStringExtra("getDue").toString()
        var getReceive = intent.getStringExtra("getReceive").toString()






        tvDepart.setText(getDepart)
        tvMajor.setText(getMajor)
        tvName.setText(getName)
        tvId.setText(getId)

        if(getProfile !="")
            Glide.with(this).load(getProfile).into(this.imgProfile)
        else
            imgProfile.setImageResource(R.drawable.person)


        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        // Register callback
        nfcAdapter?.setNdefPushMessageCallback(this, this)


    }


    fun Context.isInstalledApp(packageName: String): Boolean {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return intent != null
    }
    fun Context.openApp(packageName: String) { // 특정 앱을 실행하는 함수
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }



    override fun createNdefMessage(event: NfcEvent): NdefMessage {
        var getId = intent.getStringExtra("getId").toString()
        return NdefMessage(
            arrayOf(
                createMime("application/vnd.com.example.android.beam",getId.toByteArray())
            )
        )
    }

    override fun onBackPressed() {
        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getProfile = intent.getStringExtra("getProfile").toString()
        var getDue = intent.getStringExtra("getDue").toString()
        var getReceive = intent.getStringExtra("getReceive").toString()
        val intent = Intent(this,login_first::class.java)
        intent.putExtra("getId", getId)
        intent.putExtra("getPwd", getPwd)
        intent.putExtra("getAuth", getAuth)
        intent.putExtra("getName", getName)
        intent.putExtra("getMajor", getMajor)
        intent.putExtra("getDepart", getDepart)
        intent.putExtra("getProfile", getProfile)
        intent.putExtra("getDue",getDue)
        intent.putExtra("getReceive",getReceive)
        startActivity(intent)
    }

}