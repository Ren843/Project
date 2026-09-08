package com.example.ryo_q;

import android.content.Context;
import android.content.SharedPreferences;

 // คลาส ScoreManager ทำหน้าที่จัดการการบันทึกและดึงข้อมูลคะแนนดาว
 // โดยใช้ SharedPreferences ในการเก็บข้อมูลลงในเครื่อง
public class ScoreManager {
    private static final String PREF_NAME = "RobotRepairScores";
    private static final String KEY_PREFIX = "STARS_";

     // บันทึกจำนวนดาวที่ทำได้ในแต่ละด่าน
     // จะบันทึกเฉพาะเมื่อคะแนนใหม่สูงกว่าคะแนนเดิมที่เคยทำไว้
     // @param context Context ของแอปพลิเคชัน
     // @param lang    ภาษาโปรแกรม (แนะนำใช้ GameConstants.LANG_XXX)
     // @param diff    ระดับความยาก (แนะนำใช้ GameConstants.DIFF_XXX)
     // @param stars   จำนวนดาวที่ทำได้ (0-3)
    public static void saveStars(Context context, String lang, String diff, int stars) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String key = generateKey(lang, diff);
        int currentBest = pref.getInt(key, 0);

        // บันทึกเฉพาะเมื่อทำคะแนนได้ดีกว่าสถิติเดิม (High Score)
        if (stars > currentBest) {
            editor.putInt(key, stars);
            editor.apply();
        }
    }

    // ดึงจำนวนดาวที่เคยบันทึกไว้ในด่านนั้นๆ
    public static int getSavedStars(Context context, String lang, String diff) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(generateKey(lang, diff), 0);
    }

    // คำนวณหาจำนวนดาวรวมทั้งหมดที่ผู้เล่นสะสมได้จากทุกภาษาและทุกระดับ
    public static int getTotalStars(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int total = 0;
        String[] languages = {GameConstants.LANG_JAVA, GameConstants.LANG_PYTHON, GameConstants.LANG_CPP};
        String[] difficulties = {GameConstants.DIFF_EASY, GameConstants.DIFF_NORMAL, GameConstants.DIFF_HARD};

        for (String lang : languages) {
            for (String diff : difficulties) {
                total += pref.getInt(generateKey(lang, diff), 0);
            }
        }
        return total;
    }

    // ล้างข้อมูลคะแนนทั้งหมด (Reset Game)
    public static void clearAllScores(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }

    // สร้าง Key สำหรับจัดเก็บข้อมูลเพื่อให้เป็นรูปแบบเดียวกัน
    private static String generateKey(String lang, String diff) {
        return KEY_PREFIX + lang.toUpperCase() + "_" + diff.toUpperCase();
    }
}
