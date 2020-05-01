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

package escape.board.coordinate;

/**
 * Two Dimensional Coordinate of a hexagonal grids 
 * @version Apr 19, 2020
 */
public class HexCoordinate extends Coordinate2D
{
    private HexCoordinate(int x, int y)
    {
    	super(x, y);
    }
    
    /**
     * A static factory method to create a HexCoordinate
	 * 
     * @param x the x value of the HexCoordinate
     * @param y the y value of the HexCoordinate
     * @return a new HexCoordinate object
     */
    public static HexCoordinate makeCoordinate(int x, int y)
    {
    	return new HexCoordinate(x, y);
    }
    
    /*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		HexCoordinate other = (HexCoordinate)c;
		if(sameSign(getX(), getY()) && sameSign(other.getX(), other.getY()))
		{
			return Math.abs(deltaX(other)) +  Math.abs(deltaY(other));
		}
		return Math.max(Math.abs(deltaX(other)), Math.abs(deltaY(other)));
	}
	
	/**
	 * Compares two values and returns true if the two values are 
	 *  both positive, both negative, or both zeros and false otherwise.
	 * 
	 * @param x
	 * @param y
	 * @return true if both given inputs are the same sign, false otherwise
	 */
	private boolean sameSign(int x, int y)
	{
		return x < 0 && y < 0 || 
				x > 0 && y > 0 || 
				x == 0 && y == 0;
	}
}
