package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Field;

/**
 * the panel that contains the game view.
 * 
 * @author Jakob
 *
 */
@SuppressWarnings("serial")
public class PlaygroundView extends JPanel implements Observer {
	
	/** the buttons that represent the field. */
	private JButton[][] fieldButtons;
	
	/** the place ships button. */
	private JButton placeShips;
	
	/** the next move button. */
	private JButton nextMove;
	
	/** the run through button. */
	private JButton runThrough;
	
	/** the run play 10 games button. */
	private JButton play10Games;
	
	/** the start automatic mode button. */
	private JButton startAutomaticMode;
	
	/** the panel that contains the field. */
	private JPanel field;
	
	/** the panel that contains the buttons. */
	private JPanel buttons;
	
	/** the container for the field. */
	private Container fieldContainer;
	
	/** the icon for blue water. */
	private ImageIcon blueWater;
	
	/** the icon for green water. */
	private ImageIcon greenWater;
	
	/**the icon for red water. */
	private ImageIcon redWater;
	
	/** the icon for orange water. */
	private ImageIcon orangeWater;
	
	/** the icon for grey water. */
	private ImageIcon greyWater;
	
	/**
	 * constructor.
	 */
	public PlaygroundView() {
		super();
		this.fieldButtons = new JButton[10][10];
		this.field = new JPanel();
		this.fieldContainer = new Container();
		this.buttons = new JPanel();
		this.initialize();
	}

	/** placing buttons, components, icons, etc. */
	private void initialize() {
		this.blueWater = new ImageIcon(getClass().getResource("/view/img/blueWater.png"));
		this.greenWater = new ImageIcon(getClass().getResource("/view/img/greenWater.png"));
		this.redWater = new ImageIcon(getClass().getResource("/view/img/redWater.png"));
		this.orangeWater = new ImageIcon(getClass().getResource("/view/img/orangeWater.png"));
		this.greyWater = new ImageIcon(getClass().getResource("/view/img/greyWater.png"));
		this.setMaximumSize(new Dimension(400, 400));
		this.fieldContainer.setLayout(new BorderLayout());
		this.setLayout(new FlowLayout());
		this.buttons.setLayout(new GridLayout(5, 1));
		GridLayout grid = new GridLayout(10, 10);
		this.field.setLayout(grid);
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				JButton button = new JButton(){
					public Dimension getPreferredSize(){
						return new Dimension(40, 40);
					}
				};
				button.setIcon(blueWater);
				this.fieldButtons[i][j] = button;
				this.field.add(button);
			}
		}
		this.placeShips = new JButton("Place ships");
		this.nextMove = new JButton("Next Move");
		this.runThrough = new JButton("Run through");
		this.play10Games = new JButton("Play 10 Games");
		this.startAutomaticMode = new JButton("Start Automatic Mode");
		this.placeShips.setEnabled(false);
		this.nextMove.setEnabled(false);
		this.runThrough.setEnabled(false);
		this.play10Games.setEnabled(false);
		this.buttons.add(this.placeShips);
		this.buttons.add(this.nextMove);
		this.buttons.add(this.runThrough);
		this.buttons.add(this.play10Games);
		this.buttons.add(this.startAutomaticMode);
		this.add(field);
		this.add(buttons);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Field field = (Field)arg0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				int value = (field).getAsArray()[i][j];
				switch(value) {
				case Field.UNKNOWN: this.fieldButtons[i][j].setIcon(this.blueWater);
				break;
				case Field.UNKNOWN_SHIP: this.fieldButtons[i][j].setIcon(this.greyWater);
				break;
				case Field.EMPTY: this.fieldButtons[i][j].setIcon(this.redWater);
				break;
				case Field.HIT: this.fieldButtons[i][j].setIcon(this.orangeWater);
				break;
				case Field.SUNK: this.fieldButtons[i][j].setIcon(this.greenWater);
				}
			}
		}	
	}
	
	/**
	 * adds listener to a specific field button.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param l the listener.
	 */
	public void addFieldButtonListener(int x, int y, ActionListener l) {
		this.fieldButtons[x][y].addActionListener(l);
	}
	
	/**
	 * adds listener to the place ships button.
	 * 
	 * @param l the listener.
	 */
	public void addPlaceShipsButtonListener(ActionListener l) {
		this.placeShips.addActionListener(l);
	}
	
	/**
	 * adds listener to the next move button.
	 * 
	 * @param l the listener.
	 */
	public void addNextMoveButtonListener(ActionListener l) {
		this.nextMove.addActionListener(l);
	}
	
	/**
	 * adds listener to the run through button.
	 * 
	 * @param l the listener.
	 */
	public void addRunThroughButtonListener(ActionListener l) {
		this.runThrough.addActionListener(l);
	}
	
	/**
	 * adds listener to the play 10 games button.
	 * 
	 * @param l the listener.
	 */
	public void addPlay10GamesButtonListener(ActionListener l) {
		this.play10Games.addActionListener(l);
	}
	
	/**
	 * adds listener to the start automatic mode button.
	 * 
	 * @param l the listener.
	 */
	public void addStartAutomaticModeButtonListener(ActionListener l) {
		this.startAutomaticMode.addActionListener(l);
	}
	
	/**
	 * set enabled for the place ships button.
	 * 
	 * @param enabled enabled value.
	 */
	public void setPlaceShipsButtonEnabled(boolean enabled) {
		this.placeShips.setEnabled(enabled);
	}
	
	/**
	 * set enabled for the next move button.
	 * 
	 * @param enabled enabled value.
	 */
	public void setNextMoveButtonEnabled(boolean enabled) {
		this.nextMove.setEnabled(enabled);
	}
	
	/**
	 * set enabled for the run through button.
	 * 
	 * @param enabled enabled value.
	 */
	public void setRunThroughButtonEnabled(boolean enabled) {
		this.runThrough.setEnabled(enabled);
	}
	
	/**
	 * set enabled for the run through button.
	 * 
	 * @param enabled enabled value.
	 */
	public void setPlay10GamesButtonEnabled(boolean enabled) {
		this.play10Games.setEnabled(enabled);
	}
}
