package com.example.ryo_q;

/**
 * คลาส GameConstants สำหรับเก็บค่าคงที่ส่วนกลางที่ใช้ร่วมกันทั้งแอปพลิเคชัน
 * เช่น ชื่อภาษาโปรแกรม ระดับความยาก เวลาถอยหลัง และเกณฑ์การคิดดาว
 */
public class GameConstants {

    // ==========================================
    // 1. หมวดภาษาโปรแกรม (Programming Languages)
    // ==========================================
    public static final String LANG_JAVA = "JAVA";
    public static final String LANG_PYTHON = "PYTHON";
    public static final String LANG_CPP = "CPP";

    // ==========================================
    // 2. ระดับความยาก (Difficulty Levels)
    // ==========================================
    public static final String DIFF_EASY = "EASY";
    public static final String DIFF_NORMAL = "NORMAL";
    public static final String DIFF_HARD = "HARD";

    // ==========================================
    // 3. กำหนดเวลาถอยหลังแยกตามระดับความยาก (หน่วย: วินาที)
    // ==========================================
    // ระดับง่าย (EASY): ให้เวลาข้อละ 60 วินาที
    public static final int TIME_LIMIT_EASY_SEC = 60;
    
    // ระดับปานกลาง (NORMAL): ให้เวลาข้อละ 45 วินาที
    public static final int TIME_LIMIT_NORMAL_SEC = 45;
    
    // ระดับยาก (HARD): ให้เวลาข้อละ 30 วินาที
    public static final int TIME_LIMIT_HARD_SEC = 30;

    // ==========================================
    // 4. เกณฑ์การคิดคะแนนดาว (Star Thresholds)
    // ==========================================
    // ได้ 3 ดาว: ตอบถูกตั้งแต่ 80% ขึ้นไป
    public static final double PASS_3_STARS_PERCENT = 0.80;
    
    // ได้ 2 ดาว: ตอบถูกตั้งแต่ 50% ขึ้นไป
    public static final double PASS_2_STARS_PERCENT = 0.50;

    /**
     * ดึงค่าจำกัดเวลา (Time Limit) ในหน่วยวินาทีตามระดับความยากที่เลือก
     * @param difficulty ระดับความยาก (GameConstants.DIFF_EASY, DIFF_NORMAL, DIFF_HARD)
     * @return จำนวนวินาที
     */
    public static int getTimeLimitInSeconds(String difficulty) {
        if (DIFF_NORMAL.equalsIgnoreCase(difficulty)) {
            return TIME_LIMIT_NORMAL_SEC;
        } else if (DIFF_HARD.equalsIgnoreCase(difficulty)) {
            return TIME_LIMIT_HARD_SEC;
        } else {
            // ค่าเริ่มต้น หรือกรณี DIFF_EASY
            return TIME_LIMIT_EASY_SEC;
        }
    }

    /**
     * คำนวณจำนวนดาว (0 - 3 ดาว) จากคะแนนที่ทำได้และจำนวนข้อทั้งหมด
     * @param score คะแนนที่ตอบถูก
     * @param totalQuestions จำนวนโจทย์ทั้งหมด
     * @return จำนวนดาวที่ได้ (0, 1, 2, หรือ 3)
     */
    public static int calculateStars(int score, int totalQuestions) {
        if (totalQuestions <= 0) return 0;
        double ratio = (double) score / totalQuestions;

        if (ratio >= PASS_3_STARS_PERCENT) {
            return 3;
        } else if (ratio >= PASS_2_STARS_PERCENT) {
            return 2;
        } else if (score > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
