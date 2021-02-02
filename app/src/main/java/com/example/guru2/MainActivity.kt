package com.example.guru2


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

     var myHelper : DBHelper? = null
    lateinit var sqlDB : SQLiteDatabase
    lateinit var btnLogin1 : Button
    lateinit var btnLogin2 : Button
    lateinit var edtId : EditText
    lateinit var edtPwd :  EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin1 = findViewById(R.id.btnLogin1) //로그인
        btnLogin2 = findViewById(R.id.btnLogin2) //관리자로그인
        edtId = findViewById(R.id.edtId)        //입력된 id
        edtPwd = findViewById(R.id.edtPwd)      //입력된 비밀번호
        myHelper = DBHelper(this)


        ////////로그인
        btnLogin1.setOnClickListener {
            var getId = edtId.text.toString() //입력된 id 문자열로 저장
            var getPwd = edtPwd.text.toString() //입력된 패스워드 문자열로 저장

            if(getId =="" || getPwd ==""){ //둘중 하나라도 공백이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("아이디/비밀번호를 입력해주세요")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }
            else { //공백이 아니면
                sqlDB = myHelper.readableDatabase

                var cursor: Cursor =
                    sqlDB.rawQuery("SELECT * FROM db1 WHERE gNumber = '$getId';", null)
                if (!cursor.moveToFirst()){ //첫번째 레코드가 비어있을 경우 대화상자
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("알림!")
                    builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                    builder.setIcon(R.drawable.symbol)
                    builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    })
                    builder.show()

                    cursor?.close()
                    sqlDB.close()
                } else { //첫 레코드가 비어있지 않을경우
                        if (getId == cursor.getString(0) &&
                                getPwd == cursor.getString(2)) { //입력된 아이디와 레코드[0] 값, 비밀번호와 첫 레코드[2]값이 둘다 같은지 비교

                            var getAuth= cursor.getString(3)
                            var getName = cursor.getString(1)

                            Toast.makeText(this, "로그인됨", Toast.LENGTH_SHORT).show() //같으면 로그인됨 토스트 메시지

                            var intent = Intent(this,idcard::class.java) //다음 액티비티로 데이터 넘김
                            intent.putExtra("getId",getId)
                            intent.putExtra("getPwd",getPwd)
                            intent.putExtra("getAuth",getAuth)
                            intent.putExtra("getName",getName)
                            startActivity(intent)


                        } else { //아이디와 비밀번호 둘중하나라도 다르면 대화상자
                            val builder = AlertDialog.Builder(this)

                            builder.setTitle("알림!")
                            builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                            builder.setIcon(R.drawable.symbol)
                            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            builder.show()
                        }
                    }
                    cursor.close()
                    sqlDB.close()

            }

        }

        ///////관리자 로그인
        btnLogin2.setOnClickListener {
            var getId = edtId.text.toString() //입력된 id 문자열로 저장
            var getPwd = edtPwd.text.toString() //입력된 패스워드 문자열로 저장

            if(getId =="" || getPwd ==""){  //둘중 하나라도 공백이면 대화상자
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("아이디/비밀번호를 입력해주세요")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }
            else { //공백아니면
                sqlDB = myHelper.readableDatabase

                var cursor: Cursor =
                    sqlDB.rawQuery("SELECT * FROM db1 WHERE gNumber = '$getId';", null)


                if (!cursor.moveToFirst()) { //레코드가 비어있으면 대화상자
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("알림!")
                    builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                    builder.setIcon(R.drawable.symbol)
                    builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    })
                    builder.show()

                    cursor?.close()
                    sqlDB.close()
                } else { //비어있지않으면
                        if (getId == cursor.getString(0) && //입력된 아이디와 첫 레코드[0] 값, 비밀번호와 첫 레코드[2]값이 둘다 같은지 비교
                                getPwd == cursor.getString(2))
                        { if (cursor.getString(3) == "1" || cursor.getString(3) == "2") { //둘다 같은경우 권한 확인 1또는 2인지
                                var getAuth = cursor.getString(3)
                                var getName = cursor.getString(1)
                                if (getAuth == "1") { //권한이 1일경우 학생회 로그인 텍스트 메시지
                                    Toast.makeText(this, "관리자 로그인됨 : 학생회", Toast.LENGTH_SHORT).show()
                                } else if (getAuth == "2") //권한이 2일 경우 교직원 로그인 텍스트 메시지
                                    Toast.makeText(this, "관리자 로그인됨 : 교직원", Toast.LENGTH_SHORT).show()
                                var intent = Intent(this, admin_login_first::class.java) //다음 액티비티로
                                intent.putExtra("getId", getId)
                                intent.putExtra("getPwd", getPwd)
                                intent.putExtra("getAuth", getAuth)
                                intent.putExtra("getName", getName)
                                startActivity(intent)
                            } else { //권한이 1또는 2가 아닐경우 권한 없음 대화상자
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("알림!")
                                builder.setMessage("관리자 권한이 없습니다.")
                                builder.setIcon(R.drawable.symbol)
                                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                                })
                                builder.show()
                            }
                        } else{ //입력된 값과 레코드에서 받은 값이 다를경우 대화상자
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("알림!")
                            builder.setMessage("아이디/비밀번호가 일치하지 않거나 회원정보가 존재하지 않습니다.")
                            builder.setIcon(R.drawable.symbol)
                            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            builder.show()
                        }

                    cursor.close()
                    sqlDB.close()
                }
            }

        }

    }

    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "db1", null, 1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE db1 (gNumber CHAR(20) PRIMARY KEY, gName CHAR(20), gPassword CHAR(20), gAuth INTEGER);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS db1 ")
            onCreate(db)
        }

    }
}