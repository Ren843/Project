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
 * MainActivity: หน้าเมนูหลัก (Main Menu) ของแอปพลิเคชัน
 * หน้าที่:
 * 1. แสดงเมนูเลือกเข้าสู่การเล่นเกม (Play), สถิติ (Stats), หรือออกจากแอป (Exit)
 * 2. จัดการแอนิเมชันของปุ่มและซ่อน System UI (Status Bar / Navigation Bar) เพื่อความสวยงาม
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // เปิดใช้งานโหมดแสดงผลแบบเต็มจอ (Edge-to-Edge)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ซ่อน ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // ซ่อน System Bars (Status Bar และ Navigation Bar) เพื่อให้แสดงผลแบบ Immersive Mode
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }

        // กำหนดให้ไม่มี Padding ส่วนเกินจาก System Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        // ตั้งค่าปุ่มกดต่างๆ บนหน้าเมนูหลัก
        setupButtons();
    }

    /**
     * setupButtons(): กำหนดการทำงานและแอนิเมชันสำหรับปุ่มเมนูหลัก
     * - btnMenuPlay: ไปยังหน้าเลือกหมวดหมู่และระดับความยาก (CategoryActivity)
     * - btnMenuStats: ไปยังหน้าแสดงสถิติและดาวสะสม (StatsActivity)
     * - btnMenuExit: ปิดแอปพลิเคชัน (finish)
     */
    private void setupButtons() {
        View btnPlay = findViewById(R.id.btnMenuPlay);
        View btnExit = findViewById(R.id.btnMenuExit);
        View btnStats = findViewById(R.id.btnMenuStats);

        // ปุ่มเล่นเกม (Play)
        if (btnPlay != null) {
            setupButtonTouchAnimation(btnPlay);
            btnPlay.setOnClickListener(v -> {
                Log.d("UI", "Play Button Clicked");
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            });
        }

        // ปุ่มออกจากแอป (Exit)
        if (btnExit != null) {
            setupButtonTouchAnimation(btnExit);
            btnExit.setOnClickListener(v -> {
                Log.d("UI", "Exit Button Clicked");
                finish();
            });
        }

        // ปุ่มดูสถิติ (Stats)
        if (btnStats != null) {
            setupButtonTouchAnimation(btnStats);
            btnStats.setOnClickListener(v -> {
                Log.d("UI", "Stats Button Clicked");
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(intent);
            });
        }
    }

    /**
     * setupButtonTouchAnimation(): เพิ่มแอนิเมชันย่อ/ขยายเมื่อแตะปุ่ม
     * - ACTION_DOWN: ย่อขนาดลงเล็กน้อย (scale 0.9x) และจางลง (alpha 0.8)
     * - ACTION_UP / CANCEL: คืนค่าขนาดปกติ (scale 1.0x) และทริกเกอร์คลิก
     */
    private void setupButtonTouchAnimation(View view) {
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
}
