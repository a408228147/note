1. JVM内存区域模型?
 * 方法区
    * 它用于存储虚拟机加载的类信息、常量、静态变量、是各个线程共享的内存区域。默认最小值为16MB，最大值为64MB,可以限制大小。运行时常量池：是方法区的一部分
 * 虚拟机栈
    * 描述的是java 方法执行的内存模型：每个方法被执行的时候 都会创建一个“栈帧”用于存储局部变量表(包括参数)、操作栈、方法出口等信息。每个方法被调用到执行完的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。声明周期与线程相同，是线程私有的。
 * 本地方法栈
    * 而本地方法栈则是为Native方法服务
 * 堆
    * GC堆是java虚拟机所管理的内存中最大的一块内存区域，也是被各个线程共享的内存区域，在JVM启动时创建。该内存区域存放了对象实例及数组(所有new的对象)。堆被划分为新生代和老年代。新生代主要存储新创建的对象和尚未进入老年代的对象。老年代存储经过多次新生代GC(Minor GC)任然存活的对象。
 * 程序计数器
    * 是最小的一块内存区域，它的作用是当前线程所执行的字节码的行号指示器，在虚拟机的模型里，字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令，分支、循环、异常处理、线程恢复等基础功能都需要依赖计数器完成。
2. Java堆内存的几个要点
 * Java堆内存是操作系统分配给JVM的内存的一部分。
 * 当我们创建对象时，它们存储在Java堆内存中。
 * Java堆空间不同于栈空间，栈空间是用来储存调用栈和局部变量的。
 * Java垃圾回收器是用来将死掉的对象(不再使用的对象)所占用的内存回收回来，再释放到Java堆空间中。
3. 

## 作业
  ### 题目
  * work1：http://wiki.corp.bianlifeng.com/pages/viewpage.action?pageId=59671034
  * work2：http://wiki.corp.bianlifeng.com/pages/viewpage.action?pageId=60260752
  ### 地址
  * git1：http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/tree/team8/team8/haoqiang.zheng/mysqlwork （40%）
  * git2：http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/tree/team8/team8/haoqiang.zheng/daydayhappy3 （40%）
## 学习内容
  * Mysql的发展过程；
  * Mysql中一些常用的数据类型；
  * Mysql中的常用操作；
  * Mysql的体系结构、复制原理、索引结构等；
  * Mysql优化相关的知识；
  


