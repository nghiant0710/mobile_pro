package com.example.myapplication.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.Order;
import com.example.myapplication.Model.Product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderTable {

    private final DatabaseHelper databaseHelper;
    private static final String TABLE_ORDERS = "orders";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_PRODUCT = "idProduct";
    private static final String KEY_NAME_CUSTOMER = "nameCustomer";
    private static final String KEY_PHONE_CUSTOMER = "phoneCustomer";
    private static final String KEY_ADDRESS_CUSTOMER = "addressCustomer";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIME_ORDER = "timeOrder";
    private static final String KEY_AMOUNT = "amount";

    public OrderTable(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // Thêm đơn hàng mới
    public long addOrder(Order order) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ProductTable productDatabase = new ProductTable(databaseHelper);
        Product product = productDatabase.getProductById(order.getIdProduct());
        if(product == null){
            db.close();
            return -1;
        }
        else if (product.getAmount() < order.getAmount()) {
            db.close();
            return -2;
        }
        else{
            int amountNew = product.getAmount() - order.getAmount();
            product.setAmount(amountNew);
            productDatabase.updateProduct(product);
            ContentValues values = new ContentValues();
            values.put(KEY_ID_PRODUCT, order.getIdProduct());
            values.put(KEY_NAME_CUSTOMER, order.getNameCustomer());
            values.put(KEY_PHONE_CUSTOMER, order.getPhoneCustomer());
            values.put(KEY_ADDRESS_CUSTOMER, order.getAddressCustomer());
            values.put(KEY_STATUS, order.getStatus());
            values.put(KEY_TIME_ORDER, String.valueOf(order.getTimeOrder()));
            values.put(KEY_AMOUNT, order.getAmount());

            long result = db.insert(TABLE_ORDERS, null, values);
            db.close();
            return result;
        }
    }

    // Cập nhật đơn hàng
    public int updateOrder(Order order) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Order order1 = getOrderById(order.getId());
        int amountPrev = order1.getAmount();
        if(amountPrev > order.getAmount()){
            int delta = amountPrev - order.getAmount();
            ProductTable productDatabase = new ProductTable(databaseHelper);
            Product product = productDatabase.getProductById(order.getIdProduct());
            Product product1 =productDatabase.getProductById(order1.getIdProduct());
            if(product == null){
                db.close();
                return -1;
            }
            else if (product.getAmount() < order.getAmount()){
                db.close();
                return -2;
            }
            else if (order.getIdProduct()==order1.getIdProduct()){
                product.setAmount(product.getAmount() + delta);
                productDatabase.updateProduct(product);

            }
            else {
                product.setAmount(product.getAmount()-order.getAmount());
                productDatabase.updateProduct(product);
                product1.setAmount(product1.getAmount()+order1.getAmount());
                productDatabase.updateProduct(product1);;
            }
            ContentValues values = new ContentValues();
            values.put(KEY_ID_PRODUCT, order.getIdProduct());
            values.put(KEY_NAME_CUSTOMER, order.getNameCustomer());
            values.put(KEY_PHONE_CUSTOMER, order.getPhoneCustomer());
            values.put(KEY_ADDRESS_CUSTOMER, order.getAddressCustomer());
            values.put(KEY_STATUS, order.getStatus());
            values.put(KEY_TIME_ORDER, String.valueOf(order.getTimeOrder()));
            values.put(KEY_AMOUNT, order.getAmount());

            int result = db.update(TABLE_ORDERS, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(order.getId())});
            db.close();
            return result;
        }
        else {
            int delta = order.getAmount() - amountPrev ;
            ProductTable productDatabase = new ProductTable(databaseHelper);
            Product product = productDatabase.getProductById(order.getIdProduct());
            Product product1 =productDatabase.getProductById(order1.getIdProduct());
            if(product == null){
                db.close();
                return -1;
            }
            else if (product.getAmount() < order.getAmount()){
                db.close();
                return -2;
            }
            else if (order.getIdProduct()==order1.getIdProduct()){
                product.setAmount(product.getAmount() - delta);
                productDatabase.updateProduct(product);

            }
            else {
                product.setAmount(product.getAmount()-order.getAmount());
                productDatabase.updateProduct(product);
                product1.setAmount(product1.getAmount()+order1.getAmount());
                productDatabase.updateProduct(product1);;
            }
            ContentValues values = new ContentValues();
            values.put(KEY_ID_PRODUCT, order.getIdProduct());
            values.put(KEY_NAME_CUSTOMER, order.getNameCustomer());
            values.put(KEY_PHONE_CUSTOMER, order.getPhoneCustomer());
            values.put(KEY_ADDRESS_CUSTOMER, order.getAddressCustomer());
            values.put(KEY_STATUS, order.getStatus());
            values.put(KEY_TIME_ORDER, String.valueOf(order.getTimeOrder()));
            values.put(KEY_AMOUNT, order.getAmount());

            int result = db.update(TABLE_ORDERS, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(order.getId())});
            db.close();
            return result;
        }

    }

    // Xóa đơn hàng
    public int deleteOrder(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Order order = getOrderById(id);
        ProductTable productDatabase = new ProductTable(databaseHelper);
        Product product = productDatabase.getProductById(order.getIdProduct());
        if(product == null){
            db.close();
            return -1;
        }
        else{
            product.setAmount(product.getAmount() + order.getAmount());
            productDatabase.updateProduct(product);
            int result = db.delete(TABLE_ORDERS, KEY_ID + " = ?",
                    new String[]{String.valueOf(id)});
            db.close();
            return result;
        }

    }

    // Lấy thông tin đơn hàng dựa trên ID
    public Order getOrderById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, new String[]{KEY_ID,
                        KEY_ID_PRODUCT, KEY_NAME_CUSTOMER, KEY_PHONE_CUSTOMER,
                        KEY_ADDRESS_CUSTOMER, KEY_STATUS, KEY_TIME_ORDER,
                        KEY_AMOUNT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Order order = new Order(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                Timestamp.valueOf(cursor.getString(6)), Integer.parseInt(cursor.getString(7)));

        cursor.close();
        db.close();
        return order;
    }

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(Integer.parseInt(cursor.getString(0)));
                order.setIdProduct(Integer.parseInt(cursor.getString(1)));
                order.setNameCustomer(cursor.getString(2));
                order.setPhoneCustomer(cursor.getString(3));
                order.setAddressCustomer(cursor.getString(4));
                order.setStatus(cursor.getString(5));
                order.setTimeOrder(Timestamp.valueOf(cursor.getString(6)));
                order.setAmount(Integer.parseInt(cursor.getString(7)));

                orderList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return orderList;
    }
}
