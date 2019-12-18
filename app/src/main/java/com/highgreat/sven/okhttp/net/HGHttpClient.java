package com.highgreat.sven.okhttp.net;

import com.highgreat.sven.okhttp.net.chain.Interceptor;

import java.util.ArrayList;
import java.util.List;

public class HGHttpClient {

    private Dispatcher dispatcher;
    private List<Interceptor> interceptors;
    private ConnectionPool connectionPool;
    private int retrys;

    public HGHttpClient(){
        this(new Builder());
    }

    public HGHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        connectionPool = builder.connectionPool;
        interceptors = builder.interceptors;
        retrys = builder.retrys;
    }

    public Call newCall(Request request){
        return new Call(this,request);
    }

    public int retrys() {
        return retrys;
    }

    public Dispatcher dispatcher() {
        return dispatcher;
    }

    public ConnectionPool connectionPool() {
        return connectionPool;
    }

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    public static class Builder{
        //分发器
        Dispatcher dispatcher = new Dispatcher();
        List<Interceptor> interceptors = new ArrayList<>();
        ConnectionPool connectionPool = new ConnectionPool();
        //默认重试3次
        int retrys = 3;

        public Builder retrys(int retrys) {
            this.retrys = retrys;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }
    }

}
