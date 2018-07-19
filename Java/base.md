1. String不可变的原因？
    * String 不可变，StringBuffer 和 StringBuilder 可变。
    * String 不可变，因此是线程安全的。StringBuilder 不是线程安全的；StringBuffer 是线程安全的，使用 synchronized 来同步。

### 异常
2. 异常
    * Throwable 是所有异常的父类，它有两个直接子类 Error 和 Exception
    * 其中 Exception 又被继续划分为被检查的异常（checked exception）和运行时的异常（runtime exception，即不受检查的异常）
    * Error 表示系统错误，通常不能预期和恢复（譬如 JVM 崩溃、内存不足等）；被检查的异常（Checked exception）在程序中能预期且要尝试修复（如我们必须捕获 FileNotFoundException 异常并为用户提供有用信息和合适日志来进行调试，Exception 是所有被检查的异常的父类）；
    * RuntimeException 是所有运行时异常的父类。
3.  Error 和 Exception 有什么区别？
    * Error 表示系统级的错误，是 java 运行环境内部错误或者硬件问题，不能指望程序来处理这样的问题，除了退出运行外别无选择，它是 java 虚拟机抛出的。Exception 表示程序需要捕捉、需要处理的异常，是由与程序设计的不完善而出现的问题，程序可以处理的问题。
4. [关于try、catch代码说明](https://github.com/yzrds/note/blob/63eae1bb65516dd29d8893f74d0b918cf656170b/src/com/note/base/ExceptionTest.java)

5. finally代码块一定会执行吗？
    * 只有在 try 里面通过 System.exit(0) 来退出 JVM 的情况下 finally 块中的代码才不会执行，其他 return 等情况都会调用，所以在不终止 JVM 的情况下 finally 中的代码一定会执行。

### 枚举
1. 枚举类比较用== 还是 equals，有啥区别？
    * 枚举 Enum 类的 equals 方法默认实现就是通过 == 来比较的
2. 枚举常量都通过静态代码块进行初始化实例赋值（由于是静态块，所以在类加载期间就初始化了）
3. Java 枚举类可以继承其他类（或实现其他接口）或者被其他类继承吗
    * 枚举类可以实现其他接口但不能继承其他类，因为所有枚举类在编译后的字节码中都继承自 java.lang.Enum（由编译器添加），而 Java 不支持多继承，所以枚举类不可以继承其他类。
4. Java 枚举会比静态常量更消耗内存吗？
    * 会更消耗，一般场景下不仅编译后的字节码会比静态常量多，而且运行时也会比静态常量需要更多的内存，不过这个多取决于场景和枚举的规模等等，不能明确的定论多多少（一般都至少翻倍以上），此外更不能因为多就一刀切的认为静态常量应该优于枚举使用，枚举有自己的特性和场景，优化也不能过度。
### 代码块
1. 