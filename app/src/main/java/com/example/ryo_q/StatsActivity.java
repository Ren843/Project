package com.example.ryo_q;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// หน้าแสดงสถิติดาวสูงสุดของทุกด่าน (3 ภาษา x 3 ระดับ) อ่านค่าจาก ScoreManager
public class StatsActivity extends AppCompatActivity {

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

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    // สร้างตารางใหม่ทุกครั้งที่กลับมาหน้านี้ เพื่อให้ดาวเป็นปัจจุบัน
    @Override
    protected void onResume() {
        super.onResume();
        buildTable();
    }

    private void buildTable() {
        statsContainer.removeAllViews();

        int total = ScoreManager.getTotalStars(this);
        int max = LANGS.length * DIFFS.length * MAX_STARS;
        textTotal.setText("Total: " + total + " / " + max);

        // แถวหัวตาราง: (ว่าง) | Easy | Normal | Hard
        LinearLayout header = newRow(false);
        header.addView(newLabel("", false));
        for (String name : DIFF_NAMES) {
            header.addView(newLabel(name, false));
        }
        statsContainer.addView(header);

        // แถวของแต่ละภาษา
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

    // แถวแนวนอน: แถวข้อมูลสูงเท่ากัน (weight 1) แถวหัวตารางสูงเท่าเนื้อหา
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

    private TextView newLabel(String text, boolean isLanguage) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setGravity(Gravity.CENTER);
        label.setTextSize(isLanguage ? 18f : 14f);
        label.setTextColor(getColor(isLanguage ? R.color.text_primary : R.color.gold_accent));
        label.setTypeface(null, android.graphics.Typeface.BOLD);
        label.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        return label;
    }

    // ช่องแสดงดาว 3 ดวง ดวงที่ได้แล้วชัด ดวงที่ยังไม่ได้จาง (เหมือนหน้า Result)
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

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }
}