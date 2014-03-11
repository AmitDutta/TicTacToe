import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class BoardTest {
	@Test
	public void isGoalTest(){
		int[][] squares1 = {{1,2,1},{2,1,2},{1,2,1}};
		Board board = new Board(squares1);
		assertTrue(board.isComplete());
		int[][] squares2 = {{1,2,1},{2,1,2},{1,2,-1}};
		board = new Board(squares2);
		assertFalse(board.isComplete());
	}
	@Test
	public void toStringTest(){
		int[][] squares1 = {{2,1,1},{1,-1,-1},{2,2,-1}};
		Board board = new Board(squares1);
		String str = "0 X X \nX _ _ \n0 0 _ \n";		
		assertEquals(str, board.toString());
	}
	@Test
	public void getNeighboursTest(){
		int[][] squares1 = {{2,1,1},{1,-1,-1},{2,2,-1}};
		Board board = new Board(squares1);
		List<Board> neighbours = board.getNeighbours(Player.Max);
		assertEquals(3, neighbours.size());
		for (int i = 0; i < neighbours.size(); i++){
			System.out.println(neighbours.get(i));
		}
	}
	@Test
	public void getUtilityTest(){
		int[][] squares1 = {{2,1,1},{1,1,2},{2,2,1}};
		Board board = new Board(squares1);
		assertEquals(0, board.getUtility());
		
		int[][] squares2 = {{2,1,1},{1,2,1},{2,2,1}};
		board = new Board(squares2);
		assertEquals(1, board.getUtility());
		
		int[][] squares3 = {{Board.ZERO,Board.CROSS,Board.CROSS},{Board.CROSS,Board.ZERO,Board.CROSS},{Board.ZERO,Board.ZERO,Board.CROSS}};
		board = new Board(squares3);
		assertEquals(1, board.getUtility());
		
		int[][] squares4 = {{Board.ZERO,Board.CROSS,Board.CROSS},{Board.CROSS,Board.CROSS,Board.ZERO},{Board.ZERO,Board.ZERO,Board.CROSS}};
		board = new Board(squares4);
		assertEquals(0, board.getUtility());
		
		int[][] squares5 = {{Board.CROSS,Board.ZERO,Board.ZERO},{Board.NAN,Board.CROSS,Board.NAN},{Board.NAN,Board.NAN,Board.CROSS}};
		board = new Board(squares5);
		assertEquals(1, board.getUtility());
		
		int[][] squares6 = {{Board.ZERO,Board.ZERO,Board.CROSS},{Board.NAN,Board.CROSS,Board.ZERO},{Board.CROSS,Board.NAN,Board.NAN}};
		board = new Board(squares6);
		assertEquals(1, board.getUtility());
	}
}
