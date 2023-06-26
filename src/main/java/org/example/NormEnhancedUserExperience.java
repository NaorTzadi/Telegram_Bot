package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import static org.example.Constants.BOT_NAME;
public class NormEnhancedUserExperience {
    public String getRandomBotAnswersForWhenBeingCalledByHisName(String messageText){
        String response="";
        boolean didFixedError= !messageText.equals(BOT_NAME) && Utility.checkForFixableSpellingError(BOT_NAME, messageText).equals(BOT_NAME);
        if(Utility.checkForFixableSpellingError(BOT_NAME,messageText).equals(BOT_NAME)){
            List<String> randomBotAnswersForWhenBeingCalledByHisName=new ArrayList<>();
            if(!didFixedError){
                randomBotAnswersForWhenBeingCalledByHisName.add("yes that's my name, how may i assist?");
                randomBotAnswersForWhenBeingCalledByHisName.add("how may i assist?");
                randomBotAnswersForWhenBeingCalledByHisName.add("Hello! You called? I'm Norm, ready to help you out!");
                randomBotAnswersForWhenBeingCalledByHisName.add("Norm at your service! What can I do for you?");
                randomBotAnswersForWhenBeingCalledByHisName.add("Hello there! It's me, Norm. How can I assist you today?");
                randomBotAnswersForWhenBeingCalledByHisName.add("Hey, you mentioned my name! How can I be of help?");
                Random random = new Random();
                response=randomBotAnswersForWhenBeingCalledByHisName.get(random.nextInt(randomBotAnswersForWhenBeingCalledByHisName.size()));
            }else {response="not exactly my name but ok, how can i help you?";}
        }
        return response;
    }

    public String checkForGratitudeAndReply(String messageText){
        String response="";
        if(messageText.contains("thank you")||messageText.contains("thanks")){
            List<String> randomGratitudeReplies=new ArrayList<>();
            randomGratitudeReplies.add("You're welcome! It was my pleasure to help.");
            randomGratitudeReplies.add("Glad I could assist you!");
            randomGratitudeReplies.add("No problem!");
            randomGratitudeReplies.add("You're very welcome!");
            randomGratitudeReplies.add("Glad I could be of assistance!");
            randomGratitudeReplies.add("You're most welcome!");
            randomGratitudeReplies.add("I'm pleased that I could help you out!");
            Random random = new Random();
            response=randomGratitudeReplies.get(random.nextInt(randomGratitudeReplies.size()));
        }
        return response;
    }

