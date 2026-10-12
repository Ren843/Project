package com.example.ryo_q;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ซ่อน Status Bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_result);

        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent == null) return;
        int stars = intent.getIntExtra("stars", 0);
        int correctCount = intent.getIntExtra("correctCount", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        String language = intent.getStringExtra("language");
        String difficulty = intent.getStringExtra("difficulty");

        TextView resultTitle = findViewById(R.id.resultTitle);
        TextView correctText = findViewById(R.id.correctText);
        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        View btnPlayAgain = findViewById(R.id.btnPlayAgain);
        View btnExitHome = findViewById(R.id.btnExitHome);
        View btnReview = findViewById(R.id.btnReview);
        if (btnReview != null) {
            btnReview.setOnClickListener(v -> {
                Intent reviewIntent = new Intent(ResultActivity.this, ReviewActivity.class);
                reviewIntent.putExtra("questionList", getIntent().getSerializableExtra("questionList"));
                reviewIntent.putExtra("selectedAnswers", getIntent().getSerializableExtra("selectedAnswers"));
                startActivity(reviewIntent);
            });
        }
        if (resultTitle != null) {
            resultTitle.setText(stars > 0 ? "Robot Repaired!" : "Try Again");
        }

        if (correctText != null) {
            correctText.setText("Correct: " + correctCount + " / " + totalQuestions);
        }

        setStarFilled(star1, stars >= 1);
        setStarFilled(star2, stars >= 2);
        setStarFilled(star3, stars >= 3);

        if (btnPlayAgain != null) {
            btnPlayAgain.setOnClickListener(v -> {
                Intent playIntent = new Intent(ResultActivity.this, GameplayActivity.class);
                playIntent.putExtra("language", language);
                playIntent.putExtra("difficulty", difficulty);
                startActivity(playIntent);
                finish();
            });
        }

        if (btnExitHome != null) {
            btnExitHome.setOnClickListener(v -> {
                Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                finish();
            });
        }
    }

    private void setStarFilled(ImageView star, boolean filled) {
        if (star == null) return;
        star.setImageResource(R.drawable.star);
        star.setAlpha(filled ? 1.0f : 0.3f);
    }
}