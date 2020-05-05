package com.priyo.automationreports.connectionUrls;

public class LoadTestScenarios {

    Primary primaryUrls = Primary.getInstance();

    public String getScenarioByproject() {
        return "http://13.233.196.167:8080/api/projectHome?projectName="
                +primaryUrls.getProject()+"&SubProject=" + primaryUrls.getSubProject();
    }

    public String getGetScenariosByProjectSubProjectDate() {
        return "http://13.233.196.167:8080//api/subProjectHomeFilterByDate?projectName="
                + primaryUrls.getProject() + "&SubProject=" + primaryUrls.getSubProject() + "&start_date=" + primaryUrls.getStartDate() +
                "&end_date=" + primaryUrls.getEndDate();
    }

    public String getScenarioByprojectSubProject() {
        return "http://13.233.196.167:8080/api/subProjectHome?projectName="
                +primaryUrls.getProject()+"&SubProject=" + primaryUrls.getSubProject();
    }

    public static LoadTestScenarios getInstance() {
        if (singleInstance == null) {
            singleInstance = new LoadTestScenarios();
            return singleInstance;
        } else {
            return singleInstance;
        }
    }

    private static LoadTestScenarios singleInstance;
}
