package abstractFactory;

/**
 * Created by youzeliang on 2017/10/29.
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}