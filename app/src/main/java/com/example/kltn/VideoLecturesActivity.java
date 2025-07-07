package com.example.kltn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoLecturesActivity extends AppCompatActivity {
    
    // UI Components
    private Button btnBack;
    private RecyclerView rvVideoLessons;
    private VideoAdapter videoAdapter;
    private List<VideoLesson> allVideos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lectures);
        
        initializeViews();
        setupVideoData();
        setupRecyclerView();
    }
    
    private void initializeViews() {
        btnBack = findViewById(R.id.btn_back);
        rvVideoLessons = findViewById(R.id.rv_video_lessons);
        
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void setupVideoData() {
        allVideos = new ArrayList<>();
        allVideos.add(new VideoLesson(
            "Basic Colors",
            "Learn about primary and secondary colors",
            "5:30",
            "Colors",
            R.drawable.ic_launcher_foreground
        ));
        allVideos.add(new VideoLesson(
            "Numbers 1-10",
            "Count from one to ten in English",
            "4:15",
            "Numbers",
            R.drawable.ic_launcher_foreground
        ));
        allVideos.add(new VideoLesson(
            "Animal Names",
            "Learn names of common animals",
            "6:45",
            "Animals",
            R.drawable.ic_launcher_foreground
        ));
        allVideos.add(new VideoLesson(
            "Family Members",
            "Learn family vocabulary",
            "5:20",
            "Family",
            R.drawable.ic_launcher_foreground
        ));
        allVideos.add(new VideoLesson(
            "Basic Greetings",
            "Hello, goodbye, and other greetings",
            "3:55",
            "Greetings",
            R.drawable.ic_launcher_foreground
        ));
        allVideos.add(new VideoLesson(
            "Simple Sentences",
            "Learn to make basic sentences",
            "7:10",
            "Grammar",
            R.drawable.ic_launcher_foreground
        ));
    }
    
    private void setupRecyclerView() {
        videoAdapter = new VideoAdapter(allVideos, video -> {
            // Handle video selection
            Toast.makeText(this, "Playing: " + video.getTitle(), Toast.LENGTH_SHORT).show();
            // In a real app, you would launch a video player activity here
        });
        
        rvVideoLessons.setLayoutManager(new LinearLayoutManager(this));
        rvVideoLessons.setAdapter(videoAdapter);
    }
    
    // Video Lesson data class
    private static class VideoLesson {
        private String title;
        private String description;
        private String duration;
        private String topic;
        private int thumbnailResId;
        
        public VideoLesson(String title, String description, String duration, String topic, int thumbnailResId) {
            this.title = title;
            this.description = description;
            this.duration = duration;
            this.topic = topic;
            this.thumbnailResId = thumbnailResId;
        }
        
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getDuration() { return duration; }
        public String getTopic() { return topic; }
        public int getThumbnailResId() { return thumbnailResId; }
    }
    
    // RecyclerView Adapter
    private static class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
        private List<VideoLesson> videos;
        private OnVideoClickListener listener;
        
        public interface OnVideoClickListener {
            void onVideoClick(VideoLesson video);
        }
        
        public VideoAdapter(List<VideoLesson> videos, OnVideoClickListener listener) {
            this.videos = videos;
            this.listener = listener;
        }
        
        @NonNull
        @Override
        public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_lesson, parent, false);
            return new VideoViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
            VideoLesson video = videos.get(position);
            holder.bind(video);
        }
        
        @Override
        public int getItemCount() {
            return videos.size();
        }
        
        class VideoViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivThumbnail;
            private TextView tvTitle;
            private TextView tvDescription;
            private TextView tvDuration;
            private TextView tvTopic;
            private Button btnPlay;
            
            public VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvDuration = itemView.findViewById(R.id.tvDuration);
                tvTopic = itemView.findViewById(R.id.tvTopic);
                btnPlay = itemView.findViewById(R.id.btnPlay);
            }
            
            public void bind(VideoLesson video) {
                tvTitle.setText(video.getTitle());
                tvDescription.setText(video.getDescription());
                tvDuration.setText(video.getDuration());
                tvTopic.setText(video.getTopic());
                ivThumbnail.setImageResource(video.getThumbnailResId());
                
                btnPlay.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onVideoClick(video);
                    }
                });
            }
        }
    }
} 