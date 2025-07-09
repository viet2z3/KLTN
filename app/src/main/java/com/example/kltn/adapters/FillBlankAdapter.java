package com.example.kltn.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.Question;
import com.example.kltn.R;
import java.util.List;

public class FillBlankAdapter extends RecyclerView.Adapter<FillBlankAdapter.VH> {
    List<Question> questions;
    public FillBlankAdapter(List<Question> q) { questions = q; }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fill_blank_question, parent, false);
        return new VH(v);
    }
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Question q = questions.get(position);
        holder.img.setImageResource(q.imageRes);
        holder.tvQ.setText(q.question);
        holder.etAns.setText("");
        holder.tvFeedback.setVisibility(View.GONE);
        holder.btnSubmit.setEnabled(true);
        holder.btnSubmit.setOnClickListener(v -> {
            String ans = holder.etAns.getText().toString().trim();
            if (TextUtils.isEmpty(ans)) {
                holder.tvFeedback.setText("Vui lòng nhập đáp án!");
                holder.tvFeedback.setTextColor(0xFFD32F2F); // đỏ
                holder.tvFeedback.setVisibility(View.VISIBLE);
            } else if (ans.equalsIgnoreCase(q.answer)) {
                holder.tvFeedback.setText("Chính xác!");
                holder.tvFeedback.setTextColor(0xFF388E3C); // xanh
                holder.tvFeedback.setVisibility(View.VISIBLE);
                holder.btnSubmit.setEnabled(false);
            } else {
                holder.tvFeedback.setText("Sai! Đáp án đúng: " + q.answer);
                holder.tvFeedback.setTextColor(0xFFD32F2F);
                holder.tvFeedback.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public int getItemCount() { return questions.size(); }
    public static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvQ, tvFeedback;
        EditText etAns;
        Button btnSubmit;
        public VH(View v) {
            super(v);
            img = v.findViewById(R.id.imgIllustration);
            tvQ = v.findViewById(R.id.tvQuestion);
            etAns = v.findViewById(R.id.etAnswer);
            btnSubmit = v.findViewById(R.id.btnSubmit);
            tvFeedback = v.findViewById(R.id.tvFeedback);
        }
    }
} 