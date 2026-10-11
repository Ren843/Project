package com.example.ryo_q;

import java.io.Serializable;

/**
 * คลาส Robot ตัวละครหุ่นยนต์ในเกมที่ผู้เล่นต้องซ่อมแซม
 * รองรับการเก็บรูปภาพ 2 เวอร์ชัน (รูปตอนชำรุด และรูปตอนซ่อมเสร็จ)
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
    private int brokenImageResId;        // ไอดีทรัพยากรรูปภาพหุ่นยนต์ตอนพัง/ชำรุด
    private int fixedImageResId;         // ไอดีทรัพยากรรูปภาพหุ่นยนต์ตอนซ่อมเสร็จ
    private int requiredCorrectCount;    // จำนวนข้อที่ต้องตอบถูกทั้งหมดในการซ่อมแซม
    private int currentCorrectCount;     // จำนวนข้อที่ตอบถูกสะสมในปัจจุบัน

    /**
     * คอนสตรักเตอร์สร้างหุ่นยนต์แบบ 1 รูปภาพ (รองรับโค้ดเก่า)
     */
    public Robot(int robotId, String name, int imageResId) {
        this(robotId, name, imageResId, imageResId);
    }

    /**
     * คอนสตรักเตอร์สร้างหุ่นยนต์พร้อมรูปภาพ 2 เวอร์ชัน (พัง และ ซ่อมเสร็จ)
     */
    public Robot(int robotId, String name, int brokenImageResId, int fixedImageResId) {
        this(robotId, name, "หุ่นยนต์ที่ต้องการซ่อมแซมวงจร", brokenImageResId, fixedImageResId, 5);
    }

    /**
     * คอนสตรักเตอร์รองรับรูปแบบ 1 รูปภาพพร้อมรายละเอียด
     */
    public Robot(int robotId, String name, String description, int imageResId, int requiredCorrectCount) {
        this(robotId, name, description, imageResId, imageResId, requiredCorrectCount);
    }

    /**
     * คอนสตรักเตอร์แบบสมบูรณ์รองรับรูปภาพ 2 เวอร์ชัน (พัง และ ซ่อมเสร็จ)
     */
    public Robot(int robotId, String name, String description, int brokenImageResId, int fixedImageResId, int requiredCorrectCount) {
        this.robotId = robotId;
        this.name = name;
        this.description = description;
        this.brokenImageResId = brokenImageResId;
        this.fixedImageResId = fixedImageResId;
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

    /**
     * ดึงไอดีรูปภาพประจำสถานะปัจจุบัน (คืนค่ารูปตอนซ่อมเสร็จหากซ่อมแล้ว คืนค่ารูปพังหากยังชำรุด)
     * @return ทรัพยากรรูปภาพ R.drawable.xxx
     */
    public int getImageResId() {
        return isFixed() ? fixedImageResId : brokenImageResId;
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

    public int getBrokenImageResId() {
        return brokenImageResId;
    }

    public void setBrokenImageResId(int brokenImageResId) {
        this.brokenImageResId = brokenImageResId;
    }

    public int getFixedImageResId() {
        return fixedImageResId;
    }

    public void setFixedImageResId(int fixedImageResId) {
        this.fixedImageResId = fixedImageResId;
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
