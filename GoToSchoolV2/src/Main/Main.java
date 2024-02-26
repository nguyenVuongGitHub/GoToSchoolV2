package Main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] arvc) {
		GameState gs = new GameState();
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Go To School 2.0");
		
		window.add(gs);
		window.pack();
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gs.initGame();
		gs.runGame();

	}
}
