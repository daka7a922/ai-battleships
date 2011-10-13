package ai;

import java.util.Observer;

import model.Coordinate;

/**
 * A simple interface that specifies that every player
 * needs to implement the method nextMove that returns
 * the Coordinate for the next attack, that was somehow
 * calculated.
 * 
 * @author Jakob
 *
 */
public interface IPlayer extends Observer {
	
	/**
	 * computes the next move.
	 * 
	 * @return the coordinate object that contains the x and y value.
	 */
	Coordinate nextMove();
}
