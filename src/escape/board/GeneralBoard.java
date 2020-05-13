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

import java.util.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import static escape.board.LocationType.*;

/**
 * A general game board. This abstract class is used to implement boards with 
 *  different coordinate types.
 * @version Apr 2, 2020
 */
public abstract class GeneralBoard<C extends Coordinate> implements Board<C>
{
	private Map<C, LocationType> gridspaces;
	private Map<C, EscapePiece> pieces;
	
	private final int xMax, yMax;
	
	public GeneralBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<C, EscapePiece>();
		gridspaces = new HashMap<C, LocationType>();
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(C coord)
	{
		outOfBoundException(coord);
		return pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece piece, C coord)
	{
		outOfBoundException(coord);
		if(getLocationType(coord) == BLOCK && piece != null)
			throw new EscapeException("Cannot place piece on blocked coordinate.");
		if(getLocationType(coord) == EXIT)
			return;
		pieces.put(coord, piece);
	}
	
	/**
	 * sets the given coordinate of the board to the given location type
	 * 
	 * @param coord is a coordinate
	 * @param lt a location type
	 */
	public void setLocationType(C coord, LocationType lt)
	{
		outOfBoundException(coord);
		gridspaces.put(coord, lt);
	}
	
	/**
	 * Returns the location type of a given coordinate. If the type is null
	 *  then the location type is CLEAR.
	 * 
	 * @param coord is a coordinate
	 * @return the location type of the given coordinate
	 */
	public LocationType getLocationType(C coord)
	{
		outOfBoundException(coord);
		LocationType type = gridspaces.get(coord);
		if(type == null)
			return CLEAR;
		return type;
	}
	
	/**
	 * @return xMax
	 */
	public int getMaxX()
	{
		return xMax;
	}
	
	/**
	 * @return yMax
	 */
	public int getMaxY()
	{
		return yMax;
	}
	
	/**
	 * throw exception if the given coordinate is not within board boundaries
	 * 
	 * @param coord is a coordinate
	 */
	void outOfBoundException(C coord)
	{
		if(isOutOfBound(coord))
			throw new EscapeException("Coordinate not on board.");
	}
	
	/**
	 * Check if the given coordinate is outside the bounds of the board
	 * @param coord
	 * @return true if out of bound else false
	 */
	public abstract boolean isOutOfBound(C coord);
	
}
