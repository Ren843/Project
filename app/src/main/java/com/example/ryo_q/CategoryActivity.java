package com.example.ryo_q;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class CategoryActivity extends AppCompatActivity {
    View btnJava, btnCpp, btnPython;
    View btnEasy, btnNormal, btnHard, btnStart;
    LinearLayout languageLayout;
    LinearLayout DifficultyLayout;

    // เครื่องหมายถูกของแต่ละการ์ด ไฮไลต์ตอนเลือก
    ImageView javaCheck, cppCheck, pythonCheck;
    ImageView easyCheck, normalCheck, hardCheck;

    private String selectedLanguage = "";
    private String selectedDifficulty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        languageLayout = findViewById(R.id.languageLayout);
        DifficultyLayout = findViewById(R.id.difficultyLayout);

        // Language
        btnJava = findViewById(R.id.javaButton);
        btnCpp = findViewById(R.id.cppButton);
        btnPython = findViewById(R.id.pythonButton);

        javaCheck = findViewById(R.id.javaCheck);
        cppCheck = findViewById(R.id.cppCheck);
        pythonCheck = findViewById(R.id.pythonCheck);

        // Difficulty
        btnEasy = findViewById(R.id.easyButton);
        btnNormal = findViewById(R.id.normalButton);
        btnHard = findViewById(R.id.hardButton);
        btnStart = findViewById(R.id.startButton);

        easyCheck = findViewById(R.id.easyCheck);
        normalCheck = findViewById(R.id.normalCheck);
        hardCheck = findViewById(R.id.hardCheck);

        //เลือกภาษา
        if (btnJava != null) {
            btnJava.setOnClickListener(v -> selectLanguage(GameConstants.LANG_JAVA));
        }
        if (btnCpp != null) {
            btnCpp.setOnClickListener(v -> selectLanguage(GameConstants.LANG_C));
        }
        if (btnPython != null) {
            btnPython.setOnClickListener(v -> selectLanguage(GameConstants.LANG_PYTHON));
        }
        //เลือกความยาก
        if (btnEasy != null) {
            btnEasy.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_EASY));
        }
        if (btnNormal != null) {
            btnNormal.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_NORMAL));
        }
        if (btnHard != null) {
            btnHard.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_HARD));
        }

        if (btnStart != null) {
            btnStart.setOnClickListener(v -> startGame());
         }

        // ใส่แอนิเมชันให้ปุ่ม
        setupButtonsAnimation();

        //ซ่อนแถบขาวๆ
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
    }

    private void selectLanguage(String language) {
        selectedLanguage = language;

        resetLanguageCards();

        if (language.equals(GameConstants.LANG_JAVA)) {
            if (btnJava != null) btnJava.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (javaCheck != null) javaCheck.setVisibility(View.VISIBLE);
        } else if (language.equals(GameConstants.LANG_C)) {
            if (btnCpp != null) btnCpp.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (cppCheck != null) cppCheck.setVisibility(View.VISIBLE);
        } else if (language.equals(GameConstants.LANG_PYTHON)) {
            if (btnPython != null) btnPython.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (pythonCheck != null) pythonCheck.setVisibility(View.VISIBLE);
        }

        showDifficulty();
    }

    private void resetLanguageCards() {
        if (btnJava != null) btnJava.setBackgroundResource(R.drawable.bg_card_button);
        if (btnCpp != null) btnCpp.setBackgroundResource(R.drawable.bg_card_button);
        if (btnPython != null) btnPython.setBackgroundResource(R.drawable.bg_card_button);

        if (javaCheck != null) javaCheck.setVisibility(View.GONE);
        if (cppCheck != null) cppCheck.setVisibility(View.GONE);
        if (pythonCheck != null) pythonCheck.setVisibility(View.GONE);
    }

    private void selectDifficulty(String difficulty) {
        selectedDifficulty = difficulty;

        resetDifficultyCards();

        if (difficulty.equals(GameConstants.DIFF_EASY)) {
            if (btnEasy != null) btnEasy.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (easyCheck != null) easyCheck.setVisibility(View.VISIBLE);
        } else if (difficulty.equals(GameConstants.DIFF_NORMAL)) {
            if (btnNormal != null) btnNormal.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (normalCheck != null) normalCheck.setVisibility(View.VISIBLE);
        } else if (difficulty.equals(GameConstants.DIFF_HARD)) {
            if (btnHard != null) btnHard.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (hardCheck != null) hardCheck.setVisibility(View.VISIBLE);
        }

        if (btnStart != null) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setAlpha(0f);
            btnStart.animate().alpha(1f).setDuration(500).start();
        }
    }

    private void resetDifficultyCards() {
        if (btnEasy != null) btnEasy.setBackgroundResource(R.drawable.bg_card_button);
        if (btnNormal != null) btnNormal.setBackgroundResource(R.drawable.bg_card_button);
        if (btnHard != null) btnHard.setBackgroundResource(R.drawable.bg_card_button);

        if (easyCheck != null) easyCheck.setVisibility(View.GONE);
        if (normalCheck != null) normalCheck.setVisibility(View.GONE);
        if (hardCheck != null) hardCheck.setVisibility(View.GONE);
    }

    private void showDifficulty() {
        languageLayout.setVisibility(View.VISIBLE);
        DifficultyLayout.setVisibility(View.VISIBLE);
    }

     private void startGame() {
        android.content.Intent intent = new android.content.Intent(this, GameplayActivity.class);
        intent.putExtra("language", selectedLanguage);
         intent.putExtra("difficulty", selectedDifficulty);
        startActivity(intent);
     }

    // --- ระบบแอนิเมชันปุ่ม ---
    private void setupButtonsAnimation() {
        setupButtonTouchAnimation(btnJava);
        setupButtonTouchAnimation(btnCpp);
        setupButtonTouchAnimation(btnPython);

        setupButtonTouchAnimation(btnEasy);
        setupButtonTouchAnimation(btnNormal);
        setupButtonTouchAnimation(btnHard);
        setupButtonTouchAnimation(btnStart);

        startFloatingAnimation(languageLayout, 0);
        startFloatingAnimation(DifficultyLayout, 0);
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

    private void startFloatingAnimation(View view, long delay) {
        if (view == null) return;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -20f, 0f);
        animator.setDuration(3000);
        animator.setStartDelay(delay);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}