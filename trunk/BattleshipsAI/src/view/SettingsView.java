package view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsView extends JPanel {

	private JLabel numberOfShipsHeader;
	private JLabel playerTypeHeader;
	private JLabel five;
	private JLabel four;
	private JLabel three;
	private JLabel two;
	private JComboBox fiveC;
	private JComboBox fourC;
	private JComboBox threeC;
	private JComboBox twoC;
	private JComboBox playerTypeC;
	private JPanel shipNumbersP;
	private JPanel playerTypeP;
	private JPanel completeP;
	private static final String[] maxThree = {"0", "1", "2", "3"};
	private static final String[] maxTwo = {"0", "1", "2"};
	private static final String[] playerTypes = {"Random Player", "Medium Player", "AI Player"};
	
	public SettingsView() {
		this.initialize();
	}
	
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
	
	public void addActionHandlerToComboBoxes(ActionListener l) {
		this.fiveC.addActionListener(l);
		this.fourC.addActionListener(l);
		this.threeC.addActionListener(l);
		this.twoC.addActionListener(l);
		this.playerTypeC.addActionListener(l);
	}
	
	public int getFive() {
		return this.fiveC.getSelectedIndex();
	}
	
	public int getFour() {
		return this.fourC.getSelectedIndex();
	}
	
	public int getThree() {
		return this.threeC.getSelectedIndex();
	}
	
	public int getTwo() {
		return this.twoC.getSelectedIndex();
	}
	
	public int getPlayer() {
		return this.playerTypeC.getSelectedIndex();
	}
}
