package com.example.myapplication.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GasStore.db";
    private static DatabaseHelper databaseHelper;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + "users" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL, ADDRESS TEXT)");

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + "products" + "("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name" + " TEXT NOT NULL,"
                + "price" + " REAL NOT NULL,"
                + "brand" + " TEXT,"
                + "imgUrl" + " TEXT,"
                + "amount" + " INTEGER,"
                + "capacity" + " TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + "orders" + "("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idProduct" + " INTEGER NOT NULL,"
                + "nameCustomer" + " TEXT NOT NULL,"
                + "phoneCustomer" + " TEXT NOT NULL,"
                + "addressCustomer" + " TEXT NOT NULL,"
                + "status" + " TEXT NOT NULL,"
                + "timeOrder" + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "amount" + " INTEGER NOT NULL)";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "users");
        db.execSQL("DROP TABLE IF EXISTS " + "products");
        db.execSQL("DROP TABLE IF EXISTS " + "orders");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}

