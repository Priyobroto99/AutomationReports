package com.priyo.automationreports.Adapters;

import org.json.JSONArray;

public class TestCaseList_Item {

    private String tc_id;
    private String tc_name;
    private String total_executionTime;
    private String test_status;
    private String module;
    private String release;
    private String cycle;
    private JSONArray steps;

    public TestCaseList_Item(String tc_id, String tc_name,
                             String test_status, String module, String release, String cycle,JSONArray steps) {
        this.tc_id = tc_id;
        this.tc_name = tc_name;
        this.test_status = test_status;
        this.module = module;
        this.release = release;
        this.cycle = cycle;
        this.steps = steps;
    }

    public String getTc_id() {
        return tc_id;
    }

    public String getTc_name() {
        return tc_name;
    }

    public String getTotal_executionTime() {
        return total_executionTime;
    }

    public String getTest_status() {
        return test_status;
    }

    public String getModule() {
        return module;
    }

    public String getRelease() {
        return release;
    }

    public String getCycle() {
        return cycle;
    }

    public JSONArray getSteps(){return steps;}
}
