package com.example.kltn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.ContentItem;
import com.example.kltn.R;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
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
    public class ContentViewHolder extends RecyclerView.ViewHolder {
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