package com.example.ryo_q;

import java.util.ArrayList;
import java.util.List;

/**
 * คลังข้อมูล RobotRepository สำหรับบริหารจัดการและจัดเก็บข้อมูลหุ่นยนต์ทั้งหมดในระบบ
 */
public class RobotRepository {

    /**
     * ดึงรายชื่อหุ่นยนต์ทั้งหมดที่มีในเกมพร้อมกำหนดรูปภาพทรัพยากร (R.drawable.robot)
     * @return List ของ Object Robot
     */
    public static List<Robot> getAllRobots() {
        List<Robot> list = new ArrayList<>();

        // หุ่นยนต์ตัวหลักของเกม Ryo-01 ใช้รูปภาพ R.drawable.robot
        list.add(new Robot(
                1,
                "Ryo-01",
                "หุ่นยนต์ผู้ช่วยอัจฉริยะ วงจรประมวลผลหลักเสียหายจากไฟฟ้าช็อต",
                R.drawable.robot,
                5
        ));

        // หุ่นยนต์ตัวอื่นๆ ในระบบ
        list.add(new Robot(
                2,
                "Q-Bot",
                "หุ่นยนต์ตรวจสอบคุณภาพ ซิปประมวลผลคำสั่งขัดข้อง",
                R.drawable.robot,
                5
        ));

        list.add(new Robot(
                3,
                "Steel-Wing",
                "หุ่นยนต์บินสำรวจ ระบบควบคุมการบินขัดข้อง",
                R.drawable.robot,
                5
        ));

        list.add(new Robot(
                4,
                "Rusty",
                "หุ่นยนต์รุ่นเก่า หน่วยความจำทำงานผิดพลาด",
                R.drawable.robot,
                5
        ));

        list.add(new Robot(
                5,
                "Sparky",
                "หุ่นยนต์พลังงานไฟฟ้า ระบบแปลงสัญญาณเสียหาย",
                R.drawable.robot,
                5
        ));

        return list;
    }

    /**
     * ค้นหาหุ่นยนต์ตามรหัสประจำตัว (ID)
     * @param id รหัสหุ่นยนต์
     * @return วัตถุ Robot หรือ null หากไม่พบ
     */
    public static Robot getRobotById(int id) {
        for (Robot r : getAllRobots()) {
            if (r.getRobotId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * ดึงหุ่นยนต์ประจำภาษาและระดับความยาก
     * @param lang ภาษาโปรแกรม (JAVA, PYTHON, CPP)
     * @param diff ระดับความยาก (EASY, NORMAL, HARD)
     * @return วัตถุ Robot ที่กำหนดไว้สำหรับด่านนั้น
     */
    public static Robot getRobotForCategory(String lang, String diff) {
        List<Robot> robots = getAllRobots();
        int baseIndex = 0;

        if (GameConstants.LANG_PYTHON.equalsIgnoreCase(lang)) {
            baseIndex = 1;
        } else if (GameConstants.LANG_CPP.equalsIgnoreCase(lang)) {
            baseIndex = 2;
        }

        int offset = 0;
        if (GameConstants.DIFF_NORMAL.equalsIgnoreCase(diff)) {
            offset = 1;
        } else if (GameConstants.DIFF_HARD.equalsIgnoreCase(diff)) {
            offset = 2;
        }

        int finalIndex = (baseIndex + offset) % robots.size();
        return robots.get(finalIndex);
    }
}
