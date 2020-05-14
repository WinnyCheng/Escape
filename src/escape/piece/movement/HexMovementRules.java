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

import escape.board.*;
import escape.board.coordinate.HexCoordinate;
import escape.piece.*;
import static escape.board.coordinate.HexCoordinate.makeCoordinate;
import static escape.piece.MovementPatternID.*;
import java.util.*;

/**
 * Class describes a set of rules for a piece on a Hex board
 * @version May 4, 2020
 */
public class HexMovementRules extends MovementRules<HexCoordinate, HexBoard>
{
	public HexMovementRules(MovementPatternID pattern, int distance) 
	{
		super(pattern, distance);
	}

	/*
	 * @see escape.piece.movement.MovementRules#abideRules(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean abideRules(HexCoordinate from, HexCoordinate to, HexBoard board) 
	{
		abideRulesHelper(from, to, board);
		
		if(pattern == LINEAR) 
		{
			if(from.deltaX(to) == 0) {
				for(HexCoordinate key : map.keySet()) 
				{
					if(from.deltaX(key) != 0)
						map.put(key, null);
				}
			}
			else if(from.deltaY(to) == 0) {
				for(HexCoordinate key : map.keySet()) 
				{
					if(from.deltaY(key) != 0)
						map.put(key, null);
				}
			}
			else if(-1*from.deltaX(to) == from.deltaY(to))
			{
				for(HexCoordinate key : map.keySet()) 
				{
					if(-1*from.deltaX(key) != from.deltaY(key))
						map.put(key, null);
				}
			}
			else
				return false;
		}
		
		return distance >= findPath(from, to);
	}

	/*
	 * @see escape.piece.movement.MovementRules#addVertex(int, int, escape.board.GeneralBoard)
	 */
	@Override
	protected void addVertex(int x, int y, HexBoard board)
	{
		HexCoordinate coord = makeCoordinate(x, y);
		map.put(coord, new ArrayList<HexCoordinate>());
		LocationType type = board.getLocationType(coord);
		EscapePiece piece = board.getPieceAt(coord);
		
		for(HexCoordinate key : map.keySet()) 
		{
			if(hasPathTo(key, board, type, piece) && coord.distanceTo(key) == 1)
	    		addEdge(coord, key);
		}
	}
}
