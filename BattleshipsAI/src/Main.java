import model.Settings;
import controller.GameHandler;
import view.MainFrame;
import view.Playground;
import view.SettingsView;
import view.Statistics;


public class Main {

	public static void main(String[] args) {
		Playground p = new Playground();
		SettingsView s = new SettingsView();
		Statistics st = new Statistics();
		MainFrame m = new MainFrame(p, s, st);
		m.setVisible(true);
		GameHandler handler = new GameHandler(new Settings(), p);
	}
}
