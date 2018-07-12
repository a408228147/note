import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Test {
    public static List<String> compareTest1() {
        List<String> wordList = Arrays.asList("lianggzone", "spring", "summer", "autumn", "winter");
        wordList.sort(new Comparator<String>() {
            @Override
            public int compare(String w1, String w2) {
                return Integer.compare(w1.length(), w2.length());
            }
        });
        return wordList;

    }
}
