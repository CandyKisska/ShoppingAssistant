package com.example.shoppingassistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class ProductActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                //Spinner spinner = findViewById(R.id.spinner);
                Log.println(Log.ASSERT, "Product", "внутри");
                final EditText href = findViewById(R.id.href);
                final TextView message = findViewById(R.id.message);

                try {
                    result = makeRequest(href.getText().toString());
                    Log.println(Log.ASSERT, "Add", result);
                    message.setText("✔ Товар успешно добавлен");
                    message.setTextColor(Color.GREEN);
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    message.setText("✖ Ошибка. Неверная ссылка");
                    message.setTextColor(Color.RED);

                }


            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        //finish();
    }


    public String makeRequest(String url) throws Exception {
        String itemId = "";
        String regex = "https://www.ebay.com/itm/[A-Za-z0-9-]+/[0-9]+[?]+[/A-Za-z0-9_=:%.&�-]*";
        if (url.matches(regex)) {
            for (int i = url.indexOf("?") - 1; i > url.indexOf("?") - 20; i--) {
                if (url.substring(i, i + 1).equals("/")) {
                    break;
                } else {
                    itemId = itemId.concat(url.substring(i, i + 1));
                }
            }
            return "https://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=XML&appid=C-Shopping-PRD-bc51f635b-dd94064c&siteid=0&version=967&ItemID=" + new StringBuffer(itemId).reverse().toString() + "&IncludeSelector=Details";

        } else {
            throw new Exception();
        }
    }
}
