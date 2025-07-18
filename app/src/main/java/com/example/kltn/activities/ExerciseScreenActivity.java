package com.example.kltn.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.kltn.R;
import com.example.kltn.fragments.ExerciseSetListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExerciseScreenActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private String userId;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_screen);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        userId = getIntent().getStringExtra("user_id");
        userEmail = getIntent().getStringExtra("user_email");
        setupTabs();
    }

    private void setupTabs() {
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public Fragment createFragment(int position) {
                if (position == 0)
                    return com.example.kltn.fragments.ExerciseSetListFragment.newInstance("fill_blank", userId);
                else
                    return com.example.kltn.fragments.ExerciseSetListFragment.newInstance("multiple_choice", userId);
            }
            @Override
            public int getItemCount() { return 2; }
        });
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "Điền từ" : "Trắc nghiệm")
        ).attach();
    }
} 