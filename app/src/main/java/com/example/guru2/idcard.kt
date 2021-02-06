package com.example.guru2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord.createMime
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.lang.Exception

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
                var getDue = intent.getStringExtra("getDue").toString()
                var getReceive = intent.getStringExtra("getReceive").toString()
                var getProfile = intent.getStringExtra("getProfile").toString()
                val intent = Intent(this,idcard::class.java)
                intent.putExtra("getId",getId)
                intent.putExtra("getPwd",getPwd)
                intent.putExtra("getAuth",getAuth)
                intent.putExtra("getName",getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart",getDepart)
                intent.putExtra("getDue",getDue)
                intent.putExtra("getReceive",getReceive)
                intent.putExtra("getProfile",getProfile)
                startActivity(intent)
                return true
            }
            R.id.record -> {
                var getId = intent.getStringExtra("getId").toString()
                var getPwd = intent.getStringExtra("getPwd").toString()
                var getAuth = intent.getStringExtra("getAuth").toString()
                var getName = intent.getStringExtra("getName").toString()
                var getDepart = intent.getStringExtra("getDepart").toString()
                var getMajor = intent.getStringExtra("getMajor").toString()
                var getDue = intent.getStringExtra("getDue").toString()
                var getReceive = intent.getStringExtra("getReceive").toString()
                var getProfile = intent.getStringExtra("getProfile").toString()
                val intent = Intent(this,access_record::class.java)
                intent.putExtra("getId",getId)
                intent.putExtra("getPwd",getPwd)
                intent.putExtra("getAuth",getAuth)
                intent.putExtra("getName",getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart",getDepart)
                intent.putExtra("getDue",getDue)
                intent.putExtra("getReceive",getReceive)
                intent.putExtra("getProfile",getProfile)
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

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


    override fun createNdefMessage(event: NfcEvent): NdefMessage {
        var getId = intent.getStringExtra("getDepart").toString()
        return NdefMessage(
            arrayOf(
                createMime("application/vnd.com.example.android.beam",getId.toByteArray())
            )
            /**
             * The Android Application Record (AAR) is commented out. When a device
             * receives a push with an AAR in it, the application specified in the AAR
             * is guaranteed to run. The AAR overrides the tag dispatch system.
             * You can add it back in to guarantee that this
             * activity starts when receiving a beamed message. For now, this code
             * uses the tag dispatch system.
             *///,NdefRecord.createApplicationRecord("com.example.android.beam")
        )
    }

}