package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private JButton placeShips;
	private JButton nextMove;
	private JButton runThrough;
	private JPanel field;
	private JPanel buttons;
	private Container fieldContainer;
	private ImageIcon blueWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/blueWater.png");
	private ImageIcon greenWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/greenWater.png");
	private ImageIcon redWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/redWater.png");
	private ImageIcon orangeWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/orangeWater.png");
	private ImageIcon greyWater = new ImageIcon(System.getProperty( "user.dir" ) + "/res/greyWater.png");
	
	public Playground() {
		super();
		this.fieldButtons = new JButton[10][10];
		this.field = new JPanel();
		this.fieldContainer = new Container();
		this.buttons = new JPanel();
		this.initialize();
	}

	private void initialize() {
		this.setMaximumSize(new Dimension(400, 400));
		this.fieldContainer.setLayout(new BorderLayout());
		GridLayout g = new GridLayout(1, 2);
		this.setLayout(new FlowLayout());
		this.buttons.setLayout(new GridLayout(3, 1));
		ImageIcon water = new ImageIcon(System.getProperty( "user.dir" ) + "/res/blueWater.png");
		GridLayout grid = new GridLayout(10, 10);
		this.field.setLayout(grid);
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				JButton button = new JButton(){
					public Dimension getPreferredSize(){
						return new Dimension(40, 40);
					}
				};
				button.setIcon(water);
				this.fieldButtons[i][j] = button;
				this.field.add(button);
			}
		}
		this.placeShips = new JButton("Place ships");
		this.nextMove = new JButton("Next Move");
		this.runThrough = new JButton("Run through");
		this.placeShips.setEnabled(false);
		this.nextMove.setEnabled(false);
		this.runThrough.setEnabled(false);
		this.buttons.add(this.placeShips);
		this.buttons.add(this.nextMove);
		this.buttons.add(this.runThrough);
		this.add(field);
		this.add(buttons);
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
	
	public void addFieldButtonListener(int x, int y, ActionListener l) {
		this.fieldButtons[x][y].addActionListener(l);
	}
	
	public void addPlaceShipsButtonListener(ActionListener l) {
		this.placeShips.addActionListener(l);
	}
	
	public void addNextMoveButtonListener(ActionListener l) {
		this.nextMove.addActionListener(l);
	}
	
	public void addRunThroughButtonListener(ActionListener l) {
		this.runThrough.addActionListener(l);
	}
	
	public void setPlaceShipsButtonEnabled(boolean enabled) {
		this.placeShips.setEnabled(enabled);
	}
	
	public void setNextMoveButtonEnabled(boolean enabled) {
		this.nextMove.setEnabled(enabled);
	}
	
	public void setRunThroughButtonEnabled(boolean enabled) {
		this.runThrough.setEnabled(enabled);
	}
}
