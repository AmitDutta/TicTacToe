public class Square {
	private int i;
	private int j;
	private int val;
	public Square(int i, int j){
		this.setI(i);
		this.setJ(j);
	}
	public String toString(){
		return "" + getVal() + "";
	}
	private int getI() {
		return i;
	}
	private void setI(int i) {
		this.i = i;
	}
	private int getJ() {
		return j;
	}
	private void setJ(int j) {
		this.j = j;
	}
	private int getVal() {
		return val;
	}
	private void setVal(int val) {
		this.val = val;
	}
}
