package com.priyo.automationreports.connectionUrls;

public class LoadTestCases{

    Primary primaryUrls = Primary.getInstance();

    public static LoadTestCases getInstance() {
        if (singleInstance == null) {
            singleInstance = new LoadTestCases();
            return singleInstance;
        } else {
            return singleInstance;
        }
    }

    public String getTestCasesBytoken() {
        return "http://13.233.196.167:8080//api/getTestCases?" +
                "projectName=" + primaryUrls.getProject() + "&SubProject=" + primaryUrls.getSubProject() + "&token=" + primaryUrls.getToken();
    }

    private static LoadTestCases singleInstance;

}
