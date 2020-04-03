package com.example.freshtext.Utility;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtility {
    StringBuffer res = null;
    Boolean goon = false;
    final OkHttpClient client = new OkHttpClient();


    public StringBuffer get(String url) {
        goon = false;
        res = null;
        //get
        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        res = new StringBuffer(response.body().string());
                        goon = true;
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (!goon){}
        return res;
    }

    public synchronized StringBuffer post(RequestBody body, String url) {
        goon = false;
        res = null;
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //接收到的json数据
                        res = new StringBuffer(response.body().string());
                        goon = true;
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (!goon){ }
        return res;
    }
}
