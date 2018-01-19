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
     * Listing scenarios inserted in API
     */
    private JButton loadListOfScenariosButton;
    /**
     * Scenario title text field
     */
    private JTextField scenarioTitleTextField;
    /**
     * Button - load scenario with selected title
     */
    private JButton loadScenarioWithSelectedButton;
    /**
     * Scenario title label
     */
    private JLabel scenarioTitleLabel;
    /**
     * Button - save scenario in API
     */
    private JButton saveScenarioButton;

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
     * @param checkInput Check input field - not empty required when true
     */
    private void callAPI(String endpoint, Map<String, String> parameters, boolean checkInput, boolean checkScenarioTitle) {
        String scenarioText = inputField.getText().trim();
        String title = scenarioTitleTextField.getText().trim();

        if (checkInput && scenarioText.equals("")) {
            JOptionPane.showMessageDialog(null, "Scenario input empty. Please insert scenario.");
            logger.warning("Body field in scenario is empty!");
        } else if (checkScenarioTitle && title.equals("")) {
            JOptionPane.showMessageDialog(null, "Scenario title empty. Please insert title.");
            logger.warning("Scenario title field is empty!");
        } else {
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("scenario", scenarioText);
                jsonObject.addProperty("title", title);

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
                callAPI("steps", null, true, false);
            }
        });
        howManyStepsKeyWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("number_keywords", null, true, false);
            }
        });
        whichStepsNotStartFromActor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("without_actors", null, true, false);
            }
        });
        getScenarioWithNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("numeric", null, true, false);
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
                        callAPI("level", parameters, true, false);
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Nesting level not positive number!");
                }
            }
        });
        loadListOfScenariosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("listing_scenarios", null, false, false);
            }
        });
        loadScenarioWithSelectedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("read_scenario", null, false, true);
            }
        });
        saveScenarioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callAPI("save_scenario", null, true, true);
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
        frame.pack();
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
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            br.close();
            connection.disconnect();

            JsonObject jsonObject = new JsonParser().parse(stringBuffer.toString()).getAsJsonObject();
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
        panelMain.setLayout(new GridLayoutManager(15, 2, new Insets(10, 10, 10, 10), -1, -1));
        inputLabel = new JLabel();
        inputLabel.setText("Your insert");
        panelMain.add(inputLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        getScenarioWithNumber = new JButton();
        getScenarioWithNumber.setText("Scenario with numeric");
        panelMain.add(getScenarioWithNumber, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        whichStepsNotStartFromActor = new JButton();
        whichStepsNotStartFromActor.setText("Check lines not start from actor");
        panelMain.add(whichStepsNotStartFromActor, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        howManyStepsKeyWord = new JButton();
        howManyStepsKeyWord.setText("Count keywords in scenario");
        panelMain.add(howManyStepsKeyWord, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        scenarioTitleTextField = new JTextField();
        scenarioTitleTextField.setText("");
        scenarioTitleTextField.setToolTipText("Insert scenario title");
        panelMain.add(scenarioTitleTextField, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        levelLabel = new JLabel();
        levelLabel.setText("Nesting level");
        panelMain.add(levelLabel, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputFieldScroll = new JScrollPane();
        panelMain.add(inputFieldScroll, new GridConstraints(1, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 200), new Dimension(300, 200), null, 0, false));
        inputField = new JTextArea();
        inputField.setLineWrap(false);
        inputFieldScroll.setViewportView(inputField);
        outputFieldScroll = new JScrollPane();
        panelMain.add(outputFieldScroll, new GridConstraints(7, 0, 8, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 200), new Dimension(300, 200), null, 0, false));
        outputField = new JTextArea();
        outputField.setEditable(false);
        outputFieldScroll.setViewportView(outputField);
        howManyStepsScenario = new JButton();
        howManyStepsScenario.setText("Count steps in scenario");
        panelMain.add(howManyStepsScenario, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        loadListOfScenariosButton = new JButton();
        loadListOfScenariosButton.setText("Load list of scenarios");
        panelMain.add(loadListOfScenariosButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        loadScenarioWithSelectedButton = new JButton();
        loadScenarioWithSelectedButton.setText("Load scenario with selected title");
        panelMain.add(loadScenarioWithSelectedButton, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        saveScenarioButton = new JButton();
        saveScenarioButton.setText("Save scenario");
        panelMain.add(saveScenarioButton, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        scenarioTitleLabel = new JLabel();
        scenarioTitleLabel.setText("Scenario title");
        panelMain.add(scenarioTitleLabel, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputLevel = new JTextField();
        inputLevel.setText("");
        inputLevel.setToolTipText("Insert scenario body");
        panelMain.add(inputLevel, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        getScenarioToXLevel = new JButton();
        getScenarioToXLevel.setText("Scenario to selected nesting level");
        panelMain.add(getScenarioToXLevel, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        outputLabel = new JLabel();
        outputLabel.setText("In field below you should receive response");
        panelMain.add(outputLabel, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scenarioTitleLabel.setLabelFor(scenarioTitleTextField);
    }

    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
