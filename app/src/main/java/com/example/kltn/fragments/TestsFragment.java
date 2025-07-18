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
import com.example.kltn.fragments.TestsManageAdapter;
import com.example.kltn.models.TestSet;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class TestsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TestsManageAdapter adapter;
    private List<TestSet> tests = new ArrayList<>();
    private String courseId = "";
    private String searchQuery = "";

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        loadTests();
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query == null ? "" : query.trim().toLowerCase();
        filterAndShow();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        recyclerView = view.findViewById(R.id.rvTests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TestsManageAdapter(tests);
        recyclerView.setAdapter(adapter);
        loadTests();
        return view;
    }

    private void loadTests() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (courseId != null && !courseId.isEmpty()) {
            db.collection("tests").whereEqualTo("course_id", courseId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    tests.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        TestSet test = new TestSet();
                        test.id = doc.getId();
                        test.title = doc.getString("title");
                        test.duration = doc.contains("duration") ? doc.getLong("duration").intValue() : 0;
                        test.maxScore = doc.contains("max_score") ? doc.getLong("max_score").intValue() : 0;
                        tests.add(test);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải tests: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            db.collection("tests").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    tests.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        TestSet test = new TestSet();
                        test.id = doc.getId();
                        test.title = doc.getString("title");
                        test.duration = doc.contains("duration") ? doc.getLong("duration").intValue() : 0;
                        test.maxScore = doc.contains("max_score") ? doc.getLong("max_score").intValue() : 0;
                        tests.add(test);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải tests: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void filterAndShow() {
        List<TestSet> filtered = new ArrayList<>();
        if (searchQuery.isEmpty()) {
            filtered.addAll(tests);
        } else {
            for (TestSet test : tests) {
                if (test.title != null && test.title.toLowerCase().contains(searchQuery)) {
                    filtered.add(test);
                }
            }
        }
        adapter = new TestsManageAdapter(filtered);
        recyclerView.setAdapter(adapter);
    }
} 