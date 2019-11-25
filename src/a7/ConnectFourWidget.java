package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener {

	private enum Player {
		RED, BLACK
	};

	private JSpotBoardConnectFour _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;

	public ConnectFourWidget() {
		_board = new JSpotBoardConnectFour(7, 6);
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

	private void resetGame() {

		for (Spot s : _board) {
			s.clearSpot();
		}

		_game_won = false;
		_next_to_play = Player.RED;

		_message.setText("Welcome to the ConnectFour. Red to play");
	}

	@Override
	public void spotClicked(Spot spot) {

		Spot bottom = null;

		if (_game_won) {
			return;
		}

		String player_name = null;
		String next_player_name = null;
		Color player_color = null;

		if (_next_to_play == Player.RED) {
			player_color = Color.RED;
			player_name = "Red";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "Red";
			_next_to_play = Player.RED;
		}

		for (int n = _board.getSpotHeight() - 1; n >= 0; n--) {
			if (_board.getSpotAt(spot.getSpotX(), n).isEmpty()) {
				bottom = _board.getSpotAt(spot.getSpotX(), n);
				bottom.setSpotColor(player_color);
				bottom.toggleSpot();
				break;
			} else {
				continue;
			}
		}

		// Here should do something to indicate winning.
		// check for the vertical direction

		List<Spot> spotList1 = new ArrayList<Spot>();
		List<Spot> spotList2 = new ArrayList<Spot>();
		List<Spot> spotList3 = new ArrayList<Spot>();
		List<Spot> spotList4 = new ArrayList<Spot>();

		if (bottom != null) {
			int count = 0;

			for (int i = 0; i < _board.getSpotHeight(); i++) {

				Spot spotupp = _board.getSpotAt(bottom.getSpotX(), _board.getSpotHeight() - 1 - i);
				if (!spotupp.isEmpty() && (spotupp.getSpotColor() == bottom.getSpotColor())) {
					count++;
					spotList1.add(spotupp);

				} else {
					count = 0;
				}
				if (count == 4) {
					System.out.println(count);
					_game_won = true;

				}
			}

			// check for horizontal direction
			int count1 = 0;

			for (int j = 0; j < _board.getSpotWidth(); j++) {

				Spot spotRight = _board.getSpotAt(j, bottom.getSpotY());
				if (!spotRight.isEmpty() && spotRight.getSpotColor() == bottom.getSpotColor()) {
					count1++;
					spotList2.add(spotRight);

				} else {
					count1 = 0;
				}
				if (count1 == 4) {
					_game_won = true;

					for (Spot each : spotList2) {
						each.highlightSpot();
					}

				}
			}

			// check for northeast(southwest) direction

			Spot spotNortheast = null;
			Spot spotSouthwest = null;

			if ((bottom.getSpotX() + bottom.getSpotY()) < (_board.getSpotWidth() - 1)) {
				spotNortheast = _board.getSpotAt(bottom.getSpotX() + bottom.getSpotY(), 0);
				spotSouthwest = _board.getSpotAt(0, bottom.getSpotX() + bottom.getSpotY());

			} else {

				spotNortheast = _board.getSpotAt(_board.getSpotWidth() - 1,
						bottom.getSpotX() + bottom.getSpotY() - (_board.getSpotWidth() - 1));
				spotSouthwest = _board.getSpotAt(
						spotNortheast.getSpotX() + spotNortheast.getSpotY() - (_board.getSpotHeight() - 1),
						_board.getSpotHeight() - 1);

				int count2 = 0;
				for (int x = spotSouthwest.getSpotX(), y = spotSouthwest.getSpotY(); x <= spotNortheast.getSpotX()
						&& y >= spotNortheast.getSpotY(); x++, y--) {
					Spot goDown = _board.getSpotAt(x, y);
					if (!goDown.isEmpty() && goDown.getSpotColor() == bottom.getSpotColor()) {
						count2++;
						spotList3.add(goDown);
					} else {
						count2 = 0;
					}
					if (count2 == 4) {
						_game_won = true;
						for (Spot each : spotList3) {
							each.highlightSpot();
						}

					}
				}
			}

			// check for southeast (northwest) direction

			Spot Northwest = null;
			Spot Southeast = null;

			if (bottom.getSpotX() - bottom.getSpotY() <= 0) {

				Northwest = _board.getSpotAt(0, bottom.getSpotY() - bottom.getSpotX());
				Southeast = _board.getSpotAt(_board.getSpotHeight() - 1 - (bottom.getSpotY() - bottom.getSpotX()),
						_board.getSpotHeight() - 1);
			} else {

				Northwest = _board.getSpotAt(bottom.getSpotX() - bottom.getSpotY(), 0);
				Southeast = _board.getSpotAt(_board.getSpotWidth() - 1,
						_board.getSpotWidth() - 1 - (bottom.getSpotX() - bottom.getSpotY()));
			}
			int count3 = 0;
			for (int x = Northwest.getSpotX(), y = Northwest.getSpotY(); x <= Southeast.getSpotX()
					&& y <= Southeast.getSpotY(); x++, y++) {

				Spot goDown = _board.getSpotAt(x, y);

				if ((!goDown.isEmpty() && goDown.getSpotColor() == bottom.getSpotColor())) {
					count3++;
					spotList4.add(goDown);
				} else {
					count3 = 0;
				}

				if (count3 == 4) {
					_game_won = true;

					for (Spot each : spotList4) {
						each.highlightSpot();
					}

				}
			}

		}

		if (spot.isEmpty()) {
			_message.setText(next_player_name + " to play.");
			
		}else{
			if (_game_won) {

			_message.setText(player_name + " wins!");
			if (spotList1.size() == 4) {
				for (Spot each : spotList1) {
					each.highlightSpot();
				}
			} else if (spotList2.size() == 4) {
				for (Spot each : spotList2) {
					each.highlightSpot();
				}
			} else if (spotList3.size() == 4) {
				for (Spot each : spotList3) {
					each.highlightSpot();
				}
			} else if (spotList4.size() == 4) {
				for (Spot each : spotList4) {
					each.highlightSpot();
				}
			}

		} else {
			_message.setText(player_name + " to play.");
		}}

		int num = 0;
		for (Spot s : _board) {
			if (!s.isEmpty()) {
				num++;
			}
			if (num == _board.getHeight() * _board.getWidth() && !_game_won) {
				_message.setText("Draw game.");

			}
		}
	}

	@Override
	public void spotEntered(Spot spot) {

		if (_game_won) {
			return;
		} else {

			for (int y = 0; y < _board.getSpotHeight(); y++) {
				Spot Hspot = _board.getSpotAt(spot.getSpotX(), y);
				if (Hspot.isEmpty()) {
					Hspot.highlightSpot();
				}

			}
		}
	}

	@Override
	public void spotExited(Spot spot) {
		if (_game_won) {
			for (int y = 0; y < _board.getSpotHeight(); y++) {
				Spot s = _board.getSpotAt(spot.getSpotX(), y);
				if (s.isEmpty()) {
					s.unhighlightSpot();

				}
			}
			return;
		}

		for (int y = 0; y < _board.getSpotHeight(); y++) {
			Spot Uspot = _board.getSpotAt(spot.getSpotX(), y);
			Uspot.unhighlightSpot();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();

	}

}
