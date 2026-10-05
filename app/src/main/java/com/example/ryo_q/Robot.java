package com.example.ryo_q;

import java.io.Serializable;

/**
 * คลาส Robot ตัวละครหุ่นยนต์ในเกมที่ผู้เล่นต้องซ่อมแซม
 * รองรับ Serializable เพื่อให้สามารถส่ง Object ข้อมูลผ่าน Intent ระหว่าง Activity ได้
 */
public class Robot implements Serializable {

    // ==========================================
    // ค่าคงที่แสดงสถานะของหุ่นยนต์ (Robot Status)
    // ==========================================
    public static final String STATUS_BROKEN = "BROKEN"; // สถานะ: ชำรุด/พัง
    public static final String STATUS_FIXED = "FIXED";   // สถานะ: ซ่อมแซมเสร็จสิ้น

    // ==========================================
    // ข้อมูลสมาชิกของหุ่นยนต์ (Robot Fields)
    // ==========================================
    private final int robotId;           // รหัสประจำตัวหุ่นยนต์ (ID)
    private final String name;           // ชื่อของหุ่นยนต์
    private String description;          // คำอธิบาย/รายละเอียดประวัติของหุ่นยนต์
    private String status;               // เก็บสถานะปัจจุบัน ("BROKEN" หรือ "FIXED")
    private int imageResId;              // ไอดีของทรัพยากรรูปภาพหุ่นยนต์ (เช่น R.drawable.robot)
    private int requiredCorrectCount;    // จำนวนข้อที่ต้องตอบถูกทั้งหมดในการซ่อมแซม
    private int currentCorrectCount;     // จำนวนข้อที่ตอบถูกสะสมในปัจจุบัน

    /**
     * คอนสตรักเตอร์สร้างหุ่นยนต์ใหม่แบบพื้นฐาน
     * @param robotId รหัสประจำตัว
     * @param name ชื่อหุ่นยนต์
     * @param imageResId ทรัพยากรรูปภาพ (R.drawable.xxx)
     */
    public Robot(int robotId, String name, int imageResId) {
        this(robotId, name, "หุ่นยนต์ที่ต้องการซ่อมแซมวงจร", imageResId, 5);
    }

    /**
     * คอนสตรักเตอร์แบบสมบูรณ์
     * @param robotId รหัสประจำตัว
     * @param name ชื่อหุ่นยนต์
     * @param description รายละเอียดหุ่นยนต์
     * @param imageResId ทรัพยากรรูปภาพ
     * @param requiredCorrectCount จำนวนข้อที่ต้องตอบถูกเพื่อซ่อม
     */
    public Robot(int robotId, String name, String description, int imageResId, int requiredCorrectCount) {
        this.robotId = robotId;
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.requiredCorrectCount = requiredCorrectCount;
        this.currentCorrectCount = 0;
        this.status = STATUS_BROKEN; // เริ่มต้นให้สถานะเป็นชำรุด (BROKEN)
    }

    /**
     * สั่งซ่อมแซมหุ่นยนต์ทันทีเมื่อเงื่อนไขสำเร็จ เปลี่ยนสถานะเป็น FIXED
     */
    public void repair() {
        this.status = STATUS_FIXED;
    }

    /**
     * เพิ่มจำนวนข้อที่ตอบถูกสะสม และตรวจสอบว่าครบตามเงื่อนไขที่ต้องซ่อมหรือไม่
     * หากตอบถูกครบตาม requiredCorrectCount จะทำการเรียก repair() อัตโนมัติ
     */
    public void addCorrectAnswer() {
        this.currentCorrectCount++;
        if (this.currentCorrectCount >= this.requiredCorrectCount) {
            repair();
        }
    }

    /**
     * ตรวจสอบว่าหุ่นยนต์ได้รับการซ่อมแซมเสร็จสิ้นแล้วหรือไม่
     * @return true ถ้าซ่อมเสร็จแล้ว (FIXED), false ถ้ายังชำรุด (BROKEN)
     */
    public boolean isFixed() {
        return STATUS_FIXED.equals(this.status);
    }

    // ==========================================
    // Getter & Setter Methods
    // ==========================================

    public int getRobotId() {
        return robotId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getRequiredCorrectCount() {
        return requiredCorrectCount;
    }

    public void setRequiredCorrectCount(int requiredCorrectCount) {
        this.requiredCorrectCount = requiredCorrectCount;
    }

    public int getCurrentCorrectCount() {
        return currentCorrectCount;
    }

    public void setCurrentCorrectCount(int currentCorrectCount) {
        this.currentCorrectCount = currentCorrectCount;
    }
}
