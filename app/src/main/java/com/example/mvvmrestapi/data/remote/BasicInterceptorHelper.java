package com.example.mvvmrestapi.data.remote;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicInterceptorHelper implements Interceptor {

    private final String credentials;

    public BasicInterceptorHelper() {
        this.credentials = Credentials.basic("AMSAPIADMIN", "AMSAPIP@SSWORD");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}
