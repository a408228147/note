package abstractFactory;

/**
 * Created by youzeliang on 2017/10/29.
 */
public class Red implements Color{
    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}
