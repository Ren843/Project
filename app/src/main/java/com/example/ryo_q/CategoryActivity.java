package com.example.ryo_q;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * คลาส CategoryActivity หน้าเลือกหมวดหมู่ภาษาโปรแกรมและระดับความยาก
 */
public class CategoryActivity extends AppCompatActivity {

    private View btnJava, btnCpp, btnPython;
    private View btnEasy, btnNormal, btnHard, btnStart;
    private View backButtonLanguage, backButtonDifficulty;
    private LinearLayout languageLayout;
    private LinearLayout difficultyLayout;

    private String selectedLanguage = GameConstants.LANG_JAVA;
    private String selectedDifficulty = GameConstants.DIFF_EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        languageLayout = findViewById(R.id.languageLayout);
        difficultyLayout = findViewById(R.id.difficultyLayout);

        // ปุ่มเลือกภาษาโปรแกรม
        btnJava = findViewById(R.id.javaButton);
        btnCpp = findViewById(R.id.cppButton);
        btnPython = findViewById(R.id.pythonButton);
        backButtonLanguage = findViewById(R.id.backButtonLanguage);

        // ปุ่มเลือกระดับความยาก
        btnEasy = findViewById(R.id.easyButton);
        btnNormal = findViewById(R.id.normalButton);
        btnHard = findViewById(R.id.hardButton);
        btnStart = findViewById(R.id.startButton);
        backButtonDifficulty = findViewById(R.id.backButtonDifficulty);

        // ตั้งค่า OnClickListener สำหรับปุ่มภาษา
        if (btnJava != null) {
            btnJava.setOnClickListener(v -> selectLanguage(GameConstants.LANG_JAVA));
        }
        if (btnCpp != null) {
            btnCpp.setOnClickListener(v -> selectLanguage(GameConstants.LANG_CPP));
        }
        if (btnPython != null) {
            btnPython.setOnClickListener(v -> selectLanguage(GameConstants.LANG_PYTHON));
        }
        if (backButtonLanguage != null) {
            backButtonLanguage.setOnClickListener(v -> finish());
        }

        // ตั้งค่า OnClickListener สำหรับปุ่มความยาก
        if (btnEasy != null) {
            btnEasy.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_EASY));
        }
        if (btnNormal != null) {
            btnNormal.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_NORMAL));
        }
        if (btnHard != null) {
            btnHard.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_HARD));
        }
        if (backButtonDifficulty != null) {
            backButtonDifficulty.setOnClickListener(v -> showLanguage());
        }

        if (btnStart != null) {
            btnStart.setOnClickListener(v -> startGame());
        }

        // ตั้งค่าแอนิเมชันสำหรับปุ่มและเลย์เอาต์
        setupButtonsAnimation();

        // ซ่อน Action Bar และแถบระบบ
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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
    }

    /**
     * เลือกภาษาโปรแกรมและเปลี่ยนไปแสดงหน้าระดับความยาก
     * @param language ภาษาโปรแกรมที่เลือก
     */
    private void selectLanguage(String language) {
        selectedLanguage = language;
        showDifficulty();
    }

    /**
     * เลือกระดับความยากและแสดงปุ่มเริ่มเกม
     * @param difficulty ระดับความยากที่เลือก
     */
    private void selectDifficulty(String difficulty) {
        selectedDifficulty = difficulty;
        if (btnStart != null) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setAlpha(0f);
            btnStart.animate().alpha(1f).setDuration(500).start();
        }
    }

    /**
     * ซ่อนส่วนเลือกภาษา และเปิดแสดงส่วนเลือกระดับความยาก
     */
    private void showDifficulty() {
        if (languageLayout != null) {
            languageLayout.setVisibility(View.GONE);
        }
        if (difficultyLayout != null) {
            difficultyLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ซ่อนส่วนเลือกระดับความยาก และเปิดแสดงส่วนเลือกภาษา
     */
    private void showLanguage() {
        if (difficultyLayout != null) {
            difficultyLayout.setVisibility(View.GONE);
        }
        if (languageLayout != null) {
            languageLayout.setVisibility(View.VISIBLE);
        }
        if (btnStart != null) {
            btnStart.setVisibility(View.GONE);
        }
    }

    /**
     * เริ่มเกมโดยเปิด GameActivity พร้อมแนบข้อมูลภาษาและความยากผ่าน Intent
     */
    private void startGame() {
        Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
        intent.putExtra("language", selectedLanguage);
        intent.putExtra("difficulty", selectedDifficulty);
        startActivity(intent);
    }

    // --- ระบบแอนิเมชันปุ่มและเลย์เอาต์ ---

    private void setupButtonsAnimation() {
        // แอนิเมชันปุ่มภาษา
        setupButtonTouchAnimation(btnJava);
        setupButtonTouchAnimation(btnCpp);
        setupButtonTouchAnimation(btnPython);
        setupButtonTouchAnimation(backButtonLanguage);

        // แอนิเมชันปุ่มความยาก
        setupButtonTouchAnimation(btnEasy);
        setupButtonTouchAnimation(btnNormal);
        setupButtonTouchAnimation(btnHard);
        setupButtonTouchAnimation(btnStart);
        setupButtonTouchAnimation(backButtonDifficulty);

        // แอนิเมชันลอยแบบนุ่มนวล (Floating Animation)
        startFloatingAnimation(languageLayout);
        startFloatingAnimation(difficultyLayout);
    }

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

    private void startFloatingAnimation(View view) {
        if (view == null) return;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -20f, 0f);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}
