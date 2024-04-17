package com.example.myapplication.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductTable {

    private final DatabaseHelper databaseHelper;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_IMGURL = "imgUrl";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CAPACITY = "capacity";

    private static final String TABLE_PRODUCTS = "products";

    public ProductTable(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // Thêm sản phẩm mới
    public long addProduct(Product product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_BRAND, product.getBrand());
        values.put(KEY_IMGURL, product.getImgUrl());
        values.put(KEY_AMOUNT, product.getAmount());
        values.put(KEY_CAPACITY, product.getCapacity());

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result;
    }

    // Cập nhật sản phẩm
    public int updateProduct(Product product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_BRAND, product.getBrand());
        values.put(KEY_IMGURL, product.getImgUrl());
        values.put(KEY_AMOUNT, product.getAmount());
        values.put(KEY_CAPACITY, product.getCapacity());

        int result = db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
        return result;
    }

    // Xóa sản phẩm
    public int deleteProduct(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // Lấy thông tin sản phẩm dựa trên ID
    public Product getProductById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{KEY_ID, KEY_NAME,
                        KEY_PRICE, KEY_BRAND, KEY_IMGURL, KEY_AMOUNT, KEY_CAPACITY},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)),
                cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)),
                cursor.getString(6));

        cursor.close();
        db.close();
        return product;
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setName(cursor.getString(1));
                product.setPrice(Double.parseDouble(cursor.getString(2)));
                product.setBrand(cursor.getString(3));
                product.setImgUrl(cursor.getString(4));
                product.setAmount(Integer.parseInt(cursor.getString(5)));
                product.setCapacity(cursor.getString(6));

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
}
