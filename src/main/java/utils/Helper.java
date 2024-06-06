package utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Helper {
    public static String formatString(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("\\s+");
        for (String s : searchArray) {
            result.append(s).append(" ");
        }
        return result.toString().trim();
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year, month, dayOfMonth);
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
