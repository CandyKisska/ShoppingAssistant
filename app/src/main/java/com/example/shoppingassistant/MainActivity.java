package com.example.shoppingassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductAdapter titleAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(titleAdapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                startActivityForResult(intent, 1);
                Log.println(Log.ASSERT, "execute", "дошло");

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyRequest myRequest = new MyRequest();
        String result = "";
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                result = data.getStringExtra("result");
                Log.println(Log.ASSERT, "result", "result");
            }

            myRequest.execute(result.split(" "));
            try {
                products.add(myRequest.get(5, TimeUnit.SECONDS));
                recyclerView.getAdapter().notifyDataSetChanged();

            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }

    }

}
