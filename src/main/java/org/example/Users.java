package org.example;
public class Users {
    private String username;
    private final Long chatId;
    private boolean isNasaOptionActive;
    private boolean isJokeOptionsActive;
    private boolean isCountryOptionActive;
    private boolean isNumbersOptionActive;
    private boolean isNewsOptionActive;
    private boolean isHasLeft;
    private boolean shouldCheckForResponseAfterJoke;
    private boolean hasChatStarted;
    private boolean hasGreeted;
    private String numbersCallBackQueryData;
    private boolean isNewsCallBackQueryData;

    public Users(String username, Long chatId) {
        this.username = username;
        this.chatId = chatId;
        this.isNasaOptionActive = false;
        this.isJokeOptionsActive = false;
        this.isCountryOptionActive = false;
        this.isNumbersOptionActive = false;
        this.isNewsOptionActive = false;
        this.shouldCheckForResponseAfterJoke = false;
        this.isHasLeft=false;
        this.hasChatStarted = false;
        this.hasGreeted = false;
        this.numbersCallBackQueryData = "";
        this.isNewsCallBackQueryData = false;
    }
    public String getUsername(){return username;}
    public boolean isShouldCheckForResponseAfterJoke() {return shouldCheckForResponseAfterJoke;}
    public void setShouldCheckForResponseAfterJoke(boolean shouldCheckForResponseAfterJoke) {this.shouldCheckForResponseAfterJoke = shouldCheckForResponseAfterJoke;}
    public boolean isHasChatStarted() {return hasChatStarted;}
    public void setHasChatStarted(boolean hasChatStarted) {this.hasChatStarted = hasChatStarted;}
    public boolean hasGreeted() {return hasGreeted;}
    public void setHasGreeted(boolean hasGreeted) {this.hasGreeted = hasGreeted;}
    public boolean isNasaOptionActive(){return this.isNasaOptionActive;}
    public void setIsNasaOptionActive(boolean isNasaOptionActive){this.isNasaOptionActive=isNasaOptionActive;}
    public boolean isJokeOptionsActive(){return this.isJokeOptionsActive;}
    public void setIsJokesOptionActive(boolean isJokeOptionsActive){this.isJokeOptionsActive=isJokeOptionsActive;}
    public boolean isNumbersOptionActive() {return isNumbersOptionActive;}
    public void setIsNumbersOptionActive(boolean isNumbersOptionActive) {this.isNumbersOptionActive = isNumbersOptionActive;}
    public boolean isCountryOptionActive(){return isCountryOptionActive;}
    public void setIsCountryOptionsActive(boolean isCountryOptionActive){this.isCountryOptionActive=isCountryOptionActive;}
    public boolean isNewsOptionActive(){return isNewsOptionActive;}
    public void setIsNewsOptionActive(boolean isNewsOptionActive){this.isNewsOptionActive=isNewsOptionActive;}
    public String getNumbersCallBackQueryData() {return numbersCallBackQueryData;}
    public void setNumbersCallBackQueryData(String numbersCallBackQueryData) {this.numbersCallBackQueryData = numbersCallBackQueryData;}
    public boolean isIsNewsCallBackQueryData() {return isNewsCallBackQueryData;}
    public boolean isHasLeft() {return isHasLeft;}
    public void setHasLeft(boolean hasLeft) {this.isHasLeft = hasLeft;}
    //public void setIsNewsCallBackQueryData(boolean isNewsCallBackQueryData) {this.isNewsCallBackQueryData = isNewsCallBackQueryData;}
}