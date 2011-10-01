package ai;

public abstract class AbstractPlayer implements IPlayer {

	public AbstractPlayer() {
		this.field = new int[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.field[i][j] = 0;
			}
		}
	}
	
	protected int[][] field;
}
