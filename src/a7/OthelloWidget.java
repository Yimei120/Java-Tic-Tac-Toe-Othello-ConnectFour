package a7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import a7.ConnectFourWidget.Player;

public class OthelloWidget extends JPanel implements ActionListener, SpotListener{
	
	private enum Player{WHITE, BLACK};
	
	private JSpotBoardConnectFour _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	
	public OthelloWidget() {
		_board = new JSpotBoardConnectFour(8,8);
		_message = new JLabel();
		
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
		
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());
		
		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);
		
		
		add(reset_message_panel, BorderLayout.SOUTH);
		_board.addSpotListener(this);
		
		resetGame();
	}

	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
