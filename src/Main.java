import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static int maxSize = 0;
    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String text;
                text = generateRoute("RLRFR", 100);
                Matcher m = Pattern.compile("(R)\\1+").matcher(text);
                while (m.find()) {
                    String sub = m.group();
                    if (maxSize < sub.length()) {
                        maxSize = sub.length();
                    }
                    synchronized (sizeToFreq) {
                        if (!sizeToFreq.containsKey(sub.length())) {
                            sizeToFreq.put(sub.length(), 1);
                        } else {
                            sizeToFreq.put(sub.length(), sizeToFreq.get(sub.length()) + 1);
                        }
                    }
                }

            }).start();
        }

        System.out.println("Самое частое количество повторений " + maxSize + " (встретилось " + sizeToFreq.get(maxSize) + " раз(а))");
        System.out.println("Другие размеры: " + sizeToFreq);

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}