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
import static escape.board.coordinate.HexCoordinate.makeCoordinate;
import static escape.piece.PieceName.*;
import static escape.piece.Player.*;
import static escape.piece.MovementPatternID.*;
import org.junit.jupiter.api.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.movement.HexMovementRules;

/**
 * Description
 * @version May 5, 2020
 */
public class HexMovementRulesTest
{
	private static HexBoard board;
	
	private static HexCoordinate c0_0, c1_n1, c0_n1, cn1_0, cn1_1, c0_1, c1_0, 
		c0_2, c1_1, c2_0, c2_n1, c2_n2, c1_n2, c0_n2, cn1_n1, cn2_0, cn2_1, cn2_2, cn1_2;
	
	@BeforeAll
	public static void setup() throws Exception
	{	
		c0_0 = makeCoordinate(0, 0);		
		c1_n1 = makeCoordinate(1, -1);
		c0_n1 = makeCoordinate(0, -1);
		cn1_0 = makeCoordinate(-1, 0);
		cn1_1 = makeCoordinate(-1, 1);
		c0_1 = makeCoordinate(0, 1);
		c1_0 = makeCoordinate(1, 0);
		
		c0_2 = makeCoordinate(0, 2);
		c1_1 = makeCoordinate(1, 1);
		c2_0 = makeCoordinate(2, 0);
		c2_n1 = makeCoordinate(2, -1);
		c2_n2 = makeCoordinate(2, -2);
		c1_n2 = makeCoordinate(1, -2);
		c0_n2 = makeCoordinate(0, -2);
		cn1_n1 = makeCoordinate(-1, -1);
		cn2_0 = makeCoordinate(-2, 0);
		cn2_1 = makeCoordinate(-2, 1);
		cn2_2 = makeCoordinate(-2, 2);
		cn1_2 = makeCoordinate(-1, 2);
		
		board = new HexBoard(0, 0);
		board.setLocationType(cn1_0, BLOCK);
		board.putPieceAt(new EscapePiece(PLAYER1, SNAIL), c1_0);
	}
	
	@Test
	void testLinearTrue() 
	{
		HexMovementRules rules = new HexMovementRules(LINEAR, 4);
		assertTrue(rules.abideRules(c0_n2, c0_2, board));
		assertTrue(rules.abideRules(cn1_n1, c2_n1, board));
		assertTrue(rules.abideRules(c0_0, c2_n2, board));
		assertTrue(rules.abideRules(c1_0, c2_n1, board));
		assertNotNull(board.getPieceAt(c1_0));
	}
	
	@Test
	void testLinearFalse() 
	{
		HexMovementRules rules = new HexMovementRules(LINEAR, 3);
		assertFalse(rules.abideRules(cn2_1, c2_n1, board));
		assertFalse(rules.abideRules(cn1_n1, cn1_2, board));   //linear move but blocked
		assertFalse(rules.abideRules(c2_0, c0_1, board));
		assertFalse(rules.abideRules(c0_0, c2_0, board));
	}
	
	@Test 
	void testOmniTrue()
	{
		HexMovementRules rules = new HexMovementRules(OMNI, 4);
		assertTrue(rules.abideRules(c0_n2, c1_1, board));
		assertTrue(rules.abideRules(cn1_n1, cn1_1, board));
		
	}
	
	@Test 
	void testOmniFalse()
	{
		HexMovementRules rules = new HexMovementRules(OMNI, 3);
		assertFalse(rules.abideRules(c1_n1, cn1_0, board));
		assertFalse(rules.abideRules(c1_n2, c1_0, board));
		
	}
	
	@Test
	void testJump() 
	{	
		HexMovementRules rules = new HexMovementRules(LINEAR, 2);
		rules.setJump(true);
		
		assertFalse(rules.abideRules(cn2_0, c0_0, board));
		assertFalse(rules.abideRules(cn2_1, c0_n1, board));
		assertTrue(rules.abideRules(c1_n1, c1_1, board));
		assertTrue(rules.abideRules(c0_1, c2_n1, board));
	}
	
	@Test
	void testFly() 
	{
		HexMovementRules rules = new HexMovementRules(LINEAR, 2);
		rules.setFly(true);
		assertTrue(rules.abideRules(cn1_n1, cn1_1, board));
		assertTrue(rules.abideRules(c2_n1, c0_1, board));
	}
	
	@Test
	void testUnblocked() 
	{
		HexMovementRules rules = new HexMovementRules(OMNI, 2);
		rules.setUnblock(true);
		assertTrue(rules.abideRules(cn1_1, cn1_n1, board));
	}
}
