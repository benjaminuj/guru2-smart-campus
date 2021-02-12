package com.example.guru2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class admin_login_first : AppCompatActivity() { ////////관리자 로그인 첫 페이지
    lateinit var btnReader :Button
    lateinit var btnRecord :Button
    lateinit var btnAttend : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login_first)
        btnReader = findViewById(R.id.btnCard) //리더기 버튼
        btnAttend = findViewById(R.id.btnAttend) //출결확인 버튼
        btnRecord = findViewById(R.id.btnLogout) //출입기록확인 버튼

         ////로그인 창에서 보낸 변수 받기
        var getAuth = intent.getStringExtra("getAuth").toString()
        var getName = intent.getStringExtra("getName").toString()




        //출결확인 버튼
        btnAttend.setOnClickListener {
            if(getAuth =="1"){      //권한이 1(학생회)이면 권한없음 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("출결 확인 권한이 없습니다.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }else {
                var intent = Intent(this, attend_confirm::class.java)
                intent.putExtra("getName", getName)
                startActivity(intent)
            }


        }


        ////////출입기록 버튼
        btnRecord.setOnClickListener {
            if(getAuth =="3"){   //권한이 3이면 출입기록 확인 페이지로 아니면 권한없음
                var intent = Intent(this, access_record::class.java)
                startActivity(intent)
            }else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("출입 기록 확인 권한이 없습니다.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()


            }


        }


        ///////////리더기 버튼
        btnReader.setOnClickListener {
            var intent = Intent(this, nfc_reader::class.java)
            startActivity(intent)

        }
    }

    // 메뉴 생성
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
        when(item?.itemId){
            R.id.Logout-> { // 로그아웃 선택 시
                val intent = Intent(this,MainActivity::class.java) // 로그인 화면으로 이동
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() { // 뒤로가기 버튼
        val intent = Intent(this,MainActivity::class.java) // 로그인 화면으로 이동
        startActivity(intent)
    }



}