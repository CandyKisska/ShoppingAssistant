package com.example.shoppingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final EditText editText = findViewById(R.id.time);
        final Intent intent = new Intent();
        int hint = getIntent().getIntExtra("Hint",1);

        editText.setText(Integer.toString(hint));

        Button button = findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String timeString = editText.getText().toString();
                try {
                    int time = Integer.parseInt(timeString);
                    intent.putExtra("Time", time);
                    Log.println(Log.ASSERT, "result", Integer.toString(time));
                    setResult(RESULT_OK, intent);

                    if (time <= 0) {
                        throw new Exception();

                    }

                    finish();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Неверный формат. Введите целое число", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}