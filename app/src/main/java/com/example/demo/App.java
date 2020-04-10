package com.example.demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者：qgl 时间： 2020/4/10 16:07
 * Describe:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //图片框架
        Fresco.initialize(this);
    }
}
