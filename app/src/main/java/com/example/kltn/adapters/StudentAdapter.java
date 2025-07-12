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
        private android.widget.ImageView ivStudentAvatar;
        private TextView tvStudentName, tvStudentGrade, tvStudentAge;
        private android.widget.ImageButton btnEditStudent;

        public ViewHolder(View itemView) {
            super(itemView);
            ivStudentAvatar = itemView.findViewById(R.id.ivStudentAvatar);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentGrade = itemView.findViewById(R.id.tvStudentGrade);
            tvStudentAge = itemView.findViewById(R.id.tvStudentAge);
            btnEditStudent = itemView.findViewById(R.id.btnEditStudent);
        }

        void bind(Student student) {
            tvStudentName.setText(student.getName());
            tvStudentGrade.setText("Grade: " + student.getGrade() + ", Email: " + student.getEmail());
            tvStudentAge.setText("Age: " + student.getAge());

            btnEditStudent.setOnClickListener(v -> {
                if (listener != null) listener.onStudentAction(student, "delete");
            });
        }
    }
} 