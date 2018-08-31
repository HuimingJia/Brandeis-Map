package BrandeisMap.utils;

public class TimeUtil {
    public static String translateTime(double T) {
        int time = (int)Math.round(T);
        if (time < 60) {
            return "" + time + " seconds";
        } else {
            return "" + Math.round(((double)time / 60) * 10.0) / 10.0 + " minutes";
        }
    }
}
