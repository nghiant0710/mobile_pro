package com.example.myapplication.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.User;

public class UserTable {

    private final  DatabaseHelper databaseHelper ;
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "PASSWORD";

    private static final String COL_4 = "ADDRESS";
    public UserTable(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }



    public boolean registerUser(User user) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, user.getUsername());
        contentValues.put(COL_3, user.getPassword());
        contentValues.put(COL_4, user.getAddress());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME=? AND PASSWORD=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }
}
