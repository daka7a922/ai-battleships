package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** the view for the statistics section.
 * 
 * @author Jakob
 *
 */
@SuppressWarnings("serial")
public class Statistics extends JPanel {
	
	private JPanel completeP;
	private JPanel allShotsP;
	private JPanel faultsAbsoluteP;
	private JPanel faultsRelativeP;

	public Statistics() {
		this.initialize();
	}
	
	private void initialize() {
		this.completeP = new JPanel(new GridLayout(1, 3));
		this.allShotsP = new JPanel();
		this.faultsAbsoluteP = new JPanel();
		this.faultsRelativeP = new JPanel();
		this.completeP.add(this.allShotsP);
		this.completeP.add(this.faultsAbsoluteP);
		this.completeP.add(this.faultsRelativeP);
		this.add(completeP);
	}
	
	public void setAllShotsPanel(List<String> names, List<Integer> allShotsMax, List<Integer> allShotsMin, List<Double> allShotsAvg) {
		DecimalFormat df = new DecimalFormat( "0.00" );
		JPanel p = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		JLabel l1 = new JLabel("All Shots");
		p.add(l1);
		p.add(new JLabel(""));
		p.add(new JLabel("Max"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + allShotsMax.get(i)));
		}
		p.add(new JLabel());
		p.add(new JLabel("Min"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + allShotsMin.get(i)));
		}
		p.add(new JLabel());
		p.add(new JLabel("Average"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + df.format(allShotsAvg.get(i))));
		}
		this.allShotsP.removeAll();
		this.allShotsP.add(p);
	}
	
	public void setAbsoluteFaultsPanel(List<String> names, List<Integer> maxAbsolute, List<Integer> minAbsolute, List<Double> avgAbsolute) {
		DecimalFormat df = new DecimalFormat( "0.00" );
		JPanel p = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		p.add(new JLabel("Absolute Faults"));
		p.add(new JLabel(""));
		p.add(new JLabel("Max"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + maxAbsolute.get(i)));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Min"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + minAbsolute.get(i)));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Average"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + df.format(avgAbsolute.get(i))));
		}
		
		this.faultsAbsoluteP.removeAll();
		this.faultsAbsoluteP.add(p);
	}
	
	public void setRelativeFaultsPanel(List<String> names, List<Double> maxRelative, List<Double> minRelative, List<Double> avgRelative) {
		JPanel p = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		DecimalFormat df = new DecimalFormat( "0.00" );
		p.add(new JLabel("Relative Faults"));
		p.add(new JLabel(""));
		p.add(new JLabel("Max"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + df.format(maxRelative.get(i)) + "%"));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Min"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + df.format(minRelative.get(i)) + "%"));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Average"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(names.get(i) + ": " + df.format(avgRelative.get(i)) + "%"));
		}
		this.faultsRelativeP.removeAll();
		this.faultsRelativeP.add(p);
	}
}
