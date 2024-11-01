import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
public class WeatherInDate {
    public static String WEATHER_API_KEY = System.getenv("WEATHER_API_KEY");
    /**
     * Retrieves hourly temperature values at specific intervals for a given date and location
     * using Tomorrow.io's weather API.
     * @param date The date for which to retrieve weather data (in format yyyy-MM-dd).
     * @param location The location (city, region, or coordinates) for which to retrieve weather data.
     * @return An array of double values representing temperatures at three specific hours: 08:00, 14:00, and 20:00.
     * @throws IOException If there is an issue with the network request or response.
     */
    public static double[] getDateWeather(String date, String location) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        // Build JSON request body
        JSONObject data = new JSONObject();
        data.put("location", location);
        JSONArray fields = new JSONArray();
        fields.put("temperature");
        data.put("fields", fields);
        data.put("units", "metric");
        JSONArray timesteps = new JSONArray();
        timesteps.put("1h");
        data.put("timesteps" , timesteps);
        data.put("startTime", date + "T08:00:00+03:00");
        data.put("endTime", date + "T20:00:00+03:00");
        data.put("timezone", "Asia/Tel_Aviv");

        RequestBody body = RequestBody.create(mediaType, data.toString());
        Request request = new Request.Builder()
                .url("https://api.tomorrow.io/v4/timelines?apikey=" + WEATHER_API_KEY)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String responseAsString = response.body().string();
        System.out.println(responseAsString);

        // Extract and return temperature data at three specified times (08:00, 14:00, and 20:00)
        double[] temperature = new double[3];
        temperature[0] = new JSONObject(responseAsString).getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals").getJSONObject(0).getJSONObject("values").getDouble("temperature");
        temperature[1] = new JSONObject(responseAsString).getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals").getJSONObject(6).getJSONObject("values").getDouble("temperature");
        temperature[2] = new JSONObject(responseAsString).getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals").getJSONObject(12).getJSONObject("values").getDouble("temperature");
        return (temperature);
    }
}
