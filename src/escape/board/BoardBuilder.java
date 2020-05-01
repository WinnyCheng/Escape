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

import static escape.board.LocationType.*;
import static escape.board.coordinate.CoordinateID.*;
import java.io.*;
import javax.xml.bind.*;
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * A Builder class for creating Boards. It is only an example and builds
 * just Square boards. If you choose to use this
 * @version Apr 2, 2020
 */
public class BoardBuilder
{
	private BoardInitializer bi;
	/**
	 * The constructor for this takes a file name. It is either an absolute path
	 * or a path relative to the beginning of this project.
	 * @param fileName
	 * @throws Exception 
	 */
	public BoardBuilder(File fileName) throws Exception
	{
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        bi = (BoardInitializer)mub.unmarshal(new FileReader(fileName));
	}
	
	public Board makeBoard()
	{
		GeneralBoard board = null; 
		
		if(bi.getCoordinateId() == SQUARE) 
	        board = new SquareBoard(bi.getxMax(), bi.getyMax());
		else if(bi.getCoordinateId() == ORTHOSQUARE) 
			board = new OrthoSquareBoard(bi.getxMax(), bi.getyMax());
		else if(bi.getCoordinateId() == HEX)
			board = new HexBoard(bi.getxMax(), bi.getyMax());
		
		if(board != null && bi.getLocationInitializers() != null)
			initializeBoard(board, bi.getLocationInitializers());
        return board;
	}
	
	private void initializeBoard(GeneralBoard b, LocationInitializer... initializers)
	{
		for (LocationInitializer li : initializers) {
			
			Coordinate c = null;
			if(bi.getCoordinateId() == SQUARE) 
		        c = SquareCoordinate.makeCoordinate(li.x, li.y);
			else if(bi.getCoordinateId() == ORTHOSQUARE) 
				c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
			else if(bi.getCoordinateId() == HEX)
				c = HexCoordinate.makeCoordinate(li.x, li.y);
			
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
}
