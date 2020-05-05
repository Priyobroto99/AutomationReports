package com.priyo.automationreports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.priyo.automationreports.connection.ConnectionReciever;
import com.priyo.automationreports.connectionUrls.Primary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Reports extends AppCompatActivity implements ScenarioAdapter.OnScenarioListener,
        View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private final String TAG = "ReportsActivity";
    private LoginActivityViewModel loginViewModel;
    ReportsViewModel reportsViewModel;
    Primary primaryUrls;
    private List<ScenarioList_Item> list;

    private Spinner project;
    private Spinner subproject;
    private Spinner release;
    private TextInputEditText startDate;
    private TextInputEditText endDate;
    private Button viewReportBtn;
    private Button showSearchBtn;
    private LinearLayout searchLayout;
    private LinearLayout reportLayout;
    private RecyclerView recyclerView;
    private ScenarioAdapter adapter;

    private TextView projectText;
    private TextView subProjectText;
    private TextView releaseText;

    private View view;

    private ProgressDialog progressDialog;
    private int h;
    private int w;

    private ConnectionReciever intrnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        mAuth = FirebaseAuth.getInstance();
        loginViewModel = LoginActivityViewModel.getInstance();
        reportsViewModel = ViewModelProviders.of(this).get(ReportsViewModel.class);

        project = (Spinner) findViewById(R.id.spinner);
        subproject = (Spinner) findViewById(R.id.spinner2);
        release = (Spinner) findViewById(R.id.spinner3);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);

        viewReportBtn = (Button) findViewById(R.id.reportBtn);
        showSearchBtn = (Button) findViewById(R.id.showSearchBtn);

        searchLayout = findViewById(R.id.search_Layout);
        reportLayout = findViewById(R.id.reportLayout);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        projectText = findViewById(R.id.projecttxt);
        subProjectText = findViewById(R.id.subprojecttxt);
        releaseText = findViewById(R.id.releasetxt);

        view = findViewById(R.id.cView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        primaryUrls = Primary.getInstance();
        hideKeyBoard();

        viewReportBtn.setOnClickListener(this);
        showSearchBtn.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        intrnt = new ConnectionReciever() {
            @Override
            protected void onNetworkChange() {
                boolean net = checkInternet();

                if (net == false) {
                    Snackbar.make(view, "Please connect to the internet", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                        onNetworkChange();

                                    // Respond to the click, such as by undoing the modification that caused
                                    // this message to be displayed
                                }
                            }).show();
                } else {
                    Snackbar.make(view, "We're back", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        };
        registerReceiver(intrnt, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        Log.i(TAG, "ONCREATE");


    }//end of onCreate

    @Override
    protected void onStart() {
        super.onStart();


        if (!loginViewModel.checkLogin()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }
        LiveData<JsonArray> projects = reportsViewModel.getallProjects();
        setSpinnerWithLiveData(project, projects);
        project.setOnItemSelectedListener(this);

        LiveData<JsonArray> subprojects = reportsViewModel.getallSubProjects();
        setSpinnerWithLiveData(subproject, subprojects);
        subproject.setOnItemSelectedListener(this);

        LiveData<JsonArray> releases = reportsViewModel.getallReleaseNames();
        setSpinnerWithLiveData(release, releases);
        release.setOnItemSelectedListener(this);

        Log.i(TAG, "ONSTART");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logoutmenu) {
            signOut();
        } else if (item.getItemId() == R.id.analyticsmenu) {
            Intent intent = new Intent(getApplicationContext(), Analytics.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.adminmenu) {

            Toast.makeText(getApplicationContext(), "Contact xyz to gain Admin Access", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reportmenu, menu);
        return true;
    }


    void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public Boolean hasValue(String input) {

        if (input.isEmpty() || input == null) {
            return false;
        } else {
            input = input.trim();
            if (input.isEmpty() || input == null) {
                return false;
            } else {
                return true;
            }
        }

    }

    @Override
    public void onScenarioClick(int position) {

        new AlertDialog.Builder(Reports.this)
                .setMessage("View all Test Cases in this scenario?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Clicked:" + position);
                        ScenarioList_Item scenario = list.get(position);
                        String tokenselected = scenario.getToken();
                        primaryUrls.setToken(tokenselected);

                        LiveData<JSONArray> testCases = reportsViewModel.getAllTestCasesByProjectSubToken();
                        loadtestCaseActivity(testCases);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ONDESTROY");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "ONPAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "ONSTOP");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ONRESUME");
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.reportBtn) {

            primaryUrls.setStartDate(startDate.getText().toString().replaceAll("/", ""));
            primaryUrls.setEndDate(endDate.getText().toString().replaceAll("/", ""));

            Boolean hasProject = hasValue(primaryUrls.getProject());
            Boolean hasSubProject = hasValue(primaryUrls.getSubProject());
            Boolean hasStartDate = hasValue(primaryUrls.getStartDate());
            Boolean hasEndDate = hasValue(primaryUrls.getEndDate());

            if (!hasProject) {
                Toast.makeText(Reports.this, "You must atleast select a project", Toast.LENGTH_LONG).show();
                return;
            }
            progressDialog = new ProgressDialog(Reports.this);
            progressDialog.setMessage("Loading report...");
            progressDialog.show();
            hideKeyBoard();

            if (hasProject && hasSubProject && hasStartDate && hasEndDate) {
                //call getGetScenariosByProjectSubProjectDate
                progressDialog.dismiss();
                new AlertDialog.Builder(Reports.this)
                        .setMessage("View all Scenarios in project " + primaryUrls.getProject() + ", Sub Project " + primaryUrls.getSubProject() +
                                ", Start Date " + primaryUrls.getStartDate() + "& End Date " + primaryUrls.getEndDate() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setScenarioRecycler(reportsViewModel.getAllTestScenariosByDate());
                                hide_searchSection();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            } else if (hasProject && hasSubProject) {
                //call gettestScenariosProjSub
                progressDialog.dismiss();
                new AlertDialog.Builder(Reports.this)
                        .setMessage("View all Scenarios in project " + primaryUrls.getProject() + "& Sub Project " + primaryUrls.getSubProject() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setScenarioRecycler(reportsViewModel.getAllTestScenariosByProjectSubproject());
                                hide_searchSection();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            } else if (hasProject) {
                //call getScenariosByProject
                progressDialog.dismiss();
                new AlertDialog.Builder(Reports.this)
                        .setMessage("View all Scenarios in project " + primaryUrls.getProject() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setScenarioRecycler(reportsViewModel.getAllTestScenariosByProject());
                                hide_searchSection();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();

            }

        } else if (id == R.id.search_Layout) {
            hideKeyBoard();
        } else if (id == R.id.showSearchBtn) {
            show_searchLayout();
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
        int id = parent.getId();

        if (id == R.id.spinner) {
            Log.i(TAG, "Project " + parent.getItemAtPosition(position).toString() + " selected.");
            primaryUrls.setProject(parent.getItemAtPosition(position).toString());
            projectText.setText(primaryUrls.getProject());
        }
        if (id == R.id.spinner2) {
            Log.i(TAG, "Sub Project " + parent.getItemAtPosition(position).toString() + " selected.");
            primaryUrls.setSubProject(parent.getItemAtPosition(position).toString());
            subProjectText.setText(primaryUrls.getSubProject());
        }
        if (id == R.id.spinner3) {
            Log.i(TAG, "Release " + parent.getItemAtPosition(position).toString() + " selected.");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setSpinnerWithLiveData(Spinner spinner, LiveData<JsonArray> arr) {


        arr.observe(this, new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonElements) {

                ArrayList<String> list = new ArrayList();

                for (JsonElement e : jsonElements) {
                    String s = e.getAsString();
                    list.add(s);
                }

                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinner.setAdapter(aa);
            }
        });
    }

    public void loadtestCaseActivity(LiveData<JSONArray> testCases) {
        testCases.observe(Reports.this, new Observer<JSONArray>() {
            @Override
            public void onChanged(JSONArray jsonElements) {

                Log.i(TAG, "Extra " + jsonElements.length());
                Intent intent = new Intent(Reports.this, TestCaseActivity.class);
                intent.putExtra("TestCaseArray", jsonElements.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 005);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 005) {

        }
    }

    public void signOut() {
        progressDialog = new ProgressDialog(Reports.this);
        progressDialog.setMessage("Signing you out");
        progressDialog.show();
        mAuth.signOut();
        progressDialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void setScenarioRecycler(LiveData<JSONArray> testScenarios) {
        testScenarios.observe(Reports.this, new Observer<JSONArray>() {
            @Override
            public void onChanged(JSONArray jsonElements) {
                list = new ArrayList<>();

                //ArrayList<JSONObject> scenariosList = new ArrayList();

                int l = jsonElements.length();

                for (int i = 0; i < l; i++) {
                    try {
                        // scenariosList.add((JSONObject) jsonElements.get(i));
                        ScenarioList_Item list_item = new ScenarioList_Item(
                                ((JSONObject) jsonElements.get(i)).get("sc_id").toString(),
                                ((JSONObject) jsonElements.get(i)).get("scenarionName").toString(),
                                ((JSONObject) jsonElements.get(i)).get("execution_mode").toString(),
                                ((JSONObject) jsonElements.get(i)).get("token").toString());

                        list.add(list_item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new ScenarioAdapter(list, Reports.this, Reports.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    void hide_searchSection() {
        searchLayout.setVisibility(View.INVISIBLE);
        reportLayout.setVisibility(View.VISIBLE);
        h = searchLayout.getLayoutParams().height;
        w = searchLayout.getLayoutParams().width;

        searchLayout.getLayoutParams().height = 0;
        searchLayout.getLayoutParams().width = 0;
        searchLayout.requestLayout();

        showSearchBtn.setVisibility(View.VISIBLE);
    }

    void show_searchLayout() {

        searchLayout.getLayoutParams().height = h;
        searchLayout.getLayoutParams().width = w;
        searchLayout.requestLayout();

        showSearchBtn.setVisibility(View.INVISIBLE);
        searchLayout.setVisibility(View.VISIBLE);
        reportLayout.setVisibility(View.INVISIBLE);
    }
boolean checkInternet(){
    ConnectivityManager connectivityManager
            = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}

}
