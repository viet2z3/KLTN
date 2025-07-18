package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.R;
import com.example.kltn.adapters.TestSetAdapter;
import com.example.kltn.models.TestSet;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class TestChoiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_choice);
        RecyclerView rv = findViewById(R.id.rvTestList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<TestSet> tests = new ArrayList<>();
        TestSetAdapter adapter = new TestSetAdapter(tests, test -> {
            Intent intent = new Intent(this, TestDetailActivity.class);
            intent.putExtra("test_id", test.id);
            String userId = getIntent().getStringExtra("user_id");
            if (userId != null) intent.putExtra("user_id", userId);
            startActivity(intent);
        });
        rv.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tests").get().addOnSuccessListener(queryDocumentSnapshots -> {
            tests.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                String id = doc.getId();
                String title = doc.getString("title");
                int duration = doc.getLong("duration") != null ? doc.getLong("duration").intValue() : 0;
                int maxScore = doc.getLong("max_score") != null ? doc.getLong("max_score").intValue() : 0;
                String exerciseId = doc.getString("exercise_id");
                List<?> questions = (List<?>) doc.get("questions");
                int questionCount = questions != null ? questions.size() : 0;
                tests.add(new TestSet(id, title, duration, maxScore, exerciseId, questionCount));
            }
            adapter.notifyDataSetChanged();
        });
    }
} 