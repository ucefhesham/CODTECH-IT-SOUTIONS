import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class NumberGuessingGame {
    private int numberToGuess;
    private int attemptCount;
    private final int maxAttempts = 10;

    private JFrame frame;
    private JTextField guessField;
    private JButton guessButton, playAgainButton;
    private JLabel messageLabel, attemptsLabel, titleLabel;

    public NumberGuessingGame() {
        initializeGame();
        initializeGUI();
    }

    private void initializeGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        attemptCount = 0;
    }

    private void initializeGUI() {
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Guess the Number", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        messageLabel = new JLabel("Enter your guess (1-100):");
        guessField = new JTextField(5);
        centerPanel.add(messageLabel);
        centerPanel.add(guessField);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        guessButton = new JButton("Guess");
        playAgainButton = new JButton("Play Again");
        attemptsLabel = new JLabel("Attempts: 0/" + maxAttempts);
        southPanel.add(guessButton);
        southPanel.add(playAgainButton);
        southPanel.add(attemptsLabel);
        frame.add(southPanel, BorderLayout.SOUTH);

        guessButton.addActionListener(this::handleGuess);
        playAgainButton.addActionListener(e -> restartGame());

        frame.setVisible(true);
    }

    private void handleGuess(ActionEvent e) {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attemptCount++;
            if (guess == numberToGuess) {
                messageLabel.setText("Correct! The number was " + numberToGuess);
                guessButton.setEnabled(false);
            } else if (guess > numberToGuess) {
                messageLabel.setText("Too high!");
            } else {
                messageLabel.setText("Too low!");
            }
            attemptsLabel.setText("Attempts: " + attemptCount + "/" + maxAttempts);
            if (attemptCount >= maxAttempts) {
                guessButton.setEnabled(false);
                messageLabel.setText("Attempts exhausted! Number was " + numberToGuess);
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Enter a valid number!");
        }
    }

    private void restartGame() {
        initializeGame();
        guessField.setText("");
        guessButton.setEnabled(true);
        messageLabel.setText("Enter your guess (1-100):");
        attemptsLabel.setText("Attempts: 0/" + maxAttempts);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::new);
    }
}
