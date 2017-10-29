package abstractFactory;

/**
 * Created by youzeliang on 2017/10/29.
 */
public class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}
