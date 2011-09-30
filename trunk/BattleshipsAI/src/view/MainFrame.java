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
	
	private JTabbedPane mainPanel = new JTabbedPane();
	private JPanel playground = new JPanel();
	private Container playgroundContainer = new Container();
	private Container playgroundContainerContainer = new Container();
	private JPanel options = new JPanel();
	private JPanel statistics = new JPanel();
	
	public MainFrame() {
		super();
		this.initialize();
	}
	
	private void initialize() {
		this.setSize(800, 600);
		ImageIcon play = new ImageIcon(System.getProperty( "user.dir" ) + "/res/play.png");
		ImageIcon settings = new ImageIcon(System.getProperty( "user.dir" ) + "/res/settings.png");
		ImageIcon statistics = new ImageIcon(System.getProperty( "user.dir" ) + "/res/statistics.png");
		ImageIcon water = new ImageIcon(System.getProperty( "user.dir" ) + "/res/blueWater.png");
		GridLayout grid = new GridLayout(10, 10);
		this.playground.setLayout(grid);
		this.playground.setMaximumSize(new Dimension(400, 400));
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				JButton button = new JButton();
				final int a = i;
				final int b = j;
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null,"" + a + ", " + b,"Titel", JOptionPane.PLAIN_MESSAGE);
					}	
				});
				button.setIcon(water);
				this.playground.add(button);
			}
		}
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
