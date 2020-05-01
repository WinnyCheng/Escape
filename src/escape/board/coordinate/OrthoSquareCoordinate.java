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
 * Two Dimensional coordinate of a square grid where distance 
 *  is calculates by the shortest combination of orthogonal paths.
 * @version Apr 19, 2020
 */
public class OrthoSquareCoordinate extends Coordinate2D
{
    private OrthoSquareCoordinate(int x, int y)
    {
    	super(x, y);
    }
    
    /**
     * A static factory method to create a OrthoSquareCoordinate
	 * 
     * @param x the x value of the OrthoSquareCoordinate
     * @param y the y value of the OrthoSquareCoordinate
     * @return a new OrthoSquareCoordinate object
     */
    public static OrthoSquareCoordinate makeCoordinate(int x, int y)
    {
    	return new OrthoSquareCoordinate(x, y);
    }
    
    /*
 	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
 	 */
 	@Override
 	public int distanceTo(Coordinate c)
 	{
 		OrthoSquareCoordinate other = (OrthoSquareCoordinate)c;
 		return Math.abs(deltaX(other)) +  Math.abs(deltaY(other));
 	}
}
