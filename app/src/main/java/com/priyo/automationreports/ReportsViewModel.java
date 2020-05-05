package com.priyo.automationreports;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.priyo.automationreports.connectionUrls.LoadTestCases;
import com.priyo.automationreports.connectionUrls.LoadTestScenarios;
import com.priyo.automationreports.connectionUrls.Primary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReportsViewModel extends ViewModel {

    private static final String TAG = "ReportsViewModel";
    Primary primaryUrls = Primary.getInstance();
    LoadTestScenarios loadTestScenarios = LoadTestScenarios.getInstance();
    LoadTestCases loadTestCases = LoadTestCases.getInstance();

    MutableLiveData<JsonArray> allProjects = new MutableLiveData<>();

    public MutableLiveData<JsonArray> getallProjects() {
        String allprojectsApi = primaryUrls.getAllprojectsApi();
        getAllProjectsTask downloadTask = new getAllProjectsTask();
        downloadTask.execute(allprojectsApi);
        return allProjects;
    }

    MutableLiveData<JsonArray> allSubProjects = new MutableLiveData<>();

    public MutableLiveData<JsonArray> getallSubProjects() {
        String allSubProjectsApi = primaryUrls.getAllSubProjectsApi();
        getAllSubProjectsTask downloadTask = new getAllSubProjectsTask();
        downloadTask.execute(allSubProjectsApi);
        return allSubProjects;
    }

    MutableLiveData<JsonArray> allReleaseNames = new MutableLiveData<>();

    public MutableLiveData<JsonArray> getallReleaseNames() {
        String allReleaseNamesApi = primaryUrls.getAllReleaseNamesApi();
        getAllReleaseNames downloadTask = new getAllReleaseNames();
        downloadTask.execute(allReleaseNamesApi);
        return allReleaseNames;
    }

    MutableLiveData<JSONArray> allScenariosByDate = new MutableLiveData<>();

    public MutableLiveData<JSONArray> getAllTestScenariosByDate() {
        String scs = loadTestScenarios.getGetScenariosByProjectSubProjectDate();
        getAllScenariosByDate downloadTask = new getAllScenariosByDate();
        downloadTask.execute(scs);
        return allScenariosByDate;
    }

    MutableLiveData<JSONArray> allScenariosByProject = new MutableLiveData<>();

    public MutableLiveData<JSONArray> getAllTestScenariosByProject() {
        String scs = loadTestScenarios.getScenarioByproject();
        getAllScenariosByProject downloadTask = new getAllScenariosByProject();
        Log.i(TAG, "Request : " + scs);
        downloadTask.execute(scs);
        return allScenariosByProject;
    }

    MutableLiveData<JSONArray> allScenariosByProjectSubProject = new MutableLiveData<>();

    public MutableLiveData<JSONArray> getAllTestScenariosByProjectSubproject() {
        String scs = loadTestScenarios.getScenarioByprojectSubProject();
        getAllScenariosByProjectSubProject downloadTask = new getAllScenariosByProjectSubProject();
        Log.i(TAG, "Request : " + scs);
        downloadTask.execute(scs);
        return allScenariosByProjectSubProject;
    }

    MutableLiveData<JSONArray> allTestCasesByProjectSubToken = new MutableLiveData<>();

    public MutableLiveData<JSONArray> getAllTestCasesByProjectSubToken() {
        String scs = loadTestCases.getTestCasesBytoken();
        getAllTestCasesByProjectSubProjectToken downloadTask = new getAllTestCasesByProjectSubProjectToken();
        Log.i(TAG, "Request : " + scs);
        downloadTask.execute(scs);
        return allTestCasesByProjectSubToken;
    }


    public class getAllProjectsTask extends AsyncTask<String, String, String> {

        public final String TAG = "getAllProjectsTask";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                s = s.replaceAll("null", "");
                JsonParser jsonParser = new JsonParser();
                // Convert JSON Array String into JSON Array

                JsonArray arrayFromString = jsonParser.parse(s).getAsJsonArray();

                Log.i(TAG, s);
                allProjects.setValue(arrayFromString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getAllSubProjectsTask extends AsyncTask<String, String, String> {

        public final String TAG = "getAllSubProjectsTask";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                s = s.replaceAll("null", "");
                JsonParser jsonParser = new JsonParser();
                // Convert JSON Array String into JSON Array

                JsonArray arrayFromString = jsonParser.parse(s).getAsJsonArray();

                Log.i(TAG, s);
                allSubProjects.setValue(arrayFromString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getAllReleaseNames extends AsyncTask<String, String, String> {

        public final String TAG = "getAllReleaseNames";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                s = s.replaceAll("null", "");
                JsonParser jsonParser = new JsonParser();
                // Convert JSON Array String into JSON Array

                JsonArray arrayFromString = jsonParser.parse(s).getAsJsonArray();

                Log.i(TAG, s);
                allReleaseNames.setValue(arrayFromString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getAllScenariosByDate extends AsyncTask<String, String, String> {

        public final String TAG = "getAllScenariosByDate";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //s = s.replaceAll("null", "");

                s = s.substring(4);


                JSONObject object = new JSONObject(s);
                JSONArray arr = new JSONArray(object.get("scenarioResponse").toString());
                Log.i(TAG, s);

                allScenariosByDate.setValue(arr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getAllScenariosByProjectSubProject extends AsyncTask<String, String, String> {

        public final String TAG = "getAllScenariosByProjectSubProject";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //s = s.replaceAll("null", "");

                s = s.substring(4);


                JSONObject object = new JSONObject(s);
                JSONArray arr = new JSONArray(object.get("scenarioResponse").toString());
                Log.i(TAG, s);

                allScenariosByProjectSubProject.setValue(arr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getAllScenariosByProject extends AsyncTask<String, String, String> {

        public final String TAG = "getAllScenariosByProject";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //s = s.replaceAll("null", "");

                s = s.substring(4);


                JSONObject object = new JSONObject(s);
                JSONArray arr = new JSONArray(object.get("scenarioResponse").toString());
                Log.i(TAG, s);

                allScenariosByProject.setValue(arr);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public class getAllTestCasesByProjectSubProjectToken extends AsyncTask<String, String, String> {

        public final String TAG = "getAllTestCasesByProjectSubProjectToken";


        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //s = s.replaceAll("null", "");
            try {

                s = s.substring(4);

                JSONObject object = new JSONObject(s);
                JSONArray arr = new JSONArray(object.get("testCaseResponse").toString());
                Log.i(TAG, s);

                allTestCasesByProjectSubToken.setValue(arr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

