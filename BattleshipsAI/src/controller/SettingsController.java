package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;

import ai.AIPlayer;
import ai.MediumPlayer;
import ai.RandomPlayer;

import view.SettingsView;
import model.Settings;

/**
 * the controller that handles the communication with the settings view
 * and the settings model.
 * 
 * @author Jakob
 *
 */
public class SettingsController extends Observable {
	
	/** the settings view. */
	private SettingsView settingsView;

	/** the settings (model). */
	private Settings settings;	
	
	/**
	 * constructor.
	 * 
	 * @param settingsView the settings view.
	 */
	public SettingsController(SettingsView settingsView) {
		this.settingsView = settingsView;
		this.settings = new Settings();
		this.addActionListener();
	}
	
	/**
	 * adds action listener to the components that allow user input.
	 */
	private void addActionListener() {
		this.settingsView.addActionHandlerToComboBoxes(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				settings.getShipNumbers().put(5, settingsView.getFive());
				settings.getShipNumbers().put(4, settingsView.getFour());
				settings.getShipNumbers().put(3, settingsView.getThree());
				settings.getShipNumbers().put(2, settingsView.getTwo());
				switch(settingsView.getPlayer()) {
				case 0: 
					settings.setPlayer(new RandomPlayer(settings.getShipNumbers())); 
					break;
				case 1: 
					settings.setPlayer(new MediumPlayer(settings.getShipNumbers())); 
					break;
				case 2: 
					settings.setPlayer(new AIPlayer(settings.getShipNumbers())); 
					break;				
				}
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
	
	public int getShipCount() {
		int shipCount = 0;
		HashMap<Integer, Integer> map = settings.getShipNumbers();
		for(int x : map.keySet()) {
			shipCount = shipCount + map.get(x);
		}
		return shipCount;
	}
	
}
