package com.example.guru2

import android.app.DatePickerDialog
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity



class attend_confirm : AppCompatActivity() {

    lateinit var tvProfessor: TextView // 교수명 출력 텍스트뷰
    lateinit var btnResult:Button // 조회 버튼
    lateinit var spinner: Spinner // 강의명 선택 스피너
    lateinit var btnclassDate:Button // 날짜 선택 버튼

    // DB 클래스 객체 생성
    lateinit var myHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    // 스크롤뷰에 출력할 텍스트뷰
    lateinit var timeResult2: TextView
    lateinit var nameResult2: TextView
    lateinit var idResult2: TextView
    lateinit var majorResult2: TextView

    // DB에서 정보 검색 위한 문자열 담을 공간 생성
    var strSpot: String ?= ""
    var strDate: String ?= ""
    var intPeriod: Int ?= null
    var strWDay: String ?= ""
    var strRange: String ?= ""
    var strHour: String ?= ""
    var strMinute: String ?= ""

    //스피너 항목준비
    var listLecture = mutableListOf<String>()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attend_confirm)

        // xml 객체 연결
        tvProfessor = findViewById(R.id.tvProfessor)
        spinner = findViewById(R.id.spinner)
        btnResult = findViewById(R.id.btnResult)
        btnclassDate = findViewById(R.id.btnclassDate)

        timeResult2 = findViewById(R.id.timeResult2)
        nameResult2 = findViewById(R.id.nameResult2)
        idResult2 = findViewById(R.id.idResult2)
        majorResult2 = findViewById(R.id.majorResult2)

        // 교수님 성함 가져오기
        var getName = intent.getStringExtra("getName").toString()

        // 텍스트뷰 글자 설정(교수님 성함)
        tvProfessor.setText(getName+"교수")

        // DB 클래스 객체 생성
        myHelper = DBHelper(this)
        sqlDB = myHelper.readableDatabase

        // 커서 선언, 테이블 조회 후 대입
        val cursor1: Cursor = sqlDB.rawQuery("SELECT professor, lecture FROM lec_info;", null)

        // lec_info 테이블에서 이름이 일치하는 교수의 강의명 불러오기
        while (cursor1.moveToNext()){
            if (getName == cursor1.getString(0)) {
                listLecture.add(cursor1.getString(1)) // 스피너 항목에 대입
            }
            //Toast.makeText(this, "강의명 불러옴", Toast.LENGTH_SHORT).show()
        }
        cursor1.close()
        sqlDB.close()

        // 스피너 어댑터 연결
       spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listLecture)

        // 스피너 아이템 선택 리스너
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { // 선택되지 않았을 때
                Toast.makeText(this@attend_confirm, "강의를 선택하세요", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                // lec_info 테이블에서 교수명+강의명으로 필터링하여 강의실, 교시, 요일 정보 찾기
                sqlDB = myHelper.readableDatabase
                val cursor2: Cursor = sqlDB.rawQuery("SELECT spot, period, dayOfWeek FROM lec_info " +
                        "WHERE professor = '" + getName + "' AND lecture = '" + listLecture[position] + "';",
                        null)

                while(cursor2.moveToNext()){
                    strSpot = cursor2.getString(0) // 강의실 정보 저장
                    intPeriod = cursor2.getInt(1) // 교시 정보 저장
                    strWDay = cursor2.getString(2) // 요일 정보 저장
                }

                when(intPeriod){ // 출입 기록 검색 시 time을 필터링할 정보 저장
                    12 -> strRange = "0830 < time < 1145" // 1-2교시인 경우, 08시30분~11시45분에 출입한 기록 필터링
                    34 -> strRange = "1130 < time < 1445" // 3-4교시인 경우, 11시30분~14시45분에 출입한 기록 필터링
                    56 -> strRange = "1430 < time < 1745" // 5-6교시인 경우, 14시30분~17시45분에 출입한 기록 필터링
                }

                cursor2.close()
                sqlDB.close()
            }
        }

        // 날짜 선택 버튼
        btnclassDate.setOnClickListener { view ->
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var dateFormat = SimpleDateFormat("yyyy-MM-dd") // 날짜 저장할 형식 지정

            var date_listener  = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    btnclassDate.setText("${month+1}월 ${dayOfMonth}일") // 날짜가 선택됨을 보여주기 위해 날짜 선택 버튼 텍스트를 날짜로 변경
                    calendar.set(year, month, dayOfMonth)
                    strDate = dateFormat.format(calendar.getTime()) // 형식에 따라 날짜 저장
                }
            }

            var builder = DatePickerDialog(this, date_listener, year, month, day)
            builder.show() // 대화상자를 보임

        }

        // 조회 버튼
        btnResult.setOnClickListener {

            if (strDate==""||spinner.adapter.isEmpty) { // 날짜나 강의명이 선택되지 않았을 때

                Toast.makeText(this, "강의 정보를 선택해주세요", Toast.LENGTH_SHORT).show()

            } else { // 정상적으로 선택되었을 때

                // 강의실+날짜+시간 정보로 전체 학생 출입 기록 테이블에서 정보 찾기
                sqlDB = myHelper.readableDatabase
                val cursor3: Cursor = sqlDB.rawQuery("SELECT time, name, id, major FROM entry " +
                        "WHERE date = '"+strDate+"' AND spot = '"+strSpot+"' AND "+strRange+";", null)

                // 시간, 이름, 학번, 학과 나타낼 문자열 선언
                var strTime = ""
                var strName = ""
                var strId = ""
                var strMajor = ""

                // 시, 분 나타낼 변수
                var intHour: Int ?= null
                var intMinute: Int ?= null

                if (cursor3.count == 0){ // 해당하는 기록이 없을 때

                    // 오류 토스트 메시지 출력
                    Toast.makeText(this, "강의 정보를 다시 입력하세요", Toast.LENGTH_SHORT).show()

                    // 빈칸 출력
                    timeResult2.setText("")
                    nameResult2.setText("")
                    idResult2.setText("")
                    majorResult2.setText("")

                } else { // 해당 기록이 있을 때

                    while (cursor3.moveToNext()) {

                        // 시: 네 자리 중 앞의 두 자리, 분: 뒤의 두 자리
                        intHour = cursor3.getInt(0) / 100
                        intMinute = cursor3.getInt(0) - intHour*100

                        // 시, 분 정보 문자열 저장
                        if(intHour<10) {strHour = "0"+intHour.toString()}
                        else {strHour = intHour.toString()}
                        if(intMinute==0) {strMinute = "00"}
                        else if (intMinute<10) {strMinute = "0"+intMinute.toString()}
                        else {strMinute = intMinute.toString()}

                        // 최근 입력된 기록이 상단에 오도록 문자열 저장
                        strTime = strHour + ":" + strMinute + "\r\n\r\n" + strTime
                        strName = cursor3.getString(1) + "\r\n\r\n" + strName
                        strId = cursor3.getString(2) + "\r\n\r\n" + strId
                        strMajor = cursor3.getString(3) + "\r\n\r\n" + strMajor
                    }

                    // 출력
                    timeResult2.setText(strTime)
                    nameResult2.setText(strName)
                    idResult2.setText(strId)
                    majorResult2.setText(strMajor)

                    cursor3.close()
                    sqlDB.close()

                    Toast.makeText(this, "$strDate $strSpot ${intPeriod}교시 조회됨", Toast.LENGTH_SHORT).show()
                }
            }

        }

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

}
