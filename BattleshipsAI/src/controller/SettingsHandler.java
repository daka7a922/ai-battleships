package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;

import view.SettingsView;
import model.Settings;

/**
 * the controller that handles the communication with the settings view
 * and the settings model.
 * 
 * @author Jakob
 *
 */
public class SettingsHandler extends Observable {

	/** the settings (model). */
	private Settings settings;
	
	/** the settings view. */
	private SettingsView settingsView;
	
	/**
	 * constructor.
	 * 
	 * @param settingsView the settings view.
	 */
	public SettingsHandler(SettingsView settingsView) {
		this.settings = new Settings();
		this.settingsView = settingsView;
		this.addActionListener();
	}
	
	/**
	 * adds action listener to the components that allow user input.
	 */
	private void addActionListener() {
		this.settingsView.addActionHandlerToComboBoxes(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				map.put(5, settingsView.getFive());
				map.put(4, settingsView.getFour());
				map.put(3, settingsView.getThree());
				map.put(2, settingsView.getTwo());
				settings.setShipNumbers(map);
				settings.setPlayer(settingsView.getPlayer());
				setChanged();
				notifyObservers();
				clearChanged();
			}
		});
	}
	
	/**
	 * getter for the settings.
	 * 
	 * @return settings.
	 */
	public Settings getSettings() {
		return this.settings;
	}
}
