package com.example.kltn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProgressTrackingActivity extends AppCompatActivity {

    private TextView tvCompletionPercentage, tvStreakDays;
    private RecyclerView rvSubjectProgress, rvRecentActivities;
    private Button btnBack;
    
    private SubjectProgressAdapter subjectProgressAdapter;
    private RecentActivityAdapter recentActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        initViews();
        setupData();
        setupRecyclerViews();
    }

    private void initViews() {
        tvCompletionPercentage = findViewById(R.id.tv_completion_percentage);
        tvStreakDays = findViewById(R.id.tv_streak_days);
        rvSubjectProgress = findViewById(R.id.rv_subject_progress);
        rvRecentActivities = findViewById(R.id.rv_recent_activities);
        btnBack = findViewById(R.id.btn_back);
        
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupData() {
        // Set overall progress data
        tvCompletionPercentage.setText("75%");
        tvStreakDays.setText("12");
    }

    private void setupRecyclerViews() {
        // Setup subject progress
        List<SubjectProgress> subjectProgressList = createSubjectProgress();
        subjectProgressAdapter = new SubjectProgressAdapter(subjectProgressList);
        rvSubjectProgress.setLayoutManager(new LinearLayoutManager(this));
        rvSubjectProgress.setAdapter(subjectProgressAdapter);

        // Setup recent activities
        List<RecentActivity> recentActivities = createRecentActivities();
        recentActivityAdapter = new RecentActivityAdapter(recentActivities);
        rvRecentActivities.setLayoutManager(new LinearLayoutManager(this));
        rvRecentActivities.setAdapter(recentActivityAdapter);
    }

    private List<SubjectProgress> createSubjectProgress() {
        List<SubjectProgress> subjects = new ArrayList<>();
        subjects.add(new SubjectProgress("Vocabulary", 85, "Great progress!"));
        subjects.add(new SubjectProgress("Grammar", 72, "Keep practicing"));
        subjects.add(new SubjectProgress("Reading", 90, "Excellent!"));
        subjects.add(new SubjectProgress("Listening", 68, "Need more practice"));
        return subjects;
    }

    private List<RecentActivity> createRecentActivities() {
        List<RecentActivity> activities = new ArrayList<>();
        activities.add(new RecentActivity("Completed Animals Quiz", "Scored 95%", "2 hours ago"));
        activities.add(new RecentActivity("Learned 10 new words", "Vocabulary practice", "Yesterday"));
        activities.add(new RecentActivity("Finished Grammar Lesson", "Past tense", "2 days ago"));
        activities.add(new RecentActivity("Watched Video Lesson", "Colors and shapes", "3 days ago"));
        return activities;
    }

    // Subject Progress Data Class
    public static class SubjectProgress {
        private String subject;
        private int progress;
        private String comment;

        public SubjectProgress(String subject, int progress, String comment) {
            this.subject = subject;
            this.progress = progress;
            this.comment = comment;
        }

        public String getSubject() { return subject; }
        public int getProgress() { return progress; }
        public String getComment() { return comment; }
    }

    // Recent Activity Data Class
    public static class RecentActivity {
        private String title;
        private String description;
        private String time;

        public RecentActivity(String title, String description, String time) {
            this.title = title;
            this.description = description;
            this.time = time;
        }

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getTime() { return time; }
    }

    // Subject Progress Adapter
    private static class SubjectProgressAdapter extends RecyclerView.Adapter<SubjectProgressAdapter.ViewHolder> {
        private List<SubjectProgress> subjects;

        public SubjectProgressAdapter(List<SubjectProgress> subjects) {
            this.subjects = subjects;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_topic_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SubjectProgress subject = subjects.get(position);
            holder.tvTopicName.setText(subject.getSubject());
            holder.tvProgress.setText(subject.getProgress() + "%");
            holder.tvDescription.setText(subject.getComment());
        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTopicName, tvProgress, tvDescription;

            ViewHolder(View itemView) {
                super(itemView);
                tvTopicName = itemView.findViewById(R.id.tv_topic_name);
                tvProgress = itemView.findViewById(R.id.tv_progress);
                tvDescription = itemView.findViewById(R.id.tv_description);
            }
        }
    }

    // Recent Activity Adapter
    private static class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.ViewHolder> {
        private List<RecentActivity> activities;

        public RecentActivityAdapter(List<RecentActivity> activities) {
            this.activities = activities;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_activity_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecentActivity activity = activities.get(position);
            holder.tvActivityTitle.setText(activity.getTitle() + " - " + activity.getDescription());
            holder.tvActivityTime.setText(activity.getTime());
        }

        @Override
        public int getItemCount() {
            return activities.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvActivityTitle, tvActivityDescription, tvActivityTime;

            ViewHolder(View itemView) {
                super(itemView);
                tvActivityTitle = itemView.findViewById(R.id.tv_description);
                tvActivityDescription = itemView.findViewById(R.id.tv_description);
                tvActivityTime = itemView.findViewById(R.id.tv_time_ago);
            }
        }
    }
} 