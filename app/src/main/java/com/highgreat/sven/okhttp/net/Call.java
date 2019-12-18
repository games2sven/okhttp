package com.highgreat.sven.okhttp.net;

import com.highgreat.sven.okhttp.net.chain.CallServiceInterceptor;
import com.highgreat.sven.okhttp.net.chain.ConnectionInterceptor;
import com.highgreat.sven.okhttp.net.chain.HeadersInterceptor;
import com.highgreat.sven.okhttp.net.chain.Interceptor;
import com.highgreat.sven.okhttp.net.chain.InterceptorChain;
import com.highgreat.sven.okhttp.net.chain.RetryInterceptor;

import java.io.IOException;
import java.util.ArrayList;

public class Call {


    private  HGHttpClient client;
    private  Request request;

    /**
     * 是否执行过
     */
    boolean executed;

    boolean canceled;

    public Call(HGHttpClient hgHttpClient, Request request) {
        this.client = hgHttpClient;
        this.request = request;
    }

    public Request request() {
        return request;
    }

    public HGHttpClient client() {
        return client;
    }

    public Call enqueue(Callback callback){
        //不能重复执行
        synchronized (this){
            if (executed) {
                throw new IllegalStateException("Already Executed");
            }
            executed = true;
        }
        client.dispatcher().enqueue(new AsyncCall(callback));
        return this;
    }


    public Response execute() throws IOException{
        synchronized (this){
            if (executed) throw new IllegalStateException("Already Executed");
            executed = true;
        }

        try {
            client.dispatcher().executed(this);
            Response response = getResponse();
            return response;
        } finally {
            client.dispatcher().finished(this);
        }
    }

    private Response getResponse() throws IOException {
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(client.interceptors());
        interceptors.add(new RetryInterceptor());
        interceptors.add(new HeadersInterceptor());
        interceptors.add(new ConnectionInterceptor());
        interceptors.add(new CallServiceInterceptor());
        InterceptorChain interceptorChain = new InterceptorChain(interceptors,0,this,null);
        return interceptorChain.proceed();
    }

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }


    class AsyncCall implements Runnable{

        private final Callback callback;

        public AsyncCall(Callback callback){
             this.callback = callback;
        }

        @Override
        public void run() {
            //是否已经通知过callback
            boolean signalledCallback = false;
            //进行异步网络操作，并回调结果
            try {
                Response response = getResponse();
                if(canceled){
                    signalledCallback = true;
                    callback.onFailure(Call.this,new IOException("Canceled"));
                }else{
                    signalledCallback = true;
                    callback.onResponse(Call.this,response);
                }
            } catch (IOException e) {
                if (!signalledCallback) {
                    callback.onFailure(Call.this, e);
                }
            }finally {
                client.dispatcher().finished(this);
            }
        }

        public String host() {
            return request.url().host;
        }

    }




}
