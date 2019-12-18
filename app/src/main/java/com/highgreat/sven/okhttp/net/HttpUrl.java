package com.highgreat.sven.okhttp.net;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrl {

    String protocol;
    String host;
    int port;
    String file;

    public HttpUrl(String url) throws MalformedURLException {
        URL url1 = new URL(url);
        host = url1.getHost();
        file = url1.getFile();
        file = TextUtils.isEmpty(file) ? "/" : file;
        port = url1.getPort();
        protocol = url1.getProtocol();
        port = port == -1 ? url1.getDefaultPort() : port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getFile() {
        return file;
    }

}
