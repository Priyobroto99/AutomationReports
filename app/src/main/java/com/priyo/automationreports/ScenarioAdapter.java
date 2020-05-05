package com.priyo.automationreports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScenarioAdapter extends RecyclerView.Adapter<ScenarioAdapter.ViewHolder> {


    private List<ScenarioList_Item> list;
    private Context context;
    private OnScenarioListener mOnScenarioListener;

    public ScenarioAdapter(List<ScenarioList_Item> list, Context context,OnScenarioListener onScenarioListener) {
        this.list = list;
        this.context = context;
        this.mOnScenarioListener = onScenarioListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scenario_list_item,parent,false);

        return new ViewHolder(v,mOnScenarioListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            ScenarioList_Item scenarioList_item = list.get(position);
            holder.sc_id.setText(scenarioList_item.getSc_id());
            holder.scenarionName.setText(scenarioList_item.getScenarionName());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView sc_id;
        public TextView scenarionName;
        public OnScenarioListener onScenarioListener;

        public ViewHolder(@NonNull View itemView,OnScenarioListener onScenarioListener) {
            super(itemView);

            sc_id = (TextView) itemView.findViewById(R.id.sc_id);
            scenarionName = (TextView) itemView.findViewById(R.id.scenarionName);

            this.onScenarioListener = onScenarioListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onScenarioListener.onScenarioClick(getAdapterPosition());
        }
    }

    public interface OnScenarioListener{
        void onScenarioClick(int position);
    }
}
