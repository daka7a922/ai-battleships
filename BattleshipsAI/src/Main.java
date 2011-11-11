import controller.GameController;
import controller.GameHandler;
import controller.PlaygroundController;
import controller.SettingsController;
import controller.StatisticsViewController;
import view.MainFrame;
import view.PlaygroundView;
import view.SettingsView;
import view.StatisticsView;

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

		SettingsView set = new SettingsView();
		SettingsController setC = new SettingsController(set);
		
		StatisticsView stat = new StatisticsView();
		StatisticsViewController statC = new StatisticsViewController(stat);	
		
		PlaygroundView p = new PlaygroundView();
		PlaygroundController pc = new PlaygroundController(p, setC, statC);

		//new GameHandler(p, settingsHandler, statisticsHandler);
		
		MainFrame m = new MainFrame(p, set, stat);
		m.setVisible(true);		
	}
}
