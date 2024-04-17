package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.ProductTable;
import com.example.myapplication.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ManageProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;
    private List<Product> productList;
    private ProductTable db;
    private EditText edtProductName, edtProductPrice, edtProductBrand, edtProductImgUrl, edtProductAmount, edtProductCapacity;
    private Button btnAdd, btnUpdate, btnDelete,btnToggleForm,btnBack;
    private Product selectedProduct;
    private LinearLayout formLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        db = new ProductTable(databaseHelper);

        recyclerView = findViewById(R.id.recyclerView);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductBrand = findViewById(R.id.edtProductBrand); // Assuming you have this EditText in your layout
        edtProductImgUrl = findViewById(R.id.edtProductImgUrl); // Assuming you have this EditText in your layout
        edtProductAmount = findViewById(R.id.edtProductAmount); // Assuming you have this EditText in your layout
        edtProductCapacity = findViewById(R.id.edtProductCapacity); // Assuming you have this EditText in your layout
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnToggleForm=findViewById(R.id.btnToggleForm);
        btnBack=findViewById(R.id.btnBack);
        productList = new ArrayList<>();
        productList = db.getAllProducts();

        productAdapter = new ProductAdapter(this, productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                selectedProduct = product;
                edtProductName.setText(selectedProduct.getName());
                edtProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
                edtProductBrand.setText(selectedProduct.getBrand());
                edtProductImgUrl.setText(selectedProduct.getImgUrl());
                edtProductAmount.setText(String.valueOf(selectedProduct.getAmount()));
                edtProductCapacity.setText(selectedProduct.getCapacity());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);
        formLayout = findViewById(R.id.formLayout); // Khởi tạo formLayout
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageProductsActivity.this, DashboardActivity.class);
                startActivity(intent);        }
        });
        btnToggleForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formLayout.getVisibility() == View.VISIBLE) {
                    formLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);// Ẩn formLayout nếu nó đang hiển thị
                } else {
                    formLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);// Hiển thị formLayout nếu nó đang ẩn
                }            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = edtProductName.getText().toString().trim();
                double productPrice = Double.parseDouble(edtProductPrice.getText().toString().trim());
                String productBrand = edtProductBrand.getText().toString().trim();
                String productImgUrl = edtProductImgUrl.getText().toString().trim();
                int productAmount = Integer.parseInt(edtProductAmount.getText().toString().trim());
                String productCapacity = edtProductCapacity.getText().toString().trim();

                Product product = new Product(productName, productPrice, productBrand, productImgUrl, productAmount, productCapacity);
                long result = db.addProduct(product);

                if (result > 0) {
                    Toast.makeText(ManageProductsActivity.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                    refreshList();
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Failed to add product!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProduct != null) {
                    String productName = edtProductName.getText().toString().trim();
                    double productPrice = Double.parseDouble(edtProductPrice.getText().toString().trim());
                    String productBrand = edtProductBrand.getText().toString().trim();
                    String productImgUrl = edtProductImgUrl.getText().toString().trim();
                    int productAmount = Integer.parseInt(edtProductAmount.getText().toString().trim());
                    String productCapacity = edtProductCapacity.getText().toString().trim();

                    selectedProduct.setName(productName);
                    selectedProduct.setPrice(productPrice);
                    selectedProduct.setBrand(productBrand);
                    selectedProduct.setImgUrl(productImgUrl);
                    selectedProduct.setAmount(productAmount);
                    selectedProduct.setCapacity(productCapacity);

                    int result = db.updateProduct(selectedProduct);

                    if (result > 0) {
                        Toast.makeText(ManageProductsActivity.this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                        refreshList();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Failed to update product!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Please select a product to update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProduct != null) {
                    int result = db.deleteProduct(selectedProduct.getId());

                    if (result > 0) {
                        Toast.makeText(ManageProductsActivity.this, "Product deleted successfully!", Toast.LENGTH_SHORT).show();
                        refreshList();
                        clearFields();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Failed to delete product!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Please select a product to delete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshList() {
        productList.clear();
        productList.addAll(db.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        edtProductName.setText("");
        edtProductPrice.setText("");
        edtProductBrand.setText("");
        edtProductImgUrl.setText("");
        edtProductAmount.setText("");
        edtProductCapacity.setText("");
        selectedProduct = null;
    }
}
