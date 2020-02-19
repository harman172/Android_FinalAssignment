package com.example.harman_fa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PhonebookDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "person";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";

    public DatabaseHelper(@Nullable Context context) {
        //cursorFactory is when you are using your own custom cursor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " varchar(200) NOT NULL, " +
                COLUMN_LAST_NAME + " varchar(200) NOT NULL, " +
                COLUMN_PHONE + " varchar(200) NOT NULL, " +
                COLUMN_ADDRESS + " varchar(200) NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addPerson(String fName, String lName, String phone, String address){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, fName);
        cv.put(COLUMN_LAST_NAME, lName);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_ADDRESS, address);

        return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;
    }

    Cursor getAllPersons(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    boolean updatePerson(int id, String fName, String lName, String phone, String address){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, fName);
        cv.put(COLUMN_LAST_NAME, lName);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_ADDRESS, address);

        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    boolean deletePerson(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }



}
