# 锁池和等待池
- 锁池:假设线程A已经拥有了某个对象(注意:不是类)的锁，而其它的线程想要调用这个对象的某个synchronized方法(或者synchronized块)，由于这些线程在进入对象的synchronized方法之前必须先获得该对象的锁的拥有权，但是该对象的锁目前正被线程A拥有，所以这些线程就进入了该对象的锁池中。
- 等待池:假设一个线程A调用了某个对象的wait()方法，线程A就会释放该对象的锁后，进入到了该对象的等待池中
# notify和notifyAll的区别
- 如果线程调用了对象的wait()方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。
- 当有线程调用了对象的 notifyAll()方法（唤醒所有 wait 线程）或 notify()方法（只随机唤醒一个 wait线程），被唤醒的的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。也就是说，调用了notify后只要一个线程会由等待池进入锁池，而notifyAll会将该对象等待池内的所有线程移动到锁池中，等待锁竞争。
- 优先级高的线程竞争到对象锁的概率大，假若某线程没有竞争到该对象锁，它还会留在锁池中，唯有线程再次调用wait()方法，它才会重新回到等待池中。而竞争到对象锁的线程则继续往下执行，直到执行完了 synchronized代码块，它会释放掉该对象锁，这时锁池中的线程会继续竞争该对象锁。
# 测试代码：

```java
public class MyThreadPrinter2 implements Runnable {     
    
    private String name;     
    private Object prev;     
    private Object self;     
    
    private MyThreadPrinter2(String name, Object prev, Object self) {     
        this.name = name;     
        this.prev = prev;     
        this.self = self;     
    }     
    
    public void run() {     
        int count = 10;     
        while (count > 0) {     
            synchronized (prev) {     
                synchronized (self) {     
                    System.out.print(name);     
                    count--;    
                    self.notify();//不唤醒就     
                }     
                try {     
                    prev.wait();     
                } catch (InterruptedException e) {     
                    e.printStackTrace();     
                }     
            }     
        }     
    }     
    
    public static void main(String[] args) throws Exception {     
        Object a = new Object();     
        Object b = new Object();     
        Object c = new Object();     
        MyThreadPrinter2 pa = new MyThreadPrinter2("A", c, a);     
        MyThreadPrinter2 pb = new MyThreadPrinter2("B", a, b);     
        MyThreadPrinter2 pc = new MyThreadPrinter2("C", b, c);                  
        new Thread(pa).start();  
        Thread.sleep(100);  //确保按顺序A、B、C执行  
        new Thread(pb).start();  
        Thread.sleep(100);    
        new Thread(pc).start();     
        Thread.sleep(100);    
        }     
}
```

```
输出：ABCABCABCABCABCABCABCABCABCABC
```
但程序现在还没结束，因为除了“C”线程其他所有的线程都
> 综上，所谓唤醒线程，另一种解释可以说是将线程由等待池移动到锁池，notifyAll调用后，会将全部线程由等待池移到锁池，然后参与锁的竞争，竞争成功则继续执行，如果不成功则留在锁池等待锁被释放后再次参与竞争。而notify只会唤醒一个线程。 


# 题目：编写10个线程，第一个线程从1加到10，第二个线程从11加到20……第十个线程从91加到100，最后再把十个线程结果相加。

# 测试代码：

```java
public class MainAction {

    /**
     * @Description: 编写10个线程，第一个线程从1加到10，第二个线程从11加到20……第十个线程从91加到100，最后再把十个线程结果相加
     * @Author: by haoqiang.zheng
     * @CreateDate: 16/07/2018 10:36 AM
     */
    public static void main(String[] args) throws InterruptedException {
        final Logger logger = Logger.getLogger(MainAction.class);
        for (int i = 0; i < 10; i++) {
            ThreadCount tc1 = new ThreadCount(i * 10 + 1, (i + 1) * 10);
            Thread t1 = new Thread(tc1);
            t1.start();
            Thread.sleep(100);
        }
        logger.info(ThreadCount.sum);
    }
}

public class ThreadCount implements Runnable {

    private int start;
    private int end;
    static int sum;//学习到了，在线程中，就算new了新的对象也能储存新的值。
    private int count;

    ThreadCount(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = this.start; i <= this.end; i++) {
            this.count += i;
        }
        this.sum += this.count;
    }

}
```