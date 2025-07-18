package com.example.kltn.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.R;
import com.example.kltn.models.ContentItem;
import java.util.List;

public class FlashcardSetAdapter extends RecyclerView.Adapter<FlashcardSetAdapter.ViewHolder> {
    private List<ContentItem> flashcardSets;

    public FlashcardSetAdapter(List<ContentItem> flashcardSets) {
        this.flashcardSets = flashcardSets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentItem item = flashcardSets.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return flashcardSets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvFlashcardSetTitle);
            tvDescription = itemView.findViewById(R.id.tvFlashcardSetDescription);
        }
    }
} 