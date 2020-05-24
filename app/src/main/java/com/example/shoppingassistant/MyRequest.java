package com.example.shoppingassistant;

import android.os.AsyncTask;
import android.util.Log;



class MyRequest extends AsyncTask<String, Void, Product> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Product doInBackground(String... urls) {

        Log.println(Log.ASSERT, "WORKS",urls[0]);
        return new EbayRequest().get(urls[0]);
    }

    @Override
    protected void onPostExecute(Product result) {
        super.onPostExecute(result);
    }
}