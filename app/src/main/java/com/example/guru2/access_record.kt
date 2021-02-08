package com.example.guru2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import org.w3c.dom.Text

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

            // 커서 움직이며 데이터값 반환, 문자열 변수에 누적
            while (cursor.moveToNext()) {
                strDate = cursor.getString(0) + "\r\n" + strDate
                strTime = cursor.getString(1) + "\r\n" + strTime
                strSpot = cursor.getString(2) + "\r\n" + strSpot
                strId = cursor.getString(3) + "\r\n" + strId
            }

            // 출력
            dateResult.setText(strDate)
            timeResult.setText(strTime)
            spotResult.setText(strSpot)
            idReslt.setText(strId)

            cursor.close()
            sqlDB.close()

            Toast.makeText(applicationContext, "조회됨", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.access_record_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
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
                val intent = Intent(this, idcard::class.java)
                intent.putExtra("getId", getId)
                intent.putExtra("getPwd", getPwd)
                intent.putExtra("getAuth", getAuth)
                intent.putExtra("getName", getName)
                intent.putExtra("getMajor", getMajor)
                intent.putExtra("getDepart", getDepart)
                intent.putExtra("getDue", getDue)
                intent.putExtra("getReceive", getReceive)
                intent.putExtra("getProfile", getProfile)
                startActivity(intent)
                return true
            }

            R.id.pay -> {
                if (isInstalledApp("com.nhnent.payapp")) {
                    openApp("com.nhnent.payapp")
                } else {
                    val intentPlayStore = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.nhnent.payapp")
                    ) // 설치 링크를 인텐트에 담아
                    startActivity(intentPlayStore) // 플레이스토어로 이동시켜 설치유도.
                }

            }
            R.id.Logout -> {
                val intent = Intent(this, MainActivity::class.java)
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


}


