import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnhancedCaesarCipher {

    public static void main(String[] args) {
        if (args.length > 0) {
            // Command-line mode
            processCommandLineArgs(args);
        } else {
            // GUI mode
            SwingUtilities.invokeLater(EnhancedCaesarCipher::createAndShowGUI);
        }
    }

    private static void processCommandLineArgs(String[] args) {
        try {
            String mode = args[0];
            int key = Integer.parseInt(args[1]);
            String inputFile = args[2];
            String outputFile = args[3];

            processFile(inputFile, outputFile, key, mode.equalsIgnoreCase("encrypt"));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.out.println("Usage: java EnhancedCaesarCipher <encrypt|decrypt> <key> <inputFile> <outputFile>");
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Caesar Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CipherPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class CipherPanel extends JPanel {
        JTextField keyField, inputFileField, outputFileField;
        JButton encryptButton, decryptButton;

        CipherPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            keyField = new JTextField(10);
            inputFileField = new JTextField(20);
            outputFileField = new JTextField(20);

            encryptButton = new JButton("Encrypt");
            decryptButton = new JButton("Decrypt");

            encryptButton.addActionListener(e -> processFileGui(inputFileField.getText(), outputFileField.getText(), Integer.parseInt(keyField.getText()), true));
            decryptButton.addActionListener(e -> processFileGui(inputFileField.getText(), outputFileField.getText(), Integer.parseInt(keyField.getText()), false));

            add(new JLabel("Key:"));
            add(keyField);
            add(new JLabel("Input File:"));
            add(inputFileField);
            add(new JLabel("Output File:"));
            add(outputFileField);
            add(encryptButton);
            add(decryptButton);
        }

        private void processFileGui(String inputFile, String outputFile, int key, boolean encrypt) {
            try {
                processFile(inputFile, outputFile, key, encrypt);
                JOptionPane.showMessageDialog(this, "Process completed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void processFile(String inputFile, String outputFile, int key, boolean encrypt) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {

            int character;
            while ((character = reader.read()) != -1) {
                char processedChar = (encrypt) ? encryptCharacter((char) character, key) : decryptCharacter((char) character, key);
                writer.write(processedChar);
            }
        }
    }

    private static char encryptCharacter(char ch, int key) {
        if (!Character.isLetter(ch)) {
            return ch;
        }
        char base = Character.isLowerCase(ch) ? 'a' : 'A';
        return (char) (((ch - base + key) % 26) + base);
    }

    private static char decryptCharacter(char ch, int key) {
        return encryptCharacter(ch, 26 - (key % 26));
    }
}
