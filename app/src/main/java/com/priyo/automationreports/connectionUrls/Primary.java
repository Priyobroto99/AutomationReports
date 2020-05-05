package com.priyo.automationreports.connectionUrls;

public class Primary {

    public final String TAG = "Primary";

    private static Primary singleInstance;

    private String allprojectsApi = "http://13.233.196.167:8080/api/getAllProjectNames";

    private String allSubProjectsApi = "http://13.233.196.167:8080/api/getAllSubProjectNames";

    private String allReleaseNamesApi = "http://13.233.196.167:8080/api/getAllReleaseNames";

    private String project = "";

    private String subProject = "";

    private String token = "";

    private String startDate = "";

    private String endDate = "";

    public static Primary getInstance() {
        if (singleInstance == null) {
            singleInstance = new Primary();
            return singleInstance;
        } else {
            return singleInstance;
        }
    }

    public String getAllprojectsApi() {
        return allprojectsApi;
    }

    public String getAllSubProjectsApi() {
        return allSubProjectsApi;
    }

    public String getAllReleaseNamesApi() {
        return allReleaseNamesApi;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSubProject() {
        return subProject;
    }

    public void setSubProject(String subProject) {
        this.subProject = subProject;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
