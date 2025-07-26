package com.example.kltn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kltn.R;
import com.example.kltn.fragments.FlashcardsFragment;
import com.example.kltn.fragments.ExercisesFragment;
import com.example.kltn.fragments.TestsFragment;
import com.example.kltn.fragments.VideosFragment;
import com.example.kltn.models.ContentItem;
import com.example.kltn.utils.DummyDataGenerator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class ManageContentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private EditText etSearch;
    private ImageView btnBack, btnAddContent;
    private ViewPagerAdapter viewPagerAdapter;
    private FlashcardsFragment flashcardsFragment;
//    private TestsFragment testsFragment;
    private Spinner spinnerCourse;
    private List<String> courseIds = new ArrayList<>();
    private String selectedCourseId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_content);

        initViews();
        setupTabLayout();
        setupClickListeners();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        etSearch = findViewById(R.id.etSearch);
        btnBack = findViewById(R.id.btnBack);
        btnAddContent = findViewById(R.id.btnAddContent);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        loadCoursesToSpinner();
    }

    private void loadCoursesToSpinner() {
        FirebaseFirestore.getInstance().collection("courses").get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<String> courseNames = new ArrayList<>();
                courseIds.clear();
                courseNames.add("Tất cả khóa học");
                courseIds.add("");
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    String name = doc.getString("name");
                    String id = doc.getId();
                    courseNames.add(name);
                    courseIds.add(id);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCourse.setAdapter(adapter);
                spinnerCourse.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                        selectedCourseId = courseIds.get(position);
                        // Truyền selectedCourseId vào fragment hiện tại
                        int tab = viewPager.getCurrentItem();
                        androidx.fragment.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + tab);
                        if (fragment == null) {
                            fragment = getSupportFragmentManager().findFragmentById(R.id.viewPager);
                        }
                        if (fragment instanceof com.example.kltn.fragments.FlashcardsFragment) {
                            ((com.example.kltn.fragments.FlashcardsFragment) fragment).setCourseId(selectedCourseId);
                        } else if (fragment instanceof com.example.kltn.fragments.ExercisesFragment) {
                            ((com.example.kltn.fragments.ExercisesFragment) fragment).setCourseId(selectedCourseId);
                        } else if (fragment instanceof com.example.kltn.fragments.TestsFragment) {
                            ((com.example.kltn.fragments.TestsFragment) fragment).setCourseId(selectedCourseId);
                        } else if (fragment instanceof com.example.kltn.fragments.VideosFragment) {
                            ((com.example.kltn.fragments.VideosFragment) fragment).setCourseId(selectedCourseId);
                        }
                    }
                    @Override
                    public void onNothingSelected(android.widget.AdapterView<?> parent) {}
                });
            });
    }

    private void setupTabLayout() {
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Flashcards");
                    break;
                case 1:
                    tab.setText("Exercises");
                    break;
                case 2:
                    tab.setText("Tests");
                    break;
                case 3:
                    tab.setText("Video");
                    break;
            }
        }).attach();

        // Set default tab to Flashcards
        viewPager.setCurrentItem(0);

        // Khi chuyển tab, cũng truyền selectedCourseId vào fragment mới
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                androidx.fragment.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + position);
                if (fragment == null) {
                    fragment = getSupportFragmentManager().findFragmentById(R.id.viewPager);
                }
                if (fragment instanceof com.example.kltn.fragments.FlashcardsFragment) {
                    ((com.example.kltn.fragments.FlashcardsFragment) fragment).setCourseId(selectedCourseId);
                } else if (fragment instanceof com.example.kltn.fragments.ExercisesFragment) {
                    ((com.example.kltn.fragments.ExercisesFragment) fragment).setCourseId(selectedCourseId);
                } else if (fragment instanceof com.example.kltn.fragments.TestsFragment) {
                    ((com.example.kltn.fragments.TestsFragment) fragment).setCourseId(selectedCourseId);
                } else if (fragment instanceof com.example.kltn.fragments.VideosFragment) {
                    ((com.example.kltn.fragments.VideosFragment) fragment).setCourseId(selectedCourseId);
                }
            }
        });
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Add content button
        btnAddContent.setOnClickListener(v -> {
            int currentTab = viewPager.getCurrentItem();
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + currentTab);

            if (currentTab == 0) {
                if (currentFragment instanceof com.example.kltn.fragments.FlashcardsFragment) {
                    ((com.example.kltn.fragments.FlashcardsFragment) currentFragment).showAddEditFlashcardSetDialog(null);
                }
            } else if (currentTab == 1) {
                if (currentFragment instanceof com.example.kltn.fragments.ExercisesFragment) {
                    ((com.example.kltn.fragments.ExercisesFragment) currentFragment).showAddEditExerciseDialog(null);
                }
            } else if (currentTab == 2) {
                if (currentFragment instanceof com.example.kltn.fragments.TestsFragment) {
                    ((com.example.kltn.fragments.TestsFragment) currentFragment).showAddEditTestDialog(null);
                }
            } else if (currentTab == 3) {
                if (currentFragment instanceof com.example.kltn.fragments.VideosFragment) {
                    ((com.example.kltn.fragments.VideosFragment) currentFragment).showAddEditVideoDialog(null);
                }
            }
        });

        // Search functionality
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                int tab = viewPager.getCurrentItem();
                androidx.fragment.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + tab);
                if (fragment == null) {
                    fragment = getSupportFragmentManager().findFragmentById(R.id.viewPager);
                }
                if (fragment instanceof com.example.kltn.fragments.FlashcardsFragment) {
                    ((com.example.kltn.fragments.FlashcardsFragment) fragment).setSearchQuery(query);
                } else if (fragment instanceof com.example.kltn.fragments.ExercisesFragment) {
                    ((com.example.kltn.fragments.ExercisesFragment) fragment).setSearchQuery(query);
                } else if (fragment instanceof com.example.kltn.fragments.TestsFragment) {
                    ((com.example.kltn.fragments.TestsFragment) fragment).setSearchQuery(query);
                } else if (fragment instanceof com.example.kltn.fragments.VideosFragment) {
                    ((com.example.kltn.fragments.VideosFragment) fragment).setSearchQuery(query);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        etSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard();
            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            android.view.inputmethod.InputMethodManager imm = 
                (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void addNewTest() {
        showCreateTestDialog();
    }

    private void performSearch(String query) {
        if (query.trim().isEmpty()) {
            // If search is empty, reload all content
            if (viewPager.getCurrentItem() == 0) {
                // Xóa gọi refreshFlashcards()
                // if (flashcardsFragment != null) {
                //     flashcardsFragment.refreshFlashcards();
                // }
            } else {
//                if (testsFragment != null) {
//                    testsFragment.refreshTests();
//                }
            }
            return;
        }

        // Perform search using dummy data
        List<ContentItem> searchResults;
        if (viewPager.getCurrentItem() == 0) {
            // Xóa gọi updateFlashcards()
            // if (flashcardsFragment != null) {
            //     flashcardsFragment.updateFlashcards(searchResults);
            // }
        } else {
            searchResults = DummyDataGenerator.searchTests(query);
//            if (testsFragment != null) {
//                testsFragment.updateTests(searchResults);
//            }
        }
    }

    private void showCreateTestDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_test, null);
        builder.setView(dialogView);

        android.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Initialize views
        EditText etTestTitle = dialogView.findViewById(R.id.etTestTitle);
        EditText etTestDescription = dialogView.findViewById(R.id.etTestDescription);
        AutoCompleteTextView spinnerTestType = dialogView.findViewById(R.id.spinnerTestType);
        TextView tvQuestionCount = dialogView.findViewById(R.id.tvQuestionCount);
        Button btnAddQuestion = dialogView.findViewById(R.id.btnAddQuestion);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnCreate = dialogView.findViewById(R.id.btnCreate);
        ImageView btnClose = dialogView.findViewById(R.id.btnClose);
        LinearLayout containerQuestions = dialogView.findViewById(R.id.containerQuestions);

        // Setup test type spinner
        String[] testTypes = {"Multiple Choice", "True/False", "Fill in the Blank", "Essay"};
        android.widget.ArrayAdapter<String> testTypeAdapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, testTypes);
        spinnerTestType.setAdapter(testTypeAdapter);

        // Question counter
        final int[] questionCount = {0};

        // Add question button
        btnAddQuestion.setOnClickListener(v -> {
            questionCount[0]++;
            addQuestionItem(containerQuestions, questionCount[0], tvQuestionCount);
        });

        // Close button
        btnClose.setOnClickListener(v -> dialog.dismiss());
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Create button
        btnCreate.setOnClickListener(v -> {
            String title = etTestTitle.getText().toString().trim();
            String description = etTestDescription.getText().toString().trim();
            String testType = spinnerTestType.getText().toString();

            if (title.isEmpty()) {
                etTestTitle.setError("Title is required");
                return;
            }

            if (questionCount[0] == 0) {
                Toast.makeText(this, "Please add at least one question", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create test
            ContentItem newTest = new ContentItem(
                String.valueOf(System.currentTimeMillis()),
                title,
                description,
                "",
                "test",
                testType
            );

            Toast.makeText(this, "Test created successfully!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            // Refresh the tests list
//            if (testsFragment != null) {
//                testsFragment.refreshTests();
//            }
        });

        // Add first question automatically
        btnAddQuestion.performClick();
    }

    private void addQuestionItem(LinearLayout container, int questionNumber, TextView tvQuestionCount) {
        View questionView = getLayoutInflater().inflate(R.layout.item_question_edit, container, false);
        
        TextView tvQuestionNumber = questionView.findViewById(R.id.tvQuestionNumber);
        EditText etQuestionText = questionView.findViewById(R.id.etQuestionText);
        EditText etOptionA = questionView.findViewById(R.id.etOptionA);
        EditText etOptionB = questionView.findViewById(R.id.etOptionB);
        EditText etOptionC = questionView.findViewById(R.id.etOptionC);
        EditText etOptionD = questionView.findViewById(R.id.etOptionD);
        android.widget.RadioButton rbOptionA = questionView.findViewById(R.id.rbOptionA);
        android.widget.RadioButton rbOptionB = questionView.findViewById(R.id.rbOptionB);
        android.widget.RadioButton rbOptionC = questionView.findViewById(R.id.rbOptionC);
        android.widget.RadioButton rbOptionD = questionView.findViewById(R.id.rbOptionD);
        ImageView btnRemoveQuestion = questionView.findViewById(R.id.btnRemoveQuestion);

        tvQuestionNumber.setText("Question " + questionNumber);

        btnRemoveQuestion.setOnClickListener(v -> {
            container.removeView(questionView);
            updateQuestionCount(container, tvQuestionCount);
        });

        container.addView(questionView);
        updateQuestionCount(container, tvQuestionCount);
    }

    private void updateQuestionCount(LinearLayout container, TextView tvQuestionCount) {
        int count = container.getChildCount();
        tvQuestionCount.setText(count + " questions");
    }

    // ViewPager Adapter
    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FlashcardsFragment();
                case 1:
                    return new ExercisesFragment();
                case 2:
                    return new TestsFragment();
                case 3:
                    return new VideosFragment();
                default:
                    return new FlashcardsFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 4; // 4 tabs: Flashcards, Exercises, Tests, Videos
        }
    }
} 