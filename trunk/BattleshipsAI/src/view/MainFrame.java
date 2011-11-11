package view;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	/** the tabbed panel for the different sections. */
	private JTabbedPane mainPanel;
	
	/** the playground view. */
	private PlaygroundView playground;
	
	/** the settings view. */
	private SettingsView options;
	
	/** the statistics view. */
	private StatisticsView statistics;
	
	/**
	 * constructor.
	 * 
	 * @param playground playground view.
	 * @param options settings view.
	 * @param statistics statistics view.
	 */
	public MainFrame(PlaygroundView playground, SettingsView options, StatisticsView statistics) {
		super();
		this.mainPanel = new JTabbedPane();
		this.playground = playground;
		this.options = options;
		this.statistics = statistics;
		this.initialize();
	}
	
	/**
	 * adds panels to the tabbed pane, sets size, title, icons, etc.
	 */
	private void initialize() {
		WindowListener listener = new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				//Message after window was close
				System.exit(0);
			}
		};
		addWindowListener(listener);
		ImageIcon play = new ImageIcon(getClass().getResource("/view/img/play.png"));
		ImageIcon settings = new ImageIcon(getClass().getResource("/view/img/settings.png"));
		ImageIcon statistics = new ImageIcon(getClass().getResource("/view/img/statistics.png"));
		this.setSize(800, 600);
		this.setTitle("Battleships AI Project");
		this.mainPanel.addTab("Settings", settings, this.options, "Select the wished mode");
		this.mainPanel.addTab("Play", play, this.playground, "Play the game");
		this.mainPanel.addTab("Statistics", statistics, this.statistics, "Provides statistics about performance");
		this.add(mainPanel);
	}
}
