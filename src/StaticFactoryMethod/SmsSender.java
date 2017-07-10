package StaticFactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */

/**
 * create the implement class
 */
public class SmsSender implements Sender {
    public void send() {
        System.out.println("This is a sms sender");
    }
}
