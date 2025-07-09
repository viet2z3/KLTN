package com.example.kltn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.Badge;
import com.example.kltn.R;
import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {
    private List<Badge> badges;

    public BadgeAdapter(List<Badge> badges) {
        this.badges = badges;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Badge badge = badges.get(position);
        holder.tvBadgeName.setText(badge.getName());
        holder.tvBadgeDescription.setText(badge.getDescription());
        if (badge.isEarned()) {
            holder.ivBadgeIcon.setBackgroundTintList(holder.itemView.getContext()
                    .getResources().getColorStateList(R.color.primary_blue));
            holder.tvBadgeStatus.setText(holder.itemView.getContext()
                    .getString(R.string.badge_earned));
            holder.tvBadgeStatus.setBackgroundTintList(holder.itemView.getContext()
                    .getResources().getColorStateList(R.color.success_green));
        } else {
            holder.ivBadgeIcon.setBackgroundTintList(holder.itemView.getContext()
                    .getResources().getColorStateList(R.color.medium_gray));
            holder.tvBadgeStatus.setText(holder.itemView.getContext()
                    .getString(R.string.badge_locked));
            holder.tvBadgeStatus.setBackgroundTintList(holder.itemView.getContext()
                    .getResources().getColorStateList(R.color.dark_gray));
        }
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBadgeIcon;
        TextView tvBadgeName, tvBadgeDescription, tvBadgeStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBadgeIcon = itemView.findViewById(R.id.iv_badge_icon);
            tvBadgeName = itemView.findViewById(R.id.tv_badge_name);
            tvBadgeDescription = itemView.findViewById(R.id.tv_badge_description);
            tvBadgeStatus = itemView.findViewById(R.id.tv_badge_status);
        }
    }
} 