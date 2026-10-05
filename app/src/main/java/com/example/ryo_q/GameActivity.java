package com.example.ryo_q;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * คลาส GameActivity ทำหน้าที่ควบคุมหน้าจอการเล่นเกม (UI Activity)
 * เชื่อมต่อกับ QuizManager คุมระบบจับเวลาถอยหลัง (CountDownTimer) แสดงคำถาม ตัวเลือก และอัปเดตสถานะหุ่นยนต์
 */
public class GameActivity extends AppCompatActivity {

    // ==========================================
    // ส่วนประกอบ UI (UI Elements)
    // ==========================================
    private TextView tvHeader;
    private TextView tvTimer;
    private TextView tvCodeSnippet;
    private TextView tvFeedback;
    private ImageView ivRobot;
    private TextView tvRobotName;
    private TextView tvRobotStatus;
    private Button btnOption0;
    private Button btnOption1;
    private Button btnOption2;
    private Button btnNext;

    // ==========================================
    // ตัวแปรลอจิกและเวลา (Logic & Timer)
    // ==========================================
    private QuizManager quizManager;        // ตัวควบคุมการเล่นเกม
    private CountDownTimer countDownTimer;  // ตัวจับเวลาถอยหลังประจำข้อ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        // 1. รับข้อมูลภาษาและระดับความยากที่ส่งมาจาก CategoryActivity
        String langExtra = getIntent() != null ? getIntent().getStringExtra("language") : null;
        String diffExtra = getIntent() != null ? getIntent().getStringExtra("difficulty") : null;

        String language = langExtra != null ? langExtra : GameConstants.LANG_JAVA;
        String difficulty = diffExtra != null ? diffExtra : GameConstants.DIFF_EASY;

        // 2. ผูกองค์ประกอบ UI เข้ากับไฟล์ Layout activity_game.xml
        tvHeader = findViewById(R.id.tvHeader);
        tvTimer = findViewById(R.id.tvTimer);
        tvCodeSnippet = findViewById(R.id.tvCodeSnippet);
        tvFeedback = findViewById(R.id.tvFeedback);
        ivRobot = findViewById(R.id.ivRobot);
        tvRobotName = findViewById(R.id.tvRobotName);
        tvRobotStatus = findViewById(R.id.tvRobotStatus);
        btnOption0 = findViewById(R.id.btnOption0);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnNext = findViewById(R.id.btnNext);

        // 3. ซ่อน Action Bar และระบบแทร็กบาร์แถบระบบ
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

        // 4. เริ่มสร้างวัตถุ QuizManager เพื่อจัดการลอจิกคำถามและหุ่นยนต์
        quizManager = new QuizManager(language, difficulty);

        if (quizManager.getQuestions().isEmpty()) {
            Toast.makeText(this, R.string.no_questions, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 5. ตั้งค่า OnClickListener ให้ปุ่มเลือกคำตอบทั้ง 3 ข้อ
        btnOption0.setOnClickListener(v -> handleAnswerClick(0));
        btnOption1.setOnClickListener(v -> handleAnswerClick(1));
        btnOption2.setOnClickListener(v -> handleAnswerClick(2));

        // 6. ปุ่มคำถามข้อถัดไป
        btnNext.setOnClickListener(v -> {
            if (quizManager.moveToNextQuestion()) {
                displayQuestion();
            } else {
                finishGame();
            }
        });

        // 7. แสดงโจทย์ข้อแรก
        displayQuestion();
    }

    /**
     * แสดงรายละเอียดโจทย์ปัจจุบัน ข้อมูลหุ่นยนต์ และเริ่มจับเวลาถอยหลัง
     */
    private void displayQuestion() {
        Question currentQuestion = quizManager.getCurrentQuestion();
        if (currentQuestion == null) {
            finishGame();
            return;
        }

        // อัปเดตหัวข้อโจทย์
        String headerText = quizManager.getLanguage() + " - " + quizManager.getDifficulty()
                + " | ข้อที่ " + (quizManager.getCurrentQuestionIndex() + 1) + "/" + quizManager.getTotalQuestions();
        tvHeader.setText(headerText);
        tvCodeSnippet.setText(currentQuestion.getCodeSnippet());

        // อัปเดตตัวเลือกคำตอบทั้ง 3 ตัวเลือก
        String[] options = currentQuestion.getOptions();
        btnOption0.setText(options.length > 0 ? options[0] : "");
        btnOption1.setText(options.length > 1 ? options[1] : "");
        btnOption2.setText(options.length > 2 ? options[2] : "");

        btnOption0.setEnabled(true);
        btnOption1.setEnabled(true);
        btnOption2.setEnabled(true);

        tvFeedback.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.GONE);

        // แสดงข้อมูลหุ่นยนต์ประจำด่าน
        updateRobotUI();

        // เริ่มจับเวลาถอยหลังตามกำหนดใน GameConstants
        startCountDownTimer();
    }

