package put.io.black.java;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

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

    public ScenarioGUI() {
        howManyStepsScenario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //TODO API liczba krokow w scenariuszu
                outputField.setText("Dużo kroków w scenariuszu!");
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
                try{
                    Integer numberLevel = Integer.parseInt(inputLevel.getText());
                    if(numberLevel < 1){
                        JOptionPane.showMessageDialog(null, "Liczba poziomów nie jest liczbą dodatnią!");
                    } else {
                        //TODO API Scenariusz do pewnego poziomu
                        outputField.setText("To jest scenariusz do "+numberLevel.toString()+" poziomu!");
                    }

                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Liczba poziomów nie jest liczbą!");
                }

            }
        });
    }


public static void main(String args[]){
    JFrame frame = new JFrame("ScenarioGUI");
    frame.setContentPane(new ScenarioGUI().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();



    frame.setVisible(true);
}


}