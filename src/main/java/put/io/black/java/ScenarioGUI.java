package put.io.black.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private JButton getScenariuWithNumber;
    private JButton getScenarioToXLevel;
    private JLabel levelLabel;
    private JTextField inputLevel;
    private JScrollPane inputFieldScroll;
    private JScrollPane outputFieldScroll;

    private String localhost = "http://localhost:8080/";

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    /**
     * Class to handle listener from GUI.
     */
    public ScenarioGUI() {
        howManyStepsScenario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO API poprawna funkcje wlozyc (teraz jest stnadard co bylo w projektcie z moodle)
                if (inputField.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Pole wejściowe scenariusza jest puste!");
                    logger.error("Pole wejściowe scenariusza jest puste!");
                } else {
                    try {
                        String prepareUrl = localhost + inputField.getText();
                        prepareUrl = prepareUrl.replace(" ","%20"); // to handle with space ONLY in input
                        URL url = new URL(prepareUrl);
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
                        String outputF = "";
                        logger.debug("Output from Server ....");
                        while ((output = br.readLine()) != null) {
                            logger.debug(output);
                            outputF += output;
                        }
                        outputField.setText(outputF);
                        conn.disconnect();
                        logger.debug("Rozłączono z serwerem!");
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        howManyStepsKeyWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO API liczba krokow zawierajace slowa kluczowe
                outputField.setText("Dużo kroków zawiera słowa kluczowe!");
            }
        });
        whichStepsNotStartFromActor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO API liczba krokow zaczynajacych sie od aktora
                outputField.setText("Dużo kroków w zaczyna się od aktora!");
            }
        });
        getScenariuWithNumber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO API Scenariusz z numeracja
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
                        //TODO API Scenariusz do pewnego poziomu
                        outputField.setText("To jest scenariusz do " + numberLevel.toString() + " poziomu!");
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Liczba poziomów nie jest liczbą!");
                }
            }
        });
    }

    /**
     * Main class to run GUI.
     * @param args for now args are for nothing.
     */
    public static void main(String args[]) {
        JFrame frame = new JFrame("ScenarioGUI");
        frame.setContentPane(new ScenarioGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}