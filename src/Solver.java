import java.io.File;
import java.util.Scanner;

public class Solver {
	public Solver(Board board){
		autoPlay(board, Player.Max);
	 	//Action result = decide(Player.Max, board, 0);
	 	/*System.out.println(result.getScore());
	 	System.out.println(result.getBoard());*/
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
		if (player == player.Max){
			Action result = decide(player.Max, board, 0);
			autoPlay(result.getBoard(), player.Min);
		}else{
			Action result = decide(player.Min, board, 0);
			autoPlay(result.getBoard(), player.Max);
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
