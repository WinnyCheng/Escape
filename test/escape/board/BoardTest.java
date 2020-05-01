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

import static org.junit.jupiter.api.Assertions.*;
import static escape.piece.PieceName.*;
import static escape.piece.Player.*;
import static escape.board.LocationType.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * Description
 * @version Apr 2, 2020
 */
class BoardTest
{
	private static SquareBoard sboard;
	private static OrthoSquareBoard oboard;
	private static HexBoard hboard;
	
	@BeforeAll
	public static void setup() throws Exception
	{
		BoardBuilder bb1 = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		sboard = (SquareBoard) bb1.makeBoard();
		
		BoardBuilder bb2 = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		oboard = (OrthoSquareBoard) bb2.makeBoard();
		
		BoardBuilder bb3 = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		hboard = (HexBoard) bb3.makeBoard();
	}
	
	// Square Board Config Tests
	@Test
	void squareBoardNotNullTest()
	{
		assertNotNull(sboard);
	}
	
	@Test
	void squareBoardGetPieceTest()
	{
		EscapePiece actual = sboard.getPieceAt(SquareCoordinate.makeCoordinate(2, 2));
		assertEquals(actual.getName(), HORSE);
		assertEquals(actual.getPlayer(), PLAYER1);
	}
	
	@Test
	void squareBoardGetPieceIsNullTest()
	{
		assertNull(sboard.getPieceAt(SquareCoordinate.makeCoordinate(5, 5)));
	}
	
	@Test
	void squareBoardGetLocationTypeTest()
	{
		assertEquals(sboard.getLocationType(SquareCoordinate.makeCoordinate(3, 5)), BLOCK);
		assertEquals(sboard.getLocationType(SquareCoordinate.makeCoordinate(5, 7)), CLEAR);
		assertEquals(sboard.getLocationType(SquareCoordinate.makeCoordinate(1, 1)), CLEAR);
		assertEquals(sboard.getLocationType(SquareCoordinate.makeCoordinate(4, 5)), CLEAR);
	}
	
	@Test
	void squareBoardThrowExceptionTest()
	{		
		Exception exception = assertThrows(EscapeException.class, () -> {
			sboard.setLocationType(SquareCoordinate.makeCoordinate(1, 9), EXIT);
	    });
	    assertTrue(exception.getMessage().contains("Coordinate not on board."));
	}
	
	// OrthoSquare Board Config Tests
	@Test
	void orthoSquareBoardNotNullTest()
	{
		assertNotNull(oboard);
	}
	
	@Test
	void orthoSquareBoardGetPieceTest()
	{
		EscapePiece actual = oboard.getPieceAt(OrthoSquareCoordinate.makeCoordinate(5, 7));
		assertEquals(actual.getName(), FROG);
		assertEquals(actual.getPlayer(), PLAYER2);
	}
	
	@Test
	void orthoSquareBoardGetPieceIsNullTest()
	{
		assertNull(oboard.getPieceAt(OrthoSquareCoordinate.makeCoordinate(1, 7)));
	}
	
	@Test
	void orthoSquareBoardGetLocationTypeTest()
	{
		assertEquals(oboard.getLocationType(OrthoSquareCoordinate.makeCoordinate(3, 5)), BLOCK);
		assertEquals(oboard.getLocationType(OrthoSquareCoordinate.makeCoordinate(4, 1)), CLEAR);
		assertEquals(oboard.getLocationType(OrthoSquareCoordinate.makeCoordinate(6, 10)), CLEAR);
		assertEquals(oboard.getLocationType(OrthoSquareCoordinate.makeCoordinate(1, 1)), CLEAR);
	}
	
	@Test
	void orthoSquareBoardThrowExceptionTest()
	{
		Exception exception = assertThrows(EscapeException.class, () -> {
			oboard.setLocationType(OrthoSquareCoordinate.makeCoordinate(7, 9), EXIT);
	    });
	    assertTrue(exception.getMessage().contains("Coordinate not on board."));
	}
	
	// Hex Board Config Tests
	@Test
	void hexBoardNotNullTest()
	{
		assertNotNull(hboard);
	}
	
	@Test
	void hexBoardGetPieceTest()
	{
		EscapePiece actual = hboard.getPieceAt(HexCoordinate.makeCoordinate(-12, 15));
		assertEquals(actual.getName(), SNAIL);
		assertEquals(actual.getPlayer(), PLAYER1);
	}
	
	@Test
	void hexBoardGetPieceIsNullTest()
	{
		assertNull(hboard.getPieceAt(HexCoordinate.makeCoordinate(7, 20)));
	}
	
	@Test
	void hexBoardGetLocationTypeTest()
	{
		assertEquals(hboard.getLocationType(HexCoordinate.makeCoordinate(7, 0)), BLOCK);
		assertEquals(hboard.getLocationType(HexCoordinate.makeCoordinate(3, -19)), CLEAR);
		assertEquals(hboard.getLocationType(HexCoordinate.makeCoordinate(27, 30)), CLEAR);
		assertEquals(hboard.getLocationType(HexCoordinate.makeCoordinate(-100, 0)), CLEAR);
	}
	
	@Test
	void hexBoardThrowExceptionTest()
	{		
		Exception exception = assertThrows(EscapeException.class, () -> {
			hboard.putPieceAt(new EscapePiece(PLAYER2, FROG), HexCoordinate.makeCoordinate(7, 0));
	    });
	    assertTrue(exception.getMessage().contains("Cannot place piece on blocked coordinate."));
	}
	
	@Test
	void buildBoardNull() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig4.xml"));
		assertNull(bb.makeBoard());
	}
	
}
