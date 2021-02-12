package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class admin_read_card : AppCompatActivity() {

    lateinit var tvId1: TextView
    lateinit var tvMajor1: TextView
    lateinit var tvName1: TextView
    lateinit var tvDepart1: TextView
    lateinit var tvParticipate1: TextView
    lateinit var tvMoney1: TextView

    lateinit var imgProfile: ImageView
    lateinit var btnReceive : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_read_card)
        setTitle("학생 정보")

        tvDepart1 = findViewById(R.id.tvDepart)
        tvMajor1 = findViewById(R.id.tvMajor)
        tvName1 = findViewById(R.id.tvName)
        tvId1 = findViewById(R.id.tvId)
        tvParticipate1 = findViewById(R.id.participate)
        tvMoney1 = findViewById(R.id.money)
        imgProfile = findViewById(R.id.ImgView)
        btnReceive = findViewById(R.id.btnReceive)
        var getDepart: String = ""
        var getName = ""
        var getDue = ""
        var getProfile = ""
        var getReceive = ""
        var getMajor = ""

        var getData = object : ValueEventListener { ///서버에서 데이터 가져오기
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) { //데이터가 변경되면 값을 받음
                getDepart = snapshot.child("depart").value.toString()
                getDue = snapshot.child("due").value.toString()
                getMajor = snapshot.child("major").value.toString()
                getName = snapshot.child("name").value.toString()
                getProfile = snapshot.child("profile").value.toString()
                getReceive = snapshot.child("receive").value.toString()
            }


        }


        val database = FirebaseDatabase.getInstance()
        var getId = intent.getStringExtra("getId").toString() //nfc리더에서 받아온 id(학번)값
        database.getReference(getId).addValueEventListener(getData) //getId 테이블에서 값을 검색해 변경된 데이터 저장


        Handler().postDelayed(Runnable { //서버에서값 가져오는것 기다리는 시간 주기

            tvDepart1.text = getDepart
            tvMajor1.text = getMajor
            tvName1.setText(getName)
            tvId1.setText(getId)

            //db에서 받아온 이미지파일 정보가 없으면 기본 사진을 보여주고 있으면 서버 storage에 저장된 사진을 보여줌
            if(getProfile != "")
                Glide.with(this).load(getProfile).into(this.imgProfile)
            else
                imgProfile.setImageResource(R.drawable.person)

            ////db에서 받아온 getDue(학생회비 납부 여부)값이 0이면 텍스트를 O, 아니면 텍스트를 X로 바꿈
            if(getDue =="0"){
                tvMoney1.text = "O"}
            else{
                tvMoney1.text = "X"}
            ////db에서 받아온 getReceive(행사 수령 여부)값이 0이면 텍스트를 수령, 아니면 텍스트를 미수령으로 바꿈
            if(getReceive =="0")
                tvParticipate1.text = "참여"
            else
                tvParticipate1.text = "미참여"

            //학생회비 납부 여부가 O이고 행사 수령 여부가 미수령이면 버튼 활성화
            btnReceive.isEnabled = getDue=="0" && getReceive=="1"

        }, 1500)



        //버튼을 클릭했을 때
        btnReceive.setOnClickListener {
            database.reference.child(getId).child("receive").setValue("0") //서버 getId 이름의 테이블의 receive 값을 0(수령함)으로 변경
            Toast.makeText(this, "참여로 변경되었습니다", Toast.LENGTH_SHORT).show()
            btnReceive.isEnabled=false

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

