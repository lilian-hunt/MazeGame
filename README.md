# MazeGame
To start the game the MazeGame takes 1 command line argument.

This is the file that we are going to be reading in the game configuration from.

Type 'help' to see a list of possible options. This will return the following options: 
  
  help Print the help message.
  board Print the current board.
  status Print the current status.
  left Move the player 1 square to the left.
  right Move the player 1 square to the right.
  up Move the player 1 square up.
  down Move the player 1 square down.
  save <file> Save the current game configuration to the given file.

You will have a maze board and a player moving around the board.

The player can step left, right, up or down.

However, you need to complete the maze within a given number of steps.

As in any maze, there are walls that you cannot move through.

If you try to move through a wall, you lose a life.

There is also gold on the board that you can collect if you move ontop of it.

The game can end in one of the following ways:

1. The player has completed the maze. That is, they have reached the destination.
2. The player has no lives.
3. The player has no remaining steps.
