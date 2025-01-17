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

package escape.piece;

import escape.board.coordinate.Coordinate;
import escape.piece.movement.*;
import escape.board.Board;

/**
 * This is a class for Pieces.
 * 
 * You may change this class except for the signature of the static factory 
 * method makePiece() and the getter methods for the name and player.
 * 
 * @version Mar 28, 2020
 */
public class EscapePiece
{
    private final PieceName name;
    private final Player player;
    private Movement rules;
    private int value;
    
    /**
     * Constructor that takes the player and piece name.
     * @param player
     * @param name
     */
    public EscapePiece(Player player, PieceName name) 
    {
    	this.player = player;
    	this.name = name;
    	this.rules = null;
    	this.value = 1;
    }
	
	/**
	 * Static factory method. This creates and returns the specified
	 * Escape piece for the current game version.
	 * 
	 * DO NOT CHANGE THE SIGNATURE.
	 * @param player the player the piece belongs to
	 * @param name the piee name
	 * @return the piece
	 */
	public static EscapePiece makePiece(Player player, PieceName name)
	{
		return new EscapePiece(player, name);
	}

	/**
	 * @return the name
	 */
	public PieceName getName()
	{
		return name;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * @return the piece value
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * @param value is the new value for the piece
	 */
	public void updateValue(int newValue)
	{
		this.value = newValue;
	}
	
	/**
	 * set movement rules for this piece
	 * @param rules
	 */
	public void setRules(Movement rules) 
	{
		this.rules = rules;
		this.value = ((MovementRules)rules).getValue();
	}
	
	/**
	 * Detemines if a given move is valid for this piece
	 * @param from is the coordinate where the piece is currently at 
	 * @param to is the coordinate where the piece would like to go
	 * @param board is the game board
	 * @return true if the move is valid, false otherwise
	 */
	public boolean canMove(Coordinate from, Coordinate to, Board board) 
	{
		return rules.abideRules(from, to, board);
	}
}
