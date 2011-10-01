package view;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Container playgroundContainer;
	private Container playgroundContainerContainer;
	
	public MainFrame(Playground playground, SettingsView options, Statistics statistics) {
		super();
		this.mainPanel = new JTabbedPane();
		this.playground = playground;
		this.options = options;
		this.statistics = statistics;
		this.playgroundContainer = new Container();
		this.playgroundContainerContainer = new Container();
		this.initialize();
	}
	
	private void initialize() {
		ImageIcon play = new ImageIcon(System.getProperty( "user.dir" ) + "/res/play.png");
		ImageIcon settings = new ImageIcon(System.getProperty( "user.dir" ) + "/res/settings.png");
		ImageIcon statistics = new ImageIcon(System.getProperty( "user.dir" ) + "/res/statistics.png");
		this.setSize(800, 600);
		this.playgroundContainer.setLayout(new BoxLayout(this.playgroundContainer, BoxLayout.PAGE_AXIS));
		this.playgroundContainer.add(this.playground);
		this.playgroundContainerContainer.setLayout(new BorderLayout());
		this.playgroundContainerContainer.add(this.playgroundContainer, BorderLayout.CENTER);
		this.mainPanel.addTab("Settings", settings, this.options, "Select the wished mode");
		this.mainPanel.addTab("Play", play, this.playgroundContainerContainer, "Play battleships");
		this.mainPanel.addTab("Statistics", statistics, this.statistics, "Provides statistics about performance");
		this.add(mainPanel);
	}
}
