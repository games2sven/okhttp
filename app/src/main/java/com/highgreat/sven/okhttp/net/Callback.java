package com.highgreat.sven.okhttp.net;

public interface Callback {

    void onFailure(Call call,Throwable throwable);

    void onResponse(Call call,Response response);

}
