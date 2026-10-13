package com.example.ryo_q;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameplayActivity extends AppCompatActivity {

    private static final long QUESTION_TIME_MS = 20000; // 20 วินาทีต่อข้อ

    private QuizManager quizManager;
    private CountDownTimer countDownTimer;
    private long timeLeftMs;

    private TextView textCodeSnippet, textTimer, textQuestionPrompt, textFileName;
    private Button option1Button, option2Button, option3Button;

    private int totalStarsEarned = 0;
    private String language, difficulty;

    // [FIX 1] กันแตะซ้ำ / กัน finishGame ซ้ำ
    private boolean answered = false;
    private boolean finished = false;
    // [FIX 4] จำว่า timer ถูกหยุดเพราะแอปถูกพักอยู่หรือไม่
    private boolean timerPausedByStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_gameplay);

        // ซ่อน ActionBar ครั้งเดียวที่นี่ (เดิมเรียกทุกครั้งที่ตอบ)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textCodeSnippet = findViewById(R.id.textCodeSnippet);
        textTimer = findViewById(R.id.textTimer);
        textQuestionPrompt = findViewById(R.id.textQuestionPrompt);
        textFileName = findViewById(R.id.textFileName); // [FIX 5] ดูข้อ XML ด้านล่าง
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);

        language = getIntent().getStringExtra("language");
        difficulty = getIntent().getStringExtra("difficulty");

        // กัน NPE ถ้าไม่มี extra ส่งมา
        if (language == null || difficulty == null) {
            finish();
            return;
        }

        if (textFileName != null) {
            textFileName.setText(fileNameFor(language));
        }

        // ตั้ง listener ครั้งเดียว (เดิมตั้งใหม่ทุกข้อ)
        option1Button.setOnClickListener(v -> onAnswerSelected(0));
        option2Button.setOnClickListener(v -> onAnswerSelected(1));
        option3Button.setOnClickListener(v -> onAnswerSelected(2));

        quizManager = new QuizManager();
        quizManager.startGame(language, difficulty);

        showCurrentQuestion();
    }

    private String fileNameFor(String lang) {
        if (GameConstants.LANG_JAVA.equals(lang)) return "Main.java";
        if (GameConstants.LANG_PYTHON.equals(lang)) return "main.py";
        return "main.c";
    }

    private void showCurrentQuestion() {
        Question current = quizManager.getCurrentQuestion();

        if (current == null) {
            finishGame();
            return;
        }

        textCodeSnippet.setText(current.getCodeSnippet());
        if (textQuestionPrompt != null) {
            textQuestionPrompt.setText(current.getQuestionText());
        }

        String[] options = current.getOptions();
        bindOption(option1Button, options, 0);
        bindOption(option2Button, options, 1);
        bindOption(option3Button, options, 2);

        answered = false;               // [FIX 1] เปิดรับคำตอบของข้อใหม่
        startTimer(QUESTION_TIME_MS);
    }

    // [FIX 3] ซ่อนปุ่มที่ไม่มีตัวเลือก แทนการแสดงปุ่มว่างที่กดได้
    private void bindOption(Button button, String[] options, int index) {
        if (index < options.length) {
            button.setText(options[index]);
            button.setVisibility(View.VISIBLE);

            // ความสูงปุ่มตามความยาวตัวเลือก (บรรทัดมาก = สูงขึ้น)
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) button.getLayoutParams();
            lp.weight = estimateLines(options[index]) + 1; // +1 เป็นพื้นฐานให้ปุ่มสั้นยังกดง่าย
            button.setLayoutParams(lp);
        } else {
            button.setText("");
            button.setVisibility(View.GONE);
        }
    }

    // ประมาณจำนวนบรรทัดที่ข้อความจะใช้ (นับ \n และบรรทัดที่ยาวจนขึ้นบรรทัดใหม่)
    private float estimateLines(String text) {
        final int CHARS_PER_LINE = 60;   // แนวนอน ปุ่มกว้าง
        int lines = 0;
        for (String line : text.split("\n", -1)) {
            lines += Math.max(1, (int) Math.ceil(line.length() / (double) CHARS_PER_LINE));
        }
        return Math.max(1, Math.min(lines, 10));
    }

    private void startTimer(long durationMs) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        timeLeftMs = durationMs;
        countDownTimer = new CountDownTimer(durationMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMs = millisUntilFinished;
                textTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timeLeftMs = 0;
                onAnswerSelected(-1); // -1 = หมดเวลา
            }
        };
        countDownTimer.start();
    }

    private void onAnswerSelected(int selectedIndex) {
        // [FIX 1] ตอบได้ครั้งเดียวต่อข้อ และไม่รับหลังจบเกม
        if (answered || finished) return;
        answered = true;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        boolean isCorrect = quizManager.checkAnswer(selectedIndex);
        int stars = isCorrect ? quizManager.calculateStars(timeLeftMs, QUESTION_TIME_MS) : 0;
        totalStarsEarned += stars;

        if (quizManager.hasNextQuestion()) {
            quizManager.moveToNextQuestion();
            showCurrentQuestion();
        } else {
            finishGame();
        }
    }

    private void finishGame() {
        if (finished) return;           // [FIX 1] กันเปิด ResultActivity ซ้อน
        finished = true;

        int totalQuestions = quizManager.getTotalQuestions();
        int finalStars = 0;

        if (totalQuestions > 0) {
            double average = (double) totalStarsEarned / totalQuestions;
            finalStars = (int) Math.round(average);
            finalStars = Math.max(0, Math.min(3, finalStars));
        }

        ScoreManager.saveStars(this, language, difficulty, finalStars);

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("stars", finalStars);
        intent.putExtra("correctCount", quizManager.getCorrectCount());
        intent.putExtra("totalQuestions", totalQuestions);
        intent.putExtra("language", language);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("questionList", new ArrayList<>(quizManager.getQuestionList()));
        intent.putExtra("selectedAnswers", new ArrayList<>(quizManager.getSelectedAnswers()));
        startActivity(intent);
        finish();
    }

    //  หยุดเวลาตอนแอปไม่อยู่หน้าจอ แล้วเดินต่อจากเวลาที่เหลือ
    @Override
    protected void onStop() {
        super.onStop();
        if (!answered && !finished && countDownTimer != null) {
            countDownTimer.cancel();
            timerPausedByStop = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerPausedByStop && !answered && !finished) {
            timerPausedByStop = false;
            if (timeLeftMs > 0) {
                startTimer(timeLeftMs);
            } else {
                onAnswerSelected(-1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}