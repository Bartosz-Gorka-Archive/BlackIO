package put.io.black.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import put.io.black.java.rest.TextTransformerController;

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

    private final String scheme = "http";
    private final String host = "localhost";
    private final String port = "8080";

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

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
                    JOptionPane.showMessageDialog(null, "Pole wejściowe scenariusza jest puste!");
                    logger.error("Pole wejściowe scenariusza jest puste!");
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
                outputField.setText("Dużo kroków zawiera słowa kluczowe!");
            }
        });
        whichStepsNotStartFromActor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO CORRECT API (name of functions)
                outputField.setText("Dużo kroków w zaczyna się od aktora!");
            }
        });
        getScenarioWithNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO CORRECT API (name of functions)
                outputField.setText("1. To jest scenariusz z numeracją!");
            }
        });
        getScenarioToXLevel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Integer numberLevel = Integer.parseInt(inputLevel.getText());
                    if (numberLevel < 1) {
                        JOptionPane.showMessageDialog(null, "Liczba poziomów nie jest liczbą dodatnią!");
                    } else {
                        //TODO CORRECT API (name of functions)
                        outputField.setText("To jest scenariusz do " + numberLevel.toString() + " poziomu!");
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Liczba poziomów nie jest liczbą!");
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
        JFrame frame = new JFrame("ScenarioGUI");
        frame.setContentPane(new ScenarioGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Function to send data
     *
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
            logger.debug(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            logger.debug("Połączono z serwerem!");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            logger.debug("Output from Server ....");
            while ((output = br.readLine()) != null) {
                logger.debug(output);
                outputF += output;
            }
            conn.disconnect();
            logger.debug("Rozłączono z serwerem!");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            logger.error("MalformedURLException - see Stack");
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error("IOException - see Stack");
        }
        return outputF;
    }
}