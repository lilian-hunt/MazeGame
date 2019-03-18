import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
/**
 * Maze Game
 *
 * The Maze Game.
 * There is a maze board and a player moving around the board.
 * The player can step left, right, up or down.
 * However, you need to complete the maze within a given number of steps.
 *
 * As in any maze, there are walls that you cannot move through. If you try to
 * move through a wall, you lose a life. You have a limited number of lives.
 * There is also gold on the board that you can collect if you move ontop of it.
 *
 */
public class MazeGame {
  
	static int numLives, numSteps, amountGold, numRows;
	static int xPos;
	static int yPos;
	static int columns;
	static int[] destination;
	static String[][] grid;
	
    /**
     * Initialises the game from the given configuration file.
     * This includes the number of lives, the number of steps, the starting gold
     * and the board.
     *
     * If the configuration file name is "DEFAULT", it loads the default
     * game configuration.
     */
    public static void initialiseGame(String configFileName) throws IOException {
		if (configFileName.equals("DEFAULT")) {
			numLives = 3;
			numSteps = 20;
			amountGold = 0;
			numRows = 4;
			columns = 10;
			destination = new int[] {1,0};
			grid = new String[][] {{"#","@"," ","#","#"," "," ","&","4","#"},
								   {"#","#"," "," ","#"," ","#","#"," ","#"},
								   {"#","#","#"," "," ","3","#"," "," "," "},
								   {"#","#","#","#","#","#","#"," "," ","#"}};
			xPos = 7;
			yPos = 0;
			
		}
		else {
			File f = new File(configFileName);
			Scanner fileReader = new Scanner(f);
			numLives = fileReader.nextInt();
			numSteps = fileReader.nextInt();
			amountGold = fileReader.nextInt();
			numRows = fileReader.nextInt();
			String firstLine = fileReader.nextLine();
			firstLine = fileReader.nextLine();
			columns = firstLine.length();
			destination = new int[] {-1,-1};
			grid = new String[numRows][columns];

			for (int j = 0; j < columns; j++){
						grid[0][j] = firstLine.charAt(j)+"";
				if ((firstLine.charAt(j)+"").equals("@")){
						destination = new int[] {j,0};
				}
				if ((firstLine.charAt(j)+"").equals("&")){
								xPos = j;
								yPos = 0;
							}
					}
			for (int i = 1; i < numRows; i++) {
				if (fileReader.hasNextLine()){
					String line = fileReader.nextLine();
					for (int j = 0; j < columns; j++){
						try {
							grid[i][j] = line.charAt(j)+"";
							if ((line.charAt(j)+"").equals("@")){
								destination = new int[] {j,i};
							}
							if ((line.charAt(j)+"").equals("&")){
								xPos = j;
								yPos = i;
							}
						}
						catch (StringIndexOutOfBoundsException e){
							grid[i][j] = " ";
						}
					}
				}
			} 
		}
    }

    /**
     * Save the current board to the given file name.
     * Note: save it in the same format as you read it in.
     * That is:
     *
     * <number of lives> <number of steps> <amount of gold> <number of rows on the board>
     * <BOARD>
     */
    public static void saveGame(String toFileName) throws IOException {
        PrintWriter p = new PrintWriter(toFileName);
		p.println(numLives + " " + numSteps + " " + amountGold + " " + numRows);
		for (String[] ss : grid) {
			for (String s : ss) {
				p.print(s);
			}
			p.println();
		}
		p.close();
		System.out.println("Successfully saved the current game configuration to '"+toFileName+"'.");
		
    }

    /**
     * Gets the current x position of the player.
     *
     * @return The players current x position.
     */
    public static int getCurrentXPosition() {
        return xPos;
    }

    /**
     * Gets the current y position of the player.
     *
     * @return The players current y position.
     */
    public static int getCurrentYPosition() {
        return yPos;
    }

    /**
     * Gets the number of lives the player currently has.
     *
     * @return The number of lives the player currently has.
     */
    public static int numberOfLives() {
        return numLives;
    }

