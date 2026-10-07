package com.example.ryo_q;

import java.util.List;

/**
 * คลาส QuizManager คุมลอจิกการดำเนินเกมคำถาม (Quiz Engine)
 * ทำหน้าที่ถือรายการ Question ดึงจาก QuestionRepository, ควบคุม Robot, ตรวจคำตอบ, คิดดาว และคำนวณเวลา
 */
public class QuizManager {

    // ==========================================
    // ตัวแปรสถานะเกม (Quiz State Variables)
    // ==========================================
    private final String language;          // ภาษาโปรแกรมที่เลือก (JAVA, PYTHON, CPP)
    private final String difficulty;        // ระดับความยากที่เลือก (EASY, NORMAL, HARD)
    private final List<Question> questions; // รายการคำถามทั้งหมดดึงมาจาก QuestionRepository
    private final Robot robot;             // หุ่นยนต์ที่กำลังทำการซ่อมแซมในด่านนี้
    private final int timeLimitPerQuestion; // เวลาถอยหลังต่อข้อ (หน่วย: วินาที)

    private int currentQuestionIndex;       // ดัชนีคำถามปัจจุบัน (เริ่มจาก 0)
    private int score;                      // คะแนนรวมที่ตอบถูก

    /**
     * คอนสตรักเตอร์สำหรับสร้าง QuizManager
     * @param language ภาษาโปรแกรมที่ผู้เล่นเลือก
     * @param difficulty ระดับความยากที่ผู้เล่นเลือก
     */
    public QuizManager(String language, String difficulty) {
        this.language = language;
        this.difficulty = difficulty;

        // 1. ดึงรายการโจทย์คำถามตรงตามภาษาและความยากจาก QuestionRepository
        this.questions = QuestionRepository.getQuestions(language, difficulty);

        // 2. ดึงข้อมูลหุ่นยนต์ประจำด่านจาก RobotRepository
        this.robot = RobotRepository.getRobotForCategory(language, difficulty);

        // 3. ดึงค่าเวลาจำกัดถอยหลังต่อข้อจาก GameConstants
        this.timeLimitPerQuestion = GameConstants.getTimeLimitInSeconds(difficulty);

        // 4. ตั้งค่าเริ่มต้นดัชนีข้อและคะแนน
        this.currentQuestionIndex = 0;
        this.score = 0;

        // กำหนดจำนวนข้อที่ต้องตอบถูกเพื่อซ่อมแซมหุ่นยนต์ให้สอดคล้องกับจำนวนโจทย์
        if (this.robot != null && !this.questions.isEmpty()) {
            this.robot.setRequiredCorrectCount((int) Math.ceil(this.questions.size() * 0.5));
        }
    }

    /**
     * ดึงวัตถุคำถามปัจจุบัน
     * @return วัตถุ Question หรือ null หากหมดคำถามแล้ว
     */
    public Question getCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    /**
     * ตรวจคำตอบที่ผู้เล่นเลือกในข้อปัจจุบัน
     * @param selectedIndex ดัชนีตัวเลือกที่ผู้เล่นเลือก (0, 1, 2)
     * @return true ถ้าตอบถูก, false ถ้าตอบผิด
     */
    public boolean checkAnswer(int selectedIndex) {
        Question current = getCurrentQuestion();
        if (current == null) return false;

        boolean isCorrect = current.isCorrect(selectedIndex);
        if (isCorrect) {
            score++;
            // เมื่อตอบถูก สั่งเพิ่มคะแนนความสำเร็จให้หุ่นยนต์ (หากครบตามเกณฑ์ robot จะเรียก repair() เปลี่ยนสถานะเป็น FIXED)
            if (robot != null) {
                robot.addCorrectAnswer();
            }
        }
        return isCorrect;
    }

    /**
     * ข้ามไปยังคำถามข้อถัดไป
     * @return true ถ้ายังมีคำถามข้อถัดไป, false ถ้าจบชุดคำถามแล้ว
     */
    public boolean moveToNextQuestion() {
        currentQuestionIndex++;
        return !isQuizFinished();
    }

    /**
     * ตรวจสอบว่าทำคำถามครบทุกข้อแล้วหรือยัง
     * @return true ถ้าทำครบหมดแล้ว
     */
    public boolean isQuizFinished() {
        return currentQuestionIndex >= questions.size();
    }

    /**
     * คำนวณดาวที่ได้รับ (0 - 3 ดาว) ตามเกณฑ์ที่กำหนดใน GameConstants
     * @return จำนวนดาว
     */
    public int calculateStars() {
        return GameConstants.calculateStars(score, questions.size());
    }

    // ==========================================
    // Getter Methods
    // ==========================================

    public String getLanguage() {
        return language;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Robot getRobot() {
        return robot;
    }

    public int getTimeLimitPerQuestion() {
        return timeLimitPerQuestion;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getScore() {
        return score;
    }
}
