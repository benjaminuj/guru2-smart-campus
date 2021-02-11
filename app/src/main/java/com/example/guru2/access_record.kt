package com.example.guru2

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*


class access_record : AppCompatActivity() {

    lateinit var dateResult: TextView
    lateinit var timeResult: TextView
    lateinit var spotResult: TextView
    lateinit var idReslt: TextView
    lateinit var btnSelect: Button

    lateinit var myHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_record)

        setTitle("출입 기록")

        // xml 위젯 객체 연결
        dateResult = findViewById(R.id.dateResult)
        timeResult = findViewById(R.id.timeResult)
        spotResult = findViewById(R.id.spotResult)
        idReslt = findViewById(R.id.idResult)
        btnSelect = findViewById(R.id.btnSelect)

        // DB 클래스 객체 생성
        myHelper = DBHelper(this)

        // 조회 버튼 리스너
        btnSelect.setOnClickListener {
            sqlDB = myHelper.readableDatabase

            // 커서 선언, 테이블 조회 후 대입
            var cursor: Cursor = sqlDB.rawQuery("SELECT date, time, spot, id FROM entry;", null)

            // 시간, 장소 나타낼 문자열 선언
            var strDate = ""
            var strTime = ""
            var strSpot = ""
            var strId =""
            var strHour=""
            var strMinute=""

            var intHour: Int ?= null
            var intMinute: Int ?= null

            // 커서 움직이며 데이터값 반환, 문자열 변수에 누적
            while (cursor.moveToNext()) {
                strDate = cursor.getString(0) + "\r\n\r\n" + strDate

                intHour = cursor.getInt(1) / 100
                intMinute = cursor.getInt(1) - intHour*100

                // 시, 분 구하기
                if(intHour<10) {strHour = "0"+intHour.toString()}
                else {strHour = intHour.toString()}
                if(intMinute==0) {strMinute = "00"}
                else if (intMinute<10) {strMinute = "0"+intMinute.toString()}
                else {strMinute = intMinute.toString()}

                // 최근 입력된 기록이 상단에 뜨도록 문자열 저장
                strTime = strHour + ":" + strMinute + "\r\n\r\n" + strTime
                strSpot = cursor.getString(2) + "\r\n\r\n" + strSpot
                strId = cursor.getString(3) + "\r\n\r\n" + strId
            }

            // 출력
            dateResult.setText(strDate)
            timeResult.setText(strTime)
            spotResult.setText(strSpot)
            idReslt.setText(strId)

            // 테두리 생성
            dateResult.setBackgroundResource(R.drawable.edge_right)
            timeResult.setBackgroundResource(R.drawable.edge_right)
            spotResult.setBackgroundResource(R.drawable.edge_right)

            cursor.close()
            sqlDB.close()

            Toast.makeText(applicationContext, "조회되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    // 메뉴 생성
    @SuppressLint("InflateParams")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.access_record_menu, menu)

            //액션바 커스터마이징 허용
            supportActionBar?.setDisplayShowCustomEnabled(true)

            //기존 액션바 요소 숨김
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)

            var actionView =layoutInflater.inflate(R.layout.custom_actionbar,null)
            supportActionBar?.customView=actionView
            return true

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


