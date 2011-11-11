package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import model.Field;
import model.Settings;
import model.Ship;

import view.PlaygroundView;

public class PlaygroundController implements Observer {
	
	/** the view for the playground. */
	private PlaygroundView playground;
	
	private GameController gc;
	
	private SettingsController settingsController;
	
	public PlaygroundController (PlaygroundView playground, SettingsController settingsController, StatisticsViewController statisticsController) {

		//this.settingsHandler = settingsHandler;
		//this.statisticsHandler = statisticsHandler;
		
		//his.field.addObserver(playground);

		this.playground = playground;
		this.gc = new GameController(statisticsController);
		this.gc.getField().addObserver(this.playground); 
		this.settingsController = settingsController;
		this.settingsController.addObserver(this); 
		this.addActionListener();
	}
	/**
	 * adds the action listeners to the buttons of the playground view.
	 */
	private void addActionListener() {
		this.addFieldButtonListener();
		this.addPlaceShipsButtonListener();
		this.addNextMoveButtonListener();
		this.addRunThroughButtonListener();
		this.addStartAutomaticModeButtonListener();
	}

	/**
	 * adds the controller to the field buttons.
	 *
	 */
	//TODO remove!?
	private void addFieldButtonListener() {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				final int a = x;
				final int b = y;
				this.playground.addFieldButtonListener(x, y, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null,"Clicked on: " + a + ", " + b,"Titel", JOptionPane.PLAIN_MESSAGE);
					}
					
				});
			}
		}
	}
	
	/**
	 * add the listener to the place ships button.
	 */
	private void addPlaceShipsButtonListener() {
		this.playground.addPlaceShipsButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playground.setNextMoveButtonEnabled(true);
				playground.setRunThroughButtonEnabled(true);
				gc.placeShips();
			}
		});
	}
	
	/**
	 * add the listener to the next move button.
	 */
	private void addNextMoveButtonListener() {
		this.playground.addNextMoveButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gc.nextMove()) {
					playground.setNextMoveButtonEnabled(false);
					playground.setRunThroughButtonEnabled(false);
				}
			}
		});
	}
	
	/**
	 * add the listener to the run through button.
	 */
	private void addRunThroughButtonListener() {
		this.playground.addRunThroughButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playground.setNextMoveButtonEnabled(false);
				playground.setRunThroughButtonEnabled(false);
				gc.runThrough();
			}
		});
	}

	/**
	 * add the listener to the start automatic mode button.
	 */
	private void addStartAutomaticModeButtonListener() {
		this.playground.addStartAutomaticModeButtonListener(new ActionListener() {		
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start automatic Mode");
				playground.setNextMoveButtonEnabled(false);
				playground.setRunThroughButtonEnabled(false);
				playground.setPlaceShipsButtonEnabled(false);
				//
				playground.setPlaceShipsButtonEnabled(true);
				playground.setNextMoveButtonEnabled(false);
				playground.setRunThroughButtonEnabled(false);		
			}
		});	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o.getClass().equals(SettingsController.class)) {
			this.gc.reset();
			if(((SettingsController)o).getShipCount()>1) {
				this.playground.setPlaceShipsButtonEnabled(true);
			} else {
				this.playground.setPlaceShipsButtonEnabled(false);
			}
			this.gc.setSettings(((SettingsController)o).getSettings());		
		}
	}
	
}
