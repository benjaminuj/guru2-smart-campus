package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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

            override fun onDataChange(snapshot: DataSnapshot) {
                getDepart = snapshot.child("depart").value.toString()
                getDue = snapshot.child("due").value.toString()
                getMajor = snapshot.child("major").value.toString()
                getName = snapshot.child("name").value.toString()
                getProfile = snapshot.child("profile").value.toString()
                getReceive = snapshot.child("receive").value.toString()
            }


        }


        val database = FirebaseDatabase.getInstance()
        var getId = intent.getStringExtra("getId").toString()
        database.getReference(getId).addValueEventListener(getData)


        Handler().postDelayed(Runnable {

            tvDepart1.text = getDepart
            tvMajor1.text = getMajor
            tvName1.setText(getName)
            tvId1.setText(getId)
            if(getProfile != "")
                Glide.with(this).load(getProfile).into(this.imgProfile)
            else
                imgProfile.setImageResource(R.drawable.person)
            if(getDue =="0"){
                tvMoney1.text = "O"}
            else{
                tvMoney1.text = "X"}
            if(getReceive =="0")
                tvParticipate1.text = "수령"
            else
                tvParticipate1.text = "미수령"


            btnReceive.isEnabled = getDue=="0" && getReceive=="1"

        }, 1500)




        btnReceive.setOnClickListener {
            database.reference.child(getId).child("receive").setValue("0")
            Toast.makeText(this, "수령으로 변경되었습니다", Toast.LENGTH_SHORT).show()
            btnReceive.isEnabled=false

        }

    }

        override fun onBackPressed() {

            var getReaderId = intent.getStringExtra("getReaderId").toString()
            var getReaderPwd = intent.getStringExtra("getReaderPwd").toString()
            var getReaderAuth = intent.getStringExtra("getReaderAuth").toString()
            var getReaderName = intent.getStringExtra("getReaderName").toString()
            var getReaderDepart = intent.getStringExtra("getReaderDepart").toString()
            var getReaderMajor = intent.getStringExtra("getReaderMajor").toString()
            var getReaderProfile = intent.getStringExtra("getReaderProfile").toString()
            var getReaderDue = intent.getStringExtra("getReaderDue").toString()
            var getReaderReceive = intent.getStringExtra("getReaderReceive").toString()

            val intent = Intent(this, nfc_reader::class.java)
            intent.putExtra("getReaderId", getReaderId)
            intent.putExtra("getReaderPwd", getReaderPwd)
            intent.putExtra("getReaderAuth", getReaderAuth)
            intent.putExtra("getReaderName", getReaderName)
            intent.putExtra("getReaderMajor", getReaderMajor)
            intent.putExtra("getReaderDepart", getReaderDepart)
            intent.putExtra("getReaderProfile", getReaderProfile)
            intent.putExtra("getReaderDue",getReaderDue)
            intent.putExtra("getReaderReceive",getReaderReceive)
            startActivity(intent)
        }


    }

