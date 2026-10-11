package com.example.ryo_q;

import java.util.ArrayList;
import java.util.List;

// คลาส QuestionRepository ทำหน้าที่เป็นคลังเก็บข้อมูลคำถามทั้งหมดในเกม
// โดยจัดการแยกโจทย์ตามภาษาโปรแกรมและระดับความยาก
public class QuestionRepository {

    // ดึงรายการคำถามที่กรองตามภาษาและระดับความยาก
    // @param lang ภาษาโปรแกรม (แนะนำให้ใช้จาก GameConstants เช่น GameConstants.LANG_JAVA)
    // @param diff ระดับความยาก (แนะนำให้ใช้จาก GameConstants เช่น GameConstants.DIFF_EASY)
    // @return List ของ Object Question ที่ตรงตามเงื่อนไข
    public static List<Question> getQuestions(String lang, String diff) {
        List<Question> filteredList = new ArrayList<>();
        for (Question q : getAllQuestions()) {
            // ตรวจสอบความถูกต้องโดยใช้ค่าคงที่เพื่อความปลอดภัย
            if (q.getLanguage().equalsIgnoreCase(lang) && q.getDifficulty().equalsIgnoreCase(diff)) {
                filteredList.add(q);
            }
        }
        return filteredList;
    }

    // คลังรวบรวมโจทย์ทั้งหมดในระบบ (เป็นฐานข้อมูลแบบ Hardcode)
    // @return List ของคำถามทั้งหมดที่มีในเกม
    private static List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();

