package StaticFactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */

/**
 * create the implements class
 */
public class MailSender implements Sender {
    public void send(){
        System.out.println("This is a mailsender");
    }
}
