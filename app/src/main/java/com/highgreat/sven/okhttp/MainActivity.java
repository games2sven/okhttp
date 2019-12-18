package com.highgreat.sven.okhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.highgreat.sven.okhttp.net.Call;
import com.highgreat.sven.okhttp.net.Callback;
import com.highgreat.sven.okhttp.net.HGHttpClient;
import com.highgreat.sven.okhttp.net.Request;
import com.highgreat.sven.okhttp.net.RequestBody;
import com.highgreat.sven.okhttp.net.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sven";
    private HGHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new HGHttpClient();

    }

    public void get(View view) {
        //       final OkHttpClient client = new OkHttpClient();
//
//       final Request request = new Request.Builder()
//                .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
//                .get()
//                .build();
//       //异步请求
//        Call asyncCall = client.newCall(request);
//        asyncCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, "get响应体: " + response.body().string());
//                Log.d(TAG,System.currentTimeMillis()+""+Thread.currentThread().getName());
//            }
//        });
//        Log.d(TAG,System.currentTimeMillis()+"异步任务");
//        //同步请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = null;
//                    response = client.newCall(request).execute();//得到Response 对象
//                    if (response.isSuccessful()) {
//                        Log.d(TAG,"response.code()=="+response.code());
//                        Log.d(TAG,"response.message()=="+response.message());
//                        Log.d(TAG,"res=="+response.body().string());
//                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        Log.d(TAG,System.currentTimeMillis()+"同步任务");



        Request request = new Request.Builder()
                .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
                .get()
                .build();
        //异步任务
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.e(TAG, "get响应体: " + response.getBody());
//                Log.d(TAG,System.currentTimeMillis()+""+Thread.currentThread().getName());
//            }
//        });

        //同步任务
//        final Call call = client.newCall(request);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();
//                    Log.e(TAG, "get响应体: " + response.getBody());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }


    public void post(View view) {
        RequestBody body = new RequestBody()
                .add("city", "长沙")
                .add("key", "13cb58f5884f9749287abbead9c658f2");
        Request request = new Request.Builder().url("http://restapi.amap" +
                ".com/v3/weather/weatherInfo").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG,"post响应体: " + response.getBody());
            }
        });
    }

}
