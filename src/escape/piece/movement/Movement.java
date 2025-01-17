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

package escape.piece.movement;

import escape.board.coordinate.Coordinate;
import escape.board.Board;

/**
 * Interface that defines the methods that any movement instance must apply.
 * @version May 4, 2020
 */
public interface Movement<C extends Coordinate, B extends Board>
{
	/**
	 * Detemines if a move follows all the movement pattern and attributes
	 * @param from starting location
	 * @param to ending location
	 * @return true if the move was abide all the rules, false otherwise
	 */
	boolean abideRules(C from, C to, B board);
}
