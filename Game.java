import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class Game extends JFrame {
    private JPanel grid;

    private JButton[][] gridButtons;
    private JButton[] settingButtons;

    private String winner;
    private String theme = "dark";
    private int clicks;
    private boolean isOver;

    private final Locale gameLocale = new Locale("en");

    public Game() {
        this.setConfigurations();
    }

    public Game(String title) {
        this.setTitle(title);
        setConfigurations();
    }

    private void setConfigurations() {
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // this.setSize(screenSize.width, screenSize.height);
        this.setSize(600, 600);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void init() {
        this.settingButtons = new JButton[2];
        this.gridButtons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridButtons[i][j] = new JButton("empty");
            }
        }

        this.clicks = 0;
        this.isOver = false;
        this.winner = null;

        this.grid = new JPanel(new GridLayout(3, 3));
        this.add(grid, BorderLayout.CENTER);
        this.addButtons();
        this.setVisible(true);
    }

    private void addButtons() {
        JButton themeButton = new JButton("light mode".toUpperCase(gameLocale));
        themeButton.setBackground(Color.DARK_GRAY);
        themeButton.setForeground(Color.WHITE);
        themeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (theme.equals("dark")) {
                    setTheme("light");
                } else {
                    setTheme("dark");
                }
            }
        });
        this.add(themeButton, BorderLayout.SOUTH);

        JButton restartButton = new JButton("restart".toUpperCase(gameLocale));
        restartButton.setBackground(Color.DARK_GRAY);
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        this.add(restartButton, BorderLayout.NORTH);

        this.settingButtons[0] = themeButton;
        this.settingButtons[1] = restartButton;

        for (int i = 0, row = 0, col = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button.setText((clicks % 2 == 0) ? "X" : "O");
                    clicks++;
                    if (checkGame()) {
                        disableAllGridButtons();
                        restartButton
                                .setText(("game is over! " + ((winner != null) ? "winner is " + winner + "!"
                                        : "draw!")).toUpperCase(gameLocale));
                        // JOptionPane.showMessageDialog(null, "Game is over! Winner is: " + winner,
                        // "GAME OVER",
                        // JOptionPane.INFORMATION_MESSAGE);
                        Timer delay = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                restartButton.setText("restart".toUpperCase(gameLocale));
                            }
                        });
                        delay.setRepeats(false);
                        delay.start();
                    }
                    button.setEnabled(false);
                }
            });
            grid.add(button);
            this.gridButtons[row][col] = button;
            col++;
            if (col == 3) {
                row++;
                col = 0;
            }
        }
    }

    private boolean checkGame() {
        for (int i = 0, j = 0; i < 3; i++) {
            if (this.gridButtons[i][j].getText().equals(this.gridButtons[i][j + 1].getText())
                    && this.gridButtons[i][j + 1].getText().equals(this.gridButtons[i][j + 2].getText())
                    && this.gridButtons[i][j].getText() != "" && this.gridButtons[i][j + 1].getText() != ""
                    && this.gridButtons[i][j + 2].getText() != "") {
                this.isOver = true;
                this.winner = this.gridButtons[i][j].getText();
                return this.isOver;
            }
        }

        for (int i = 0, k = 0; i < this.gridButtons.length; i++) {
            if (this.gridButtons[k][i].getText().equals(this.gridButtons[k + 1][i].getText())
                    && this.gridButtons[k + 1][i].getText().equals(this.gridButtons[k + 2][i].getText())
                    && this.gridButtons[k][i].getText() != "" && this.gridButtons[k + 1][i].getText() != ""
                    && this.gridButtons[k + 2][i].getText() != "") {
                this.winner = this.gridButtons[k][i].getText();
                this.isOver = true;
                return this.isOver;
            }
        }

        if (this.gridButtons[0][0].getText().equals(this.gridButtons[1][1].getText())
                && this.gridButtons[1][1].getText().equals(this.gridButtons[2][2].getText())
                && this.gridButtons[0][0].getText() != "" && this.gridButtons[1][1].getText() != ""
                && this.gridButtons[2][2].getText() != "") {
            this.winner = this.gridButtons[0][0].getText();
            this.isOver = true;
            return this.isOver;
        }

        if (this.gridButtons[0][2].getText().equals(this.gridButtons[1][1].getText())
                && this.gridButtons[1][1].getText().equals(this.gridButtons[2][0].getText())
                && this.gridButtons[0][2].getText() != "" && this.gridButtons[1][1].getText() != ""
                && this.gridButtons[1][1].getText() != "") {
            this.winner = this.gridButtons[0][2].getText();
            this.isOver = true;
            return this.isOver;
        }

        if (this.clicks == 9) {
            this.isOver = true;
            return true;
        }

        return false;
    }

    private void disableAllGridButtons() {
        for (JButton[] jgridButtons : this.gridButtons) {
            for (JButton jButton : jgridButtons) {
                jButton.setEnabled(false);
            }
        }
    }

    private void setTheme(String theme) {
        Color gridBgColor = theme.equals("dark") ? Color.BLACK : Color.WHITE;
        Color settingBgColor = theme.equals("dark") ? Color.DARK_GRAY : Color.GRAY;
        Color fgColor = theme.equals("dark") ? Color.WHITE : Color.BLACK;

        this.theme = theme.equals("dark") ? "dark" : "light";
        this.settingButtons[0].setText( // themeButton
                theme.equals("dark") ? "light mode".toUpperCase(gameLocale) : "dark mode".toUpperCase(gameLocale));

        for (JButton[] jgridButtons : this.gridButtons) {
            for (JButton jButton : jgridButtons) {
                jButton.setBackground(gridBgColor);
                jButton.setForeground(fgColor);
            }
        }

        for (JButton jButton : settingButtons) {
            jButton.setBackground(settingBgColor);
            jButton.setForeground(fgColor);
        }
    }

    private void restartGame() {
        this.getContentPane().removeAll();
        this.repaint();
        this.init();
        this.setTheme(this.theme);
    }
}
