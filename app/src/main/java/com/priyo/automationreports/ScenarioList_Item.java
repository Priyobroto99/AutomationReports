package com.priyo.automationreports;

public class ScenarioList_Item {

    private String sc_id;
    private String scenarionName;
    private String execution_mode;
    private String token;

    public String getExecution_mode() {
        return execution_mode;
    }

    public String getToken() {
        return token;
    }

    public ScenarioList_Item(String sc_id, String scenarionName, String execution_mode, String token) {
        this.sc_id = sc_id;
        this.scenarionName = scenarionName;
        this.execution_mode = execution_mode;
        this.token = token;
    }

    public String getSc_id() {
        return sc_id;
    }

    public String getScenarionName() {
        return scenarionName;
    }
}
