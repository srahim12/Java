package samp;

import java.io.IOException;
import java.util.Scanner;

/**
 * The Chess class is the main class where chess games are created and played
 * @author Shah Rahim, John Chen
 *
 */
public class Chess {
	
	public static void main(String[] args) throws IOException, NullPointerException {
		Board br = new Board();
		Game game = new Game();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String input = "";
		boolean draw = false;
		boolean whiteTurn = true;
		br.toString();
		String endGame = "";
		while (true) {
			if (whiteTurn == true) {
				System.out.printf("White's move: ");
			} 
			else {
				System.out.printf("Black's move: ");
			}
			input = sc.nextLine();
			if (input.toLowerCase().equals("resign")) {
				endGame = "Resign";
				break;
			} 
			else if (input.length() == 11) {
				if (input.substring(6, 11).equals("draw?")) {
					draw = true;
				}
				whiteTurn = changeTurn(whiteTurn);
			} 
			else if (input.length() > 5|| (input.length()<5&& !input.toLowerCase().equals("draw"))) 
			{
				System.out.println("Invalid move try again");
				continue;
			}
			else if(input.toLowerCase().equals("draw")&& draw ==true){
				endGame = "Draw";
				break;
			}
			else if (input.charAt(2) != ' '){
				System.out.println("Invalid move try again");
				continue;
			}
			else {
				
				if(game.move(br, input, whiteTurn)!=true){
					System.out.println("Illegal move, try again");
					continue;
				}
				else if(game.checkMate(br, whiteTurn)==true){
					endGame = "CheckMate";
					break;
				}
				else if(game.enemyCheck(br, whiteTurn).equals("enemyCheck")){
					System.out.println("Check");
				}
				resetEpos(br,whiteTurn);
			}
			whiteTurn = changeTurn(whiteTurn);
			System.out.println();
			br.toString();
		}
	
			if(endGame.equals("CheckMate")){
				System.out.println();
				br.toString();
				System.out.println("CheckMate! " + turnColor(whiteTurn) + " Wins!");
			}
			else if(endGame.equals("Draw")){
				System.out.println("Game Ended---> Draw");
			}
			else if(endGame.equals("Resign")){
				System.out.println("Game Ended---> " + turnColor(whiteTurn) + " resigned!");
			}
	}
	
	/**
	 * This method changes the colors turn when invoked
	 * @param whiteTurn the current turn
	 * @return returns a boolean-- the opposite colors turn
	 */
	public static boolean changeTurn(boolean whiteTurn) {
		if (whiteTurn == true) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method takes the boolean turn value and convertis it into a string color
	 * @param whiteTurn the boolean which indicates whos turn it is
	 * @return returns a string of the color
	 */
	public static String turnColor(boolean whiteTurn){
		if (whiteTurn == true) {
			return "White";
		}
		return "Black";
	}
	
	/**
	 * This method resets empassant for paws when a piece has moved
	 * @param br the boaard to be examined
	 * @param whiteTurn indicating which color piece has moved
	 */
	public static void resetEpos(Board br, boolean whiteTurn){
		if (whiteTurn == true){
			for (int i = 0; i < 8; i++) {
				if (br.board[3][i].getClass().isInstance(new Pawn())) {
					br.board[3][i].ePos = false;
				}
			}
		}
		else{
			for (int i = 0; i < 8; i++) {
				if (br.board[4][i].getClass().isInstance(new Pawn())) {
					br.board[4][i].ePos = false;
				}
			}
		}
	}
}
