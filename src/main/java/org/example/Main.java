package org.example;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
public class Main {
    public static void main(String[] args){
        Menu menu=new Menu();
        try {TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);bot.registerBot(new TelegramBot());}
        catch (TelegramApiException e) {throw new RuntimeException(e);}
    }
}