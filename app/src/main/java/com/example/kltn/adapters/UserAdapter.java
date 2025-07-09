package com.example.kltn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.models.User;
import com.example.kltn.R;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onUserAction(User user, String action);
    }

    public UserAdapter(List<User> users, OnUserActionListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserRole, tvUserStatus, tvUserEmail, tvUserPhone, tvLastLogin;
        Button btnEdit, btnResetPassword, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserRole = itemView.findViewById(R.id.tvUserRole);
            tvUserStatus = itemView.findViewById(R.id.tvUserStatus);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserPhone = itemView.findViewById(R.id.tvUserPhone);
            tvLastLogin = itemView.findViewById(R.id.tvLastLogin);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnResetPassword = itemView.findViewById(R.id.btnResetPassword);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(User user) {
            tvUserName.setText(user.getName());
            tvUserRole.setText(user.getRole());
            tvUserStatus.setText(user.getStatus());
            tvUserEmail.setText(user.getEmail());
            tvUserPhone.setText(user.getPhone());
            tvLastLogin.setText(user.getLastLogin());

            btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onUserAction(user, "edit");
            });
            btnResetPassword.setOnClickListener(v -> {
                if (listener != null) listener.onUserAction(user, "reset_password");
            });
            btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onUserAction(user, "delete");
            });
        }
    }
} 