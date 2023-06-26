package org.example;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class NumbersAPI {
    public static String activateNumbersAPIBasedOnUserNumberInput(String messageText){
        if(extractTypeAndNumber(messageText).equals("")){return "";}
        String urlBasedOnUserNumberInput="http://numbersapi.com/"+ extractTypeAndNumber(messageText);
        return Utility.getTextFromAPI(urlBasedOnUserNumberInput);
    }
    public static String extractFirstDigitsGroup(String input) {
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
    private static String extractTypeAndNumber(String messageText) {
        Pattern triviaPattern = Pattern.compile("\\b"+ Constants.NUMBERS_OPTION_TRIVIA +"\\b", Pattern.CASE_INSENSITIVE);
        Pattern mathPattern = Pattern.compile("\\b"+ Constants.NUMBERS_OPTION_MATH +"\\b", Pattern.CASE_INSENSITIVE);
        Pattern datePattern = Pattern.compile("\\b"+ Constants.NUMBERS_OPTION_DATE +"\\b", Pattern.CASE_INSENSITIVE);
        Pattern yearPattern = Pattern.compile("\\b"+ Constants.NUMBERS_OPTION_YEAR +"\\b", Pattern.CASE_INSENSITIVE);
        if (triviaPattern.matcher(messageText).find()) {return extractFirstDigitsGroup(messageText) + "/" + Constants.NUMBERS_OPTION_TRIVIA;
        } else if (mathPattern.matcher(messageText).find()) {return extractFirstDigitsGroup(messageText) + "/" + Constants.NUMBERS_OPTION_MATH;
        } else if (datePattern.matcher(messageText).find()) {return extractDatePattern(messageText) + "/" + Constants.NUMBERS_OPTION_DATE;
        } else if (yearPattern.matcher(messageText).find()) {return extractFirstDigitsGroup(messageText) + "/" + Constants.NUMBERS_OPTION_YEAR;
        } else {return "";}
    }
    private static String extractDatePattern(String input) {
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            int firstNumber = Integer.parseInt(matcher.group(1));
            int secondNumber = Integer.parseInt(matcher.group(2));
            if (firstNumber <= 12 && secondNumber <= 32) {
                return matcher.group();
            }
        }
        return "";
    }


}