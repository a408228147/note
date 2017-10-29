package abstractFactory;

/**
 * Created by youzeliang on 2017/10/29.
 */
public class Green implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}
