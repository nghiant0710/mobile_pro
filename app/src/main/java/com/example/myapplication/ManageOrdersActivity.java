package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.OrderAdapter;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.OrderTable;
import com.example.myapplication.Model.Order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ManageOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private DatabaseHelper databaseHelper;
    private OrderTable db;
    private EditText edtIdProduct, edtNameCustomer, edtPhoneCustomer, edtAddressCustomer, edtStatus, edtAmount;
    private Button btnAddOrder, btnUpdateOrder, btnDeleteOrder;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        db = new OrderTable(databaseHelper);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        edtIdProduct = findViewById(R.id.edtIdProduct);
        edtNameCustomer = findViewById(R.id.edtNameCustomer);
        edtPhoneCustomer = findViewById(R.id.edtPhoneCustomer);
        edtAddressCustomer = findViewById(R.id.edtAddressCustomer);
        edtStatus = findViewById(R.id.edtStatus);
        edtAmount = findViewById(R.id.edtAmount);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnUpdateOrder = findViewById(R.id.btnUpdateOrder);
        btnDeleteOrder = findViewById(R.id.btnDeleteOrder);

        orderList = new ArrayList<>();
        orderList = db.getAllOrders();

        orderAdapter = new OrderAdapter(this, orderList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                selectedOrder = order;
                edtIdProduct.setText(String.valueOf(selectedOrder.getIdProduct()));
                edtNameCustomer.setText(selectedOrder.getNameCustomer());
                edtPhoneCustomer.setText(selectedOrder.getPhoneCustomer());
                edtAddressCustomer.setText(selectedOrder.getAddressCustomer());
                edtStatus.setText(selectedOrder.getStatus());
                edtAmount.setText(String.valueOf(selectedOrder.getAmount()));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idProduct = Integer.parseInt(edtIdProduct.getText().toString().trim());
                String nameCustomer = edtNameCustomer.getText().toString().trim();
                String phoneCustomer = edtPhoneCustomer.getText().toString().trim();
                String addressCustomer = edtAddressCustomer.getText().toString().trim();
                String status = edtStatus.getText().toString().trim();
                Timestamp timeOrder = new Timestamp(System.currentTimeMillis());
                int amount = Integer.parseInt(edtAmount.getText().toString().trim());

                Order order = new Order(idProduct, nameCustomer, phoneCustomer, addressCustomer, status, timeOrder, amount);
                long result = db.addOrder(order);

                if (result > 0) {
                    Toast.makeText(ManageOrdersActivity.this, "Order added successfully!", Toast.LENGTH_SHORT).show();
                    refreshOrderList();
                } else {
                    Toast.makeText(ManageOrdersActivity.this, "Failed to add order!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    int idProduct = Integer.parseInt(edtIdProduct.getText().toString().trim());
                    String nameCustomer = edtNameCustomer.getText().toString().trim();
                    String phoneCustomer = edtPhoneCustomer.getText().toString().trim();
                    String addressCustomer = edtAddressCustomer.getText().toString().trim();
                    String status = edtStatus.getText().toString().trim();
                    int amount = Integer.parseInt(edtAmount.getText().toString().trim());

                    selectedOrder.setIdProduct(idProduct);
                    selectedOrder.setNameCustomer(nameCustomer);
                    selectedOrder.setPhoneCustomer(phoneCustomer);
                    selectedOrder.setAddressCustomer(addressCustomer);
                    selectedOrder.setStatus(status);
                    selectedOrder.setAmount(amount);

                    int result = db.updateOrder(selectedOrder);

                    if (result > 0) {
                        Toast.makeText(ManageOrdersActivity.this, "Order updated successfully!", Toast.LENGTH_SHORT).show();
                        refreshOrderList();
                    } else {
                        Toast.makeText(ManageOrdersActivity.this, "Failed to update order!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageOrdersActivity.this, "Please select an order to update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    int result = db.deleteOrder(selectedOrder.getId());

                    if (result > 0) {
                        Toast.makeText(ManageOrdersActivity.this, "Order deleted successfully!", Toast.LENGTH_SHORT).show();
                        refreshOrderList();
                        clearFields();
                    } else {
                        Toast.makeText(ManageOrdersActivity.this, "Failed to delete order!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageOrdersActivity.this, "Please select an order to delete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshOrderList() {
        orderList.clear();
        orderList.addAll(db.getAllOrders());
        orderAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        edtIdProduct.setText("");
        edtNameCustomer.setText("");
        edtPhoneCustomer.setText("");
        edtAddressCustomer.setText("");
        edtStatus.setText("");
        edtAmount.setText("");
        selectedOrder = null;
    }
}
