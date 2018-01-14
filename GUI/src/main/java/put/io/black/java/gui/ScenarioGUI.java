package put.io.black.java.gui;

import java.awt.*;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * GUI Class for work with logic in this package through REST API.
 */
public class ScenarioGUI {
    /**
     * Button - how many steps prepared in scenario
     */
    private JButton howManyStepsScenario;
    /**
     * Main panel
     */
    private JPanel panelMain;
    /**
     * Input - scenario from user
     */
    private JTextArea inputField;
    /**
     * Output field - response from server
     */
    private JTextArea outputField;
    /**
     * Label with note about input
     */
    private JLabel inputLabel;
    /**
     * Label with note about output
     */
    private JLabel outputLabel;
    /**
     * Button - how many keywords in scenario
     */
    private JButton howManyStepsKeyWord;
    /**
     * Button - check lines start not from actor
     */
    private JButton whichStepsNotStartFromActor;
    /**
     * Button - get scenario with numeric
     */
    private JButton getScenarioWithNumber;
    /**
     * Button - get scenario to selected nesting level
     */
    private JButton getScenarioToXLevel;
    /**
     * Label about nesting level
     */
    private JLabel levelLabel;
    /**
     * Input for nesting level
     */
    private JTextField inputLevel;
    /**
     * Scroll pane in input
     */
    private JScrollPane inputFieldScroll;
    /**
     * Scroll pane in output
     */
    private JScrollPane outputFieldScroll;

    /**
     * Server IP
     */
    private final String serverIP = "http://localhost:8080/";
    /**
     * GUI logger
     */
    private static final Logger logger = Logger.getLogger(ScenarioGUI.class.getName());

    /**
     * Call to API
     *
     * @param endpoint   Endpoint from API to call
     * @param parameters Optional parameters as Map with key, value (Both string)
     */
    private void callAPI(String endpoint, Map<String, String> parameters) {
        String text = inputField.getText().trim();
        if (text.equals("")) {
            JOptionPane.showMessageDialog(null, "Scenario input empty. Please insert scenario.");
            logger.warning("Input field in scenario is empty!");
        } else {
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("scenario", text);

                if (parameters != null) {
                    for (Map.Entry<String, String> entry : parameters.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        jsonObject.addProperty(key, value);
                    }
                }
                outputField.setText(sendRequest(endpoint, jsonObject.toString()));
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Function to set handle listener from GUI.
     */
    public ScenarioGUI() {
        // Change tab size in fields
        inputField.setTabSize(1);
        outputField.setTabSize(1);

        howManyStepsScenario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("steps", null);
            }
        });
        howManyStepsKeyWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("number_keywords", null);
            }
        });
        whichStepsNotStartFromActor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("without_actors", null);
            }
        });
        getScenarioWithNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("numeric", null);
            }
        });
        getScenarioToXLevel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Integer numberLevel = Integer.parseInt(inputLevel.getText());
                    if (numberLevel < 1) {
                        JOptionPane.showMessageDialog(null, "Nesting level should be positive number!");
                    } else {
                        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                        parameters.put("level", Integer.toString(numberLevel));
                        callAPI("level", parameters);
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Nesting level not positive number!");
                }
            }
        });
    }

    /**
     * Main function to run GUI.
     *
     * @param args for now args are for nothing.
     */
    public static void main(String args[]) {
        ScenarioGUI scenarioGUI = new ScenarioGUI();
        JFrame frame = new JFrame("ScenarioGUI");
        frame.setContentPane(scenarioGUI.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Function to send data
     *
     * @param endpoint API Endpoint
     * @param source   Query
     * @return Response from server
     * @throws RuntimeException When can not connect with server
     */
    private String sendRequest(String endpoint, String source) throws RuntimeException {
        try {
            URL url = new URL(serverIP + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(source);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();

            JsonObject jsonObject = new JsonParser().parse(jsonString.toString()).getAsJsonObject();
            JsonElement status = jsonObject.get("status");
            if (status != null) {
                if (status.getAsString().trim().equals("success")) {
                    return jsonObject.get("result").getAsString();
                } else {
                    return jsonObject.get("message").getAsString();
                }
            } else {
                return "No return from server";
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(10, 2, new Insets(10, 10, 10, 10), -1, -1));
        inputLabel = new JLabel();
        inputLabel.setText("Write scenario in field below");
        panelMain.add(inputLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputLabel = new JLabel();
        outputLabel.setText("In field belowe you should receive response");
        panelMain.add(outputLabel, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        getScenarioToXLevel = new JButton();
        getScenarioToXLevel.setText("Scenario to selected nesting level");
        panelMain.add(getScenarioToXLevel, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        getScenarioWithNumber = new JButton();
        getScenarioWithNumber.setText("Scenario with numeric");
        panelMain.add(getScenarioWithNumber, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        whichStepsNotStartFromActor = new JButton();
        whichStepsNotStartFromActor.setText("Check lines not start from actor");
        panelMain.add(whichStepsNotStartFromActor, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        howManyStepsKeyWord = new JButton();
        howManyStepsKeyWord.setText("Count keywords in scenario");
        panelMain.add(howManyStepsKeyWord, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        inputLevel = new JTextField();
        inputLevel.setToolTipText("Wpisz liczbę poziomów");
        panelMain.add(inputLevel, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        levelLabel = new JLabel();
        levelLabel.setText("Nesting level");
        panelMain.add(levelLabel, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputFieldScroll = new JScrollPane();
        panelMain.add(inputFieldScroll, new GridConstraints(1, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 200), new Dimension(300, 200), null, 0, false));
        inputField = new JTextArea();
        inputField.setLineWrap(false);
        inputFieldScroll.setViewportView(inputField);
        outputFieldScroll = new JScrollPane();
        panelMain.add(outputFieldScroll, new GridConstraints(6, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 200), new Dimension(300, 200), null, 0, false));
        outputField = new JTextArea();
        outputField.setEditable(false);
        outputFieldScroll.setViewportView(outputField);
        howManyStepsScenario = new JButton();
        howManyStepsScenario.setText("Count steps in scenario");
        panelMain.add(howManyStepsScenario, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
