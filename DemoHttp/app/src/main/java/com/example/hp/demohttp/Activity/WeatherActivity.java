package com.example.hp.demohttp.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.demohttp.Adapter.WeatherRecyclerViewAtapter;
import com.example.hp.demohttp.Data.Weather;
import com.example.hp.demohttp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WeatherRecyclerViewAtapter atapter;
    List<Weather> weatherList = new ArrayList<>();
    String city;
    String tip;
    String temp;
    TextView tittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tittle = findViewById(R.id.tv_tittle);

        initData();
        getData();

        ImageView back = findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        recyclerView = findViewById(R.id.weather_rev_layout);
        atapter = new WeatherRecyclerViewAtapter(this, weatherList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(atapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initData() {
        HttpConnection httpConnection = new HttpConnection("https://www.apiopen.top/weatherApi?city=成都");
        httpConnection.sendReqestWithHttpURLConnection(new HttpConnection.CallBack() {
            @Override
            public void finish(String response) {
                parseJson(response);
            }
        });

    }


    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("yesterday");

            Weather weather1 = new Weather();
            city = jsonObject1.getString("city");
            tip = jsonObject1.getString("ganmao");
            this.temp = jsonObject1.getString("wendu");

            tittle.setText(city);

            weather1.setDate(jsonObject2.getString("date"));
            weather1.setHighTemp(jsonObject2.getString("high"));
            weather1.setLowTemp(jsonObject2.getString("low"));
            weather1.setType(jsonObject2.getString("type"));
            weather1.setWindDirection(jsonObject2.getString("fx"));
            weather1.setWindPower(jsonObject2.getString("fl"));

            TextView date = findViewById(R.id.tv_day);
            ImageView imageView = findViewById(R.id.imageView);
            TextView type = findViewById(R.id.tv_weather);
            TextView high = findViewById(R.id.tv_high_temperature);
            TextView low = findViewById(R.id.tv_low_temperature);
            TextView windPower = findViewById(R.id.tv_fj);
            TextView windDirection = findViewById(R.id.tv_fx);
            TextView temp = findViewById(R.id.tv_temp);
            TextView tip = findViewById(R.id.tv_tip);

            date.setText(weather1.getDate());
            type.setText(weather1.getType());
            high.setText(weather1.getHighTemp());
            low.setText(weather1.getLowTemp());
            windDirection.setText(weather1.getWindDirection());
            windPower.setText(weather1.getWindPower());
            temp.setText(this.temp);
            tip.setText(this.tip);
            if (weather1.getType().equals("小雨")) {
                imageView.setImageResource(R.drawable.img_rain);
            } else if (weather1.getType().equals("晴")) {
                imageView.setImageResource(R.drawable.img_sunny);
            } else if (weather1.getType().equals("阴")) {
                imageView.setImageResource(R.drawable.img_cloudy_day);
            } else if (weather1.getType().equals("多云")) {
                imageView.setImageResource(R.drawable.img_cloudy);
            }

            JSONArray jsonArray = new JSONArray(jsonObject1.getString("forecast"));

            for (int i = 0; i < jsonArray.length(); i++) {
                Weather weather = new Weather();
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                weather.setDate(jsonObject3.getString("date"));
                weather.setType(jsonObject3.getString("type"));
                weather.setHighTemp(jsonObject3.getString("high"));
                weather.setLowTemp(jsonObject3.getString("low"));
                weather.setWindDirection(jsonObject3.getString("fengxiang"));
                weather.setWindPower(jsonObject3.getString("fengli"));
                if ((jsonObject3.getString("type").equals("小雨"))) {
                    weather.setImage(R.drawable.img_rain);
                } else if (jsonObject3.getString("type").equals("晴")) {
                    weather.setImage(R.drawable.img_sunny);
                } else if (jsonObject3.getString("type").equals("多云")) {
                    weather.setImage(R.drawable.img_cloudy);
                } else if (jsonObject3.getString("type").equals("阴")) {
                    weather.setImage(R.drawable.img_cloudy_day);
                }
                weatherList.add(weather);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    atapter.notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
