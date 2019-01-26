package com.housecurling.com.Network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class CurlingNetwork {
    public CurlingNetwork() {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://10.5.1.109:8080/game/list").build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("asd");
                httpResponse.getResultAsString();
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("asda");
            }

            @Override
            public void cancelled() {
                System.out.println("wiiii");
            }
        });
    }
}
