package abstractFactory;

/**
 * Created by youzeliang on 2017/10/29.
 */
public abstract class AbstractFactory {
    abstract Color getColor(String color);
    abstract Shape getShape(String shape) ;
}
