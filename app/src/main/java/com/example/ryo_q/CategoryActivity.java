package com.example.ryo_q;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
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

/**
 * CategoryActivity: หน้าเลือกภาษาโปรแกรมและระดับความยากในการตอบคำถาม
 * หน้าที่:
 * 1. ให้ผู้ใช้เลือกภาษาโปรแกรม (Java, C, Python)
 * 2. เมื่อเลือกภาษาแล้ว จะแสดงส่วนเลือกความยาก (Easy, Normal, Hard)
 * 3. เมื่อเลือกความยากแล้ว จะแสดงปุ่ม Start เพื่อเริ่มเกม
 * 4. ส่งข้อมูลภาษาและความยากไปยัง GameplayActivity ผ่าน Intent
 */
public class CategoryActivity extends AppCompatActivity {
    // View สำหรับการ์ดปุ่มเลือกภาษาและความยาก และปุ่มย้อนกลับ
    View btnBack;
    View btnJava, btnC, btnPython;
    View btnEasy, btnNormal, btnHard, btnStart;
    LinearLayout languageLayout;
    LinearLayout DifficultyLayout;

    // เครื่องหมายถูกของแต่ละการ์ด แสดงไฮไลต์เมื่อถูกเลือก
    ImageView javaCheck, cCheck, pythonCheck;
    ImageView easyCheck, normalCheck, hardCheck;

    // ตัวแปรเก็บค่าภาษาและความยากที่ผู้ใช้เลือก
    private String selectedLanguage = "";
    private String selectedDifficulty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        // ผูก View Container สำหรับภาษาและความยาก
        languageLayout = findViewById(R.id.languageLayout);
        DifficultyLayout = findViewById(R.id.difficultyLayout);

        // ปุ่ม Back ย้อนกลับ
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // ผูก View สำหรับปุ่มเลือกภาษา
        btnJava = findViewById(R.id.javaButton);
        btnC = findViewById(R.id.cButton);
        btnPython = findViewById(R.id.pythonButton);

        javaCheck = findViewById(R.id.javaCheck);
        cCheck = findViewById(R.id.cCheck);
        pythonCheck = findViewById(R.id.pythonCheck);

        // ผูก View สำหรับปุ่มเลือกระดับความยากและปุ่ม Start
        btnEasy = findViewById(R.id.easyButton);
        btnNormal = findViewById(R.id.normalButton);
        btnHard = findViewById(R.id.hardButton);
        btnStart = findViewById(R.id.startButton);

        easyCheck = findViewById(R.id.easyCheck);
        normalCheck = findViewById(R.id.normalCheck);
        hardCheck = findViewById(R.id.hardCheck);

        // ตั้งค่า Event Listener เมื่อกดเลือกภาษา
        if (btnJava != null) {
            btnJava.setOnClickListener(v -> selectLanguage(GameConstants.LANG_JAVA));
        }
        if (btnC != null) {
            btnC.setOnClickListener(v -> selectLanguage(GameConstants.LANG_C));
        }
        if (btnPython != null) {
            btnPython.setOnClickListener(v -> selectLanguage(GameConstants.LANG_PYTHON));
        }

