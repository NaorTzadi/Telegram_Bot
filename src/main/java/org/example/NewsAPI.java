package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
public class NewsAPI {
    private static final String API_KEY="8bf38fed18ee429fa21c664589c6d59f";
    public String getNewsBasedOnUserInput(String key,String startDate,String endDate){
        /** אי אפשר לתת תאריך יותר מכ- 33 ימים אחורה (תאריך בזמן קבלת ההערה: 2023-06-20)
         {"status":"error","code":"parameterInvalid","message":"You are trying to request results too far in the past. Your plan permits you to request articles as far back as 2023-05-18, but you have requested 2023-05-01. You may need to upgrade to a paid plan."}**/

        if(endDate.isEmpty()){endDate=startDate;}
        String urlBasedOnUserInput="https://newsapi.org/v2/everything?q="+key+"&from="+startDate+"&to="+endDate+"&sortBy=popularity&apiKey="+API_KEY;

//        System.out.println(key);
//        System.out.println(startDate);
//        System.out.println(endDate);
//        System.out.println(urlBasedOnUserInput);
//        System.out.println(Utility.getTextFromAPI(urlBasedOnUserInput));

        return extractNewsInfo(Utility.getTextFromAPI(urlBasedOnUserInput));
    }

    private String extractNewsInfo(String jsonString) {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jsonString, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonObject();

        JsonArray articles = jsonObject.getAsJsonArray("articles");
        JsonObject firstArticle = articles.get(0).getAsJsonObject();
        String author = firstArticle.get("author").getAsString();
        String title=firstArticle.get("title").getAsString();
        String description=firstArticle.get("description").getAsString();
        String url=firstArticle.get("url").getAsString();
        String urlImage=firstArticle.get("urlToImage").getAsString();
        String publishedDate=firstArticle.get("publishedAt").getAsString();
        String content=firstArticle.get("content").getAsString();

        return "Author: " + author                 + "\n" +
                "title: " + title                  + "\n" +
                "description: " + description      + "\n" +
                "url: " + url                      + "\n" +
                "image: " + urlImage               + "\n" +
                "published date: " + publishedDate + "\n" +
                "content: " + content              + "\n";
    }



}