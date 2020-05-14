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
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.piece.*;
import static escape.board.coordinate.OrthoSquareCoordinate.makeCoordinate;
import static escape.piece.MovementPatternID.*;
import static escape.board.LocationType.*;
import java.util.*;

/**
 * Class describes a set of rules for a piece on an Ortho Board
 * @version May 4, 2020
 */
public class OrthoMovementRules extends MovementRules<OrthoSquareCoordinate, OrthoSquareBoard>
{
	public OrthoMovementRules(MovementPatternID pattern, int distance) 
	{
		super(pattern, distance);
	}
		
	/*
	 * @see escape.piece.movement.MovementRules#abideRules(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean abideRules(OrthoSquareCoordinate from, OrthoSquareCoordinate to, OrthoSquareBoard board) 
	{
		// remove piece from the "from" and "to" coordinate
		// this needs to be done because of the way search algorithm and map is set up
		EscapePiece frompiece = board.getPieceAt(from);
		EscapePiece topiece = board.getPieceAt(to);
		
		board.putPieceAt(null, from);
		board.putPieceAt(null, to);
		LocationType type = board.getLocationType(to);
		if(type == EXIT)
			board.setLocationType(to, CLEAR);
		
		toGraph(from, to, board);
		
		if(type == EXIT)
			board.setLocationType(to, EXIT);
		
		// put the pieces back to thier original place
		board.putPieceAt(frompiece, from);
		board.putPieceAt(topiece, to);
		
		if(pattern == LINEAR) 
		{
			if(from.deltaX(to) == 0) {
				for(OrthoSquareCoordinate key : map.keySet()) 
				{
					if(from.deltaX(key) != 0)
						map.put(key, null);
				}
			}
			else if(from.deltaY(to) == 0) {
				for(OrthoSquareCoordinate key : map.keySet()) 
				{
					if(from.deltaY(key) != 0)
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
	protected void addVertex(int x, int y, OrthoSquareBoard board)
	{
		OrthoSquareCoordinate coord = makeCoordinate(x, y);
		map.put(coord, new ArrayList<OrthoSquareCoordinate>());
		LocationType type = board.getLocationType(coord);
		EscapePiece piece = board.getPieceAt(coord);
		
		for(OrthoSquareCoordinate key : map.keySet()) 
		{
			LocationType keytype = board.getLocationType(key);
			EscapePiece keypiece = board.getPieceAt(key);
			if((type == CLEAR && keytype == CLEAR && piece == null && keypiece == null) 
					|| (unblock && piece == null && keypiece == null) 
					|| (jump && type == CLEAR && keytype == CLEAR) 
					|| jump && unblock || fly)
			{
			    if(coord.distanceTo(key) == 1)
			    	addEdge(coord, key);
			}
		}
		
	}
}
