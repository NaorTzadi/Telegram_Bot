package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
public class CountriesAPI {
    public String getCountryInfo(String countryByUser) {
        String urlOfCountryByUser = "https://restcountries.com/v3.1/name/"+countryByUser;
        //return extractCountryInfo(Utility.getTextFromAPI(urlOfCountryByUser));    // קבל שדות רלוונטיים
        return extractFieldContent(Utility.getTextFromAPI(urlOfCountryByUser)); // קבל את כל השדות
    }
    public static String extractFieldContent(String jsonString) { // לא למחוק!
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jsonString, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonArray().get(0).getAsJsonObject();
        StringBuilder result = new StringBuilder();
        traverseJsonObject(jsonObject, result);
        return result.toString();
    }
    private static void traverseJsonObject(JsonObject jsonObject, StringBuilder result) {
        jsonObject.entrySet().forEach(entry -> {
            String fieldName = entry.getKey();
            JsonElement fieldValue = entry.getValue();
            if (fieldValue.isJsonPrimitive()) {
                String fieldValueString = fieldValue.getAsString();
                result.append(fieldName).append(": ").append(fieldValueString).append("\n");
            } else if (fieldValue.isJsonObject()) {
                result.append(fieldName).append(": ").append("\n");
                traverseJsonObject(fieldValue.getAsJsonObject(), result);
            } else if (fieldValue.isJsonArray()) {
                result.append(fieldName).append(": ").append("\n");
                traverseJsonArray(fieldValue.getAsJsonArray(), result);
            }
        });
    }
    private static void traverseJsonArray(JsonArray jsonArray, StringBuilder result) {
        jsonArray.forEach(jsonElement -> {
            if (jsonElement.isJsonPrimitive()) {
                String elementValueString = jsonElement.getAsString();
                result.append("- ").append(elementValueString).append("\n");
            } else if (jsonElement.isJsonObject()) {
                result.append("- ").append("\n");
                traverseJsonObject(jsonElement.getAsJsonObject(), result);
            }
        });
    }

