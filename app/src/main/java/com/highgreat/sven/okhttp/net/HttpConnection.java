package com.highgreat.sven.okhttp.net;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class HttpConnection {

    Socket socket;
    private Request request;

    long lastUsetime;
    static final String HTTPS = "https";
    private OutputStream os;
    private InputStream is;

    public boolean isSameAddress(String host, int port) {
        if (null == socket) {
            return false;
        }
        return TextUtils.equals(socket.getInetAddress().getHostName(),host) && port == socket
                .getPort();
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public InputStream call(HttpCodec httpCodec) throws IOException {

        try {
            createSocket();
            //写出请求
            httpCodec.writeRequest(os,request);
            return is;
        } catch (IOException e) {
            closeQuietly();
            throw  new IOException(e);
        }
    }

    public void closeQuietly() {
        if(null != socket){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createSocket() throws IOException {
        if(null == socket || socket.isClosed()){
            HttpUrl url = request.url();
            //需要sslsocket
            if(url.protocol.equalsIgnoreCase(HTTPS)){
                socket = SSLSocketFactory.getDefault().createSocket();
            }else{
                socket = new Socket();
            }
            socket.connect(new InetSocketAddress(url.host,url.port));
            os = socket.getOutputStream();
            is = socket.getInputStream();
        }
    }

    public void updateLastUseTime() {
        //更新最后使用时间
        lastUsetime = System.currentTimeMillis();
    }
}
