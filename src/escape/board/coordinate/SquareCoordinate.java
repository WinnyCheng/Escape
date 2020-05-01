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
package escape.board.coordinate;

/**
 * Two Dimenstional coordinate of a standard squares grid 
 *  where distance is measure as shortest combination of orthogonal and diagonal.
 * @version Mar 27, 2020
 */
public class SquareCoordinate extends Coordinate2D
{   
    private SquareCoordinate(int x, int y)
    {
		super(x, y);
    }
    
    /**
     * A static factory method to create a SquareCoordinate
	 * 
     * @param x the x value of the SquareCoordinate
     * @param y the y value of the SquareCoordinate
     * @return a new SquareCoordinate object
     */
    public static SquareCoordinate makeCoordinate(int x, int y)
    {
    	return new SquareCoordinate(x, y);
    }
    
    /*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		SquareCoordinate other = (SquareCoordinate)c;
		return Math.max(Math.abs(deltaX(other)), Math.abs(deltaY(other)));
	}
}