    private String extractCountryInfo(String jsonString) {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jsonString, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonArray().get(0).getAsJsonObject();

        String officialName = jsonObject.get("name").getAsJsonObject().get("official").getAsString();
        String capital = jsonObject.get("capital").getAsJsonArray().get(0).getAsString();
        boolean independent = jsonObject.get("independent").getAsBoolean();
        String region = jsonObject.get("region").getAsString();
        String subregion = jsonObject.get("subregion").getAsString();
        int population = jsonObject.get("population").getAsInt();
        boolean unMember = jsonObject.get("unMember").getAsBoolean();
        JsonObject currenciesObject = jsonObject.get("currencies").getAsJsonObject();
        String currencyCode = currenciesObject.entrySet().iterator().next().getKey();
        JsonObject currencyObject = currenciesObject.getAsJsonObject(currencyCode);
        String currencyName = currencyObject.get("name").getAsString();
        String currencySymbol = currencyObject.get("symbol").getAsString();
        String suffixes = jsonObject.get("idd").getAsJsonObject().get("suffixes").getAsJsonArray().get(0).getAsString();
        JsonObject languagesObject = jsonObject.get("languages").getAsJsonObject();
        String languages = "";
        for (Map.Entry<String, JsonElement> entry : languagesObject.entrySet()) {
            String languageCode = entry.getKey();
            String languageName = entry.getValue().getAsString();
            languages += languageCode + ": " + languageName + ", ";
        }
        languages = languages.substring(0, languages.length() - 2); // Remove the trailing comma and space
        String latlng = jsonObject.get("latlng").getAsJsonArray().toString();
        boolean landlocked = jsonObject.get("landlocked").getAsBoolean();
        JsonArray bordersArray = jsonObject.get("borders").getAsJsonArray();
        String borders = bordersArray.toString();
        double area = jsonObject.get("area").getAsDouble();
        String flag = jsonObject.get("flag").getAsString();
        JsonObject mapsObject = jsonObject.get("maps").getAsJsonObject();
        String googleMaps = mapsObject.get("googleMaps").getAsString();
        String openStreetMaps = mapsObject.get("openStreetMaps").getAsString();
        JsonObject carObject = jsonObject.get("car").getAsJsonObject();
        JsonArray carSignsArray = carObject.get("signs").getAsJsonArray();
        String carSigns = carSignsArray.get(0).getAsString();
        String carSide = carObject.get("side").getAsString();
        String fifaCode = jsonObject.get("fifa").getAsString();
        JsonArray timezonesArray = jsonObject.get("timezones").getAsJsonArray();
        String timezones = "";
        for (JsonElement timezoneElement : timezonesArray) {
            String timezone = timezoneElement.getAsString();
            timezones += timezone + ", ";
        }
        JsonArray continentsArray = jsonObject.get("continents").getAsJsonArray();
        String continents = "";

        for (JsonElement continentElement : continentsArray) {
            String continent = continentElement.getAsString();
            continents += continent + ", ";
        }
        continents = continents.substring(0, continents.length() - 2);
        JsonObject flagsObject = jsonObject.get("flags").getAsJsonObject();
        String pngUrl = flagsObject.get("png").getAsString();
        String svgUrl = flagsObject.get("svg").getAsString();

        //String altDescription = jsonObject.get("alt").getAsString();/////////////////// זה התרסק עם צרפת

        JsonObject coatOfArmsObject = jsonObject.get("coatOfArms").getAsJsonObject();
        String coatOfArmsPng = coatOfArmsObject.get("png").getAsString();
        String coatOfArmsSvg = coatOfArmsObject.get("svg").getAsString();
        String startOfWeek = jsonObject.get("startOfWeek").getAsString();
        JsonObject capitalInfoObject = jsonObject.get("capitalInfo").getAsJsonObject();
        JsonArray latlngArray = capitalInfoObject.get("latlng").getAsJsonArray();
        double latitude = latlngArray.get(0).getAsDouble();
        double longitude = latlngArray.get(1).getAsDouble();
        JsonObject postalCodeObject = jsonObject.get("postalCode").getAsJsonObject();
        String format = postalCodeObject.get("format").getAsString();
        String regex = postalCodeObject.get("regex").getAsString();

        StringBuilder result = new StringBuilder();
        result.append("Country official name: ").append(officialName).append(("\n"));
        if(capital.toLowerCase().equals("jerusalem")){
            result.append("the capital of israel is forever be jerusalem").append(("\n"));
        }else {result.append("Capital: ").append(capital).append(("\n"));}
        result.append("Independent: ").append(independent).append("\n");
        result.append("Region: ").append(region).append("\n");
        result.append("Subregion: ").append(subregion).append("\n");
        result.append("Population: ").append(population).append("\n");
        result.append("Officially-assigned UN member: ").append(unMember).append("\n");
        result.append("Currencies: ").append(currencyCode).append(" - ").append(currencyName).append(" - symbol: ").append(currencySymbol).append("\n");
        result.append("Suffixes: ").append(suffixes).append("\n");
        if(officialName.toLowerCase().contains("israel")){result.append("Language: ").append("Hebrew").append("\n");}else {result.append("Languages: ").append(languages).append("\n");}
        result.append("Latlng: ").append(latlng.substring(1,latlng.length()-1)).append("\n");
        result.append("Landlocked: ").append(landlocked).append("\n");

        if(officialName.toLowerCase().contains("israel")){result.append("Boarders: ").append("\"EGY\",\"JOR\",\"LEN\",\"SYR\"").append("\n");}else {result.append("Borders: ").append(borders.substring(1,borders.length()-1)).append("\n");}

        result.append("Area: ").append(area).append("\n");
        result.append("Flag: ").append(flag).append("\n");
        result.append("Maps: ").append(googleMaps).append("\n");
        result.append("OpenStreetMaps: ").append(openStreetMaps).append("\n");
        result.append("Car Signs: ").append(carSigns).append("\n");
        result.append("Side: ").append(carSide).append("\n");
        result.append("FIFA Code: ").append(fifaCode).append("\n");
        timezones = timezones.substring(0, timezones.length() - 2);
        result.append("Timezones: ").append(timezones).append("\n");
        result.append("Continents: ").append(continents).append("\n");
        result.append("Flags (PNG): ").append(pngUrl).append("\n");
        result.append("Flags (SVG): ").append(svgUrl).append("\n");

        //result.append("Alt Description: ").append(altDescription).append("\n");////////////////////////////// זה התרסק עם צרפת

        result.append("Coat of Arms (PNG): ").append(coatOfArmsPng).append("\n");
        result.append("Coat of Arms (SVG): ").append(coatOfArmsSvg).append("\n");
        result.append("Start of Week: ").append(startOfWeek).append("\n");
        result.append("Capital Info: latlng: ").append(latitude).append(",").append(longitude).append("\n");
        result.append("Postal Code: format: ").append(format).append(" , regex: ").append(regex).append("\n");

        return result.toString();
    } // לא לפתוח את קופסת פנדורה!!

}