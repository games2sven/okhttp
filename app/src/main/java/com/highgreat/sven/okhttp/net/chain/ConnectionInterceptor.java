package com.highgreat.sven.okhttp.net.chain;

import android.util.Log;

import com.highgreat.sven.okhttp.net.HGHttpClient;
import com.highgreat.sven.okhttp.net.HttpConnection;
import com.highgreat.sven.okhttp.net.HttpUrl;
import com.highgreat.sven.okhttp.net.Request;
import com.highgreat.sven.okhttp.net.Response;

import java.io.IOException;

public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("interceprot", "连接拦截器....");
        Request request = chain.call.request();
        HGHttpClient client = chain.call.client();
        HttpUrl url = request.url();
        String host = url.getHost();
        int port = url.getPort();
        HttpConnection connection = client.connectionPool().get(host, port);
        if (null == connection) {
            connection = new HttpConnection();
        } else {
            Log.e("call", "使用连接池......");
        }
        connection.setRequest(request);

        try {
            Response response = chain.proceed(connection);
            if (response.isKeepAlive()) {
                //保持连接
                client.connectionPool().put(connection);
            }
            return response;
        } catch (IOException e) {
            throw e;
        }
    }
}
