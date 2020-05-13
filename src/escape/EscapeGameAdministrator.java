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

package escape;

import escape.board.*;
import escape.board.coordinate.*;
import static escape.board.coordinate.CoordinateID.*;
import static escape.board.LocationType.*;
import escape.piece.EscapePiece;


/**
 * Description
 * @version May 6, 2020
 */
public class EscapeGameAdministrator implements EscapeGameManager
{
	private Board gameboard;
	private CoordinateID coordType;
	
	public EscapeGameAdministrator(Board gameboard, CoordinateID coordType)
	{
		this.gameboard = gameboard;
		this.coordType = coordType;
	}

	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{
		EscapePiece fromPiece = getPieceAt(from);
		EscapePiece toPiece = getPieceAt(to);
		
		// no piece to move or player has another piece at destination coordinate
		if(fromPiece == null || (toPiece != null && toPiece.getPlayer() == fromPiece.getPlayer())) 
			return false;
		
		if(fromPiece.canMove(from, to, gameboard))
		{
			if(((GeneralBoard)gameboard).getLocationType(to) == EXIT)
				gameboard.putPieceAt(null, to);
			else
				gameboard.putPieceAt(fromPiece, to);
			gameboard.putPieceAt(null, from);
			return true;
		}
		return false;
	}

	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coordinate)
	{
		return gameboard.getPieceAt(coordinate);
	}

	/*
	 * @see escape.EscapeGameManager#makeCoordinate(int, int)
	 */
	@Override
	public Coordinate makeCoordinate(int x, int y)
	{
		Coordinate coordinate = null;
		
		if(coordType == SQUARE) 
			coordinate = SquareCoordinate.makeCoordinate(x, y);
		else if(coordType == HEX)
			coordinate = HexCoordinate.makeCoordinate(x, y);
		else if(coordType == ORTHOSQUARE)
			coordinate = OrthoSquareCoordinate.makeCoordinate(x, y);
		
		if(coordinate == null || ((GeneralBoard)gameboard).isOutOfBound(coordinate))
			return null;
		
		return coordinate;
	}

}
