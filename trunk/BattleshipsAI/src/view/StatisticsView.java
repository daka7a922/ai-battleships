package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** the view for the statistics section.
 * 
 * @author Jakob
 *
 */
@SuppressWarnings("serial")
public class StatisticsView extends JPanel {
	
	private JPanel completeP;
	private JPanel configurationNameP;
	private JPanel configurationShipNumbersP;
	private JPanel allShotsP;
	private JPanel faultsAbsoluteP;
	private JPanel faultsRelativeP;
	private JPanel timeP;

	public StatisticsView() {
		this.initialize();
	}
	
	private void initialize() {
		this.completeP = new JPanel(new GridLayout(1, 6));
		this.allShotsP = new JPanel();
		this.faultsAbsoluteP = new JPanel();
		this.faultsRelativeP = new JPanel();
		this.configurationNameP = new JPanel();
		this.configurationShipNumbersP = new JPanel();
		this.timeP = new JPanel();
		this.completeP.add(this.configurationNameP);
		this.completeP.add(this.configurationShipNumbersP);
		this.completeP.add(this.allShotsP);
		this.completeP.add(this.faultsAbsoluteP);
		this.completeP.add(this.faultsRelativeP);
		this.completeP.add(this.timeP);
		this.add(completeP);
	}
	
	public void setLabelsPanel(List<String> names, List<HashMap<Integer, Integer>> shipNumbersList) {
		DecimalFormat df = new DecimalFormat( "0.00" );
		JPanel pNames = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		pNames.add(new JLabel("Names"));
		pNames.add(new JLabel());
		pNames.add(new JLabel("Max"));
		for(int i = 0; i < names.size(); i++) {
			pNames.add(new JLabel(names.get(i)));
		}
		pNames.add(new JLabel());
		pNames.add(new JLabel("Min"));
		for(int i = 0; i < names.size(); i++) {
			pNames.add(new JLabel(names.get(i)));
		}
		pNames.add(new JLabel());
		pNames.add(new JLabel("Average"));
		for(int i = 0; i < names.size(); i++) {
			pNames.add(new JLabel(names.get(i)));
		}
		this.configurationNameP.removeAll();
		this.configurationNameP.add(pNames);
		JPanel pShipNumbers = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		pShipNumbers.add(new JLabel("Ship Numbers"));
		pShipNumbers.add(new JLabel());
		pShipNumbers.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			pShipNumbers.add(new JLabel("" + shipNumbersList.get(i)));
		}
		pShipNumbers.add(new JLabel());
		pShipNumbers.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			pShipNumbers.add(new JLabel("" + shipNumbersList.get(i)));
		}
		pShipNumbers.add(new JLabel());
		pShipNumbers.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			pShipNumbers.add(new JLabel("" + shipNumbersList.get(i)));
		}
		this.configurationShipNumbersP.removeAll();
		this.configurationShipNumbersP.add(pShipNumbers);
	}
	
	
	public void setAllShotsPanel(List<String> names, List<Integer> allShotsMax, List<Integer> allShotsMin, List<Double> allShotsAvg) {
		DecimalFormat df = new DecimalFormat( "0.00" );
		JPanel p = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		p.add(new JLabel("All Shots"));
		p.add(new JLabel());
		p.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(allShotsMax.get(i))));
		}
		p.add(new JLabel());
		p.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(allShotsMin.get(i))));
		}
		p.add(new JLabel());
		p.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(allShotsAvg.get(i))));
		}
		this.allShotsP.removeAll();
		this.allShotsP.add(p);
	}
	
	public void setAbsoluteFaultsPanel(List<String> names, List<Integer> maxAbsolute, List<Integer> minAbsolute, List<Double> avgAbsolute) {
		DecimalFormat df = new DecimalFormat( "0.00" );
		JPanel p = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		p.add(new JLabel("Absolute Faults"));
		p.add(new JLabel());
		p.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(maxAbsolute.get(i))));
		}
		p.add(new JLabel());
		p.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(minAbsolute.get(i))));
		}
		p.add(new JLabel());
		p.add(new JLabel());
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(avgAbsolute.get(i))));
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
			p.add(new JLabel(df.format(maxRelative.get(i)) + "%"));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Min"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(minRelative.get(i)) + "%"));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Average"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(avgRelative.get(i)) + "%"));
		}
		this.faultsRelativeP.removeAll();
		this.faultsRelativeP.add(p);
	}
	
	public void setTimePanel(List<String> names, List<Long> maxTime, List<Long> minTime, List<Long> avgTime) {
		JPanel p = new JPanel(new GridLayout((names.size() * 3) + 7, 1));
		DecimalFormat df = new DecimalFormat( "0" );
		p.add(new JLabel("Time"));
		p.add(new JLabel(""));
		p.add(new JLabel("Max"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(maxTime.get(i)) + "ms"));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Min"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(minTime.get(i)) + "ms"));
		}
		p.add(new JLabel(""));
		p.add(new JLabel("Average"));
		for(int i = 0; i < names.size(); i++) {
			p.add(new JLabel(df.format(avgTime.get(i)) + "ms"));
		}
		this.timeP.removeAll();
		this.timeP.add(p);
	}
}
