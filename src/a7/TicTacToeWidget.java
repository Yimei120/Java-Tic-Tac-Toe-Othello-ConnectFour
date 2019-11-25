package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener{
	
	private enum Player{BLACK, WHITE};
	
	private JSpotBoard _board;		/* SpotBoard playing area. */
	private JLabel _message;		/* Label for messages. */
	private boolean _game_won;		/* Indicates if games was been won already.*/
	private Spot _secret_spot;		/* Secret spot which wins the game. */
	private Color _secret_spot_bg;  /* Needed to reset the background of the secret spot. */
	private Player _next_to_play;	/* Identifies who has next turn. */
	
	public TicTacToeWidget(){
		
		_board = new JSpotBoard(3,3);
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

		/* Reset game. */
		resetGame();
		
	}
	
	private void resetGame() {
		/* Clear all spots on board. Uses the fact that SpotBoard
		 * implements Iterable<Spot> to do this in a for-each loop.
		 */

		for (Spot s : _board) {
			s.clearSpot();
		}

	
		_game_won = false;
		_next_to_play = Player.WHITE;		
		
		
		_message.setText("Welcome to Tic Tac Toe. White to play");
	}


	public void spotClicked(Spot spot) {
		/* If game already won, do nothing. */
		if (_game_won) {
			return;
		}


		/* Set up player and next player name strings,
		 * and player color as local variables to
		 * be used later.
		 */
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		
		if (_next_to_play == Player.WHITE) {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "BLACK";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			_next_to_play = Player.WHITE;			
		}
				
		
		/* Set color of spot clicked and toggle. */
		spot.setSpotColor(player_color);
		spot.toggleSpot();

		
		
		//check for vertical and horizontal directions
		
		for (int i=0; i<3;i++) {
			if (!_board.getSpotAt(i, i).isEmpty()) {
			if (_board.getSpotAt(0, i).getSpotColor() == _board.getSpotAt(1, i).getSpotColor()&& 
					_board.getSpotAt(1, i).getSpotColor() == _board.getSpotAt(2, i)) {
				_game_won = true;
			}else if(_board.getSpotAt(i, 0).getSpotColor() == _board.getSpotAt(i, 1)&&
					_board.getSpotAt(i, 1).getSpotColor() == _board.getSpotAt(i, 2)){
				_game_won = true;
			}
		}
		}
		
		//check for diagonals
		if (!_board.getSpotAt(0, 0).isEmpty()&&!_board.getSpotAt(1, 1).isEmpty()&&!_board.getSpotAt(2, 2).isEmpty()&&
				!_board.getSpotAt(0, 2).isEmpty()&&!_board.getSpotAt(2, 0).isEmpty()) {
		if (_board.getSpotAt(0, 0).getSpotColor() == _board.getSpotAt(1, 1).getSpotColor()&&
				_board.getSpotAt(1, 1).getSpotColor() == _board.getSpotAt(2, 2).getSpotColor()) {
			_game_won = true;
		}else if (_board.getSpotAt(0, 2).getSpotColor() == _board.getSpotAt(1, 1).getSpotColor()&&
				_board.getSpotAt(1, 1).getSpotColor() == _board.getSpotAt(2, 0).getSpotColor()) {
			_game_won = true;
		}
	}

		
		if (spot.isEmpty()) {
			_message.setText( next_player_name + " to play.");
		} else {
			if (_game_won)  {
				
				_message.setText(player_name + " wins!");
				
				spot.unhighlightSpot();
			}else {
				_message.setText( next_player_name + " to play.");
			}
			
		}
		int count =0;
		for (Spot s: _board) {
			if (!s.isEmpty()) {
				count++;
			}
			if (count == _board.getSpotHeight()*_board.getSpotWidth() && !_game_won) {
				_message.setText("Draw game.");
				
			}
		}
		
	}

	@Override
	public void spotEntered(Spot spot) {
		if (_game_won) {
			return;
		}
		if (spot.isEmpty()) {
			spot.highlightSpot();
		}
		
		
	}

	@Override
	public void spotExited(Spot spot) {
		spot.unhighlightSpot();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();
		
	}
	

}
