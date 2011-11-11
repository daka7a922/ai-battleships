package model;

import java.util.HashMap;

import ai.IPlayer;

import model.StatisticSet;

public class StatisticSetKey {
			private Class<? extends IPlayer> playerClass;
			private HashMap<Integer, Integer> shipNumbers;
			
			public StatisticSetKey (Class<? extends IPlayer> playerClass, HashMap<Integer, Integer> shipNumbers) {
				this.playerClass = playerClass;
				this.shipNumbers = shipNumbers;
			}
			
			public StatisticSetKey(Settings settings) {
				this(settings.getPlayer().getClass(), settings.getShipNumbers());
			}
			
			public HashMap<Integer, Integer> getShipNumbers() {
				return this.shipNumbers;
			}
			
			public Class<? extends IPlayer> getPlayerClass() {
				return this.playerClass;
			}
			
			@Override
			public boolean equals(Object o) {
				if (playerClass.equals(((StatisticSetKey)o).playerClass) && shipNumbers.equals(((StatisticSetKey)o).shipNumbers)) {
					return true;
				}
				return false;
			}
			
			@Override
		    public int hashCode() {
		        return (playerClass + this.shipNumbers.toString()).hashCode();
		    }
			
			@Override
			public String toString() {
				return "{Player=" + this.playerClass + ", ShipNumbers="+ this.shipNumbers + "}";
			}
		}