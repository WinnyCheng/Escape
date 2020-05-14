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
import escape.board.coordinate.SquareCoordinate;
import escape.piece.*;
import static escape.board.coordinate.SquareCoordinate.makeCoordinate;
import static escape.piece.MovementPatternID.*;
import java.util.*;

/**
 * Class describes a set of rules for a piece on a Square Board
 * @version May 4, 2020
 */
public class SquareMovementRules extends MovementRules<SquareCoordinate, SquareBoard>
{
	public SquareMovementRules(MovementPatternID pattern, int distance) 
	{
		super(pattern, distance);
	}

	/*
	 * @see escape.piece.movement.MovementRules#abideRules(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean abideRules(SquareCoordinate from, SquareCoordinate to, SquareBoard board) 
	{
		abideRulesHelper(from, to, board);
		
		if(pattern == LINEAR) 
		{
			if(from.deltaX(to) == 0) {
				for(SquareCoordinate key : map.keySet()) 
				{
					if(from.deltaX(key) != 0)
						map.put(key, null);
				}
			}
			else if(from.deltaY(to) == 0) {
				for(SquareCoordinate key : map.keySet()) 
				{
					if(from.deltaY(key) != 0)
						map.put(key, null);
				}
			}
			else if(Math.abs(from.deltaX(to)) == Math.abs(from.deltaY(to)))
			{
				for(SquareCoordinate key : map.keySet()) 
				{
					if(Math.abs(from.deltaX(key)) != Math.abs(from.deltaY(key)))
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
	protected void addVertex(int x, int y, SquareBoard board) 
	{
		SquareCoordinate coord = makeCoordinate(x, y);
		map.put(coord, new ArrayList<SquareCoordinate>());
		LocationType type = board.getLocationType(coord);
		EscapePiece piece = board.getPieceAt(coord);
		
		for(SquareCoordinate key : map.keySet()) 
		{
			if(hasPathTo(key, board, type, piece) && isDistanceOne(key, coord))
	    		addEdge(coord, key);
		}
	}
	
	/**
	 * Determines if key coordinate and coord coordinate are one distance apart
	 *  taking into accound the movement pattern
	 * @param key
	 * @param coord
	 * @return true if the distance is one taking into account the movement pattern
	 */
	private boolean isDistanceOne(SquareCoordinate key, SquareCoordinate coord)
	{
		return coord.distanceTo(key) == 1 && (pattern == OMNI || pattern == LINEAR ||
    			pattern == DIAGONAL && Math.abs(coord.deltaX(key)) == Math.abs(coord.deltaY(key))||
    			pattern == ORTHOGONAL && (coord.deltaX(key) == 0 || coord.deltaY(key) == 0));
	}

}
