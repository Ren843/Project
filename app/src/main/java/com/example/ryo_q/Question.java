package com.example.ryo_q;

import java.io.Serializable;

 // คลาส Question ใช้สำหรับเก็บข้อมูลของคำถามแต่ละข้อในแอปพลิเคชัน
 // รองรับ Serializable เพื่อให้สามารถส่ง Object ผ่าน Intent ระหว่าง Activity ได้
public class Question implements Serializable {

     // โจทย์หรือคำอธิบายคำถาม (นำมาจากคอมเมนต์)
     private String questionText;

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

     // คอนสตรักเตอร์แบบมีข้อความโจทย์
     public Question(String questionText, String codeSnippet, String[] options, int correctAnswerIndex, String language, String difficulty) {
         this.questionText = cleanQuestionText(questionText);
         this.codeSnippet = codeSnippet;
         this.options = options;
         this.correctAnswerIndex = correctAnswerIndex;
         this.language = language;
         this.difficulty = difficulty;
     }

     // คอนสตรักเตอร์แบบเดิม (รองรับกรณีไม่ได้ใส่ข้อความโจทย์)
     public Question(String codeSnippet, String[] options, int correctAnswerIndex, String language, String difficulty) {
         this.questionText = "Fix the bug in the following code:";
         this.codeSnippet = codeSnippet;
         this.options = options;
         this.correctAnswerIndex = correctAnswerIndex;
         this.language = language;
         this.difficulty = difficulty;
     }

     // Getter Methods
     public String getQuestionText() {
         return cleanQuestionText(questionText);
     }

     private String cleanQuestionText(String text) {
         if (text == null) return null;
         // ลบเลขข้อนำหน้า เช่น "1. ", "10. ", " 2. "
         return text.replaceAll("^\\s*\\d+\\.\\s*", "").trim();
     }

     public String getCodeSnippet() {
         return codeSnippet;
     }

     public String[] getOptions() {
         return options;
     }

     public int getCorrectAnswerIndex() {
         return correctAnswerIndex;
     }

     public String getLanguage() {
         return language;
     }

     public String getDifficulty() {
         return difficulty;
     }

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

     // สุ่มสลับตัวเลือกคำตอบ (Options) และอัปเดต index ของคำตอบที่ถูกต้อง
     public void shuffleOptions() {
         if (options == null || options.length <= 1) return;

         String correctOption = getCorrectAnswerText();
         java.util.List<String> optionList = new java.util.ArrayList<>(java.util.Arrays.asList(options));

         String last = optionList.get(optionList.size() - 1);
         boolean pinLast = last.startsWith("Both") || last.startsWith("All of");

         java.util.List<String> shuffleable = pinLast
                 ? optionList.subList(0, optionList.size() - 1)
                 : optionList;
         java.util.Collections.shuffle(shuffleable);

         this.options = optionList.toArray(new String[0]);
         for (int i = 0; i < this.options.length; i++) {
             if (this.options[i].equals(correctOption)) {
                 this.correctAnswerIndex = i;
                 break;
             }
         }
     }
 }
