# แผนการปรับปรุง Code ส่วนกลางให้สมบูรณ์ (Data Layer Completion)

เพื่อให้ส่วนของ `feature/quiz-logic`, `feature/menu-ui`, และ `feature/battle-ui` สามารถทำงานร่วมกันได้อย่างไม่มีปัญหา เราจำเป็นต้องจัดการเรื่องมาตรฐานของข้อมูล (Constants) และเพิ่มตัวจัดการหุ่นยนต์ (RobotRepository) ให้สมบูรณ์ครับ

## User Review Required

> [!IMPORTANT]
> - ผมจะสร้างไฟล์ `GameConstants.java` เพื่อเก็บชื่อภาษาและระดับความยากเป็นค่าคงที่ เพื่อป้องกันความผิดพลาดจากการพิมพ์ String ผิด (Typos) ในหน้าอื่นๆ
> - ผมจะสร้างไฟล์ `RobotRepository.java` เพื่อให้หน้า Menu และ Battle สามารถดึงข้อมูลหุ่นยนต์ที่ผู้เล่นต้องซ่อมได้ง่ายขึ้น

## Proposed Changes

### [Core Data Layer]

#### [NEW] [GameConstants.java](file:///D:/ProjectGame/app/src/main/java/com/example/ryo_q/GameConstants.java)
- เก็บค่าคงที่ เช่น `LANG_JAVA`, `LANG_PYTHON`, `LANG_CPP`
- เก็บค่าคงที่ระดับความยาก `DIFF_EASY`, `DIFF_NORMAL`, `DIFF_HARD`

#### [NEW] [RobotRepository.java](file:///D:/ProjectGame/app/src/main/java/com/example/ryo_q/RobotRepository.java)
- เก็บรายชื่อและข้อมูลหุ่นยนต์ทั้งหมดในเกม
- มีฟังก์ชันดึงหุ่นยนต์ตาม ID หรือตามด่านที่ผู้เล่นเลือก

#### [MODIFY] [QuestionRepository.java](file:///D:/ProjectGame/app/src/main/java/com/example/ryo_q/QuestionRepository.java)
- ปรับปรุงให้ใช้ค่าคงที่จาก `GameConstants` แทนการใช้ String ตรงๆ

#### [MODIFY] [ScoreManager.java](file:///D:/ProjectGame/app/src/main/java/com/example/ryo_q/ScoreManager.java)
- ปรับปรุงให้ใช้ค่าคงที่จาก `GameConstants` เพื่อความแม่นยำในการเก็บคะแนน

#### [MODIFY] [Robot.java](file:///D:/ProjectGame/app/src/main/java/com/example/ryo_q/Robot.java)
- ตรวจสอบความสมบูรณ์ของ Constructor และ Helper Methods ให้พร้อมสำหรับหน้า UI

## Verification Plan

### Manual Verification
- ทดสอบเรียกใช้ `GameConstants` ใน `MainActivity`
- ทดสอบดึงหุ่นยนต์จาก `RobotRepository` มาแสดงผลผ่าน Log
- ตรวจสอบว่าโจทย์ใน `QuestionRepository` ยังทำงานได้ถูกต้องหลังจากเปลี่ยนเป็น Constants
