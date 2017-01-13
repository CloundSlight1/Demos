package com.wuyz.demos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuyz on 2017/1/13.
 * RecyclerViewDemo
 */

public class RecyclerViewDemo extends Activity {

    private static final String TAG = "RecyclerViewDemo";
    private static final String URL1 = "http://api.meituan.com/mmdb/movie/v2/list/rt/order/coming.json?ci=1&limit=12&token=&__vhost=api.maoyan.com&utm_campaign=AmovieBmovieCD-1&movieBundleVersion=6801&utm_source=xiaomi&utm_medium=android&utm_term=6.8.0&utm_content=868030022327462&net=255&dModel=MI%205&uuid=0894DE03C76F6045D55977B6D4E32B7F3C6AAB02F9CEA042987B380EC5687C43&lat=40.100673&lng=116.378619&__skck=6a375bce8c66a0dc293860dfa83833ef&__skts=1463704714271&__skua=7e01cf8dd30a179800a7a93979b430b2&__skno=1a0b4a9b-44ec-42fc-b110-ead68bcc2824&__skcy=sXcDKbGi20CGXQPPZvhCU3%2FkzdE%3D";

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_list);
        adapter = new MovieAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);

        Request request = new Request.Builder().url(URL1).get().build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log2.e(TAG, e);
                final String msg = e.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecyclerViewDemo.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log2.d(TAG, "onResponse %s", response);
                String ret = response.body().string();
                if (TextUtils.isEmpty(ret))
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(ret);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("coming");
                    int n = jsonArray.length();
                    Gson gson = new Gson();
                    List<MovieData> list = new ArrayList<>(n);
                    for (int i = 0; i < n; i++) {
                        MovieData movieData = gson.fromJson(jsonArray.get(i).toString(), MovieData.class);
                        if (movieData != null) {
                            list.add(movieData);
                            Log2.d(TAG, "%s", movieData);
                        }
                    }
                    final List<MovieData> list2 = new ArrayList<>(n << 1);
                    list2.addAll(list);
                    list2.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.addItemDecoration(new MovieDecoration(RecyclerViewDemo.this, new MovieDecoration.Callback() {
                                @Override
                                public String getGroup(int pos) {
                                    return adapter.getItems().get(pos).getComingTitle();
                                }
                            }));
                            adapter.setItems(list2);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
