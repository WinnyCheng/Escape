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

package escape.board.coordinate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Tests for various coordinates
 * @version Mar 28, 2020
 */
class CoordinateTest
{
	//SquareCoordinate
	static Stream<Arguments> squareDistance()
	{
		return Stream.of(
				Arguments.of(6, 3, 6, 3, 0),
				Arguments.of(4, 4, 4, 1, 3),
				Arguments.of(1, 4, 1, 9, 5),
				Arguments.of(3, 1, 12, 1, 9),
				Arguments.of(8, 5, 1, 5, 7),
				Arguments.of(3, 2, 7, 5, 4),
				Arguments.of(4, 6, 5, 1, 5),
				Arguments.of(6, 2, 2, 5, 4),
				Arguments.of(7, 7, 1, 3, 6)
			);
	}
    
	@ParameterizedTest
	@MethodSource("squareDistance")
	void squareCoordinateDistanceTo(int x1, int y1, int x2, int y2, int distance) 
	{
		assertEquals(distance, SquareCoordinate.makeCoordinate(x1, y1).distanceTo(SquareCoordinate.makeCoordinate(x2, y2)));
	}
	
	@Test
	void squareCoordinateEqualsTrue() 
	{
		assertTrue(SquareCoordinate.makeCoordinate(5, 7).equals(SquareCoordinate.makeCoordinate(5, 7)));
	}
	@Test
	void squareCoordinateEqualsFalse() 
	{
		assertFalse(SquareCoordinate.makeCoordinate(5, 0).equals(SquareCoordinate.makeCoordinate(8, 7)));
	}
	
	
	//OrthoSquareCoordinate
	static Stream<Arguments> orthoSquareDistance()
	{
		return Stream.of(
				Arguments.of(6, 3, 6, 3, 0),
				Arguments.of(4, 4, 4, 1, 3),
				Arguments.of(1, 4, 1, 9, 5),
				Arguments.of(3, 1, 12, 1, 9),
				Arguments.of(8, 5, 1, 5, 7),
				Arguments.of(3, 2, 7, 5, 7),
				Arguments.of(4, 6, 5, 1, 6),
				Arguments.of(6, 2, 2, 5, 7),
				Arguments.of(7, 7, 1, 3, 10)
			);
	}
    
	@ParameterizedTest
	@MethodSource("orthoSquareDistance")
	void orthoSquareCoordinateDistanceTo(int x1, int y1, int x2, int y2, int distance) 
	{
		assertEquals(distance, OrthoSquareCoordinate.makeCoordinate(x1, y1).distanceTo(OrthoSquareCoordinate.makeCoordinate(x2, y2)));
	}
	
	@Test
	void orthoSquareCoordinateEqualsTrue() 
	{
		assertTrue(OrthoSquareCoordinate.makeCoordinate(5, 7).equals(OrthoSquareCoordinate.makeCoordinate(5, 7)));
	}
	@Test
	void orthoSquareCoordinateEqualsFalse() 
	{
		assertFalse(OrthoSquareCoordinate.makeCoordinate(5, 0).equals(OrthoSquareCoordinate.makeCoordinate(8, 7)));
	}
	
	
	//HexCoordinate (0,0) -> (-1, 2) is 2, (-1, 2) -> (2, -2) is 4.
	static Stream<Arguments> hexDistance()
	{
		return Stream.of(
				Arguments.of(-2, -3, -2, -3, 0),
				Arguments.of(0, 0, 0, 7, 7),
				Arguments.of(3, -4, 3, 7, 11),
				Arguments.of(-2, 0, 2, -2, 4),
				Arguments.of(1, 0, -1, 1, 2),
				Arguments.of(0, 0, -2, 2, 2),
				Arguments.of(0, 0, 1, 0, 1),
				Arguments.of(0, 0, -3, 0, 3),
				Arguments.of(0, 0, 4, -4, 4),
				Arguments.of(0, 0, 3, 2, 5),
				Arguments.of(0, 0, 1, -3, 3),
				Arguments.of(0, 0, -1, 2, 2),
				Arguments.of(-5, 3, 3, -2, 8),
				Arguments.of(-5, 3, -2, -3, 6),
				Arguments.of(1, 1, -1, -1, 4),
				Arguments.of(-2, -3, 1, 2, 8),
				Arguments.of(0, 3, 2, 2, 2),
				Arguments.of(0, 0, -1, 2, 2),
				Arguments.of(-1, 2, 2, -2, 4)
			);
	}
    
	@ParameterizedTest
	@MethodSource("hexDistance")
	void hexCoordinateDistanceTo(int x1, int y1, int x2, int y2, int distance) 
	{
		assertEquals(distance, HexCoordinate.makeCoordinate(x1, y1).distanceTo(HexCoordinate.makeCoordinate(x2, y2)));
	}
	
	@Test
	void hexCoordinateEqualsTrue() 
	{
		assertTrue(HexCoordinate.makeCoordinate(5, 7).equals(HexCoordinate.makeCoordinate(5, 7)));
	}
	@Test
	void hexCoordinateEqualsFalse() 
	{
		assertFalse(HexCoordinate.makeCoordinate(5, 0).equals(HexCoordinate.makeCoordinate(8, 7)));
	}
	
   
    
}
