package com.example.guru2

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2.R

class attend_confirm : AppCompatActivity() {

    lateinit var btnResult:Button
    lateinit var spinner: Spinner
    lateinit var btnclassDate:Button
    lateinit var classDateView : TextView

    //스피너 항목준비
    var list_of_items = arrayOf("데이타베이스", "C프로그래밍", "자바프로그래밍", "모바일보안")


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        title = " "
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attend_confirm)
        spinner = findViewById(R.id.spinner)
        btnResult = findViewById(R.id.btnResult)
        btnclassDate = findViewById(R.id.btnclassDate)
        classDateView = findViewById(R.id.classDateView)

        if (::spinner.isInitialized) {
            //어답터 설정 - 안드로이드에서 제공하는 어답터를 연결
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_of_items)

            //아이템 선택 리스너
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    println("과목을 선택하세요.")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }


            }
            //버튼 클릭 리스너
            btnResult.setOnClickListener {
                //현재 어떤 항목이 선택되어 있는지 어떻게 알지?
                var myClassName = spinner.selectedItem
                var MCL = Toast.makeText(this, "${myClassName}", Toast.LENGTH_SHORT)
                MCL.show()
            }

            btnclassDate.setOnClickListener { view ->
                var calendar = Calendar.getInstance()
                var year = calendar.get(Calendar.YEAR)
                var month = calendar.get(Calendar.MONTH)
                var day = calendar.get(Calendar.DAY_OF_MONTH)

                var date_listener  = object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        classDateView.text =  "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    }
                }

                var builder = DatePickerDialog(this, date_listener, year, month, day)
                builder.show()

            }
        }

    }
}