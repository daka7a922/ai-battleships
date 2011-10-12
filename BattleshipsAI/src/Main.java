import model.Settings;
import controller.GameHandler;
import controller.SettingsHandler;
import view.MainFrame;
import view.Playground;
import view.SettingsView;
import view.Statistics;


public class Main {

	public static void main(String[] args) {
		Playground p = new Playground();
		SettingsView s = new SettingsView();
		Statistics st = new Statistics();
		SettingsHandler settingsHandler = new SettingsHandler(s);
		new GameHandler(p, settingsHandler);
		MainFrame m = new MainFrame(p, s, st);
		m.setVisible(true);
	}
}
