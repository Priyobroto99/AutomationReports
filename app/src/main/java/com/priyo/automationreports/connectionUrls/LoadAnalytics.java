package com.priyo.automationreports.connectionUrls;

public class LoadAnalytics {

    public static LoadAnalytics getInstance() {
        if (singleInstance == null) {
            singleInstance = new LoadAnalytics();
            return singleInstance;
        } else {
            return singleInstance;
        }
    }

    private static LoadAnalytics singleInstance;
}
