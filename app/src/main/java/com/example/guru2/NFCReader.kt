package com.example.guru2

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcF
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PersonnelInfo : AppCompatActivity() {

    // NfcAdapter 변수 선언
    private lateinit var nfcAdapter: NfcAdapter // 단말기의 NFC 정보 얻어오기
    private lateinit var pendingIntent: PendingIntent // 다음 액티비티에 전송할 intent 값 가짐

    private lateinit var statementTV: TextView // 단말기의 NFC 상태 알려주는 택스트 뷰

    // Intent to start an activity when a tag with NDEF payload is discovered
    private var ndefIntent = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)

    var mIntentFilters = arrayOf(ndefIntent) // intent 필터 지정

    // 4. NFCtech 지정
    var mNFCTechLists = arrayOf(arrayOf(NfcF::class.java.name))// 연결 단말기 정보 담음

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nfc_reader)
        setTitle("NFC reader")

        statementTV = findViewById(R.id.statementTV)

        // 1. 단말기의 NFC 상태를 점검
        nfcAdapter = NfcAdapter.getDefaultAdapter(this) // NFC 사용 불가할 경우 null 반환

        if(nfcAdapter == null){
            statementTV.setText("NFC가 꺼져있습니다")
        } else {
            statementTV.setText("NFC가 켜져있습니다")
        }

        // 2. intent 값 지정
        val intent = Intent(this, /*액티비티명*/MainActivity::class.java) // 다른 액티비티에서 처리
        //현재 액티비티에서 해결할 때
        // val intent = Intent(this, /*액티비티명*/MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0) // Intent 정보를 PendingIntent 변수로 옮기기

        // 3. IntentFilter 지정
        try {
            ndefIntent.addDataType("*/*")
        } catch (e: Exception) {
            Log.e("TagDispatch", e.toString())
        }


    }

    override fun onPause() { // 화면이 보이지 않을 때 데이터 수신 중지 (화면 중지될 때 사용 안하기 위해)
        super.onPause()
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this) // 수신
            finish()
        }
    }

    override fun onResume() { // 화면에 액티비티가 올라오기 전에 실행될 내용
        super.onResume()
        if(nfcAdapter != null){ // NFC 태그 사용 가능할 경우

            // mNFCTechLists 변수 이용 다음 액티비티로 정보 전송
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, mIntentFilters, mNFCTechLists)
            // 필터 없이 모든 정보 읽어 들이고 전송
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
        }
    }

}