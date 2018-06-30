package net.bplaced.greench.weather;

public class AppUtils {

    public static String getTimeMillis() {
        Long tsLong = System.currentTimeMillis();
        return tsLong.toString();
    }
}
