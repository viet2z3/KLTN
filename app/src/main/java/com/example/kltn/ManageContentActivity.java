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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageContentActivity extends AppCompatActivity {
    private TextView tvTitle;
    private RecyclerView rvContent;
    private Button btnAddContent, btnBack;
    private ContentAdapter contentAdapter;
    private List<ContentItem> contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_content);
        initializeViews();
        setupContentData();
        setupRecyclerView();
        setupEventHandlers();
    }

    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        rvContent = findViewById(R.id.rvContent);
        btnAddContent = findViewById(R.id.btnAddContent);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupContentData() {
        contentList = new ArrayList<>();
        contentList.add(new ContentItem("Flashcards: Animals", "Flashcards", "2024-01-10"));
        contentList.add(new ContentItem("Video: Colors", "Videos", "2024-01-12"));
        contentList.add(new ContentItem("Test: Numbers", "Tests", "2024-01-15"));
    }

    private void setupRecyclerView() {
        contentAdapter = new ContentAdapter(contentList, new ContentAdapter.OnContentActionListener() {
            @Override
            public void onEditContent(ContentItem item) {
                editContent(item);
            }
            @Override
            public void onDeleteContent(ContentItem item) {
                deleteContent(item);
            }
        });
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(contentAdapter);
    }

    private void setupEventHandlers() {
        btnAddContent.setOnClickListener(v -> addContent());
        btnBack.setOnClickListener(v -> finish());
    }

    private void addContent() {
        Toast.makeText(this, "Add new content", Toast.LENGTH_SHORT).show();
    }

    private void editContent(ContentItem item) {
        Toast.makeText(this, "Edit content: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void deleteContent(ContentItem item) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Content")
            .setMessage("Are you sure you want to delete this content?")
            .setPositiveButton("Delete", (dialog, which) -> {
                int pos = contentList.indexOf(item);
                contentList.remove(item);
                contentAdapter.notifyItemRemoved(pos);
                Toast.makeText(this, "Content deleted", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    // Content item data class
    private static class ContentItem {
        private String title, type, date;
        public ContentItem(String title, String type, String date) {
            this.title = title; this.type = type; this.date = date;
        }
        public String getTitle() { return title; }
        public String getType() { return type; }
        public String getDate() { return date; }
    }

    // RecyclerView Adapter
    private static class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
        private List<ContentItem> items;
        private OnContentActionListener listener;
        public interface OnContentActionListener {
            void onEditContent(ContentItem item);
            void onDeleteContent(ContentItem item);
        }
        public ContentAdapter(List<ContentItem> items, OnContentActionListener listener) {
            this.items = items; this.listener = listener;
        }
        @NonNull
        @Override
        public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            return new ContentViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
            holder.bind(items.get(position));
        }
        @Override
        public int getItemCount() { return items.size(); }
        class ContentViewHolder extends RecyclerView.ViewHolder {
            private TextView tvContentTitle, tvContentType, tvContentDate;
            private Button btnEdit, btnDelete;
            private ImageView ivContentIcon;
            public ContentViewHolder(@NonNull View itemView) {
                super(itemView);
                tvContentTitle = itemView.findViewById(R.id.tvContentTitle);
                tvContentType = itemView.findViewById(R.id.tvContentType);
                tvContentDate = itemView.findViewById(R.id.tvContentDate);
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                ivContentIcon = itemView.findViewById(R.id.ivContentIcon);
            }
            public void bind(ContentItem item) {
                tvContentTitle.setText(item.getTitle());
                tvContentType.setText(item.getType());
                tvContentDate.setText(item.getDate());
                ivContentIcon.setImageResource(R.drawable.ic_launcher_foreground);
                btnEdit.setOnClickListener(v -> { if (listener != null) listener.onEditContent(item); });
                btnDelete.setOnClickListener(v -> { if (listener != null) listener.onDeleteContent(item); });
            }
        }
    }
} 