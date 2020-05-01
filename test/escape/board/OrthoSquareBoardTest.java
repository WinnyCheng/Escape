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

package escape.board;

import static escape.board.LocationType.*;
import static escape.board.coordinate.OrthoSquareCoordinate.makeCoordinate;
import static escape.piece.PieceName.*;
import static escape.piece.Player.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * Description
 * @version Apr 19, 2020
 */
public class OrthoSquareBoardTest
{
	private static OrthoSquareBoard board;
	
	@BeforeAll
	public static void setup()
	{
		board = new OrthoSquareBoard(8, 10);
		board.setLocationType(makeCoordinate(5, 7), BLOCK);
		board.setLocationType(makeCoordinate(8, 10), EXIT);
		board.putPieceAt(new EscapePiece(PLAYER1, SNAIL), makeCoordinate(1, 1));
	}
	
	@Test
	void getLocationTypeTest()
	{
		assertEquals(CLEAR, board.getLocationType(makeCoordinate(4, 8)));
	}
	
	@Test
	void getBlockedLocationTypeTest()
	{
		assertEquals(BLOCK, board.getLocationType(makeCoordinate(5, 7)));
	}
	
	@Test
	void getPlacedPieceTest()
	{
		assertNotNull(board.getPieceAt(makeCoordinate(1, 1)));
	}
	
	@Test
	void getPieceNullTest()
	{
		assertNull(board.getPieceAt(makeCoordinate(8, 2)));
	}
	
	@Test
	void getLocationTypeOuttaBoundryThrowsExceptionTest()
	{
		Exception exception = assertThrows(EscapeException.class, () -> {
			board.getLocationType(makeCoordinate(0, 5));
	    });
	    assertTrue(exception.getMessage().contains("Coordinate not on board."));
	}
	
	@Test
	void setLocationTypeOuttaBoundryThrowsExceptionTest()
	{
		Exception exception = assertThrows(EscapeException.class, () -> {
			board.setLocationType(makeCoordinate(1, 13), EXIT);
	    });
	    assertTrue(exception.getMessage().contains("Coordinate not on board."));
	}
	
	@Test
	void getPieceAtOuttaBoundryThrowsExceptionTest()
	{
		Exception exception = assertThrows(EscapeException.class, () -> {
			board.getPieceAt(makeCoordinate(3, -2));
	    });
	    assertTrue(exception.getMessage().contains("Coordinate not on board."));
	}
	
	@Test
	void putPieceAtOuttaBoundryThrowsExceptionTest()
	{
		Exception exception = assertThrows(EscapeException.class, () -> {
			board.putPieceAt(new EscapePiece(PLAYER2, FROG), makeCoordinate(10, 2));
	    });
	    assertTrue(exception.getMessage().contains("Coordinate not on board."));
	}
	
	@Test
	void putPieceAtBlockThrowsExceptionTest()
	{
		Exception exception = assertThrows(EscapeException.class, () -> {
			board.putPieceAt(new EscapePiece(PLAYER1, FROG), makeCoordinate(5, 7));
	    });
	    assertTrue(exception.getMessage().contains("Cannot place piece on blocked coordinate."));
	    assertNull(board.getPieceAt(makeCoordinate(5, 7)));
	}
	
	@Test
	void putPieceAtExitAndGetPieceTest()
	{
		board.putPieceAt(new EscapePiece(PLAYER2, FOX), makeCoordinate(8, 10));
		assertNull(board.getPieceAt(makeCoordinate(8, 10)));
	}
}
