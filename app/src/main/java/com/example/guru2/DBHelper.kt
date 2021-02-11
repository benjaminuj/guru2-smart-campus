package com.example.guru2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Blob

class DBHelper(context: Context)
    :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_NAME = "RecordDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "entry"
        private val DATE = "date"
        private val TIME = "time"
        private val NAME = "name"
        private val ID = "id"
        private val MAJOR = "major"
        private val SPOT = "spot"
        private val LECTURE = "lecture"
        private val PROFESSOR = "professor"
        private val PERIOD = "period"
        private val DAYOFWEEK = "dayOfWeek"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // entry 테이블 생성
        val createTable_entry =
            "CREATE TABLE $TABLE_NAME" +
                    "($DATE TEXT, $TIME TEXT," +
                    "$NAME TEXT, $ID INTEGER, $MAJOR TEXT, $SPOT TEXT)"

        // lecture information 테이블 생성
        val createTable_lec_info =
            "CREATE TABLE lec_info" +
                    "($PROFESSOR TEXT," + "$SPOT TEXT," + "$LECTURE TEXT," + "$PERIOD INTEGER," + "$DAYOFWEEK TEXT)"

        db?.execSQL(createTable_entry)
        db?.execSQL(createTable_lec_info)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}

