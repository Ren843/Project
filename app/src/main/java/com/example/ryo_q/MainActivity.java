package com.example.ryo_q;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * คลาส MainActivity หน้าแรกของแอปพลิเคชัน (Main Menu)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ซ่อน Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // ตั้งค่าซ่อนระบบแทร็กบาร์แถบระบบ
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        setupButtons();
    }

    /**
     * ตั้งค่าปุ่มกดเมนูหลัก (Play & Exit) พร้อมใส่แอนิเมชัน
     */
    private void setupButtons() {
        View btnPlay = findViewById(R.id.btnMenuPlay);
        View btnExit = findViewById(R.id.btnMenuExit);

        if (btnPlay != null) {
            setupButtonTouchAnimation(btnPlay);
            startFloatingAnimation(btnPlay, 0);
            btnPlay.setOnClickListener(v -> {
                Log.d("UI", "Play Button Clicked");
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
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

    /**
     * แอนิเมชันปุ่มกดเมื่อผู้เล่นสัมผัสหน้าจอ
     */
    private void setupButtonTouchAnimation(View view) {
        if (view == null) return;
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

    /**
     * แอนิเมชันลอยตัวขึ้นลงแบบวนลูป (Floating Animation)
     */
    private void startFloatingAnimation(View view, long delay) {
        if (view == null) return;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -30f, 0f);
        animator.setDuration(2000);
        animator.setStartDelay(delay);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}
