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
            new Thread(() ->{
                String text;
                int count = 0;
                text = generateRoute("RLRFR", 100);
                for (char ch : text.toCharArray()) {
                    if (ch == 'R'){
                        count++;
                    }
//                    System.out.println("Число поворотов направо в строке " + text.substring(0, 10) + "... -> " + " равно: " + count);
                }
                synchronized (sizeToFreq){
                    int countR = 0;
                    Matcher m = Pattern.compile("(.)\\1+").matcher(text);
                    while (m.find()){
                        countR++;
                        String sub = m.group();
                        if (sub.length() > maxSize){
                            maxSize = sub.length();
                        }
                        if (!sizeToFreq.containsKey(sub.length())){
                            sizeToFreq.put(sub.length(), countR);
                        }
                        else {
                            sizeToFreq.put(sub.length(), countR + 1);
                        }
                    }
                    sizeToFreq.notify();
                }
            }).start();
        }
        synchronized (sizeToFreq){
            System.out.println(sizeToFreq);
            try {
                sizeToFreq.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Самое частое количество повторений " + maxSize + " (встретилось " + sizeToFreq.get(maxSize) + " раз)");
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