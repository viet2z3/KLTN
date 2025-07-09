package com.example.kltn.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kltn.R;
import com.example.kltn.fragments.FragmentFillBlank;
import com.example.kltn.fragments.FragmentMultipleChoice;
import com.google.android.material.tabs.TabLayout;

public class ExerciseScreenActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_screen);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        setupTabs();
    }

    private void setupTabs() {
        TabAdapter adapter = new TabAdapter(this);
        viewPager.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("Fill in the blank"));
        tabLayout.addTab(tabLayout.newTab().setText("Multiple choice"));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private static class TabAdapter extends FragmentStateAdapter {
        public TabAdapter(FragmentActivity fa) { super(fa); }
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) return new FragmentFillBlank();
            else return new FragmentMultipleChoice();
        }
        @Override
        public int getItemCount() { return 2; }
    }
} 