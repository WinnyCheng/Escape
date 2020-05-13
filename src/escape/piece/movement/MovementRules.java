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
import escape.board.coordinate.*;
import escape.piece.*;
import java.util.*;

/**
 * Class describes a set of rules for a piece
 * @version May 4, 2020
 */
public abstract class MovementRules<C extends Coordinate2D, B extends GeneralBoard> implements Movement<C, B>
{
	protected MovementPatternID pattern;
	protected int value, distance;
	protected boolean jump, unblock, fly;
	protected HashMap<C, ArrayList<C>> map;
	
	
	public MovementRules(MovementPatternID pattern, int distance) 
	{
		this.pattern = pattern;
		this.value = 0;
		this.distance = distance;
		this.fly = false;
		this.jump = false;
		this.unblock = false;
		this.map = new HashMap<C, ArrayList<C>>();
	}
	
	/**
	 * sets the value
	 * @param value
	 */
	public void setValue(int value) 
	{
		this.value = value;
	}
	
	/**
	 * sets the distance
	 * @param value
	 */
	public void setDistance(int dis) 
	{
		this.distance = dis;
	}
	
	/**
	 * set jump to true if can jump otherwise false
	 * @param jump
	 */
	public void setJump(boolean jump) 
	{
		this.jump = jump;
	}
	
	/**
	 * set unblock to true if can unblock otherwise false
	 * @param unblock
	 */
	public void setUnblock(boolean unblock) 
	{
		this.unblock = unblock;
	}
	
	/**
	 * set fly to true if can fly otherwise false
	 * @param fly
	 */
	public void setFly(boolean fly) 
	{
		this.fly = fly;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() 
	{
		return value;
	}
	
	/*
	 * @see escape.piece.Movement#abideRules(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public abstract boolean abideRules(C from, C to, B board);
	
	/**
	 * Modified version of BFS algorithm take from source below
	 * Source: https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
	 *         https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
	 * @param from is the source
	 * @param to is the destination
	 * @return the distance of the path taken from source to destination
	 */
	protected int findPath(C from, C to) {
		HashMap<C, Integer> distance = new HashMap<C, Integer>();
        HashMap<C, Boolean> visited = new HashMap<C, Boolean>(); 
        LinkedList<C> queue = new LinkedList<C>(); 

        for(C key : map.keySet()) 
		{ 
            distance.put(key, Integer.MAX_VALUE);
            visited.put(key, false);
        } 
        
        visited.put(from, true); 
        distance.put(from, 0);
        queue.add(from); 
  
        //BFS algorithm 
        while (queue.size() != 0) 
        { 
            from = queue.poll();
            ArrayList<C> adj = map.get(from);
            if(adj != null) 
            {
	            for (int i = 0; i < adj.size(); i++) 
	            { 
	                if (visited.get(adj.get(i)) == false) 
	                { 
	                    visited.put(adj.get(i), true); 
	                    distance.put(adj.get(i), distance.get(from) + 1); 
	                    queue.add(adj.get(i)); 
	      
	                    if (adj.get(i).equals(to))
	                       return distance.get(to);
	                } 
	            } 
            }
        } 
        return Integer.MAX_VALUE; 
	}
	
	/**
	 * Translate the board into a graph with edge connections based on movement patterns
	 *  for example a SquareBoard with Orthogonal pattern would only have edges that
	 *  connect to horizontal and vertical neighbors only
	 * @param board is the game board
	 * @return a hashmap representation of the board
	 */
	protected void toGraph(C from, C to, B board)
	{
		int ymax = board.getMaxY(); 
		int xmax = board.getMaxX(); 
		int ymin = 1; 
		int xmin = 1; 
		
		if(xmax == 0)
		{
			xmax = Math.max(from.getX(), to.getX()) + 1;
			xmin = Math.min(from.getX(), to.getX()) - 1; 
		}
		if(ymax == 0)
		{
			ymax = Math.max(from.getY(), to.getY()) + 1; 
			ymin = Math.min(from.getY(), to.getY()) - 1; 
		}
		
		
		map = new HashMap<C, ArrayList<C>>();
		for(int i = xmin; i <= xmax; i++)
		{
			for(int j = ymin; j <= ymax; j++)
			{
				addVertex(i, j, board);
			}
		}
	}
	
	/**
	 * Adds an undirected edge to the map
	 * @param src
	 * @param des
	 */
	protected void addEdge(C src, C des){
		map.get(src).add(des);
		map.get(des).add(src);
	}
	
	/**
	 * Adds a new vertex and its neighbors that is currently in map
	 * @param src
	 * @param des
	 */
	protected abstract void addVertex(int x, int y, B board);

}
