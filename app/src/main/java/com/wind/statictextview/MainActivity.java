package com.wind.statictextview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private StaticTextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view = findViewById(R.id.text_view);
    }

    public void changeText(View v) {
        String label = System.currentTimeMillis() + "_random";
        text_view.setText(label);
    }

    public void changeColor(View v) {
        text_view.setTextColor(Color.RED);
    }

    public void changeSize(View v) {
        text_view.setTextSize(24, TypedValue.COMPLEX_UNIT_SP);
    }
}
