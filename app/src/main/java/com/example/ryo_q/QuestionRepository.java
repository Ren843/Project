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

        // หมวดภาษา JAVA
        // JAVA EASY
        // 1. ตรวจสอบการเปรียบเทียบข้อความ (String) ที่ถูกต้องใน Java ต้องใช้ .equals()
        list.add(new Question("String s = \"Bot\";\nif (s == \"Bot\") {\n    repair();\n}",
                new String[]{"if (s.equals(\"Bot\")) {", "if (s = \"Bot\") {", "if (s.length() > 0) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 2. ตรวจสอบความเข้าใจเรื่อง Index ของ Array (เริ่มที่ 0 ถึง size-1)
        list.add(new Question("int[] arr = new int[3];\narr[3] = 100;",
                new String[]{"arr[4] = 100;", "arr[2] = 100;", "arr[100] = 3;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 3. ตรวจสอบไวยากรณ์พื้นฐาน การปิดท้ายคำสั่งด้วย Semicolon (;)
        list.add(new Question("System.out.println(\"Hello Bot\")",
                new String[]{"System.out.print(\"Hello Bot\")", "System.println(\"Hello Bot\");", "System.out.println(\"Hello Bot\");"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 4. ตรวจสอบการใช้เครื่องหมายคำพูด สำหรับ String ต้องใช้ Double Quote (")
        list.add(new Question("String name = 'Robot';",
                new String[]{"String name = \"Robot\";", "String name == \"Robot\";", "char name = \"Robot\";"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 5. ตรวจสอบการประกาศตัวแปรและการกำหนดค่าเริ่มต้นก่อนใช้งาน
        list.add(new Question("int x;\nSystem.out.print(x);",
                new String[]{"print(x);", "int x = 0;\nSystem.out.print(x);", "int x == 0;\nSystem.out.print(x);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 6. ตรวจสอบการคืนค่า (Return) ในเมธอดที่มีการระบุประเภทส่งกลับ
        list.add(new Question(
                "public int getSum() {\n    int a = 5;\n    int b = 10;\n}",
                new String[]{"public int getSum() {\n    int a = 5;\n    int b = 10;\n    return a + b;\n}", "public void getSum() {\n    int a = 5;\n    int b = 10;\n}", "public int getSum() {\n    int a = 5;\n    int b = 10;\n    return;\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 7. ตรวจสอบความแตกต่างระหว่างการกำหนดค่า (=) และการเปรียบเทียบ (==) ในเงื่อนไข
        list.add(new Question(
                "boolean isBroken = true;\nif (isBroken = false) { }",
                new String[]{"if (isBroken == false) { }", "if (isBroken := false) { }", "if (isBroken.equals(false)) { }"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 8. ตรวจสอบการหาความยาวของ Array ใน Java ใช้ .length (ไม่ใช่เมธอด)
        list.add(new Question(
                "int[] arr = {1, 2, 3};\nSystem.out.println(arr.length());",
                new String[]{"System.out.println(arr.length);", "System.out.println(arr.size());", "System.out.println(arr.count);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 9. ตรวจสอบการแปลงข้อความเป็นตัวเลข (Parsing String to Int)
        list.add(new Question(
                "int score = \"100\";",
                new String[]{"int score = Integer.parseInt(\"100\");", "int score = (int)\"100\";", "int score = Convert.toInt(\"100\");"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // 10. ตรวจสอบรูปแบบมาตรฐานของ Main Method ใน Java
        list.add(new Question(
                "public static void main(String args) { }",
                new String[]{"public void main(String[] args) { }", "public static void main(String[] args) { }", "public static void main(String args) { }"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_EASY));

        // JAVA NORMAL
        // 1. ตรวจสอบการจัดการค่าว่าง (Null) เพื่อป้องกัน NullPointerException
        list.add(new Question("String s = null;\nint len = s.length();",
                new String[]{"int len = s.size();", "int len = (s == null) ? s.length() : 0;", "int len = (s != null) ? s.length() : 0;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 2. ตรวจสอบตรรกะในลูปเพื่อป้องกัน Infinite Loop (ลูปไม่รู้จบ)
        list.add(new Question("for (int i = 0; i < 5; i--) {\n    repair();\n}",
                new String[]{"for (int i = 0; i < 5; i++) {", "for (int i = 0; i > 5; i++) {", "for (int i = 5; i < 0; i--) {"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 3. ตรวจสอบการสร้าง Object ใหม่ด้วย Keyword 'new'
        list.add(new Question("Robot r;\nr.repair();",
                new String[]{"Robot r = null;\nr.repair();", "Robot r = new Robot(1, \"Ryo\", 0);\nr.repair();", "new r = Robot();\nr.repair();"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 4. ตรวจสอบการหารตัวเลขทศนิยมใน Java (ต้องมีเลข .0 เพื่อให้ผลลัพธ์ไม่ถูกตัดเศษ)
        list.add(new Question("double a = 1 / 2;",
                new String[]{"double a = 1 // 2;", "double a = 1 \\ 2;", "double a = 1.0 / 2.0;"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 5. ตรวจสอบกฎการเรียก Non-static method จากภายใน Static method
        list.add(new Question("public void run(){}\npublic static void main(){\n    run();\n}",
                new String[]{"new Main().run();", "Main.run();", "this.run();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 6. ตรวจสอบการใช้ Break ใน Switch-Case เพื่อหยุดการทำงานของ Case นั้นๆ
        list.add(new Question(
                "switch(mode) {\n    case 1: repair();\n    case 2: reset();\n}",
                new String[]{"switch(mode) {\n    case 1: repair(); break;\n    case 2: reset(); break;\n}", "switch(mode) {\n    case 1: repair(); continue;\n    case 2: reset(); continue;\n}", "switch(mode) {\n    case 1: repair(); return;\n    case 2: reset(); exit;\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 7. ตรวจสอบการแปลงประเภทข้อมูล (Casting) และการใช้ instanceof
        list.add(new Question(
                "Object obj = \"Robot\";\nInteger num = (Integer) obj;",
                new String[]{"Integer num = Integer.valueOf(obj);", "if (obj instanceof Integer) {\n    Integer num = (Integer) obj;\n}", "Integer num = (int) obj;"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 8. ตรวจสอบข้อจำกัดของตัวแปรภายใน Lambda Expression (ต้องเป็น final หรือ effectively final)
        list.add(new Question(
                "int count = 0;\nlist.forEach(x -> count++);",
                new String[]{"AtomicInteger count = new AtomicInteger(0);\nlist.forEach(x -> count.incrementAndGet());", "final int count = 0;\nlist.forEach(x -> count++);", "int count = 0;\nlist.forEach(x -> { static int count; count++; });"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 9. ตรวจสอบการใช้ substring และการป้องกัน IndexOutOfBounds
        list.add(new Question(
                "String code = \"Hi\";\nString sub = code.substring(0, 5);",
                new String[]{"String sub = code.substring(0, 5);", "String sub = code.substring(0, Math.min(code.length(), 5));", "String sub = code.slice(0, 5);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // 10. ตรวจสอบความเข้าใจเรื่อง List ที่แก้ไขไม่ได้ (Immutable/Fixed-size) จาก Arrays.asList
        list.add(new Question(
                "List<Integer> list = Arrays.asList(1, 2);\nlist.add(3);",
                new String[]{"List<Integer> list = new ArrayList<>(Arrays.asList(1, 2));\nlist.add(3);", "List<Integer> list = Arrays.asList(1, 2, 3);", "List<Integer> list = Arrays.asList(1, 2);\nlist.push(3);"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_NORMAL));

        // JAVA HARD
        // 1. ตรวจสอบปัญหาการแก้ไขข้อมูลใน List ขณะกำลังวนลูป (ConcurrentModificationException)
        list.add(new Question("for(String s : list) {\n    list.remove(s);\n}",
                new String[]{"for(int i=0; i<list; i++)", "list.removeIf(s -> true);", "list.delete(s);"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 2. ตรวจสอบกลไก Integer Cache ของ Wrapper Class ใน Java (-128 ถึง 127)
        list.add(new Question("Integer a = 128, b = 128;\nif (a == b) { }",
                new String[]{"if (a === b) { }", "if (a = b) { }", "if (a.equals(b)) { }"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 3. ตรวจสอบความเข้าใจเรื่องความไม่เปลี่ยนแปลงของ String (Immutability)
        list.add(new Question("String s = \"A\";\ns.concat(\"B\");",
                new String[]{"s = s.concat(\"B\");", "s.append(\"B\");", "s.add(\"B\");"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 4. ตรวจสอบความปลอดภัยในการทำงานพร้อมกันหลาย Thread (Thread Safety/Synchronization)
        list.add(new Question("public void addCount() {\n    count++;\n}",
                new String[]{"public void addCount() { count =+ 1; }", "public synchronized void addCount() {\n    count++;\n}", "public static void addCount() {\n    count++;\n}"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 5. ตรวจสอบกฎการ Override เมธอด equals (พารามิเตอร์ต้องเป็น Object)
        list.add(new Question("public boolean equals(Robot r) {\n    return true;\n}",
                new String[]{"public Boolean equals(Robot r)", "public void equals(Object r)", "public boolean equals(Object r) {\n    return true;\n}"},
                2, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 6. ตรวจสอบวิธีการสร้าง Object ของ Inner Class ที่ไม่ได้ประกาศเป็น static
        list.add(new Question(
                "Outer.Inner obj = new Outer.Inner();",
                new String[]{"Outer outer = new Outer();\nOuter.Inner obj = outer.new Inner();", "Outer.Inner obj = Outer.new Inner();", "Outer.Inner obj = (Outer.Inner) new Outer();"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 7. ตรวจสอบวิธีการเปรียบเทียบเลขทศนิยมที่ถูกต้อง (ต้องใช้ค่าความต่างเล็กน้อยหรือ Epsilon)
        list.add(new Question(
                "if (0.1 + 0.2 == 0.3) { }",
                new String[]{"if (0.1 + 0.2 === 0.3) { }", "if (Math.abs((0.1 + 0.2) - 0.3) < 1e-9) { }", "if ((0.1 + 0.2).equals(0.3)) { }"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 8. ตรวจสอบการจัดการทรัพยากรใน ThreadLocal เพื่อป้องกันปัญหาหน่วยความจำรั่ว (Memory Leak)
        list.add(new Question(
                "ThreadLocal<User> userHolder = new ThreadLocal<>();\nuserHolder.set(user);",
                new String[]{"userHolder.set(user);\ntry { /* ... */ } finally {\n    userHolder.remove();\n}", "userHolder.set(user);\nuserHolder.clear();", "userHolder.set(user);\nuserHolder = null;"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 9. ตรวจสอบการเขียน Recursion ที่ถูกต้อง (ต้องมี Base Case เพื่อหยุดการทำงาน)
        list.add(new Question(
                "int fact(int n) {\n    return n * fact(n - 1);\n}",
                new String[]{"int fact(int n) {\n    if (n <= 1) return 1;\n    return n * fact(n - 1);\n}", "int fact(int n) {\n    return n * fact(n);\n}", "int fact(int n) {\n    return n * (n - 1);\n}"},
                0, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // 10. ตรวจสอบข้อจำกัดของ Volatile (ไม่รองรับการทำงานแบบ Atomic เช่น ++)
        list.add(new Question(
                "private volatile int count = 0;\npublic void inc() {\n    count++;\n}",
                new String[]{"private volatile int count = 0;\npublic void inc() { count =+ 1; }", "private AtomicInteger count = new AtomicInteger(0);\npublic void inc() {\n    count.incrementAndGet();\n}", "private static int count = 0;\npublic void inc() { count++; }"},
                1, GameConstants.LANG_JAVA, GameConstants.DIFF_HARD));

        // หมวดภาษา PYTHON
        // PYTHON EASY
        // 1. ตรวจสอบความเข้าใจเรื่องการเปรียบเทียบค่า (==) และการกำหนดค่า (=) ใน Python
        list.add(new Question("if x = 5:\n    print('Fixing')",
                new String[]{"if x == 5:", "if x === 5:", "if (x = 5):"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 2. ตรวจสอบการแปลงประเภทข้อมูลเพื่อนำมาเชื่อมต่อ String (ต้องใช้ str())
        list.add(new Question("print(\"Bot \" + 1)",
                new String[]{"print(\"Bot \" . 1)", "print(\"Bot \" + str(1))", "print(\"Bot \" & 1)"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 3. ตรวจสอบไวยากรณ์การประกาศฟังก์ชันและการใช้เครื่องหมาย Colon (:)
        list.add(new Question("def repair()\n    pass",
                new String[]{"function repair():", "def repair;", "def repair():\n    pass"},
                2, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 4. ตรวจสอบการใช้ค่า Boolean ใน Python (ต้องขึ้นต้นด้วยตัวใหญ่: True/False)
        list.add(new Question("while true:\n    break",
                new String[]{"while True:\n    break", "while(true):", "do while True:"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 5. ตรวจสอบกฎการย่อหน้า (Indentation) ของ Python ซึ่งสำคัญมากต่อการทำงาน
        list.add(new Question("if True:\nprint('A')",
                new String[]{"if True:\nprint('A');", "if True:\n    print('A')", "if True:\n{ print('A') }"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 6. ตรวจสอบการตั้งชื่อตัวแปรซ้ำกับชื่อฟังก์ชันมาตรฐาน (Shadowing Built-ins)
        list.add(new Question(
                "len = 10\nprint(len(\"Bot\"))",
                new String[]{"length = 10\nprint(len(\"Bot\"))", "len = 10\nprint(length(\"Bot\"))", "del len\nlen = 10\nprint(len(\"Bot\"))"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 7. ตรวจสอบการรับค่าจากผู้ใช้ (input) ซึ่งจะได้ข้อมูลเป็น String เสมอ
        list.add(new Question(
                "num = input(\"Enter: \")\nprint(num + 5)",
                new String[]{"num = int(input(\"Enter: \"))\nprint(num + 5)", "num = input(\"Enter: \")\nprint(int(num + 5))", "num = input(\"Enter: \").toInt()\nprint(num + 5)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 8. ตรวจสอบความเข้าใจเรื่องการ Return ค่าจากฟังก์ชัน (ถ้าลืมจะคืนค่า None)
        list.add(new Question(
                "def add(a, b):\n    a + b\nresult = add(1, 2)",
                new String[]{"def add(a, b):\n    return a + b", "def add(a, b):\n    print(a + b)", "def add(a, b):\n    yield a + b"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 9. ตรวจสอบการใช้เครื่องหมายคำพูดภายในข้อความ (String Escaping)
        list.add(new Question(
                "msg = 'It\\'s a robot'",
                new String[]{"msg = \"It's a robot\"", "msg = 'It\"s a robot'", "msg = It's a robot"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // 10. ตรวจสอบขอบเขตของ Index ใน List ของ Python
        list.add(new Question(
                "items = [1, 2]\nprint(items[2])",
                new String[]{"items = [1, 2]\nprint(items[1])", "items = [1, 2]\nprint(items[2])", "items = [1, 2]\nprint(items[3])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_EASY));

        // PYTHON NORMAL
        // 1. ตรวจสอบวิธีการดึงค่าจาก Dictionary อย่างปลอดภัยด้วย .get()
        list.add(new Question("d = {'a': 1}\nprint(d['b'])",
                new String[]{"print(d.index('b'))", "print(d.find('b'))", "print(d.get('b'))"},
                2, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 2. ตรวจสอบวิธีการวนลูปดึงข้อมูลทั้ง Key และ Value จาก Dictionary
        list.add(new Question("for k, v in d:\n    print(v)",
                new String[]{"for k, v in d.items():", "for k, v in d.values():", "for k, v in d.keys():"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 3. ตรวจสอบการใช้ Keyword 'global' เพื่อแก้ไขตัวแปรที่อยู่นอกฟังก์ชัน
        list.add(new Question("x = 0\ndef fix():\n    x += 1",
                new String[]{"def fix():\n    super x\n    x += 1", "def fix():\n    global x\n    x += 1", "def fix(x):\n    x += 1"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 4. ตรวจสอบปัญหาการใช้ Mutable Object เป็นค่าเริ่มต้นของพารามิเตอร์ (Default Arguments)
        list.add(new Question("def add(l=[]):\n    l.append(1)",
                new String[]{"def add(l=list()):", "def add(l={}):", "def add(l=None):\n    if l is None: l = []"},
                2, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 5. ตรวจสอบวิธีการจัดการไฟล์ที่ปลอดภัยด้วย Keyword 'with'
        list.add(new Question("f = open('log.txt')\nf.write('Error')",
                new String[]{"with open('log.txt', 'w') as f:\n    f.write('Error')", "file = open('log.txt')\nfile.close", "f = open('log.txt', 'write')"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 6. ตรวจสอบปัญหาการลบสมาชิกออกจาก Dictionary ขณะกำลังวนลูป
        list.add(new Question(
                "for k in d:\n    del d[k]",
                new String[]{"for k in list(d.keys()):\n    del d[k]", "for k in d.copy():\n    del d[k]", "for k in d:\n    d.pop(k)"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 7. ตรวจสอบปัญหา UnboundLocalError ใน Python
        list.add(new Question(
                "x = 10\ndef f():\n    print(x)\n    x = 5",
                new String[]{"def f():\n    global x\n    print(x)\n    x = 5", "def f():\n    local x\n    print(x)\n    x = 5", "def f():\n    nonlocal x\n    print(x)\n    x = 5"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 8. ตรวจสอบความเข้าใจเรื่อง Shallow Copy และ Deep Copy ใน Python
        list.add(new Question(
                "a = [[1]]\nb = a.copy()\nb[0][0] = 9",
                new String[]{"import copy\nb = copy.deepcopy(a)", "b = a.shallow_copy()", "b = a[:]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 9. ตรวจสอบรูปแบบการใช้ f-string เพื่อแทรกตัวแปรลงใน String
        list.add(new Question(
                "name = \"Bot\"\nprint(\"Hello {name}\")",
                new String[]{"name = \"Bot\"\nprint(f\"Hello {name}\")", "name = \"Bot\"\nprint(\"Hello %name\")", "name = \"Bot\"\nprint(\"Hello $name\")"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // 10. ตรวจสอบความเข้าใจเรื่อง Generator และการทำงานของ next()
        list.add(new Question(
                "g = (x for x in range(3))\nlist(g)\nprint(list(g))",
                new String[]{"g = [x for x in range(3)]\nlist(g)\nprint(list(g))", "g = (x for x in range(3))\nlist(g).reset()", "g = (x for x in range(3))\nprint(next(g))"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_NORMAL));

        // PYTHON HARD
        // 1. ตรวจสอบปัญหาการลบข้อมูลใน List ขณะวนลูป (ต้องใช้ copy หรือ slice)
        list.add(new Question("for i in lst:\n    lst.remove(i)",
                new String[]{"for i in lst.copy():", "for i in lst[:]:\n    lst.remove(i)", "for i in list(lst):"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 2. ตรวจสอบความเข้าใจเรื่อง Late Binding ของตัวแปรภายใน Lambda Expression
        list.add(new Question("funcs = [lambda: i for i in range(3)]",
                new String[]{"funcs = [lambda i: i for i in range(3)]", "funcs = [lambda(i): i for i in range(3)]", "funcs = [lambda i=i: i for i in range(3)]"},
                2, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 3. ตรวจสอบปัญหาการคูณ List (*) ซึ่งจะทำให้สมาชิกอ้างอิงไปยัง Object เดียวกัน
        list.add(new Question("matrix = [[0]] * 3\nmatrix[0][0] = 1",
                new String[]{"matrix = [[0] for _ in range(3)]", "matrix = [[0]] ** 3", "matrix = Array(3, [0])"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 4. ตรวจสอบกรณีพิเศษของการแก้ไข Mutable Object ภายใน Tuple
        list.add(new Question("t = (1, [2])\nt[1] += [3]",
                new String[]{"t[1] = [2, 3]", "t[1].extend([3])", "t.append([3])"},
                1, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 5. ตรวจสอบการใช้ __slots__ เพื่อจำกัดการสร้างแอตทริบิวต์ใหม่ใน Class
        list.add(new Question("class A:\n    __slots__ = ['x']\n    def __init__(self):\n        self.y = 1",
                new String[]{"self.x = 1", "del self.y", "__slots__ = ['x', 'y']"},
                2, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 6. ตรวจสอบการใช้ functools.wraps เพื่อรักษาข้อมูลเมตาของฟังก์ชันเดิมใน Decorator
        list.add(new Question(
                "def my_dec(f):\n    def wrapper():\n        return f()\n    return wrapper",
                new String[]{"from functools import wraps\ndef my_dec(f):\n    @wraps(f)\n    def wrapper():\n        return f()\n    return wrapper", "def my_dec(f):\n    @staticmethod\n    def wrapper():\n        return f()\n    return wrapper", "def my_dec(f):\n    def wrapper(f):\n        return f()\n    return wrapper"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 7. ตรวจสอบการประกาศ Instance Variable ที่ถูกต้องภายใน __init__
        list.add(new Question(
                "class Bot:\n    items = []",
                new String[]{"class Bot:\n    def __init__(self):\n        self.items = []", "class Bot:\n    static items = []", "class Bot:\n    private items = []"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 8. ตรวจสอบการจัดการ Exception ที่ดี (เลี่ยงการใช้ Bare Except)
        list.add(new Question(
                "try:\n    do_something()\nexcept:\n    pass",
                new String[]{"try:\n    do_something()\nexcept Exception as e:\n    logger.error(e)\n    raise", "try:\n    do_something()\ncatch Exception:\n    pass", "try:\n    do_something()\nexcept Error:\n    break"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 9. ตรวจสอบการใช้งานฟังก์ชัน Asynchronous และ Keyword 'await'
        list.add(new Question(
                "async def main():\n    fetch_data()",
                new String[]{"async def main():\n    await fetch_data()", "async def main():\n    async fetch_data()", "async def main():\n    yield fetch_data()"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // 10. ตรวจสอบการใช้ .extend() สำหรับเพิ่มสมาชิกจาก iterable ลงใน List
        list.add(new Question(
                "lst = [1, 2]\n[lst.append(x) for x in range(3)]",
                new String[]{"lst = [1, 2]\nlst.extend(range(3))", "lst = [1, 2]\nlst.add(range(3))", "lst = [1, 2]\n[lst.push(x) for x in range(3)]"},
                0, GameConstants.LANG_PYTHON, GameConstants.DIFF_HARD));

        // หมวดภาษา C++ (CPP)
        // CPP EASY
        // 1. ตรวจสอบกฎพื้นฐานการปิดท้ายคำสั่งด้วย Semicolon (;) ใน C++
        list.add(new Question("int x = 5",
                new String[]{"int x = 5;", "int x = 5:", "int x == 5;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 2. ตรวจสอบการใช้เครื่องหมายสำหรับแสดงผล (cout) ซึ่งต้องใช้ << (Insertion Operator)
        list.add(new Question("cout >> \"Fix Bot\";",
                new String[]{"cout << \"Fix Bot\";", "cin >> \"Fix Bot\";", "print << \"Fix Bot\";"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 3. ตรวจสอบรูปแบบการประกาศฟังก์ชัน main() ที่ถูกต้องในมาตรฐาน C++
        list.add(new Question("void main() {\n}",
                new String[]{"String main() {}", "public main() {}", "int main() {\n    return 0;\n}"},
                2, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 4. ตรวจสอบการจองหน่วยความจำของ Array ให้เพียงพอต่อการเก็บข้อมูลเริ่มต้น
        list.add(new Question("int a[2] = {1, 2, 3};",
                new String[]{"int a[3] = {1, 2, 3};", "int a[] = new int[2];", "int a(3) = {1, 2, 3};"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 5. ตรวจสอบการเรียกใช้งานคำสั่งจาก Namespace มาตรฐาน (std::)
        list.add(new Question("cout << \"Hello\";",
                new String[]{"console.cout << \"Hello\";", "std::cout << \"Hello\";", "std.cout << \"Hello\";"},
                1, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 6. ตรวจสอบการกำหนดค่าเริ่มต้นให้ตัวแปรเพื่อป้องกันปัญหาค่าขยะ (Garbage Value)
        list.add(new Question(
                "int score;\nstd::cout << score;",
                new String[]{"int score = 0;\nstd::cout << score;", "int score = null;\nstd::cout << score;", "int score();\nstd::cout << score;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 7. ตรวจสอบกฎการห้ามแก้ไขค่าของตัวแปรที่ประกาศเป็น const
        list.add(new Question(
                "const int MAX = 100;\nMAX = 200;",
                new String[]{"int MAX = 100;\nMAX = 200;", "const int MAX = 200;", "var MAX = 100;\nMAX = 200;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 8. ตรวจสอบปัญหาการคูณเลขจำนวนเต็มที่อาจเกิดการล้นของข้อมูล (Overflow)
        list.add(new Question(
                "int total = 100000 * 100000;",
                new String[]{"long long total = 100000LL * 100000LL;", "double total = (int)(100000 * 100000);", "int total = (long)100000 * 100000;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 9. ตรวจสอบความเข้าใจเรื่องการรวม Library (Include) สำหรับใช้งาน STL
        list.add(new Question(
                "std::vector<int> v;",
                new String[]{"#include <vector>\nstd::vector<int> v;", "#include <array>\nstd::vector<int> v;", "#include <list>\nstd::vector<int> v;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // 10. ตรวจสอบวิธีการสลับค่าตัวแปรโดยใช้ความสามารถของ STL (std::swap)
        list.add(new Question(
                "a ^= b;\nb ^= a;\na ^= b;",
                new String[]{"std::swap(a, b);", "swap(&a, &b);", "a = b; b = a;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_EASY));

        // CPP NORMAL
        // 1. ตรวจสอบการจองหน่วยความจำให้ Pointer ด้วย Keyword 'new' ก่อนการใช้งาน
        list.add(new Question("int *p;\n*p = 5;",
                new String[]{"int p = 5;", "int &p = 5;", "int *p = new int(5);"},
                2, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 2. ตรวจสอบกฎการประกาศ Reference (ต้องถูกจับคู่กับตัวแปรที่มีอยู่จริงทันที)
        list.add(new Question("int x = 5;\nint &ref;\nref = x;",
                new String[]{"int &ref = x;", "int *ref = x;", "int ref = &x;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 3. ตรวจสอบความเข้าใจเรื่อง Pass by Value และการใช้ Reference/Pointer เพื่อเปลี่ยนค่าในพารามิเตอร์
        list.add(new Question("void swap(int a, int b) {}",
                new String[]{"void swap(int *a, int b) {}", "void swap(int &a, int &b) {}", "void swap(int a[], int b[]) {}"},
                1, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 4. ตรวจสอบปัญหาการคืนค่า Address ของตัวแปร Local (Dangling Pointer)
        list.add(new Question("int* get() {\n    int x = 1;\n    return &x;\n}",
                new String[]{"return x;", "return *x;", "return new int(1);"},
                2, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 5. ตรวจสอบวิธีการลบหน่วยความจำของ Array (ต้องใช้ delete[])
        list.add(new Question("int *arr = new int[5];\ndelete arr;",
                new String[]{"delete[] arr;", "delete arr[];", "free(arr);"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 6. ตรวจสอบข้อจำกัดของ sizeof เมื่อใช้กับพารามิเตอร์ประเภท Array (จะได้ขนาดของ Pointer แทน)
        list.add(new Question(
                "void check(int arr[]) {\n    int len = sizeof(arr)/sizeof(arr[0]);\n}",
                new String[]{"void check(const std::vector<int>& arr) {\n    int len = arr.size();\n}", "void check(int arr[10]) {\n    int len = sizeof(arr);\n}", "void check(int* arr) {\n    int len = arr.length();\n}"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 7. ตรวจสอบวิธีการดึงค่าจาก vector อย่างปลอดภัยด้วย .at() (ป้องกันการเข้าถึง Index เกิน)
        list.add(new Question(
                "std::vector<int> v = {1};\nint x = v[5];",
                new String[]{"try {\n    int x = v.at(5);\n} catch(const std::out_of_range& e) { }", "int x = v.get(5);", "int x = v[5] != null ? v[5] : 0;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 8. ตรวจสอบปัญหาการลบ Pointer ซ้ำซ้อน (Double Free) และความสำคัญของการตั้งค่า nullptr
        list.add(new Question(
                "int *p = new int(5);\ndelete p;\ndelete p;",
                new String[]{"delete p;\np = nullptr;", "free(p);\nfree(p);", "delete[] p;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 9. ตรวจสอบความแตกต่างระหว่าง Class และ Struct ใน C++ (เรื่องการเข้าถึงข้อมูลพื้นฐาน)
        list.add(new Question(
                "class Bot { int id; };\nBot b;\nb.id = 1;",
                new String[]{"struct Bot { int id; };\nBot b;\nb.id = 1;", "class Bot { public int id; };", "class Bot { friend id; };"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // 10. ตรวจสอบปัญหา Iterator Invalidation เมื่อมีการเปลี่ยนขนาดของ vector ขณะวนลูป
        list.add(new Question(
                "for (auto x : vec) {\n    if (x == 0) vec.push_back(1);\n}",
                new String[]{"size_t sz = vec.size();\nfor (size_t i = 0; i < sz; ++i) { }", "for (auto& x : vec.copy()) { }", "for (const auto x : vec) { vec.add(1); }"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_NORMAL));

        // CPP HARD
        // 1. ตรวจสอบความสำคัญของ Virtual Destructor เมื่อทำงานกับระบบสืบทอด (Inheritance)
        list.add(new Question("class Base {\n    ~Base() {}\n};",
                new String[]{"static ~Base() {}", "virtual ~Base() {}", "const ~Base() {}"},
                1, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 2. ตรวจสอบวิธีการลบสมาชิกใน vector ระหว่างการวนลูปที่ถูกต้องเพื่อเลี่ยง Error
        list.add(new Question("for(auto it=v.begin(); it!=v.end(); it++) {\n    v.erase(it);\n}",
                new String[]{"it.erase();", "v.delete(it);", "it = v.erase(it);"},
                2, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 3. ตรวจสอบปัญหาหน่วยความจำ Use-after-free (การใช้ Pointer หลังการลบ)
        list.add(new Question("delete p;\n*p = 10;",
                new String[]{"delete p;\np = nullptr;", "free p;\n*p = 10;", "p.delete();"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 4. ตรวจสอบความเข้าใจเรื่องความไม่เปลี่ยนแปลงของ String Literal ในภาษา C++
        list.add(new Question("char *s = \"Bot\";\ns[0] = 'C';",
                new String[]{"const char *s = \"Bot\";", "char s[] = \"Bot\";\ns[0] = 'C';", "string s = 'Bot';"},
                1, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 5. ตรวจสอบความเข้าใจเรื่องการคัดลอก Object และการใช้ Reference ในลูปเพื่อประสิทธิภาพ
        list.add(new Question("for (auto s : huge_vector) { }",
                new String[]{"for (auto* s : huge_vector) { }", "for (auto[] s : huge_vector) { }", "for (const auto& s : huge_vector) { }"},
                2, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 6. ตรวจสอบความเข้าใจเรื่อง Circular Reference ของ shared_ptr และการแก้ปัญหาด้วย weak_ptr
        list.add(new Question(
                "struct A {\n    std::shared_ptr<A> next;\n};",
                new String[]{"struct A {\n    std::weak_ptr<A> next;\n};", "struct A {\n    std::unique_ptr<A> next;\n};", "struct A {\n    A* next;\n};"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 7. ตรวจสอบความปลอดภัยในการใช้ std::string_view กับวัตถุชั่วคราว
        list.add(new Question(
                "std::string_view sv = std::string(\"Bot\") + \"1\";",
                new String[]{"std::string s = std::string(\"Bot\") + \"1\";\nstd::string_view sv = s;", "std::string_view sv = (std::string(\"Bot\") + \"1\").c_str();", "const std::string_view& sv = std::string(\"Bot\") + \"1\";"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 8. ตรวจสอบความเข้าใจเรื่อง Move Semantics และสถานะของ Object หลังถูก Move
        list.add(new Question(
                "std::string s2 = std::move(s1);\nstd::cout << s1;",
                new String[]{"std::string s2 = s1;\nstd::cout << s1;", "std::string s2 = std::move(s1);\ns1.reset();", "std::string s2 = std::forward(s1);"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 9. ตรวจสอบวิธีการดึงค่าจาก std::optional อย่างปลอดภัย
        list.add(new Question(
                "std::optional<int> opt;\nint x = *opt;",
                new String[]{"if (opt.has_value()) {\n    int x = *opt;\n}", "int x = opt.get();", "int x = opt != null ? *opt : 0;"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        // 10. ตรวจสอบความเข้าใจเรื่องลำดับการทำงาน (Order of Evaluation) ในพารามิเตอร์ของฟังก์ชัน
        list.add(new Question(
                "void print(int a, int b);\nint i = 0;\nprint(i++, i++);",
                new String[]{"int a = i++;\nint b = i++;\nprint(a, b);", "print(++i, ++i);", "print(i, i + 1);"},
                0, GameConstants.LANG_CPP, GameConstants.DIFF_HARD));

        return list;
    }
}
