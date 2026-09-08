package com.example.ryo_q;

import java.io.Serializable;

 // คลาส Question ใช้สำหรับเก็บข้อมูลของคำถามแต่ละข้อในแอปพลิเคชัน
 // รองรับ Serializable เพื่อให้สามารถส่ง Object ผ่าน Intent ระหว่าง Activity ได้
public class Question implements Serializable {

    // ส่วนของโค้ดโปรแกรมที่จะแสดงให้ผู้ใช้ดู
    private String codeSnippet;
    
    // รายการตัวเลือกคำตอบ
    private String[] options;
    
    // ลำดับ ของคำตอบที่ถูกต้องในอาร์เรย์ options
    private int correctAnswerIndex;
    
    // ภาษาโปรแกรมของคำถามข้อนี้ (JAVA, PYTHON, CPP)
    private String language;
    
    // ระดับความยากของคำถาม (EASY, NORMAL, HARD)
    private String difficulty;

    // คอนสตรักเตอร์สำหรับสร้าง Object ของ Question พร้อมกำหนดค่าเริ่มต้น
    public Question(String codeSnippet, String[] options, int correctAnswerIndex, String language, String difficulty) {
        this.codeSnippet = codeSnippet;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.language = language;
        this.difficulty = difficulty;
    }

    // Getter Methods
    public String getCodeSnippet() { return codeSnippet; }
    
    public String[] getOptions() { return options; }
    
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    
    public String getLanguage() { return language; }
    
    public String getDifficulty() { return difficulty; }

    // Helper Methods
    // ตรวจสอบว่าคำตอบที่ผู้เล่นเลือกถูกต้องหรือไม่
    // @param selectedIndex ลำดับที่ผู้เล่นเลือก
    // @return true ถ้าถูกต้อง, false ถ้าไม่ถูกต้อง
    public boolean isCorrect(int selectedIndex) {
        return selectedIndex == correctAnswerIndex;
    }

    // ดึงข้อความของคำตอบที่ถูกต้องออกมา
    // @return ข้อความคำตอบที่ถูกต้อง
    public String getCorrectAnswerText() {
        if (options != null && correctAnswerIndex >= 0 && correctAnswerIndex < options.length) {
            return options[correctAnswerIndex];
        }
        return " ";
    }
}
