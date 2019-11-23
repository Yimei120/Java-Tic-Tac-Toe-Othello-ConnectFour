package a7;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConnectFourGame {

	public static void main(String[] args) {

		JFrame main_frame = new JFrame();
		main_frame.setTitle("ConnectFour");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);

		ConnectFourWidget ttt = new ConnectFourWidget();
		top_panel.add(ttt, BorderLayout.CENTER);

		main_frame.pack();
		main_frame.setVisible(true);

	}
}
