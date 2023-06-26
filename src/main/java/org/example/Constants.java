package org.example;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.*;
import java.util.stream.Collectors;
public class Constants {
    public static final String BOT_TOKEN="6032499312:AAHCaczBe76Vu0_Y8WjeiyU5eY5n-R40vls";
    public static final String BOT_FULL_NAME="NormTheCollectorBot";
    public static final String BOT_NAME="norm";
    public static final String MAIN_OPTION_OPTIONS="options";
    public static final String JOKES_OPTION="jokes";
    public static final String NUMBERS_OPTION="numbers";
    public static final String NASA_OPTION="nasa";
    public static final String NEWS_OPTION="news";
    public static final String COUNTRIES_OPTION="countries";
    public static final String JOKES_OPTION_NSFW="nsfw";
    public static final String JOKES_OPTION_RELIGIOUS="religious";
    public static final String JOKES_OPTION_POLITICAL="political";
    public static final String JOKES_OPTION_RACIST="racist";
    public static final String JOKES_OPTION_SEXIST="sexist";
    public static final String JOKES_OPTION_EXPLICIT="explicit";
    public static final String NUMBERS_OPTION_TRIVIA="trivia";
    public static final String NUMBERS_OPTION_MATH="math";
    public static final String NUMBERS_OPTION_DATE="date";
    public static final String NUMBERS_OPTION_YEAR="year";
    public static final String MENU_IMAGE_DISCONNECTED_PATH="resources/disconnectedIcon.png";
    public static final  String MENU_IMAGE_BACKGROUND_PATH="resources/telegram-bot-background.jpg";
    public static final String MENU_IMAGE_LOGO_PATH="resources/telegram-logo.png";
    public static final int MENU_FRAME_WIDTH=400;
    public static final int MENU_FRAME_HEIGHT=600;
    public static final int MENU_BUTTON_OPTION_WIDTH=200;
    public static final int MENU_COMPONENT_STANDARD_HEIGHT =30;
    public static final String BOT_INSTRUCTIONS= """
            by sending "options" via message, three options would be presented (as buttons) based on the options settings that were adjusted to the bot. in general there are five options this bot can do.\s

            Option 1->Countries- >give a name of a country-> get the info about the country.

            Option 2->news-> give a name of a subject and two dates to set the range of search by date.\s
            Keep in mind that the current API the bot uses for the news only allows up to about 33 days backwards.
            You can also type just one date to check specifically for that date. You can also not send a date at all, just a subject, in this case the bot would return the first article it finds within the max range it could truck backwards up to the current date.

            Option 3->jokes->if accessed by button, a menu of joke button types would appear, simply press on the desired button that is the joke type you want to receive.
            Option 3->jokes-> if accessed by messaging “jokes”, a menu would appear, simply type and send the type of joke you want to receive.\s
            Do note that the current API the bot uses for the jokes has some issues so you might not always get the joke type you picked.

            Options 4-> numbers-> if accessed by button, a menu of numbers options would appear, simply press the one you desire and follow the instructions that would be given by the bot.
            Option 4->numbers-> if accessed by messaging “numbers”, a menu would appear, simply type the number option you want and follow the bot instructions.

            Option 5-> NASA-> give a single date and get info regarding as to what happened in NASA at that date.

            Mention: all options could be accessed by also typing the name of the option. Once you type it, the bot would give you the instructions.
            Mention: date formats are:
             YYYY/MM/DD\s
            YYYY.MM.DD\s
             YYYY\\MM\\DD\s
             YYYY-MM-DD\s
            You can also get these instruction from the bot by messaging “instructions”
            """;
    public HashMap<SendMessage,String> getJokeOptions(String chatId){
        HashMap<SendMessage,String> jokeOptions=new HashMap<>();
        SendMessage option2=new SendMessage(chatId,JOKES_OPTION_NSFW);
        SendMessage option3=new SendMessage(chatId,JOKES_OPTION_RELIGIOUS);
        SendMessage option4=new SendMessage(chatId,JOKES_OPTION_POLITICAL);
        SendMessage option5=new SendMessage(chatId,JOKES_OPTION_RACIST);
        SendMessage option6=new SendMessage(chatId,JOKES_OPTION_SEXIST);
        SendMessage option7=new SendMessage(chatId,JOKES_OPTION_EXPLICIT);
        jokeOptions.put(option2,"https://v2.jokeapi.dev/joke/Any?blacklistFlags="+JOKES_OPTION_NSFW+"&format=txt");
        jokeOptions.put(option3,"https://v2.jokeapi.dev/joke/Any?blacklistFlags="+JOKES_OPTION_RELIGIOUS+"&format=txt");
        jokeOptions.put(option4,"https://v2.jokeapi.dev/joke/Any?blacklistFlags="+JOKES_OPTION_POLITICAL+"&format=txt");
        jokeOptions.put(option5,"https://v2.jokeapi.dev/joke/Any?blacklistFlags="+JOKES_OPTION_RACIST+"&format=txt");
        jokeOptions.put(option6,"https://v2.jokeapi.dev/joke/Any?blacklistFlags="+JOKES_OPTION_SEXIST+"&format=txt");
        jokeOptions.put(option7,"https://v2.jokeapi.dev/joke/Any?blacklistFlags="+JOKES_OPTION_EXPLICIT+"&format=txt");
        return jokeOptions;
    }
    public List<SendMessage> getNumbersOptions(String chatId){
        List<SendMessage> numbersOptions=new ArrayList<>();
        numbersOptions.add(new SendMessage(String.valueOf(chatId),NUMBERS_OPTION_TRIVIA));
        numbersOptions.add(new SendMessage(String.valueOf(chatId),NUMBERS_OPTION_MATH));
        numbersOptions.add(new SendMessage(String.valueOf(chatId),NUMBERS_OPTION_DATE));
        numbersOptions.add(new SendMessage(String.valueOf(chatId),NUMBERS_OPTION_YEAR));
        return numbersOptions;
    }
    private List<List<InlineKeyboardButton>> getOptionButtons(String chatId,String option) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        switch (option) {
            case JOKES_OPTION -> {
                int row1Limit = 3;
                int row2Skip = 3;
                HashMap<SendMessage, String> jokeOptions = getJokeOptions(chatId);
                List<InlineKeyboardButton> row1 = jokeOptions.keySet().stream().limit(row1Limit).map(message -> createButton(message.getText())).collect(Collectors.toList());
                List<InlineKeyboardButton> row2 = jokeOptions.keySet().stream().skip(row2Skip).map(message -> createButton(message.getText())).collect(Collectors.toList());
                keyboard.add(row1);
                keyboard.add(row2);
            }
            case NUMBERS_OPTION -> {
                int row1Limit = 2;
                int row2Skip = 2;
                List<SendMessage> numberOptions = getNumbersOptions(chatId);
                List<InlineKeyboardButton> row1 = numberOptions.stream().limit(row1Limit).map(message -> createButton(message.getText())).collect(Collectors.toList());
                List<InlineKeyboardButton> row2 = numberOptions.stream().skip(row2Skip).map(message -> createButton(message.getText())).collect(Collectors.toList());
                keyboard.add(row1);
                keyboard.add(row2);
            }
            case MAIN_OPTION_OPTIONS -> {
                int row1Limit = 0;
                int row2Skip = 0;
                List<InlineKeyboardButton> row1 = Menu.chosenOptions.stream().limit(row1Limit).map(radioButton -> createButton(radioButton.getText())).collect(Collectors.toList());
                List<InlineKeyboardButton> row2 = Menu.chosenOptions.stream().skip(row2Skip).map(radioButton -> createButton(radioButton.getText())).collect(Collectors.toList());
                keyboard.add(row1);
                keyboard.add(row2);
                return keyboard;
            }
        }
        keyboard.add(getReturnButton());
        return keyboard;
    }
    private InlineKeyboardButton createButton(String message) {
        InlineKeyboardButton button = new InlineKeyboardButton(message);
        button.setCallbackData(message);
        return button;
    }
    private List<InlineKeyboardButton> getReturnButton() {
        InlineKeyboardButton returnButton = new InlineKeyboardButton("   << return >>   ");
        returnButton.setCallbackData("return");
        return Collections.singletonList(returnButton);
    }
    public List<List<InlineKeyboardButton>> getJokeOptionButtons(String chatId) {return getOptionButtons(chatId,JOKES_OPTION);}
    public List<List<InlineKeyboardButton>> getNumbersOptionButtons(String chatId) {return getOptionButtons(chatId,NUMBERS_OPTION);}
    public List<List<InlineKeyboardButton>> getMainOptionButtons(String chatId) {return getOptionButtons(chatId,MAIN_OPTION_OPTIONS);}
}