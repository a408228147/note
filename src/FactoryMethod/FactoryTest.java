package FactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */
public class FactoryTest {
    public static void main(String[] args) {
        SendFactory sendFactory = new SendFactory();
        Sender sender =  sendFactory.produce("sms");
        sender.send();
    }
}
