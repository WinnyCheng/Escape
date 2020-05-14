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

import escape.piece.Player;
import static escape.piece.Player.*;

/**
 * Keeps track of the state of the game
 * @version May 13, 2020
 */
public class GameState
{
	private Player currentPlayer;
	private int turnNum, player1Points, player2Points;
	private boolean gameWon;
	
	public GameState() 
	{
		this.currentPlayer = PLAYER1;
		this.turnNum = 0;
		this.player1Points = 0;
		this.player2Points = 0;
		this.gameWon = false;
	}
	
	public Player getPlayer()
	{
		return currentPlayer;
	}
	
	public int getTurn()
	{
		return turnNum;
	}
	
	public int getPlayerPoints(Player player)
	{
		if(player == PLAYER1)
			return player1Points;
		else
			return player2Points;
	}
	
	public boolean isGameWon()
	{
		return gameWon;
	}
	
	public void updateTurn() 
	{
		if(currentPlayer == PLAYER1)
			currentPlayer = PLAYER2;
		else 
		{
			currentPlayer = PLAYER1;
			turnNum++;
		}
	}
	
	public void updatePlayerPoints(int points, Player player) 
	{
		if(player == PLAYER1)
			this.player1Points = points;
		else
			this.player2Points = points;
	}
	
	public void gameHasWon() 
	{
		this.gameWon = true;
	}
	
	public Player winner()
	{
		if(!gameWon) //game has not ended, no winner yet
			return null;
		
		if(player1Points > player2Points)
			return PLAYER1;
		else if(player1Points < player2Points)
			return PLAYER2;
		return null;
	}
}
