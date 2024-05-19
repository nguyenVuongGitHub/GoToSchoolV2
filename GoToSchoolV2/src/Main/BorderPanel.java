package Main;

import javax.swing.*;
import java.awt.*;

public class BorderPanel extends JPanel {
    private GameState gameState;

    public BorderPanel(GameState gameState) {
        this.gameState = gameState;
        setLayout(new GridBagLayout());
        setBackground(new Color(14, 14, 21)); // màu xám nhạt
        addGamePanel();
    }

    private void addGamePanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(gameState, gbc);
    }
}
