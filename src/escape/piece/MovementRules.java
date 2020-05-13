/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.piece;

import escape.board.Board;
import escape.board.coordinate.Coordinate;

/**
 * Interface that contains the methods that any set of movement rules needs
 * @version May 5, 2020
 */
public interface MovementRules<C extends Coordinate, B extends Board>
{
	/**
	 * Detemines if a move follows all the appropriate attributes and movement pattern rules
	 * @param from - the starting cood on the board
	 * @param to - the ending cood on the board
	 * @return true if the move is possible, if not false 
	 */
	boolean canMove(C from, C to, B board);
}
