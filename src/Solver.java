import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Solver {
	public Solver(Board board){
		//autoPlay(board, Player.Max);
	 	//Action result = decide(Player.Min, board, 0);
		//Action result = decide(Player.Max, board, 0);
		//Action result = decideAlphaBeta(Player.Max, board, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
		Action result = minimax(Player.Min, board);
		System.out.println(result.getScore());
	 	System.out.println(result.getBoard());
	 	
		Action result2 = decide(Player.Min, board, 0);
	 	System.out.println(result2.getScore());
	 	System.out.println(result2.getBoard());
	}
	public void autoPlay(Board board, Player player){
		if (board.isComplete()){
			int score = board.getUtility();
			if (score == 1){				
				System.out.println("Max Won");
				System.out.println(board);
			}else if (score == -1){
				System.out.println("Min Won");
				System.out.println(board);
			}else{
				System.out.println("Draw");
				System.out.println(board);
			}
			return;
		}
		if (player == Player.Max){
			Action result = minimax(Player.Max, board, Integer.MAX_VALUE, Integer.MIN_VALUE);
			autoPlay(result.getBoard(), Player.Min);
		}else{
			Action result = minimax(Player.Min, board, Integer.MAX_VALUE, Integer.MIN_VALUE);
			autoPlay(result.getBoard(), Player.Max);
		}
		
	}
	
	public Action minimax(Player player, Board board){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) {
			return new Action(board, score);
		}
		if (player == Player.Max){
			Action act = null;
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Min, child);
				if (act == null) act = action;
				else if (action.getScore() > act.getScore()) act = new Action(child, action.getScore());
			}
			return act;
		}else{
			Action act = null;
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Max, child);
				if (act == null) act = action;
				else if (action.getScore() < act.getScore()) act = new Action(child, action.getScore());
			}
			return act;
		}
	}
	
	public Action minimax(Player player, Board board, int alpha, int beta){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) {
			return new Action(board, score);
		}
		if (player == Player.Max){
			Action act = null;
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Min, child, act == null ? Integer.MIN_VALUE : act.getScore(), alpha);
				if (act == null) act = action;
				else if (action.getScore() > act.getScore()) act = new Action(child, action.getScore());
				if(act.getScore() > alpha) {
					System.out.println("Pruned Alpha");
					return act;
				}
			}
			return act;
		}else{
			Action act = null;
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Min, child, beta, act == null ? Integer.MAX_VALUE : act.getScore());
				if (act == null) act = action;
				else if (action.getScore() < act.getScore()) act = new Action(child, action.getScore());
				if (act.getScore() < beta) {
					System.out.println("Pruned Beta");
					return act;
				}
			}
			return act;
		}
	}
	
	public Action decide(Player player, Board board, int level){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) {
			return new Action(board, score);
		}
		Action currentAction = null;
		if (player == Player.Max){
			for (Board neighbour : board.getNeighbours(player)){				
				Action action = decide(Player.Min, neighbour, level + 1);
				/*if (level == 0) {
					System.out.print(neighbour);
					System.out.println(action.getScore());
				}*/
				if (currentAction == null){
					currentAction = new Action(neighbour, action.getScore());
				}else if (action.getScore() > currentAction.getScore()){
					currentAction =  new Action(neighbour, action.getScore());
				}
			}
		}else if (player == Player.Min){			
			for (Board neighbour : board.getNeighbours(player)){
				Action action = decide(Player.Max, neighbour, level + 1);
				if (currentAction == null){
					currentAction = new Action(neighbour, action.getScore());
				}else if (action.getScore() < currentAction.getScore()){
					currentAction = new Action(neighbour, action.getScore());
				}
			}
		}
		return currentAction;
	}
	public static void main(String... s){
		//readGame(s);
		randomGame();		
	}
	private static void randomGame(){
		int[][] squares = new int[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				squares[i][j] = Board.NAN;
			}
		}
		int num = new Random().nextInt(9);		
		int row = num / 3;
		int col = num % 3;
		squares[row][col] = Board.CROSS;
		Board board = new Board(squares);		
		new Solver(board);
	}
	private static void readGame(String... s) {
		try {
			Scanner scanner = new Scanner(new File(s[0]));
			int size = scanner.nextInt();
			int[][] squares = new int[size][size];
			for (int i = 0; i < size; i++){
				for (int j = 0; j < size; j++){					
					squares[i][j] = scanner.nextInt();
				}
			}
			new Solver(new Board(squares));
			scanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
