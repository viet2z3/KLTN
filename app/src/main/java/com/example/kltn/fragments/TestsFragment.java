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

public class TestsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<ContentItem> testList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        
        initViews(view);
        setupRecyclerView();
        loadTests();
        
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        testList = new ArrayList<>();
        adapter = new ContentAdapter(getContext(), testList);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        
        adapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ContentItem item) {
                Toast.makeText(getContext(), "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // TODO: Navigate to test detail activity
            }

            @Override
            public void onEditClick(ContentItem item) {
                Toast.makeText(getContext(), "Edit: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // TODO: Navigate to edit test activity
            }

            @Override
            public void onDeleteClick(ContentItem item) {
                showDeleteConfirmationDialog(item);
            }
        });
    }

    private void loadTests() {
        // Load dummy data for UI testing
        testList.clear();
        testList.addAll(DummyDataGenerator.getTests());
        adapter.notifyDataSetChanged();
    }

    public void updateTests(List<ContentItem> newList) {
        testList.clear();
        testList.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    public void refreshTests() {
        loadTests();
    }

    private void showDeleteConfirmationDialog(ContentItem item) {
        new android.app.AlertDialog.Builder(requireContext())
            .setTitle("Delete Test")
            .setMessage("Are you sure you want to delete \"" + item.getTitle() + "\"?")
            .setPositiveButton("Delete", (dialog, which) -> {
                // TODO: Delete from Firebase
                Toast.makeText(getContext(), "Deleted: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                refreshTests();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
} 