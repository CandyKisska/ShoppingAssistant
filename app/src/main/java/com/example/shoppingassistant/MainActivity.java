package com.example.shoppingassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static final String APP_PREFERENCES = "settings";
    SharedPreferences settings;
    ArrayList<Product> products = new ArrayList<>();
    HashSet<String> urls = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);


        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (settings.contains("Products")) {
            settings.getStringSet("Products", urls);
            Log.println(Log.ASSERT, "List", urls.toString());
            MyRequest myRequest = new MyRequest();
            if (!urls.isEmpty()) {
                for (String url : urls) {
                    Log.println(Log.ASSERT, "aga", url);
                    myRequest.execute(url);
                    try {
                        products.add(myRequest.get(5, TimeUnit.SECONDS));
                        recyclerView.getAdapter().notifyDataSetChanged();

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }


        }


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
            urls.add(result);
            SharedPreferences.Editor editor = settings.edit();
            editor.putStringSet("Products", urls);
            editor.apply();
            Log.println(Log.ASSERT, "settings", urls.toString());
            myRequest.execute(result);
            try {
                products.add(myRequest.get(5, TimeUnit.SECONDS));
             //   reRequest.run(products,recyclerView);
                recyclerView.getAdapter().notifyDataSetChanged();

            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }

    }

}
