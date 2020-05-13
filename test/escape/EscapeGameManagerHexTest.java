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
public class EscapeGameManagerHexTest
{
	
	private static HexBoard board;
	private static EscapeGameManager manager;
	
	@BeforeEach
	public void setup() throws Exception
	{
		board = new HexBoard(0, 0);	
		EscapePiece piece1 = new EscapePiece(PLAYER1, HORSE);
		EscapePiece piece2 = new EscapePiece(PLAYER1, SNAIL);
		EscapePiece piece3 = new EscapePiece(PLAYER2, HORSE);
		
		HexMovementRules rules1 = new HexMovementRules(LINEAR, 3);
		rules1.setJump(true);
		
		HexMovementRules rules2 = new HexMovementRules(OMNI, 1);
		
		piece1.setRules(rules1);
		piece2.setRules(rules2);
		piece3.setRules(rules1);
		
		board.putPieceAt(piece1, HexCoordinate.makeCoordinate(1, 0));
		board.putPieceAt(piece2, HexCoordinate.makeCoordinate(2, -2));
		board.putPieceAt(piece3, HexCoordinate.makeCoordinate(-1, 1));
		board.setLocationType(HexCoordinate.makeCoordinate(-1, 0), BLOCK);
		board.setLocationType(HexCoordinate.makeCoordinate(1, 1), EXIT);
		
		manager = new EscapeGameAdministrator(board, HEX);
	}
	
	@Test
	void testMoveNoPieceAtFrom()
	{
		assertFalse(manager.move(HexCoordinate.makeCoordinate(-1, -1), HexCoordinate.makeCoordinate(0, 1)));
		assertNull(manager.getPieceAt(HexCoordinate.makeCoordinate(-1, -1)));
		assertNull(manager.getPieceAt(HexCoordinate.makeCoordinate(0, 1)));
	}
	
	@Test
	void testMoveSamePlayerPieceAtTo()
	{
		assertFalse(manager.move(HexCoordinate.makeCoordinate(1, 0), HexCoordinate.makeCoordinate(2, -2)));
		assertEquals(HORSE, manager.getPieceAt(HexCoordinate.makeCoordinate(1, 0)).getName());
		assertEquals(PLAYER1, manager.getPieceAt(HexCoordinate.makeCoordinate(1, 0)).getPlayer());
		assertEquals(SNAIL, manager.getPieceAt(HexCoordinate.makeCoordinate(2, -2)).getName());
		assertEquals(PLAYER1, manager.getPieceAt(HexCoordinate.makeCoordinate(2, -2)).getPlayer());
	}
	
	@Test
	void testMoveCaptureOtherPlayer()
	{
		assertTrue(manager.move(HexCoordinate.makeCoordinate(-1, 1), HexCoordinate.makeCoordinate(2, -2)));
		assertEquals(HORSE, manager.getPieceAt(HexCoordinate.makeCoordinate(2, -2)).getName());
		assertEquals(PLAYER2, manager.getPieceAt(HexCoordinate.makeCoordinate(2, -2)).getPlayer());
		assertNull(manager.getPieceAt(HexCoordinate.makeCoordinate(-1, 1)));
		
	}
	
	@Test
	void testMoveExit()
	{
		assertTrue(manager.move(HexCoordinate.makeCoordinate(1, 0), HexCoordinate.makeCoordinate(1, 1)));
		assertNull(manager.getPieceAt(HexCoordinate.makeCoordinate(1, 1)));
		assertNull(manager.getPieceAt(HexCoordinate.makeCoordinate(1, 0)));	
	}

	@Test
	void testMakeCoordinate()
	{
		assertTrue(HexCoordinate.makeCoordinate(2, 1).equals(manager.makeCoordinate(2, 1)));
	}
}
