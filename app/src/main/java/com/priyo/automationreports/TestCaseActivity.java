package com.priyo.automationreports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.priyo.automationreports.Adapters.TestCaseAdapter;
import com.priyo.automationreports.Adapters.TestCaseList_Item;
import com.priyo.automationreports.Adapters.TestStep;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class TestCaseActivity extends AppCompatActivity implements TestCaseAdapter.OnTestCaseListener {

    private RecyclerView recyclerView;
    private TestCaseAdapter adapter;
    private List<TestCaseList_Item> list;

    private Dialog stepsDialog;

    private static final String TAG = "TestCaseActivity";
    private String testCasesJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_case);
        recyclerView = (RecyclerView) findViewById(R.id.tc_recycler_view);

        list = new ArrayList<>();

        Intent intent = getIntent();
        testCasesJsonArray = intent.getStringExtra("TestCaseArray");
        JSONArray arr = null;
        try {
            arr = new JSONArray(testCasesJsonArray);
            for (int i = 0; i < arr.length(); i++) {

                JSONArray steps = (JSONArray) ((JSONObject) arr.get(i)).get("testStepsResponse");
                list.add(new TestCaseList_Item(
                        ((JSONObject) arr.get(i)).get("tc_id").toString(),
                        ((JSONObject) arr.get(i)).get("tc_name").toString(),
                        ((JSONObject) arr.get(i)).get("test_status").toString(),
                        ((JSONObject) arr.get(i)).get("module").toString(),
                        ((JSONObject) arr.get(i)).get("release").toString(),
                        ((JSONObject) arr.get(i)).get("cycle").toString(),
                        steps
                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TestCaseAdapter(list, TestCaseActivity.this, TestCaseActivity.this);
        recyclerView.setAdapter(adapter);

        stepsDialog = new Dialog(this);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "Back Pressed");
        super.onBackPressed();
    }

    @Override
    public void onTestCaseClick(int position) {

        TestCaseList_Item item = list.get(position);
        Log.i(TAG, "Clicked "+position);
        JSONArray steps = item.getSteps();
        Log.i(TAG,steps.length()+" stepsTable");

        List<TestStep> steplist = new ArrayList<>();

        for (int i = 0; i < steps.length(); i++) {
            try {
                JSONObject step = (JSONObject) steps.get(i);


                String no = step.get("step_no").toString();
                String na = step.get("step_name").toString();
                String sts = step.get("test_step_status").toString();
                Log.i(TAG,no + " " + na + " " + sts);
                steplist.add(new TestStep(no,na,sts));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initTable(steplist);


    }

    public void initTable(List<TestStep> steplist) {
        stepsDialog.setContentView(R.layout.steps);
        TableLayout stepsTable;

        stepsTable = stepsDialog.findViewById(R.id.table_main);

        TableRow headerRow = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("step #".toUpperCase());
        tv0.setGravity(Gravity.CENTER);
        tv0.setPadding(5,5,5,5);
        headerRow.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" step name ".toUpperCase());
        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(5,5,5,5);
        headerRow.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Status ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(5,5,5,5);
        headerRow.addView(tv2);
        stepsTable.addView(headerRow);


        int stepslength = steplist.size();
        for (int i = 0; i < stepslength; i++) {
            TableRow tbrow = new TableRow(this);
            TextView count = new TextView(this);
            count.setText(steplist.get(i).getStepNo());
            count.setGravity(Gravity.CENTER);
            count.setPadding(5,5,5,5);
            tbrow.addView(count);
            TextView name = new TextView(this);
            name.setText(steplist.get(i).getStepName());
            name.setGravity(Gravity.CENTER);
            name.setPadding(5,5,5,5);
            tbrow.addView(name);
            TextView status = new TextView(this);
            status.setText(steplist.get(i).getStepStatus());
            status.setGravity(Gravity.CENTER);
            status.setPadding(5,5,5,5);
            tbrow.addView(status);
            stepsTable.addView(tbrow);
        }

        stepsDialog.show();

    }
}
