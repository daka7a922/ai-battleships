package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Field;

public class Playground extends JPanel implements Observer {
	
	private JButton[][] fieldButtons;
	private ImageIcon blueWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/blueWater.png");
	private ImageIcon greenWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/greenWater.png");
	private ImageIcon redWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/redWater.png");
	private ImageIcon orangeWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/orangeWater.png");
	private ImageIcon greyWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/greyWater.png");
	
	public Playground() {
		super();
		this.fieldButtons = new JButton[10][10];
		this.initialize();
	}

	private void initialize() {
		ImageIcon water = new ImageIcon(System.getProperty( "user.dir" ) + "/res/blueWater.png");
		GridLayout grid = new GridLayout(10, 10);
		this.setLayout(grid);
		this.setMaximumSize(new Dimension(400, 400));
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
				this.fieldButtons[i][j] = button;
				this.add(button);
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				int value = ((Field)arg0).getFields()[i][j];
				switch(value) {
				case 0: this.fieldButtons[i][j].setIcon(this.blueWater);
				break;
				case 1: this.fieldButtons[i][j].setIcon(this.greyWater);
				break;
				case 2: this.fieldButtons[i][j].setIcon(this.redWater);
				break;
				case 3: this.fieldButtons[i][j].setIcon(this.orangeWater);
				break;
				case 4: this.fieldButtons[i][j].setIcon(this.greenWater);
				}
			}
		}
		
	}
}
