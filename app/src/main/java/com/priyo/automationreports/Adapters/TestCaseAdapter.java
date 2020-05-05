package com.priyo.automationreports.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.priyo.automationreports.R;
import com.priyo.automationreports.TestCaseActivity;

import org.json.JSONObject;

import java.util.List;

public class TestCaseAdapter extends RecyclerView.Adapter<TestCaseAdapter.ViewHolder> {


    private List<TestCaseList_Item> list;
    private Context context;
    private OnTestCaseListener mOnTestCaseListener;

    public TestCaseAdapter(List<TestCaseList_Item> list, Context context, OnTestCaseListener onTestCaseListener) {
        this.list = list;
        this.context = context;
        this.mOnTestCaseListener = onTestCaseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.testcase_list_item,parent,false);

        return new ViewHolder(v,mOnTestCaseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TestCaseList_Item TestCaseList_item = list.get(position);


        holder.tc_id.setText(TestCaseList_item.getTc_id());
        holder.test_status.setText(TestCaseList_item.getTest_status());
        holder.tc_name.setText(TestCaseList_item.getTc_name());
        holder.module.setText(TestCaseList_item.getModule());
        holder.release.setText(TestCaseList_item.getRelease());
        holder.cycle.setText(TestCaseList_item.getCycle());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tc_id;
        public TextView test_status;
        public TextView tc_name;
        public TextView module;
        public TextView release;
        public TextView cycle;
        public MaterialCardView tc;

        public OnTestCaseListener onTestCaseListener;

        public ViewHolder(@NonNull View itemView,OnTestCaseListener onTestCaseListener) {
            super(itemView);

            tc_id = (TextView) itemView.findViewById(R.id.tc_id);
            test_status = (TextView) itemView.findViewById(R.id.test_status);
            tc_name = (TextView) itemView.findViewById(R.id.tc_name);
            module = (TextView) itemView.findViewById(R.id.module);
            release = (TextView) itemView.findViewById(R.id.release);
            cycle = (TextView) itemView.findViewById(R.id.cycle);
            tc = itemView.findViewById(R.id.testCaseCard);

            this.onTestCaseListener = onTestCaseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTestCaseListener.onTestCaseClick(getAdapterPosition());
        }
    }

    public interface OnTestCaseListener{
        void onTestCaseClick(int position);
    }
}
