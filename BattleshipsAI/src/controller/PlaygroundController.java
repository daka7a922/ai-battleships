package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import model.Settings;
import view.PlaygroundView;

public class PlaygroundController implements Observer {
	
	/** the view for the playground. */
	private PlaygroundView playground;
	
	private GameController gc;
	
	private SettingsController settingsController;
	private StatisticsViewController statisticsController;
	
	public PlaygroundController (PlaygroundView playground, SettingsController settingsController, StatisticsViewController statisticsController) {
		
		//his.field.addObserver(playground);

		this.playground = playground;
		this.gc = new GameController(statisticsController);
		this.gc.getField().addObserver(this.playground); 
		this.settingsController = settingsController;
		this.settingsController.addObserver(this); 
		this.statisticsController = statisticsController;
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
		this.addPlay10GamesButtonListener();
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
				displayReady(false);
				displayInProcess(false);
				gc.placeShips();
				displayReady(true);
				displayInProcess(true);
				statisticsController.output();
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
				displayReady(false);
				displayInProcess(false);
				if(gc.nextMove()) {
					displayInProcess(false);
					statisticsController.output();
				} else { 
					displayInProcess(true); 
				}
				displayReady(true);
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
				displayReady(false);
				displayInProcess(false);
				gc.runThrough();
				displayReady(true);
				displayInProcess(false);
				statisticsController.output();
			}
		});
	}
	
	/**
	 * add the listener to the play 10 games button.
	 */
	private void addPlay10GamesButtonListener() {
		this.playground.addPlay10GamesButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayReady(false);
				displayInProcess(false);
				gc.playGames(20);
				displayReady(true);
				displayInProcess(false);
				statisticsController.output();
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
				statisticsController.output();	
			}
		});	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o.getClass().equals(SettingsController.class)) {
			this.gc.reset();
			displayReady(((Settings)arg).getShipCount()>1);
			displayInProcess(false);
			if (((Settings)arg).getShipCount()>1) {
				this.gc.setSettings((Settings)arg);			
			}
		}
	}
	
	private void displayReady(Boolean ready) {
		playground.setPlaceShipsButtonEnabled(ready);
		playground.setPlay10GamesButtonEnabled(ready);
	}
	
	private void displayInProcess(Boolean inProcess) {
		playground.setNextMoveButtonEnabled(inProcess);
		playground.setRunThroughButtonEnabled(inProcess);
	}
}
