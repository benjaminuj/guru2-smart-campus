package com.example.guru2

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset
import java.util.*
import kotlin.experimental.and


class admin_read_card : AppCompatActivity() {

    lateinit var tvId1 : TextView
    lateinit var tvMajor1 : TextView
    lateinit var tvName1 : TextView
    lateinit var tvDepart1 : TextView
    lateinit var tvParticipate1 : TextView
    lateinit var tvMoney1 : TextView

    lateinit var passedIntent: Intent

    lateinit var ivImage : ImageView

    /*
        private val paycoPackage = "com.nhnent.payapp" // 페이코 앱의 패키지 주소
        private val intentPayco = packageManager.getLaunchIntentForPackage(paycoPackage) // 인텐트에 패키지 주소 저장
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_read_card)
        setTitle("학생 정보")

        this.tvDepart1 = findViewById(R.id.tvDepart)
        this.tvMajor1 = findViewById(R.id.tvMajor)
        this.tvName1 = findViewById(R.id.tvName)
        this.tvId1 = findViewById(R.id.tvId)
        this.tvParticipate1 = findViewById(R.id.participate)
        this.tvMoney1 = findViewById(R.id.money)

        passedIntent = getIntent() // nfc_reader 에서 보낸 intent 값 받아들임

        if (passedIntent != null) { // intent 값 null 아닐 때
            onNewIntent(passedIntent)
        }

        ivImage = findViewById(R.id.ivImage)

        /*
        var getId = intent.getStringExtra("getId").toString()
        var getPwd = intent.getStringExtra("getPwd").toString()
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        var getDepart = intent.getStringExtra("getDepart").toString()
        var getMajor = intent.getStringExtra("getMajor").toString()
        var getParticipate = intent.getStringExtra("getParticipate").toString()
        var getMoney = intent.getStringExtra("getMoney").toString()


        tvDepart1.setText(getDepart)
        tvMajor1.setText(getMajor)
        tvName1.setText(getName)
        tvId1.setText(getId)
        tvParticipate1.setText(getParticipate)
        tvMoney1.setText(getMoney)
*/
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        var s = "" // 글씨 띄우는 데 사용
        val data = intent!!.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES) // EXTRA_NEDF_MESSAGES: 여분의 배열이 태그에 존재한다

        // 조건이 null이 아닌 경우 글씨/그림으로 변환작업
        // null인 경우 메소드 작동 안 함
        if(data != null){
            try {

                //var i:Int
                for (i in 0..data.size step 1){
                    val recs = (data[i] as NdefMessage).records

                    for (j in 0.. recs.size step 1){

                        if(recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {

                            val payload = recs[j].payload

                            val textEncoding: Charset
                            var a : Byte
                            a = payload[0] and 128.toByte()
                            if (a.equals(0)) textEncoding = Charsets.UTF_8
                            else textEncoding = Charsets.UTF_16

                            val langCodeLen: Int
                            langCodeLen = payload[0].toInt() and 63

                            s += """
                                
                                ${String(payload, langCodeLen + 1, payload.size - langCodeLen - 1, textEncoding)}
                                """.trimIndent()

                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("TagDispatch", e.toString())
            }
        }

        tvId1.setText(s) // 가져온 정보를 다시 종류별로 나눠야 하는데
        // 어떤 형식으로 넘어오는지 알지 못 해 우선 한 텍스트 뷰에 정보 출력하는 것으로 처리
        // 이 부분이 후에도 구현이 안 된다면 하나의 큰 텍스트 뷰에 모든 정보를 출력하는 방향으로 처리해야 할 것
    }

}