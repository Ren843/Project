package com.example.ryo_q;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * คลังข้อมูล RobotRepository สำหรับบริหารจัดการและจัดเก็บข้อมูลหุ่นยนต์ทั้งหมดในระบบ
 */
public class RobotRepository {

    /**
     * ดึงรายชื่อหุ่นยนต์ทั้งหมดที่มีในเกมพร้อมกำหนดรูปภาพ 2 สถานะ (พัง และ ซ่อมเสร็จ)
     * @return List ของ Object Robot
     */
    public static List<Robot> getAllRobots() {
        List<Robot> list = new ArrayList<>();

        // 1. หุ่นยนต์สีฟ้า (Blue Bot - Ryo-Blue)
        list.add(new Robot(
                1,
                "Ryo-Blue",
                "หุ่นยนต์ล้อเลื่อนสีฟ้า ระบบสายไฟและเลนส์ประมวลผลแตกเสียหาย",
                R.drawable.robot_blue_broken,
                R.drawable.robot_blue_repair,
                5
        ));

        // 2. หุ่นยนต์สีเขียว (Green Bot - G-Mech)
        list.add(new Robot(
                2,
                "G-Mech",
                "หุ่นยนต์เกราะเหล็กสีเขียว แขนซ้ายขาดและวงจรภายในลัดวงจร",
                R.drawable.robot_green_broken,
                R.drawable.robot_green_repair,
                5
        ));

        // 3. หุ่นยนต์สีส้ม (Orange Bot - Wheel-Orange)
        list.add(new Robot(
                3,
                "Wheel-Orange",
                "หุ่นยนต์สี่ล้อสีส้ม ล้อแบนและแขนขวาหลุดร่วง",
                R.drawable.robot_orange_broken,
                R.drawable.robot_orange_repair,
                5
        ));

        // 4. หุ่นยนต์สีม่วง (Purple Bot - Volt-Purple)
        list.add(new Robot(
                4,
                "Volt-Purple",
                "หุ่นยนต์ฮิวมานอยด์สีม่วง วงจรเกราะอกและสายไฟห้อยระย้า",
                R.drawable.robot_purple_broken,
                R.drawable.robot_purple_repair,
                5
        ));

        // 5. หุ่นยนต์สีแดง (Red Bot - Mecha-Red)
        list.add(new Robot(
                5,
                "Mecha-Red",
                "หุ่นยนต์ต่อสู้สีแดง ส่วนหัวหลุดหายและเกราะอกเสียหายหนัก",
                R.drawable.robot_red_broken,
                R.drawable.robot_red_repair,
                5
        ));

        // 6. หุ่นยนต์สีเหลือง (Yellow Bot - Spark-Yellow)
        list.add(new Robot(
                6,
                "Spark-Yellow",
                "หุ่นยนต์ความเร็วสูงสีเหลือง เซ็นเซอร์ดวงตาดับและเกราะทะลุ",
                R.drawable.robot_yellow_broken,
                R.drawable.robot_yellow_repair,
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
     * สุ่มหุ่นยนต์ 1 ตัวจากทั้งหมด 6 สี
     * @return วัตถุ Robot ที่ถูกสุ่มมา
     */
    public static Robot getRandomRobot() {
        List<Robot> robots = getAllRobots();
        int randomIndex = new Random().nextInt(robots.size());
        return robots.get(randomIndex);
    }

    /**
     * ดึงหุ่นยนต์ประจำภาษาและระดับความยาก
     * @param lang ภาษาโปรแกรม (JAVA, PYTHON, CPP)
     * @param diff ระดับความยาก (EASY, NORMAL, HARD)
     * @return วัตถุ Robot ที่ถูกสุ่มมาเล่นในด่านนั้น
     */
    public static Robot getRobotForCategory(String lang, String diff) {
        return getRandomRobot();
    }
}
