package com.example.kltn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.Question;
import com.example.kltn.R;
import java.util.List;

public class MultipleChoiceAdapter extends RecyclerView.Adapter<MultipleChoiceAdapter.VH> {
    List<Question> questions;
    public MultipleChoiceAdapter(List<Question> q) { questions = q; }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multiple_choice_question, parent, false);
        return new VH(v);
    }
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Question q = questions.get(position);
        holder.img.setImageResource(q.imageRes);
        holder.tvQ.setText(q.question);
        holder.rg.clearCheck();
        holder.tvFeedback.setVisibility(View.GONE);
        holder.btnSubmit.setEnabled(true);
        // Gán đáp án
        for (int i = 0; i < 4; i++) {
            RadioButton rb = holder.rb[i];
            rb.setText(q.choices.get(i));
            rb.setChecked(false);
        }
        holder.btnSubmit.setOnClickListener(v -> {
            int checkedId = holder.rg.getCheckedRadioButtonId();
            if (checkedId == -1) {
                holder.tvFeedback.setText("Vui lòng chọn đáp án!");
                holder.tvFeedback.setTextColor(0xFFD32F2F);
                holder.tvFeedback.setVisibility(View.VISIBLE);
            } else {
                RadioButton rb = holder.itemView.findViewById(checkedId);
                String ans = rb.getText().toString().trim();
                if (ans.equalsIgnoreCase(q.answer)) {
                    holder.tvFeedback.setText("Chính xác!");
                    holder.tvFeedback.setTextColor(0xFF388E3C);
                    holder.tvFeedback.setVisibility(View.VISIBLE);
                    holder.btnSubmit.setEnabled(false);
                } else {
                    holder.tvFeedback.setText("Sai! Đáp án đúng: " + q.answer);
                    holder.tvFeedback.setTextColor(0xFFD32F2F);
                    holder.tvFeedback.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public int getItemCount() { return questions.size(); }
    public static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvQ, tvFeedback;
        RadioGroup rg;
        RadioButton[] rb = new RadioButton[4];
        Button btnSubmit;
        public VH(View v) {
            super(v);
            img = v.findViewById(R.id.imgIllustration);
            tvQ = v.findViewById(R.id.tvQuestion);
            rg = v.findViewById(R.id.rgAnswers);
            rb[0] = v.findViewById(R.id.rbAnswer1);
            rb[1] = v.findViewById(R.id.rbAnswer2);
            rb[2] = v.findViewById(R.id.rbAnswer3);
            rb[3] = v.findViewById(R.id.rbAnswer4);
            btnSubmit = v.findViewById(R.id.btnSubmit);
            tvFeedback = v.findViewById(R.id.tvFeedback);
        }
    }
} 