package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;

import view.SettingsView;
import model.Settings;

public class SettingsHandler extends Observable {

	private Settings settings;
	private SettingsView settingsView;
	
	public SettingsHandler(SettingsView settingsView) {
		this.settings = new Settings();
		this.settingsView = settingsView;
		this.addActionListener();
	}
	
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
	
	public Settings getSettings() {
		return this.settings;
	}
}
