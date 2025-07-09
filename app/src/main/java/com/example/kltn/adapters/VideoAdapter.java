package com.example.kltn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.VideoLesson;
import com.example.kltn.R;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
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

    public class VideoViewHolder extends RecyclerView.ViewHolder {
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