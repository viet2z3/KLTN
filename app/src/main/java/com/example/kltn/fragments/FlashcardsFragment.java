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
import com.example.kltn.adapters.ContentAdapter;
import com.example.kltn.models.ContentItem;
import com.example.kltn.utils.DummyDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class FlashcardsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<ContentItem> flashcardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcards, container, false);
        
        initViews(view);
        setupRecyclerView();
        loadFlashcards();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        flashcardList = new ArrayList<>();
        adapter = new ContentAdapter(getContext(), flashcardList);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        
        adapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ContentItem item) {
                Toast.makeText(getContext(), "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // TODO: Navigate to flashcard detail activity
            }

            @Override
            public void onEditClick(ContentItem item) {
                Toast.makeText(getContext(), "Edit: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // TODO: Navigate to edit flashcard activity
            }

            @Override
            public void onDeleteClick(ContentItem item) {
                showDeleteConfirmationDialog(item);
            }
        });
    }

    private void loadFlashcards() {
        // Load dummy data for UI testing
        flashcardList.clear();
        flashcardList.addAll(DummyDataGenerator.getFlashcards());
        adapter.notifyDataSetChanged();
    }

    public void updateFlashcards(List<ContentItem> newList) {
        flashcardList.clear();
        flashcardList.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    public void refreshFlashcards() {
        loadFlashcards();
    }

    private void showDeleteConfirmationDialog(ContentItem item) {
        new android.app.AlertDialog.Builder(requireContext())
            .setTitle("Delete Flashcard")
            .setMessage("Are you sure you want to delete \"" + item.getTitle() + "\"?")
            .setPositiveButton("Delete", (dialog, which) -> {
                // TODO: Delete from Firebase
                Toast.makeText(getContext(), "Deleted: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                refreshFlashcards();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
} 