import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Design {
    /**
     * Initializes and displays the main frame for the Weather application.
     * The frame contains a split pane with left and right panels for user input
     * and results display.
     */
    public static void frame() {
        JFrame frame = new JFrame("what the weather?");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel leftPanel = leftPanel();
        JPanel rightPanel = rightPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(250);
        frame.add(splitPane);
        frame.setVisible(true);
    }

    /**
     * Creates the left panel for user input, where the user can enter a location and date,
     * and view temperature results for specific times of day.
     * @return A JPanel containing the input fields and display labels for temperature.
     */
    public static JPanel leftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Enter your location & Date and click the button:", SwingConstants.CENTER);
        leftPanel.add(centerComponent(titleLabel));

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Location input section
        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField(20);
        locationPanel.add(locationLabel);
        locationPanel.add(locationField);
        leftPanel.add(locationPanel);

        // Date input section
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Date:");
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        datePanel.add(dateLabel);
        leftPanel.add(datePanel);
        dateSpinner.setEditor(dateEditor);
        leftPanel.add(dateSpinner);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton buttonleft = new JButton("Done");
        leftPanel.add(centerComponent(buttonleft));

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel[] responseLabels = { new JLabel(""), new JLabel(""), new JLabel("") };
        leftPanel.add(centerComponent(responseLabels[0]));
        leftPanel.add(centerComponent(responseLabels[1]));
        leftPanel.add(centerComponent(responseLabels[2]));

        // Action listener for "Done" button to fetch and display temperatures
        buttonleft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String location = locationField.getText();
                Date selectedDate = (Date) dateSpinner.getValue();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(selectedDate);
                fetchWeather(location, date,responseLabels);
            }
        });
        return leftPanel;
    }
    /**
     * Creates the right panel for free text input and automated location and date extraction,
     * with the results displayed for specific times of the day.
     * @return A JPanel containing the free text area, extraction button, and temperature labels.
     */
    public static JPanel rightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel freeTextLabel = new JLabel("Free Text:");
        JTextArea freeTextArea = new JTextArea();
        rightPanel.add(centerComponent(freeTextLabel));
        rightPanel.add(new JScrollPane(freeTextArea));

        JButton buttonRight = new JButton("Done");
        rightPanel.add(centerComponent(buttonRight));

        JLabel[] responseLabels = { new JLabel(""), new JLabel(""), new JLabel("") };
        rightPanel.add(centerComponent(responseLabels[0]));
        rightPanel.add(centerComponent(responseLabels[1]));
        rightPanel.add(centerComponent(responseLabels[2]));

        // Action listener for "Done" button to extract location, date, and fetch temperatures
        buttonRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String freeText = freeTextArea.getText();
                String[] answer = chatGPT.processPrompt("Please extract the location and date from the following question as json which including location and date (in format YYYY-MM-DD (Return only the json - Just the location and date itself and nothing else in string format and not json format):\n" + freeText);
                String location = answer[0];
                String date = answer[1];
                fetchWeather(location,date,responseLabels);
            }
        });
        return rightPanel;
    }
    private static void fetchWeather(String location, String date, JLabel[] weatherLabels) {
        try {
            double[] temperatures = WeatherInDate.getDateWeather(date, location);
            weatherLabels[0].setText("At 08:00: " + temperatures[0]);
            weatherLabels[1].setText("At 14:00: " + temperatures[1]);
            weatherLabels[2].setText("At 20:00: " + temperatures[2]);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error fetching weather data.");
        }
    }
    private static JComponent centerComponent(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        return component;
    }
}
