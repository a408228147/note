package MoreFactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */
public class SendFactory1 {
    public Sender1 produceMail(){
        return new MailSender1();
    }

    public Sender1 produceSms(){
        return new SmsSender1();
    }
}
