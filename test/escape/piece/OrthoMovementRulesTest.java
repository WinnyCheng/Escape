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

package escape.piece;

import static org.junit.jupiter.api.Assertions.*;
import static escape.board.LocationType.*;
import static escape.board.coordinate.OrthoSquareCoordinate.makeCoordinate;
import static escape.piece.PieceName.*;
import static escape.piece.Player.*;
import static escape.piece.MovementPatternID.*;
import org.junit.jupiter.api.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.movement.OrthoMovementRules;

/**
 * Description
 * @version May 5, 2020
 */
public class OrthoMovementRulesTest
{
	private static OrthoSquareBoard board;
	
	private static OrthoSquareCoordinate c1_1, c1_2, c1_3, c1_4, 
										c2_1, c2_2, c2_3, c2_4,
										c3_1, c3_2, c3_3, c3_4;
	
	@BeforeAll
	public static void setup() throws Exception
	{			
		c1_1 = makeCoordinate(1, 1);
		c1_2 = makeCoordinate(1, 2);
		c1_3 = makeCoordinate(1, 3);
		c1_4 = makeCoordinate(1, 4);
		c2_1 = makeCoordinate(2, 1);
		c2_2 = makeCoordinate(2, 2);
		c2_3 = makeCoordinate(2, 3);
		c2_4 = makeCoordinate(2, 4);
		c3_1 = makeCoordinate(3, 1);
		c3_2 = makeCoordinate(3, 2);
		c3_3 = makeCoordinate(3, 3);
		c3_4 = makeCoordinate(3, 4);
		
		board = new OrthoSquareBoard(3, 4);
		board.setLocationType(c1_2, BLOCK);
		board.setLocationType(c1_3, BLOCK);
		board.putPieceAt(new EscapePiece(PLAYER1, SNAIL), c3_1);
	}
	
	@Test
	void testLinearTrue() 
	{
		OrthoMovementRules rules = new OrthoMovementRules(LINEAR, 4);
		assertTrue(rules.abideRules(c2_1, c2_4, board));
		assertTrue(rules.abideRules(c1_4, c3_4, board));
	}
	
	@Test
	void testLinearFalse() 
	{
		OrthoMovementRules rules = new OrthoMovementRules(LINEAR, 4);
		assertFalse(rules.abideRules(c1_1, c2_3, board));
		assertFalse(rules.abideRules(c1_1, c1_4, board));   //linear move but blocked
	}
	
	@Test
	void testOrthoTrue() 
	{
		OrthoMovementRules rules = new OrthoMovementRules(ORTHOGONAL, 6);
		assertTrue(rules.abideRules(c1_1, c1_4, board));
		assertTrue(rules.abideRules(c3_4, c2_1, board));
	}
	
	@Test 
	void testOmniFalse()
	{
		OrthoMovementRules rules = new OrthoMovementRules(OMNI, 4);
		assertFalse(rules.abideRules(c1_1, c1_4, board));
	}
	
	@Test
	void testJump() 
	{
		OrthoSquareBoard oboard = new OrthoSquareBoard(3, 4);
		oboard.setLocationType(c1_2, BLOCK);
		oboard.setLocationType(c1_3, BLOCK);
		oboard.putPieceAt(new EscapePiece(PLAYER1, SNAIL), c2_2);
		oboard.putPieceAt(new EscapePiece(PLAYER1, SNAIL), c3_1);
		
		OrthoMovementRules rules = new OrthoMovementRules(OMNI, 3);
		rules.setJump(true);
		
		assertFalse(rules.abideRules(c1_1, c1_4, oboard));
//		assertFalse(rules.abideRules(c2_1, c3_2, oboard));
		assertTrue(rules.abideRules(c2_1, c2_3, oboard));
	}
	
	@Test
	void testFly() 
	{
		OrthoMovementRules rules = new OrthoMovementRules(ORTHOGONAL, 4);
		rules.setFly(true);
		assertTrue(rules.abideRules(c1_4, c1_1, board));
	}
	
	@Test
	void testUnblocked() 
	{
		OrthoMovementRules rules = new OrthoMovementRules(LINEAR, 4);
		rules.setUnblock(true);
		assertTrue(rules.abideRules(c1_1, c1_4, board));
	}
}
