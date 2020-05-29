package com.example.shoppingassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private Handler mHandler;
    public int mInterval = 300000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductAdapter productAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);


        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (settings.contains("Products")) {
            settings.getStringSet("Products", urls);
            Log.println(Log.ASSERT, "List", urls.toString());

            if (!urls.isEmpty()) {
                for (String url : urls) {
                    MyRequest myRequest = new MyRequest();
                    Log.println(Log.ASSERT, "aga", url);
                    myRequest.execute(url);
                    try {
                        products.add(myRequest.get(10, TimeUnit.SECONDS));
                        recyclerView.getAdapter().notifyDataSetChanged();

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }


        }

        mHandler = new Handler();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                stopRepeatingTask();
                startActivityForResult(intent, 1);
                Log.println(Log.ASSERT, "execute", "дошло");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.putExtra("Hint",mInterval/60000);
            Log.println(Log.ASSERT, "result", Integer.toString(mInterval/60000));
            startActivityForResult(intent, 2);

        }
        return true;
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

                urls.add(result);
                SharedPreferences.Editor editor = settings.edit();
                editor.putStringSet("Products", urls);
                editor.apply();
                Log.println(Log.ASSERT, "settings", urls.toString());
                myRequest.execute(result);
                try {
                    products.add(myRequest.get(5, TimeUnit.SECONDS));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    startRepeatingTask();
                    myRequest.cancel(true);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            }else{
                if (!products.isEmpty()){
                    startRepeatingTask();
                }
            }
        } else {
            if (resultCode == RESULT_OK) {
                mInterval = data.getIntExtra("Time",1);
                Log.println(Log.ASSERT, "result", Integer.toString(mInterval));
                mInterval*=60000;
                startRepeatingTask();


            }
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {

            try {

                for (Product product : products) {
                    MyRequest reRequest = new MyRequest();
                    reRequest.execute(product.url);
                    products.set(products.indexOf(product), reRequest.get(10, TimeUnit.SECONDS));
                    recyclerView.getAdapter().notifyDataSetChanged();

                }

            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);

    }

}
