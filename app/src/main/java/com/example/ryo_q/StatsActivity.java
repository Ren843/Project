package com.example.ryo_q;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * StatsActivity: หน้าแสดงสถิติดาวสูงสุดที่ได้ในแต่ละภาษาและระดับความยาก
 * หน้าที่:
 * 1. ดึงข้อมูลจำนวนดาวสะสมจาก ScoreManager (ใช้ SharedPreferences)
 * 2. สร้างตารางแสดงดาวของทุกด่าน (3 ภาษา x 3 ระดับความยาก = 9 ด่าน)
 * 3. คำนวณผลรวมดาวทั้งหมดที่ทำได้เทียบกับจำนวนดาวสูงสุดที่เป็นไปได้ (เช่น 27 ดาว)
 * 4. อัปเดตข้อมูลทุกครั้งใน onResume() เพื่อให้แสดงดาวที่เป็นปัจจุบันเสมอ
 */
public class StatsActivity extends AppCompatActivity {

    // กำหนดค่าคอนสแตนต์สำหรับภาษา และระดับความยากในการดึงข้อมูลจาก ScoreManager
    private static final String[] LANGS = {
            GameConstants.LANG_JAVA, GameConstants.LANG_PYTHON, GameConstants.LANG_C};
    private static final String[] LANG_NAMES = {"Java", "Python", "C"};
    private static final String[] DIFFS = {
            GameConstants.DIFF_EASY, GameConstants.DIFF_NORMAL, GameConstants.DIFF_HARD};
    private static final String[] DIFF_NAMES = {"Easy", "Normal", "Hard"};
    private static final int MAX_STARS = 3;

    private LinearLayout statsContainer;
    private TextView textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // แสดงผลเต็มหน้าจอ ซ่อน Status Bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_stats);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        statsContainer = findViewById(R.id.statsContainer);
        textTotal = findViewById(R.id.textTotal);

        // ปุ่มย้อนกลับ
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        /*
        // [OPTIONAL] ผูกปุ่ม Reset สถิติดาว
        View btnReset = findViewById(R.id.btnReset);
        if (btnReset != null) {
            btnReset.setOnClickListener(v -> showResetConfirmDialog());
        }
        */
    }

    /**
     * onResume(): เรียกใช้ buildTable() ทุกครั้งที่หน้าจอกลับมาแสดงผล
     * เพื่อให้แน่ใจว่าตารางแสดงดาวล่าสุดเสมอหลังจากเล่นจบเกม
     */
    @Override
    protected void onResume() {
        super.onResume();
        buildTable();
    }

    /**
     * buildTable(): สร้างตารางสถิติดาวและคำนวณดาวรวมทั้งหมดแบบไดนามิก
     */
    private void buildTable() {
        // ล้าง View ในคอนเทนเนอร์ก่อนสร้างใหม่
        statsContainer.removeAllViews();

        // ดึงดาวรวม และคำนวณดาวสูงสุดที่เป็นไปได้ (3 ภาษา * 3 ความยาก * 3 ดาว = 27)
        int total = ScoreManager.getTotalStars(this);
        int max = LANGS.length * DIFFS.length * MAX_STARS;
        textTotal.setText("Total: " + total + " / " + max);

        // 1. สร้างแถวหัวตาราง (Header Row): [ คอลัมน์ว่าง | Easy | Normal | Hard ]
        LinearLayout header = newRow(false);
        header.addView(newLabel("", false));
        for (String name : DIFF_NAMES) {
            header.addView(newLabel(name, false));
        }
        statsContainer.addView(header);

        // 2. สร้างแถวของแต่ละภาษา (Java, Python, C) และดึงดาวจาก ScoreManager
        for (int i = 0; i < LANGS.length; i++) {
            LinearLayout row = newRow(true);
            row.addView(newLabel(LANG_NAMES[i], true));
            for (String diff : DIFFS) {
                int stars = ScoreManager.getSavedStars(this, LANGS[i], diff);
                row.addView(newStarCell(stars));
            }
            statsContainer.addView(row);
        }
    }

    /**
     * newRow(): สร้าง Layout แถวนอนสำหรับตาราง
     * @param stretch กำหนดว่าให้ขยายความสูงตามสัดส่วน (weight 1) หรือไม่
     */
    private LinearLayout newRow(boolean stretch) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                stretch ? 0 : LinearLayout.LayoutParams.WRAP_CONTENT,
                stretch ? 1f : 0f));
        return row;
    }

    /**
     * newLabel(): สร้าง TextView สำหรับส่วนหัวข้อตาราง (ภาษา/ความยาก)
     */
    private TextView newLabel(String text, boolean isLanguage) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setGravity(Gravity.CENTER);
        label.setTextSize(isLanguage ? 18f : 14f);
        label.setTextColor(getColor(isLanguage ? R.color.text_primary : R.color.gold_accent));
        label.setTypeface(null, Typeface.BOLD);
        label.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        return label;
    }

    /**
     * newStarCell(): สร้างช่องแสดงรูปดาว 3 ดวง
     * - ดาวที่ได้แล้ว: alpha = 1.0f (ชัด)
     * - ดาวที่ยังไม่ได้: alpha = 0.3f (จาง)
     */
    private View newStarCell(int stars) {
        LinearLayout cell = new LinearLayout(this);
        cell.setOrientation(LinearLayout.HORIZONTAL);
        cell.setGravity(Gravity.CENTER);
        cell.setBackgroundResource(R.drawable.bg_card_button);
        cell.setContentDescription(stars + " of " + MAX_STARS + " stars");

        LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        cellParams.setMargins(dp(4), dp(4), dp(4), dp(4));
        cell.setLayoutParams(cellParams);

        for (int s = 1; s <= MAX_STARS; s++) {
            ImageView star = new ImageView(this);
            star.setImageResource(R.drawable.star);
            star.setAlpha(s <= stars ? 1.0f : 0.3f);
            star.setLayoutParams(new LinearLayout.LayoutParams(dp(24), dp(24)));
            cell.addView(star);
        }
        return cell;
    }

    /**
     * dp(): แปลงค่า dp เป็นพิกเซล (px)
     */
    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }

    /*
    // ========================================================
    // [OPTIONAL] แสดง AlertDialog ยืนยันก่อนล้างดาวทั้งหมด
    // ========================================================
    private void showResetConfirmDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Reset Statistics")
                .setMessage("Are you sure you want to reset all earned stars?")
                .setPositiveButton("Reset", (dialog, which) -> {
                    ScoreManager.clearAllScores(this);
                    buildTable(); // สร้างตารางใหม่ทันทีเพื่ออัปเดตดาวเป็น 0
                })
                .setNegativeButton("Cancel", null)
                .show();
    }*/

}