    public String checkForGreetingsAndReply1(String messageText) {
        String response="";
        if (messageText.contains("whats up") || messageText.contains("hello") || messageText.contains("good day") || messageText.contains("hey")) {
            List<String> randomBotAnswersForGreetings = new ArrayList<>();
            randomBotAnswersForGreetings.add("Hey there! How can I assist you today?");
            randomBotAnswersForGreetings.add("Hello! How are you doing?");
            randomBotAnswersForGreetings.add("Hi! What can I help you with?");
            randomBotAnswersForGreetings.add("Good day to you too! How may I be of service?");
            randomBotAnswersForGreetings.add("Hi there! How can I assist you today?");
            Random random = new Random();
            response=randomBotAnswersForGreetings.get(random.nextInt(randomBotAnswersForGreetings.size()));
        }
        return response;
    }
    public String checkForGreetingAndReply2(String messageText) {
        String response="";
        if (messageText.contains("are")&& messageText.contains("you") && (messageText.contains("ok")||messageText.contains("good")
                ||messageText.contains("fine")||messageText.contains("alright"))||messageText.contains("how")) {
            List<String> randomBotAnswersForWellBeing = new ArrayList<>();
            randomBotAnswersForWellBeing.add("I'm doing well, thank you! How about you?");
            randomBotAnswersForWellBeing.add("I'm good, thanks for asking! How can I assist you today?");
            randomBotAnswersForWellBeing.add("I'm functioning optimally! How can I be of help?");
            randomBotAnswersForWellBeing.add("I'm doing great! How can I assist you today?");
            randomBotAnswersForWellBeing.add("I'm fine, thank you! How may I assist you?");
            Random random = new Random();
            response=randomBotAnswersForWellBeing.get(random.nextInt(randomBotAnswersForWellBeing.size()));
        }
        return response;
    }
    public String checkForByeAndReply(String messageText,Users user){
        String response="";
        String lowercaseMessage=messageText.replace(" ","");
        if(lowercaseMessage.equals("bye") || lowercaseMessage.equals("goodbye")|| lowercaseMessage.equals("farewell") || lowercaseMessage.equals("seeya")
                || lowercaseMessage.equals("adios") || lowercaseMessage.equals("sayonara")){
            user.setHasLeft(true);
            if(!user.hasGreeted()){user.setHasGreeted(true);return "you just got here, but ok.";}
            List<String> randomBotAnswersForBye=new ArrayList<>();
            randomBotAnswersForBye.add("Goodbye!");
            randomBotAnswersForBye.add("See you later!");
            randomBotAnswersForBye.add("Farewell!");
            randomBotAnswersForBye.add("Have a great day!");
            randomBotAnswersForBye.add("Take care!");
            randomBotAnswersForBye.add("Until next time!");
            randomBotAnswersForBye.add("Looking forward to our next chat!");
            randomBotAnswersForBye.add("Goodbye, stay safe!");
            Random random = new Random();
            response = randomBotAnswersForBye.get(random.nextInt(randomBotAnswersForBye.size()));
        }
        return response;
    }
    public String checkForPositiveFeedBackAndReply(String messageText) {
        String response = "";
        String trimmedMessageText = messageText.replace(" ", "");
        if (trimmedMessageText.equals("good") || trimmedMessageText.equals("nice") || trimmedMessageText.equals("great")
                || trimmedMessageText.equals("welldone") || trimmedMessageText.equals("superb") || trimmedMessageText.equals("fantastic")
                || trimmedMessageText.equals("awesome") || trimmedMessageText.equals("brilliant")) {
            List<String> randomBotAnswersForCompliments = new ArrayList<>();
            randomBotAnswersForCompliments.add("Thank you!");
            randomBotAnswersForCompliments.add("I'm glad you think so!");
            randomBotAnswersForCompliments.add("I appreciate your kind words!");
            randomBotAnswersForCompliments.add("Thanks for your feedback!");
            randomBotAnswersForCompliments.add("Your words are encouraging!");
            randomBotAnswersForCompliments.add("That's very kind of you!");
            randomBotAnswersForCompliments.add("You made my day!");
            Random random = new Random();
            response = randomBotAnswersForCompliments.get(random.nextInt(randomBotAnswersForCompliments.size()));
        }
        return response;
    }
    public String checkForNegativeFeedBack(String messageText) {
        String response = "";
        Pattern negationPattern = Pattern.compile("\\b(not|isnt)\\b");
        Pattern responsePattern = Pattern.compile("\\b(good|ok|fine|alright|nice)\\b");
        if (negationPattern.matcher(messageText).find() && responsePattern.matcher(messageText).find()) {
            List<String> randomBotAnswersForNegativeFeedBack = new ArrayList<>();
            randomBotAnswersForNegativeFeedBack.add("Thank you for bringing this to my attention. I will make a note of it.");
            randomBotAnswersForNegativeFeedBack.add("I appreciate your feedback. I will take it into consideration.");
            randomBotAnswersForNegativeFeedBack.add("Noted. I will work on improving this.");
            randomBotAnswersForNegativeFeedBack.add("Thanks for letting me know. I'll make sure to address it.");
            randomBotAnswersForNegativeFeedBack.add("I understand. I'll do my best to make it better.");
            Random random = new Random();
            response = randomBotAnswersForNegativeFeedBack.get(random.nextInt(randomBotAnswersForNegativeFeedBack.size()));
        }
        return response;
    }
    public String checkForEmojisAndReplyWithEmoji(String messageText) {
        List<String> emojis = new ArrayList<>();
        StringBuilder reply = new StringBuilder();
        StringBuilder response= new StringBuilder();
        for (int i = 0; i < messageText.length(); i++) {
            int codepoint = messageText.codePointAt(i);
            if ((codepoint >= 0x1F600 && codepoint <= 0x1F64F) || // Emoticons
                    (codepoint >= 0x1F300 && codepoint <= 0x1F5FF) || // Miscellaneous Symbols and Pictographs
                    (codepoint >= 0x1F680 && codepoint <= 0x1F6FF) || // Transport and Map Symbols
                    (codepoint >= 0x2600 && codepoint <= 0x26FF) || // Miscellaneous Symbols
                    (codepoint >= 0x2700 && codepoint <= 0x27BF) || // Dingbats
                    (codepoint >= 0xFE00 && codepoint <= 0xFE0F) || // Variation Selectors
                    (codepoint >= 0x1F900 && codepoint <= 0x1F9FF) || // Supplemental Symbols and Pictographs
                    (codepoint >= 0x1F1E6 && codepoint <= 0x1F1FF)) // Flags
            {emojis.add(new String(Character.toChars(codepoint)));}
        }
        for (String emoji : emojis) {
            reply.append(emoji);
            response.append(emoji);
        }
        return response.toString();
    }
    public String checkForLaughterAndReply(String messageText){
        String response = "";
        if (messageText.contains("haha") || messageText.contains("lol") || messageText.contains("rofl")) {
            List<String> randomLaughterReplies = new ArrayList<>();
            randomLaughterReplies.add("what's so funny?");
            randomLaughterReplies.add("i dont understand what's amuses you.");
            randomLaughterReplies.add("i dont recall telling a joke.");
            Random random = new Random();
            response = randomLaughterReplies.get(random.nextInt(randomLaughterReplies.size()));
        }
        return response;
    }
    public String checkForPurposeQuestioningAndReply(String messageText){
        String response = "";
        if (messageText.contains("who are you") || messageText.contains("what do you do") || messageText.contains("what can you do")||
                messageText.contains("what are you") || messageText.contains("what to do")||messageText.contains("help")) {
            List<String> randomQuestionReplies = new ArrayList<>();
            randomQuestionReplies.add("I'm Norm, your friendly chatbot! Type guide and send for more info.");
            randomQuestionReplies.add("Hello, I'm Norm! Type guide and send for more info.");
            randomQuestionReplies.add("As a chatbot, I can provide you with a variety of services.Type guide for more info.");
            randomQuestionReplies.add("I'm Norm, an AI developed to help users like you.Type guide for more info.");
            Random random = new Random();
            response = randomQuestionReplies.get(random.nextInt(randomQuestionReplies.size()));
        }
        return response;
    }







}