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

public class FlashcardsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FlashcardSetAdapter adapter;
    private List<ContentItem> flashcardSets = new ArrayList<>();
    private String courseId = "";
    private String searchQuery = "";

    public void setCourseId(String courseId) {
        this.courseId = courseId;
        loadFlashcardSets();
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query == null ? "" : query.trim().toLowerCase();
        filterAndShow();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcards, container, false);
        recyclerView = view.findViewById(R.id.rvFlashcardSets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FlashcardSetAdapter(flashcardSets);
        recyclerView.setAdapter(adapter);
        loadFlashcardSets();
        return view;
    }

    private void loadFlashcardSets() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (courseId != null && !courseId.isEmpty()) {
            db.collection("flashcard_sets").whereEqualTo("course_id", courseId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    flashcardSets.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ContentItem item = new ContentItem();
                        item.setId(doc.getId());
                        item.setTitle(doc.getString("title"));
                        item.setDescription(doc.getString("description"));
                        flashcardSets.add(item);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải flashcard sets: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            db.collection("flashcard_sets").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    flashcardSets.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ContentItem item = new ContentItem();
                        item.setId(doc.getId());
                        item.setTitle(doc.getString("title"));
                        item.setDescription(doc.getString("description"));
                        flashcardSets.add(item);
                    }
                    filterAndShow();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi tải flashcard sets: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void filterAndShow() {
        List<ContentItem> filtered = new ArrayList<>();
        if (searchQuery.isEmpty()) {
            filtered.addAll(flashcardSets);
        } else {
            for (ContentItem item : flashcardSets) {
                if ((item.getTitle() != null && item.getTitle().toLowerCase().contains(searchQuery)) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchQuery))) {
                    filtered.add(item);
                }
            }
        }
        adapter = new FlashcardSetAdapter(filtered);
        recyclerView.setAdapter(adapter);
    }
} 