package com.example.kltn.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.kltn.R;

import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;
import java.util.Map;


public class ProgressTrackingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        // Lấy userId từ intent (hoặc session)
        String userId = getIntent().getStringExtra("user_id");
        if (userId == null) return;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Đếm số bộ flashcard đã học
        db.collection("users").document(userId)
            .collection("flashcard_progress")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                int flashcardSetsLearned = queryDocumentSnapshots.size();
                ((TextView)findViewById(R.id.tv_words_learned)).setText(String.valueOf(flashcardSetsLearned));
                // Lấy tổng số bộ flashcard
                db.collection("flashcard_sets").get().addOnSuccessListener(snaps -> {
                    int totalFlashcardSets = snaps.size();
                    int percent = totalFlashcardSets > 0 ? (int) (100.0 * flashcardSetsLearned / totalFlashcardSets) : 0;
                    ((ProgressBar)findViewById(R.id.progress_words_learned)).setProgress(percent);
                    ((TextView)findViewById(R.id.tv_words_learned_progress)).setText(flashcardSetsLearned + "/" + totalFlashcardSets);
                });
            });

        // Đếm số bài ôn tập đã làm
        db.collection("users").document(userId)
            .collection("exercises")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                int exercisesCompleted = queryDocumentSnapshots.size();
                ((TextView)findViewById(R.id.tv_exercises_completed)).setText(String.valueOf(exercisesCompleted));
                // Lấy tổng số bài ôn tập
                db.collection("exercises").get().addOnSuccessListener(snaps -> {
                    int totalExercises = snaps.size();
                    int percent = totalExercises > 0 ? (int) (100.0 * exercisesCompleted / totalExercises) : 0;
                    ((ProgressBar)findViewById(R.id.progress_exercises_completed)).setProgress(percent);
                    ((TextView)findViewById(R.id.tv_exercises_completed_progress)).setText(exercisesCompleted + "/" + totalExercises);
                });
            });

        // Đếm số bài kiểm tra đã làm
        db.collection("users").document(userId)
            .collection("tests")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                int testsCompleted = queryDocumentSnapshots.size();
                ((TextView)findViewById(R.id.tv_tests_completed)).setText(String.valueOf(testsCompleted));
                // Lấy tổng số bài kiểm tra
                db.collection("tests").get().addOnSuccessListener(snaps -> {
                    int totalTests = snaps.size();
                    int percent = totalTests > 0 ? (int) (100.0 * testsCompleted / totalTests) : 0;
                    ((ProgressBar)findViewById(R.id.progress_tests_completed)).setProgress(percent);
                    ((TextView)findViewById(R.id.tv_tests_completed_progress)).setText(testsCompleted + "/" + totalTests);
                });
            });

        // Đếm số video đã xem
        db.collection("users").document(userId)
                .collection("video lectures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int videosWatched = queryDocumentSnapshots.size();
                    ((TextView)findViewById(R.id.tv_videos_watched)).setText(String.valueOf(videosWatched));
                    // Lấy tổng số video
                    db.collection("video_lectures").get()
                            .addOnSuccessListener(snaps -> {
                                int totalVideos = snaps.size();
                                Log.d("ProgressTracking", "Total videos: " + totalVideos);
                                int percent = totalVideos > 0 ? (int) (100.0 * videosWatched / totalVideos) : 0;
                                ((ProgressBar)findViewById(R.id.progress_videos_watched)).setProgress(percent);
                                ((TextView)findViewById(R.id.tv_videos_watched_progress)).setText(videosWatched + "/" + totalVideos);
                            })
                            .addOnFailureListener(e -> Log.e("ProgressTracking", "Error getting total videos: " + e.getMessage()));
                })
                .addOnFailureListener(e -> Log.e("ProgressTracking", "Error getting videos watched: " + e.getMessage()));

        // Lấy đánh giá học viên mới nhất (evaluation) và hiển thị lên card
        db.collection("users").document(userId)
            .collection("evaluations")
            .orderBy("evaluation_date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                if (queryDocumentSnapshots.isEmpty()) return;
                com.google.firebase.firestore.DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                // Lấy dữ liệu
                String evaluationDate = doc.getString("evaluation_date");
                double overallRating = doc.contains("overall_rating") ? doc.getDouble("overall_rating") : 0.0;
                double ratingParticipation = doc.contains("rating_participation") ? doc.getDouble("rating_participation") : 0.0;
                double ratingUnderstanding = doc.contains("rating_understanding") ? doc.getDouble("rating_understanding") : 0.0;
                double ratingProgress = doc.contains("rating_progress") ? doc.getDouble("rating_progress") : 0.0;
                String comments = doc.getString("comments");
                long score = doc.contains("score") ? doc.getLong("score") : 0;
                String teacherId = doc.getString("teacher_id");

                // Lấy tên giáo viên
                if (teacherId != null && !teacherId.isEmpty()) {
                    db.collection("users").document(teacherId).get().addOnSuccessListener(teacherDoc -> {
                        String teacherName = teacherDoc.getString("full_name");
                        showEvaluationCard(evaluationDate, overallRating, ratingParticipation, ratingUnderstanding, ratingProgress, comments, score, teacherName);
                    });
                } else {
                    showEvaluationCard(evaluationDate, overallRating, ratingParticipation, ratingUnderstanding, ratingProgress, comments, score, "");
                }
            });
    }
    private void showEvaluationCard(String evaluationDate, double overall, double participation, double understanding, double progress, String comments, long score, String teacherName) {
        findViewById(R.id.card_evaluation).setVisibility(android.view.View.VISIBLE);
        ((TextView)findViewById(R.id.tv_evaluation_date)).setText("Ngày đánh giá: " + (evaluationDate != null ? evaluationDate : "N/A"));
        ((TextView)findViewById(R.id.tv_teacher_name)).setText("Giáo viên đánh giá: " + (teacherName != null ? teacherName : "N/A"));
        ((android.widget.RatingBar)findViewById(R.id.rating_participation)).setRating((float) participation);
        ((android.widget.RatingBar)findViewById(R.id.rating_understanding)).setRating((float) understanding);
        ((android.widget.RatingBar)findViewById(R.id.rating_progress)).setRating((float) progress);
        ((TextView)findViewById(R.id.tv_overall_rating)).setText("Điểm tổng: " + String.format(java.util.Locale.getDefault(), "%.1f", overall));
        ((TextView)findViewById(R.id.tv_score)).setText("Điểm số: " + score);
        ((TextView)findViewById(R.id.tv_comments)).setText("Nhận xét: " + (comments != null ? comments : ""));
    }
}



