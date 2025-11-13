import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends JFrame {

    ArrayList<JButton> buttons = new ArrayList<>();
    JPanel gamePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel selectionPanel = new JPanel();
    JButton newGameButton = new JButton("New Game");
    JButton quickSolve = new JButton("Quick Solve");
    JButton emptyButton = new JButton("");


    public void myGame() {
        gamePanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(3, 3));
        selectionPanel.add(newGameButton);
        selectionPanel.add(quickSolve);

        gamePanel.add(selectionPanel, BorderLayout.NORTH);
        gamePanel.add(buttonPanel, BorderLayout.CENTER);


        for (int i = 1; i < 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener((ActionEvent e) -> MoveButton(button));
            buttons.add(button);
            buttonPanel.add(button);
        }
        buttons.add(emptyButton);
        buttonPanel.add(emptyButton);

        add(gamePanel);

        setTitle("3x3 puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        newGameButton.addActionListener((ActionEvent e) -> NewGame());
        quickSolve.addActionListener((ActionEvent e) -> QuickSolve());


    }

    private void NewGame() {
        Collections.shuffle(buttons);
        UpdateGamePanel();
    }

    private boolean checkMoveable(int button1, int button2) {
        final int size = 3;
        int row1 = button1 / size;
        int colum1 = button1 % size;
        int row2 = button2 / size;
        int colum2 = button2 % size;
        return (Math.abs(row1 - row2) == 1 && colum1 == colum2) ||
                (Math.abs(colum1 - colum2) == 1 && row1 == row2);
    }

    private void MoveButton(JButton button) {
        int clickedButton = buttons.indexOf(button);
        int textEmpty = buttons.indexOf(emptyButton);
        if (checkMoveable(clickedButton, textEmpty)) {
            Collections.swap(buttons, clickedButton, textEmpty);
            UpdateGamePanel();
            if (didIWin()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You win!");
            }

        }
    }

    private void UpdateGamePanel() {
        buttonPanel.removeAll();
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private boolean didIWin() {
        for (int i = 1; i < 9; i++) {
            if (!buttons.get(i - 1).getText().equals(String.valueOf(i))) {
                return false;
            }
        }
        return true;
    }

    private void QuickSolve() {
        buttons.clear();
        for (int i = 1; i < 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener((ActionEvent e) -> MoveButton(button));
            buttons.add(button);
        }
        buttons.add(emptyButton);
        buttonPanel.add(emptyButton);
        Collections.swap(buttons, 7, 8);
        UpdateGamePanel();
    }
}