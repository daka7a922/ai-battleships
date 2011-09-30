import controller.GameHandler;
import view.MainFrame;


public class Main {

	public static void main(String[] args) {
		MainFrame p = new MainFrame();
		p.setVisible(true);
		GameHandler handler = new GameHandler();
	}
}
