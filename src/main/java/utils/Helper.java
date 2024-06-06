package utils;

public class Helper {
    public static String formatString(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("\\s+");
        for (String s : searchArray) {
            result.append(s).append(" ");
        }
        return result.toString().trim();
    }
}
