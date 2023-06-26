package org.example;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.Map;
import java.util.Optional;
public class JokesAPI {
    private final Constants constants=new Constants();
    /**הערה זאת פונה לשי: אם אתה רואה את זה, מצטער שאני שובר את הקיר הרביעי ככה אבל אני מאמין שקיימת בעיה עם ה-Jokes API.
     * לא משנה איזה כתובת Url אני נותן, הבדיחה המתקבלת אינה מתייחסת לסוג הבדיחה המצויין בכתובת url.**/
    public String getJokeBasedOnUserOption(String userOption,String chatId) {
        String urlBasedOnUserOption=null;
        Optional<Map.Entry<SendMessage, String>> matchingEntry = constants.getJokeOptions(chatId).entrySet().stream().filter(entry -> userOption.equals(entry.getKey().getText())).findFirst();
        if (matchingEntry.isPresent()) {urlBasedOnUserOption = matchingEntry.get().getValue();}

        // System.out.println(urlBasedOnUserOption); // מהירה url לבדיקת

        return Utility.getTextFromAPI(urlBasedOnUserOption);
    }
}