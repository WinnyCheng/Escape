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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import escape.board.*;
import escape.piece.EscapePiece;
import escape.piece.movement.*;
import escape.board.coordinate.*;
import static escape.board.coordinate.CoordinateID.*;
import static escape.piece.Player.*;
import static escape.piece.PieceName.*;
import static escape.piece.MovementPatternID.*;
import static escape.board.LocationType.*;

/**
 * Description
 * @version May 6, 2020
 */
public class EscapeGameManagerSquareTest
{
	
	private static SquareBoard board;
	private static EscapeGameManager manager;
	
	@BeforeEach
	void setup() throws Exception
	{
		board = new SquareBoard(3, 5);	
		EscapePiece piece1 = new EscapePiece(PLAYER1, HORSE);
		EscapePiece piece2 = new EscapePiece(PLAYER1, SNAIL);
		EscapePiece piece3 = new EscapePiece(PLAYER2, HORSE);
		
		SquareMovementRules rules1 = new SquareMovementRules(ORTHOGONAL, 3);
		rules1.setUnblock(true);
		
		SquareMovementRules rules2 = new SquareMovementRules(OMNI, 2);
		
		piece1.setRules(rules1);
		piece2.setRules(rules2);
		piece3.setRules(rules1);
		
		board.putPieceAt(piece1, SquareCoordinate.makeCoordinate(1, 1));
		board.putPieceAt(piece2, SquareCoordinate.makeCoordinate(2, 3));
		board.putPieceAt(piece3, SquareCoordinate.makeCoordinate(2, 1));
		board.setLocationType(SquareCoordinate.makeCoordinate(2, 2), BLOCK);
		board.setLocationType(SquareCoordinate.makeCoordinate(2, 5), EXIT);
		
		manager = new EscapeGameAdministrator(board, SQUARE, null);
	}
	
	@Test
	void testMoveNoPieceAtFrom()
	{
		assertFalse(manager.move(SquareCoordinate.makeCoordinate(1, 2), SquareCoordinate.makeCoordinate(2, 4)));
		assertNull(manager.getPieceAt(SquareCoordinate.makeCoordinate(1, 2)));
		assertNull(manager.getPieceAt(SquareCoordinate.makeCoordinate(2, 4)));
	}
	
	@Test
	void testMove_PieceExistAtTo()
	{
		assertFalse(manager.move(SquareCoordinate.makeCoordinate(1, 1), SquareCoordinate.makeCoordinate(2, 1)));
		assertEquals(HORSE, manager.getPieceAt(SquareCoordinate.makeCoordinate(1, 1)).getName());
		assertEquals(PLAYER1, manager.getPieceAt(SquareCoordinate.makeCoordinate(1, 1)).getPlayer());
		assertEquals(HORSE, manager.getPieceAt(SquareCoordinate.makeCoordinate(2, 1)).getName());
		assertEquals(PLAYER2, manager.getPieceAt(SquareCoordinate.makeCoordinate(2, 1)).getPlayer());
	}
	
	@Test
	void testExit()
	{
		assertTrue(manager.move(SquareCoordinate.makeCoordinate(2, 3), SquareCoordinate.makeCoordinate(2, 5)));
		assertNull(manager.getPieceAt(SquareCoordinate.makeCoordinate(2, 3)));	
		assertNull(manager.getPieceAt(SquareCoordinate.makeCoordinate(2, 5)));	
	}
	
	@Test
	void testMove()
	{		
		assertTrue(manager.move(SquareCoordinate.makeCoordinate(1, 1), SquareCoordinate.makeCoordinate(3, 2)));
		assertNull(manager.getPieceAt(SquareCoordinate.makeCoordinate(1, 1)));	
		assertEquals(HORSE, manager.getPieceAt(SquareCoordinate.makeCoordinate(3, 2)).getName());
		assertEquals(PLAYER1, manager.getPieceAt(SquareCoordinate.makeCoordinate(3, 2)).getPlayer());
	}

	@Test
	void testMakeCoordinate()
	{
		assertTrue(SquareCoordinate.makeCoordinate(2, 1).equals(manager.makeCoordinate(2, 1)));
		assertNull(manager.makeCoordinate(10, 10));
	}
}
