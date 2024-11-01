import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;

public class chatGPT {
    public static String CHATGPT_API_KEY = System.getenv("CHATGPT_API_KEY");
    /**
     * Sends a prompt to the OpenAI ChatGPT API and returns the response.
     * @param prompt The user-provided prompt to send to ChatGPT.
     * @return The generated response content from the API.
     */
    public static String[] processPrompt(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = CHATGPT_API_KEY;
        String model = "gpt-4o-mini";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            JSONObject data = new JSONObject();
            data.put("model", model);
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.put(userMessage);
            data.put("messages", messages);
            String body = data.toString();

            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            // calls the method to extract the message.
            return extractLocationAndDateFromJSONResponse(extractMessageFromJSONResponse(response.toString()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the message content from the JSON response returned by the ChatGPT API.
     * @param response The full JSON response as a String.
     * @return The content of the message within the "choices" array in the response.
     */
    public static String extractMessageFromJSONResponse(String response) {
        return (new JSONObject(response).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content"));
    }

    /**
     * Extracts location and date information from a JSON response string.
     * @param extractMessageFromJSONResponse The JSON response as a String.
     * @return A String array containing location and date, respectively.
     */
    public static String[] extractLocationAndDateFromJSONResponse(String extractMessageFromJSONResponse) {
        String[] extractLocationAndDateFromJSONResponse = new String[2];
        extractLocationAndDateFromJSONResponse[0] =  new JSONObject(extractMessageFromJSONResponse).getString("location");
        extractLocationAndDateFromJSONResponse[1] =  new JSONObject(extractMessageFromJSONResponse).getString("date");
        return extractLocationAndDateFromJSONResponse;
    }
}