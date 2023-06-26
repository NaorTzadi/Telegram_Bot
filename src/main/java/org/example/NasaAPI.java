package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
public class NasaAPI {
    private final String API_KEY="xBdWkema5QlyGnhdLoPXbjbcNFScxwbQGKDISDWn";
    public String activateNasaAPIBasedOnUserDateInput(String date){
        /** מההבחנה שלי, נאס"ה מתעדים כמעט כל יום בחודש אירוע ומוסיפים אותו לארכיון, לכן לפי דעתי אין צורך לבקש תאריך התחלה וסיום (בדומה ל-NewsAPI).
         * לפי דעתי, הצורך בלבקש שני תאריכים הוא משום שברוב המקרים לא מתקבלת תוצאה בחיפוש של תאריך ספציפי אחד ובשל כך צריך להרחיב את החיפוש על ידי טווח תאריכים.
         * סיבה נוספת היא בכדי לקבל כמה תוצאות במקביל, דבר שלא נראה לי סביר משום שכל תיעוד הוא באורך של כ-30 עד 40 שורות לא כולל קישורים**/

        String urlBasedOnUserGivenDate="https://api.nasa.gov/planetary/apod?start_date="+date+"&end_date="+date+"&api_key="+API_KEY;
        return extract(Utility.getTextFromAPI(urlBasedOnUserGivenDate));
    }
    private String extract(String response) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(response, JsonArray.class);

        if (jsonArray != null && jsonArray.size() > 0) {
            return StreamSupport.stream(jsonArray.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .filter(jsonObject -> jsonObject.has("date") && jsonObject.has("explanation") && jsonObject.has("hdurl") && jsonObject.has("url"))
                    .map(jsonObject -> jsonObject.get("date").getAsString() + " " +
                            jsonObject.get("explanation").getAsString() + " " +
                            jsonObject.get("hdurl").getAsString() + " " +
                            jsonObject.get("url").getAsString())
                    .collect(Collectors.joining("\n"));
        } else {
            return "No data found.";
        }
    }
}