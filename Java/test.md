## 一、作业
### 题目:
   * work1：3个线程分别打印A、B、C10次。要求按照ABC的顺序输出（提示：wait（）、notifyAll()）。
   * work2：实现一个nio的timeServer服务。client通过“time”命令获取server的当前时间。
### git地址：
   * work1：
     * http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/tree/team8/team8/haoqiang.zheng/javaWork2/src/main/java/work1
   * work2：
     * http://gitlab.corp.bianlifeng.com/campus2018-dev/homework/tree/team8/team8/haoqiang.zheng/javaWork2/src/main/java/work2
## 二、学习内容

   1. 多线程
      * 线程的使用
        * Runnable
        * Thread
      * 线程的几种状态
        * wait() 使持有该对象的线程把该对象的控制权交出去，然后处于等待状态。 
        * notiyAll() 通知所有等待这个对象控制权的线程继续运行。
        * seelp() 线程停止
        * notify() 随机唤醒一个等待中的线程
      * 线程池
      * 同步
        * synchronized 同步方式
        * Lock 普通加锁方式
        * ReentrantLock 类重入锁方式
        * ReadWriteLock​ 读写锁方式
        * Condition 条件
   2. IO
      * 文件IO
        * File
        * Input/OutputStream
      * 网络IO
        * NIO
        * BIO
   3. 用shell写上次作业
      * 完善中

### 遇到的问题
   * 问题：当一个获得锁的线程A，执行过程中调用A.notify()之后，A线程还有可能立马被唤醒，再次执行A线程吗？
   * 解决：notify一次只随机通知一个线程进行唤醒，sync 关键字，当一个线程因为没有获得锁，会放入一个 lockPool中，当获得锁的线程执行完毕会随机唤醒该池子中的一个线程，因为获得锁的在调用notify之后依旧不在lockPool中，所以A线程不会有立马被执行的可能。
   
   * 对NIO不熟
