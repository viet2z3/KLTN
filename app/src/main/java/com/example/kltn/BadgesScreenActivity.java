package com.example.kltn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BadgesScreenActivity extends AppCompatActivity {

    private TextView tvEarnedBadges, tvTotalBadges;
    private RecyclerView rvBadges;
    
    private String userEmail;
    private BadgeAdapter badgeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges_screen);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        initViews();
        setupData();
    }

    private void initViews() {
        tvEarnedBadges = findViewById(R.id.tv_badges_earned);
        tvTotalBadges = findViewById(R.id.tv_total_badges);
        rvBadges = findViewById(R.id.rv_badges);
    }

    private void setupData() {
        // Set badge statistics
        tvEarnedBadges.setText("4");
        tvTotalBadges.setText("8");

        // Setup badges grid
        setupBadgesGrid();
    }

    private void setupBadgesGrid() {
        List<Badge> badges = createBadges();
        badgeAdapter = new BadgeAdapter(badges);
        rvBadges.setLayoutManager(new GridLayoutManager(this, 2));
        rvBadges.setAdapter(badgeAdapter);
    }

    private List<Badge> createBadges() {
        List<Badge> badges = new ArrayList<>();
        
        // Earned badges
        badges.add(new Badge("First Lesson", "Complete your first lesson", true));
        badges.add(new Badge("Week Warrior", "Complete 7 days in a row", true));
        badges.add(new Badge("Perfect Score", "Score 100% on any quiz", true));
        badges.add(new Badge("Vocabulary Master", "Learn 50 new words", true));
        
        // Locked badges
        badges.add(new Badge("Grammar Expert", "Complete all grammar lessons", false));
        badges.add(new Badge("Reading Champion", "Read 100 passages", false));
        badges.add(new Badge("Speaking Star", "Practice speaking for 10 hours", false));
        badges.add(new Badge("Listening Pro", "Complete 50 listening exercises", false));
        
        return badges;
    }

    // Badge Data Class
    public static class Badge {
        private String name;
        private String description;
        private boolean isEarned;

        public Badge(String name, String description, boolean isEarned) {
            this.name = name;
            this.description = description;
            this.isEarned = isEarned;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public boolean isEarned() { return isEarned; }
    }

    // Badge Adapter
    private static class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {
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

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivBadgeIcon;
            TextView tvBadgeName, tvBadgeDescription, tvBadgeStatus;

            ViewHolder(View itemView) {
                super(itemView);
                ivBadgeIcon = itemView.findViewById(R.id.iv_badge_icon);
                tvBadgeName = itemView.findViewById(R.id.tv_badge_name);
                tvBadgeDescription = itemView.findViewById(R.id.tv_badge_description);
                tvBadgeStatus = itemView.findViewById(R.id.tv_badge_status);
            }
        }
    }
} 