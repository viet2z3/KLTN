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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kltn.R;
import com.example.kltn.fragments.FlashcardsFragment;
import com.example.kltn.fragments.TestsFragment;
import com.example.kltn.models.ContentItem;
import com.example.kltn.utils.DummyDataGenerator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.ArrayList;

public class ManageContentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private EditText etSearch;
    private ImageView btnBack, btnAddContent;
    private ViewPagerAdapter viewPagerAdapter;
    private FlashcardsFragment flashcardsFragment;
    private TestsFragment testsFragment;

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
                    tab.setText("Tests");
                    break;
            }
        }).attach();

        // Set default tab to Flashcards
        viewPager.setCurrentItem(0);
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Add content button
        btnAddContent.setOnClickListener(v -> {
            int currentTab = viewPager.getCurrentItem();
            if (currentTab == 0) {
                // Add new flashcard
                addNewFlashcard();
            } else {
                // Add new test
                addNewTest();
            }
        });

        // Search functionality
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
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

    private void addNewFlashcard() {
        showCreateFlashcardDialog();
    }

    private void addNewTest() {
        showCreateTestDialog();
    }

    private void performSearch(String query) {
        if (query.trim().isEmpty()) {
            // If search is empty, reload all content
            if (viewPager.getCurrentItem() == 0) {
                if (flashcardsFragment != null) {
                    flashcardsFragment.refreshFlashcards();
                }
            } else {
                if (testsFragment != null) {
                    testsFragment.refreshTests();
                }
            }
            return;
        }

        // Perform search using dummy data
        List<ContentItem> searchResults;
        if (viewPager.getCurrentItem() == 0) {
            searchResults = DummyDataGenerator.searchFlashcards(query);
            if (flashcardsFragment != null) {
                flashcardsFragment.updateFlashcards(searchResults);
            }
        } else {
            searchResults = DummyDataGenerator.searchTests(query);
            if (testsFragment != null) {
                testsFragment.updateTests(searchResults);
            }
        }
    }

    private void showCreateFlashcardDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_flashcard_set, null);
        builder.setView(dialogView);

        android.app.AlertDialog dialog = builder.create();
        dialog.show();

        // Initialize views
        EditText etSetTitle = dialogView.findViewById(R.id.etSetTitle);
        EditText etSetDescription = dialogView.findViewById(R.id.etSetDescription);
        AutoCompleteTextView spinnerCategory = dialogView.findViewById(R.id.spinnerCategory);
        TextView tvCardCount = dialogView.findViewById(R.id.tvCardCount);
        Button btnAddCard = dialogView.findViewById(R.id.btnAddCard);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnCreate = dialogView.findViewById(R.id.btnCreate);
        ImageView btnClose = dialogView.findViewById(R.id.btnClose);
        LinearLayout containerFlashcards = dialogView.findViewById(R.id.containerFlashcards);

        // Setup category spinner
        String[] categories = {"Vocabulary", "Grammar", "Reading", "Listening", "Writing", "Speaking"};
        android.widget.ArrayAdapter<String> categoryAdapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        spinnerCategory.setAdapter(categoryAdapter);

        // Card counter
        final int[] cardCount = {0};

        // Add card button
        btnAddCard.setOnClickListener(v -> {
            cardCount[0]++;
            addFlashcardItem(containerFlashcards, cardCount[0], tvCardCount);
        });

        // Close button
        btnClose.setOnClickListener(v -> dialog.dismiss());
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Create button
        btnCreate.setOnClickListener(v -> {
            String title = etSetTitle.getText().toString().trim();
            String description = etSetDescription.getText().toString().trim();
            String category = spinnerCategory.getText().toString();

            if (title.isEmpty()) {
                etSetTitle.setError("Title is required");
                return;
            }

            if (cardCount[0] == 0) {
                Toast.makeText(this, "Please add at least one flashcard", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create flashcard set
            ContentItem newFlashcardSet = new ContentItem(
                String.valueOf(System.currentTimeMillis()),
                title,
                description,
                "",
                "flashcard",
                category
            );

            Toast.makeText(this, "Flashcard set created successfully!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            // Refresh the flashcards list
            if (flashcardsFragment != null) {
                flashcardsFragment.refreshFlashcards();
            }
        });

        // Add first card automatically
        btnAddCard.performClick();
    }

    private void addFlashcardItem(LinearLayout container, int cardNumber, TextView tvCardCount) {
        View cardView = getLayoutInflater().inflate(R.layout.item_flashcard_edit, container, false);
        
        TextView tvCardNumber = cardView.findViewById(R.id.tvCardNumber);
        EditText etFrontText = cardView.findViewById(R.id.etFrontText);
        EditText etBackText = cardView.findViewById(R.id.etBackText);
        ImageView btnRemoveCard = cardView.findViewById(R.id.btnRemoveCard);

        tvCardNumber.setText("Card " + cardNumber);

        btnRemoveCard.setOnClickListener(v -> {
            container.removeView(cardView);
            updateCardCount(container, tvCardCount);
        });

        container.addView(cardView);
        updateCardCount(container, tvCardCount);
    }

    private void updateCardCount(LinearLayout container, TextView tvCardCount) {
        int count = container.getChildCount();
        tvCardCount.setText(count + " cards");
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
            if (testsFragment != null) {
                testsFragment.refreshTests();
            }
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
                    flashcardsFragment = new FlashcardsFragment();
                    return flashcardsFragment;
                case 1:
                    testsFragment = new TestsFragment();
                    return testsFragment;
                default:
                    flashcardsFragment = new FlashcardsFragment();
                    return flashcardsFragment;
            }
        }

        @Override
        public int getItemCount() {
            return 2; // 2 tabs: Flashcards and Tests
        }
    }
} 