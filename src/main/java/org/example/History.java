package org.example;
import java.util.ArrayList;
import java.util.List;
public record History(String username, String activity, String date) {
    private static List<History> historyList = new ArrayList<>();
    public static List<History> getHistoryList() {return historyList;}
    public static void addHistory(History history) {historyList.add(history);if (historyList.size() > 10) {historyList.subList(0, historyList.size() - 10).clear();}}
}