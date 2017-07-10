package StaticFactoryMethod;

/**
 * Created by admin on 2017/7/10.
 */
public class FactoryTest {
    public static void main(String[] args) {
        Sender sender = SendFactory.produceMail();
        sender.send();
    }
}
