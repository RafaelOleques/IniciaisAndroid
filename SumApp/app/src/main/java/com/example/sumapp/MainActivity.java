package com.example.sumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected int editTexttoInt(EditText number){
        int val;

        if(TextUtils.isEmpty((number.getText().toString())))
            return 0;
        else
            return Integer.parseInt(number.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.buttonSum);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText number1 = (EditText) findViewById(R.id.number1);
                int val1 = editTexttoInt(number1);

                EditText number2 = (EditText) findViewById(R.id.number2);
                int val2 = editTexttoInt(number2);

                int sum = val1  + val2;

                TextView result = (TextView) findViewById(R.id.result);
                result.setText(String.valueOf(sum));
            }
        });
    }

}