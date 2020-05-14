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
import java.util.HashMap;

/**
 * Keeps track of the state of the game
 * @version May 13, 2020
 */
public class GameState 
{
	private HashMap<Player, Integer> players;
	private Player currentPlayer;
	private int turnNum;
	private boolean gameWon;
	
	public GameState() 
	{
		this.currentPlayer = PLAYER1;
		this.turnNum = 0;
		this.players = new HashMap<Player, Integer>();
		players.put(PLAYER1, 0);
		players.put(PLAYER2, 0);
		this.gameWon = false;
	}
	
	/**
	 * @return the player that is making a move
	 */
	public Player getPlayer() 
	{
		return currentPlayer;
	}
	
	/**
	 * @return the number of turns so far in game
	 */
	public int getTurn()
	{
		return turnNum;
	}
	
	/**
	 * @param player the player
	 * @return the points of the given player
	 */
	public int getPlayerPoints(Player player)
	{
		return players.get(player);
	}
	
	/**
	 * @return true if a player wins or theres a tie, otherwise false
	 */
	public boolean isGameWon()
	{
		return gameWon;
	}
	
	/**
	 * Update turn and current player for each move made
	 */
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
	
	/**
	 * Updates player's total points for the game
	 * @param pointsGain the number of points to add
	 * @param player is the player
	 */
	public void updatePlayerPoints(int pointsGain, Player player) 
	{
		players.put(player, players.get(player) + pointsGain);
	}
	
	/**
	 * The game is over 
	 */
	public void gameHasWon() 
	{
		this.gameWon = true;
	}
	
	/**
	 * Determines who the winner of the game is
	 * @return the winner of the game, if tie or game is not over return null
	 */
	public Player winner()
	{
		if(!gameWon) //game has not ended, no winner yet
			return null;
		
		if(players.get(PLAYER1) > players.get(PLAYER2))
			return PLAYER1;
		else if(players.get(PLAYER1) < players.get(PLAYER2))
			return PLAYER2;
		return null;
	}
}
