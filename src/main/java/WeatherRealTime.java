import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class WeatherRealTime {

    public static String WEATHER_API_KEY = System.getenv("WEATHER_API_KEY");
    /**
     * Main method to launch the WeatherRealTime application with a GUI for entering a location
     * and retrieving the current temperature for that location.
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Create a new frame (the main window)
        JFrame frame = new JFrame("what the weather?");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter your location and click the button:", SwingConstants.CENTER);
        JLabel image = new JLabel("");


        JTextField textField = new JTextField(20);
        JLabel resLabel = new JLabel("");
        JButton button = new JButton("done");

        button.addActionListener(new ActionListener() {
            /**
             * Action performed when the button is clicked. Fetches the temperature for the entered location
             * and updates the label and icon based on the temperature.
             * @param e The action event triggered by clicking the button.
             */
            public void actionPerformed(ActionEvent e) {
                String location = textField.getText();
                try {
                    double temperature = getWeather(location);
                    resLabel.setText("Temperature: " + temperature);
                    ImageIcon weatherImage;
                    if (temperature > 20) {
                        weatherImage = new ImageIcon("/Users/noaapteker/Downloads/sum_03.png");
                    } else if (temperature > 10 && temperature < 20) {
                        weatherImage = new ImageIcon("/Users/noaapteker/Downloads/simplecloud.png");
                    } else {
                        weatherImage = new ImageIcon("/Users/noaapteker/Downloads/thunderstorm.png");
                    }
                    Image scaledImage = weatherImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    image.setIcon(new ImageIcon(scaledImage));
                    panel.revalidate();
                    panel.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(button);
        panel.add(resLabel);
        panel.add(image);
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Retrieves the current temperature for a specified location using Tomorrow.io's real-time weather API.
     * @param location The location (city, region, or coordinates) for which to retrieve the temperature.
     * @return The temperature at the specified location in degrees Celsius.
     * @throws IOException If there is an issue with the network request or response.
     */
    public static double getWeather(String location) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.tomorrow.io/v4/weather/realtime?location=" + location + "&apikey=" + WEATHER_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String responseAsString = response.body().string();
        return (new JSONObject(responseAsString).getJSONObject("data").getJSONObject("values").getDouble("temperature"));
    }
}