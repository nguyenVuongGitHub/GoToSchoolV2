package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	public static void main(String[] argc) {
		GameState gs = new GameState();
		JFrame window = new JFrame();
		window.setUndecorated(true); // close title java
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderPanel borderPanel = new BorderPanel(gs); // thêm border
		window.add(borderPanel);

		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Đặt cửa sổ full màn hình
		window.setVisible(true);

		gs.initGame();
		gs.runGame(); //
	}
}
