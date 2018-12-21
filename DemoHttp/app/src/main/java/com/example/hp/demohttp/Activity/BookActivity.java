package com.example.hp.demohttp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.hp.demohttp.Adapter.MyRecyclerAdapter;
import com.example.hp.demohttp.Data.Book;
import com.example.hp.demohttp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    List<Book> bookList = new ArrayList<>();
    RecyclerView recyclerView;
    MyRecyclerAdapter myRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initData();

        recyclerView = findViewById(R.id.rec_view);
        myRecyclerAdapter = new MyRecyclerAdapter(this, bookList);
        LinearLayoutManager Manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.setLayoutManager(Manager);

        ImageView back = findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initData() {
        HttpConnection httpConnection = new HttpConnection("https://www.apiopen.top/novelSearchApi?name=盗墓笔记");
        httpConnection.sendReqestWithHttpURLConnection(new HttpConnection.CallBack() {
            @Override
            public void finish(String response) {
                parseJSON(response);
            }
        });


    }

    private void parseJSON(String response) {
        try {
            int[] image = {R.drawable.imag1, R.drawable.imag2};
            JSONObject jsonObject = new JSONObject(response);
            // Log.d("dlx" ,response);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                Book book = new Book();
                book.setName((String) jsonArray.get(i));
                if (i % 2 == 0) {
                    book.setImageId(image[0]);
                } else {
                    book.setImageId(image[1]);
                }
                bookList.add(book);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myRecyclerAdapter.notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
