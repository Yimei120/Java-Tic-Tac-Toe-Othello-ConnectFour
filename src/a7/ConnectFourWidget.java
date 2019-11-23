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
		
		Spot bottom=null;
		
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
		
		
		for (int n=_board.getSpotHeight()-1; n>=0; n--) {
			if (_board.getSpotAt(spot.getSpotX(), n).isEmpty()) {
			    bottom = _board.getSpotAt(spot.getSpotX(), n);
			    bottom.setSpotColor(player_color);
				bottom.toggleSpot();
				break;
			}else {continue;}
		}
		
        
        // Here should do something to indicate winning.
        // check for the vertical direction
        for (int i=0;i < _board.getSpotHeight(); i++) {
        	List<Spot> SpotList = new ArrayList<Spot>();
        	Spot spotupp = _board.getSpotAt(bottom.getSpotX(), _board.getSpotHeight() - 1- i);
        	int count = 0;
        		if (! spotupp.isEmpty()&&(spotupp.getSpotColor()== bottom.getSpotColor())) {
        			count ++;
        			SpotList.add(spotupp);
        			
        		}else {count = 0;}
        	if (count == 4) {
        		_game_won = true;
        		for (Spot each: SpotList) {
        			each.getHighlight();
        			
        		}
        	}
        }
        // check for horizontal direction
        for (int j=0; j<_board.getSpotWidth();j++) {
        	int count = 0;
        	List<Spot> SpotList = new ArrayList<Spot>();
        	Spot spotRight = _board.getSpotAt(j, bottom.getSpotY());
        		if (!spotRight.isEmpty()&&spotRight.getSpotColor() == bottom.getSpotColor()) {
        			count ++;
        			SpotList.add(spotRight);
        		
        	}else {count =0;}
        	if (count == 4) {
        		_game_won = true;
        		for (Spot each:SpotList) {
        			each.getHighlight();
        		}
        	}
        }
        // check for north(south)east direction
       
        		
        		Spot spotNortheast = null;
        		 
        		if ((bottom.getSpotX()+bottom.getSpotY())< (_board.getSpotWidth()-1)) {
        			spotNortheast = _board.getSpotAt(bottom.getSpotX()+bottom.getSpotY(), 0);
        			
        		}else {
        		
        			spotNortheast = _board.getSpotAt(_board.getSpotWidth()-1, bottom.getSpotX()+bottom.getSpotY()-(_board.getSpotWidth()-1));
        		}
        		
        		for(int x=1; x<=spotNortheast.getSpotX() - bottom.getSpotX();x++) {
        	        	for (int y=1; y<=_board.getSpotHeight()-bottom.getSpotY();y++) {
        		
        	        		int count = 0;
        	        		List<Spot> SpotList = new ArrayList<Spot>();
        		Spot goDown = _board.getSpotAt(spotNortheast.getSpotX()-x,spotNortheast.getSpotY()+y);
        				if ( !goDown.isEmpty()&&goDown.getSpotColor() == bottom.getSpotColor()) {
        					count ++;
        					SpotList.add(goDown);
        				}else {count = 0;}
        		if (count == 4) {
            		_game_won = true;
            	for (Spot each:SpotList) {
            		each.getHighlight();
            	}
            	}
        	}
        }
        // check for north(south)west direction
  
        		
        		Spot Northwest = null;
        		 
        		if (bottom.getSpotX()- bottom.getSpotY()<0) {
        			
        			 Northwest = _board.getSpotAt(0, bottom.getSpotY()- bottom.getSpotX());
        		}else {
        			
        			Northwest = _board.getSpotAt(bottom.getSpotX()- bottom.getSpotY(), 0);
        		}
        	      for(int x=1; x<=bottom.getSpotX() - Northwest.getSpotX();x++) {
        	        	for (int y=1; y<=_board.getSpotHeight()-bottom.getSpotY();y++) {
        	        		
        	        		int count = 0;
        	        		List<Spot> SpotList = new ArrayList<Spot>();
        		
        		Spot goDown = _board.getSpotAt(Northwest.getSpotX()+ x, Northwest.getSpotY()+y);

        				if ((! goDown.isEmpty()&&goDown.getSpotColor() == bottom.getSpotColor())) {
        					count ++;
        					SpotList.add(goDown);
        				}else {count = 0;}
        		if (count == 4) {
            		_game_won = true;
            		for (Spot each:SpotList) {
            			each.getHighlight();
            		}
            		
            	}
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
			
			int num =0;
			for (Spot s: _board) {
				if (!s.isEmpty()) {
					num++;
				}
				if (num == _board.getHeight()*_board.getWidth() && !_game_won) {
					_message.setText("Draw game.");
					
				}
			}
		}
		
		
	}

	@Override
	public void spotEntered(Spot spot) {

		if (_game_won) {
			return;
		}
		Spot Hspot = null;
		if (spot.isEmpty()) {
			for (int y = 0; y <= spot.getSpotY() - 1; y++) {
				Hspot = new JSpot(spot.getBackground(), spot.getSpotColor(), spot.getHighlight(), spot.getBoard(),
						spot.getSpotX(), y);
				Hspot.getHighlight();
			}

		}

	}

	@Override
	public void spotExited(Spot spot) {
		if (_game_won) {
			return;
		}

		Spot Uspot = null;
		if (spot.isEmpty()) {
			for (int y = 0; y <= spot.getSpotY() - 1; y++) {
				Uspot = new JSpot(spot.getBackground(), spot.getSpotColor(), spot.getHighlight(), spot.getBoard(),
						spot.getSpotX(), y);
				Uspot.unhighlightSpot();
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
