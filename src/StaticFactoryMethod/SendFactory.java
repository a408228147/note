package StaticFactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */
public class SendFactory {
    public static Sender produceMail(){
        return new MailSender();
    }

    public static Sender produceSms(){
        return new SmsSender();
    }
}
