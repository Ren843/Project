package com.example.ryo_q;

import java.io.Serializable;

 // คลาส Robot แทนตัวละครหุ่นยนต์ในเกมที่ผู้เล่นต้องซ่อมแซม
 // รองรับ Serializable เพื่อให้สามารถส่งข้อมูลระหว่าง Activity ได้
public class Robot implements Serializable {
    
    // สถานะของหุ่นยนต์
    public static final String STATUS_BROKEN = "BROKEN";
    public static final String STATUS_FIXED = "FIXED";

    private int robotId;
    private String name;
    private String status; // เก็บสถานะ "BROKEN" หรือ "FIXED"
    private int imageResId; // ไอดีของรูปภาพหุ่นยนต์ (R.drawable.xxx)

     // คอนสตรักเตอร์สำหรับสร้างหุ่นยนต์ใหม่
     // @param robotId รหัสประจำตัวหุ่นยนต์
     // @param name ชื่อหุ่นยนต์
     // @param imageResId ไอดีทรัพยากรรูปภาพ
    public Robot(int robotId, String name, int imageResId) {
        this.robotId = robotId;
        this.name = name;
        this.imageResId = imageResId;
        this.status = STATUS_BROKEN; // ตั้งค่าเริ่มต้นเป็น "พัง"
    }

    // อัปเดตสถานะเป็นซ่อมเสร็จเรียบร้อย
    public void repair() {
        this.status = STATUS_FIXED;
    }

    // ตรวจสอบว่าหุ่นยนต์ตัวนี้ซ่อมเสร็จแล้วหรือยัง
    // @return true ถ้าซ่อมเสร็จแล้ว
    public boolean isFixed() {
        return STATUS_FIXED.equals(this.status);
    }

    // Getter Methods
    public int getRobotId() {
        return robotId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getImageResId() {
        return imageResId;
    }
    
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