        // ตั้งค่า Event Listener เมื่อกดเลือกความยาก
        if (btnEasy != null) {
            btnEasy.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_EASY));
        }
        if (btnNormal != null) {
            btnNormal.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_NORMAL));
        }
        if (btnHard != null) {
            btnHard.setOnClickListener(v -> selectDifficulty(GameConstants.DIFF_HARD));
        }

        // ปุ่ม Start เริ่มเกม
        if (btnStart != null) {
            btnStart.setOnClickListener(v -> startGame());
        }

        // ตั้งค่าระบบแอนิเมชันสำหรับปุ่มและการ์ด
        setupButtonsAnimation();

        // ซ่อน ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // ซ่อน System Bars ให้แสดงแบบ Immersive Mode
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

    /**
     * selectLanguage(): จัดการสถานะเมื่อผู้ใช้เลือกภาษา
     * @param language ชื่อภาษาโปรแกรมที่เลือก (LANG_JAVA, LANG_C, LANG_PYTHON)
     */
    private void selectLanguage(String language) {
        selectedLanguage = language;

        // รีเซ็ตสไตล์การ์ดภาษาทั้งหมดก่อน
        resetLanguageCards();

        // เปลี่ยนพื้นหลังและแสดงเครื่องหมายถูกเฉพาะการ์ดที่ถูกเลือก
        if (language.equals(GameConstants.LANG_JAVA)) {
            if (btnJava != null) btnJava.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (javaCheck != null) javaCheck.setVisibility(View.VISIBLE);
        } else if (language.equals(GameConstants.LANG_C)) {
            if (btnC != null) btnC.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (cCheck != null) cCheck.setVisibility(View.VISIBLE);
        } else if (language.equals(GameConstants.LANG_PYTHON)) {
            if (btnPython != null) btnPython.setBackgroundResource(R.drawable.bg_card_button_selected);
            if (pythonCheck != null) pythonCheck.setVisibility(View.VISIBLE);
        }

        // แสดงส่วนเลือกความยาก
        showDifficulty();
    }

    /**
     * resetLanguageCards(): รีเซ็ตสถานะการแสดงผลของการ์ดภาษาทั้งหมดเป็นปกติ
     */
    private void resetLanguageCards() {
        if (btnJava != null) btnJava.setBackgroundResource(R.drawable.bg_card_button);
        if (btnC != null) btnC.setBackgroundResource(R.drawable.bg_card_button);
        if (btnPython != null) btnPython.setBackgroundResource(R.drawable.bg_card_button);

        if (javaCheck != null) javaCheck.setVisibility(View.GONE);
        if (cCheck != null) cCheck.setVisibility(View.GONE);
        if (pythonCheck != null) pythonCheck.setVisibility(View.GONE);
    }

    /**
     * selectDifficulty(): จัดการสถานะเมื่อผู้ใช้เลือกระดับความยาก
     * @param difficulty ระดับความยากที่เลือก (DIFF_EASY, DIFF_NORMAL, DIFF_HARD)
     */
    private void selectDifficulty(String difficulty) {
        selectedDifficulty = difficulty;

        // รีเซ็ตสไตล์การ์ดความยากทั้งหมดก่อน
        resetDifficultyCards();

        // เปลี่ยนพื้นหลังและแสดงเครื่องหมายถูกของการ์ดความยากที่ถูกเลือก
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

        // แสดงปุ่ม Start พร้อมแอนิเมชัน ค่อยๆ ปรากฏ (Fade-in)
        if (btnStart != null) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setAlpha(0f);
            btnStart.animate().alpha(1f).setDuration(500).start();
        }
    }

    /**
     * resetDifficultyCards(): รีเซ็ตสถานะการแสดงผลของการ์ดความยากทั้งหมดเป็นปกติ
     */
    private void resetDifficultyCards() {
        if (btnEasy != null) btnEasy.setBackgroundResource(R.drawable.bg_card_button);
        if (btnNormal != null) btnNormal.setBackgroundResource(R.drawable.bg_card_button);
        if (btnHard != null) btnHard.setBackgroundResource(R.drawable.bg_card_button);

        if (easyCheck != null) easyCheck.setVisibility(View.GONE);
        if (normalCheck != null) normalCheck.setVisibility(View.GONE);
        if (hardCheck != null) hardCheck.setVisibility(View.GONE);
    }

    /**
     * showDifficulty(): แสดง Layout ส่วนเลือกความยากเมื่อผู้ใช้เลือกภาษาเรียบร้อยแล้ว
     */
    private void showDifficulty() {
        languageLayout.setVisibility(View.VISIBLE);
        DifficultyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * startGame(): ส่งข้อมูลภาษาและความยากที่เลือกไปยัง GameplayActivity และเริ่มเล่นเกม
     */
    private void startGame() {
        Intent intent = new Intent(this, GameplayActivity.class);
        intent.putExtra("language", selectedLanguage);
        intent.putExtra("difficulty", selectedDifficulty);
        startActivity(intent);
    }

    // --- ระบบแอนิเมชันปุ่ม ---

    /**
     * setupButtonsAnimation(): ตั้งค่าแอนิเมชันตอบสนองการกดและแอนิเมชันลอยให้กับการ์ด
     */
    private void setupButtonsAnimation() {
        setupButtonTouchAnimation(btnBack);
        setupButtonTouchAnimation(btnJava);
        setupButtonTouchAnimation(btnC);
        setupButtonTouchAnimation(btnPython);

        setupButtonTouchAnimation(btnEasy);
        setupButtonTouchAnimation(btnNormal);
        setupButtonTouchAnimation(btnHard);
        setupButtonTouchAnimation(btnStart);

        startFloatingAnimation(languageLayout, 0);
        startFloatingAnimation(DifficultyLayout, 0);
    }

    /**
     * setupButtonTouchAnimation(): เพิ่มแอนิเมชันย่อ/ขยายขณะแตะปุ่ม
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
     * startFloatingAnimation(): สร้างแอนิเมชันขยับลอยขึ้นลงเบาๆ เพื่อความสวยงาม
     */
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
