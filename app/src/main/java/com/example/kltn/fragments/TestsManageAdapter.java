package com.example.kltn.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.R;
import com.example.kltn.models.TestSet;
import java.util.List;

public class TestsManageAdapter extends RecyclerView.Adapter<TestsManageAdapter.ViewHolder> {
    private List<TestSet> tests;
    public TestsManageAdapter(List<TestSet> tests) {
        this.tests = tests;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_manage, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestSet test = tests.get(position);
        holder.tvTitle.setText(test.title);
        holder.tvDuration.setText("Duration: " + test.duration + " min");
        holder.tvMaxScore.setText("Max Score: " + test.maxScore);
    }
    @Override
    public int getItemCount() { return tests.size(); }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDuration, tvMaxScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTestTitle);
            tvDuration = itemView.findViewById(R.id.tvTestDuration);
            tvMaxScore = itemView.findViewById(R.id.tvTestMaxScore);
        }
    }
} 