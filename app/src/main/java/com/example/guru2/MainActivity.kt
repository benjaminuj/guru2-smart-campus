package com.example.guru2


import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.lang.StringBuilder
import java.util.*
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    lateinit var myHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var btnLogin1: Button
    lateinit var btnLogin2: Button
    lateinit var edtId: EditText
    lateinit var edtPwd: EditText
    lateinit var reff: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        title = " "
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin1 = findViewById(R.id.btnLogin1) //로그인
        btnLogin2 = findViewById(R.id.btnLogin2) //관리자로그인
        edtId = findViewById(R.id.edtId)        //입력된 id
        edtPwd = findViewById(R.id.edtPwd)      //입력된 비밀번호
        myHelper = DBHelper(this)

        var getId: String = ""
        var getDepart: String = ""
        var getAuth = ""
        var getPwd = ""
        var getName = ""
        var getDue = ""
        var getProfile = ""
        var getReceive = ""
        var getMajor = ""


        val database = FirebaseDatabase.getInstance()

        var getData = object : ValueEventListener { ///서버에서 데이터 가져오기
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                getAuth = snapshot.child("auth").value.toString()
                getDepart = snapshot.child("depart").value.toString()
                getDue = snapshot.child("due").value.toString()
                getId = snapshot.child("id").value.toString()
                getMajor = snapshot.child("major").value.toString()
                getName = snapshot.child("name").value.toString()
                getPwd = snapshot.child("password").value.toString()
                getProfile = snapshot.child("profile").value.toString()
                getReceive = snapshot.child("receive").value.toString()
            }


        }


        ////////로그인
        btnLogin1.setOnClickListener {
            var enterId = edtId.text.toString() //입력된 id 문자열로 저장
            var enterPwd = edtPwd.text.toString() //입력된 패스워드 문자열로 저장

            database.getReference(enterId).addValueEventListener(getData)
            //anything you want to start after 3s

            if (enterId == "" || enterPwd == "") { //둘중 하나라도 공백이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("아이디/비밀번호를 입력해주세요")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            } else { //공백이 아니면
                Handler().postDelayed(Runnable { //서버에서값 가져오는거 기다리는시간
                    if (enterId == getId && enterPwd == getPwd) {
                        var intent = Intent(this, idcard::class.java)


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


                    } else {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("알림!")
                        builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                        builder.setIcon(R.drawable.symbol)
                        builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        })
                        builder.show()

                    }
                    //anything you want to start after 3s
                }, 1000)

            }

        }


        ///////관리자 로그인
        btnLogin2.setOnClickListener {
            var enterId = edtId.text.toString() //입력된 id 문자열로 저장
            var enterPwd = edtPwd.text.toString() //입력된 패스워드 문자열로 저장

            database.getReference(enterId).addValueEventListener(getData)
            //anything you want to start after 3s

            if (enterId == "" || enterPwd == "") { //둘중 하나라도 공백이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("아이디/비밀번호를 입력해주세요")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            } else { //공백이 아니면
                Handler().postDelayed(Runnable { //서버에서값 가져오는거 기다리는시간
                    if (enterId == getId && enterPwd == getPwd) {
                        if (getAuth == "1" || getAuth == "2") {
                            if (getAuth == "1") { //권한이 1일경우 학생회 로그인 텍스트 메시지
                                Toast.makeText(this, "관리자 로그인됨 : 학생회", Toast.LENGTH_SHORT).show()
                            } else if (getAuth == "2") //권한이 2일 경우 교직원 로그인 텍스트 메시지
                                Toast.makeText(this, "관리자 로그인됨 : 교직원", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, admin_login_first::class.java)


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

                        } else {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("알림!")
                            builder.setMessage("관리자 권한이 없습니다.")
                            builder.setIcon(R.drawable.symbol)
                            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            builder.show()
                        }
                    } else {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("알림!")
                        builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                        builder.setIcon(R.drawable.symbol)
                        builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        })
                        builder.show()

                    }
                    //anything you want to start after 3s
                }, 1000)

            }
        }
    }
}




