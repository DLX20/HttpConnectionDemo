package com.example.hp.demohttp.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
    String url1;

    public HttpConnection(String url) {
        this.url1 = url;
    }

    public interface CallBack {
        void finish(String response);
    }

    public void sendReqestWithHttpURLConnection(final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conection = null;
                BufferedReader reader = null;
                InputStream inputStream;
                try {
                    URL url = new URL(url1);
                    conection = (HttpURLConnection) url.openConnection();
                    conection.setRequestMethod("GET");
                    conection.setConnectTimeout(5000);
                    conection.setReadTimeout(5000);
                    inputStream = conection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    inputStream.close();
                    if (callBack != null) {
                        callBack.finish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (conection != null) {
                            conection.disconnect();
                        }
                    }
                }
            }
        }).start();
    }
}
