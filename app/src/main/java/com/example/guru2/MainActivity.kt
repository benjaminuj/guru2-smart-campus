package com.example.guru2


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.database.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() { ///어플 실행시 첫 화면(로그인)

    lateinit var btnLogin1: Button
    lateinit var btnLogin2: Button
    lateinit var edtId: EditText
    lateinit var edtPwd: EditText

    var mBackWait:Long = 0 //뒤로가기 두번 클릭시 어플 종료할때 사용

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ThemeGURU2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin1 = findViewById(R.id.btnLogin1) //로그인
        btnLogin2 = findViewById(R.id.btnLogin2) //관리자로그인
        edtId = findViewById(R.id.edtId)        //입력된 id
        edtPwd = findViewById(R.id.edtPwd)      //입력된 비밀번호

        var getId: String = ""
        var getDepart: String = ""
        var getAuth = ""
        var getPwd = ""
        var getName = ""
        var getProfile = ""
        var getMajor = ""


        val database = FirebaseDatabase.getInstance()

        var getData = object : ValueEventListener { ///서버에서 데이터 가져오기
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) { //데이터가 변경되면 값을 받음
                getAuth = snapshot.child("auth").value.toString()
                getDepart = snapshot.child("depart").value.toString()
                getId = snapshot.child("id").value.toString()
                getMajor = snapshot.child("major").value.toString()
                getName = snapshot.child("name").value.toString()
                getPwd = snapshot.child("password").value.toString()
                getProfile = snapshot.child("profile").value.toString()
            }


        }


        //////////////////로그인
        btnLogin1.setOnClickListener {
            var enterId = edtId.text.toString() //입력된 id 문자열로 저장
            var enterPwd = edtPwd.text.toString() //입력된 패스워드 문자열로 저장

            database.getReference(enterId).addValueEventListener(getData) //서버의 enterId 테이블에서 정보를 받아옴

            if (enterId == "" || enterPwd == "") { //입력된 아이디와 비밀번호가 둘중 하나라도 공백이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("아이디/비밀번호를 입력해주세요")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            } else { //입력된 아이디와 비밀번호가 공백이 아니면
                Handler().postDelayed(Runnable { //서버에서값 가져오는것 기다리는 시간 주기
                    if (enterId == getId && enterPwd == getPwd) { //입력된 아이디 비밀번호와 서버의 db에서 검색해 받아온 값이 같으면
                        var intent = Intent(this, idcard::class.java)


                        intent.putExtra("getId", getId)     //서버에서 받아온 값 다음 액티비티로 넘겨주기
                        intent.putExtra("getName", getName)
                        intent.putExtra("getMajor", getMajor)
                        intent.putExtra("getDepart", getDepart)
                        intent.putExtra("getProfile", getProfile)
                        startActivity(intent) //login first 액티비티 실행


                    } else { //입력된 아이디 비밀번호와 서버의 db에서 검색해 받아온 값이 둘 중 하나라도 다르면 대화상자
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("알림!")
                        builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                        builder.setIcon(R.drawable.symbol)
                        builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        })
                        builder.show()

                    }

                }, 1200) //기다리는 시간

            }

        }


        /////////////////////관리자 로그인
        btnLogin2.setOnClickListener {
            var enterId = edtId.text.toString() //입력된 id 문자열로 저장
            var enterPwd = edtPwd.text.toString() //입력된 패스워드 문자열로 저장

            database.getReference(enterId).addValueEventListener(getData)//서버의 enterId 테이블에서 정보를 받아옴

            if (enterId == "" || enterPwd == "") { //입력된 아이디와 비밀번호가 둘중 하나라도 공백이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("아이디/비밀번호를 입력해주세요")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            } else { //입력된 아이디와 비밀번호가 공백이 아니면
                Handler().postDelayed(Runnable { //서버에서값 가져오는것 기다리는 시간 주기
                    if (enterId == getId && enterPwd == getPwd) { //입력된 아이디 비밀번호와 서버의 db에서 검색해 받아온 값이 같은지 판단
                        if (getAuth == "1" || getAuth == "2") { //받아온 학생의 권한이 1(학생회) 2(교직원)일 경우
                            if (getAuth == "1") { //권한이 1일경우 학생회 로그인 텍스트 메시지
                                Toast.makeText(this, "관리자 로그인됨 : 학생회", Toast.LENGTH_SHORT).show()
                            } else if (getAuth == "2") //권한이 2일 경우 교직원 로그인 텍스트 메시지
                                Toast.makeText(this, "관리자 로그인됨 : 교직원", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, admin_login_first::class.java)


                            intent.putExtra("getId", getId)  //서버에서 받아온 값 다음 액티비티로 넘겨주기
                            intent.putExtra("getName", getName)
                            intent.putExtra("getMajor", getMajor)
                            intent.putExtra("getDepart", getDepart)
                            intent.putExtra("getAuth",getAuth)
                            startActivity(intent)       ///관리자 로그인 첫 페이지 실행

                        } else {   //입력된 아이디 비밀번호와 서버의 db에서 검색해 받아온 값이 같지만 권한이 0(일반학생)일 경우 대화상자
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("알림!")
                            builder.setMessage("관리자 권한이 없습니다.")
                            builder.setIcon(R.drawable.symbol)
                            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            builder.show()
                        }
                    } else { //입력된 아이디 비밀번호와 서버의 db에서 검색해 받아온 값이 둘 중 하나라도 다르면 대화상자
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("알림!")
                        builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                        builder.setIcon(R.drawable.symbol)
                        builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        })
                        builder.show()

                    }
                }, 1200)

            }
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼을 시간내에 2번 연속 눌렀을 때 어플 종료기능
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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




