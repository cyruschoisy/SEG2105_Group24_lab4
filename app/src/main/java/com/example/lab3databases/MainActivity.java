package com.example.lab3databases;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView productId;
    EditText productName, productPrice;
    Button addBtn, findBtn, deleteBtn;
    ListView productListView;

    ArrayList<String> productList;
    ArrayAdapter adapter;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        // info layout
        productId = findViewById(R.id.productId);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);

        //buttons
        addBtn = findViewById(R.id.addBtn);
        findBtn = findViewById(R.id.findBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        // listview
        productListView = findViewById(R.id.productListView);

        // db handler
        dbHandler = new MyDBHandler(this);

        // button listeners
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                double price = Double.parseDouble(productPrice.getText().toString());
                Product product = new Product(name, price);
                dbHandler.addProduct(product);

                productName.setText("");
                productPrice.setText("");

                Toast.makeText(MainActivity.this, "Add product", Toast.LENGTH_SHORT).show();
                viewProducts();
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                String priceStr = productPrice.getText().toString();
                Cursor cursor;

                if (!name.isEmpty() && !priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    cursor = dbHandler.findProduct(name, price);
                } else if (!name.isEmpty()) {
                    cursor = dbHandler.findProduct(name);
                } else if (!priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    cursor = dbHandler.findProduct(price);
                } else {
                    Toast.makeText(MainActivity.this, "Cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder results = new StringBuilder();
                    results.append("Found ").append(cursor.getCount()).append(" product(s):\n");
                    while (cursor.moveToNext()) {
                        String foundName = cursor.getString(1);
                        String foundPrice = cursor.getString(2);
                        results.append("- ").append(foundName).append(" ($").append(foundPrice).append(")\n");
                    }
                    Toast.makeText(MainActivity.this, results.toString().trim(), Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean result = dbHandler.deleteProduct(name);

                if (result) {
                    productId.setText("Product ID");
                    productName.setText("");
                    productPrice.setText("");
                    Toast.makeText(MainActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No product to delete", Toast.LENGTH_SHORT).show();
                }
                viewProducts();
            }
        });
        // db handler
        MyDBHandler dbhandler = new MyDBHandler(this);
        viewProducts();
    }

    private void viewProducts() {
        productList.clear();
        Cursor cursor = dbHandler.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                productList.add(cursor.getString(1) + " (" + cursor.getString(2) + ") ");
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);
    }
}