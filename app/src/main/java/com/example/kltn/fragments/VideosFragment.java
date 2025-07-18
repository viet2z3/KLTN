package com.example.kltn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.R;
import com.example.kltn.fragments.VideosManageAdapter;
import com.example.kltn.models.VideoLesson;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment {
    private RecyclerView recyclerView;
    private VideosManageAdapter adapter;
    private List<VideoLesson> videos = new ArrayList<>();
    private String courseId = "";
    private String searchQuery = "";

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        loadVideos();
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query == null ? "" : query.trim().toLowerCase();
        filterAndShow();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        recyclerView = view.findViewById(R.id.rvVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VideosManageAdapter(videos);
        recyclerView.setAdapter(adapter);
        loadVideos();
        return view;
    }

    private void loadVideos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (courseId != null && !courseId.isEmpty()) {
            db.collection("video_lectures").whereEqualTo("course_id", courseId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    videos.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        VideoLesson v = new VideoLesson();
                        v.id = doc.getId();
                        v.title = doc.getString("title");
                        v.description = doc.getString("description");
                        v.duration = doc.getString("duration");
                        v.topic = doc.getString("topic");
                        v.thumbnailUrl = doc.getString("thumbnail_url");
                        v.videoUrl = doc.getString("video_url");
                        videos.add(v);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải videos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            db.collection("video_lectures").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    videos.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        VideoLesson v = new VideoLesson();
                        v.id = doc.getId();
                        v.title = doc.getString("title");
                        v.description = doc.getString("description");
                        v.duration = doc.getString("duration");
                        v.topic = doc.getString("topic");
                        v.thumbnailUrl = doc.getString("thumbnail_url");
                        v.videoUrl = doc.getString("video_url");
                        videos.add(v);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải videos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void filterAndShow() {
        List<VideoLesson> filtered = new ArrayList<>();
        if (searchQuery.isEmpty()) {
            filtered.addAll(videos);
        } else {
            for (VideoLesson v : videos) {
                if ((v.title != null && v.title.toLowerCase().contains(searchQuery)) ||
                    (v.description != null && v.description.toLowerCase().contains(searchQuery)) ||
                    (v.topic != null && v.topic.toLowerCase().contains(searchQuery))) {
                    filtered.add(v);
                }
            }
        }
        adapter = new VideosManageAdapter(filtered);
        recyclerView.setAdapter(adapter);
    }
} 