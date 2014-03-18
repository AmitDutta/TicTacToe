import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Solver {
	public Solver(Board board){
		//autoPlay(board, Player.Max);
	 	//Action result = decide(Player.Min, board, 0);
		//Action result = decide(Player.Max, board, 0);
		//Action result = decideAlphaBeta(Player.Max, board, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
		System.out.println("MiniMax");
		Action result = minimax(Player.Max, board);
		System.out.println(result.getScore());
	 	System.out.println(result.getBoard());
	 	
	 	System.out.println("MiniMax discreet");
		System.out.println(minimax2(Player.Max, board));
		
		//System.out.println("MiniMax ab");
		//System.out.println(minimax2alphabeta(Player.Max, board, Integer.MIN_VALUE, Integer.MAX_VALUE));
		
		System.out.println("MiniMax ab");
		Action result4 = minimax2alphabeta(Player.Max, board, Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.println(result4.getScore());
		System.out.println(result4.getBoard());
	 	
	 	/*System.out.println("MiniMax with Alpha Beta");
		Action result3 = minimax(Player.Min, board, Integer.MAX_VALUE, Integer.MIN_VALUE);
		System.out.println(result3.getScore());
	 	System.out.println(result3.getBoard()); */
	 	
		/*Action result2 = decide(Player.Min, board, 0);
	 	System.out.println(result2.getScore());
	 	System.out.println(result2.getBoard());*/
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
				if (act == null) act = new Action(child, action.getScore());
				else if (action.getScore() > act.getScore()) act = new Action(child, action.getScore());
			}
			return act;
		}else{
			Action act = null;
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Max, child);
				if (act == null) act = new Action(child, action.getScore());
				else if (action.getScore() < act.getScore()) act = new Action(child, action.getScore());
			}
			return act;
		}
	}
	
	public int minimax2(Player player, Board board){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) return score;
		if (player == Player.Max){
			int val = Integer.MIN_VALUE;
			for (Board child : board.getNeighbours(player)){
				val = Math.max(val, minimax2(Player.Min, child));
			}
			return val;
		}else{
			int val = Integer.MAX_VALUE;
			for (Board child : board.getNeighbours(player)){
				val = Math.min(val, minimax2(Player.Max, child));
			}
			return val;
		}
	}
	
	public Action minimax2alphabeta(Player player, Board board, int alpha, int beta){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) return new Action(board, score);
		if (player == Player.Max){
			int val = Integer.MIN_VALUE;
			Action result = null;
			for (Board child : board.getNeighbours(player)){
				val = Math.max(val, minimax2(Player.Min, child));
				result = new Action(child, val);
				if (val >= beta) {
					return result;
				}
				alpha = Math.max(alpha, val);
			}
			return result;
		}else{
			int val = Integer.MAX_VALUE;
			Action result = null;
			for (Board child : board.getNeighbours(player)){
				val = Math.min(val, minimax2(Player.Max, child));
				result = new Action(child, val);
				if (val <= alpha) return result;
				beta = Math.min(beta, val);
			}
			return result;
		}
	}
	
	public Action minimax(Player player, Board board, int alpha, int beta){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) {
			return new Action(board, score);
		}
		if (player == Player.Max){
			Action act = new Action(null, beta);
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Min, child, act.getScore(), alpha);
				if (act.getBoard() == null) act = new Action(child, action.getScore());
				if (action.getScore() > act.getScore()) act = new Action(child, action.getScore());
				if(act.getScore() > alpha) {
					//System.out.println(act.getScore());
					//System.out.println("Pruned Alpha");					
					return new Action(child, alpha);
				}
			}
			return act;
		}else{
			Action act = new Action(null, alpha);
			for (Board child : board.getNeighbours(player)){
				Action action = minimax(Player.Max, child, beta, act.getScore());
				if (act.getBoard() == null) act = new Action(child, action.getScore());
				if (action.getScore() < act.getScore()) act = new Action(child, action.getScore());
				if (act.getScore() < beta) {
					//System.out.println(act.getScore());
					//System.out.println("Pruned Beta");					
					return new Action(child, beta);
				}
			}
			return act;
		}
	}
	
	/*public Action minimax2(Player player, Board board, int alpha, int beta){
		int score = board.getUtility();
		if (score != Integer.MAX_VALUE) {
			return new Action(board, score);
		}
		if (player == Player.Max){
			
		}
	}*/
	
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
		readGame(s);
		//randomGame();		
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
