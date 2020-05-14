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
import java.util.ArrayList;
import static escape.board.LocationType.*;
import escape.piece.EscapePiece;
import escape.rule.*;
import static escape.rule.RuleID.*;
import static escape.piece.Player.*;


/**
 * Description
 * @version May 6, 2020
 */
public class EscapeGameAdministrator implements EscapeGameManager
{
	private Board gameboard;
	private CoordinateID coordType;
	private GameState state;
	private ArrayList<GameObserver> observers;
	private boolean remove = false, pointconflict = false;
	private int turnLimit = Integer.MAX_VALUE, score = Integer.MAX_VALUE;
	
	public EscapeGameAdministrator(Board gameboard, CoordinateID coordType, Rule[] rules)
	{
		this.gameboard = gameboard;
		this.coordType = coordType;
		this.state = new GameState();
		this.observers = new ArrayList<GameObserver>();
		if(rules != null)
		{
			for(Rule r: rules)
			{
				RuleID id = r.getId();
				if(id == TURN_LIMIT)
					turnLimit = r.getIntValue();
				else if(id == SCORE)
					score = r.getIntValue();
				else if(id == REMOVE)
					remove = true;
				else if(id == POINT_CONFLICT)
					pointconflict = true; 
			}
		}
	}

	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{	
		try 
		{
			// check if game is over
			if(state.isGameWon()) 
			{
				if(state.winner() == PLAYER1)
					notifyAllObservers("The game is already over. The winner is player 1!");
				else if(state.winner() == PLAYER2)
					notifyAllObservers("The game is already over. The winner is player 2!");
				else
					notifyAllObservers("The game is already over. It's a tie.");
				return false;
			}
			
			EscapePiece fromPiece = getPieceAt(from);
			EscapePiece toPiece = getPieceAt(to);
			
			// no piece to move
			if(fromPiece == null)
			{
				notifyAllObservers("There is no piece currently on the given coordinate.");
				return false;
			}
			
			// there is the same player's piece at destination
			if(toPiece != null && fromPiece.getPlayer() == toPiece.getPlayer())
			{
				notifyAllObservers("Cannot move piece to new coordinate. Another one of your pieces currently occupies that space.");
				return false;
			}
			
			// remove is false and there is a piece at destination
			if(toPiece != null && !remove & !pointconflict)
			{
				notifyAllObservers("Cannot move piece to new coordinate. Another piece currenlty occupies that space.");
				return false;
			}
			
			// check if it is player's turn
			if(fromPiece.getPlayer() != state.getPlayer())
			{
				notifyAllObservers("Wrong player: It is the other player's turn to make a move");
				return false;
			}
			
			if(fromPiece.canMove(from, to, gameboard))
			{
				if(toPiece != null && pointconflict)
				{
					// case of point conflict
					if(toPiece.getValue() > fromPiece.getValue()) 
					{ 
						toPiece.updateValue(toPiece.getValue() - fromPiece.getValue());
						gameboard.putPieceAt(toPiece, to);
					}
					else if(toPiece.getValue() < fromPiece.getValue())
					{
						fromPiece.updateValue(fromPiece.getValue() - toPiece.getValue());
						gameboard.putPieceAt(fromPiece, to);
					}
					else
					{ 
						//piece tie, both are removed from board
						gameboard.putPieceAt(null, to);
						gameboard.putPieceAt(null, from);
					}
				}
				else if(((GeneralBoard)gameboard).getLocationType(to) == EXIT) 
				{
					// case of exiting the board
					gameboard.putPieceAt(null, to);
					int newPoints = state.getPlayerPoints(fromPiece.getPlayer()) + fromPiece.getValue();
					state.updatePlayerPoints(newPoints, fromPiece.getPlayer());
				}
				else
					gameboard.putPieceAt(fromPiece, to);
				
				gameboard.putPieceAt(null, from);
				state.updateTurn();
				if(turnLimit <= state.getTurn() || score <= state.getPlayerPoints(fromPiece.getPlayer()))
					state.gameHasWon();
				return true;
			}
		}
		catch(Exception e)
		{
			notifyAllObservers(e.getMessage(), e);
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
	
	/*
	 * @see escape.EscapeGameManager#addObserver(escape.GameObserver)
	 */
	@Override
	public GameObserver addObserver(GameObserver observer)
	{
		observers.add(observer);
	    return observer;
	}
	
	/*
	 * @see escape.EscapeGameManager#removeObserver(escape.GameObserver)
	 */
	@Override
	public GameObserver removeObserver(GameObserver observer)
	{
		if(observers.remove(observer))
			return observer;
		return null;
	}
	
	private void notifyAllObservers(String message)
	{
		for(GameObserver obe: observers)
			obe.notify(message);
	}
	
	private void notifyAllObservers(String message, Throwable cause )
	{
		for(GameObserver obe: observers)
			obe.notify(message, cause);
	}

}
