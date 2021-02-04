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
        val createTable =
            "CREATE TABLE $TABLE_NAME" +
                    "($ID Integer PRIMARY KEY," + "$PASSWORD TEXT,"+
                    "$NAME TEXT," + "$AUTH INTEGER," +"$DEPART TEXT," + "$MAJOR TEXT," +"$IMAGE BLOB)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}


    fun getAllUser() : String {

        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var id : String =""
        var name :String = ""
        var password :String= ""
        var auth : String =""
        var depart : String =""
        var major : String =""
        var image : Blob
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex(ID))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    auth = cursor.getString(cursor.getColumnIndex(AUTH))
                    depart = cursor.getString(cursor.getColumnIndex(DEPART))
                    major = cursor.getString(cursor.getColumnIndex(MAJOR))
                    //image = cursor.getBlob(Int)



                    allUser = "$allUser \n $id $firstName $lastName"

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }

}

private fun Cursor.getBlob(int: Int.Companion): ByteArray? {

}
