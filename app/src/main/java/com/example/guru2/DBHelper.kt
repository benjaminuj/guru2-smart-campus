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
        private val DB_NAME = "UserDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "private_info"
        private val ID = "id"
        private val NAME = "name"
        private val AUTH = "auth"
        private val DEPART = "depart"
        private val MAJOR = "major"
        private val PASSWORD = "password"
        private val IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable_privateinfo =
            "CREATE TABLE $TABLE_NAME" +
                    "($ID Integer PRIMARY KEY," + "$PASSWORD TEXT," +
                    "$NAME TEXT," + "$AUTH INTEGER," + "$DEPART TEXT," + "$MAJOR TEXT," + "$IMAGE BLOB)"
        val createTable_access =
            "CREATE TABLE access" + "(time TEXT, spot TEXt, id TEXT, name TEXT, auth INTEGER, depart TEXT, major TEXT, image BLOB)"
        db?.execSQL(createTable_privateinfo)
        db?.execSQL(createTable_access)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}

