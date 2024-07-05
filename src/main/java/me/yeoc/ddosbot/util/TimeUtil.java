package me.yeoc.ddosbot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {
    public static long parseTime(String source) {


        long totalTime = 0L;
        boolean found = false;
        Matcher matcher = Pattern.compile("\\d+\\D+").matcher(source);

        while (matcher.find()) {
            String s = matcher.group();
            Long value = Long.parseLong(s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0]);
            String type = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1];

            switch (type) {
                case "s":
                    totalTime += value;
                    found = true;
                    break;
                case "m":
                    totalTime += value * 60;
                    found = true;
                    break;
                case "h":
                    totalTime += value * 60 * 60;
                    found = true;
                    break;
                case "d":
                    totalTime += value * 60 * 60 * 24;
                    found = true;
                    break;
                case "w":
                    totalTime += value * 60 * 60 * 24 * 7;
                    found = true;
                    break;
                case "M":
                    totalTime += value * 60 * 60 * 24 * 30;
                    found = true;
                    break;
                case "y":
                    totalTime += value * 60 * 60 * 24 * 365;
                    found = true;
                    break;
            }
        }

        return !found ? -1 : totalTime * 1000;
    }
    public static String getTime(int seconds) {
        if (seconds < 60) {
            return seconds + "秒";
        } else {
            int minutes = seconds / 60;
            int s = 60 * minutes;
            int secondsLeft = seconds - s;
            if (minutes < 60) {
                return secondsLeft > 0 ? minutes + "分钟" + secondsLeft + "秒" : minutes + "分钟";
            } else {
                String time;
                int days;
                int inMins;
                int leftOver;
                if (minutes < 1440) {
                    days = minutes / 60;
                    time = days + "h";
                    inMins = 60 * days;
                    leftOver = minutes - inMins;
                    if (leftOver >= 1) {
                        time = time + " " + leftOver + "分钟";
                    }

                    if (secondsLeft > 0) {
                        time = time + " " + secondsLeft + "秒";
                    }

                    return time;
                } else {
                    days = minutes / 1440;
                    time = days + "天";
                    inMins = 1440 * days;
                    leftOver = minutes - inMins;
                    if (leftOver >= 1) {
                        if (leftOver < 60) {
                            time = time + " " + leftOver + "分钟";
                        } else {
                            int hours = leftOver / 60;
                            time = time + " " + hours + "小时";
                            int hoursInMins = 60 * hours;
                            int minsLeft = leftOver - hoursInMins;
                            time = time + " " + minsLeft + "分钟";
                        }
                    }

                    if (secondsLeft > 0) {
                        time = time + " " + secondsLeft + "秒";
                    }

                    return time;
                }
            }
        }
    }
}
