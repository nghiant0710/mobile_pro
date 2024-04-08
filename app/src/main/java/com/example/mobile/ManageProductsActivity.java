package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManageProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private ProductDatabaseHelper db;
    private EditText edtProductName, edtProductPrice;
    private Button btnAdd, btnUpdate, btnDelete;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        db = new ProductDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        productList = new ArrayList<>();
        productList = db.getAllProducts();

        productAdapter = new ProductAdapter(this, productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                selectedProduct = product;
                edtProductName.setText(selectedProduct.getName());
                edtProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = edtProductName.getText().toString().trim();
                double productPrice = Double.parseDouble(edtProductPrice.getText().toString().trim());

                Product product = new Product(productName, productPrice);
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

                    selectedProduct.setName(productName);
                    selectedProduct.setPrice(productPrice);

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
        selectedProduct = null;
    }
}
