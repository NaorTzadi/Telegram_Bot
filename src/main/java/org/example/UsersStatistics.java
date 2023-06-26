package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UsersStatistics {
    private static int sumOfRequests=0;
    private static final HashMap<String,Integer> requests=new HashMap<>();
    private static final HashMap<String,Integer> activeUsers =new HashMap<>();
    private static HashMap<String,Integer> timeBasedOnRequests=new HashMap<>();

    public static void addRequest(String nameOfRequest){
        Integer sum = requests.get(nameOfRequest);if(sum==null){sum=1;}else {sum++;}
        requests.put(nameOfRequest,sum);
        sumOfRequests++;

        Integer amount=timeBasedOnRequests.get(getFormattedTime());if(amount==null){amount=1;}else {amount++;}
        timeBasedOnRequests.put(getFormattedTime(),amount);
    }
    public static void addActiveUser(String username){
        Integer sum = activeUsers.get(username);if(sum==null){sum=1;}else {sum++;}
        activeUsers.put(username,sum);
    }
    public static HashMap<String,Integer> getRequests(){return requests;}
    public static int getSumOfRequests(){return sumOfRequests;}

    public static HashMap<String,Integer> getActiveUsers(){return activeUsers;}

    public static String getMostCommon(HashMap<String, Integer> map) {
        Optional<Map.Entry<String, Integer>> mostCommonEntry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
        return mostCommonEntry.map(Map.Entry::getKey).orElse("");
    }

    public static String getLeastCommon(HashMap<String, Integer> map) {
        Optional<Map.Entry<String, Integer>> leastCommonEntry = map.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue());
        return leastCommonEntry.map(Map.Entry::getKey).orElse("");
    }
    private static String getFormattedTime() {return LocalDateTime.now().format( DateTimeFormatter.ofPattern("HH:mm"));}

    public static String getTheChartGraphUrl(){
        String timeLabels = "";
        String[] test = new String[timeBasedOnRequests.size()];
        int num = 0;
        for (String time : timeBasedOnRequests.keySet()) {
            test[num] = "\"" + time + "\"";
            num++;
        }
        for(int i=0;i<test.length;i++){
            timeLabels+=test[i];
            if (i != test.length-1) {
                timeLabels += ",";
            }
        }

        String amountLabels="";
        for(Integer amount:timeBasedOnRequests.values()){
            amountLabels+=amount+",";
        }
        if(!amountLabels.isEmpty()){amountLabels=amountLabels.substring(0,amountLabels.length()-1);}


        //System.out.println("https://quickchart.io/chart?c={type:'bar',data:{labels:["+timeLabels+"],datasets:[{label:'Users',data:["+amountLabels+"]}]}}");
        return "https://quickchart.io/chart?c={type:'bar',data:{labels:["+timeLabels+"],datasets:[{label:'Users',data:["+amountLabels+"]}]}}";
    }

}