        // บั๊ก: ลืมใส่ ; ท้ายคำสั่ง | เฉลย: int hp = 100;
        list.add(new Question("1. ตรวจสอบการลืมใส่เครื่องหมาย ; ปิดท้ายคำสั่ง",
                "int hp = 100\nSystem.out.println(hp);",
                new String[]{"int hp = 100;", "int hp == 100;", "int hp : 100;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ใช้ string ตัวพิมพ์เล็ก | เฉลย: ต้องเป็น String (S ตัวใหญ่)
        list.add(new Question("2. ตรวจสอบการประกาศตัวแปรชนิดข้อความ (String ต้องขึ้นต้นด้วย S ตัวใหญ่)",
                "string name = \"Bot\";",
                new String[]{"str name = \"Bot\";", "String name = \"Bot\";", "Text name = \"Bot\";"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: a[3] เกินขนาด array ที่มี 3 ช่อง (index 0-2) | เฉลย: a[2] = 10;
        list.add(new Question("3. ตรวจสอบการเข้าถึง Array เกินขนาด (ArrayIndexOutOfBoundsException)",
                "int[] a = new int[3];\na[3] = 10;",
                new String[]{"a[-1] = 10;", "a[4] = 10;", "a[2] = 10;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ใช้ = (กำหนดค่า) แทน == (เปรียบเทียบ) | เฉลย: if (x == 5)
        list.add(new Question("4. ตรวจสอบการใช้ = (กำหนดค่า) แทน == (เปรียบเทียบ) ในเงื่อนไข if",
                "int x = 5;\nif (x = 5) {\n go();\n}",
                new String[]{"if (x == 5) {", "if (x === 5) {", "if (x := 5) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: method ไม่มี return type | เฉลย: public int add(int a, int b)
        list.add(new Question("5. ตรวจสอบการลืมระบุชนิดข้อมูลที่ method คืนค่า",
                "public add(int a, int b) {\n return a + b;\n}",
                new String[]{"public add int(int a, int b) {", "public int add(int a, int b) {", "public void add(int a, int b) {"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ประกาศ void แต่มี return ค่า | เฉลย: public int getHp()
        list.add(new Question("6. ตรวจสอบ method ที่ return ค่าแต่ประกาศเป็น void",
                "public void getHp() {\n return hp;\n}",
                new String[]{"public void getHp(int hp) {", "public static getHp() {", "public int getHp() {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: char ใช้ "" (เป็น String) | เฉลย: ต้องใช้ 'A' (single quote)
        list.add(new Question("7. ตรวจสอบการเก็บตัวอักษรเดี่ยวชนิด char (ต้องใช้เครื่องหมาย ' ')",
                "char c = \"A\";",
                new String[]{"char c = 'A';", "char c = 'AB';", "Char c = \"A\";"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ใช้ bool และ True ซึ่งไม่มีใน Java | เฉลย: boolean ok = true;
        list.add(new Question("8. ตรวจสอบการประกาศตัวแปรค่าจริง/เท็จ (boolean และค่า true ตัวพิมพ์เล็ก)",
                "bool ok = True;",
                new String[]{"bool ok = true;", "boolean ok = true;", "Boolean ok = 'true';"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: main รับ String แทน String[] | เฉลย: public static void main(String[] args)
        list.add(new Question("9. ตรวจสอบรูปแบบ method main ที่ถูกต้องของ Java",
                "public static void main(String args) {\n start();\n}",
                new String[]{"public void main(String[] args) {", "static void main(string[] args) {", "public static void main(String[] args) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ; หลัง for ทำให้ลูปว่างเปล่า | เฉลย: ลบ ; ออกและใช้ { หลัง for
        list.add(new Question("10. ตรวจสอบเครื่องหมาย ; ที่ทำให้ลูป for ไม่ทำงานตามที่ต้องการ",
                "for (int i = 0; i < 5; i++);\n{\n System.out.println(i);\n}",
                new String[]{"for (int i = 0; i < 5; i++) {", "for (int i = 0, i < 5, i++) {", "for (int i < 5; i++) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: int / int ได้ผลเป็นจำนวนเต็ม | เฉลย: 5 / 2.0
        list.add(new Question("11. ตรวจสอบการหารจำนวนเต็มที่ทำให้ทศนิยมหายไป",
                "double avg = 5 / 2; // ต้องการ 2.5",
                new String[]{"double avg = 5 // 2;", "double avg = 5 / 2.0;", "int avg = 5 / 2.0;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: =+ คือกำหนดค่า +1 ไม่ใช่การบวกเพิ่ม | เฉลย: count += 1;
        list.add(new Question("12. ตรวจสอบการเพิ่มค่าตัวแปรทีละ 1 ให้ถูกรูปแบบ",
                "int count = 0;\ncount =+ 1; // ต้องการเพิ่มทีละ 1",
                new String[]{"count + = 1;", "count =++ 1;", "count += 1;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: String.length เป็น method | เฉลย: s.length()
        list.add(new Question("13. ตรวจสอบการหาความยาวของ String (ต้องเรียกเป็น method)",
                "String s = \"Robot\";\nint n = s.length;",
                new String[]{"int n = s.length();", "int n = s.size();", "int n = s.len();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: array.length เป็น field ไม่มีวงเล็บ | เฉลย: a.length
        list.add(new Question("14. ตรวจสอบการหาความยาวของ Array (เป็น field ไม่ใช่ method)",
                "int[] a = {1, 2, 3};\nint n = a.length();",
                new String[]{"int n = a.size;", "int n = a.length;", "int n = a.count;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: System และ println ตัวพิมพ์ผิด | เฉลย: System.out.println("Hi");
        list.add(new Question("15. ตรวจสอบตัวพิมพ์เล็ก-ใหญ่ของคำสั่งพิมพ์ข้อความ",
                "system.out.println(\"Hi\");",
                new String[]{"System.Out.Println(\"Hi\");", "system.out.printLn(\"Hi\");", "System.out.println(\"Hi\");"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ลืมใส่ " ครอบข้อความ | เฉลย: System.out.println("Hello");
        list.add(new Question("16. ตรวจสอบการลืมใส่เครื่องหมาย \" ครอบข้อความ",
                "System.out.println(Hello);",
                new String[]{"System.out.println(\"Hello\");", "System.out.println('Hello');", "System.out.println(\"Hello);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ไม่ได้ประกาศชนิดตัวแปร | เฉลย: int x = 10;
        list.add(new Question("17. ตรวจสอบการใช้ตัวแปรที่ยังไม่ได้ประกาศชนิดข้อมูล",
                "x = 10;\nSystem.out.println(x);",
                new String[]{"var x;", "int x = 10;", "x := 10;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ลืม break ทำให้ทำงานต่อเนื่อง (fall-through) | เฉลย: case 1: a(); break;
        list.add(new Question("18. ตรวจสอบ switch ที่ลืม break ทำให้ทำงานต่อเนื่อง (fall-through)",
                "switch (n) {\n case 1: a();\n case 2: b();\n}",
                new String[]{"case 1: a(); stop;", "case 1: a(); exit;", "case 1: a(); break;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ไม่มี i++ ลูปไม่จบ | เฉลย: เพิ่ม i++; ในลูป
        list.add(new Question("19. ตรวจสอบลูป while ที่ไม่มีวันจบเพราะไม่เพิ่มค่าตัวนับ",
                "int i = 0;\nwhile (i < 3) {\n System.out.println(i);\n}",
                new String[]{"while (i < 3) {\n System.out.println(i);\n i++;\n}", "while (i < 3);{\n System.out.println(i);\n}", "while (i > 3) {\n System.out.println(i);\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: and ไม่ใช่ตัวดำเนินการของ Java | เฉลย: &&
        list.add(new Question("20. ตรวจสอบตัวดำเนินการตรรกะ \"และ\" ที่ถูกต้องใน Java",
                "if (a > 0 and b > 0) {\n go();\n}",
                new String[]{"if (a > 0 & & b > 0) {", "if (a > 0 && b > 0) {", "if (a > 0 AND b > 0) {"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: not ไม่ใช่ตัวดำเนินการของ Java | เฉลย: !done
        list.add(new Question("21. ตรวจสอบตัวดำเนินการตรรกะ \"ไม่\" ที่ถูกต้องใน Java",
                "if (not done) {\n wait();\n}",
                new String[]{"if (~done) {", "if (done != ) {", "if (!done) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: final เปลี่ยนค่าซ้ำไม่ได้ | เฉลย: ไม่ใช้ final ถ้าต้องการเปลี่ยนค่า
        list.add(new Question("22. ตรวจสอบการเปลี่ยนค่าตัวแปรที่ประกาศเป็น final",
                "final int MAX = 5;\nMAX = 6; // compile error",
                new String[]{"int max = 5;\nmax = 6;", "final int MAX = 5;\nMAX += 1;", "const int MAX = 5;\nMAX = 6;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: Constructor ต้องชื่อตรงกับคลาสและไม่มี return type | เฉลย: Robot() {}
        list.add(new Question("23. ตรวจสอบชื่อ Constructor (ต้องตรงกับชื่อคลาสและไม่มี return type)",
                "class Robot {\n Robo() {}\n}",
                new String[]{"void Robot() {}", "Robot() {}", "robot() {}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ใช้ - กับ String ไม่ได้ | เฉลย: "HP: " + hp
        list.add(new Question("24. ตรวจสอบการต่อ String กับตัวเลข",
                "String s = \"HP: \" - hp;",
                new String[]{"String s = \"HP: \" * hp;", "String s = \"HP: \" & hp;", "String s = \"HP: \" + hp;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: # ไม่ใช่ comment ของ Java | เฉลย: // ...
        list.add(new Question("25. ตรวจสอบรูปแบบ comment บรรทัดเดียวของ Java",
                "# ถือเป็น comment\nint a = 1;",
                new String[]{"// ถือเป็น comment", "-- ถือเป็น comment", "<!-- ถือเป็น comment -->"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: กำหนด String ให้ int ตรง ๆ ไม่ได้ | เฉลย: Integer.parseInt("42")
        list.add(new Question("26. ตรวจสอบการแปลง String เป็น int",
                "int n = \"42\";",
                new String[]{"int n = (int) \"42\";", "int n = Integer.parseInt(\"42\");", "int n = String.toInt(\"42\");"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: elseif ไม่ใช่ keyword | เฉลย: else if
        list.add(new Question("27. ตรวจสอบรูปแบบเงื่อนไข else if",
                "if (a) {\n x();\n} elseif (b) {\n y();\n}",
                new String[]{"elif (b) {", "elsif (b) {", "else if (b) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: new int[] ไม่ระบุขนาด | เฉลย: int[] a = new int[5];
        list.add(new Question("28. ตรวจสอบการประกาศ Array พร้อมกำหนดขนาด",
                "int a[] = new int[];",
                new String[]{"int[] a = new int[5];", "int[] a = int[5];", "int a = new int[5];"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: <> ไม่ใช่ตัวดำเนินการใน Java | เฉลย: !=
        list.add(new Question("29. ตรวจสอบตัวดำเนินการ \"ไม่เท่ากับ\" ใน Java",
                "if (x <> 3) {\n go();\n}",
                new String[]{"if (x =! 3) {", "if (x != 3) {", "if (x ! 3) {"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ลืม import Scanner | เฉลย: import java.util.Scanner;
        list.add(new Question("30. ตรวจสอบการนำเข้าคลาส Scanner",
                "Scanner sc = new Scanner(System.in); // cannot find symbol",
                new String[]{"include java.util.Scanner;", "import java.io.Scan;", "import java.util.Scanner;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: เรียก method ไม่มี () | เฉลย: robot.repair();
        list.add(new Question("31. ตรวจสอบการเรียก method ที่ต้องมีวงเล็บ ()",
                "Robot robot = new Robot(1, \"Ryo\", 0);\nrobot.repair;",
                new String[]{"robot.repair();", "robot.repair[];", "robot->repair();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ค่าทศนิยมเป็น double ต้องมี f สำหรับ float | เฉลย: 3.14f
        list.add(new Question("32. ตรวจสอบการกำหนดค่าทศนิยมให้ตัวแปร float",
                "float f = 3.14;",
                new String[]{"float f = 3.14d;", "float f = 3.14f;", "float f = (3.14);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: เลขเกินช่วง int ต้องมี L | เฉลย: 3000000000L
        list.add(new Question("33. ตรวจสอบตัวเลขจำนวนมากที่เกินช่วงของ int",
                "long big = 3000000000;",
                new String[]{"int big = 3000000000;", "long big = \"3000000000\";", "long big = 3000000000L;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: / คือหาร ไม่ใช่หารเอาเศษ | เฉลย: n % 2 == 0
        list.add(new Question("34. ตรวจสอบการเช็คเลขคู่ (ต้องใช้ตัวดำเนินการ modulo)",
                "if (n / 2 == 0) {\n // ต้องการเช็คว่า n เป็นเลขคู่\n}",
                new String[]{"if (n % 2 == 0) {", "if (n // 2 == 0) {", "if (n * 2 == 0) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: เรียก sq() โดยไม่ส่งค่า parameter | เฉลย: Util.sq(4)
        list.add(new Question("35. ตรวจสอบการเรียก static method พร้อมส่งค่า parameter",
                "class Util { static int sq(int x) { return x * x; } }\nint r = Util.sq();",
                new String[]{"int r = Util.sq[4];", "int r = Util.sq(4);", "int r = sq.Util(4);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ternary ต้องใช้ : คั่น ไม่ใช่ ; | เฉลย: ? "pos" : "neg"
        list.add(new Question("36. ตรวจสอบรูปแบบ ternary operator (? :)",
                "String s = (x > 0) ? \"pos\" ; \"neg\";",
                new String[]{"String s = (x > 0) ? \"pos\", \"neg\";", "String s = (x > 0) ?: \"pos\" \"neg\";", "String s = (x > 0) ? \"pos\" : \"neg\";"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: printline ไม่มีใน Java | เฉลย: println
        list.add(new Question("37. ตรวจสอบชื่อ method พิมพ์ข้อความขึ้นบรรทัดใหม่",
                "System.out.printline(\"Hi\");",
                new String[]{"System.out.println(\"Hi\");", "System.out.printLine(\"Hi\");", "System.out.print.ln(\"Hi\");"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: วงเล็บปีกกา } ไม่ครบ | เฉลย: ปิดด้วย }
        list.add(new Question("38. ตรวจสอบการปิดวงเล็บปีกกา { } ให้ครบ",
                "if (hp > 0) {\n alive();\n",
                new String[]{"if (hp > 0) \n alive();\n}", "if (hp > 0) {\n alive();\n}", "if (hp > 0) {\n alive();\n)"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: Math.sqrt คืน double ไม่ใช่ int | เฉลย: double r = Math.sqrt(16);
        list.add(new Question("39. ตรวจสอบชนิดข้อมูลที่ Math.sqrt() คืนค่า",
                "int r = Math.sqrt(16);",
                new String[]{"int r = sqrt(16);", "int r = Math.sqr(16);", "double r = Math.sqrt(16);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ชื่อตัวแปรขึ้นต้นด้วยตัวเลขไม่ได้ | เฉลย: int second = 5;
        list.add(new Question("40. ตรวจสอบกฎการตั้งชื่อตัวแปร (ห้ามขึ้นต้นด้วยตัวเลข)",
                "int 2nd = 5;",
                new String[]{"int second = 5;", "int 2nd_ = 5;", "int 'second' = 5;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // ---------- ข้อ 41-50 ----------

        // บั๊ก: ประกาศ int a ซ้ำสองครั้ง | เฉลย: ประกาศครั้งเดียวแล้วกำหนดค่าใหม่ a = 2;
        list.add(new Question("41. ตรวจสอบการประกาศตัวแปรชื่อซ้ำในขอบเขตเดียวกัน",
                "int a = 1;\nint a = 2;",
                new String[]{"int a = 1;\nint a := 2;", "int a = 1;\na = 2;", "int a = 1;\nvar int a = 2;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: Java ไม่มี keyword then | เฉลย: if (x > 5) {
        list.add(new Question("42. ตรวจสอบรูปแบบเงื่อนไข if ที่ถูกต้อง",
                "if (x > 5) then {\n go();\n}",
                new String[]{"if x > 5 {", "if (x > 5): {", "if (x > 5) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: เทียบ char กับ String ("a") | เฉลย: ใช้ 'a' (single quote)
        list.add(new Question("43. ตรวจสอบการเปรียบเทียบ char กับ String",
                "char c = 'a';\nif (c == \"a\") {\n go();\n}",
                new String[]{"if (c == 'a') {", "if (c.equals('a')) {", "if (c == 'aa') {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: do-while ต้องมี ; ท้ายสุด และเงื่อนไขอยู่ในวงเล็บ | เฉลย: } while (i < 3);
        list.add(new Question("44. ตรวจสอบรูปแบบลูป do-while",
                "do {\n i++;\n} while (i < 3)",
                new String[]{"} while i < 3;", "} while (i < 3);", "} until (i < 3);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ใช้ [ ] กำหนดค่า array ซึ่งไม่ใช่ syntax ของ Java | เฉลย: int[] a = {1, 2, 3};
        list.add(new Question("45. ตรวจสอบการกำหนดค่าเริ่มต้นให้ Array",
                "int[] a = [1, 2, 3];",
                new String[]{"int[] a = (1, 2, 3);", "int[] a = <1, 2, 3>;", "int[] a = {1, 2, 3};"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: ^ คือ XOR ไม่ใช่ยกกำลัง | เฉลย: Math.pow(2, 3)
        list.add(new Question("46. ตรวจสอบการคำนวณยกกำลัง",
                "int r = 2 ^ 3; // ต้องการ 2 ยกกำลัง 3",
                new String[]{"double r = Math.pow(2, 3);", "int r = 2 ** 3;", "int r = pow(2, 3);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: String ใช้ [ ] เข้าถึงตัวอักษรไม่ได้ | เฉลย: s.charAt(0)
        list.add(new Question("47. ตรวจสอบการอ่านตัวอักษรตำแหน่งที่ต้องการจาก String",
                "String s = \"Bot\";\nchar c = s[0];",
                new String[]{"char c = s.get(0);", "char c = s.charAt(0);", "char c = s(0);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: นับถอยหลังแต่ใช้ i++ ลูปไม่จบ | เฉลย: i--
        list.add(new Question("48. ตรวจสอบลูปนับถอยหลัง",
                "for (int i = 10; i > 0; i++) {\n System.out.println(i);\n}",
                new String[]{"for (int i = 10; i > 0; i+1) {", "for (int i = 10; i < 0; i--) {", "for (int i = 10; i > 0; i--) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: nextLine() คืน String กำหนดให้ int ไม่ได้ | เฉลย: sc.nextInt()
        list.add(new Question("49. ตรวจสอบการอ่านตัวเลขจาก Scanner",
                "Scanner sc = new Scanner(System.in);\nint n = sc.nextLine(); // nextLine คืน String",
                new String[]{"int n = sc.nextInt();", "int n = sc.readInt();", "int n = sc.next(int);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // บั๊ก: equals แยกตัวพิมพ์เล็ก-ใหญ่ | เฉลย: equalsIgnoreCase
        list.add(new Question("50. ตรวจสอบการเปรียบเทียบข้อความโดยไม่สนตัวพิมพ์เล็ก-ใหญ่",
                "if (s.equals(\"yes\")) {\n go();\n} // ต้องการให้ \"YES\" ผ่านด้วย",
                new String[]{"if (s.equal(\"yes\")) {", "if (s.equalsIgnoreCase(\"yes\")) {", "if (s == \"yes\") {"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        addNormal(list);
        addHard(list);
        return list;
    }

    // =====================================================================
    // ระดับกลาง (DIFF_NORMAL) 50 ข้อ
    // =====================================================================
    public static void addNormal(List<Question> list) {

        // บั๊ก: เรียก method บน null (NullPointerException) | เฉลย: เช็ค s != null ก่อนด้วย ternary
        list.add(new Question("1. ตรวจสอบการป้องกัน NullPointerException ก่อนเรียก method",
                "String s = null;\nint n = s.length();",
                new String[]{"int n = (s != null) ? s.length() : 0;", "int n = s?.length();", "int n = s.length() ?: 0;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: ชื่อ Run ไม่ตรงกับ run() ใน A ทำให้ @Override ใช้ไม่ได้ | เฉลย: void run() {}
        list.add(new Question("2. ตรวจสอบการ Override method ให้ชื่อและ parameter ตรงกับคลาสแม่",
                "class A { void run() {} }\nclass B extends A {\n @Override\n void Run() {}\n}",
                new String[]{"void run(int x) {}", "void run() {}", "void RUN() {}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: method ของ interface ต้องเป็น public | เฉลย: public void fly() {}
        list.add(new Question("3. ตรวจสอบการ implements interface (method ต้องเป็น public)",
                "interface Fly { void fly(); }\nclass Bird implements Fly {\n void fly() {}\n}",
                new String[]{"void fly() {}", "public int fly() {}", "public void fly() {}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: catch Exception มาก่อน IOException ทำให้ catch หลังไม่มีวันถึง | เฉลย: จับ IOException ก่อน Exception
        list.add(new Question("4. ตรวจสอบลำดับ catch ของ Exception (ตัวเฉพาะต้องมาก่อนตัวทั่วไป)",
                "try {\n risky();\n} catch (Exception e) {\n} catch (IOException e) {\n}",
                new String[]{"catch (IOException e) {\n} catch (Exception e) {", "catch (Exception | IOException e) {", "catch (Throwable e) {\n} catch (Exception e) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Generics ใช้ primitive (int) ไม่ได้ | เฉลย: ArrayList<Integer>
        list.add(new Question("5. ตรวจสอบการใช้ Generics กับชนิดข้อมูลพื้นฐาน (ต้องใช้ Wrapper class)",
                "ArrayList<int> list = new ArrayList<>();",
                new String[]{"ArrayList<Int> list = new ArrayList<>();", "ArrayList<Integer> list = new ArrayList<>();", "ArrayList list<int> = new ArrayList<>();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: map.get() คืน null แล้ว unbox เป็น int จะพัง | เฉลย: map.getOrDefault("x", 0)
        list.add(new Question("6. ตรวจสอบการอ่านค่าจาก HashMap เมื่อ key อาจไม่มีอยู่ (null unboxing)",
                "int v = map.get(\"x\"); // key \"x\" อาจไม่มี",
                new String[]{"int v = map.get(\"x\") || 0;", "int v = map.find(\"x\", 0);", "int v = map.getOrDefault(\"x\", 0);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: super(...) ต้องเป็นบรรทัดแรกใน Constructor | เฉลย: ย้าย super(5) ขึ้นก่อน println
        list.add(new Question("7. ตรวจสอบตำแหน่งการเรียก super(...) ใน Constructor (ต้องเป็นบรรทัดแรก)",
                "class B extends A {\n B() {\n  System.out.println(1);\n  super(5);\n }\n}",
                new String[]{"B() {\n super(5);\n System.out.println(1);\n}", "B() {\n this.super(5);\n}", "B() {\n A(5);\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: static method เข้าถึง field แบบ instance ไม่ได้ | เฉลย: ทำให้ score เป็น static
        list.add(new Question("8. ตรวจสอบ static method ที่เข้าถึงตัวแปร instance",
                "class G {\n int score;\n static void add() { score++; }\n}",
                new String[]{"int static score;", "static int score;", "final int score;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: String ไม่มี method append() | เฉลย: ใช้ StringBuilder
        list.add(new Question("9. ตรวจสอบการต่อข้อความด้วย append() (String ไม่มี method นี้)",
                "String s = \"a\";\ns.append(\"b\");",
                new String[]{"String s = new String(\"a\");\ns.append(\"b\");", "String s = \"a\";\ns = s.add(\"b\");", "StringBuilder s = new StringBuilder(\"a\");\ns.append(\"b\");"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Integer เทียบด้วย == เป็นการเทียบ reference | เฉลย: a.equals(b)
        list.add(new Question("10. ตรวจสอบการเปรียบเทียบ Integer object ด้วย == (ค่าเกิน 127 จะไม่เท่ากัน)",
                "Integer a = 1000, b = 1000;\nif (a == b) {\n same();\n}",
                new String[]{"if (a.equals(b)) {", "if (a === b) {", "if (a.compare(b)) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: new abstract class ตรง ๆ ไม่ได้ | เฉลย: สร้างจากคลาสลูก new Circle()
        list.add(new Question("11. ตรวจสอบการสร้าง object จาก abstract class",
                "abstract class Shape {}\nclass Circle extends Shape {}\nShape s = new Shape();",
                new String[]{"Shape s = Shape.create();", "Shape s = new Circle();", "Shape s = (Shape) new Object();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: checked exception ต้องประกาศ throws | เฉลย: void read() throws IOException
        list.add(new Question("12. ตรวจสอบการประกาศ checked exception ที่ method ต้อง throws",
                "void read() {\n throw new IOException(\"err\");\n}",
                new String[]{"void read() throw IOException {", "void read() catch IOException {", "void read() throws IOException {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: b = a แค่ชี้ array เดียวกัน ไม่ได้คัดลอก | เฉลย: Arrays.copyOf(a, a.length)
        list.add(new Question("13. ตรวจสอบการคัดลอก Array ให้เป็นสำเนาแยกอิสระ",
                "int[] a = {1, 2, 3};\nint[] b = a; // ต้องการสำเนาที่แยกจากกัน",
                new String[]{"int[] b = Arrays.copyOf(a, a.length);", "int[] b = new a;", "int[] b = copy(a);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Collections.sort ต้องการ Comparable | เฉลย: implements Comparable<Robot> + compareTo
        list.add(new Question("14. ตรวจสอบการเรียง List ของ object ที่กำหนดเอง (ต้อง implements Comparable)",
                "Collections.sort(robots); // Robot ไม่ได้ implements Comparable",
                new String[]{"class Robot extends Comparable {}", "class Robot implements Comparable<Robot> {\n public int compareTo(Robot o) { return id - o.id; }\n}", "class Robot implements Sortable {}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: id = id กำหนดค่าให้ parameter ตัวเอง (shadowing) | เฉลย: this.id = id;
        list.add(new Question("15. ตรวจสอบการตั้งชื่อ parameter ซ้ำกับ field (shadowing)",
                "class R {\n int id;\n R(int id) {\n  id = id;\n }\n}",
                new String[]{"R.id = id;", "self.id = id;", "this.id = id;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: equals(Robot) เป็น overload ไม่ใช่ override | เฉลย: equals(Object o)
        list.add(new Question("16. ตรวจสอบการ Override method equals ให้ signature ถูกต้อง",
                "public boolean equals(Robot r) {\n return id == r.id;\n}",
                new String[]{"public boolean equals(Object o) {", "public bool equals(Object o) {", "public boolean equal(Object o) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: ต้อง downcast จาก Animal เป็น Dog | เฉลย: (Dog) a
        list.add(new Question("17. ตรวจสอบการแปลงชนิด object จากคลาสแม่เป็นคลาสลูก (downcast)",
                "Animal a = new Dog();\nDog d = a;",
                new String[]{"Dog d = a.toDog();", "Dog d = (Dog) a;", "Dog d = new a;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: ลืมปิดทรัพยากร | เฉลย: try-with-resources
        list.add(new Question("18. ตรวจสอบการปิดทรัพยากรอัตโนมัติด้วย try-with-resources",
                "Scanner sc = new Scanner(System.in);\ntry {\n sc.nextLine();\n}\n// ลืมปิด sc",
                new String[]{"try Scanner sc = new Scanner(System.in) {", "using (Scanner sc = new Scanner(System.in)) {", "try (Scanner sc = new Scanner(System.in)) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: varargs ต้องเขียน int... (จุดสามจุดหลังชนิด) | เฉลย: int... nums
        list.add(new Question("19. ตรวจสอบรูปแบบ varargs (จำนวน parameter ไม่จำกัด)",
                "static int sum(int nums...) {\n int t = 0;\n for (int n : nums) t += n;\n return t;\n}",
                new String[]{"static int sum(int... nums) {", "static int sum(...int nums) {", "static int sum(int[] nums...) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: char + int ได้ int ต้อง cast กลับ | เฉลย: (char) (c + 1)
        list.add(new Question("20. ตรวจสอบการบวกเลขกับ char แล้วกำหนดกลับให้ char",
                "char c = 'a';\nc = c + 1;",
                new String[]{"c = c + '1';", "c = (char) (c + 1);", "c = c + 1.0;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: lambda แก้ local variable ภายนอกไม่ได้ | เฉลย: ใช้ array 1 ช่อง int[] count = {0}
        list.add(new Question("21. ตรวจสอบการแก้ค่าตัวแปรภายนอกใน lambda (ต้องเป็น effectively final)",
                "int count = 0;\nlist.forEach(x -> count++);",
                new String[]{"list.forEach(x -> this.count);", "final count = 0;\nlist.forEach(x -> count++);", "int[] count = {0};\nlist.forEach(x -> count[0]++);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: List<String> ไม่ใช่ subtype ของ List<Object> | เฉลย: List<?>
        list.add(new Question("22. ตรวจสอบการกำหนด List<Object> ด้วย ArrayList<String> (Generics ไม่รองรับ inheritance)",
                "List<Object> objs = new ArrayList<String>();",
                new String[]{"List<?> objs = new ArrayList<String>();", "List<Object> objs = new ArrayList<String>() {};", "List<Object> objs = (List<Object>) new ArrayList<String>();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: method ใน interface ที่มี body ต้องเป็น default | เฉลย: default void hi()
        list.add(new Question("23. ตรวจสอบการเขียน method ที่มี body ใน interface",
                "interface A {\n void hi() { System.out.println(\"hi\"); }\n}",
                new String[]{"final void hi() { System.out.println(\"hi\"); }", "default void hi() { System.out.println(\"hi\"); }", "static void hi(); { System.out.println(\"hi\"); }"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: List.of() สร้าง list ที่แก้ไขไม่ได้ | เฉลย: new ArrayList<>(List.of(1, 2))
        list.add(new Question("24. ตรวจสอบการเพิ่มข้อมูลลงใน List ที่สร้างจาก List.of() (immutable)",
                "List<Integer> l = List.of(1, 2);\nl.add(3); // UnsupportedOperationException",
                new String[]{"List<Integer> l = List.mutable(1, 2);\nl.add(3);", "List<Integer> l = List.of(1, 2);\nl.addAll(3);", "List<Integer> l = new ArrayList<>(List.of(1, 2));\nl.add(3);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: private เข้าถึงจากนอกคลาสไม่ได้ | เฉลย: เพิ่ม getter getX()
        list.add(new Question("25. ตรวจสอบการเข้าถึง private field จากคลาสอื่น",
                "class A { private int x; }\nclass B {\n int f(A a) { return a.x; }\n}",
                new String[]{"เพิ่ม public int getX() { return x; } ใน A แล้วเรียก a.getX()", "เปลี่ยนเป็น a->x", "ใส่ final ให้คลาส B"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: run() ไม่ได้สร้าง thread ใหม่ | เฉลย: t.start()
        list.add(new Question("26. ตรวจสอบการสั่งให้ Thread เริ่มทำงานแบบขนาน",
                "Thread t = new Thread(task);\nt.run(); // รันใน thread เดิม ไม่ได้ขนาน",
                new String[]{"t.begin();", "t.start();", "t.execute();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: a * b ล้นเป็น int ก่อนแปลงเป็น long | เฉลย: (long) a * b
        list.add(new Question("27. ตรวจสอบการคูณ int สองตัวแล้วล้น (overflow) ก่อนกำหนดให้ long",
                "int a = 100000, b = 100000;\nlong sum = a * b;",
                new String[]{"long sum = (long) (a * b);", "long sum = a * b * 1L;", "long sum = (long) a * b;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: case ของ enum ใช้ชื่อค่าตรง ๆ | เฉลย: case UP:
        list.add(new Question("28. ตรวจสอบการเขียน case ของ switch ที่ใช้กับ enum",
                "enum Dir { UP, DOWN }\nswitch (d) {\n case Dir.UP: go(); break;\n}",
                new String[]{"case UP: go(); break;", "case 'UP': go(); break;", "case \"UP\": go(); break;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Stream ต้องมี terminal operation | เฉลย: collect(Collectors.toList())
        list.add(new Question("29. ตรวจสอบการใช้ Stream filter ที่ต้องมี terminal operation",
                "List<Integer> ev = list.stream().filter(x -> x % 2 == 0);",
                new String[]{"List<Integer> ev = list.stream().filter(x -> x % 2 == 0).get();", "List<Integer> ev = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());", "List<Integer> ev = list.stream().filter(x -> x % 2 == 0).toArray();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Optional.of(null) จะ throw NPE | เฉลย: Optional.ofNullable(null)
        list.add(new Question("30. ตรวจสอบการสร้าง Optional จากค่าที่อาจเป็น null",
                "Optional<String> o = Optional.of(null); // NullPointerException",
                new String[]{"Optional<String> o = Optional.empty(null);", "Optional<String> o = new Optional<>(null);", "Optional<String> o = Optional.ofNullable(null);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: %d ใช้กับทศนิยมไม่ได้ | เฉลย: %.1f
        list.add(new Question("31. ตรวจสอบรูปแบบ format ให้ตรงกับชนิดข้อมูลทศนิยม",
                "String s = String.format(\"%d\", 3.5); // IllegalFormatConversionException",
                new String[]{"String s = String.format(\"%.1f\", 3.5);", "String s = String.format(\"%s\" 3.5);", "String s = String.format(3.5, \"%d\");"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: remove(1) ลบตาม index ไม่ใช่ตามค่า | เฉลย: remove(Integer.valueOf(1))
        list.add(new Question("32. ตรวจสอบการลบสมาชิกที่เป็นค่า (ไม่ใช่ index) ออกจาก List<Integer>",
                "List<Integer> l = new ArrayList<>(List.of(10, 20, 1));\nl.remove(1); // ต้องการลบ \"ค่า 1\" ไม่ใช่ index 1",
                new String[]{"l.remove((int) 1);", "l.remove(Integer.valueOf(1));", "l.removeValue(1);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: inner class ต้องมี object ของ Outer ก่อน | เฉลย: new Outer().new Inner()
        list.add(new Question("33. ตรวจสอบการสร้าง inner class (non-static) จากภายนอกคลาส",
                "class Outer { class Inner {} }\nInner i = new Inner(); // ใน static main",
                new String[]{"Inner i = Outer.new Inner();", "Inner i = new Outer.Inner();", "Outer.Inner i = new Outer().new Inner();"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: class extends หลาย interface ไม่ได้ | เฉลย: implements A, B
        list.add(new Question("34. ตรวจสอบการสืบทอดหลาย interface พร้อมกัน",
                "interface A {}\ninterface B {}\nclass C extends A, B {}",
                new String[]{"class C implements A, B {}", "class C inherits A, B {}", "class C extends A implements B {}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Comparator ต้องคืน int ไม่ใช่ boolean | เฉลย: a.hp - b.hp
        list.add(new Question("35. ตรวจสอบการเขียน Comparator แบบ lambda เพื่อเรียงตามค่า hp",
                "list.sort((a, b) -> a.hp > b.hp);",
                new String[]{"list.sort(a, b -> a.hp - b.hp);", "list.sort((a, b) -> a.hp - b.hp);", "list.sort((a, b) => a.hp - b.hp);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: String วน for-each ตรง ๆ ไม่ได้ | เฉลย: "abc".toCharArray()
        list.add(new Question("36. ตรวจสอบการวนอ่านทีละตัวอักษรของ String",
                "for (char c : \"abc\") {\n print(c);\n}",
                new String[]{"for (char c in \"abc\") {", "for (String c : \"abc\") {", "for (char c : \"abc\".toCharArray()) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Math.round(double) คืน long | เฉลย: (int) Math.round(2.5)
        list.add(new Question("37. ตรวจสอบชนิดข้อมูลที่ Math.round(double) คืนค่า",
                "int r = Math.round(2.5); // possible lossy conversion from long",
                new String[]{"int r = (int) Math.round(2.5);", "int r = Math.round(2.5) as int;", "int r = Math.rint(2.5);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: local variable ต้องกำหนดค่าก่อนใช้ | เฉลย: int x = 0;
        list.add(new Question("38. ตรวจสอบการใช้ตัวแปร local ที่ยังไม่ได้กำหนดค่าเริ่มต้น",
                "int x;\nif (flag) x = 1;\nSystem.out.println(x);",
                new String[]{"int x = null;\nif (flag) x = 1;\nSystem.out.println(x);", "int x = 0;\nif (flag) x = 1;\nSystem.out.println(x);", "int x?;\nif (flag) x = 1;\nSystem.out.println(x);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: ต้อง return ทุกเส้นทาง | เฉลย: เพิ่ม return 0;
        list.add(new Question("39. ตรวจสอบ method ที่ไม่ return ค่าในทุกเส้นทางของโค้ด",
                "int f(int n) {\n if (n > 0) return 1;\n}",
                new String[]{"void f(int n) {\n if (n > 0) return 1;\n}", "int f(int n) {\n if (n > 0) return 1;\n else return;\n}", "int f(int n) {\n if (n > 0) return 1;\n return 0;\n}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: String เป็น immutable ต้องรับค่าใหม่ | เฉลย: s = s.toUpperCase();
        list.add(new Question("40. ตรวจสอบความไม่เปลี่ยนแปลงของ String (immutable)",
                "String s = \"abc\";\ns.toUpperCase();\nSystem.out.println(s); // ต้องการ ABC",
                new String[]{"s = s.toUpperCase();", "s.toUpperCase(s);", "s.upper();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // ---------- ข้อ 41-50 ----------

        // บั๊ก: String ไม่มี method reverse() | เฉลย: StringBuilder(...).reverse().toString()
        list.add(new Question("41. ตรวจสอบการกลับด้านข้อความ (String ไม่มี reverse())",
                "String r = \"abc\".reverse();",
                new String[]{"String r = Reverse.of(\"abc\");", "String r = new StringBuilder(\"abc\").reverse().toString();", "String r = \"abc\".reversed();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: get(0) บน list ว่างเกิด IndexOutOfBoundsException | เฉลย: เช็ค isEmpty() ก่อน
        list.add(new Question("42. ตรวจสอบการอ่านค่าจาก List ที่ว่างอยู่ (IndexOutOfBoundsException)",
                "List<String> l = new ArrayList<>();\nString s = l.get(0);",
                new String[]{"String s = l.get(-1);", "String s = l.first();", "String s = l.isEmpty() ? null : l.get(0);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: cast String เป็น Integer ตรง ๆ | เฉลย: เช็ค instanceof ก่อน cast
        list.add(new Question("43. ตรวจสอบการ cast ที่ทำให้เกิด ClassCastException",
                "Object o = \"hi\";\nInteger n = (Integer) o;",
                new String[]{"if (o instanceof Integer) {\n Integer n = (Integer) o;\n}", "Integer n = (int) o.toString();", "Integer n = Integer(o);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: Map ใช้ for-each ตรง ๆ ไม่ได้ | เฉลย: map.keySet()
        list.add(new Question("44. ตรวจสอบการวนอ่าน key ของ Map",
                "Map<String, Integer> map = new HashMap<>();\nfor (String k : map) {\n print(k);\n}",
                new String[]{"for (String k : map.values()) {", "for (String k : map.keySet()) {", "for (String k : map.keys()) {"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: overload ที่ต่างกันแค่ return type ไม่ได้ | เฉลย: เปลี่ยนชนิด parameter เป็น double
        list.add(new Question("45. ตรวจสอบการ Overload method (ต้องต่างกันที่ parameter ไม่ใช่แค่ return type)",
                "int f(int a) { return a; }\ndouble f(int a) { return a; }",
                new String[]{"int f(int a) { return a; }\nint f(int a) { return a * 2; }", "int f(int a) { return a; }\nvoid f(int a) {}", "int f(int a) { return a; }\ndouble f(double a) { return a; }"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: final method ถูก override ไม่ได้ | เฉลย: เอา final ออกจาก go() ใน A
        list.add(new Question("46. ตรวจสอบการ Override method ที่ถูกประกาศเป็น final",
                "class A { final void go() {} }\nclass B extends A {\n void go() {}\n}",
                new String[]{"class A { void go() {}  }\nclass B extends A {\n void go() {}\n}", "class A { final void go() {} }\nclass B extends A {\n final void go() {}\n}", "class A { static void go() {} }\nclass B extends A {\n void go() {}\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: ไม่มี constructor ที่รับ String | เฉลย: เพิ่ม MyEx(String m) { super(m); }
        list.add(new Question("47. ตรวจสอบการสร้าง Custom Exception ที่รับข้อความ",
                "class MyEx extends Exception {}\nthrow new MyEx(\"err\");",
                new String[]{"class MyEx extends Exception { MyEx() { super(\"err\"); } }", "class MyEx extends Exception {\n MyEx(String m) { super(m); }\n}", "class MyEx implements Exception {}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: total เป็น instance field แต่ละ object จึงนับแยกกัน | เฉลย: static int total
        list.add(new Question("48. ตรวจสอบการนับจำนวน object ที่ถูกสร้างทั้งหมด",
                "class C {\n int total = 0;\n C() { total++; } // ต้องการนับ object ทั้งหมด\n}",
                new String[]{"final int total = 0;", "int total = 0;\nstatic C() { total++; }", "static int total = 0;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: List ไม่มี method ตัดค่าซ้ำ | เฉลย: new HashSet<>(l)
        list.add(new Question("49. ตรวจสอบการตัดค่าซ้ำออกจาก List",
                "List<Integer> l = List.of(1, 1, 2);\n// ต้องการให้เหลือ 1, 2",
                new String[]{"Set<Integer> s = new HashSet<>(l);", "Set<Integer> s = l.distinct();", "List<Integer> s = l.unique();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // บั๊ก: . ใน regex คือตัวอักษรใดก็ได้ | เฉลย: split("\\.")
        list.add(new Question("50. ตรวจสอบการแยกข้อความด้วย split (regex)",
                "String[] p = \"a.b\".split(\".\"); // ได้ array ว่าง",
                new String[]{"String[] p = \"a.b\".split('.');", "String[] p = \"a.b\".split(\"\\\\.\");", "String[] p = \"a.b\".splitAt(\".\");"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));
    }

    // =====================================================================
    // ระดับยาก (DIFF_HARD) 50 ข้อ
    // =====================================================================
    public static void addHard(List<Question> list) {

        // บั๊ก: Singleton ไม่ปลอดภัยเมื่อหลาย thread | เฉลย: volatile + synchronized แบบ double-checked
        list.add(new Question("1. ตรวจสอบการสร้าง Singleton ให้ปลอดภัยเมื่อมีหลาย Thread (Double-Checked Locking)",
                "private static Singleton inst;\nstatic Singleton get() {\n if (inst == null) inst = new Singleton(); // หลาย thread สร้างซ้ำได้\n return inst;\n}",
                new String[]{"private static volatile Singleton inst;\nstatic Singleton get() {\n if (inst == null) {\n  synchronized (Singleton.class) {\n   if (inst == null) inst = new Singleton();\n  }\n }\n return inst;\n}", "private static transient Singleton inst;\nstatic Singleton get() {\n if (inst == null) inst = new Singleton();\n return inst;\n}", "private static final Singleton inst = null;\nstatic Singleton get() {\n return inst;\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: count++ ไม่ atomic เกิด race condition | เฉลย: AtomicInteger.incrementAndGet()
        list.add(new Question("2. ตรวจสอบ Race Condition เมื่อหลาย Thread เพิ่มค่าตัวนับพร้อมกัน",
                "int count = 0;\n// 2 threads ทำ count++ พร้อมกัน ผลลัพธ์ไม่ตรงตามจริง",
                new String[]{"volatile int count = 0;\ncount++;", "AtomicInteger count = new AtomicInteger();\ncount.incrementAndGet();", "int count = 0;\ncount = count + 1;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: ล็อก a, b สลับลำดับกันทำให้ deadlock | เฉลย: ล็อกด้วยลำดับเดียวกัน a ก่อน b
        list.add(new Question("3. ตรวจสอบการเกิด Deadlock จากการล็อกสองตัวสลับลำดับกัน",
                "Thread1: synchronized (a) { synchronized (b) { work(); } }\nThread2: synchronized (b) { synchronized (a) { work(); } }",
                new String[]{"Thread2: synchronized (b) { synchronized (a) { work(); a.wait(); } }", "Thread2: synchronized (a.hashCode()) { work(); }", "Thread2: synchronized (a) { synchronized (b) { work(); } }"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: override equals แต่ไม่ override hashCode | เฉลย: เพิ่ม hashCode() ที่สอดคล้องกับ id
        list.add(new Question("4. ตรวจสอบสัญญาระหว่าง equals() และ hashCode() เมื่อใช้กับ HashSet",
                "class P {\n int id;\n public boolean equals(Object o) { return o instanceof P p && p.id == id; }\n}\nSet<P> s = new HashSet<>(); // เก็บ P ซ้ำได้",
                new String[]{"public int hashCode() { return Integer.hashCode(id); }", "public long hashCode() { return id; }", "public int hashCode() { return (int) (Math.random() * 100); }"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: static cache โตไม่หยุด (memory leak) | เฉลย: WeakHashMap
        list.add(new Question("5. ตรวจสอบ Memory Leak จาก cache แบบ static ที่โตขึ้นเรื่อย ๆ",
                "static List<Object> cache = new ArrayList<>();\nvoid load() {\n cache.add(new byte[1_000_000]); // memory leak\n}",
                new String[]{"System.gc(); // เรียกทุกครั้งหลัง add", "static Map<String, Object> cache = new WeakHashMap<>();", "static final List<Object> cache = null;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: ArrayList ไม่ thread-safe เมื่อเขียนใน parallel stream | เฉลย: collect(Collectors.toList())
        list.add(new Question("6. ตรวจสอบการเขียน List จากหลาย Thread ใน parallel stream",
                "List<Integer> out = new ArrayList<>();\nIntStream.range(0, 1000).parallel().forEach(out::add); // ข้อมูลหาย/พัง",
                new String[]{"IntStream.range(0, 1000).parallel().peek(out::add).count();", "out = Collections.unmodifiableList(out);", "List<Integer> out = IntStream.range(0, 1000).parallel().boxed().collect(Collectors.toList());"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: new T[] ไม่ได้เพราะ type erasure | เฉลย: (T[]) new Object[10]
        list.add(new Question("7. ตรวจสอบการสร้าง Generic Array (Java ห้าม new T[])",
                "class Box<T> {\n T[] arr = new T[10];\n}",
                new String[]{"T[] arr = (T[]) new Object[10];", "T[] arr = new T<10>();", "T[] arr = T.newArray(10);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: PECS: ผู้ผลิต extends / ผู้บริโภค super | เฉลย: List<? super T> dst, List<? extends T> src
        list.add(new Question("8. ตรวจสอบการใช้ Wildcard ตามหลัก PECS (Producer Extends, Consumer Super)",
                "void copy(List<Number> dst, List<Integer> src)\n// ต้องการให้ copy ได้ยืดหยุ่นกับชนิดย่อย/ชนิดแม่",
                new String[]{"void copy(List<?> dst, List<?> src)", "<T> void copy(List<? super T> dst, List<? extends T> src)", "<T> void copy(List<T extends Number> dst, List<T> src)"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: recursion ไม่มี base case (StackOverflowError) | เฉลย: n <= 1 ? 1 : n * fact(n - 1)
        list.add(new Question("9. ตรวจสอบ Recursion ที่ไม่มีเงื่อนไขหยุด (StackOverflowError)",
                "int fact(int n) {\n return n * fact(n - 1); // StackOverflowError\n}",
                new String[]{"int fact(int n) {\n return n * fact(n);\n}", "int fact(int n) {\n if (n == 0) return 0;\n return n * fact(n - 1);\n}", "int fact(int n) {\n return n <= 1 ? 1 : n * fact(n - 1);\n}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: "P:" + this เรียก toString() วนไม่รู้จบ | เฉลย: ใช้ field เช่น id
        list.add(new Question("10. ตรวจสอบ toString() ที่เรียกตัวเองวนไม่รู้จบ",
                "public String toString() {\n return \"P:\" + this;\n}",
                new String[]{"return \"P:\" + id;", "return \"P:\" + toString();", "return \"P:\" + super;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: ต้องการ immutable class | เฉลย: final class + private final field + ไม่มี setter
        list.add(new Question("11. ตรวจสอบการออกแบบคลาสแบบ Immutable",
                "class Pt { public int x; } // ต้องการคลาสที่เปลี่ยนค่าไม่ได้หลังสร้าง",
                new String[]{"class Pt { private int x; void setX(int x) { this.x = x; } }", "final class Pt {\n private final int x;\n Pt(int x) { this.x = x; }\n int getX() { return x; }\n}", "class Pt { static int x; }"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: return ใน finally ทับค่า return ของ try | เฉลย: ใช้ finally แค่ cleanup
        list.add(new Question("12. ตรวจสอบ return ใน finally ที่กลบค่า return ของ try",
                "int f() {\n try { return 1; }\n finally { return 2; } // ผลลัพธ์คือ 2 เสมอ\n}",
                new String[]{"int f() {\n try { return 1; }\n catch (Exception e) { return 2; }\n finally { return 2; }\n}", "int f() {\n return 1;\n finally { }\n}", "int f() {\n try { return 1; }\n finally { cleanup(); }\n}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: Comparator ไม่คืน 0 เมื่อค่าเท่ากัน | เฉลย: Integer.compare(a, b)
        list.add(new Question("13. ตรวจสอบ Comparator ที่ผิดสัญญา (ไม่คืน 0 เมื่อค่าเท่ากัน)",
                "list.sort((a, b) -> a > b ? 1 : -1); // IllegalArgumentException: Comparison method violates its general contract",
                new String[]{"list.sort((a, b) -> Integer.compare(a, b));", "list.sort((a, b) -> a > b ? 1 : 0);", "list.sort((a, b) -> a - b > 0);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: ใช้ y ก่อนถูกกำหนดค่า (ได้ค่า default 0) | เฉลย: ย้าย y ขึ้นก่อน x
        list.add(new Question("14. ตรวจสอบลำดับการกำหนดค่า static ที่ใช้ตัวแปรก่อนถูกกำหนดค่า",
                "class A {\n static int x = A.y + 1;\n static int y = 5;\n} // x ได้ 1 ไม่ใช่ 6",
                new String[]{"static int x = A.y + 1;\nstatic int y = 5;", "static int y = 5;\nstatic int x = y + 1;", "static int x = this.y + 1;\nstatic int y = 5;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: catch InterruptedException แล้วเงียบ | เฉลย: Thread.currentThread().interrupt()
        list.add(new Question("15. ตรวจสอบการจัดการ InterruptedException อย่างถูกต้อง",
                "try {\n Thread.sleep(1000);\n} catch (InterruptedException e) {\n // ignore\n}",
                new String[]{"catch (InterruptedException e) {\n e = null;\n}", "catch (InterruptedException e) {\n Thread.sleep(0);\n}", "catch (InterruptedException e) {\n Thread.currentThread().interrupt();\n}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: thread pool ไม่ shutdown โปรแกรมไม่จบ | เฉลย: ex.shutdown()
        list.add(new Question("16. ตรวจสอบ ExecutorService ที่ไม่ปิดทำให้โปรแกรมไม่จบ",
                "ExecutorService ex = Executors.newFixedThreadPool(4);\nex.submit(task);\n// โปรแกรมไม่ยอมจบการทำงาน",
                new String[]{"ex.submit(task);\nex.shutdown();", "ex.submit(task);\nex.stop();", "ex.submit(task).cancel();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: CompletableFuture ต้องรอผลด้วย join()/get() | เฉลย: f.join()
        list.add(new Question("17. ตรวจสอบการรอผลลัพธ์จาก CompletableFuture",
                "CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> load());\nString s = f;",
                new String[]{"String s = f.value;", "String s = f.join();", "String s = (String) f;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: SimpleDateFormat ไม่ thread-safe | เฉลย: ThreadLocal<SimpleDateFormat>
        list.add(new Question("18. ตรวจสอบการใช้ SimpleDateFormat ร่วมกันหลาย Thread (ไม่ thread-safe)",
                "static SimpleDateFormat fmt = new SimpleDateFormat(\"yyyy\"); // ใช้ร่วมกันทุก thread",
                new String[]{"static volatile SimpleDateFormat fmt = new SimpleDateFormat(\"yyyy\");", "static final SimpleDateFormat fmt = new SimpleDateFormat(\"yyyy\");", "static final ThreadLocal<SimpleDateFormat> fmt =\n ThreadLocal.withInitial(() -> new SimpleDateFormat(\"yyyy\"));"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: clone() ของ array 2 มิติเป็นแบบตื้น | เฉลย: clone ทีละแถว
        list.add(new Question("19. ตรวจสอบการคัดลอก Array 2 มิติแบบ Deep Copy",
                "int[][] b = a.clone(); // ต้องการ deep copy แต่แถวยังใช้ร่วมกัน",
                new String[]{"int[][] b = new int[a.length][];\nfor (int i = 0; i < a.length; i++) b[i] = a[i].clone();", "int[][] b = Arrays.copyOf(a, a.length);", "int[][] b = (int[][]) a;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: Thread ไม่ Serializable | เฉลย: transient Thread t;
        list.add(new Question("20. ตรวจสอบ NotSerializableException จาก field ที่ไม่รองรับ Serializable",
                "class Bot implements Serializable {\n Thread t; // NotSerializableException\n}",
                new String[]{"volatile Thread t;", "transient Thread t;", "final Thread t;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: double เทียบด้วย == คลาดเคลื่อน | เฉลย: Math.abs(d - 0.3) < 1e-9
        list.add(new Question("21. ตรวจสอบการเปรียบเทียบเลขทศนิยม double ที่คลาดเคลื่อน",
                "double d = 0.1 + 0.2;\nif (d == 0.3) {\n pass();\n}",
                new String[]{"if (d.equals(0.3)) {", "if ((float) d == 0.3) {", "if (Math.abs(d - 0.3) < 1e-9) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: double ไม่เหมาะกับเงิน | เฉลย: BigDecimal สร้างจาก String
        list.add(new Question("22. ตรวจสอบการคำนวณเงินที่ต้องแม่นยำ (ห้ามใช้ double/float)",
                "double total = 0.1 * 3; // ใช้กับเงิน ต้องการความแม่นยำ",
                new String[]{"BigDecimal total = new BigDecimal(\"0.1\").multiply(BigDecimal.valueOf(3));", "BigDecimal total = new BigDecimal(0.1).multiply(BigDecimal.valueOf(3));", "float total = 0.1f * 3;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: & มี precedence ต่ำกว่า == | เฉลย: ใส่วงเล็บ (flags & MASK) == MASK
        list.add(new Question("23. ตรวจสอบลำดับความสำคัญของตัวดำเนินการระดับบิต (bitwise precedence)",
                "if (flags & MASK == MASK) {\n go();\n}",
                new String[]{"if (flags && MASK == MASK) {", "if ((flags & MASK) == MASK) {", "if (flags & (MASK == MASK)) {"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: เรียก method ก่อนเช็ค null | เฉลย: obj != null && obj.isReady()
        list.add(new Question("24. ตรวจสอบ Short-circuit ที่ทำให้เกิด NullPointerException",
                "if (obj.isReady() & obj != null) {\n run();\n}",
                new String[]{"if (obj.isReady() && obj != null) {", "if (obj != null & obj.isReady()) {", "if (obj != null && obj.isReady()) {"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: break ออกได้แค่ลูปใน | เฉลย: break outer;
        list.add(new Question("25. ตรวจสอบการออกจากลูปซ้อนหลายชั้นพร้อมกันด้วย Label",
                "outer:\nfor (int i = 0; i < n; i++) {\n for (int j = 0; j < n; j++) {\n  if (x) break; // ออกได้แค่ลูปใน\n }\n}",
                new String[]{"if (x) break outer;", "if (x) exit;", "if (x) break 2;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: Java เป็น pass-by-value เปลี่ยน reference ไม่มีผลกับ caller | เฉลย: แก้ state ของ object เดิม r.reset()
        list.add(new Question("26. ตรวจสอบความเข้าใจ Java เป็น pass-by-value (เปลี่ยน reference ใน method ไม่ส่งผลกับผู้เรียก)",
                "void reset(Robot r) {\n r = new Robot(); // caller ไม่เห็นการเปลี่ยนแปลง\n}",
                new String[]{"void reset(ref Robot r) {\n r = new Robot();\n}", "void reset(Robot r) {\n r.reset(); // แก้ state ของ object เดิม\n}", "void reset(Robot& r) {\n r = new Robot();\n}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: HashMap ไม่ thread-safe | เฉลย: ConcurrentHashMap
        list.add(new Question("27. ตรวจสอบการเลือก Map ที่ปลอดภัยเมื่อหลาย Thread เขียนพร้อมกัน",
                "Map<String, Integer> m = new HashMap<>(); // หลาย thread เขียนพร้อมกัน",
                new String[]{"Map<String, Integer> m = new TreeMap<>();", "Map<String, Integer> m = new LinkedHashMap<>();", "Map<String, Integer> m = new ConcurrentHashMap<>();"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: wait() ต้องอยู่ใน synchronized | เฉลย: synchronized + while (!ready)
        list.add(new Question("28. ตรวจสอบการเรียก wait() นอก synchronized (IllegalMonitorStateException)",
                "lock.wait(); // IllegalMonitorStateException",
                new String[]{"synchronized (lock) {\n while (!ready) lock.wait();\n}", "if (!ready) lock.wait();", "lock.sleep();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: Stream ใช้ซ้ำหลัง terminal operation ไม่ได้ | เฉลย: สร้าง stream ใหม่จาก list
        list.add(new Question("29. ตรวจสอบการใช้ Stream ซ้ำหลังถูกใช้ไปแล้ว",
                "Stream<String> s = list.stream();\ns.forEach(System.out::println);\nlong n = s.count(); // IllegalStateException",
                new String[]{"s.reset();\nlong n = s.count();", "list.stream().forEach(System.out::println);\nlong n = list.stream().count();", "s.rewind();\nlong n = s.count();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: default method ชนกันจาก 2 interface | เฉลย: A.super.hi()
        list.add(new Question("30. ตรวจสอบการแก้ปัญหา default method ชนกันจากสอง interface (Diamond Problem)",
                "interface A { default void hi() {} }\ninterface B { default void hi() {} }\nclass C implements A, B {} // compile error",
                new String[]{"class C implements A, B {\n public void hi() { super.hi(); }\n}", "class C implements A, B {\n default void hi() {}\n}", "class C implements A, B {\n public void hi() { A.super.hi(); }\n}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: instanceof + cast ซ้ำซ้อน | เฉลย: pattern matching: instanceof String s
        list.add(new Question("31. ตรวจสอบ Pattern Matching ของ instanceof (Java 16+) เพื่อลดการ cast ซ้ำ",
                "if (o instanceof String) {\n String s = (String) o;\n print(s);\n}",
                new String[]{"if (o instanceof String s) {\n print(s);\n}", "if (o is String s) {\n print(s);\n}", "if (o instanceof String as s) {\n print(s);\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: คลาสข้อมูลต้องเขียนโค้ดซ้ำมาก | เฉลย: record Point(int x, int y) {}
        list.add(new Question("32. ตรวจสอบการใช้ record (Java 16+) แทนคลาสข้อมูลที่ต้องเขียนซ้ำซ้อน",
                "class Point {\n final int x, y;\n // constructor, getters, equals, hashCode, toString...\n}",
                new String[]{"data class Point(int x, int y)", "record Point(int x, int y) {}", "struct Point { int x; int y; }"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: switch แบบเก่ายาวและต้องใช้ break | เฉลย: switch expression ด้วย ->
        list.add(new Question("33. ตรวจสอบการใช้ switch expression (Java 14+) ที่คืนค่าได้",
                "String r;\nswitch (d) {\n case 1: r = \"a\"; break;\n default: r = \"b\";\n}",
                new String[]{"String r = switch d { 1 => \"a\"; _ => \"b\"; };", "String r = switch (d) {\n case 1: \"a\";\n default: \"b\";\n};", "String r = switch (d) {\n case 1 -> \"a\";\n default -> \"b\";\n};"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: T ไม่มี compareTo | เฉลย: <T extends Comparable<T>>
        list.add(new Question("34. ตรวจสอบการกำหนดขอบเขตของ Generic ให้เรียก compareTo ได้",
                "<T> T max(T a, T b) {\n return a.compareTo(b) > 0 ? a : b;\n}",
                new String[]{"<T extends Comparable<T>> T max(T a, T b) {", "<T implements Comparable<T>> T max(T a, T b) {", "<T super Comparable<T>> T max(T a, T b) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: try-with-resources ต้อง implements AutoCloseable | เฉลย: implements AutoCloseable + public close()
        list.add(new Question("35. ตรวจสอบการทำให้คลาสใช้กับ try-with-resources ได้",
                "class Res { void close() {} }\ntry (Res r = new Res()) {\n use(r);\n}",
                new String[]{"class Res extends AutoCloseable {\n public void close() {}\n}", "class Res implements AutoCloseable {\n public void close() {}\n}", "class Res implements Closing {\n public void close() {}\n}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: String += ในลูป สร้าง object ใหม่ทุกรอบ | เฉลย: StringBuilder.append()
        list.add(new Question("36. ตรวจสอบการต่อ String ในลูปจำนวนมากที่ช้าและกิน memory",
                "String s = \"\";\nfor (int i = 0; i < 100000; i++) {\n s += i; // ช้าและกิน memory\n}",
                new String[]{"String s = \"\";\nfor (int i = 0; i < 100000; i++) s = s.concat(\"\" + i);", "StringBuffer s = \"\";\nfor (int i = 0; i < 100000; i++) s.append(i);", "StringBuilder sb = new StringBuilder();\nfor (int i = 0; i < 100000; i++) sb.append(i);"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: binarySearch ต้องใช้กับ array ที่เรียงแล้ว | เฉลย: Arrays.sort(a) ก่อน
        list.add(new Question("37. ตรวจสอบเงื่อนไขก่อนใช้ Binary Search (ข้อมูลต้องเรียงลำดับก่อน)",
                "int idx = Arrays.binarySearch(a, key); // a ยังไม่เรียงลำดับ ผลลัพธ์ผิด",
                new String[]{"Arrays.sort(a);\nint idx = Arrays.binarySearch(a, key);", "int idx = Arrays.binarySearch(a, key, true);", "Arrays.unsorted(a);\nint idx = Arrays.binarySearch(a, key);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: key ที่ mutable ทำให้ hashCode เปลี่ยนหลัง put | เฉลย: ใช้ key immutable เช่น List.of(1)
        list.add(new Question("38. ตรวจสอบการใช้ key ที่เปลี่ยนค่าได้ใน HashMap (hashCode เปลี่ยนหลัง put)",
                "Map<List<Integer>, String> m = new HashMap<>();\nList<Integer> k = new ArrayList<>(List.of(1));\nm.put(k, \"a\");\nk.add(2);\nm.get(k); // ได้ null",
                new String[]{"m.put(k, \"a\");\nm.rehash();", "List<Integer> k = List.of(1);\nm.put(k, \"a\");\nm.get(k);", "k = k.clone();\nm.get(k);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: for-each ของ PriorityQueue ไม่รับประกันลำดับ | เฉลย: poll() ทีละตัว
        list.add(new Question("39. ตรวจสอบการอ่านค่าจาก PriorityQueue ให้เรียงจากน้อยไปมาก (การวนด้วย for ไม่รับประกันลำดับ)",
                "PriorityQueue<Integer> pq = new PriorityQueue<>(List.of(5, 1, 3));\nfor (int x : pq) System.out.println(x); // ลำดับไม่เรียง",
                new String[]{"for (int x : pq.sorted()) System.out.println(x);", "pq.sort();\nfor (int x : pq) System.out.println(x);", "while (!pq.isEmpty()) System.out.println(pq.poll());"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: setDaemon ต้องเรียกก่อน start() | เฉลย: t.setDaemon(true); t.start();
        list.add(new Question("40. ตรวจสอบลำดับการตั้งค่า Daemon Thread (ต้องตั้งก่อน start)",
                "Thread t = new Thread(task);\nt.start();\nt.setDaemon(true); // IllegalThreadStateException",
                new String[]{"t.setDaemon(true);\nt.start();", "t.start();\nt.daemon = true;", "t.setPriority(Thread.MAX_PRIORITY);\nt.start();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // ---------- ข้อ 41-50 ----------

        // บั๊ก: ค่าที่เปลี่ยนใน thread อื่นอาจไม่ถูกมองเห็น (visibility) | เฉลย: volatile boolean
        list.add(new Question("41. ตรวจสอบ flag หยุดการทำงานที่อีก Thread เปลี่ยนค่าแล้วลูปไม่เห็น",
                "boolean running = true;\nwhile (running) {\n work();\n} // อีก thread ตั้ง running = false แต่ลูปไม่หยุด",
                new String[]{"final boolean running = true;", "volatile boolean running = true;", "transient boolean running = true;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: key ซ้ำทำให้ toMap โยน exception | เฉลย: ใส่ merge function Integer::sum
        list.add(new Question("42. ตรวจสอบ Collectors.toMap เมื่อมี key ซ้ำ (IllegalStateException)",
                "Map<String, Integer> m = list.stream()\n .collect(Collectors.toMap(x -> x.name, x -> x.hp));",
                new String[]{"Map<String, Integer> m = list.stream()\n .collect(Collectors.toMap(x -> x.name));", "Map<String, Integer> m = list.stream()\n .collect(Collectors.toMap(x -> x.name, x -> x.hp, null));", "Map<String, Integer> m = list.stream()\n .collect(Collectors.toMap(x -> x.name, x -> x.hp, Integer::sum));"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: static member ใช้ type parameter ของ instance ไม่ได้ | เฉลย: ทำให้เป็น instance field
        list.add(new Question("43. ตรวจสอบการใช้ type parameter ของคลาสกับ static field",
                "class Box<T> {\n static T value; // compile error\n}",
                new String[]{"T value; // เปลี่ยนเป็น instance field", "static <T> T value;", "static T<> value;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: exception ทำให้ข้าม unlock() | เฉลย: try { ... } finally { lock.unlock(); }
        list.add(new Question("44. ตรวจสอบการปลดล็อกเมื่อเกิด Exception (ReentrantLock)",
                "lock.lock();\nwork(); // ถ้า work() throw ล็อกจะค้าง\nlock.unlock();",
                new String[]{"lock.lock();\nwork();\nlock.unlock();", "lock.lock();\ntry {\n work();\n} finally {\n lock.unlock();\n}", "lock.lock();\ntry {\n work();\n} catch (Exception e) {\n lock.unlock();\n}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: คืน reference ของ list จริงทำให้แก้ไขจากภายนอกได้ | เฉลย: Collections.unmodifiableList(m)
        list.add(new Question("45. ตรวจสอบการคืน collection ภายในคลาสโดยไม่ป้องกันการแก้ไขจากภายนอก",
                "class Team {\n private List<String> m = new ArrayList<>();\n List<String> getM() { return m; } // ภายนอกแก้ไขได้\n}",
                new String[]{"List<String> getM() { return m.clone(); }", "List<String> getM() { return final m; }", "List<String> getM() { return Collections.unmodifiableList(m); }"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: map() ไม่ทำให้ list ซ้อนแบนลง | เฉลย: flatMap(List::stream)
        list.add(new Question("46. ตรวจสอบการรวม List ซ้อน List ให้เป็น List เดียว",
                "List<List<Integer>> n = ...;\nList<Integer> f = n.stream().map(l -> l).collect(Collectors.toList());",
                new String[]{"List<Integer> f = n.stream().flatMap(List::stream).collect(Collectors.toList());", "List<Integer> f = n.stream().flat().collect(Collectors.toList());", "List<Integer> f = n.stream().map(List::stream).collect(Collectors.toList());"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: ไม่รอ thread ให้จบ | เฉลย: t1.join(); t2.join();
        list.add(new Question("47. ตรวจสอบการรอให้ Thread ทำงานเสร็จก่อนพิมพ์ผล",
                "t1.start();\nt2.start();\nSystem.out.println(\"done\"); // พิมพ์ก่อน thread จบ",
                new String[]{"t1.start();\nt2.start();\nt1.stop();\nt2.stop();\nSystem.out.println(\"done\");", "t1.start();\nt2.start();\nt1.join();\nt2.join();\nSystem.out.println(\"done\");", "t1.run();\nt2.start();\nSystem.out.println(\"done\");"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: int หารด้วย 0 throw exception ส่วน double ได้ Infinity | เฉลย: ใช้ 5.0 / 0
        list.add(new Question("48. ตรวจสอบการหารด้วยศูนย์ของจำนวนเต็มและทศนิยม",
                "int r = 5 / 0; // ArithmeticException",
                new String[]{"int r = 5 / 0.0;", "double r = 5 / 0;", "double r = 5.0 / 0; // ได้ Infinity ไม่ throw"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: record ไม่มีการตรวจค่าตอนสร้าง | เฉลย: compact constructor Age { ... throw ... }
        list.add(new Question("49. ตรวจสอบการตรวจค่าใน record ด้วย compact constructor",
                "record Age(int v) {} // ต้องการห้ามค่าติดลบ",
                new String[]{"record Age(int v) {\n Age {\n  if (v < 0) throw new IllegalArgumentException();\n }\n}", "record Age(int v) {\n void set(int v) { if (v < 0) return; }\n}", "record Age(int v) {\n Age(int v) { if (v < 0) return; }\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // บั๊ก: exception ใน async ไม่ถูกจัดการ | เฉลย: .exceptionally(e -> 0)
        list.add(new Question("50. ตรวจสอบการจัดการ Exception ใน CompletableFuture",
                "CompletableFuture.supplyAsync(() -> 1 / 0)\n .thenAccept(System.out::println); // exception ถูกกลืนเงียบ ๆ",
                new String[]{"CompletableFuture.supplyAsync(() -> 1 / 0)\n .thenAccept(System.out::println)\n .catch(e -> 0);", "CompletableFuture.supplyAsync(() -> 1 / 0)\n .exceptionally(e -> 0)\n .thenAccept(System.out::println);", "CompletableFuture.supplyAsync(() -> 1 / 0)\n .thenAccept(System.out::println)\n .finally(() -> {});"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));


        // EASY
// 1. การใช้งานตัวพิมพ์ใหญ่-เล็กของค่า Boolean
        list.add(new Question("1. การใช้งานตัวพิมพ์ใหญ่-เล็กของค่า Boolean",
                "while true:\n    break",
                new String[]{"while True:\n    break", "while(true):", "do while True:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 2. การใช้อินเดนเทชัน (Indentation) ในฟังก์ชัน
        list.add(new Question("2. การใช้อินเดนเทชัน (Indentation) ในฟังก์ชัน",
                "def repair():\nprint('Repairing')",
                new String[]{"def repair():\n    print('Repairing')", "def repair(): print('Repairing');", "repair(): print('Repairing')"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 3. การพิมพ์ข้อความรวมกับตัวเลขด้วยการแปลงประเภทข้อมูล
        list.add(new Question("3. การพิมพ์ข้อความรวมกับตัวเลขด้วยการแปลงประเภทข้อมูล",
                "print(\"Bot \" + 1)",
                new String[]{"print(\"Bot \" . 1)", "print(\"Bot \" + str(1))", "print(\"Bot \" & 1)"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 4. การประกาศฟังก์ชันด้วยคีย์เวิร์ด def และเครื่องหมายโคลอน
        list.add(new Question("4. การประกาศฟังก์ชันด้วยคีย์เวิร์ด def และเครื่องหมายโคลอน",
                "def repair()\n    pass",
                new String[]{"function repair():", "def repair;", "def repair():\n    pass"},
                2, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 5. การเปรียบเทียบเงื่อนไขด้วยเครื่องหมายเท่ากับสองตัว
        list.add(new Question("5. การเปรียบเทียบเงื่อนไขด้วยเครื่องหมายเท่ากับสองตัว",
                "if x = 5:\n    print('Fixing')",
                new String[]{"if x == 5:", "if x === 5:", "if (x = 5):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 6. การใช้งานคำสั่งพิมพ์ข้อความออกหน้าจอ print
        list.add(new Question("6. การใช้งานคำสั่งพิมพ์ข้อความออกหน้าจอ print",
                "echo(\"Hello Bot\")",
                new String[]{"print(\"Hello Bot\")", "printf(\"Hello Bot\")", "Console.WriteLine(\"Hello Bot\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 7. การรับค่าจากผู้ใช้ผ่านฟังก์ชัน input
        list.add(new Question("7. การรับค่าจากผู้ใช้ผ่านฟังก์ชัน input",
                "num = input(\"Enter: \")\nprint(num + 5)",
                new String[]{"num = int(input(\"Enter: \"))\nprint(num + 5)", "num = input(\"Enter: \")\nprint(int(num + 5))", "num = input(\"Enter: \").toInt()\nprint(num + 5)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 8. การประกาศตัวแปรสตริงด้วยเครื่องหมายคำพูดคู่หรือเดี่ยว
        list.add(new Question("8. การประกาศตัวแปรสตริงด้วยเครื่องหมายคำพูดคู่หรือเดี่ยว",
                "msg = 'It's a robot'",
                new String[]{"msg = \"It's a robot\"", "msg = 'It\"s a robot'", "msg = It's a robot"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 9. การใช้งานตัวดำเนินการหารเอาเศษ (Modulo)
        list.add(new Question("9. การใช้งานตัวดำเนินการหารเอาเศษ (Modulo)",
                "remainder = 5 %% 2",
                new String[]{"remainder = 5 % 2", "remainder = mod(5, 2)", "remainder = 5 mod 2"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 10. การสร้างลิสต์ (List) ข้อมูลพื้นฐาน
        list.add(new Question("10. การสร้างลิสต์ (List) ข้อมูลพื้นฐาน",
                "items = {1, 2, 3}",
                new String[]{"items = [1, 2, 3]", "items = (1, 2, 3)", "items = <1, 2, 3>"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 11. การเข้าถึงสมาชิกตัวแรกของลิสต์ด้วย Index 0
        list.add(new Question("11. การเข้าถึงสมาชิกตัวแรกของลิสต์ด้วย Index 0",
                "items = [10, 20, 30]\nval = items(0)",
                new String[]{"val = items[0]", "val = items.first()", "val = items{0}"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 12. การใช้ลูป for วนซ้ำผ่านช่วงตัวเลข range()
        list.add(new Question("12. การใช้ลูป for วนซ้ำผ่านช่วงตัวเลข range()",
                "for i in range(0, 5)\n    print(i)",
                new String[]{"for i in range(0, 5):\n    print(i)", "for (int i=0; i<5; i++)", "for i = 0 to 5:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 13. การใช้ลูป while เงื่อนไขพื้นฐาน
        list.add(new Question("13. การใช้ลูป while เงื่อนไขพื้นฐาน",
                "while x < 5\n    x += 1",
                new String[]{"while x < 5:\n    x += 1", "while (x < 5) do", "while [x < 5]:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 14. คำสั่งหยุดการทำงานของลูปทันที (break)
        list.add(new Question("14. คำสั่งหยุดการทำงานของลูปทันที (break)",
                "while True:\n    stop",
                new String[]{"break", "exit", "return"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 15. คำสั่งข้ามรอบปัจจุบันไปรอบถัดไป (continue)
        list.add(new Question("15. คำสั่งข้ามรอบปัจจุบันไปรอบถัดไป (continue)",
                "for i in range(5):\n    skip",
                new String[]{"continue", "next", "pass"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 16. ตั้งชื่อตัวแปรทับฟังก์ชันที่มีอยู่แล้ว (Built-in Name Shadowing)
        list.add(new Question("16. ตั้งชื่อตัวแปรทับฟังก์ชันที่มีอยู่แล้ว (Built-in Name Shadowing)",
                "len = 10\nprint(len(\"Bot\"))",
                new String[]{"length = 10\nprint(len(\"Bot\"))", "len = 10\nprint(length(\"Bot\"))", "del len\nlen = 10\nprint(len(\"Bot\"))"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 17. การใช้คอมเมนต์บรรทัดเดียวด้วยเครื่องหมายชาร์ป (#)
        list.add(new Question("17. การใช้คอมเมนต์บรรทัดเดียวด้วยเครื่องหมายชาร์ป (#)",
                "// Comment line",
                new String[]{"# Comment line", "/* Comment line */", "-- Comment line"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 18. การตรวจสอบสมาชิกด้วยโอเปอเรเตอร์ in
        list.add(new Question("18. การตรวจสอบสมาชิกด้วยโอเปอเรเตอร์ in",
                "if 1 has [1, 2, 3]:",
                new String[]{"if 1 in [1, 2, 3]:", "if 1 exists [1, 2, 3]:", "if contains(1, [1, 2, 3]):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 19. การต่อสตริงข้อความด้วยเครื่องหมายบวก (+)
        list.add(new Question("19. การต่อสตริงข้อความด้วยเครื่องหมายบวก (+)",
                "msg = \"Hello\" . \"Bot\"",
                new String[]{"msg = \"Hello\" + \"Bot\"", "msg = \"Hello\" & \"Bot\"", "msg = concat(\"Hello\", \"Bot\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 20. การหาความยาวของลิสต์หรือสตริงด้วยฟังก์ชัน len()
        list.add(new Question("20. การหาความยาวของลิสต์หรือสตริงด้วยฟังก์ชัน len()",
                "size = [1, 2, 3].length()",
                new String[]{"size = len([1, 2, 3])", "size = size([1, 2, 3])", "size = count([1, 2, 3])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 21. การแปลงข้อความตัวเลขเป็นจำนวนเต็มด้วย int()
        list.add(new Question("21. การแปลงข้อความตัวเลขเป็นจำนวนเต็มด้วย int()",
                "n = \"123\".to_int()",
                new String[]{"n = int(\"123\")", "n = Integer.parse(\"123\")", "n = (int)\"123\""},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 22. การแปลงตัวเลขเป็นข้อความด้วย str()
        list.add(new Question("22. การแปลงตัวเลขเป็นข้อความด้วย str()",
                "s = 100.toString()",
                new String[]{"s = str(100)", "s = String(100)", "s = 100.text()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 23. การแปลงตัวเลขเป็นทศนิยมด้วย float()
        list.add(new Question("23. การแปลงตัวเลขเป็นทศนิยมด้วย float()",
                "f = \"3.14\".toDouble()",
                new String[]{"f = float(\"3.14\")", "f = Double(\"3.14\")", "f = (float)\"3.14\""},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 24. การเข้าถึงสมาชิกตัวสุดท้ายของลิสต์ด้วย Index ติดลบ (-1)
        list.add(new Question("24. การเข้าถึงสมาชิกตัวสุดท้ายของลิสต์ด้วย Index ติดลบ (-1)",
                "items = [10, 20, 30]\nlast = items[3]",
                new String[]{"last = items[-1]", "last = items[last]", "last = items.last()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 25. การตรวจสอบประเภทข้อมูลด้วยฟังก์ชัน type()
        list.add(new Question("25. การตรวจสอบประเภทข้อมูลด้วยฟังก์ชัน type()",
                "t = gettype(5)",
                new String[]{"t = type(5)", "t = typeof(5)", "t = 5.type()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 26. การสร้างดิกชันนารี (Dictionary) พื้นฐาน
        list.add(new Question("26. การสร้างดิกชันนารี (Dictionary) พื้นฐาน",
                "d = ( \"key\": 1 )",
                new String[]{"d = { \"key\": 1 }", "d = [ \"key\": 1 ]", "d = < \"key\": 1 >"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 27. การเข้าถึงค่าใน Dictionary ด้วย Key
        list.add(new Question("27. การเข้าถึงค่าใน Dictionaryด้วย Key",
                "d = {'a': 1}\nval = d.a",
                new String[]{"val = d['a']", "val = d.get_key('a')", "val = d(a)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 28. การใช้เมธอด append เพิ่มข้อมูลเข้าลิสต์
        list.add(new Question("28. การใช้เมธอด append เพิ่มข้อมูลเข้าลิสต์",
                "l = [1, 2]\nl.add(3)",
                new String[]{"l.append(3)", "l.push(3)", "l.insert(3)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 29. การใช้เมธอด remove ลบข้อมูลออกจากลิสต์
        list.add(new Question("29. การใช้เมธอด remove ลบข้อมูลออกจากลิสต์",
                "l = [1, 2, 3]\nl.delete(2)",
                new String[]{"l.remove(2)", "l.pop(2)", "l.del(2)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 30. การสร้างทัปเปิล (Tuple) ข้อมูลแบบแก้ไขไม่ได้
        list.add(new Question("30. การสร้างทัปเปิล (Tuple) ข้อมูลแบบแก้ไขไม่ได้",
                "t = [1, 2, 3] (as immutable)",
                new String[]{"t = (1, 2, 3)", "t = {1, 2, 3}", "t = const [1, 2, 3]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 31. การใช้งานฟังก์ชัน abs() หาค่าสัมบูรณ์
        list.add(new Question("31. การใช้งานฟังก์ชัน abs() หาค่าสัมบูรณ์",
                "val = math.absolute(-10)",
                new String[]{"val = abs(-10)", "val = absolute(-10)", "val = (-10).abs()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 32. การใช้งานฟังก์ชัน max() และ min()
        list.add(new Question("32. การใช้งานฟังก์ชัน max() และ min()",
                "m = maximum([1, 5, 3])",
                new String[]{"m = max([1, 5, 3])", "m = [1, 5, 3].max()", "m = highest([1, 5, 3])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 33. การใช้เครื่องหมายคูณสตริงเพื่อทำซ้ำข้อความ
        list.add(new Question("33. การใช้เครื่องหมายคูณสตริงเพื่อทำซ้ำข้อความ",
                "s = \"A\" * 3",
                new String[]{"s = \"A\" * 3", "s = repeat(\"A\", 3)", "s = \"A\" ** 3"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 34. ฟังก์ชันไม่มีการส่งค่า (คืนค่า None อัตโนมัติ)
        list.add(new Question("34. ฟังก์ชันไม่มีการส่งค่า (คืนค่า None อัตโนมัติ)",
                "def add(a, b):\n    a + b\nresult = add(1, 2)",
                new String[]{"def add(a, b):\n    return a + b", "def add(a, b):\n    print(a + b)", "def add(a, b):\n    yield a + b"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 35. การใช้เงื่อนไขทางเลือก elif
        list.add(new Question("35. การใช้เงื่อนไขทางเลือก elif",
                "if x > 0:\n    pass\nelse if x == 0:\n    pass",
                new String[]{"elif x == 0:", "elseif x == 0:", "else if x == 0:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 36. การใช้เงื่อนไขทางเลือกสุดท้าย else
        list.add(new Question("36. การใช้เงื่อนไขทางเลือกสุดท้าย else",
                "if x > 0:\n    pass\notherwise:\n    pass",
                new String[]{"else:", "default:", "otherwise:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 37. การใช้งานตัวดำเนินการตรรกะ and
        list.add(new Question("37. การใช้งานตัวดำเนินการตรรกะ and",
                "if a > 0 && b > 0:",
                new String[]{"if a > 0 and b > 0:", "if a > 0 & b > 0:", "if a > 0 AND b > 0:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 38. การใช้งานตัวดำเนินการตรรกะ or
        list.add(new Question("38. การใช้งานตัวดำเนินการตรรกะ or",
                "if a == 1 || b == 1:",
                new String[]{"if a == 1 or b == 1:", "if a == 1 | b == 1:", "if a == 1 OR b == 1:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 39. การใช้งานตัวดำเนินการตรรกะ not
        list.add(new Question("39. การใช้งานตัวดำเนินการตรรกะ not",
                "if !flag:",
                new String[]{"if not flag:", "if ~flag:", "if NOT flag:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 40. การสร้างเซต (Set) ข้อมูลที่ไม่ซ้ำกัน
        list.add(new Question("40. การสร้างเซต (Set) ข้อมูลที่ไม่ซ้ำกัน",
                "s = [1, 1, 2] (unique set)",
                new String[]{"s = set([1, 1, 2])", "s = unique[1, 1, 2]", "s = (1, 1, 2).unique()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 41. การใช้เมธอด add เพิ่มข้อมูลลงใน Set
        list.add(new Question("41. การใช้เมธอด add เพิ่มข้อมูลลงใน Set",
                "s = {1}\ns.append(2)",
                new String[]{"s.add(2)", "s.push(2)", "s.insert(2)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 42. การกำหนดพารามิเตอร์เริ่มต้นให้ฟังก์ชัน (Default Argument)
        list.add(new Question("42. การกำหนดพารามิเตอร์เริ่มต้นให้ฟังก์ชัน (Default Argument)",
                "def greet(name = \"Bot\"):\n    pass",
                new String[]{"def greet(name = \"Bot\"):", "def greet(name := \"Bot\"):", "def greet(optional name = \"Bot\"):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 43. การใช้ pass เป็นคำสั่งว่างเปล่าชั่วคราว
        list.add(new Question("43. การใช้ pass เป็นคำสั่งว่างเปล่าชั่วคราว",
                "def todo():\n    # empty function",
                new String[]{"pass", "nil", "continue"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 44. การใช้งานเมธอด upper() แปลงข้อความตัวพิมพ์ใหญ่
        list.add(new Question("44. การใช้งานเมธอด upper() แปลงข้อความตัวพิมพ์ใหญ่",
                "s = \"bot\".to_upper()",
                new String[]{"s = \"bot\".upper()", "s = upper(\"bot\")", "s = \"bot\".toUpperCase()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 45. การใช้งานเมธอด lower() แปลงข้อความตัวพิมพ์เล็ก
        list.add(new Question("45. การใช้งานเมธอด lower() แปลงข้อความตัวพิมพ์เล็ก",
                "s = \"BOT\".to_lower()",
                new String[]{"s = \"BOT\".lower()", "s = lower(\"BOT\")", "s = \"BOT\".toLowerCase()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 46. การใช้เมธอด strip() ตัดช่องว่างหัวท้ายสตริง
        list.add(new Question("46. การใช้เมธอด strip() ตัดช่องว่างหัวท้ายสตริง",
                "s = \"  bot  \".trim()",
                new String[]{"s = \"  bot  \".strip()", "s = \"  bot  \".clean()", "s = trim(\"  bot  \")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 47. การใช้งานเมธอด split() แยกข้อความสตริงเป็นลิสต์
        list.add(new Question("47. การใช้งานเมธอด split() แยกข้อความสตริงเป็นลิสต์",
                "words = \"a,b,c\".explode(\",\")",
                new String[]{"words = \"a,b,c\".split(\",\")", "words = split(\"a,b,c\", \",\")", "words = \"a,b,c\".tokens()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 48. การใช้งานเมธอด join() รวมลิสต์สตริงเป็นข้อความเดียว
        list.add(new Question("48. การใช้งานเมธอด join() รวมลิสต์สตริงเป็นข้อความเดียว",
                "text = [\"a\", \"b\"].concat(\",\")",
                new String[]{"text = \",\".join([\"a\", \"b\"])", "text = join([\"a\", \"b\"], \",\")", "text = [\"a\", \"b\"].join(\",\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 49. การใช้ del ลบตัวแปรหรือสมาชิกในลิสต์
        list.add(new Question("49. การใช้ del ลบตัวแปรหรือสมาชิกในลิสต์",
                "l = [1, 2, 3]\nremove l[0]",
                new String[]{"del l[0]", "delete l[0]", "erase l[0]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 50. การใช้อินเดนเทชันภายในบล็อกคำสั่ง if
        list.add(new Question("50. การใช้อินเดนเทชันภายในบล็อกคำสั่ง if",
                "if True:\nprint('A')",
                new String[]{"if True:\nprint('A');", "if True:\n    print('A')", "if True:\n{ print('A') }"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // NORMAL
// 1. KeyError เวลาดึงค่าจาก Dictionary ด้วย key ตรงๆ
        list.add(new Question("1. KeyError เวลาดึงค่าจาก Dictionary ด้วย key ตรงๆ",
                "d = {'a': 1}\nprint(d['b'])",
                new String[]{"print(d.get('b'))", "print(d.index('b'))", "print(d.find('b'))"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 2. วนลูป Dictionary ได้ทั้ง Key และ Value ด้วย items()
        list.add(new Question("2. วนลูป Dictionary ได้ทั้ง Key และ Value ด้วย items()",
                "for k, v in d:\n    print(v)",
                new String[]{"for k, v in d.items():", "for k, v in d.values():", "for k, v in d.keys():"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 3. แก้ไขค่า Global Variable ภายในฟังก์ชัน
        list.add(new Question("3. แก้ไขค่า Global Variable ภายในฟังก์ชัน",
                "x = 0\ndef fix():\n    x += 1",
                new String[]{"def fix():\n    global x\n    x += 1", "def fix():\n    super x\n    x += 1", "def fix(x):\n    x += 1"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 4. Mutable Default Argument (ตัวแปร List จำค่าเดิม)
        list.add(new Question("4. Mutable Default Argument (ตัวแปร List จำค่าเดิม)",
                "def add(l=[]):\n    l.append(1)",
                new String[]{"def add(l=None):\n    if l is None: l = []", "def add(l=list()):", "def add(l={}):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 5. จัดการเปิดและปิดไฟล์อย่างปลอดภัย (Context Manager)
        list.add(new Question("5. จัดการเปิดและปิดไฟล์อย่างปลอดภัย (Context Manager)",
                "f = open('log.txt')\nf.write('Error')",
                new String[]{"with open('log.txt', 'w') as f:\n    f.write('Error')", "file = open('log.txt')\nfile.close", "f = open('log.txt', 'write')"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 6. ลบสมาชิกใน Dictionary ขณะวนลูป
        list.add(new Question("6. ลบสมาชิกใน Dictionary ขณะวนลูป",
                "for k in d:\n    del d[k]",
                new String[]{"for k in list(d.keys()):\n    del d[k]", "for k in d.keys():\n    del d[k]", "for k in d.items():\n    del d[k]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 7. ปัญหาขอบเขตตัวแปร UnboundLocalError
        list.add(new Question("7. ปัญหาขอบเขตตัวแปร UnboundLocalError",
                "x = 10\ndef f():\n    print(x)\n    x = 5",
                new String[]{"def f():\n    global x\n    print(x)\n    x = 5", "def f():\n    local x\n    print(x)", "def f():\n    nonlocal x"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 8. Shallow Copy กับ List ซ้อน List ด้วย copy()
        list.add(new Question("8. Shallow Copy กับ List ซ้อน List ด้วย copy()",
                "a = [[1]]\nb = a.copy()\nb[0][0] = 9",
                new String[]{"import copy\nb = copy.deepcopy(a)", "b = a.shallow_copy()", "b = a[:]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 9. ลืมใส่ตัว f หน้า f-string
        list.add(new Question("9. ลืมใส่ตัว f หน้า f-string",
                "name = \"Bot\"\nprint(\"Hello {name}\")",
                new String[]{"name = \"Bot\"\nprint(f\"Hello {name}\")", "name = \"Bot\"\nprint(\"Hello %name\")", "name = \"Bot\"\nprint(\"Hello $name\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 10. การใช้ Generator ซ้ำหลังจากข้อมูลหมดแล้ว
        list.add(new Question("10. การใช้ Generator ซ้ำหลังจากข้อมูลหมดแล้ว",
                "g = (x for x in range(3))\nlist(g)\nprint(list(g))",
                new String[]{"g = [x for x in range(3)]\nlist(g)\nprint(list(g))", "g = (x for x in range(3))\nlist(g).reset()", "g = (x for x in range(3))\nprint(next(g))"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 11. การใช้ enumerate ในลูปฟอร์เพื่อเอา Index
        list.add(new Question("11. การใช้ enumerate ในลูปฟอร์เพื่อเอา Index",
                "for i, val in [10, 20]:\n    print(i)",
                new String[]{"for i, val in enumerate([10, 20]):", "for i, val in range([10, 20]):", "for i, val in index([10, 20]):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 12. การใช้ zip วนลูปขนานหลายลิสต์
        list.add(new Question("12. การใช้ zip วนลูปขนานหลายลิสต์",
                "for a, b in [1, 2], [3, 4]:",
                new String[]{"for a, b in zip([1, 2], [3, 4]):", "for a, b in combine([1, 2], [3, 4]):", "for a, b in parallel([1, 2], [3, 4]):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 13. การสร้าง List Comprehension พื้นฐาน
        list.add(new Question("13. การสร้าง List Comprehension พื้นฐาน",
                "res = [x * 2 for x in range(3)]",
                new String[]{"res = [x * 2 for x in range(3)]", "res = [for x in range(3): x * 2]", "res = map(x * 2, range(3))"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 14. การใช้ฟังก์ชัน map()
        list.add(new Question("14. การใช้ฟังก์ชัน map()",
                "res = map(lambda x: x*2, [1, 2, 3])",
                new String[]{"res = list(map(lambda x: x*2, [1, 2, 3]))", "res = map(lambda x: x*2, [1, 2, 3]).to_list()", "res = [map(lambda x: x*2, [1, 2, 3])]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 15. การใช้ฟังก์ชัน filter()
        list.add(new Question("15. การใช้ฟังก์ชัน filter()",
                "res = filter(lambda x: x>0, [-1, 0, 1])",
                new String[]{"res = list(filter(lambda x: x>0, [-1, 0, 1]))", "res = filter(lambda x: x>0, [-1, 0, 1]).getList()", "res = [filter(lambda x: x>0)]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 16. การสร้างฟังก์ชันนิรนามด้วย lambda
        list.add(new Question("16. การสร้างฟังก์ชันนิรนามด้วย lambda",
                "f = function(x): x + 1",
                new String[]{"f = lambda x: x + 1", "f = def(x): return x + 1", "f = anon x: x + 1"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 17. การใช้เมธอด update() รวม Dictionary
        list.add(new Question("17. การใช้เมธอด update() รวม Dictionary",
                "d1 = {1: 'a'}\nd1.merge({2: 'b'})",
                new String[]{"d1.update({2: 'b'})", "d1.join({2: 'b'})", "d1.append({2: 'b'})"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 18. การใช้เมธอด pop() ของลิสต์
        list.add(new Question("18. การใช้เมธอด pop() ของลิสต์",
                "l = [1, 2, 3]\nval = l.pop(1)",
                new String[]{"val = l.pop(1)", "val = l.remove(1)", "val = l.delete(1)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 19. การใช้เมธอด insert() แทรกข้อมูลลงในลิสต์
        list.add(new Question("19. การใช้เมธอด insert() แทรกข้อมูลลงในลิสต์",
                "l = [1, 3]\nl.insert(1, 2)",
                new String[]{"l.insert(1, 2)", "l.add(1, 2)", "l.push(1, 2)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 20. การใช้เมธอด count() นับจำนวนสมาชิกในลิสต์
        list.add(new Question("20. การใช้เมธอด count() นับจำนวนสมาชิกในลิสต์",
                "c = [1, 2, 2, 3].occurrences(2)",
                new String[]{"c = [1, 2, 2, 3].count(2)", "c = count([1, 2, 2, 3], 2)", "c = [1, 2, 2, 3].frequency(2)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 21. การใช้เมธอด index() หาตำแหน่ง Index ในลิสต์
        list.add(new Question("21. การใช้เมธอด index() หาตำแหน่ง Index ในลิสต์",
                "idx = [10, 20, 30].find(20)",
                new String[]{"idx = [10, 20, 30].index(20)", "idx = [10, 20, 30].search(20)", "idx = search_index([10, 20, 30], 20)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 22. การใช้ฟังก์ชัน sorted() เรียงลำดับข้อมูล
        list.add(new Question("22. การใช้ฟังก์ชัน sorted() เรียงลำดับข้อมูล",
                "new_l = [3, 1, 2].sort()",
                new String[]{"new_l = sorted([3, 1, 2])", "new_l = [3, 1, 2].order()", "new_l = arrange([3, 1, 2])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 23. การใช้เมธอด sort() เรียงลำดับข้อมูลในลิสต์เดิม
        list.add(new Question("23. การใช้เมธอด sort() เรียงลำดับข้อมูลในลิสต์เดิม",
                "l = [3, 1, 2]\nsorted(l)",
                new String[]{"l.sort()", "l.order()", "l.arrange()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 24. การใช้ฟังก์ชัน reversed() พลิกกลับลำดับข้อมูล
        list.add(new Question("24. การใช้ฟังก์ชัน reversed() พลิกกลับลำดับข้อมูล",
                "r = [1, 2, 3].reverse_new()",
                new String[]{"r = list(reversed([1, 2, 3]))", "r = [1, 2, 3].inverse()", "r = reverse([1, 2, 3])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 25. การใช้เมธอด reverse() พลิกกลับลำดับข้อมูลในลิสต์เดิม
        list.add(new Question("25. การใช้เมธอด reverse() พลิกกลับลำดับข้อมูลในลิสต์เดิม",
                "l = [1, 2, 3]\nl.invert()",
                new String[]{"l.reverse()", "l.flip()", "l.back()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 26. Tuple Unpacking เบื้องต้น
        list.add(new Question("26. Tuple Unpacking เบื้องต้น",
                "a, b = (1, 2, 3)",
                new String[]{"ValueError: too many values to unpack", "a = 1, b = 2", "Works normally"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 27. การใช้ *args สำหรับ Variable-length Arguments
        list.add(new Question("27. การใช้ *args สำหรับ Variable-length Arguments",
                "def func(*args): pass",
                new String[]{"Accepts any number of positional arguments", "Accepts only keyword arguments", "Accepts fixed 2 arguments"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 28. การใช้ **kwargs สำหรับ Keyword Arguments
        list.add(new Question("28. การใช้ **kwargs สำหรับ Keyword Arguments",
                "def func(**kwargs): pass",
                new String[]{"Accepts any number of keyword arguments", "Accepts positional only", "Accepts lists only"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 29. การเปิดไฟล์โหมดอ่านไบนารี ('rb')
        list.add(new Question("29. การเปิดไฟล์โหมดอ่านไบนารี ('rb')",
                "f = open('img.png', 'read_binary')",
                new String[]{"f = open('img.png', 'rb')", "f = open('img.png', 'bin')", "f = open('img.png', 'rB')"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 30. การอ่านบรรทัดไฟล์ทั้งหมดด้วย readlines()
        list.add(new Question("30. การอ่านบรรทัดไฟล์ทั้งหมดด้วย readlines()",
                "lines = f.read_all_lines()",
                new String[]{"lines = f.readlines()", "lines = f.getLines()", "lines = f.loadLines()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 31. การใช้งานโมดูล os สำหรับตรวจสอบเส้นทาง
        list.add(new Question("31. การใช้งานโมดูล os สำหรับตรวจสอบเส้นทาง",
                "import os\nexists = os.path.exists('file.txt')",
                new String[]{"import os\nexists = os.path.exists('file.txt')", "import os\nexists = os.exists('file.txt')", "import os\nexists = file.exists('file.txt')"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 32. การใช้งานโมดูล sys อ่านพารามิเตอร์ Command Line
        list.add(new Question("32. การใช้งานโมดูล sys อ่านพารามิเตอร์ Command Line",
                "import sys\nargs = sys.arguments",
                new String[]{"import sys\nargs = sys.argv", "import sys\nargs = sys.parameters", "import sys\nargs = sys.args"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 33. การใช้งานโมดูล math คำนวณค่ารากที่สอง
        list.add(new Question("33. การใช้งานโมดูล math คำนวณค่ารากที่สอง",
                "import math\nval = math.root(16)",
                new String[]{"import math\nval = math.sqrt(16)", "import math\nval = math.sqr(16)", "import math\nval = math.power(16, 0.5)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 34. การใช้งานโมดูล random สุ่มตัวเลขช่วง
        list.add(new Question("34. การใช้งานโมดูล random สุ่มตัวเลขช่วง",
                "import random\nn = random.randInt(1, 10)",
                new String[]{"import random\nn = random.randint(1, 10)", "import random\nn = random.randomInt(1, 10)", "import random\nn = random.between(1, 10)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 35. การใช้งานโมดูล datetime ดึงเวลาปัจจุบัน
        list.add(new Question("35. การใช้งานโมดูล datetime ดึงเวลาปัจจุบัน",
                "from datetime import datetime\nnow = datetime.getNow()",
                new String[]{"from datetime import datetime\nnow = datetime.now()", "from datetime import datetime\nnow = datetime.current()", "from datetime import datetime\nnow = datetime.todayTime()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 36. การใช้เมธอด find ค้นหาตำแหน่งข้อความ
        list.add(new Question("36. การใช้เมธอด find ค้นหาตำแหน่งข้อความ",
                "pos = \"Robot\".search(\"o\")",
                new String[]{"pos = \"Robot\".find(\"o\")", "pos = \"Robot\".locate(\"o\")", "pos = \"Robot\".index_of(\"o\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 37. การใช้เมธอด replace แทนที่ข้อความ
        list.add(new Question("37. การใช้เมธอด replace แทนที่ข้อความ",
                "new_s = \"Hello Bot\".sub(\"Bot\", \"Rob\")",
                new String[]{"new_s = \"Hello Bot\".replace(\"Bot\", \"Rob\")", "new_s = \"Hello Bot\".change(\"Bot\", \"Rob\")", "new_s = \"Hello Bot\".swap(\"Bot\", \"Rob\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 38. การตรวจสอบสตริงด้วย isdigit()
        list.add(new Question("38. การตรวจสอบสตริงด้วย isdigit()",
                "is_num = \"123\".is_number()",
                new String[]{"is_num = \"123\".isdigit()", "is_num = \"123\".isNumeric()", "is_num = is_digit(\"123\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 39. การใช้คำสั่ง raise โยนข้อผิดพลาด
        list.add(new Question("39. การใช้คำสั่ง raise โยนข้อผิดพลาด",
                "throw ValueError(\"Invalid\")",
                new String[]{"raise ValueError(\"Invalid\")", "signal ValueError(\"Invalid\")", "exception ValueError(\"Invalid\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 40. การสร้าง Custom Exception
        list.add(new Question("40. การสร้าง Custom Exception",
                "class MyError(Error): pass",
                new String[]{"class MyError(Exception): pass", "class MyError extends Exception: pass", "class MyError throws Exception: pass"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 41. การใช้ assert ตรวจสอบเงื่อนไข
        list.add(new Question("41. การใช้ assert ตรวจสอบเงื่อนไข",
                "verify x > 0",
                new String[]{"assert x > 0", "check x > 0", "test x > 0"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 42. การใช้ Ellipsis (...) ในโครงสร้างโค้ด
        list.add(new Question("42. การใช้ Ellipsis (...) ในโครงสร้างโค้ด",
                "def stub():\n    ...",
                new String[]{"Valid placeholder syntax", "Syntax error", "Infinite loop trigger"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 43. การเช็กสมาชิกใน Set ด้วย in
        list.add(new Question("43. การเช็กสมาชิกใน Set ด้วย in",
                "s = {1, 2}\nif 1 in s:",
                new String[]{"Valid and highly optimized O(1) average lookup", "O(N) linear lookup like list", "Invalid syntax for sets"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 44. การใช้ pass ในโครงสร้างคลาสว่าง
        list.add(new Question("44. การใช้ pass ในโครงสร้างคลาสว่าง",
                "class EmptyClass:\n    pass",
                new String[]{"Valid way to define an empty class block", "Syntax error", "Creates a None instance"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 45. การใช้งาน dictionary comprehension
        list.add(new Question("45. การใช้งาน dictionary comprehension",
                "d = {x: x*2 for x in range(3)}",
                new String[]{"Valid dictionary comprehension syntax producing {0: 0, 1: 2, 2: 4}", "Syntax error", "Produces a list instead"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 46. การใช้งาน set comprehension
        list.add(new Question("46. การใช้งาน set comprehension",
                "s = {x for x in [1, 1, 2]}",
                new String[]{"Valid set comprehension producing unique items {1, 2}", "Syntax error", "Produces a tuple"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 47. การใช้สตริงหลายบรรทัดด้วย Triple Quotes
        list.add(new Question("47. การใช้สตริงหลายบรรทัดด้วย Triple Quotes",
                "text = \"\"\"Line 1\nLine 2\"\"\"",
                new String[]{"Valid multi-line string syntax", "Syntax error", "Treated as comment"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 48. การใช้อาร์กิวเมนต์แบบ Keyword-only ใน Python 3
        list.add(new Question("48. การใช้อาร์กิวเมนต์แบบ Keyword-only ใน Python 3",
                "def f(a, *, b): pass",
                new String[]{"b must be passed as keyword argument", "b is optional positional", "Syntax error in Python 3"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 49. การใช้เดคอเรเตอร์พื้นฐาน @staticmethod
        list.add(new Question("49. การใช้เดคอเรเตอร์พื้นฐาน @staticmethod",
                "class A:\n    @staticmethod\n    def f(): pass",
                new String[]{"Defines a static method not requiring self or cls", "Requires self parameter", "Invalid decorator"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 50. การใช้เดคอเรเตอร์ @classmethod
        list.add(new Question("50. การใช้เดคอเรเตอร์ @classmethod",
                "class A:\n    @classmethod\n    def f(cls): pass",
                new String[]{"Receives class cls as first argument", "Receives self as first argument", "Requires no arguments"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // HARD
// 1. Lambda Closure (ปัญหาตัวแปร i ในลูป)
        list.add(new Question("1. Lambda Closure (ปัญหาตัวแปร i ในลูป)",
                "funcs = [lambda: i for i in range(3)]",
                new String[]{"funcs = [lambda i=i: i for i in range(3)]", "funcs = [lambda(i): i for i in range(3)]", "funcs = [lambda i: i for i in range(3)]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 2. สร้าง 2D List แต่ Reference เดียวกันหมดด้วย *
        list.add(new Question("2. สร้าง 2D List แต่ Reference เดียวกันหมดด้วย *",
                "matrix = [[0]] * 3\nmatrix[0][0] = 1",
                new String[]{"matrix = [[0] for _ in range(3)]", "matrix = [[0]] ** 3", "matrix = Array(3, [0])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 3. แก้ไข Tuple ที่มี List ซ้อนอยู่ข้างใน
        list.add(new Question("3. แก้ไข Tuple ที่มี List ซ้อนอยู่ข้างใน",
                "t = (1, [2])\nt[1] += [3]",
                new String[]{"t[1].extend([3])", "t[1] = [2, 3]", "t.append([3])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 4. ใช้งาน __slots__ ไม่ครบตามตัวแปรที่ประกาศ
        list.add(new Question("4. ใช้งาน __slots__ ไม่ครบตามตัวแปรที่ประกาศ",
                "class A:\n    __slots__ = ['x']\n    def __init__(self):\n        self.y = 1",
                new String[]{"__slots__ = ['x', 'y']", "self.x = 1", "del self.y"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 5. แชร์ Class Attribute โดยไม่ตั้งใจผ่าน Class Body
        list.add(new Question("5. แชร์ Class Attribute โดยไม่ตั้งใจผ่าน Class Body",
                "class Bot:\n    items = []",
                new String[]{"class Bot:\n    def __init__(self):\n        self.items = []", "class Bot:\n    static items = []", "class Bot:\n    private items = []"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 6. Decorator ทำลายข้อมูล Metadata ของฟังก์ชัน
        list.add(new Question("6. Decorator ทำลายข้อมูล Metadata ของฟังก์ชัน",
                "def my_dec(f):\n    def wrapper():\n        return f()\n    return wrapper",
                new String[]{"from functools import wraps\ndef my_dec(f):\n    @wraps(f)\n    def wrapper():\n        return f()\n    return wrapper", "def my_dec(f):\n    @staticmethod\n    def wrapper():", "def my_dec(f):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 7. จับ Exception แบบกว้างเกินไปแล้วปล่อยเงียบ (Swallowing Exceptions)
        list.add(new Question("7. จับ Exception แบบกว้างเกินไปแล้วปล่อยเงียบ (Swallowing Exceptions)",
                "try:\n    do_something()\nexcept:\n    pass",
                new String[]{"try:\n    do_something()\nexcept Exception as e:\n    logger.error(e)\n    raise", "try:\n    do_something()\ncatch Exception:", "try:\n    do_something()\nexcept Error:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 8. ลืมใช้ await หน้า Async Function
        list.add(new Question("8. ลืมใช้ await หน้า Async Function",
                "async def main():\n    fetch_data()",
                new String[]{"async def main():\n    await fetch_data()", "async def main():\n    async fetch_data()", "async def main():\n    yield fetch_data()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 9. แก้ไข List ขณะกำลังทำ List Comprehension ที่มี Side Effect
        list.add(new Question("9. แก้ไข List ขณะกำลังทำ List Comprehension ที่มี Side Effect",
                "lst = [1, 2]\n[lst.append(x) for x in range(3)]",
                new String[]{"lst = [1, 2]\nlst.extend(range(3))", "lst = [1, 2]\nlst.add(range(3))", "lst = [1, 2]\n[lst.push(x) for x in range(3)]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 10. ลบสมาชิกใน List ขณะวนลูปตัวเองโดยไม่สำเนา
        list.add(new Question("10. ลบสมาชิกใน List ขณะวนลูปตัวเองโดยไม่สำเนา",
                "for i in lst:\n    lst.remove(i)",
                new String[]{"for i in lst[:]:\n    lst.remove(i)", "for i in lst.reverse():\n    lst.remove(i)", "for i in lst.pop():\n    lst.remove(i)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 11. การใช้ Descriptor ปรับแต่งการเข้าถึงแอตทริบิวต์
        list.add(new Question("11. การใช้ Descriptor ปรับแต่งการเข้าถึงแอตทริบิวต์",
                "class Desc:\n    def __get__(self, instance, owner): pass",
                new String[]{"Implements descriptor protocol (__get__, __set__, __delete__)", "Requires only __init__", "Is deprecated in Python 3"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 12. การใช้ Metaclass ควบคุมการสร้างคลาส
        list.add(new Question("12. การใช้ Metaclass ควบคุมการสร้างคลาส",
                "class Meta(type): pass",
                new String[]{"Metaclass inherits from type and customizes class creation", "Metaclass inherits from object only", "Metaclass is identical to decorator"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 13. การใช้ Property Decorator
        list.add(new Question("13. การใช้ Property Decorator",
                "class A:\n    @property\n    def x(self): return self._x",
                new String[]{"Allows method access like a normal attribute (obj.x)", "Makes attribute private and inaccessible", "Converts method to static"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 14. การใช้งาน Abstract Base Classes (ABC)
        list.add(new Question("14. การใช้งาน Abstract Base Classes (ABC)",
                "from abc import ABC, abstractmethod",
                new String[]{"Enforces subclassing implementation of abstract methods", "Optional documentation only", "Prevents class inheritance"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 15. Type Hinting และ Static Type Checking
        list.add(new Question("15. Type Hinting และ Static Type Checking",
                "def add(a: int, b: int) -> int: return a + b",
                new String[]{"Provides hints for type checkers like mypy, enforced optionally", "Enforces runtime strict type errors strictly", "Disables dynamic typing completely"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 16. การใช้ __getitem__ เพื่อทำ Custom Container
        list.add(new Question("16. การใช้ __getitem__ เพื่อทำ Custom Container",
                "def __getitem__(self, index): pass",
                new String[]{"Enables indexing support (obj[key])", "Enables iteration only", "Enables deletion"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 17. การใช้ __iter__ และ __next__ ทำ Custom Iterator
        list.add(new Question("17. การใช้ __iter__ และ __next__ ทำ Custom Iterator",
                "def __iter__(self): return self",
                new String[]{"Required magic methods for iterator protocol", "Optional methods for lists", "Causes infinite recursion"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 18. การใช้ __enter__ และ __exit__ ทำ Custom Context Manager
        list.add(new Question("18. การใช้ __enter__ และ __exit__ ทำ Custom Context Manager",
                "def __exit__(self, exc_type, exc_val, exc_tb): pass",
                new String[]{"Enables usage with 'with' statement for resource cleanup", "Handles file exceptions globally", "Defines class destructor"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 19. ปัญหา Circular Import ระหว่างโมดูล
        list.add(new Question("19. ปัญหา Circular Import ระหว่างโมดูล",
                "import moduleA inside moduleB and vice versa at top-level",
                new String[]{"Causes ImportError / AttributeError at runtime due to uninitialized modules", "Resolved automatically by interpreter", "Encouraged best practice"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 20. Global Interpreter Lock (GIL) และ Threading
        list.add(new Question("20. Global Interpreter Lock (GIL) และ Threading",
                "Python's GIL allows true multi-core parallel execution for CPU-bound threads",
                new String[]{"False: GIL prevents multiple native threads from executing Python bytecodes simultaneously", "True: Full parallel execution across all CPU cores", "GIL only applies to network I/O"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 21. multiprocessing สำหรับ CPU-bound tasks
        list.add(new Question("21. multiprocessing สำหรับ CPU-bound tasks",
                "multiprocessing module bypasses GIL by using separate processes",
                new String[]{"True: Each process has its own Python interpreter and memory space", "False: Shares same memory space like threads", "Only works on Windows"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 22. concurrent.futures ThreadPoolExecutor
        list.add(new Question("22. concurrent.futures ThreadPoolExecutor",
                "executor = ThreadPoolExecutor(max_workers=4)",
                new String[]{"Ideal for I/O-bound tasks running concurrently", "Ideal for CPU-bound matrix calculations", "Bypasses GIL for CPU tasks"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 23. asyncio Event Loop
        list.add(new Question("23. asyncio Event Loop",
                "asyncio.run(main())",
                new String[]{"Manages and runs async coroutines on a single-thread event loop", "Spawns multi-processing pool", "Blocks main thread synchronously"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 24. weakref โมดูลป้องกัน Memory Leak
        list.add(new Question("24. weakref โมดูลป้องกัน Memory Leak",
                "import weakref\nwr = weakref.ref(obj)",
                new String[]{"Creates a weak reference that does not increase object reference count", "Increases reference count by 2", "Deletes object immediately"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 25. collections.defaultdict
        list.add(new Question("25. collections.defaultdict",
                "from collections import defaultdict\nd = defaultdict(list)",
                new String[]{"Automatically initializes missing keys with default factory (e.g. list)", "Raises KeyError on missing key", "Is slower than regular dict always"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 26. collections.Counter
        list.add(new Question("26. collections.Counter",
                "from collections import Counter\nc = Counter(['a', 'b', 'a'])",
                new String[]{"Specialized dict subclass for counting hashable objects", "Counts lines in text files only", "Sorts items by value automatically"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 27. collections.namedtuple
        list.add(new Question("27. collections.namedtuple",
                "Point = namedtuple('Point', ['x', 'y'])",
                new String[]{"Creates lightweight tuple subclasses with named fields for readability", "Mutable tuple structure", "Dictionary replacement"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 28. collections.deque
        list.add(new Question("28. collections.deque",
                "from collections import deque\nq = deque([1, 2], maxlen=2)",
                new String[]{"Double-ended queue supporting O(1) appends and pops from both ends", "O(N) operations like list", "Fixed memory stack only"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 29. itertools.product
        list.add(new Question("29. itertools.product",
                "import itertools\nres = list(itertools.product([1, 2], [3, 4]))",
                new String[]{"Cartesian product of input iterables", "Scalar dot product", "Matrix multiplication"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 30. functools.lru_cache (Memoization)
        list.add(new Question("30. functools.lru_cache (Memoization)",
                "from functools import lru_cache\n@lru_cache(maxsize=None)",
                new String[]{"Caches function return values to optimize recursive/expensive calls", "Encrypts function data", "Limits recursion depth"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 31. functools.partial
        list.add(new Question("31. functools.partial",
                "from functools import partial\nnew_func = partial(func, 10)",
                new String[]{"Fixes a subset of function arguments to produce a new callable", "Splits function into two parts", "Deletes function arguments"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 32. functools.reduce
        list.add(new Question("32. functools.reduce",
                "from functools import reduce\nres = reduce(lambda x, y: x + y, [1, 2, 3])",
                new String[]{"Applies a function of two arguments cumulatively to sequence items to reduce to single value", "Filters sequence items", "Maps sequence items"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 33. dataclasses module
        list.add(new Question("33. dataclasses module",
                "from dataclasses import dataclass\n@dataclass\nclass Point: x: int; y: int",
                new String[]{"Automatically generates __init__, __repr__, __eq__ for data classes", "Manual boilerplate required", "Immutable tuple only"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 34. __bool__ and __len__ truthiness evaluation
        list.add(new Question("34. __bool__ and __len__ truthiness evaluation",
                "Python evaluates object truth value by checking __bool__ first, then __len__",
                new String[]{"True: Falls back to __len__ if __bool__ is not defined", "False: Checks __len__ first always", "Objects are always True"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 35. RecursionError and stack depth limit
        list.add(new Question("35. RecursionError and stack depth limit",
                "Exceeding maximum recursion depth raises RecursionError",
                new String[]{"True: Prevented by stack frame limits (default 1000)", "False: Infinite recursion is allowed", "Automatically converted to iteration"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 36. gc module control
        list.add(new Question("36. gc module control",
                "import gc\ngc.collect()",
                new String[]{"Explicitly triggers garbage collection of cyclic references", "Deletes all variables in memory", "Disables garbage collector"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 37. Cyclic Reference and __del__
        list.add(new Question("37. Cyclic Reference and __del__",
                "Objects with __del__ methods involved in cyclic references can leak memory in older Python versions",
                new String[]{"True: Garbage collector cannot determine safe destruction order easily", "False: Handled instantly without issues", "Only affects C extensions"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 38. super() and MRO (Method Resolution Order)
        list.add(new Question("38. super() and MRO (Method Resolution Order)",
                "super() delegates method calls to the next class in the Method Resolution Order",
                new String[]{"True: Follows C3 linearization algorithm", "False: Calls parent class hardcoded blindly", "Only works for single inheritance"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 39. Mixin Classes architecture
        list.add(new Question("39. Mixin Classes architecture",
                "Mixins provide modular method functionality to be inherited by multiple classes without standalone instantiation",
                new String[]{"True: Designed for composition via multiple inheritance", "False: Standalone base classes with full state", "Forbidden in Python"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 40. __init_subclass__ hook
        list.add(new Question("40. __init_subclass__ hook",
                "class Base:\n    def __init_subclass__(cls, **kwargs): pass",
                new String[]{"Called whenever a subclass is created, useful for plugin/registry architectures", "Called on every instance creation", "Deprecated syntax"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 41. __new__ vs __init__
        list.add(new Question("41. __new__ vs __init__",
                "__new__ is responsible for creating the instance, while __init__ initializes it",
                new String[]{"True: __new__ returns the new object instance before __init__ runs", "False: __init__ creates the instance", "They are identical methods"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 42. File encoding specification
        list.add(new Question("42. File encoding specification",
                "f = open('data.txt', 'r', encoding='utf-8')",
                new String[]{"Ensures proper character decoding across different platform default encodings", "Optional performance boost", "Forces binary read mode"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 43. struct module binary packing
        list.add(new Question("43. struct module binary packing",
                "import struct\npacked = struct.pack('i', 10)",
                new String[]{"Converts Python values to C-style byte strings according to format string", "Compresses file size", "Encrypts integer data"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 44. pickle module serialization security warning
        list.add(new Question("44. pickle module serialization security warning",
                "Unpickling untrusted data can execute arbitrary malicious code",
                new String[]{"True: Pickle format is inherently unsafe against arbitrary code execution", "False: Pickle is fully sandboxed and secure", "Only affects string types"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 45. eval() and exec() security risks
        list.add(new Question("45. eval() and exec() security risks",
                "Passing external unvalidated user input to eval() creates severe remote code execution vulnerabilities",
                new String[]{"True: Executes dynamic Python code with current permissions", "False: Automatically sanitizes input strings", "Only evaluates math expressions safely"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 46. Namespace packages (PEP 420)
        list.add(new Question("46. Namespace packages (PEP 420)",
                "Namespace packages allow splitting a single Python package across multiple directories without __init__.py",
                new String[]{"True: Supported natively since Python 3.3", "False: Requires __init__.py strictly in all directories", "Deprecated feature"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 47. Walrus operator (:=) assignment expression
        list.add(new Question("47. Walrus operator (:=) assignment expression",
                "if (n := len(a)) > 3:\n    print(n)",
                new String[]{"Assigns value to variable as part of a larger expression (Python 3.8+)", "Syntax error", "Equality comparison"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 48. Positional-only parameters (/)
        list.add(new Question("48. Positional-only parameters (/)",
                "def f(a, b, /, c): pass",
                new String[]{"Parameters before slash (/) must be specified positionally, not as keywords", "All parameters become keyword-only", "Syntax error"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 49. Generator delegation with yield from
        list.add(new Question("49. Generator delegation with yield from",
                "def gen():\n    yield from [1, 2, 3]",
                new String[]{"Delegates sub-generator iteration cleanly and efficiently", "Yields generator object itself", "Throws syntax error"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 50. __mro__ attribute inspection
        list.add(new Question("50. __mro__ attribute inspection",
                "print(MyClass.__mro__)",
                new String[]{"Returns a tuple of classes inspected in method resolution order", "Returns object instance count", "Returns parent class name string"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));
        // C
        //EASY
// 1. ตรวจสอบกฎพื้นฐานการปิดท้ายคำสั่งด้วย Semicolon
        list.add(new Question("1. ตรวจสอบกฎพื้นฐานการปิดท้ายคำสั่งด้วย Semicolon",
                "int x = 5",
                new String[]{"int x = 5;", "int x = 5:", "int x == 5;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 2. การใช้ฟังก์ชันพิมพ์ข้อความออกหน้าจอ printf
        list.add(new Question("2. การใช้ฟังก์ชันพิมพ์ข้อความออกหน้าจอ printf",
                "print(\"Hello\");",
                new String[]{"printf(\"Hello\");", "cout << \"Hello\";", "Console.WriteLine(\"Hello\");"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 3. การรับค่าตัวแปรด้วย scanf ขาดตัวดำเนินการใด
        list.add(new Question("3. การรับค่าตัวแปรด้วย scanf ขาดตัวดำเนินการใด",
                "int x; scanf(\"%d\", x);",
                new String[]{"scanf(\"%d\", &x);", "scanf(\"%d\", *x);", "scanf(\"%d\", x[]);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 4. การใช้งาน printf แสดงผลตัวแปรประเภท int
        list.add(new Question("4. การใช้งาน printf แสดงผลตัวแปรประเภท int",
                "int val = 10; printf(\"%f\", val);",
                new String[]{"printf(\"%d\", val);", "printf(\"%s\", val);", "printf(\"%c\", val);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 5. การประกาศไลบรารีมาตรฐานสำหรับ I/O
        list.add(new Question("5. การประกาศไลบรารีมาตรฐานสำหรับ I/O",
                "int main() { printf(\"Test\"); }",
                new String[]{"#include <stdio.h>", "#include <iostream>", "#include <conio.h>"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 6. รูปแบบประเภทของฟังก์ชัน main ที่ถูกต้อง
        list.add(new Question("6. รูปแบบประเภทของฟังก์ชัน main ที่ถูกต้อง",
                "void main() { return 0; }",
                new String[]{"int main() { return 0; }", "float main() { return 0; }", "char main() { return 0; }"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 7. เครื่องหมาย Single Quote สำหรับตัวอักษรเดี่ยว
        list.add(new Question("7. เครื่องหมาย Single Quote สำหรับตัวอักษรเดี่ยว",
                "char c = \"A\";",
                new String[]{"char c = 'A';", "char c = (char)\"A\";", "char c = *\"A\";"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 8. การเปรียบเทียบเงื่อนไขด้วย if ที่ถูกต้อง
        list.add(new Question("8. การเปรียบเทียบเงื่อนไขด้วย if ที่ถูกต้อง",
                "int x = 10; if (x = 5) { }",
                new String[]{"if (x == 5)", "if (x === 5)", "if (x => 5)"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 9. ตัวดำเนินการตรรกะ AND ในภาษา C
        list.add(new Question("9. ตัวดำเนินการตรรกะ AND ในภาษา C",
                "if (a > 0 & b > 0)",
                new String[]{"if (a > 0 && b > 0)", "if (a > 0 AND b > 0)", "if (a > 0 & b > 0)"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 10. ตัวดำเนินการตรรกะ OR ในภาษา C
        list.add(new Question("10. ตัวดำเนินการตรรกะ OR ในภาษา C",
                "if (a == 1 | b == 1)",
                new String[]{"if (a == 1 || b == 1)", "if (a == 1 OR b == 1)", "if (a == 1 | b == 1)"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 11. การประกาศอาเรย์ขนาดคงที่
        list.add(new Question("11. การประกาศอาเรย์ขนาดคงที่",
                "int arr(5) = {1, 2, 3, 4, 5};",
                new String[]{"int arr[5] = {1, 2, 3, 4, 5};", "int arr = new int[5];", "array int arr[5];"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 12. การเข้าถึงสมาชิกอาเรย์ช่องแรก
        list.add(new Question("12. การเข้าถึงสมาชิกอาเรย์ช่องแรก",
                "int arr[5] = {10, 20}; int x = arr[1];",
                new String[]{"int x = arr[0];", "int x = arr.first();", "int x = arr(-1);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 13. การวนลูป for ที่ถูกต้องตาม syntax
        list.add(new Question("13. การวนลูป for ที่ถูกต้องตาม syntax",
                "for (int i = 0, i < 10, i++) { }",
                new String[]{"for (int i = 0; i < 10; i++) { }", "for (int i = 0 to 10) { }", "for i = 0 to 10 { }"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 14. การวนลูป while พื้นฐาน
        list.add(new Question("14. การวนลูป while พื้นฐาน",
                "while x < 5 { x++; }",
                new String[]{"while (x < 5) { x++; }", "while [x < 5] do", "while x < 5 do { }"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 15. โครงสร้าง do-while ต้องปิดท้ายด้วยสิ่งใด
        list.add(new Question("15. โครงสร้าง do-while ต้องปิดท้ายด้วยสิ่งใด",
                "do { x++; } while (x < 5)",
                new String[]{"do { x++; } while (x < 5);", "do { x++; } until (x < 5);", "do { x++; } loop;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 16. คำสั่งออกจากลูปทันที
        list.add(new Question("16. คำสั่งออกจากลูปทันที",
                "for(int i=0; i<10; i++) { stop; }",
                new String[]{"break;", "exit;", "return 0;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 17. คำสั่งข้ามรอบปัจจุบันไปรอบถัดไป
        list.add(new Question("17. คำสั่งข้ามรอบปัจจุบันไปรอบถัดไป",
                "for(int i=0; i<10; i++) { skip; }",
                new String[]{"continue;", "next;", "pass;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 18. การใช้ switch-case ป้องกันการไหลผ่าน (Fall-through)
        list.add(new Question("18. การใช้ switch-case ป้องกันการไหลผ่าน (Fall-through)",
                "switch(x) { case 1: doSomething(); }",
                new String[]{"switch(x) { case 1: doSomething(); break; }", "switch(x) { case 1: doSomething(); return; }", "switch(x) { case 1: doSomething(); exit; }"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 19. การกำหนดค่าคงที่ด้วย preprocessor directive
        list.add(new Question("19. การกำหนดค่าคงที่ด้วย preprocessor directive",
                "variable MAX = 100;",
                new String[]{"#define MAX 100", "const int MAX = 100;", "define MAX 100;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 20. การแสดงผลตัวอักษร escape sequence ขึ้นบรรทัดใหม่
        list.add(new Question("20. การแสดงผลตัวอักษร escape sequence ขึ้นบรรทัดใหม่",
                "printf(\"Hello/n\");",
                new String[]{"printf(\"Hello\\n\");", "printf(\"Hello\\r\");", "printf(\"Hello\\t\");"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 21. ตัวแปรชนิดทศนิยมความแม่นยำสูง
        list.add(new Question("21. ตัวแปรชนิดทศนิยมความแม่นยำสูง",
                "float pi = 3.1415926535;",
                new String[]{"double pi = 3.1415926535;", "short pi = 3.1415926535;", "int pi = 3.1415926535;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 22. รูปแบบ format specifier สำหรับตัวเลขฐานสิบหก (Hex)
        list.add(new Question("22. รูปแบบ format specifier สำหรับตัวเลขฐานสิบหก (Hex)",
                "int x = 255; printf(\"%d\", x);",
                new String[]{"printf(\"%x\", x);", "printf(\"%o\", x);", "printf(\"%b\", x);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 23. การใช้ฟังก์ชันคำนวณค่าสัมบูรณ์ใน math.h
        list.add(new Question("23. การใช้ฟังก์ชันคำนวณค่าสัมบูรณ์ใน math.h",
                "int a = abs(-5);",
                new String[]{"#include <math.h>\nint a = abs(-5);", "#include <stdlib.h>\nint a = abs(-5);", "int a = fabs(-5);"},
                1, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 24. ตัวดำเนินการหารเอาเศษ (Modulo)
        list.add(new Question("24. ตัวดำเนินการหารเอาเศษ (Modulo)",
                "float r = 5.5 % 2.0;",
                new String[]{"int r = 5 % 2;", "float r = fmod(5.5, 2.0);", "int r = mod(5, 2);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 25. การเพิ่มค่าทีละ 1 แบบ Prefix
        list.add(new Question("25. การเพิ่มค่าทีละ 1 แบบ Prefix",
                "int x = 5; int y = ++x;",
                new String[]{"int x = 5; int y = ++x;", "int x = 5; int y = x++;", "int x = 5; ++(x);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 26. ขนาดของตัวแปร char ในหน่วยความจำปกติ
        list.add(new Question("26. ขนาดของตัวแปร char ในหน่วยความจำปกติ",
                "int s = size(char);",
                new String[]{"int s = sizeof(char);", "int s = lengthof(char);", "int s = sizeof(c);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 27. การคอมเมนต์แบบบรรทัดเดียว
        list.add(new Question("27. การคอมเมนต์แบบบรรทัดเดียว",
                "/ Comment line",
                new String[]{"// Comment line", "/* Comment line */", "# Comment line"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 28. การคอมเมนต์แบบหลายบรรทัด
        list.add(new Question("28. การคอมเมนต์แบบหลายบรรทัด",
                "// Line 1 \n // Line 2",
                new String[]{"/* Line 1 \n Line 2 */", "# Line 1 \n Line 2", "-- Line 1 \n Line 2"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 29. การประกาศตัวแปร pointer เบื้องต้น
        list.add(new Question("29. การประกาศตัวแปร pointer เบื้องต้น",
                "int ptr = &x;",
                new String[]{"int *ptr = &x;", "int ptr* = x;", "int &ptr = x;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 30. การอ่านค่าผ่าน dereference pointer
        list.add(new Question("30. การอ่านค่าผ่าน dereference pointer",
                "int val = *ptr;",
                new String[]{"int val = *ptr;", "int val = &ptr;", "int val = ptr;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 31. เงื่อนไข Ternary operator syntax
        list.add(new Question("31. เงื่อนไข Ternary operator syntax",
                "int m = (a > b) ? a : b;",
                new String[]{"int m = (a > b) ? a : b;", "int m = if (a > b) a else b;", "int m = a > b ? a; b;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 32. ตัวดำเนินการ Bitwise Shift ขวา
        list.add(new Question("32. ตัวดำเนินการ Bitwise Shift ขวา",
                "int res = x << 2;",
                new String[]{"int res = x >> 2;", "int res = x <<< 2;", "int res = shift_r(x, 2);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 33. ตัวดำเนินการ Bitwise XOR
        list.add(new Question("33. ตัวดำเนินการ Bitwise XOR",
                "int res = x EXOR y;",
                new String[]{"int res = x ^ y;", "int res = x ** y;", "int res = x &! y;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 34. ชนิดข้อมูลบูลีนใน C มาตรฐานใหม่ (stdbool.h)
        list.add(new Question("34. ชนิดข้อมูลบูลีนใน C มาตรฐานใหม่ (stdbool.h)",
                "bool flag = true;",
                new String[]{"#include <stdbool.h>\nbool flag = true;", "boolean flag = true;", "bit flag = 1;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 35. ฟังก์ชันอ่านตัวอักษรทีละตัวจากสตรีม
        list.add(new Question("35. ฟังก์ชันอ่านตัวอักษรทีละตัวจากสตรีม",
                "char c = readChar();",
                new String[]{"char c = getchar();", "char c = fgetc();", "char c = input();"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 36. ฟังก์ชันพิมพ์ข้อความพร้อมขึ้นบรรทัดใหม่อัตโนมัติ
        list.add(new Question("36. ฟังก์ชันพิมพ์ข้อความพร้อมขึ้นบรรทัดใหม่อัตโนมัติ",
                "printfln(\"Test\");",
                new String[]{"puts(\"Test\");", "print(\"Test\\n\");", "writeln(\"Test\");"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 37. การประกาศ Prototype ของฟังก์ชันก่อน main
        list.add(new Question("37. การประกาศ Prototype ของฟังก์ชันก่อน main",
                "void func();",
                new String[]{"void func();", "declare void func();", "function void func();"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 38. ตัวแปรขอบเขตไฟล์ (Global variable)
        list.add(new Question("38. ตัวแปรขอบเขตไฟล์ (Global variable)",
                "Outside main { int g = 5; }",
                new String[]{"int g = 5;\nint main() { }", "global int g = 5;", "public int g = 5;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 39. ตัวแปรภายในฟังก์ชัน (Local variable)
        list.add(new Question("39. ตัวแปรภายในฟังก์ชัน (Local variable)",
                "int main() { auto int x = 10; }",
                new String[]{"int main() { int x = 10; }", "local int x = 10;", "private int x = 10;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 40. การใช้อาเรย์ 2 มิติขนาด 3x3
        list.add(new Question("40. การใช้อาเรย์ 2 มิติขนาด 3x3",
                "int matrix(3)(3);",
                new String[]{"int matrix[3][3];", "int matrix[3, 3];", "matrix int[3][3];"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 41. การกำหนดค่าสตริงด้วยอาเรย์ตัวอักษร
        list.add(new Question("41. การกำหนดค่าสตริงด้วยอาเรย์ตัวอักษร",
                "char name[10] = \"Robot\";",
                new String[]{"char name[10] = \"Robot\";", "string name = \"Robot\";", "char name = \"Robot\";"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 42. การตรวจสอบการจบบล็อกโค้ดด้วยปีกกา
        list.add(new Question("42. การตรวจสอบการจบบล็อกโค้ดด้วยปีกกา",
                "int main() { printf(\"Hi\"); ",
                new String[]{"int main() { printf(\"Hi\"); }", "int main() { printf(\"Hi\"); ]", "int main() { printf(\"Hi\"); end;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 43. ฟังก์ชันแปลงข้อความตัวเลขเป็น int ใน stdlib.h
        list.add(new Question("43. ฟังก์ชันแปลงข้อความตัวเลขเป็น int ใน stdlib.h",
                "int n = stringToInt(\"123\");",
                new String[]{"int n = atoi(\"123\");", "int n = (int)\"123\";", "int n = parse_int(\"123\");"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 44. การใช้งาน macro กำหนดค่าคงที่คำนวณ
        list.add(new Question("44. การใช้งาน macro กำหนดค่าคงที่คำนวณ",
                "define SQUARE(x) x * x",
                new String[]{"#define SQUARE(x) ((x) * (x))", "#define SQUARE(x) = x * x;", "macro SQUARE(x) (x * x)"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 45. ตัวดำเนินการขนาดของตัวแปร (sizeof) คืนค่าชนิดใด
        list.add(new Question("45. ตัวดำเนินการขนาดของตัวแปร (sizeof) คืนค่าชนิดใด",
                "size_t sz = sizeof(int);",
                new String[]{"size_t (unsigned integer)", "float", "char*"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 46. การประกาศตัวแปรห้ามเปลี่ยนค่าด้วย const
        list.add(new Question("46. การประกาศตัวแปรห้ามเปลี่ยนค่าด้วย const",
                "changeable const int limit = 10;",
                new String[]{"const int limit = 10;", "constant int limit = 10;", "final int limit = 10;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 47. การใช้ format specifier พิมพ์เลขทศนิยม 2 ตำแหน่ง
        list.add(new Question("47. การใช้ format specifier พิมพ์เลขทศนิยม 2 ตำแหน่ง",
                "printf(\"%f\", val);",
                new String[]{"printf(\"%.2f\", val);", "printf(\"%2d\", val);", "printf(\"%f.2\", val);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 48. รูปแบบการเขียนคำสั่งสลับทางเลือก default ใน switch
        list.add(new Question("48. รูปแบบการเขียนคำสั่งสลับทางเลือก default ใน switch",
                "switch(x) { else: break; }",
                new String[]{"switch(x) { default: break; }", "switch(x) { other: break; }", "switch(x) { none: break; }"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 49. คำสั่งรับข้อความสตริงยาวๆ จากคีย์บอร์ดอย่างปลอดภัย
        list.add(new Question("49. คำสั่งรับข้อความสตริงยาวๆ จากคีย์บอร์ดอย่างปลอดภัย",
                "gets(str);",
                new String[]{"fgets(str, sizeof(str), stdin);", "scanf(\"%s\", str);", "get_line(str);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // 50. ฟังก์ชันจบการทำงานโปรแกรมและคืนค่าสถานะ
        list.add(new Question("50. ฟังก์ชันจบการทำงานโปรแกรมและคืนค่าสถานะ",
                "terminate(0);",
                new String[]{"exit(0);", "stop(0);", "quit(0);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_EASY));

        // NORMAL
// 1. การจองหน่วยความจำแบบไดนามิกด้วย malloc
        list.add(new Question("1. การจองหน่วยความจำแบบไดนามิกด้วย malloc",
                "int *p = malloc(10 * sizeof(int));",
                new String[]{"int *p = (int *)malloc(10 * sizeof(int));", "int *p = new int[10];", "int *p = alloc(10);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 2. การคืนหน่วยความจำที่จองด้วย malloc
        list.add(new Question("2. การคืนหน่วยความจำที่จองด้วย malloc",
                "delete p;",
                new String[]{"free(p);", "dealloc(p);", "delete[] p;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 3. ป้องกัน Dangling Pointer หลัง free
        list.add(new Question("3. ป้องกัน Dangling Pointer หลัง free",
                "free(p);",
                new String[]{"free(p); p = NULL;", "delete p;", "p = 0;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 4. การกำหนดโครงสร้าง Struct ขาดสิ่งใดที่ท้ายบล็อก
        list.add(new Question("4. การกำหนดโครงสร้าง Struct ขาดสิ่งใดที่ท้ายบล็อก",
                "struct Point { int x; int y; }",
                new String[]{"struct Point { int x; int y; };", "struct Point { int x; int y; }", "struct Point [ int x, int y; ];"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 5. การเข้าถึงสมาชิก Struct ผ่านตัวแปร Pointer
        list.add(new Question("5. การเข้าถึงสมาชิก Struct ผ่านตัวแปร Pointer",
                "struct Point *pt; pt.x = 10;",
                new String[]{"pt->x = 10;", "pt.x = 10;", "(*pt).x = 10"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 6. การใช้ typedef กับ struct เพื่อความสะดวก
        list.add(new Question("6. การใช้ typedef กับ struct เพื่อความสะดวก",
                "struct Node { int data; }; Node n;",
                new String[]{"typedef struct Node { int data; } Node;", "struct Node typedef;", "typedef Node struct;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 7. การเปิดไฟล์สำหรับการเขียนข้อความ (Write mode)
        list.add(new Question("7. การเปิดไฟล์สำหรับการเขียนข้อความ (Write mode)",
                "FILE *f = fopen(\"data.txt\", \"r\");",
                new String[]{"FILE *f = fopen(\"data.txt\", \"w\");", "FILE *f = open(\"data.txt\", \"write\");", "FILE *f = write_open(\"data.txt\");"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 8. การตรวจสอบข้อผิดพลาดหลังเปิดไฟล์
        list.add(new Question("8. การตรวจสอบข้อผิดพลาดหลังเปิดไฟล์",
                "FILE *f = fopen(\"test.txt\", \"r\"); f.read();",
                new String[]{"if (f == NULL) { /* error */ }", "if (!file_exists(f))", "if (f_error(f))"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 9. การปิดไฟล์หลังใช้งานเสร็จ
        list.add(new Question("9. การปิดไฟล์หลังใช้งานเสร็จ",
                "FILE *f = fopen(\"test.txt\", \"r\"); /* work */",
                new String[]{"fclose(f);", "close(f);", "file_close(f);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 10. อาเรย์decay เป็นพอยน์เตอร์เมื่อส่งเข้าฟังก์ชัน
        list.add(new Question("10. อาเรย์decay เป็นพอยน์เตอร์เมื่อส่งเข้าฟังก์ชัน",
                "void test(int arr[]) { int sz = sizeof(arr); }",
                new String[]{"Pass size as a separate parameter", "sizeof works on arrays inside functions", "Use length attribute"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 11. การคัดลอกสตริงอย่างปลอดภัยด้วย strncpy
        list.add(new Question("11. การคัดลอกสตริงอย่างปลอดภัยด้วย strncpy",
                "strcpy(dest, src);",
                new String[]{"strncpy(dest, src, sizeof(dest) - 1); dest[sizeof(dest)-1] = '\\0';", "str_copy(dest, src);", "memcpy_s(dest, src);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 12. การต่อสตริงด้วย strcat ความเสี่ยง Buffer Overflow
        list.add(new Question("12. การต่อสตริงด้วย strcat ความเสี่ยง Buffer Overflow",
                "strcat(dest, src);",
                new String[]{"Ensure dest has enough allocated space and null-terminated", "strcat automatically resizes dest", "strcat uses heap allocation"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 13. การประกาศตัวแปร Pointer to Pointer
        list.add(new Question("13. การประกาศตัวแปร Pointer to Pointer",
                "int **pptr;",
                new String[]{"int **pptr;", "int *ptr*;", "int &*pptr;"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 14. การประกาศตัวชี้ฟังก์ชัน (Function Pointer)
        list.add(new Question("14. การประกาศตัวชี้ฟังก์ชัน (Function Pointer)",
                "int (*funcPtr)(int, int);",
                new String[]{"int (*funcPtr)(int, int);", "int *funcPtr(int, int);", "funcPtr int(int, int);"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 15. ตัวแปร static ภายในฟังก์ชันเก็บค่าอย่างไร
        list.add(new Question("15. ตัวแปร static ภายในฟังก์ชันเก็บค่าอย่างไร",
                "void count() { static int c = 0; c++; }",
                new String[]{"Retains its value between function calls", "Resets to 0 every call", "Becomes global to other files"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 16. ตัวแปร extern สำหรับเชื่อมโยงข้ามไฟล์
        list.add(new Question("16. ตัวแปร extern สำหรับเชื่อมโยงข้ามไฟล์",
                "extern int sharedVar;",
                new String[]{"Declares a variable defined in another translation unit", "Creates a new local variable", "Makes variable private"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 17. ความแตกต่างระหว่าง const int *p กับ int * const p
        list.add(new Question("17. ความแตกต่างระหว่าง const int *p กับ int * const p",
                "const int *p vs int * const p",
                new String[]{"Pointer to const data vs Const pointer to non-const data", "Both are identical", "Neither can be reassigned"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 18. การจองหน่วยความจำแบบ calloc ต่างจาก malloc อย่างไร
        list.add(new Question("18. การจองหน่วยความจำแบบ calloc ต่างจาก malloc อย่างไร",
                "int *p = calloc(10, sizeof(int));",
                new String[]{"Initialize allocated memory to zero", "Allocates double memory", "Is faster than malloc"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 19. การขยายขนาดหน่วยความจำเดิมด้วย realloc
        list.add(new Question("19. การขยายขนาดหน่วยความจำเดิมด้วย realloc",
                "p = realloc(p, 20 * sizeof(int));",
                new String[]{"Always check if return value is NULL before overwriting pointer", "realloc never moves memory block", "realloc frees old pointer automatically on failure"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 20. การคำนวณจำนวนสมาชิกในอาเรย์ด้วย sizeof
        list.add(new Question("20. การคำนวณจำนวนสมาชิกในอาเรย์ด้วย sizeof",
                "int arr[10]; int len = sizeof(arr)/sizeof(arr[0]);",
                new String[]{"Correct way to find static array length", "Only works inside functions", "Returns pointer size"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 21. การอ่านข้อมูลไบนารีจากไฟล์ด้วย fread
        list.add(new Question("21. การอ่านข้อมูลไบนารีจากไฟล์ด้วย fread",
                "fread(buffer, size, count, fp);",
                new String[]{"Reads binary blocks into buffer", "Reads formatted text lines", "Writes data to stream"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 22. การเขียนข้อมูลไบนารีลงไฟล์ด้วย fwrite
        list.add(new Question("22. การเขียนข้อมูลไบนารีลงไฟล์ด้วย fwrite",
                "fwrite(buffer, size, count, fp);",
                new String[]{"Writes binary blocks from buffer", "Prints string to stdout", "Appends text only"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 23. การเลื่อนตำแหน่งตัวชี้ไฟล์ด้วย fseek
        list.add(new Question("23. การเลื่อนตำแหน่งตัวชี้ไฟล์ด้วย fseek",
                "fseek(fp, 0, SEEK_SET);",
                new String[]{"Moves file position indicator", "Returns current file position", "Closes file stream"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 24. การเช็กจุดสิ้นสุดไฟล์ (EOF) ในลูปรับค่า
        list.add(new Question("24. การเช็กจุดสิ้นสุดไฟล์ (EOF) ในลูปรับค่า",
                "while(feof(fp)) { ... }",
                new String[]{"Better pattern: while((c = fgetc(fp)) != EOF)", "feof checks before read attempt correctly", "feof never causes infinite loops"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 25. การใช้งาน union เพื่อใช้หน่วยความจำร่วมกัน
        list.add(new Question("25. การใช้งาน union เพื่อใช้หน่วยความจำร่วมกัน",
                "union Data { int i; float f; };",
                new String[]{"All members share the same memory location", "Members are stored sequentially like struct", "Union size is sum of all members"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 26. การกำหนดค่าเริ่มต้น enum ในภาษา C
        list.add(new Question("26. การกำหนดค่าเริ่มต้น enum ในภาษา C",
                "enum Color { RED, GREEN, BLUE };",
                new String[]{"RED defaults to 0, GREEN to 1, BLUE to 2", "RED defaults to 1", "All members start at -1"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 27. การส่งพารามิเตอร์แบบ Call by Reference ใน C
        list.add(new Question("27. การส่งพารามิเตอร์แบบ Call by Reference ใน C",
                "void modify(int *x) { *x = 20; }",
                new String[]{"Pass address using & operator", "Pass value directly", "Use reference keyword & in signature like C++"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 28. การใช้ volatile สำหรับตัวแปรที่เปลี่ยนค่าได้ตลอดเวลา
        list.add(new Question("28. การใช้ volatile สำหรับตัวแปรที่เปลี่ยนค่าได้ตลอดเวลา",
                "volatile int flag = 0;",
                new String[]{"Prevents compiler optimizations on variable access", "Makes variable thread-safe automatically", "Stores variable in ROM"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 29. การใช้งานแมโครเงื่อนไข #ifdef และ #endif
        list.add(new Question("29. การใช้งานแมโครเงื่อนไข #ifdef และ #endif",
                "#ifdef DEBUG\n/* code */\n#endif",
                new String[]{"Conditional compilation directive", "Runtime exception handler", "Loop structure"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 30. ฟังก์ชันสุ่มตัวเลข rand() ต้องใช้งานร่วมกับอะไร
        list.add(new Question("30. ฟังก์ชันสุ่มตัวเลข rand() ต้องใช้งานร่วมกับอะไร",
                "int r = rand();",
                new String[]{"srand(time(NULL)); for initialization", "random_seed()", "math.h library"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 31. การเปรียบเทียบสตริงด้วย strcmp
        list.add(new Question("31. การเปรียบเทียบสตริงด้วย strcmp",
                "if (s1 == s2)",
                new String[]{"if (strcmp(s1, s2) == 0)", "if (s1.equals(s2))", "if (string_equals(s1, s2))"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 32. การใช้งาน offsetof macro จาก stddef.h
        list.add(new Question("32. การใช้งาน offsetof macro จาก stddef.h",
                "size_t off = offsetof(struct St, member);",
                new String[]{"Returns byte offset of a member within a struct", "Returns total struct size", "Returns pointer address"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 33. บิตฟิลด์ (Bitfields) ใน Struct สำหรับประหยัดแรม
        list.add(new Question("33. บิตฟิลด์ (Bitfields) ใน Struct สำหรับประหยัดแรม",
                "struct Status { unsigned int isReady : 1; };",
                new String[]{"Allocates specified number of bits for a member", "Allocates full bytes only", "Creates bitwise operations"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 34. การใช้ assert เพื่อตรวจสอบเงื่อนไข Debug
        list.add(new Question("34. การใช้ assert เพื่อตรวจสอบเงื่อนไข Debug",
                "assert(x > 0);",
                new String[]{"Aborts program if expression evaluates to false", "Throws a try-catch exception", "Prints warning and continues"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 35. ฟังก์ชัน sscanf สำหรับแยกสตริงตามรูปแบบ
        list.add(new Question("35. ฟังก์ชัน sscanf สำหรับแยกสตริงตามรูปแบบ",
                "sscanf(str, \"%d %s\", &id, name);",
                new String[]{"Reads formatted input from a string buffer", "Writes formatted output to string", "Compares two strings"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 36. การสร้างอาเรย์พอยน์เตอร์ตัวอักษร (Array of strings)
        list.add(new Question("36. การสร้างอาเรย์พอยน์เตอร์ตัวอักษร (Array of strings)",
                "char *titles[] = {\"A\", \"B\", \"C\"};",
                new String[]{"Array of string literals pointers", "2D char array fixed size", "Invalid syntax"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 37. ปัญหาการคืนค่าพอยน์เตอร์ชี้ไปตัวแปร Local
        list.add(new Question("37. ปัญหาการคืนค่าพอยน์เตอร์ชี้ไปตัวแปร Local",
                "int* get() { int x = 5; return &x; }",
                new String[]{"Dangling pointer hazard after function returns", "Valid memory management", "Allocates on heap automatically"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 38. การเคลียร์หน่วยความจำด้วย memset
        list.add(new Question("38. การเคลียร์หน่วยความจำด้วย memset",
                "memset(arr, 0, sizeof(arr));",
                new String[]{"Fills block of memory with a specified byte value", "Frees memory allocation", "Copies memory block"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 39. การคัดลอกหน่วยความจำด้วย memcpy
        list.add(new Question("39. การคัดลอกหน่วยความจำด้วย memcpy",
                "memcpy(dest, src, size);",
                new String[]{"Copies n bytes from src to dest (non-overlapping)", "Handles overlapping memory safely", "Compares memory bytes"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 40. การคัดลอกหน่วยความจำกรณีทับซ้อนด้วย memmove
        list.add(new Question("40. การคัดลอกหน่วยความจำกรณีทับซ้อนด้วย memmove",
                "memmove(dest, src, size);",
                new String[]{"Safely copies memory even if regions overlap", "Is slower than memcpy always", "Deletes source memory"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 41. การเปรียบเทียบหน่วยความจำด้วย memcmp
        list.add(new Question("41. การเปรียบเทียบหน่วยความจำด้วย memcmp",
                "int res = memcmp(p1, p2, n);",
                new String[]{"Compares first n bytes of two memory blocks", "Compares strings case-insensitive", "Compares pointers addresses"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 42. การจัดการฟังก์ชันรับจำนวนพารามิเตอร์ไม่จำกัด (Variadic)
        list.add(new Question("42. การจัดการฟังก์ชันรับจำนวนพารามิเตอร์ไม่จำกัด (Variadic)",
                "#include <stdarg.h>",
                new String[]{"va_list, va_start, va_arg, va_end macros", "var_args library", "args... syntax"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 43. ข้อควรระวังเมื่อใช้ fflush(stdin)
        list.add(new Question("43. ข้อควรระวังเมื่อใช้ fflush(stdin)",
                "fflush(stdin);",
                new String[]{"Undefined behavior for input streams in standard C", "Clears input buffer reliably on all platforms", "Closes standard input"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 44. การใช้งานคอมไพเลอร์ inline keyword ใน C99
        list.add(new Question("44. การใช้งานคอมไพเลอร์ inline keyword ใน C99",
                "inline int square(int x) { return x*x; }",
                new String[]{"Suggests compiler to insert function code directly", "Forces function to run in separate thread", "Makes function recursive"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 45. การเขียนคอมเมนต์ควบคุมการทำงาน Preprocessor #error
        list.add(new Question("45. การเขียนคอมเมนต์ควบคุมการทำงาน Preprocessor #error",
                "#error \"Platform not supported\"",
                new String[]{"Forces compilation to stop with custom error message", "Ignores syntax errors", "Throws runtime error"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 46. การใช้ตัวดำเนินการ sizeof กับชนิดข้อมูล Pointer
        list.add(new Question("46. การใช้ตัวดำเนินการ sizeof กับชนิดข้อมูล Pointer",
                "int *p; sizeof(p);",
                new String[]{"Returns size of pointer variable itself (e.g. 4 or 8 bytes)", "Returns size of pointed data", "Causes compilation error"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 47. การประกาศอาเรย์แบบยืดหยุ่นใน Struct (Flexible Array Member)
        list.add(new Question("47. การประกาศอาเรย์แบบยืดหยุ่นใน Struct (Flexible Array Member)",
                "struct Packet { int len; char payload[]; }",
                new String[]{"Must be the last member of the struct", "Can be placed anywhere in struct", "Requires fixed size allocation"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 48. คำสั่ง goto ข้ามบล็อกโค้ด
        list.add(new Question("48. คำสั่ง goto ข้ามบล็อกโค้ด",
                "goto error_handler;",
                new String[]{"Can jump within the same function scope", "Can jump across different functions", "Is recommended for all error handling"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 49. การใช้แอดเดรสของฟังก์ชันโดยไม่ต้องใช้ &
        list.add(new Question("49. การใช้แอดเดรสของฟังก์ชันโดยไม่ต้องใช้ &",
                "int (*fp)(int) = &myFunc;",
                new String[]{"Function designator decays to pointer automatically", "Explicit & is strictly mandatory", "Pointers to functions are illegal"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // 50. การใช้ตัวแปรประเภท char เก็บตัวเลขมีเครื่องหมาย
        list.add(new Question("50. การใช้ตัวแปรประเภท char เก็บตัวเลขมีเครื่องหมาย",
                "char x = -10;",
                new String[]{"Valid, ranged usually from -128 to 127", "Invalid, char is always unsigned", "Causes syntax error"},
                0, GameConstants.LANG_C, GameConstants.DIFF_NORMAL));

        // Hard
// 1. ข้อผิดพลาด Strict Aliasing Rule ผ่าน type punning
        list.add(new Question("1. ข้อผิดพลาด Strict Aliasing Rule ผ่าน type punning",
                "float f = 1.0f; int i = *(int*)&f;",
                new String[]{"Undefined Behavior violating strict aliasing rules", "Standard compliant type conversion", "Guaranteed bitwise cast"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 2. Undefined Behavior จากลำดับการประมวลผล (Sequence Points)
        list.add(new Question("2. Undefined Behavior จากลำดับการประมวลผล (Sequence Points)",
                "int i = 0; int x = i++ + i++;",
                new String[]{"Undefined Behavior due to multiple modifications of i between sequence points", "Evaluates left to right deterministically", "Result is always 1"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 3. พอยน์เตอร์อาริ่นเมติก (Pointer Arithmetic) คำนวณตามสิ่งใด
        list.add(new Question("3. พอยน์เตอร์อาริ่นเมติก (Pointer Arithmetic) คำนวณตามสิ่งใด",
                "int *p = arr; p++;",
                new String[]{"Advances by sizeof(*p) bytes", "Advances by exactly 1 byte", "Advances by word size only"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 4. การทำพอยน์เตอร์อาริ่นเมติกกับ void pointer
        list.add(new Question("4. การทำพอยน์เตอร์อาริ่นเมติกกับ void pointer",
                "void *p; p++;",
                new String[]{"Illegal in standard C (void has no size)", "Fully standard compliant", "Advances by 1 byte always"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 5. ปัญหา Structure Padding และ Data Alignment
        list.add(new Question("5. ปัญหา Structure Padding และ Data Alignment",
                "struct Test { char c; int i; }; sizeof(struct Test);",
                new String[]{"May be greater than 5 due to compiler padding alignment bytes", "Always exactly 5 bytes", "Always 8 bytes on all platforms"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 6. การใช้ __attribute__((packed)) ป้องกัน padding
        list.add(new Question("6. การใช้ __attribute__((packed)) ป้องกัน padding",
                "struct __attribute__((packed)) Test { char c; int i; };",
                new String[]{"Forces compiler to pack members without alignment gaps", "Increases alignment padding", "Disables struct usage"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 7. ความปลอดภัยของ Macro Multi-statement ป้องกันด้วยสิ่งใด
        list.add(new Question("7. ความปลอดภัยของ Macro Multi-statement ป้องกันด้วยสิ่งใด",
                "#define SWAP(a,b) {int t=a; a=b; b=t;}",
                new String[]{"Wrap in do { ... } while(0)", "Wrap in normal brackets ( )", "Use semicolon at end of macro definition"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 8. พฤติกรรมของ Signed Integer Overflow ใน C
        list.add(new Question("8. พฤติกรรมของ Signed Integer Overflow ใน C",
                "int x = INT_MAX + 1;",
                new String[]{"Undefined Behavior according to C standard", "Guaranteed wrap around to INT_MIN", "Throws arithmetic exception automatically"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 9. การ Shift บิตเกินขนาดความกว้างของชนิดข้อมูล
        list.add(new Question("9. การ Shift บิตเกินขนาดความกว้างของชนิดข้อมูล",
                "int x = 1; int res = x << 32;",
                new String[]{"Undefined Behavior", "Guaranteed to return 0", "Returns 1"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 10. พฤติกรรม Right Shift ของจำนวนเต็มติดลบ
        list.add(new Question("10. พฤติกรรม Right Shift ของจำนวนเต็มติดลบ",
                "int x = -1; int res = x >> 1;",
                new String[]{"Implementation-defined (often arithmetic shift preserving sign bit)", "Guaranteed logical shift adding zeros", "Always results in 0"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 11. การแก้ไขข้อมูลใน String Literal ที่เก็บใน Read-only memory
        list.add(new Question("11. การแก้ไขข้อมูลใน String Literal ที่เก็บใน Read-only memory",
                "char *s = \"Hello\"; s[0] = 'J';",
                new String[]{"Segmentation Fault / Undefined Behavior", "Modifies string successfully", "Allocates new string on heap"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 12. การใช้ restrict keyword ช่วยคอมไพเลอร์อย่างไร
        list.add(new Question("12. การใช้ restrict keyword ช่วยคอมไพเลอร์อย่างไร",
                "void update(int * restrict dest, const int * restrict src);",
                new String[]{"Asserts pointers do not alias, enabling aggressive optimizations", "Prevents multi-threading access", "Makes pointers const"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 13. การใช้ setjmp และ longjmp สำหรับข้าม stack frames
        list.add(new Question("13. การใช้ setjmp และ longjmp สำหรับข้าม stack frames",
                "jmp_buf buf; setjmp(buf); longjmp(buf, 1);",
                new String[]{"Non-local jumps for exception-like error handling in C", "Standard multithreading mechanism", "Memory allocation routine"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 14. ความปลอดภัยของการเรียกฟังก์ชันใน Signal Handler
        list.add(new Question("14. ความปลอดภัยของการเรียกฟังก์ชันใน Signal Handler",
                "void handler(int sig) { printf(\"Signal\\n\"); }",
                new String[]{"Calling non-async-signal-safe functions like printf inside handler is UB", "Completely safe on all operating systems", "Required for robust logging"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 15. ตัวแปรประเภท sig_atomic_t ใน signal.h
        list.add(new Question("15. ตัวแปรประเภท sig_atomic_t ใน signal.h",
                "volatile sig_atomic_t flag;",
                new String[]{"Guaranteed to be read/written atomically with respect to signals", "Prevents all compiler warnings", "Makes variable constant"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 16. การใช้ Union เพื่อ Type Punning ใน C99 vs C++
        list.add(new Question("16. การใช้ Union เพื่อ Type Punning ใน C99 vs C++",
                "union Pun { float f; int i; } u;",
                new String[]{"Valid in C99/C11 standards, but strictly undefined in standard C++", "Invalid in both C and C++", "Guaranteed identical behavior everywhere"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 17. ปัญหา Memory Leak จากการไม่ตรวจสอบค่า realloc ล้มเหลว
        list.add(new Question("17. ปัญหา Memory Leak จากการไม่ตรวจสอบค่า realloc ล้มเหลว",
                "p = realloc(p, new_size);",
                new String[]{"If realloc returns NULL, original pointer p is lost causing a memory leak", "realloc frees original pointer automatically on failure", "realloc never fails"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 18. การละเลยค่า Return ของ fread ทำให้เกิดปัญหาใด
        list.add(new Question("18. การละเลยค่า Return ของ fread ทำให้เกิดปัญหาใด",
                "fread(buf, 100, 1, fp);",
                new String[]{"Using buffer data without checking items read count leads to uninitialized usage", "fread automatically fills missing bytes with zero", "fread halts program on partial read"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 19. ข้อผิดพลาดในการใช้ qsort กับฟังก์ชันเปรียบเทียบ
        list.add(new Question("19. ข้อผิดพลาดในการใช้ qsort กับฟังก์ชันเปรียบเทียบ",
                "int cmp(const void *a, const void *b) { return *(int*)a - *(int*)b; }",
                new String[]{"Potential integer overflow if difference exceeds int range", "Always safe from overflow", "Syntax error in casting"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 20. การเขียนโปรแกรม Thread-safe ด้วย stdatomic.h (C11)
        list.add(new Question("20. การเขียนโปรแกรม Thread-safe ด้วย stdatomic.h (C11)",
                "_Atomic int counter = 0;",
                new String[]{"Provides built-in atomic operations and memory ordering models", "Replaces mutexes entirely for all complex data structures", "Only works on single-core CPUs"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 21. ปัญหา Deadlock ระหว่างเธรดใน POSIX threads
        list.add(new Question("21. ปัญหา Deadlock ระหว่างเธรดใน POSIX threads",
                "pthread_mutex_lock(&m1); pthread_mutex_lock(&m2);",
                new String[]{"Circular wait condition where threads block each other indefinitely", "Automatic deadlock resolution by OS", "Memory leak issue"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 22. การใช้ Flexible Array Member เติมขนาดหน่วยความจำไดนามิก
        list.add(new Question("22. การใช้ Flexible Array Member เติมขนาดหน่วยความจำไดนามิก",
                "struct Foo { size_t n; int data[]; };",
                new String[]{"Allocated using malloc(sizeof(struct Foo) + n * sizeof(int))", "Allocated using standard malloc(sizeof(struct Foo)) only", "Forbidden in C99"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 23. ปัญหา Integer Conversion Truncation
        list.add(new Question("23. ปัญหา Integer Conversion Truncation",
                "int large = 3000000000; char c = large;",
                new String[]{"Truncation leads to unexpected wrapped values and security vulnerabilities", "Automatically promotes to larger type", "Throws compilation error"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 24. ไลบรารีไดนามิกโหลด (Dynamic Loading) ใน C ด้วย dlfcn.h
        list.add(new Question("24. ไลบรารีไดนามิกโหลด (Dynamic Loading) ใน C ด้วย dlfcn.h",
                "void *handle = dlopen(\"lib.so\", RTLD_LAZY);",
                new String[]{"Loads shared libraries and resolves symbols at runtime", "Static linking directive", "File compression utility"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 25. ความแตกต่างโหมด Text กับ Binary ในระบบ Windows สำหรับ fopen
        list.add(new Question("25. ความแตกต่างโหมด Text กับ Binary ในระบบ Windows สำหรับ fopen",
                "FILE *f = fopen(\"file.bin\", \"r\"); vs \"rb\"",
                new String[]{"\"rb\" prevents translation of newline characters (CRLF <-> LF)", "No difference on any platform", "\"rb\" enables encryption"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 26. ข้อผิดพลาดในการประกาศฟังก์ชันรับพารามิเตอร์แบบระบุตัวตน (K&R style)
        list.add(new Question("26. ข้อผิดพลาดในการประกาศฟังก์ชันรับพารามิเตอร์แบบระบุตัวตน (K&R style)",
                "int add(a, b) int a, int b; { return a+b; }",
                new String[]{"Obsolete legacy K&R style function definition, avoid in modern C", "Modern standard C11 syntax", "Compiler extension only"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 27. การใช้งาน atexit เพื่อลงทะเบียนฟังก์ชันล้างข้อมูลตอนปิดโปรแกรม
        list.add(new Question("27. การใช้งาน atexit เพื่อลงทะเบียนฟังก์ชันล้างข้อมูลตอนปิดโปรแกรม",
                "atexit(cleanup_func);",
                new String[]{"Functions registered with atexit are called automatically upon normal program termination", "Called only when program crashes", "Executes before main starts"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 28. พฤติกรรมเปรียบเทียบ Floating Point NaN (Not a Number)
        list.add(new Question("28. พฤติกรรมเปรียบเทียบ Floating Point NaN (Not a Number)",
                "float nan_val = 0.0f / 0.0f; if (nan_val == nan_val)",
                new String[]{"Evaluates to false because NaN is never equal to itself", "Evaluates to true", "Causes floating point exception crash"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 29. การเปิดใช้งานตัวดักจับข้อผิดพลาดทางคณิตศาสตร์ feenableexcept
        list.add(new Question("29. การเปิดใช้งานตัวดักจับข้อผิดพลาดทางคณิตศาสตร์ feenableexcept",
                "feenableexcept(FE_DIVBYZERO | FE_INVALID);",
                new String[]{"Enables hardware traps for floating-point exceptions", "Disables all math functions", "Speeds up division operations"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 30. การใช้ Compound Literals สร้างโครงสร้างชั่วคราว
        list.add(new Question("30. การใช้ Compound Literals สร้างโครงสร้างชั่วคราว",
                "struct Point p = (struct Point){.x = 10, .y = 20};",
                new String[]{"Creates an unnamed object with block scope lifetime", "Global scope lifetime only", "Invalid C syntax"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 31. การขยายอาเรย์ความยาวไม่คงที่ด้วย VLA (Variable Length Arrays)
        list.add(new Question("31. การขยายอาเรย์ความยาวไม่คงที่ด้วย VLA (Variable Length Arrays)",
                "int n; scanf(\"%d\", &n); int arr[n];",
                new String[]{"VLA can lead to stack overflow if n is excessively large; discouraged", "Allocated safely on heap", "Guaranteed bounds checking"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 32. ปัญหาการใช้ pointer casting ไม่ตรงแนวจัดวางหน่วยความจำ
        list.add(new Question("32. ปัญหาการใช้ pointer casting ไม่ตรงแนวจัดวางหน่วยความจำ",
                "int *p = (int*)(char_ptr + 1);",
                new String[]{"Can cause hardware bus errors or performance penalties on strict-alignment architectures", "Always safe on all CPUs", "Automatically realigned by compiler"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 33. ข้อควรระวังการใช้ Macro ที่ประเมินอาร์กิวเมนต์ซ้ำซ้อน
        list.add(new Question("33. ข้อควรระวังการใช้ Macro ที่ประเมินอาร์กิวเมนต์ซ้ำซ้อน",
                "#define MAX(a,b) ((a) > (b) ? (a) : (b))\nint m = MAX(i++, j++);",
                new String[]{"Arguments with side effects (like i++) get evaluated multiple times, causing bugs", "Side effects are safely ignored", "Optimized out by preprocessor"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 34. การใช้ตัวแปรประเภท wchar_t สำหรับตัวอักษรขนาดกว้าง
        list.add(new Question("34. การใช้ตัวแปรประเภท wchar_t สำหรับตัวอักษรขนาดกว้าง",
                "wchar_t *wstr = L\"Hello\";",
                new String[]{"Platform-dependent size (2 bytes on Windows, 4 bytes on Linux/Unix)", "Always exactly 1 byte", "Deprecated feature"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 35. การใช้คำสั่ง #pragma pack(1) จัดเรียงโครงสร้าง
        list.add(new Question("35. การใช้คำสั่ง #pragma pack(1) จัดเรียงโครงสร้าง",
                "#pragma pack(1)\nstruct S { char c; int i; };",
                new String[]{"Packs structure members without alignment padding, changing size to 5 bytes", "Doubles structure size", "Makes structure thread-safe"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 36. ความแตกต่างระหว่าง int const *p กับ const int *p
        list.add(new Question("36. ความแตกต่างระหว่าง int const *p กับ const int *p",
                "int const *p vs const int *p",
                new String[]{"They are completely identical (pointer to constant integer data)", "They have different meanings", "One makes pointer const, one makes data const"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 37. การใช้งาน pointer ถึงอาเรย์ (Pointer to an array)
        list.add(new Question("37. การใช้งาน pointer ถึงอาเรย์ (Pointer to an array)",
                "int (*ptr)[10];",
                new String[]{"Points to an entire array of 10 integers", "Equivalent to int *ptr[10]", "Invalid syntax"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 38. ผลลัพธ์จากการหารด้วยศูนย์ของจำนวนเต็ม (Integer division by zero)
        list.add(new Question("38. ผลลัพธ์จากการหารด้วยศูนย์ของจำนวนเต็ม (Integer division by zero)",
                "int x = 5 / 0;",
                new String[]{"Undefined Behavior (often causes hardware SIGFPE signal crash)", "Guaranteed to return 0", "Returns INT_MAX"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 39. ปัญหา Circular Dependency ระหว่างไฟล์ Header
        list.add(new Question("39. ปัญหา Circular Dependency ระหว่างไฟล์ Header",
                "FileA.h includes FileB.h and FileB.h includes FileA.h",
                new String[]{"Requires inclusion guards (#ifndef / #define) or #pragma once", "Handled automatically without guards", "Causes compiler memory extension"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 40. การใช้งาน Memory Barrier / Fence ใน low-level programming
        list.add(new Question("40. การใช้งาน Memory Barrier / Fence ใน low-level programming",
                "atomic_thread_fence(memory_order_seq_cst);",
                new String[]{"Enforces ordering constraints on memory operations across threads", "Frees heap memory block", "Locks mutex lock"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 41. พฤติกรรมของ unsigned integer underflow
        list.add(new Question("41. พฤติกรรมของ unsigned integer underflow",
                "unsigned int x = 0; x--;",
                new String[]{"Guaranteed to wrap around to UINT_MAX according to C standard", "Causes undefined behavior crash", "Stops at 0"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 42. การส่งต่อพารามิเตอร์แบบ pointer to function เข้า qsort
        list.add(new Question("42. การส่งต่อพารามิเตอร์แบบ pointer to function เข้า qsort",
                "qsort(arr, n, sizeof(int), compare_func);",
                new String[]{"Requires a comparison function returning negative, zero, or positive integer", "Requires a boolean comparison function", "Requires void return function"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 43. ข้อผิดพลาดในการใช้ bsearch บนอาเรย์ที่ยังไม่ได้เรียงลำดับ
        list.add(new Question("43. ข้อผิดพลาดในการใช้ bsearch บนอาเรย์ที่ยังไม่ได้เรียงลำดับ",
                "bsearch(key, arr, n, size, cmp);",
                new String[]{"Array must be sorted prior to calling bsearch, otherwise result is undefined", "bsearch sorts array automatically", "Works on any random array order"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 44. การตรวจสอบข้อผิดพลาดสตรีมไฟล์ด้วย ferror และ clearerr
        list.add(new Question("44. การตรวจสอบข้อผิดพลาดสตรีมไฟล์ด้วย ferror และ clearerr",
                "if (ferror(fp)) { clearerr(fp); }",
                new String[]{"ferror checks error indicator, clearerr resets error/EOF flags on stream", "ferror closes file automatically", "clearerr frees file pointer"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 45. พฤติกรรมการคืนค่าของ snprintf เมื่อ buffer มีขนาดเล็กเกินไป
        list.add(new Question("45. พฤติกรรมการคืนค่าของ snprintf เมื่อ buffer มีขนาดเล็กเกินไป",
                "int len = snprintf(buf, 5, \"123456789\");",
                new String[]{"Returns the total length of string that would have been written", "Returns -1 on truncation", "Truncates and returns 5"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 46. การใช้งาน Zero-length arrays (GCC extension)
        list.add(new Question("46. การใช้งาน Zero-length arrays (GCC extension)",
                "struct Foo { int len; char data[0]; };",
                new String[]{"Precursor to C99 flexible array members used in older GNU C code", "Standard C99 compliant feature", "Causes compile error on all compilers"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 47. ข้อผิดพลาดในการใช้ recursive mutex เมื่อล็อกซ้ำซ้อน
        list.add(new Question("47. ข้อผิดพลาดในการใช้ recursive mutex เมื่อล็อกซ้ำซ้อน",
                "pthread_mutex_lock(&mutex); pthread_mutex_lock(&mutex);",
                new String[]{"Causes self-deadlock on standard non-recursive mutexes", "Succeeds automatically", "Releases lock immediately"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 48. ผลลัพธ์ของการส่งค่าพารามิเตอร์ซ้ำซ้อนในฟังก์ชันที่มี side effect
        list.add(new Question("48. ผลลัพธ์ของการส่งค่าพารามิเตอร์ซ้ำซ้อนในฟังก์ชันที่มี side effect",
                "printf(\"%d %d\\n\", ++x, x++);",
                new String[]{"Undefined Behavior due to conflicting unsequenced side effects on x", "Evaluates strictly left to right always", "Evaluates strictly right to left always"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 49. การใช้ #undef ยกเลิกการนิยาม Macro
        list.add(new Question("49. การใช้ #undef ยกเลิกการนิยาม Macro",
                "#undef MAX",
                new String[]{"Removes a previously defined macro definition", "Deletes a global variable", "Clears terminal screen"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));

        // 50. การใช้ตัวแปรชนิด char เป็น index ของอาเรย์โดยไม่ระบุ signed/unsigned
        list.add(new Question("50. การใช้ตัวแปรชนิด char เป็น index ของอาเรย์โดยไม่ระบุ signed/unsigned",
                "char idx = 200; arr[idx] = 1;",
                new String[]{"On platforms where char is signed, 200 overflows to negative index causing out-of-bounds bug", "Always treats 200 as positive index", "Compiler converts to int automatically without warning"},
                0, GameConstants.LANG_C, GameConstants.DIFF_HARD));
    }
}