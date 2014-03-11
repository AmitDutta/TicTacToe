public class Action {
	private Board board;
	private int score;
	public Action(Board board, int score){
		this.board = board;
		this.score = score;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