    /**
     * อัปเดตรูปภาพ ชื่อ และสถานะการซ่อมแซมของหุ่นยนต์บน UI
     */
    private void updateRobotUI() {
        Robot robot = quizManager.getRobot();
        if (robot != null) {
            ivRobot.setImageResource(robot.getImageResId());
            tvRobotName.setText(robot.getName());

            if (robot.isFixed()) {
                tvRobotStatus.setText(R.string.robot_fixed_status);
                tvRobotStatus.setTextColor(0xFF00FF00); // สีเขียว
            } else {
                String statusText = getString(R.string.robot_broken_status,
                        robot.getCurrentCorrectCount(), robot.getRequiredCorrectCount());
                tvRobotStatus.setText(statusText);
                tvRobotStatus.setTextColor(0xFFFF6666); // สีแดงอ่อน
            }
        }
    }

    /**
     * เริ่มจับเวลาถอยหลังต่อข้อ
     */
    private void startCountDownTimer() {
        // ยกเลิกตัวจับเวลาเดิมก่อนสร้างใหม่
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        long timeLimitMillis = quizManager.getTimeLimitPerQuestion() * 1000L;

        countDownTimer = new CountDownTimer(timeLimitMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                String timerText = "⏱️ " + secondsRemaining + "s";
                tvTimer.setText(timerText);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("⏱️ 0s");
                onTimeOut();
            }
        }.start();
    }

    /**
     * ดำเนินการเมื่อหมดเวลาในข้อนั้นๆ (ถือว่าตอบผิด)
     */
    private void onTimeOut() {
        btnOption0.setEnabled(false);
        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);

        Question q = quizManager.getCurrentQuestion();
        tvFeedback.setVisibility(View.VISIBLE);
        tvFeedback.setTextColor(0xFFFF0000); // สีแดง
        if (q != null) {
            tvFeedback.setText(getString(R.string.timeout_correct_answer, q.getCorrectAnswerText()));
        } else {
            tvFeedback.setText(R.string.time_out);
        }

        btnNext.setVisibility(View.VISIBLE);
    }

    /**
     * ตรวจคำตอบเมื่อผู้เล่นคลิกเลือกตัวเลือกข้อใดข้อหนึ่ง
     * @param selectedIndex ดัชนีตัวเลือกที่คลิก (0, 1, 2)
     */
    private void handleAnswerClick(int selectedIndex) {
        // ยกเลิกตัวจับเวลาถอยหลัง
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        btnOption0.setEnabled(false);
        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);

        boolean isCorrect = quizManager.checkAnswer(selectedIndex);
        Question q = quizManager.getCurrentQuestion();

        tvFeedback.setVisibility(View.VISIBLE);
        if (isCorrect) {
            tvFeedback.setText(R.string.game_correct);
            tvFeedback.setTextColor(0xFF00FF00); // สีเขียว
        } else {
            String wrongText = getString(R.string.game_wrong, (q != null ? q.getCorrectAnswerText() : ""));
            tvFeedback.setText(wrongText);
            tvFeedback.setTextColor(0xFFFF0000); // สีแดง
        }

        // อัปเดตการเปลี่ยนแปลงสถานะหุ่นยนต์บน UI
        updateRobotUI();

        btnNext.setVisibility(View.VISIBLE);
    }

    /**
     * จบด่าน คิดคะแนนดาว บันทึกสถิติ และปิด Activity
     */
    private void finishGame() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        int score = quizManager.getScore();
        int total = quizManager.getTotalQuestions();
        int stars = quizManager.calculateStars();

        // บันทึกจำนวนดาวที่ทำได้ลง SharedPreferences ผ่าน ScoreManager
        ScoreManager.saveStars(this, quizManager.getLanguage(), quizManager.getDifficulty(), stars);

        Robot robot = quizManager.getRobot();
        String resultMsg = "จบการทดสอบ! คะแนน: " + score + "/" + total + " (" + stars + " ดาว)";
        if (robot != null && robot.isFixed()) {
            resultMsg += "\nซ่อมแซมหุ่นยนต์ " + robot.getName() + " สำเร็จ!";
        }

        Toast.makeText(this, resultMsg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ทำความสะอาดและยกเลิก Timer ป้องกัน Memory Leak
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
