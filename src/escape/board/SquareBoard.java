/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import escape.board.coordinate.*;

/**
 * Square grid board with SquareCoordinates
 * @version Apr 2, 2020
 */
public class SquareBoard extends GeneralBoard<SquareCoordinate>
{	
	public SquareBoard(int xMax, int yMax)
	{
		super(xMax, yMax);
	}
	
	/*
	 * @see escape.board.GeneralBoard#isOutOfBound(escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean isOutOfBound(SquareCoordinate coord)
	{
		int x = coord.getX();
		int y = coord.getY();
		
		return x <= 0 || x > getMaxX() || y <=0 || y > getMaxY();
	}
}
