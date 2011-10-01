package ai;

import java.util.Observer;

import model.Coordinate;

public interface IPlayer extends Observer {
	
	Coordinate nextMove();
}
