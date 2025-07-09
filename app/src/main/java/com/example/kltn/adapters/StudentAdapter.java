package com.example.kltn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.Student;
import com.example.kltn.R;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> students;
    private OnStudentActionListener listener;

    public interface OnStudentActionListener {
        void onStudentAction(Student student, String action);
    }

    public StudentAdapter(List<Student> students, OnStudentActionListener listener) {
        this.students = students;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvAge, tvClass, tvEmail, tvProgress, tvScore, tvStatus;
        Button btnEdit, btnViewProgress, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnViewProgress = itemView.findViewById(R.id.btnViewProgress);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(Student student) {
            tvStudentName.setText(student.getName());
            tvAge.setText(student.getAge() + " years old");
            tvClass.setText(student.getClassName());
            tvEmail.setText(student.getEmail());
            tvProgress.setText(student.getProgress() + "% Complete");
            tvScore.setText("Score: " + student.getScore() + "/100");
            tvStatus.setText(student.isActive() ? "Active" : "Inactive");

            btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onStudentAction(student, "edit");
            });
            btnViewProgress.setOnClickListener(v -> {
                if (listener != null) listener.onStudentAction(student, "view_progress");
            });
            btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onStudentAction(student, "delete");
            });
        }
    }
} 