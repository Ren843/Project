package com.example.ryo_q;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ReviewActivity: หน้าเฉลยและทบทวนคำตอบหลังจากเล่นเกมเสร็จสิ้น
 * หน้าที่:
 * 1. รับรายการคำถาม (questionList) และคำตอบที่ผู้ใช้เลือก (selectedAnswers) จาก Intent
 * 2. สร้าง Card สำหรับแต่ละข้อแสดงโจทย์ โค้ดคำถาม ตัวเลือก และสัญลักษณ์บอกว่าข้อไหนถูก/ผิด
 * 3. แสดงผลอย่างชัดเจน: คำตอบที่ถูกต้องจะทำเครื่องหมาย ✓ และเป็นสีเขียว ส่วนคำตอบที่ตอบผิดจะเป็น ✗ สีแดง
 */
public class ReviewActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ซ่อน Status Bar เพื่อแสดงผลแบบเต็มหน้าจอ
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_review);

        LinearLayout container = findViewById(R.id.reviewContainer);
        View btnBack = findViewById(R.id.btnBack);

        // ปุ่มย้อนกลับ ไปยังหน้าก่อนหน้า
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // ดึงข้อมูลคำถามและคำตอบของผู้เล่นจาก Intent
        List<Question> questionList = (ArrayList<Question>) getIntent().getSerializableExtra("questionList");
        List<Integer> selectedAnswers = (ArrayList<Integer>) getIntent().getSerializableExtra("selectedAnswers");

        if (questionList == null || selectedAnswers == null || container == null) {
            return;
        }

        // วนลูปสร้างการ์ดเฉลยคำตอบสำหรับแต่ละข้อ
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            int selectedIndex = (i < selectedAnswers.size()) ? selectedAnswers.get(i) : -1;
            container.addView(buildQuestionCard(i + 1, q, selectedIndex));
        }
    }

    /**
     * buildQuestionCard(): สร้าง View การ์ดเฉลยสำหรับคำถาม 1 ข้อด้วย Programmatic UI
     * @param questionNumber ลำดับข้อ (เริ่มจาก 1)
     * @param q วัตถุ Question เก็บโจทย์ โค้ด ตัวเลือก และคำตอบที่ถูกต้อง
     * @param selectedIndex ดัชนีตัวเลือกที่ผู้ใช้กดเลือก (-1 หมายถึงไม่ได้ตอบ/หมดเวลา)
     * @return LinearLayout Card ที่สร้างขึ้นพร้อมข้อมูลครบถ้วน
     */
    private View buildQuestionCard(int questionNumber, Question q, int selectedIndex) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.bg_card_button);
        card.setPadding(dp(12), dp(12), dp(12), dp(12));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = dp(12);
        card.setLayoutParams(cardParams);

        // 1. หัวข้อ: ข้อที่ + บอกสถานะ Correct (ถูก) หรือ Incorrect (ผิด)
        TextView header = new TextView(this);
        boolean isCorrect = selectedIndex == q.getCorrectAnswerIndex();
        header.setText("Question " + questionNumber + "  " + (isCorrect ? "✓ Correct" : "✗ Incorrect"));
        header.setTextColor(getColor(isCorrect ? R.color.correct_color : R.color.wrong_color));
        header.setTextSize(14f);
        header.setTypeface(null, Typeface.BOLD);
        card.addView(header);

        // 2. คำถาม/โจทย์ (Prompt / Comment คำอธิบายเพิ่มเติม)
        if (q.getQuestionText() != null && !q.getQuestionText().isEmpty()) {
            TextView promptView = new TextView(this);
            promptView.setText(q.getQuestionText());
            promptView.setTextColor(getColor(R.color.gold_accent));
            promptView.setTextSize(13f);
            promptView.setTypeface(null, Typeface.BOLD);
            promptView.setPadding(0, dp(4), 0, dp(4));
            card.addView(promptView);
        }

        // 3. โค้ดคำถาม (Code Snippet) ใช้ Monospace Font เหมือนใน Text Editor
        TextView code = new TextView(this);
        code.setText(q.getCodeSnippet());
        code.setTextColor(getColor(R.color.text_primary));
        code.setTypeface(Typeface.MONOSPACE);
        code.setTextSize(13f);
        code.setPadding(0, dp(8), 0, dp(8));
        card.addView(code);

        // 4. วนลูปแสดงตัวเลือกทั้งหมด พร้อมเน้นสีสำหรับข้อที่ถูกและข้อที่ผู้ใช้ตอบผิด
        String[] options = q.getOptions();
        for (int j = 0; j < options.length; j++) {
            TextView optionView = new TextView(this);
            String prefix;
            int color;

            if (j == q.getCorrectAnswerIndex()) {
                prefix = "✓ "; // ข้อที่ถูกต้อง
                color = R.color.correct_color;
            } else if (j == selectedIndex) {
                prefix = "✗ "; // ข้อที่ตอบผิด
                color = R.color.wrong_color;
            } else {
                prefix = "   "; // ตัวเลือกทั่วไป
                color = R.color.text_secondary;
            }

            optionView.setText(prefix + options[j]);
            optionView.setTextColor(getColor(color));
            optionView.setTypeface(Typeface.MONOSPACE);
            optionView.setTextSize(13f);
            optionView.setPadding(0, dp(2), 0, dp(2));
            card.addView(optionView);
        }

        // กรณีไม่ได้เลือกคำตอบ (เวลาหมดก่อน)
        if (selectedIndex == -1) {
            TextView noAnswer = new TextView(this);
            noAnswer.setText("(No answer — time's up)");
            noAnswer.setTextColor(getColor(R.color.text_secondary));
            noAnswer.setTextSize(11f);
            noAnswer.setPadding(0, dp(4), 0, 0);
            card.addView(noAnswer);
        }

        return card;
    }

    /**
     * dp(): แปลงค่า dp เป็นพิกเซล (px) ตามความหนาแน่นหน้าจอของเครื่อง
     */
    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }
}
