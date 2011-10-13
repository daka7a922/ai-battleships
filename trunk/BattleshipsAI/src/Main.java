import controller.GameHandler;
import controller.SettingsHandler;
import controller.StatisticsHandler;
import view.MainFrame;
import view.Playground;
import view.SettingsView;
import view.Statistics;

/**
 * the Main class is responsible for initializing the application
 * und plugging all the components together.
 * 
 * @author Jakob
 *
 */
public class Main {

	/**
	 * the initial method, creating the MainFrame and
	 * plugging model view and controller together.
	 * 
	 * @param args default, should be empty.
	 */
	public static void main(String[] args) {
		Playground p = new Playground();
		SettingsView s = new SettingsView();
		Statistics st = new Statistics();
		SettingsHandler settingsHandler = new SettingsHandler(s);
		StatisticsHandler statisticsHandler = new StatisticsHandler(st);
		new GameHandler(p, settingsHandler, statisticsHandler);
		MainFrame m = new MainFrame(p, s, st);
		m.setVisible(true);
	}
}
