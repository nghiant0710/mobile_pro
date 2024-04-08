package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    private Button btnManageOrders, btnManageProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnManageOrders = findViewById(R.id.btnManageOrders);
        btnManageProducts = findViewById(R.id.btnManageProducts);

        btnManageOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình quản lý đơn hàng
                Intent intent = new Intent(DashboardActivity.this, ManageOrdersActivity.class);
                startActivity(intent);
            }
        });

        btnManageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình quản lý sản phẩm
                Intent intent = new Intent(DashboardActivity.this, ManageProductsActivity.class);
                startActivity(intent);
            }
        });
    }
}
