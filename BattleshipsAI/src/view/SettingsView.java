package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * the view for the settings area.
 * 
 * @author Jakob
 *
 */
@SuppressWarnings("serial")
public class SettingsView extends JPanel {

	/** the header for the number of ships selection area. */
	private JLabel numberOfShipsHeader;
	
	/** the header for the playertype selection area. */
	private JLabel playerTypeHeader;
	
	/** the label for the ship of length 5. */
	private JLabel five;
	
	/** the label for the ships of length 4. */
	private JLabel four;
	
	/** the label for the ships of the length 3. */
	private JLabel three;
	
	/** the label for the ships of length 2. */
	private JLabel two;
	
	/** the combobox for the ships of length 5. */
	private JComboBox fiveC;
	
	/** the combobox for the ships of length 4. */
	private JComboBox fourC;
	
	/** the combobox for the ships of length 3. */
	private JComboBox threeC;
	
	/** the combobox for the ships of length 2. */
	private JComboBox twoC;
	
	/** the combobox fo thr player type. */
	private JComboBox playerTypeC;
	
	/** the panel for the ship number selection. */
	private JPanel shipNumbersP;
	
	/** the panel for the player type selection. */
	private JPanel playerTypeP;
	
	/** the panel containing all subpanels. */
	private JPanel completeP;
	
	/** values for all ships that can be placed at most three times. */
	private static final String[] maxThree = {"0", "1", "2", "3"};
	
	/** values for all ships that can be placed at most two times. */
	private static final String[] maxTwo = {"0", "1", "2"};
	
	/** values for the player type combo box. */
	private static final String[] playerTypes = {"Random Player", "Medium Player", "AI Player", "Statistical Player"};
	
	/**
	 * constructor.
	 */
	public SettingsView() {
		this.initialize();
	}
	
	/**
	 * placing buttons, labels, comboboxes, images, etc.
	 */
	private void initialize() {
		this.shipNumbersP = new JPanel(new GridLayout(2, 1));
		JPanel shipNumbersSelectP = new JPanel(new GridLayout(4, 2));
		this.five = new JLabel("Aircraft carrier (5) ");
		this.four = new JLabel("Cruiser (4)");
		this.three = new JLabel("Ship (3)");
		this.two = new JLabel("Boat (2)");
		this.fiveC = new JComboBox(maxTwo);
		this.fourC = new JComboBox(maxTwo);
		this.threeC = new JComboBox(maxThree);
		this.twoC = new JComboBox(maxThree);
		shipNumbersSelectP.add(five);
		shipNumbersSelectP.add(fiveC);
		shipNumbersSelectP.add(four);
		shipNumbersSelectP.add(fourC);
		shipNumbersSelectP.add(three);
		shipNumbersSelectP.add(threeC);
		shipNumbersSelectP.add(two);
		shipNumbersSelectP.add(twoC);
		this.numberOfShipsHeader = new JLabel ("Please select the number of ships! (At least two)");
		this.shipNumbersP.add(this.numberOfShipsHeader);
		this.shipNumbersP.add(shipNumbersSelectP);
		this.playerTypeHeader = new JLabel("Select the player type!");
		this.playerTypeC = new JComboBox(playerTypes);
		this.playerTypeP = new JPanel(new GridLayout(2, 1));
		this.playerTypeP.add(this.playerTypeHeader);
		this.playerTypeP.add(this.playerTypeC);
		this.completeP = new JPanel(new GridLayout(2, 1));
		this.completeP.add(this.shipNumbersP);
		this.completeP.add(this.playerTypeP);
		this.add(this.completeP);
	}
	
	/**
	 * adds action listener to the comboboxes.
	 * 
	 * @param l the listener.
	 */
	public void addActionHandlerToComboBoxes(ActionListener l) {
		this.fiveC.addActionListener(l);
		this.fourC.addActionListener(l);
		this.threeC.addActionListener(l);
		this.twoC.addActionListener(l);
		this.playerTypeC.addActionListener(l);
	}
	
	/**
	 * getter for the selected index of the combobox for ships of length 5.
	 * 
	 * @return the selected index.
	 */
	public int getFive() {
		return this.fiveC.getSelectedIndex();
	}
	
	/**
	 * getter for the selected index of the combobox for ships of length 4.
	 * 
	 * @return the selected index.
	 */
	public int getFour() {
		return this.fourC.getSelectedIndex();
	}
	
	/**
	 * getter for the selected index of the combobox for ships of length 3.
	 * 
	 * @return the selected index.
	 */
	public int getThree() {
		return this.threeC.getSelectedIndex();
	}
	
	/**
	 * getter for the selected index of the combobox for ships of length 2.
	 * 
	 * @return the selected index.
	 */
	public int getTwo() {
		return this.twoC.getSelectedIndex();
	}
	
	/**
	 * getter for the selected index of the player type combo box.
	 * 
	 * @return the selected index.
	 */
	public int getPlayer() {
		return this.playerTypeC.getSelectedIndex();
	}
}
