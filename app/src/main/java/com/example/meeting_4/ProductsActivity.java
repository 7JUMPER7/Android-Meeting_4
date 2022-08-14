package com.example.meeting_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meeting_4.DB.DatabaseProductsAdapter;
import com.example.meeting_4.Models.ProductEntity;

public class ProductsActivity extends AppCompatActivity {
    private EditText nameBox;
    private EditText countBox;
    private EditText priceBox;
    private Button delButton;
    private DatabaseProductsAdapter adapter;
    private long productId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        nameBox = findViewById(R.id.name);
        countBox = findViewById(R.id.count);
        priceBox = findViewById(R.id.price);

        delButton=findViewById(R.id.deleteButton);
        adapter=new DatabaseProductsAdapter(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getLong("id");
        }
        if (productId > 0) {
            adapter.open();
            ProductEntity product = adapter.getEntity(productId);
            nameBox.setText(product.getName());
            countBox.setText(String.valueOf(product.getCount()));
            priceBox.setText(String.valueOf(product.getPrice()));
            adapter.close();
        } else {
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view) {
        String name = nameBox.getText().toString();
        int count = Integer.parseInt(countBox.getText().toString());
        Double price = Double.parseDouble(priceBox.getText().toString());
        ProductEntity product = new ProductEntity(productId, name, count, price);

        adapter.open();
        if (productId > 0) {
            adapter.update(product);
        } else {
            adapter.insert(product);
        }
        adapter.close();
        goToHomeActivity();
    }

    public void delete(View view) {
        adapter.open();
        adapter.delete(productId);
        adapter.close();
        goToHomeActivity();
    }

    private void goToHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}