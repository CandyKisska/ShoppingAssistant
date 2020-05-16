package com.example.shoppingassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Item> items = new ArrayList<>();
        ItemAdapter titleAdapter = new ItemAdapter(items);
        recyclerView.setAdapter(titleAdapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRequest myRequest = new MyRequest();
                myRequest.execute();

            }
        });

    }

    class MyRequest extends AsyncTask<Void, Void, Void> {

        private TextView output;
        private TextView input;
        private String res;
        private String price;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            output = findViewById(R.id.output);
            input = findViewById(R.id.input);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                res = new EbayRequest().get();
                price= new EbayRequest().price(res);

            }catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            output.setText(res);
            input.setText(price);
        }
    }


}
