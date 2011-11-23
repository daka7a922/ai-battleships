package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import ai.AIAdvancedStatisticsPlayer;
import ai.AIBrutForcePlayer;
import ai.AIPlayer;
import ai.AISimpleStatisticsPlayer;
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
					settings.setPlayerClass(RandomPlayer.class); 
					break;
				case 1: 
					settings.setPlayerClass(MediumPlayer.class); 
					break;
				case 2: 
					settings.setPlayerClass(AIPlayer.class); 
					break;		
				case 3: 
					settings.setPlayerClass(AISimpleStatisticsPlayer.class); 
					break;		
				case 4: 
					settings.setPlayerClass(AIAdvancedStatisticsPlayer.class); 
					break;		
				case 5: 
					settings.setPlayerClass(AIBrutForcePlayer.class); 
					break;				
				}
				setChanged();
				notifyObservers(settings);
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
