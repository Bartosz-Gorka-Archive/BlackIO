package put.io.black.java.gui;

import java.awt.*;
import java.util.logging.Logger;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * GUI Class for work with logic in this package through REST API.
 */
public class ScenarioGUI {
    private JButton howManyStepsScenario;
    private JPanel panelMain;
    private JTextArea inputField;
    private JTextArea outputField;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JButton howManyStepsKeyWord;
    private JButton whichStepsNotStartFromActor;
    private JButton getScenarioWithNumber;
    private JButton getScenarioToXLevel;
    private JLabel levelLabel;
    private JTextField inputLevel;
    private JScrollPane inputFieldScroll;
    private JScrollPane outputFieldScroll;

    /**
     * Connection protocol (HTTP / HTTPS)
     */
    private final String scheme = "http";
    /**
     * Host name
     */
    private final String host = "localhost";
    /**
     * Host port
     */
    private final String port = "8080";
    /**
     * GUI logger
     */
    private static final Logger logger = Logger.getLogger(ScenarioGUI.class.getName());

    /**
     * Function to set handle listener from GUI.
     */
    public ScenarioGUI() {
        howManyStepsScenario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO CORRECT API (name of functions)
                if (inputField.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Scenario input empty. Please insert scenario.");
                    logger.warning("Input field in scenario is empty!");
                } else {
                    outputField.setText(sendRequest(scheme, host, port, inputField.getText()));
                }
            }
        });
        howManyStepsKeyWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO CORRECT API (name of functions)
                outputField.setText("To many steps!");
            }
        });
        whichStepsNotStartFromActor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO CORRECT API (name of functions)
                outputField.setText("Many staps with actors!");
            }
        });
        getScenarioWithNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO CORRECT API (name of functions)
                outputField.setText("1. Your scenario with numbers.");
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
                        //TODO CORRECT API (name of functions)
                        outputField.setText("Scenario with limit " + numberLevel.toString() + " levels!");
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Nesting level not positive number!");
                }
            }
        });
    }

    /**
     * Main function to run GUI.
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
     * @param scheme - scheme like http
     * @param host   - name of serwer
     * @param port   - connection port
     * @param source - query
     * @return - respond from server
     */
    private String sendRequest(String scheme, String host, String port, String source) {
        String outputF = "";
        try {
            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme(scheme)
                    .host(host)
                    .port(port)
                    .path("/" + source)
                    .build()
                    .encode();
            URL url = new URL(uriComponents.toUriString());
            logger.warning(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            logger.warning("Connected!");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            logger.warning("Output from Server ....");
            while ((output = br.readLine()) != null) {
                logger.warning(output);
                outputF += output;
            }
            conn.disconnect();
            logger.warning("Disconnected!");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            logger.warning("MalformedURLException - see Stack");
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.warning("IOException - see Stack");
        }
        return outputF;
    }

    {
    // GUI initializer generated by IntelliJ IDEA GUI Designer
    // >>> IMPORTANT!! <<<
    // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 2, new Insets(10, 10, 10, 10), -1, -1));
        inputLabel = new JLabel();
        inputLabel.setText("Poniżej wpisz treść scenariusza.");
        panelMain.add(inputLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputLabel = new JLabel();
        outputLabel.setText("Poniżej zostanie wypisany wynik.");
        panelMain.add(outputLabel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        getScenarioToXLevel = new JButton();
        getScenarioToXLevel.setText("Pobierz scenariusz do określonego poziomu");
        panelMain.add(getScenarioToXLevel, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        getScenarioWithNumber = new JButton();
        getScenarioWithNumber.setText("Pobierz scenariusz z numeracją");
        panelMain.add(getScenarioWithNumber, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        whichStepsNotStartFromActor = new JButton();
        whichStepsNotStartFromActor.setText("Które kroki nie rozpoczynają się od aktora?");
        panelMain.add(whichStepsNotStartFromActor, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        howManyStepsKeyWord = new JButton();
        howManyStepsKeyWord.setText("Ile króków zawiera słowa kluczowe?");
        panelMain.add(howManyStepsKeyWord, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        inputLevel = new JTextField();
        inputLevel.setToolTipText("Wpisz liczbę poziomów");
        panelMain.add(inputLevel, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
        levelLabel = new JLabel();
        levelLabel.setText("Wpisz liczbę poziomów:");
        panelMain.add(levelLabel, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputFieldScroll = new JScrollPane();
        panelMain.add(inputFieldScroll, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 200), new Dimension(300, 200), null, 0, false));
        inputField = new JTextArea();
        inputField.setLineWrap(false);
        inputFieldScroll.setViewportView(inputField);
        outputFieldScroll = new JScrollPane();
        panelMain.add(outputFieldScroll, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 200), new Dimension(300, 200), null, 0, false));
        outputField = new JTextArea();
        outputField.setEditable(false);
        outputFieldScroll.setViewportView(outputField);
        howManyStepsScenario = new JButton();
        howManyStepsScenario.setText("Ile kroków zawiera scenariusz?");
        panelMain.add(howManyStepsScenario, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, 25), new Dimension(300, 25), new Dimension(300, 25), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
