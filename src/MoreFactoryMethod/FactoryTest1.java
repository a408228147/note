package MoreFactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */
public class FactoryTest1 {
    public static void main(String[] args) {
        SendFactory1 factory = new SendFactory1();
        Sender1 sender = factory.produceMail();
        sender.send();
    }
}