    /**
     * Gets the number of remaining steps that the player can use.
     *
     * @return The number of steps remaining in the game.
     */
    public static int numberOfStepsRemaining() {
        return numSteps;
    }

    /**
     * Gets the amount of gold that the player has collected so far.
     *
     * @return The amount of gold the player has collected so far.
     */
    public static int amountOfGold() {
        return amountGold;
    }


    /**
     * Checks to see if the player has completed the maze.
     * The player has completed the maze if they have reached the destination.
     *
     * @return True if the player has completed the maze.
     */
    public static boolean isMazeCompleted() {
        if (destination[0] == getCurrentXPosition() && destination[1] == getCurrentYPosition()){
			return true;
		}
        return false;
    }

    /**
     * Checks to see if it is the end of the game.
     * It is the end of the game if one of the following conditions is true:
     *  - There are no remaining steps.
     *  - The player has no lives.
     *  - The player has completed the maze.
     *
     * @return True if any one of the conditions that end the game is true.
     */

    public static boolean isGameEnd() {
        if (numberOfStepsRemaining() <= 0 || numberOfLives() <= 0 || isMazeCompleted() == true){
			return true;
		}
        return false;
    }

    /**
     * Checks if the coordinates (x, y) are valid.
     * That is, if they are on the board.
     *
     * @args x The x coordinate.
     * @args y The y coordinate.
     * @return True if the given coordinates are valid (on the board),
     *         otherwise, false (the coordinates are out or range).
     */
    public static boolean isValidCoordinates(int x, int y) {
        if (y < numRows && y >= 0 && x < columns && x>=0){
			return true;
		}
        return false;
    }

    /**
     * Checks if a move to the given coordinates is valid.
     * A move is invalid if:
     *  - It is move to a coordinate off the board.
     *  - There is a wall at that coordinate.
     *  - The game is ended.
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     * @return True if the move is valid, otherwise false.
     */
    public static boolean canMoveTo(int x, int y) {
        // TODO: Implement this method.
		
	//	boolean a = isValidCoordinates(x,y);
	//	System.out.println(a);
		if (isGameEnd() == false){
			if (isValidCoordinates(x,y) == false || grid[y][x].equals("#") ){
				return false;
			}
			else return true;
			}
		return false;
    }

    /**
     * Move the player to the given coordinates on the board.
     * After a successful move, it prints "Moved to (x, y)."
     * where (x, y) were the coordinates given.
     *
     * If there was gold at the position the player moved to,
     * the gold should be collected and the message "Plus n gold."
     * should also be printed, where n is the amount of gold collected.
     *
     * If it is an invalid move, a life is lost.
     * The method prints: "Invalid move. One life lost."
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     */
    public static void moveTo(int x, int y) {
		if (canMoveTo(x,y) == false){
			System.out.println("Invalid move. One life lost.");
			numLives -= 1;	
			numSteps -=1;
		}
		else {
			System.out.println("Moved to ("+ x + ", " + y +").");	
			if (Character.isDigit(grid[y][x].charAt(0))){
				System.out.println("Plus "+ grid[y][x] + " gold.");
				amountGold += Integer.parseInt(grid[y][x]);
			}	
			grid[getCurrentYPosition()][getCurrentXPosition()] = ".";
			xPos = x;
			yPos = y;	
			grid[y][x] = "&";
			numSteps -= 1;
		}
    }

    /**
     * Prints out the help message.
     */
    public static void printHelp() {
        System.out.println("Usage: You can type one of the following commands.");
		System.out.println("help         Print this help message.");
		System.out.println("board        Print the current board.");
		System.out.println("status       Print the current status.");
		System.out.println("left         Move the player 1 square to the left.");
		System.out.println("right        Move the player 1 square to the right.");
		System.out.println("up           Move the player 1 square up.");
		System.out.println("down         Move the player 1 square down.");
		System.out.println("save <file>  Save the current game configuration to the given file.");
    }

    /**
     * Prints out the status message.
     */
    public static void printStatus() {
        System.out.println("Number of live(s): " + numberOfLives());
		System.out.println("Number of step(s) remaining: " + numberOfStepsRemaining());
		System.out.println("Amount of gold: " + amountOfGold());
    }
	
