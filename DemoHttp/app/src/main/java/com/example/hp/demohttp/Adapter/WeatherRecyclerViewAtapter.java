package com.example.hp.demohttp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.demohttp.Data.Weather;
import com.example.hp.demohttp.R;

import java.util.List;

public class WeatherRecyclerViewAtapter extends RecyclerView.Adapter<WeatherRecyclerViewAtapter.MyHolder> {

    List<Weather> weatherList;
    Context context;
    // List<Integer> images;

    public WeatherRecyclerViewAtapter(Context mContext, List<Weather> weatherList) {
        context = mContext;
        this.weatherList = weatherList;
    }

//    public void setImage(List<Integer> images){
//        this.images = images;
//    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Weather weather = weatherList.get(i);
        myHolder.imageView.setImageResource(weather.getImage());
        myHolder.date.setText(weather.getDate());
        myHolder.high.setText(weather.getHighTemp());
        myHolder.low.setText(weather.getLowTemp());
        myHolder.windDirection.setText(weather.getWindDirection());
        myHolder.windPower.setText(weather.getWindPower());
        myHolder.weather.setText(weather.getType());

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date;
        TextView weather;
        TextView high;
        TextView low;
        TextView windDirection;
        TextView windPower;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_item);
            date = itemView.findViewById(R.id.tv_day);
            weather = itemView.findViewById(R.id.tv_weather);
            high = itemView.findViewById(R.id.tv_high_temperature);
            low = itemView.findViewById(R.id.tv_low_temperature);
            windDirection = itemView.findViewById(R.id.tv_fx);
            windPower = itemView.findViewById(R.id.tv_fj);
        }
    }
}
