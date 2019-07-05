package com.berkutkuyenisey.registerassignment;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Setting of the log level.
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this).applicationId("nJ44hlbCBYpSHHyNHh0VXOVziyiGa4PFZMnDotew")
                .clientKey("yy28H49qhNsEGB1GXHzNMJ9fEUPKgRs8X1hFfHyt")
                .server("https://parseapi.back4app.com/")
                .build());

    }
}
