1. String不可变的原因？
    * String 不可变，StringBuffer 和 StringBuilder 可变。
    * String 不可变，因此是线程安全的。StringBuilder 不是线程安全的；StringBuffer 是线程安全的，使用 synchronized 来同步。