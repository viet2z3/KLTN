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
import com.example.kltn.models.ContentItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ExercisesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExercisesAdapter adapter;
    private List<ContentItem> exercises = new ArrayList<>();
    private String courseId = "";
    private String searchQuery = "";

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        loadExercises();
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query == null ? "" : query.trim().toLowerCase();
        filterAndShow();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        recyclerView = view.findViewById(R.id.rvExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExercisesAdapter(exercises);
        recyclerView.setAdapter(adapter);
        loadExercises();
        return view;
    }

    private void loadExercises() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (courseId != null && !courseId.isEmpty()) {
            db.collection("exercises").whereEqualTo("course_id", courseId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    exercises.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ContentItem item = new ContentItem();
                        item.setId(doc.getId());
                        item.setTitle(doc.getString("title"));
                        item.setDescription(doc.getString("type"));
                        exercises.add(item);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải exercises: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            db.collection("exercises").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    exercises.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ContentItem item = new ContentItem();
                        item.setId(doc.getId());
                        item.setTitle(doc.getString("title"));
                        item.setDescription(doc.getString("type"));
                        exercises.add(item);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải exercises: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void filterAndShow() {
        List<ContentItem> filtered = new ArrayList<>();
        if (searchQuery.isEmpty()) {
            filtered.addAll(exercises);
        } else {
            for (ContentItem item : exercises) {
                if ((item.getTitle() != null && item.getTitle().toLowerCase().contains(searchQuery)) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchQuery))) {
                    filtered.add(item);
                }
            }
        }
        adapter = new ExercisesAdapter(filtered);
        recyclerView.setAdapter(adapter);
    }
} 