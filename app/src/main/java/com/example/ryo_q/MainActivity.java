package com.example.ryo_q;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        // ทดสอบระบบ Data Layer หลังจากปรับปรุงเสร็จสมบูรณ์
        List<Question> testList = QuestionRepository.getQuestions(GameConstants.LANG_JAVA, GameConstants.DIFF_EASY);
        Log.d("DB_TEST", "จำนวนโจทย์ Java Easy (จากค่าคงที่): " + testList.size());

        Robot robot = RobotRepository.getRobotById(1);
        if (robot != null) {
            Log.d("DB_TEST", "พบหุ่นยนต์: " + robot.getName());
        }

        ScoreManager.saveStars(this, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL, 3);
        int totalStars = ScoreManager.getTotalStars(this);
        Log.d("DB_TEST", "คะแนนรวมทั้งหมด: " + totalStars);

        setupButtons();


    }

    //ปุ่ม
    private void setupButtons() {
        View btnPlay = findViewById(R.id.btnMenuPlay);
        View btnExit = findViewById(R.id.btnMenuExit);
    //Animation Button Floating
        if (btnPlay != null) {
            setupButtonTouchAnimation(btnPlay);
            startFloatingAnimation(btnPlay, 0);
            btnPlay.setOnClickListener(v -> {
                Log.d("UI", "Play Button Clicked");
                android.content.Intent intent = new android.content.Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            });
        }

        if (btnExit != null) {
            setupButtonTouchAnimation(btnExit);
            startFloatingAnimation(btnExit, 500);
            btnExit.setOnClickListener(v -> {
                Log.d("UI", "Exit Button Clicked");
                finish();
            });
        }
    }
    //Animation Button
    private void setupButtonTouchAnimation(View view){
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.8f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(100).start();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.performClick();
                    }
                    break;
            }
            return true;
        });
    }

    private void startFloatingAnimation(View view, long delay) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -30f, 0f);
        animator.setDuration(2000);
        animator.setStartDelay(delay);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}



