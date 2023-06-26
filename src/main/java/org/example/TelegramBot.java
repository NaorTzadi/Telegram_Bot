package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;
import java.util.function.Function;
import static org.example.Utility.checkForFixableSpellingError;
import static org.example.Utility.extractValidDateFormat;
public class TelegramBot extends TelegramLongPollingBot {
    Map<Long, Users> users = new HashMap<>();
    Constants constants=new Constants();
    Map<Long, Instant> lastInteractionTimes = new HashMap<>();
    private final NormEnhancedUserExperience normEnhancedUserExperience=new NormEnhancedUserExperience();


    public void onUpdateReceived(Update update) {
        long chatIdByLong;
        String chatIdByString;
        String username;
        Users user;


        if(update.hasCallbackQuery()){
            chatIdByLong=update.getCallbackQuery().getMessage().getChatId();
            chatIdByString=String.valueOf(chatIdByLong);
            user = users.get(chatIdByLong);
            username=user.getUsername();
            SendMessage response=new SendMessage();
            response.setChatId(chatIdByLong);
            response.setText("");
            if(update.getCallbackQuery().getData().equals("return")){sendOptionButtons(chatIdByString);return;
            } else if (update.getCallbackQuery().getData().equals(Constants.JOKES_OPTION)) {sendJokeOptionButtons(chatIdByString);return;
            } else if (update.getCallbackQuery().getData().equals(Constants.COUNTRIES_OPTION)) {sendCountriesOptionsPrompt(user,chatIdByString);return;
            } else if (update.getCallbackQuery().getData().equals(Constants.NEWS_OPTION)) {sendNewsOptionPrompt(user,chatIdByString);return;
            } else if (update.getCallbackQuery().getData().equals(Constants.NUMBERS_OPTION)) {sendNumbersOptionButtons(chatIdByString);return;}
            else if (update.getCallbackQuery().getData().equals(Constants.NASA_OPTION)) {sendNasaOptionPrompt(user,chatIdByString);return;}

            for (List<InlineKeyboardButton> row :constants.getJokeOptionButtons(chatIdByString)) {
                for (InlineKeyboardButton button : row) {
                    if (update.getCallbackQuery().getData().equals(button.getText())) {
                        JokesAPI jokesAPI=new JokesAPI();
                        response=new SendMessage(chatIdByString,jokesAPI.getJokeBasedOnUserOption(button.getText(), chatIdByString));
                        UsersStatistics.addRequest(Constants.JOKES_OPTION);
                        History.addHistory(new History(username,Constants.JOKES_OPTION,Utility.getFormattedTime()));
                        user.setShouldCheckForResponseAfterJoke(true);
                    }
                }
            }
            for (SendMessage message:constants.getNumbersOptions(chatIdByString)) {
                if (update.getCallbackQuery().getData().equals(message.getText())) {
                    if(update.getCallbackQuery().getData().equals("date")){
                        response.setText("choose a date in this format: MM/DD");} else {response.setText("choose a number");}
                    user.setNumbersCallBackQueryData(update.getCallbackQuery().getData());
                    user.setIsNumbersOptionActive(true);
                }

            }
            if(!response.getText().equals("")){try {execute(response);} catch (TelegramApiException e) {e.printStackTrace();}}
        }


        if (update.hasMessage() && update.getMessage().hasText()) {
            chatIdByLong=update.getMessage().getChatId();
            chatIdByString=String.valueOf(chatIdByLong);
            String messageText = update.getMessage().getText().toLowerCase();
            if(users.get(chatIdByLong)==null){
                user=new Users(update.getMessage().getFrom().getFirstName(),chatIdByLong);
                users.put(chatIdByLong,user);
            }else {
                user=users.get(chatIdByLong);
            }

            username=user.getUsername();
            UsersStatistics.addActiveUser(username);


            if ("/start".trim().toLowerCase().equals(messageText) && !user.isHasChatStarted()) {
                SendMessage welcomeMessage = new SendMessage(chatIdByString, "Hello "+username+", my name is norm.\uD83D\uDD90");try {execute(welcomeMessage);} catch (TelegramApiException e) {e.printStackTrace();}
                SendMessage description = new SendMessage(chatIdByString, "you can check my profile description for more info");try {execute(description);} catch (TelegramApiException e) {e.printStackTrace();}
                user.setHasChatStarted(true);return;
            }

            if(!user.isShouldCheckForResponseAfterJoke()){sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::checkForLaughterAndReply,user);}
            if(user.isShouldCheckForResponseAfterJoke()){
                String answer="";
                if(messageText.contains("not funny")){answer="blame the API not the Bot";}else if (messageText.contains("funny") || messageText.contains("lol") || messageText.contains("haha")) {answer="im glad to hear you enjoyed it.";}
                if(!answer.equals("")){SendMessage response=new SendMessage(chatIdByString,answer);try {execute(response);return;} catch (TelegramApiException e) {e.printStackTrace();}}
                user.setShouldCheckForResponseAfterJoke(false);
            }

            if(user.isHasLeft()){SendMessage message=new SendMessage(chatIdByString,"i thought you left.");try {execute(message); user.setHasLeft(false);return;}catch (TelegramApiException e){e.printStackTrace();}}

            sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::getRandomBotAnswersForWhenBeingCalledByHisName,user);
            sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::checkForGreetingsAndReply1,user);
            sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::checkForGreetingAndReply2,user);
            sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::checkForEmojisAndReplyWithEmoji,user);
            sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::checkForByeAndReply,user);
            sendResponseAndGreetIfNotEmpty(chatIdByString, messageText, normEnhancedUserExperience::checkForPurposeQuestioningAndReply,user);

            processNegativeOrPositiveFeedback(chatIdByString, normEnhancedUserExperience.checkForGratitudeAndReply(messageText),user);
            processNegativeOrPositiveFeedback(chatIdByString, normEnhancedUserExperience.checkForNegativeFeedBack(messageText),user);
            processNegativeOrPositiveFeedback(chatIdByString, normEnhancedUserExperience.checkForPositiveFeedBackAndReply(messageText),user);

            if(!user.isNasaOptionActive() && !user.isNumbersOptionActive() && !user.isNewsOptionActive()){if(!checkIfMessageTextIsValid(messageText,chatIdByString)){return;}}
            if(!user.hasGreeted()){user.setHasGreeted(didUserGreetYou(messageText));}
            if (shouldSendGreeting(chatIdByLong) && !user.hasGreeted()){SendMessage greetingMessage = new SendMessage(chatIdByString, Utility.getGreeting());try {execute(greetingMessage);} catch (TelegramApiException e) {e.printStackTrace();}lastInteractionTimes.put(chatIdByLong, Instant.now());}

            checkForUserGreeting(messageText,chatIdByString);

            if(checkForFixableSpellingError("guide",messageText).equals("guide")){SendMessage guide=new SendMessage(chatIdByString,Constants.BOT_INSTRUCTIONS);try {execute(guide);return;} catch (TelegramApiException e) {e.printStackTrace();}}

            if(!checkForFixableSpellingError(Constants.NUMBERS_OPTION,messageText).isEmpty() && user.getNumbersCallBackQueryData().equals("")){
                resetBooleansToFalse(user);
                user.setIsNumbersOptionActive(true);
                SendMessage options=new SendMessage(chatIdByString,"choose one option and type a number:");
                try {execute(options);} catch (TelegramApiException e) {e.printStackTrace();}
                for(SendMessage sendMessage:constants.getNumbersOptions(chatIdByString)){
                    try {execute(sendMessage);} catch (TelegramApiException e) {e.printStackTrace();}
                }
            }
            if(user.isNumbersOptionActive()){
                boolean contains=false;
                if(!user.getNumbersCallBackQueryData().equals("")){
                    contains=true;
                    messageText= user.getNumbersCallBackQueryData() +" "+messageText;
                }else {
                    String chosenOption=Utility.extractFirstGroupOfWords(messageText);
                    String chosenNumber=NumbersAPI.extractFirstDigitsGroup(messageText);
                    for(SendMessage sendMessage:constants.getNumbersOptions(chatIdByString)){
                        String chosenOptionAfterFix=checkForFixableSpellingError(sendMessage.getText(),chosenOption);
                        if(chosenOptionAfterFix.equals(sendMessage.getText())){
                            contains=true;
                            messageText=chosenOptionAfterFix+" "+chosenNumber;
                            break;
                        }
                    }
                }
                if(contains){
                    String response=NumbersAPI.activateNumbersAPIBasedOnUserNumberInput(messageText);
                    if(!response.equals("")){
                        SendMessage answer=new SendMessage(chatIdByString, response);
                        try {execute(answer);History.addHistory(new History(username,Constants.NUMBERS_OPTION,Utility.getFormattedTime()));
                            UsersStatistics.addRequest(Constants.NUMBERS_OPTION);} catch (TelegramApiException e) {e.printStackTrace();}
                        user.setIsNumbersOptionActive(false);
                        user.setNumbersCallBackQueryData("");
                    }
                }
            }

            if(user.isJokeOptionsActive()){sendJokeBasedOnUserOption(messageText,user,chatIdByString);
                UsersStatistics.addRequest(Constants.JOKES_OPTION);user.setIsJokesOptionActive(false);}
            if(!checkForFixableSpellingError(Constants.JOKES_OPTION,messageText).isEmpty()){resetBooleansToFalse(user);user.setIsJokesOptionActive(true);sendJokeOptions(chatIdByString);}
            if(messageText.contains("time") && messageText.contains("?")){SendMessage time=new SendMessage(chatIdByString,"the time is: "+Utility.getFormattedTime().substring(10)+" but you already know that.");try {execute(time);} catch (TelegramApiException e){e.printStackTrace();}}

            if(!user.isCountryOptionActive() && !checkForFixableSpellingError(Constants.COUNTRIES_OPTION,messageText.toLowerCase()).isEmpty()){
                if(messageText.length()>10) {
                    CountriesAPI countriesAPI=new CountriesAPI();
                    String countryInfo = countriesAPI.getCountryInfo(messageText.substring(10));
                    if (!countryInfo.isEmpty()) {
                        SendMessage answer = new SendMessage(chatIdByString, countryInfo);
                        try {execute(answer);History.addHistory(new History(username,Constants.COUNTRIES_OPTION,Utility.getFormattedTime()));} catch (TelegramApiException e) {e.printStackTrace();}return;}
                }else {
                    resetBooleansToFalse(user);
                    user.setIsCountryOptionsActive(true);
                    sendCountriesOptionsPrompt(user,chatIdByString);return;
                }
            }
            if(user.isCountryOptionActive()){
                user.setIsCountryOptionsActive(false);
                CountriesAPI countriesAPI=new CountriesAPI();
                SendMessage countryInfo=new SendMessage(chatIdByString, countriesAPI.getCountryInfo(messageText));
                try {execute(countryInfo);History.addHistory(new History(username,Constants.COUNTRIES_OPTION,Utility.getFormattedTime()));
                    UsersStatistics.addRequest(Constants.COUNTRIES_OPTION);} catch (TelegramApiException e) {e.printStackTrace();}
            }

            if(!checkForFixableSpellingError(Constants.NEWS_OPTION,messageText).isEmpty() || user.isIsNewsCallBackQueryData()){
                resetBooleansToFalse(user);
                sendNewsOptionPrompt(user,chatIdByString);
                return;
            }
            if(user.isNewsOptionActive()){
                NewsAPI newsAPI=new NewsAPI();
                String key= Utility.extractFirstGroupOfWords(messageText);
                String startDate = extractValidDateFormat(messageText);
                String endDate = extractValidDateFormat( messageText.substring(messageText.indexOf(startDate) + startDate.length()));
                if(!endDate.isEmpty()){
                    LocalDate start = LocalDate.parse(startDate);
                    LocalDate end = LocalDate.parse(endDate);
                    if (!start.isBefore(end)) { endDate = startDate; startDate = end.toString(); }
                }
                try {execute(new SendMessage(chatIdByString, newsAPI.getNewsBasedOnUserInput(key,startDate,endDate)));
                    History.addHistory(new History(username, Constants.NEWS_OPTION, Utility.getFormattedTime()));
                    UsersStatistics.addRequest(Constants.NEWS_OPTION);
                } catch (TelegramApiException e) {e.printStackTrace();}
                user.setIsNewsOptionActive(false);
            }

            if(user.isNasaOptionActive() && Objects.requireNonNull(extractValidDateFormat(messageText)).matches("(\\d{4}-\\d{2}-\\d{2})")){
                NasaAPI nasaAPI=new NasaAPI();
                user.setIsNasaOptionActive(false);
                SendMessage info=new SendMessage(chatIdByString,nasaAPI.activateNasaAPIBasedOnUserDateInput(extractValidDateFormat(messageText)));
                try {execute(info); History.addHistory(new History(username,Constants.NASA_OPTION,Utility.getFormattedTime()));
                    UsersStatistics.addRequest(Constants.NASA_OPTION);} catch (TelegramApiException e) {e.printStackTrace();}
                user.setIsNasaOptionActive(false);
                return;
            }

            if(!checkForFixableSpellingError(Constants.NASA_OPTION,messageText).equals("")){
                resetBooleansToFalse(user);
                sendNasaOptionPrompt(user,chatIdByString);
                return;
            }
            if (!checkForFixableSpellingError(Constants.MAIN_OPTION_OPTIONS,messageText).equals("")){sendOptionButtons(chatIdByString);}
            user.setHasGreeted(true);
        }


    }
    @Override
    public String getBotUsername() {return Constants.BOT_FULL_NAME;}
    @Override
    public String getBotToken() {return Constants.BOT_TOKEN;}

    private boolean shouldSendGreeting(long chatIdByLong) {
        if (!lastInteractionTimes.containsKey(chatIdByLong)) {
            return true;
        } else {
            Duration elapsedDuration = Duration.between(lastInteractionTimes.get(chatIdByLong), Instant.now());
            long elapsedMinutes = elapsedDuration.toMinutes();
            long minutesThreshold = 3 * 60;
            return elapsedMinutes >= minutesThreshold;
        }
    }


    private void sendOptionButtons(String chatIdByString) {
        SendMessage message = new SendMessage(chatIdByString,"choose an option");
        if(Menu.chosenOptions.isEmpty() || Menu.chosenOptions.size()<3){message.setText("sorry, it seems like someone forgot to adjust my options settings. \uD83D\uDC68\u200D\uD83D\uDCBB");try {execute(message);} catch (TelegramApiException e) {e.printStackTrace();}return;}
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(constants.getMainOptionButtons(chatIdByString));
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {execute(message);} catch (TelegramApiException e) {e.printStackTrace();}
    }
    private void sendJokeOptionButtons(String chatIdByString){
        SendMessage message = new SendMessage(chatIdByString,Constants.JOKES_OPTION+" "+Constants.MAIN_OPTION_OPTIONS+": ");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(constants.getJokeOptionButtons(chatIdByString));
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {execute(message);} catch (TelegramApiException e) {e.printStackTrace();}
    }

    private void sendNasaOptionPrompt(Users user,String chatIdByString){
        SendMessage promptDate=new SendMessage(chatIdByString,"choose a date YYYY-MM-DD");
        try {execute(promptDate);} catch (TelegramApiException e) {e.printStackTrace();} user.setIsNasaOptionActive(true);
    }

    private void sendCountriesOptionsPrompt(Users user,String chatIdByString){
        SendMessage promptCountry = new SendMessage(chatIdByString, "choose a country");
        try {execute(promptCountry);} catch (TelegramApiException e) {e.printStackTrace();}user.setIsCountryOptionsActive(true);
    }
    private void sendNewsOptionPrompt(Users user,String chatIdByString){
        SendMessage promptKeyWord=new SendMessage(chatIdByString,"choose a key word to search for");
        try {execute(promptKeyWord);} catch (TelegramApiException e) {e.printStackTrace();}
        SendMessage promptDate=new SendMessage(chatIdByString,"choose a date YYYY-MM-DD");
        try {execute(promptDate);} catch (TelegramApiException e) {e.printStackTrace();} user.setIsNewsOptionActive(true);
    }
    private void sendNumbersOptionButtons(String chatIdByString){
        SendMessage message = new SendMessage(chatIdByString,Constants.NUMBERS_OPTION+" "+Constants.MAIN_OPTION_OPTIONS+": ");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(constants.getNumbersOptionButtons(chatIdByString));
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {execute(message);} catch (TelegramApiException e) {e.printStackTrace();}
    }
    private  void sendJokeOptions(String chatIdByString){
        SendMessage options=new SendMessage(chatIdByString,Constants.JOKES_OPTION+" "+Constants.MAIN_OPTION_OPTIONS+": ");
        try {execute(options);} catch (TelegramApiException e) {e.printStackTrace();}
        for(SendMessage sendMessage:constants.getJokeOptions(chatIdByString).keySet()){
            try {execute(sendMessage);} catch (TelegramApiException e) {e.printStackTrace();}
        }
    }
    private void sendJokeBasedOnUserOption(String messageText, Users user,String chatIdByString){
        for (SendMessage sendMessage : constants.getJokeOptions(chatIdByString).keySet()) {
            String chosenOptionAfterFix=checkForFixableSpellingError(sendMessage.getText(),messageText);
            if (chosenOptionAfterFix.equals(sendMessage.getText())) {
                JokesAPI jokesAPI=new JokesAPI();
                String joke = jokesAPI.getJokeBasedOnUserOption(sendMessage.getText(),chatIdByString);// אפשר לבטל את זה ובמקום לשלוח בדיחה בהודעה אחת
                for (String line : getRearrangedJoke(joke)) {
                    SendMessage followUpMessage = new SendMessage(chatIdByString, line.trim());
                    try {execute(followUpMessage);} catch (TelegramApiException e) {e.printStackTrace();}
                    user.setIsJokesOptionActive(false);
                    user.setShouldCheckForResponseAfterJoke(true);
                }
                History.addHistory(new History(user.getUsername(),Constants.JOKES_OPTION,Utility.getFormattedTime()));
            }
        }
    }

    private void checkIfUserGreetingIsCorrect(String messageText,String chatIdByString) {
        String greeting = Utility.getGreeting().toLowerCase();String answer="";
        if (messageText.toLowerCase().contains("good morning") && !greeting.contains("good morning")) {answer = !greeting.contains("night") ? "it's not morning, it's " + greeting.substring(4) : "it's not morning, it's night.";
        } else if (messageText.contains("good afternoon") && !greeting.contains("good afternoon")) {answer = !greeting.contains("night") ? "it's not afternoon, it's " + greeting.substring(4) : "it's not afternoon, it's night.";
        } else if (messageText.contains("good evening") && !greeting.contains("good evening")) {answer = !greeting.contains("night") ? "it's not evening, it's " + greeting.substring(4) : "it's not evening, it's night.";
        } else if (messageText.contains("good night") && !greeting.contains("night")) {answer= "it's not night, it's " + greeting.substring(4);
        } else if (messageText.contains("good night")) {answer="good night to you too.";
        } else if (messageText.contains("good morning")) {answer= "good morning to you too.";
        } else if (messageText.contains("good afternoon")) {answer="good afternoon to you too.";
        } else if (messageText.contains("good evening")) {answer= "good evening to you too.";}
        SendMessage response=new SendMessage(chatIdByString,answer);try {execute(response);} catch (TelegramApiException e) {e.printStackTrace();}
    }
    private boolean checkIfMessageTextIsValid(String messageText,String chatIdByString) {
        String emojiPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
        if ((messageText.length() <= 1 || !messageText.matches(".*\\D.*")|| !messageText.matches(".*[a-zA-Z].*")) && !messageText.matches(emojiPattern)) {
            SendMessage syntaxError = new SendMessage(chatIdByString, "Sorry, I didn't quite get it.");
            try {execute(syntaxError);return false;} catch (TelegramApiException e) {e.printStackTrace();}
        }
        return true;
    }
    private void checkForUserGreeting(String messageText,String chatIdByString){
        if(messageText.contains("good morning") || messageText.toLowerCase().contains("good night") ||
                messageText.toLowerCase().contains("good evening") || messageText.contains("good afternoon")){
            checkIfUserGreetingIsCorrect(messageText,chatIdByString);
        }
    }
    private void sendResponseAndGreetIfNotEmpty(String chatId, String messageText, Function<String, String> messageFunction, Users user) {
        String responseText = messageFunction.apply(messageText);
        if (!responseText.equals("")) {
            SendMessage response = new SendMessage(chatId, responseText);
            try {execute(response); user.setHasGreeted(true);} catch (TelegramApiException e) {e.printStackTrace();}
        }
    }

    private void sendResponseAndGreetIfNotEmpty(String chatId, String messageText, BiFunction<String, Users, String> messageFunction, Users user) {
        String responseText = messageFunction.apply(messageText, user);
        if (!responseText.equals("")) {
            SendMessage response = new SendMessage(chatId, responseText);
            try {
                execute(response);
                user.setHasGreeted(true);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    private void processNegativeOrPositiveFeedback(String chatId, String textAfterCheck, Users user) {
        if (!textAfterCheck.equals("")) {
            if (user.hasGreeted()) {
                SendMessage response = new SendMessage(String.valueOf(chatId), textAfterCheck);
                try {execute(response);} catch (TelegramApiException e) {e.printStackTrace();}
            } else {
                SendMessage reply = new SendMessage(String.valueOf(chatId), "I haven't even sent anything yet.");
                try {execute(reply);user.setHasGreeted(true);} catch (TelegramApiException e) {e.printStackTrace();}
            }
        }
    }

    private void resetBooleansToFalse(Users user){user.setIsNumbersOptionActive(false);user.setIsCountryOptionsActive(false);user.setIsJokesOptionActive(false);user.setIsNewsOptionActive(false);}
    private static String[] getRearrangedJoke(String joke){if(joke.contains("...")){return joke.split("(?<=(\\.{3}|\\?|!))");}else {return joke.split("(?<=[.?])");}}
    private static boolean didUserGreetYou(String messageText) {return messageText.toLowerCase().contains("good morning") || messageText.toLowerCase().contains("good night") || messageText.toLowerCase().contains("good evening") || messageText.toLowerCase().contains("good afternoon");}

}