    /**
     * Prints out the board.
     */
    public static void printBoard() {
		for (String[] cc : grid){
			for (String c :cc){
				System.out.print(c);
			}
			System.out.println();
		}
    }
	
    /**
     * Performs the given action by calling the appropriate helper methods.
     * [For example, calling the printHelp() method if the action is "help".]
     *
     * The valid actions are "help", "board", "status", "left", "right",
     * "up", "down", and "save".
     * [Note: The actions are case insensitive.]
     * If it is not a valid action, an IllegalArgumentException should be thrown.
     *
     * @args action The action we are performing.
     * @throws IllegalArgumentException If the action given isn't one of the
     *         allowed actions.
     */
    public static void performAction(String action) throws IllegalArgumentException {
        String up = action;
		String[] spl = action.split(" ");
		action = action.toLowerCase().trim();
		
		if (action.equals("help")){
			printHelp();
		}
		else if (action.equals("board")){
			printBoard();
		}
		else if (action.equals("status")){
			printStatus();
		}
		else if (action.equals("left")){
			moveTo(getCurrentXPosition()-1,getCurrentYPosition());
		}
		else if (action.equals("right")){
			moveTo(getCurrentXPosition()+1,getCurrentYPosition());
		}
		else if (action.equals("up")){
			moveTo(getCurrentXPosition(),getCurrentYPosition()-1);
		}
		else if (action.equals("down")){
			moveTo(getCurrentXPosition(),getCurrentYPosition()+1);
		}
		else if (spl.length == 2 && spl[0].equalsIgnoreCase("save")){
			try {
				saveGame(spl[1]);
			}
			catch (IOException e){	
				System.out.println("Error: Could not save the current game configuration to '" +spl[1] + "'.");
			}
		}
		else if (action.trim().isEmpty()){
//ignores blanks
		}
		else throw new IllegalArgumentException("Error: Could not find command '"+up+"'."+"\n"+
			"To find the list of valid commands, please type 'help'.");
    }
				
 	public static void printEndGame() {
		System.out.println("Congratulations! You completed the maze!\nYour final status is:");
		printStatus();
	}
    /**
     * The main method of your program.
     *
     * @args args[0] The game configuration file from which to initialise the
     *       maze game. If it is DEFAULT, load the default configuration.
     */
    public static void main(String[] args) {
        // Run your program (reading in from args etc) from here.
		if (args.length < 1) {
			System.out.println("Error: Too few arguments given. Expected 1 argument, found 0.");
			System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
		}
		else if (args.length > 1){
			System.out.println("Error: Too many arguments given. Expected 1 argument, found " + args.length+ ".");
			System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
		}
		else if (args.length == 1){
			try {
				initialiseGame(args[0]); 
			}
			catch (IOException e ){
				System.out.println("Error: Could not load the game configuration from '" + args[0]+ "'.");
			return;
			}
				boolean done = false;
				Scanner keyboard = new Scanner(System.in);
				while (keyboard.hasNextLine()&&!isGameEnd()){
					String command = keyboard.nextLine();
					try {
						performAction(command);
					}
					catch (IllegalArgumentException e){
						System.out.println(e.getMessage());
					}
						if (isMazeCompleted() == true){
							printEndGame();
							done = true;
							break;
						}
						else if (numberOfLives() == 0 && numberOfStepsRemaining() == 0){
							System.out.println("Oh no! You have no lives and no steps left.\nBetter luck next time!");
							done = true;
							break;
						}
						else if (numberOfLives() == 0){
							System.out.println("Oh no! You have no lives left.\nBetter luck next time!");
							done = true;
							break;
						} 
						else if (numberOfStepsRemaining() == 0){
							System.out.println("Oh no! You have no steps left.\nBetter luck next time!");
							done = true;
							break;
						} 
						
					
				
				}
				if (done == false) System.out.println("You did not complete the game.");
			}
			
	
		}
		
    
}
