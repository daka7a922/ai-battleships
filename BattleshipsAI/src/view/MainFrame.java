package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private JTabbedPane mainPanel;
	private Playground playground;
	private SettingsView options;
	private Statistics statistics;
	
	public MainFrame(Playground playground, SettingsView options, Statistics statistics) {
		super();
		this.mainPanel = new JTabbedPane();
		this.playground = playground;
		this.options = options;
		this.statistics = statistics;
		this.initialize();
	}
	
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
