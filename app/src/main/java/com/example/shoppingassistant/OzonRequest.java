package com.example.shoppingassistant;



import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OzonRequest {
    static OkHttpClient client = new OkHttpClient();
    static String host = "http://api-seller.ozon.ru";
    static String API_KEY = "b1b13516-a9a9-48a2-90f9-98ab1fea3a5f";


    public OzonRequest() {
    }

    public String main() throws IOException {
        Data data = new Data( 147997818 );

        String ans = post("/v2/product/info", data, 60123);
        return ans;
    }

    public String post(String url, Data data, int clientId) throws IOException {
        MediaType JsonType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(data);

        RequestBody body = RequestBody.Companion.create(json, JsonType);
        System.out.println(body);
        Request request = new Request.Builder()
                .url(host + url)
                .addHeader("Api-Key", API_KEY)
                .addHeader("Client-Id", String.valueOf(clientId))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}


