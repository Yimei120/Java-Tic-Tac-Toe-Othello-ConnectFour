# Assignment 7

In this assignment, you'll make a few single widget games building on top of a SpotBoard component that I provide.

Read through the code provided. I suggest looking at the files in the following order:

Interfaces for a Spot and a 2D field of Spots called SpotBoard:
* Spot
* SpotBoard

Those interfaces are implemented as Java Swing components that extend JPanel by:
* JSpot
* JSpotBoard

The SpotListener interface can be used to observer enter/exit/click events on a Spot.

ExampleWidet is a user inteface widget that implements a simple game where two players
take turns trying to find a secret spot. ExampleGame simply sets up a top-level window 
with a single ExampleWidget component in its layout. In this game, a blue player
and a green player take turns either setting or clearing spots on the board. The goal is to
find the secret spot. When one of them does, the game is over and it calculates a "score". The
score is simply the number of spots on the board plus one for every spot that is set to your
opponent's color and minus one for every spot on the board that is set to your color. 

Look at how ExampleWidget is implemented. In particular, see you it acts as an ActionListener
for its reset button and as a SpotListener for the spots on the spot board. Look at how the
game logic is implemented as the handler for a spot click.

# Novice (10 points)

Create a TicTacToe game following the pattern of the example game. In particular, make sure you call your main game class `TicTacToeGame`
and the widget class `TicTacToeWidget`. Your game should have the following features:

* Players are black and white.
* Background of board is uniform.
* Spots are highlighted when entered only if clicking on them is a legal move (i.e., spot not already selected).
* Start of game should have welcome message and indicate that white goes first.
* After a game winning move, message should indicate who won and spot highlighting should stop.
* After a game drawing move, message should indicate that game is a draw.
* After a move that neither wins or draws, message should indicate who goes next.

See here for a demonstration: https://youtu.be/--5YJ4g1Pik

# Adept (8 points)

Create a ConnectFour game. Features should include:

* Players are red and black
* Board should be 7 columns and 6 rows.
* Background is set up as alternating column stripes.
* All empty spots of a column are highlighted when the cursor enters any spot in the column.
* Clicking on any spot in a column that contains an empty spot should set the bottommost empty spot and switch turns.
* Clicking on a spot in a column that does not contain an empty spot should do nothing.
* Welcome message that indicates red to play.
* After a game winning move, message should indicate who won and highlight winning spots. Column highlighting should stop.
* If a game draws, message should indicate that game is a draw.
* After a move that neither wins or draws, message should indicate who goes next.

See here for a demonstration: https://youtu.be/0lKWkFVp6-Q

# Jedi (7 points)

Create an Othello game. The rules for Othello can be found here: https://www.ultraboardgames.com/othello/game-rules.php

Features should include:
* Players are black and white.
* Board should be 8x8 with a checkerboard background pattern.
* The game should start with the middle 2x2 spots set up with alternating white and black pieces already set.
* Welcome message that indicates black to play.
* Spot highlighting should only work on spots that are valid moves for player whose turn is next.
* Clicking on a spot that is a valid move should set that spot to the player's color, flip any flanked spots in any direction as appropriate, and set the message to indicate whose turn is next.
* If a player has no valid move, their turn should be skipped.
* If there are no more valid moves available to either player, the game is over and the message should be set to indicate who won and by what score.

See here for a demonstration: https://youtu.be/GT-OG7ovHnA

