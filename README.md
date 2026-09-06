# Ryo-Q
2D Pixel Game Android Studio 
# Ryo-Q — QuestCode

เกมตอบคำถามโค้ดดิ้ง (Python / C / Java) แนว 2D pixel บน Android Studio (Java)


## โครงสร้าง branch

- `main` — เก็บเวอร์ชันที่รันได้จริงสมบูรณ์เท่านั้น ห้าม push ตรงเข้ามาที่นี่
- `dev` — branch รวมงานระหว่างทำ ทุกคน merge งานของตัวเองเข้ามาที่นี่ก่อน
- `feature/ชื่องาน` — branch ส่วนตัวของแต่ละคน เช่น `feature/database`, `feature/quiz-logic`, `feature/menu-ui`, `feature/battle-ui`

การไหลของงาน: `feature/*` → `dev` → `main` (merge เข้า `main` ตอนโปรเจกต์เสร็จหรือถึง milestone สำคัญเท่านั้น)

---

## ตั้งค่าครั้งแรก (ทำครั้งเดียวตอนเริ่ม)

### 1. ติดตั้ง Git และเช็คว่ามีอยู่แล้ว

เปิด Terminal พิมพ์:
```
git --version
```
ถ้าไม่มีให้โหลดจาก https://git-scm.com/downloads

### 2. ตั้งค่าชื่อและอีเมลของตัวเอง (ใช้ครั้งเดียว)

```
git config --global user.name "ชื่อ"
git config --global user.email "อีเมลที่ผูกกับ GitHub"
```

### 3. เตรียม Personal Access Token

GitHub ไม่รับรหัสผ่านบัญชีตรงๆ สำหรับคำสั่ง git แล้ว ต้องสร้าง token แทน:

1. ไปที่ GitHub → รูปโปรไฟล์ → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Generate new token (classic) → ตั้งชื่อ → ติ๊กสิทธิ์ `repo` → Generate
3. คัดลอก token เก็บไว้ที่ปลอดภัย (จะเห็นแค่ครั้งเดียว)

จำ token ให้อัตโนมัติ (ทำครั้งเดียว จะได้ไม่ต้องพิมพ์ทุกครั้ง):
```
git config --global credential.helper store
```

### 4. Clone โปรเจกต์มาไว้ในเครื่อง

```
git clone https://github.com/Ren843/Project.git
cd Project
```

ตอนถูกถาม username/password ให้ใส่ username GitHub และวาง token แทนรหัสผ่าน

### 5. สลับไปที่ branch dev

```
git checkout dev
git pull origin dev
```

### 6. สร้าง branch ของตัวเอง

```
git checkout -b feature/ชื่องานของตัวเอง
```

ตัวอย่างชื่อ branch ของแต่ละคน:
- `feature/database`
- `feature/quiz-logic`
- `feature/menu-ui`
- `feature/battle-ui`

ตอนนี้เปิดโปรเจกต์นี้ด้วย Android Studio (เลือก Open → เลือกโฟลเดอร์ `Ryo-Q`) แล้วเริ่มเขียนโค้ดได้เลย

---

## ทำงานประจำวัน (ทำทุกครั้งที่เริ่ม/จบการทำงาน)

### ก่อนเริ่มทำงานแต่ละวัน — ดึงงานล่าสุดของ dev เข้ามาก่อน

```
git checkout dev
git pull origin dev
git checkout feature/ชื่องานของตัวเอง
git merge dev
```

ทำแบบนี้เพื่อให้ branch ของตัวเองอัปเดตตามงานของเพื่อนที่ merge เข้า `dev` ไปแล้ว ลดโอกาส conflict ก้อนใหญ่ตอนหลัง

### ระหว่างทำงาน — commit เป็นระยะ

```
git add .
git commit -m "อธิบายสั้นๆ ว่าทำอะไร เช่น: เพิ่ม checkAnswer ใน QuizManager"
```

commit บ่อยๆ ดีกว่า commit ก้อนใหญ่ครั้งเดียว จะได้ย้อนกลับได้ง่ายถ้าพลาด

### จบการทำงานแต่ละครั้ง — push ขึ้น GitHub

```
git push -u origin feature/ชื่องานของตัวเอง
```

(ครั้งแรกต้องมี `-u` ครั้งต่อไปพิมพ์แค่ `git push` ก็พอ)

### เมื่องานส่วนของตัวเองเสร็จสมบูรณ์ — merge เข้า dev

1. ไปที่หน้า repo บน GitHub
2. กด **Compare & pull request** (จะขึ้นให้อัตโนมัติหลัง push)
3. ตั้ง base เป็น `dev`, compare เป็น `feature/ชื่องานของตัวเอง`
4. เขียนอธิบายว่าทำอะไรไปบ้าง แล้วกด Create pull request
5. รอเพื่อนอีกอย่างน้อย 1 คนกด Approve ก่อน merge

---

## ปัญหาที่เจอบ่อย

**push แล้วขึ้น error "divergent branches"**
```
git pull origin ชื่อ-branch --no-rebase
```
แล้ว push ใหม่อีกครั้ง

**pull แล้วเจอ merge conflict**
เปิดไฟล์ที่ขึ้น conflict หาเครื่องหมาย `<<<<<<<` `=======` `>>>>>>>` แก้เนื้อหาให้เหลือเวอร์ชันที่ต้องการจริงๆ ลบเครื่องหมายทั้งหมดออก แล้ว:
```
git add ชื่อไฟล์ที่แก้
git commit -m "Merge conflict resolved"
git push
```

**push แล้วขึ้นถามรหัสผ่าน**
ใส่ Personal Access Token แทนรหัสผ่านบัญชี (ดูขั้นตอนที่ 3 ด้านบน)
