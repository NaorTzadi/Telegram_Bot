package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Utility {
    private static final String greeting1="Good morning!";
    private static final String greeting2="Good afternoon!";
    private static final String greeting3="Good evening!";
    private static final String greeting4="hello, hope your night is going well.";
    private static final String greeting5="wow, you must be a night owl.\uD83E\uDD89";
    public static boolean isConnected(){
        try {InetAddress address = InetAddress.getByName("8.8.8.8");return address.isReachable(5000);
        } catch (UnknownHostException e) {System.out.println("Unknown host: " + e.getMessage());return false;
        } catch (Exception e) {System.out.println("An error occurred: " + e.getMessage());return false;}
    }
    public static String getGreeting() {
        LocalDateTime currentTime = LocalDateTime.now();int hour = currentTime.getHour();String greeting;
        if (hour >= 5 && hour < 12) {greeting = greeting1;
        } else if (hour >= 12 && hour < 18) {greeting = greeting2;
        } else if (hour>=18 && hour<22){greeting = greeting3;
        }else if (hour>=22){greeting=greeting4;
        }else {greeting=greeting5;}
        return greeting;
    }

    public static String getFormattedTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return currentTime.format(formatter);
    }

    public static String extractValidDateFormat(String messageText) {
        String regex = "(\\d{4}-\\d{2}-\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageText);
        if (matcher.find()) {
            String dateStr = matcher.group(1);
            String[] dateParts = dateStr.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            if (isValidDate(year, month, day)) {
                return dateStr;
            } else {
                return "";
            }
        }else {
            return "";}
    }
    private static boolean isValidDate(int year, int month, int day) {int currentYear = LocalDate.now().getYear();return year <= currentYear && month <= 12 && day <= 32;}

    public static String extractFirstGroupOfWords(String input) {
        StringBuilder result = new StringBuilder();
        boolean inWord = false;
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c) || c == ' ') {
                result.append(c);
                inWord = true;
            } else {
                if (inWord) {
                    break;
                }
            }
        }
        return result.toString().trim();
    }
    public static String getTextFromAPI(String urlFromGivenAPIClass){
        StringBuilder response=new StringBuilder();
        String message="";
        try {
            URL url=new URL(urlFromGivenAPIClass);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {response.append(line);}
            reader.close();
            message=response.toString();
            reader.close();connection.disconnect();
        }catch (IOException e) {e.printStackTrace();}
        return message;
    }
    public static String checkForFixableSpellingError(String original, String userInput) {
        userInput = userInput.toLowerCase();
        original = original.toLowerCase();
        int lenOrig = original.length();
        int lenUser = userInput.length();
        if (userInput.equals(original)) return original;
        if (Math.abs(lenOrig - lenUser) > 1) return "";
        int mismatchCount = 0;
        for (int i = 0, j = 0; i < lenOrig && j < lenUser;) {
            if (original.charAt(i) != userInput.charAt(j)) {
                mismatchCount++;
                if (mismatchCount > 1) return "";
                if (lenOrig < lenUser) j++;
                else if (lenOrig > lenUser) i++;
                else { i++; j++; }
            } else {
                i++; j++;
            }
        }
        if (lenOrig != lenUser && mismatchCount == 0) mismatchCount++;
        if (mismatchCount == 1) return original;
        return "";
    }

}