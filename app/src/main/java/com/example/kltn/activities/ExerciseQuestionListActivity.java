package com.example.kltn.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.kltn.R;
import com.example.kltn.models.Question;
import com.example.kltn.adapters.FillBlankAdapter;
import com.example.kltn.adapters.MultipleChoiceAdapter;

public class ExerciseQuestionListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_question_list);
        String topic = getIntent().getStringExtra("topic");
        String type = getIntent().getStringExtra("type");
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Chủ đề: " + topic + "\nLoại: " + type);
        RecyclerView rv = findViewById(R.id.rvQuestions);
        TextView tvQuestionCount = findViewById(R.id.tvQuestionCount);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<Question> questions = getQuestions(topic, type);
        tvQuestionCount.setText("Question 1/" + questions.size());
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int pos = lm.findFirstVisibleItemPosition();
                tvQuestionCount.setText("Question " + (pos + 1) + "/" + questions.size());
            }
        });
        if (type.equalsIgnoreCase("Fill in the blank")) {
            rv.setAdapter(new FillBlankAdapter(questions));
        } else {
            rv.setAdapter(new MultipleChoiceAdapter(questions));
        }
    }

    // Demo dữ liệu mẫu
    private List<Question> getQuestions(String topic, String type) {
        List<Question> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            if (type.equalsIgnoreCase("Fill in the blank")) {
                list.add(new Question(
                        topic + ": Điền từ vào chỗ trống cho câu hỏi " + i,
                        "Đáp án" + i,
                        getImageRes(topic),
                        null
                ));
            } else {
                List<String> choices = new ArrayList<>();
                for (int j = 1; j <= 4; j++) choices.add("Đáp án " + j);
                list.add(new Question(
                        topic + ": Chọn đáp án đúng cho câu hỏi " + i,
                        "Đáp án 2",
                        getImageRes(topic),
                        choices
                ));
            }
        }
        return list;
    }

    private int getImageRes(String topic) {
        if (topic.equalsIgnoreCase("Animal")) return R.drawable.animals;
        if (topic.equalsIgnoreCase("Color")) return R.drawable.color;
        if (topic.equalsIgnoreCase("Shape")) return R.drawable.shapes;
        return R.drawable.animals;
    }
} 