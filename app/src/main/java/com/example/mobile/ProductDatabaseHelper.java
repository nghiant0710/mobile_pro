package com.example.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products_db";
    private static final String TABLE_PRODUCTS = "products";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " REAL" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Thêm sản phẩm mới
    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result;
    }

    // Cập nhật sản phẩm
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());

        int result = db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
        return result;
    }

    // Xóa sản phẩm
    public int deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // Lấy thông tin sản phẩm dựa trên ID
    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PRICE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)));

        cursor.close();
        db.close();
        return product;
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setPrice(Double.parseDouble(cursor.getString(2)));

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
}
