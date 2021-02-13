package com.example.guru2


import android.content.DialogInterface
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class nfc_reader : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {

    private var nfcAdapter: NfcAdapter? = null
    lateinit var btnLoad : Button
    var readId : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)

        btnLoad = findViewById(R.id.btnLoad)



        //nfc가 실행가능한지 확인
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC 사용이 불가합니다. NFC 상태를 확인해주세요.", Toast.LENGTH_LONG).show()
            finish()
            return
        }else if(!nfcAdapter!!.isEnabled){
            Toast.makeText(this, "NFC 사용이 불가합니다. NFC 상태를 확인해주세요.", Toast.LENGTH_LONG).show()
            finish()

        }
        nfcAdapter?.setNdefPushMessageCallback(this, this)


        //정보 불러오기 버튼을 클릭했을 시
        btnLoad.setOnClickListener {
            if(readId == ""){  //읽어온 아이디 값이 없으면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("학생 정보를 불러오지 못했습니다. 다시 태그해주세요.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }
            else if(readId =="리더기"){ //메시지를 송신한 측이 리더기 쪽이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("올바른 아이디 카드가 아닙니다. 다시 태그해주세요.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }else { //제대로 태그 정보를 받아오면 받아온 아이디 값을 다음 액티비티로 전송
            var intent = Intent(this, admin_read_card::class.java)
                intent.putExtra("getId",readId) //태그로 읽어온 아이디 값 전송 readId값을 getId라는 이름의 변수로
                startActivity(intent)

            }
        }

    }

    override fun createNdefMessage(event: NfcEvent): NdefMessage { //리더기에서 메시지 전송될 때는 리더기라는 메시지를 담아 전송하여 오류 대화상자를 띄우게 함
        val text = "리더기"
        return NdefMessage(
            arrayOf(
                NdefRecord.createMime("application/vnd.com.example.android.beam", text.toByteArray())
            )
        )
    }

    override fun onResume() { //안드로이드 빔으로 인해 활동이 시작되었는지 확인
        super.onResume()
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            processIntent(intent)
        }
    }

    private fun processIntent(intent: Intent) { //ndef메시지로 받아온 내용를 readId에 저장
        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMsgs ->
            (rawMsgs[0] as NdefMessage).apply {
                readId=String(records[0].payload)



            }
        }
    }

    override fun onBackPressed() { //뒤로가기 시 관리자 로그인 첫페이지로 & 정보 상실 방지
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()
        val intent = Intent(this,admin_login_first::class.java)
        intent.putExtra("getAuth", getAuth)
        intent.putExtra("getName", getName)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_login_first,menu)

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
        when (item?.itemId) {
            R.id.Logout -> { // 로그아웃 선택 시
                val intent = Intent(this, MainActivity::class.java) // 로그인 화면으로 이동
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

