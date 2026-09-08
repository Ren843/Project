package com.example.ryo_q;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ทดสอบระบบ Data Layer หลังจากปรับปรุงเสร็จสมบูรณ์
        List<Question> testList = QuestionRepository.getQuestions(GameConstants.LANG_JAVA, GameConstants.DIFF_EASY);
        Log.d("DB_TEST", "จำนวนโจทย์ Java Easy (จากค่าคงที่): " + testList.size());

        Robot robot = RobotRepository.getRobotById(1);
        if (robot != null) {
            Log.d("DB_TEST", "พบหุ่นยนต์: " + robot.getName());
        }

        ScoreManager.saveStars(this, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL, 3);
        int totalStars = ScoreManager.getTotalStars(this);
        Log.d("DB_TEST", "คะแนนรวมทั้งหมด: " + totalStars);

    }
}