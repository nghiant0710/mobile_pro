package com.example.mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private OrderDatabaseHelper db;
    private EditText edtOrderName, edtOrderTotal;
    private Button btnAddOrder, btnUpdateOrder, btnDeleteOrder;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);

        db = new OrderDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        edtOrderName = findViewById(R.id.edtOrderName);
        edtOrderTotal = findViewById(R.id.edtOrderTotal);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnUpdateOrder = findViewById(R.id.btnUpdateOrder);
        btnDeleteOrder = findViewById(R.id.btnDeleteOrder);

        orderList = new ArrayList<>();
        orderList = db.getAllOrders();

        orderAdapter = new OrderAdapter(this, orderList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                selectedOrder = order;
                edtOrderName.setText(selectedOrder.getName());
                edtOrderTotal.setText(String.valueOf(selectedOrder.getTotal()));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderName = edtOrderName.getText().toString().trim();
                double orderTotal = Double.parseDouble(edtOrderTotal.getText().toString().trim());

                Order order = new Order(orderName, orderTotal);
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
                    String orderName = edtOrderName.getText().toString().trim();
                    double orderTotal = Double.parseDouble(edtOrderTotal.getText().toString().trim());

                    selectedOrder.setName(orderName);
                    selectedOrder.setTotal(orderTotal);

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
        edtOrderName.setText("");
        edtOrderTotal.setText("");
        selectedOrder = null;
    }
}
