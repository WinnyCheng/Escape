/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;

import static escape.board.LocationType.CLEAR;
import static escape.board.coordinate.CoordinateID.*;
import static escape.piece.PieceAttributeID.*;
import static escape.rule.RuleID.*;
import java.io.*;
import javax.xml.bind.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.movement.*;
import escape.rule.Rule;
import escape.util.*;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * This class is what a client will use to creat an instance of a game, given
 * an Escape game configuration file. The configuration file contains the 
 * information needed to create an instance of the Escape game.
 * @version Apr 22, 2020
 */
public class EscapeGameBuilder
{
    private EscapeGameInitializer gameInitializer;
    
    /**
     * The constructor takes a file that points to the Escape game
     * configuration file. It should get the necessary information 
     * to be ready to create the game manager specified by the configuration
     * file and other configuration files that it links to.
     * @param fileName the file for the Escape game configuration file.
     * @throws Exception 
     */
    public EscapeGameBuilder(File fileName) throws Exception
    {
        JAXBContext contextObj = JAXBContext.newInstance(EscapeGameInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        gameInitializer = 
            (EscapeGameInitializer)mub.unmarshal(new FileReader(fileName));
    }
    
    /**
     * Once the builder is constructed, this method creates the
     * EscapeGameManager instance.
     * @return
     */
    public EscapeGameManager makeGameManager()
    {
    	if(!validGameConfiguration())
    		throw new EscapeException("Configuration of Game is not valid.");
    	
    	GeneralBoard board = null; 
    	CoordinateID type = gameInitializer.getCoordinateType();
    	int xmax = gameInitializer.getxMax();
    	int ymax = gameInitializer.getyMax();
		
		if(type == SQUARE) 
	        board = new SquareBoard(xmax, ymax);
		else if(type == ORTHOSQUARE) 
			board = new OrthoSquareBoard(xmax, ymax);
		else if(type == HEX)
			board = new HexBoard(xmax, ymax);
		
		if(board != null && gameInitializer.getLocationInitializers() != null)
			initializeBoard(board, gameInitializer.getLocationInitializers());
    	
        return new EscapeGameAdministrator(board, type, gameInitializer.getRules());
    }
    
    /**
     * Initalizes all the location types and all the pieces on the game board
     * @param b
     * @param initializers
     */
    private void initializeBoard(GeneralBoard b, LocationInitializer... initializers)
	{
    	CoordinateID type = gameInitializer.getCoordinateType();
		for (LocationInitializer li : initializers) {
			Coordinate c = null;
			if(type == SQUARE) 
		        c = SquareCoordinate.makeCoordinate(li.x, li.y);
			else if(type == ORTHOSQUARE) 
				c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
			else if(type == HEX)
				c = HexCoordinate.makeCoordinate(li.x, li.y);
			
			if (li.pieceName != null) 
			{
				EscapePiece piece = new EscapePiece(li.player, li.pieceName);
				piece.setRules(initializeMovement(piece, gameInitializer.getPieceTypes()));
				b.putPieceAt(piece, c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) 
			{
				b.setLocationType(c, li.locationType);
			}
		}
	}
    
    /**
     * Creates the appropriate MovementRules object for the given piece and initialize all its attributes
     * @param piece
     * @param initializers are the PieceTypeInitializers
     * @return movement rules for given piece
     */
    private MovementRules initializeMovement(EscapePiece piece, PieceTypeInitializer... initializers)
  	{
    	MovementRules rules = null;
    	CoordinateID type = gameInitializer.getCoordinateType();
    	int distance = 0;
		
  		for (PieceTypeInitializer li : initializers) 
  		{
  			if(li.getPieceName() == piece.getName())
  			{  				
  				if(type == SQUARE) 
  					rules = new SquareMovementRules(li.getMovementPattern(), distance);
  				else if(type == ORTHOSQUARE) 
  					rules = new OrthoMovementRules(li.getMovementPattern(), distance);
  				else if(type == HEX)
  					rules = new HexMovementRules(li.getMovementPattern(), distance);
  				
  				for(PieceAttribute atr : li.getAttributes())
  				{
  					if(atr.getId() == DISTANCE)
  						rules.setDistance(atr.getIntValue());
  					else if(atr.getId() == FLY)
  					{
  						rules.setDistance(atr.getIntValue());
  						rules.setFly(true);
  					}
  					else if(atr.getId() == JUMP)
  						rules.setJump(atr.isBooleanValue());
  					else if(atr.getId() == UNBLOCK)
  						rules.setUnblock(atr.isBooleanValue());
  					else if(atr.getId() == VALUE)
  						rules.setValue(atr.getIntValue());
  				}
  				
  				break;
  			}
  		}
  		
  		return rules;
  	}
    
    /**
     * Checks if the configuration file is valid
     * @return true for valid configuration, otherwise false
     */
    public boolean validGameConfiguration()
    {   	
    	//Check at least 1 pieceType
    	if(gameInitializer.getPieceTypes() == null)
    		return false;
    	
    	for (PieceTypeInitializer li : gameInitializer.getPieceTypes()) 
  		{	
    		//Check pieceName and movementPattern exist
	       	if(li.getMovementPattern() == null || li.getPieceName() == null)
    	       	return false;	
    		
    		boolean distanceExist = false;
    		boolean flyExist = false;
    		
    		//Check for Distance and Fly are mutually exculsive
			for(PieceAttribute atr : li.getAttributes())
			{
				if(atr.getId() == DISTANCE)
					distanceExist = true;
				else if(atr.getId() == FLY)
					flyExist = true;
			}
			
			if(distanceExist == flyExist)
				return false;
  		}
    	
    	if(gameInitializer.getRules() != null) 
    	{
	    	// Check rules REMOVE and POINT_CONFLICT are mutually exclusive
	    	boolean remove = false;
			boolean pointConflict = false;
	    	for(Rule r: gameInitializer.getRules())
	    	{
				if(r.getId() == REMOVE)
					remove = true;
				else if(r.getId() == POINT_CONFLICT)
					pointConflict = true;
	    	}
	    	if(remove && pointConflict)
				return false;
    	}
    	
    	return true;
    }
}
