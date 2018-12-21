package com.example.hp.demohttp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.demohttp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button book = findViewById(R.id.bt_book);
        Button weather = findViewById(R.id.bt_weather);

        book.setOnClickListener(this);
        weather.setOnClickListener(this);

        TextView tittle = findViewById(R.id.tv_tittle);
        tittle.setText("首页");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_book:
                Intent intent = new Intent(this, BookActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_weather:
                Intent intent1 = new Intent(this, WeatherActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

}
