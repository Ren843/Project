package com.example.ryo_q;

import java.util.ArrayList;
import java.util.List;

 // คลังเก็บข้อมูลหุ่นยนต์ทั้งหมดในเกม
public class RobotRepository {

    // ดึงรายชื่อหุ่นยนต์ทั้งหมดที่มีในเกม
    public static List<Robot> getAllRobots() {
        List<Robot> list = new ArrayList<>();
        
        // ตัวอย่างหุ่นยนต์ (id, name, imageResId)
        // หมายเหตุ: imageResId ควรเป็น R.drawable.xxx ในโปรเจกต์จริง
        list.add(new Robot(1, "Ryo-01", 0));
        list.add(new Robot(2, "Q-Bot", 0));
        list.add(new Robot(3, "Steel-Wing", 0));
        list.add(new Robot(4, "Rusty", 0));
        list.add(new Robot(5, "Sparky", 0));
        
        return list;
    }

    // ค้นหาหุ่นยนต์ตามรหัสประจำตัว (ID)
    public static Robot getRobotById(int id) {
        for (Robot r : getAllRobots()) {
            if (r.getRobotId() == id) {
                return r;
            }
        }
        return null;
    }
}
