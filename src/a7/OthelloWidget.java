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

public class OthelloWidget extends JPanel implements ActionListener, SpotListener {

	private enum Player {
		WHITE, BLACK
	};

	private JSpotBoardOthello _board;
	private JLabel _message;
	private Player _next_to_play;
	private boolean _game_won;

	private List<Boolean> valid_moves;

	public OthelloWidget() {
		_board = new JSpotBoardOthello(8, 8);
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

		Spot d1 = _board.getSpotAt(3, 3);
		d1.setSpotColor(Color.WHITE);
		d1.setSpot();
		Spot d2 = _board.getSpotAt(4, 3);
		d2.setSpotColor(Color.BLACK);
		d2.setSpot();
		Spot d3 = _board.getSpotAt(3, 4);
		d3.setSpotColor(Color.BLACK);
		d3.setSpot();
		Spot d4 = _board.getSpotAt(4, 4);
		d4.setSpotColor(Color.WHITE);
		d4.setSpot();

		_game_won = false;
		_next_to_play = Player.BLACK;

		valid_moves = new ArrayList<Boolean>();

		_message.setText("Welcome to the Othello. Black to play");
	}

	@Override
	public void spotClicked(Spot spot) {

		if (_game_won == true) {
			return;
		}

		String player_name = null;
		String next_player_name = null;
		Color player_color = null;

		if (_next_to_play == Player.BLACK) {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			_next_to_play = Player.WHITE;
		} else {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		}

		// valid moves
		valid_moves = new ArrayList<Boolean>();
		// check horizontal direction
		for (int x = 0; x < _board.getSpotWidth(); x++) {
			Spot s = _board.getSpotAt(x, spot.getSpotY());

			if (s.getSpotColor() == player_color && Math.abs(s.getSpotX() - spot.getSpotX()) > 1) {

				if (s.getSpotX() < spot.getSpotX() && s.getSpotX() + 1 != spot.getSpotX()) {
					int count = 0;
					for (int i = s.getSpotX() + 1; i < spot.getSpotX(); i++) {
						if (!_board.getSpotAt(i, spot.getSpotY()).isEmpty()) {
							count++;
						}
						if (count == spot.getSpotX() - s.getSpotX() - 1) {
							if (_board.getSpotAt(i, spot.getSpotY()).getSpotColor() != player_color) {
								valid_moves.add(true);
							}

						} else {
							valid_moves.add(false);
						}
					}
				}

				if (s.getSpotX() > spot.getSpotX() && s.getSpotX() - 1 != spot.getSpotX()) {
					int count = 0;
					for (int i = spot.getSpotX() + 1; i < s.getSpotX(); i++) {
						if (!_board.getSpotAt(i, spot.getSpotY()).isEmpty()) {
							count++;
						}
						if (count == s.getSpotX() - spot.getSpotX() - 1) {
							if (_board.getSpotAt(i, spot.getSpotY()).getSpotColor() != player_color) {
								valid_moves.add(true);
							}

						} else {
							valid_moves.add(false);
						}

					}
				}

			} else {
				valid_moves.add(false);
			}
		}

		// check vertical direction
		for (int y = 0; y < _board.getSpotHeight(); y++) {
			Spot s = _board.getSpotAt(spot.getSpotX(), y);
			if (s.getSpotColor() == player_color && Math.abs(s.getSpotY() - spot.getSpotY()) > 1) {
				if (s.getSpotY() < spot.getSpotY() && s.getSpotY() + 1 != spot.getSpotY()) {
					int count = 0;
					for (int i = s.getSpotY() + 1; i < spot.getSpotY(); i++) {
						if (!_board.getSpotAt(spot.getSpotX(), i).isEmpty()) {
							count++;
						}
						if (count == spot.getSpotY() - s.getSpotY() - 1) {
							if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() != player_color) {
								valid_moves.add(true);
							}
						} else {
							valid_moves.add(false);
						}

					}

				}

				if (s.getSpotY() > spot.getSpotY() && s.getSpotY() - 1 != spot.getSpotY()) {
					int count = 0;
					for (int i = spot.getSpotY() + 1; i < s.getSpotY(); i++) {
						if (!_board.getSpotAt(spot.getSpotX(), i).isEmpty()) {
							count++;
						}
						if (count == s.getSpotY() - spot.getSpotY() - 1) {
							if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() != player_color) {
								valid_moves.add(true);
							}

						} else {
							valid_moves.add(false);
						}
					}

				}
			} else {
				valid_moves.add(false);
			}
		}
		// check northeast(southwest)

		Spot northeast1 = null;
		Spot southwest1 = null;

		if (spot.getSpotX() + spot.getSpotY() <= _board.getSpotHeight() - 1) {
			northeast1 = _board.getSpotAt(spot.getSpotX() + spot.getSpotY(), 0);
			southwest1 = _board.getSpotAt(0, spot.getSpotX() + spot.getSpotY());
		} else {
			northeast1 = _board.getSpotAt(7, spot.getSpotX() + spot.getSpotY() - 7);
			southwest1 = _board.getSpotAt(spot.getSpotX() + spot.getSpotY() - 7, 7);
		}
		for (int x = spot.getSpotX() + 1, y = spot.getSpotY() - 1; x <= northeast1.getSpotX()
				&& y >= northeast1.getSpotY(); x++, y--) {
			Spot s = _board.getSpotAt(x, y);
			if (s.getSpotColor() == player_color && s.getSpotX() - 1 != spot.getSpotX()
					&& s.getSpotY() + 1 != spot.getSpotY() && !s.isEmpty()) {
				for (int m = spot.getSpotX() + 1, n = spot.getSpotY() - 1; m < s.getSpotX()
						&& n > s.getSpotY(); m++, n--) {

					if (!_board.getSpotAt(m, n).isEmpty() && _board.getSpotAt(m, n).getSpotColor() != player_color) {
						valid_moves.add(true);

					} else {
						valid_moves.add(false);
					}

				}

			}

		}
		for (int x = southwest1.getSpotX(), y = southwest1.getSpotY(); x < spot.getSpotX()
				&& y > spot.getSpotY(); x++, y--) {

			Spot s = _board.getSpotAt(x, y);
			if (s.getSpotColor() == player_color && s.getSpotX() + 1 != spot.getSpotX()
					&& s.getSpotY() - 1 != spot.getSpotY() && !s.isEmpty()) {
				for (int m = s.getSpotX() + 1, n = s.getSpotY() - 1; m < spot.getSpotX()
						&& n > spot.getSpotY(); m++, n--) {

					if (!_board.getSpotAt(m, n).isEmpty() && _board.getSpotAt(m, n).getSpotColor() != player_color) {
						valid_moves.add(true);

					} else {
						valid_moves.add(false);
					}

				}
			}
		}
		// check southeast(northwest)
		Spot northwest1 = null;
		Spot southeast1 = null;

		if (spot.getSpotX() - spot.getSpotY() >= 0) {
			northwest1 = _board.getSpotAt(spot.getSpotX() - spot.getSpotY(), 0);
			southeast1 = _board.getSpotAt(7, 7 - (spot.getSpotX() - spot.getSpotY()));
		} else {
			northwest1 = _board.getSpotAt(0, spot.getSpotY() - spot.getSpotX());
			southeast1 = _board.getSpotAt(7 - (spot.getSpotY() - spot.getSpotX()), 7);
		}
		for (int x = northwest1.getSpotX(), y = northwest1.getSpotY(); x < spot.getSpotX()
				&& y < spot.getSpotY(); x++, y++) {
			Spot s = _board.getSpotAt(x, y);
			if (s.getSpotColor() == player_color && s.getSpotX() + 1 != spot.getSpotX()
					&& s.getSpotY() + 1 != spot.getSpotY() && !s.isEmpty()) {

				for (int m = s.getSpotX() + 1, n = s.getSpotY() + 1; m < spot.getSpotX()
						&& n < spot.getSpotY(); m++, n++) {
					if (_board.getSpotAt(m, n).isEmpty() && _board.getSpotAt(m, n).getSpotColor() != player_color) {
						valid_moves.add(true);
					} else {
						valid_moves.add(false);
					}

				}

			}

		}
		for (int x = spot.getSpotX() + 1, y = spot.getSpotY() + 1; x <= southeast1.getSpotX()
				&& y <= southeast1.getSpotY(); x++, y++) {
			Spot s = _board.getSpotAt(x, y);
			if (s.getSpotColor() == player_color && s.getSpotX() + 1 != spot.getSpotX()
					&& s.getSpotY() + 1 != spot.getSpotY() && !s.isEmpty()) {
				for (int m = spot.getSpotX() + 1, n = spot.getSpotY() + 1; m < s.getSpotX()
						&& n < s.getSpotY(); m++, n++) {

					if (_board.getSpotAt(m, n).isEmpty() && _board.getSpotAt(m, n).getSpotColor() != player_color) {
						valid_moves.add(true);
					} else {
						valid_moves.add(false);
					}

				}
			}
		}

		if (spot.isEmpty()) {
			int repeat = 0;
			for (boolean item : valid_moves) {
				if (item) {
					repeat++;
				}

			}
			if (repeat == 0) {
				return;
			} else {
				spot.highlightSpot();
				spot.setSpotColor(player_color);
				spot.setSpot();
			}
		} else {
			return;
		}

		if (spot != null) {
			// store the spots that need to be fliped later
			List<Spot> spotList = new ArrayList<Spot>();
			// horizontal
			for (int x = 0; x < _board.getSpotWidth(); x++) {
				Spot same = _board.getSpotAt(x, spot.getSpotY());
				if (same.getSpotColor() == spot.getSpotColor() && !same.isEmpty()) {

					if (same.getSpotX() < spot.getSpotX() && same.getSpotX() + 1 != spot.getSpotX()) {
						for (int i = same.getSpotX() + 1; i < spot.getSpotX(); i++) {
							Spot add = _board.getSpotAt(i, spot.getSpotY());
							if (add.getSpotColor() != spot.getSpotColor()) {
								spotList.add(add);
							}
						}
					} else if (same.getSpotX() > spot.getSpotX() && same.getSpotX() + 1 != spot.getSpotX()) {
						for (int i = spot.getSpotX() + 1; i < same.getSpotX(); i++) {
							Spot add = _board.getSpotAt(i, spot.getSpotY());
							if (add.getSpotColor() != spot.getSpotColor()) {
								spotList.add(add);
							}
						}
					}
				}
			}

			// vertical

			for (int y = 0; y < _board.getSpotHeight(); y++) {
				Spot same = _board.getSpotAt(spot.getSpotX(), y);
				if (same.getSpotColor() == spot.getSpotColor() && !same.isEmpty()) {
					if (same.getSpotY() < spot.getSpotY() && same.getSpotY() + 1 != spot.getSpotY()) {
						for (int i = same.getSpotY() + 1; i < spot.getSpotY(); i++) {
							Spot add = _board.getSpotAt(spot.getSpotX(), i);
							if (add.getSpotColor() != spot.getSpotColor()) {
								spotList.add(add);
							}
						}
					} else if (same.getSpotY() > spot.getSpotY() && same.getSpotY() - 1 != spot.getSpotY()) {
						for (int i = spot.getSpotY() + 1; i < same.getSpotY(); i++) {
							Spot add = _board.getSpotAt(spot.getSpotX(), i);
							if (add.getSpotColor() != spot.getSpotColor()) {
								spotList.add(add);
							}
						}
					}
				}
			}

			// northeast(southwest)
			Spot northeast = null;
			Spot southwest = null;

			if (spot.getSpotX() + spot.getSpotY() <= _board.getSpotHeight() - 1) {
				northeast = _board.getSpotAt(spot.getSpotX() + spot.getSpotY(), 0);
				southwest = _board.getSpotAt(0, spot.getSpotX() + spot.getSpotY());
			} else {
				northeast = _board.getSpotAt(7, spot.getSpotX() + spot.getSpotY() - 7);
				southwest = _board.getSpotAt(spot.getSpotX() + spot.getSpotY() - 7, 7);
			}
			for (int x = spot.getSpotX() + 1, y = spot.getSpotY() - 1; x <= northeast.getSpotX()
					&& y >= northeast.getSpotY(); x++, y--) {
				Spot same = _board.getSpotAt(x, y);
				if (same.getSpotColor() == spot.getSpotColor() && same.getSpotX() - 1 != spot.getSpotX()
						&& same.getSpotY() + 1 != spot.getSpotY() && !same.isEmpty()) {
					for (int m = spot.getSpotX() + 1, n = spot.getSpotY() - 1; m < same.getSpotX()
							&& n > same.getSpotY(); m++, n--) {
						Spot add = _board.getSpotAt(m, n);

						if (add.getSpotColor() != spot.getSpotColor()) {
							spotList.add(add);
						}

					}

				}

			}
			for (int x = southwest.getSpotX(), y = southwest.getSpotY(); x < spot.getSpotX()
					&& y > spot.getSpotY(); x++, y--) {

				Spot same = _board.getSpotAt(x, y);
				if (same.getSpotColor() == spot.getSpotColor() && same.getSpotX() + 1 != spot.getSpotX()
						&& same.getSpotY() - 1 != spot.getSpotY() && !same.isEmpty()) {
					for (int m = same.getSpotX() + 1, n = same.getSpotY() - 1; m < spot.getSpotX()
							&& n > spot.getSpotY(); m++, n--) {

						Spot add = _board.getSpotAt(m, n);

						if (add.getSpotColor() != spot.getSpotColor()) {
							spotList.add(add);
						}

					}
				}
			}

			// southeast(northwest)
			Spot northwest = null;
			Spot southeast = null;

			if (spot.getSpotX() - spot.getSpotY() >= 0) {
				northwest = _board.getSpotAt(spot.getSpotX() - spot.getSpotY(), 0);
				southeast = _board.getSpotAt(7, 7 - (spot.getSpotX() - spot.getSpotY()));
			} else {
				northwest = _board.getSpotAt(0, spot.getSpotY() - spot.getSpotX());
				southeast = _board.getSpotAt(7 - (spot.getSpotY() - spot.getSpotX()), 7);
			}
			for (int x = northwest.getSpotX(), y = northwest.getSpotY(); x < spot.getSpotX()
					&& y < spot.getSpotY(); x++, y++) {
				Spot same = _board.getSpotAt(x, y);
				if (same.getSpotColor() == spot.getSpotColor() && same.getSpotX() + 1 != spot.getSpotX()
						&& same.getSpotY() + 1 != spot.getSpotY() && !same.isEmpty()) {

					for (int m = x + 1, n = y + 1; m < spot.getSpotX() && n < spot.getSpotY(); m++, n++) {
						Spot add = _board.getSpotAt(m, n);
						if (add.getSpotColor() != spot.getSpotColor()) {
							spotList.add(add);
						}

					}

				}

			}
			for (int x = spot.getSpotX() + 1, y = spot.getSpotY() + 1; x <= southeast.getSpotX()
					&& y <= southeast.getSpotY(); x++, y++) {
				Spot same = _board.getSpotAt(x, y);
				if (same.getSpotColor() == spot.getSpotColor() && same.getSpotX() + 1 != spot.getSpotX()
						&& same.getSpotY() + 1 != spot.getSpotY() && !same.isEmpty()) {
					for (int m = spot.getSpotX() + 1, n = spot.getSpotY() + 1; m < same.getSpotX()
							&& n < same.getSpotY(); m++, n++) {

						Spot add = _board.getSpotAt(m, n);
						if (add.getSpotColor() != spot.getSpotColor()) {
							spotList.add(add);
						}

					}
				}
			}

			for (Spot each : spotList) {

				each.setSpotColor(spot.getSpotColor());

			}

		}

		int num = 0;
		for (Spot s : _board) {
			if (!s.isEmpty()) {
				num++;
			}
		}
		if (num == _board.getSpotHeight() * _board.getSpotWidth()) {
			_game_won = true;
		}

		if (_game_won) {
			int scoreW = 0;
			int scoreB = 0;
			for (Spot board_spot : _board) {

				if (board_spot.getSpotColor() == Color.WHITE) {
					scoreW += 1;
				} else {
					scoreB += 1;
				}

			}
			if (scoreW > scoreB) {
				_message.setText("Game over. " + " White wins. " + "Score: " + scoreW + " to " + scoreB);
			} else {
				_message.setText("Game over. " + " Black" + " wins. " + "Score: " + scoreW + " to " + scoreB);
			}
		} else {
			_message.setText(next_player_name + " to play.");
		}

	}

	@Override
	public void spotEntered(Spot spot) {
		if (_game_won) {
			return;
		}

		spot.highlightSpot();

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
