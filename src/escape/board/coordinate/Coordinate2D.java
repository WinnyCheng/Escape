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

import java.util.Objects;

/**
 * Two Dimensional Coordinate with x, y axis. This abstract class is use to implement 
 *  different types of two dimensional coordinates
 * @version Apr 20, 2020
 */
public abstract class Coordinate2D implements Coordinate
{
	 private final int x;
     private final int y;
     
     protected Coordinate2D(int x, int y)
     {
    	 this.x = x;
    	 this.y = y;
     }
          
     /*
 	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
 	 */
 	@Override
 	abstract public int distanceTo(Coordinate c);
 	
 	/**
	 * Calculates the difference in x values of this Coordinate2D 
	 *  and the given Coordinate2D
	 * 
	 * @param other is a Coordinate2D
	 * @return the change in x value
	 */
	public int deltaX(Coordinate2D other)
	{
		return other.getX() - x;
	}
	
	/**
	 * Calculates the difference in y values of this Coordinate2D 
	 *  and the given Coordinate2D
	 * 
	 * @param other is Coordinate2D
	 * @return the change in y value
	 */
	public int deltaY(Coordinate2D other)
	{
		return other.getY() - y;
	}
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coordinate2D)) {
			return false;
		}
		Coordinate2D other = (Coordinate2D) obj;
		return x == other.x && y == other.y;
	}
}
