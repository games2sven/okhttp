package com.highgreat.sven.okhttp.net.chain;

import com.highgreat.sven.okhttp.net.Response;

import java.io.IOException;

public interface Interceptor {

    Response intercept(InterceptorChain chain) throws IOException;

}
