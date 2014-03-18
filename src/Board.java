import java.util.ArrayList;
import java.util.List;

public class Board {
	public static final int CROSS = 1;
	public static final int ZERO = 2;
	public static final int NAN = -1;	
	private int[][] squares;
	public Board(int[][] squares){
		this.squares = squares;		
	}
	public boolean isComplete(){
		boolean flag = true;
		for (int i = 0; i < squares.length; i++){
			for (int j = 0; j < squares[0].length; j++){
				if (squares[i][j] == NAN) flag = false;
			}
		}
		return flag;
	}
	public List<Board> getNeighbours(Player player){
		List<Board> neighbours = new ArrayList<Board>();
		for (int i = 0; i < squares.length; i++){
			for (int j = 0; j < squares[0].length; j++){
				if (squares[i][j] == -1){
					int[][] copy = copySquares();
					copy[i][j] = player == Player.Max ? 1 : 2;					
					neighbours.add(new Board(copy));
				}
			}
		}
		return neighbours;
	}
	private int[][] copySquares(){
		int[][] copy = new int[squares.length][squares[0].length];
		for (int i= 0; i < squares.length; i++){
			for (int j = 0; j < squares[0].length; j++){
				copy[i][j] = squares[i][j];
			}
		}
		return copy;
	}
	public int getUtility(){
		int maxrowCount = 0, maxcolumnCount = 0, minrowCount = 0, minColumnCount = 0;
		for (int i = 0; i < squares.length; i++){
			for (int j = 0; j < squares[0].length; j++){
				if (squares[i][j] == CROSS) maxrowCount++;
				else if (squares[i][j] == ZERO) minrowCount++;
				if (squares[j][i] == CROSS) maxcolumnCount++;
				else if (squares[i][j] == ZERO) minColumnCount++;
			}
			if (maxrowCount == 3 || maxcolumnCount == 3) return 1;
			else if (minrowCount == 3 || minColumnCount == 3) return -1;
			maxrowCount = maxcolumnCount = minrowCount = minColumnCount = 0;
		}
		int maxDiag = 0, minDiag = 0, maxRevDiag = 0, minRevDiag = 0;
		for (int i = 0; i < squares.length; i++){
			if (squares[i][i] == CROSS) maxDiag++;
			else if (squares[i][i] == ZERO) minDiag++;
			if (squares[i][squares.length - i -1] == CROSS) maxRevDiag++;
			else if (squares[i][i] == ZERO) minRevDiag++;			
		}
		if (maxDiag == 3 || maxRevDiag == 3) return 1;
		else if (minDiag == 3 || minRevDiag == 3) return -1;
		return isComplete() ? 0 : Integer.MAX_VALUE;
	}
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < squares.length; i++){
			for (int j = 0; j < squares[0].length; j++){
				if (squares[i][j] == 1){
					buffer.append("X ");					
				}else if (squares[i][j] == 2){
					buffer.append("0 ");
				}else{
					buffer.append("_ ");
				}
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
