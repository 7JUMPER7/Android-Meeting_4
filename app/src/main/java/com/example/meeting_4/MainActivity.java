package com.example.meeting_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.meeting_4.DB.DatabaseProductsAdapter;
import com.example.meeting_4.Models.ProductEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView products;
    ArrayAdapter<ProductEntity> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products=findViewById(R.id.products);
        products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductEntity user=arrayAdapter.getItem(position);
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                    intent.putExtra("id",user.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseProductsAdapter adapter = new DatabaseProductsAdapter(this);
        adapter.open();

        List<ProductEntity> entities = adapter.getEntities();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entities);
        products.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void addProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
        startActivity(intent);
    }
}