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

package escape;

import static escape.board.coordinate.HexCoordinate.makeCoordinate;
import static escape.piece.PieceName.*;
import static escape.piece.Player.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * Description
 * @version Apr 24, 2020
 */
class BetaEscapeGameTests
{
    
    @Test
    void testEscapeGameBuilderSquare() throws Exception
    {
        EscapeGameBuilder egb 
            = new EscapeGameBuilder(new File("config/SampleEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        assertTrue(emg.move(emg.makeCoordinate(15, 15), emg.makeCoordinate(10, 10)));
        assertEquals(emg.getPieceAt(emg.makeCoordinate(10, 10)).getName(), FROG);
        assertEquals(emg.getPieceAt(emg.makeCoordinate(10, 10)).getPlayer(), PLAYER2);
        assertNull(emg.getPieceAt(emg.makeCoordinate(15, 15)));
        
        assertTrue(emg.move(emg.makeCoordinate(8, 9), emg.makeCoordinate(10, 10)));
        assertEquals(emg.getPieceAt(emg.makeCoordinate(10, 10)).getName(), HORSE);
        assertEquals(emg.getPieceAt(emg.makeCoordinate(10, 10)).getPlayer(), PLAYER1);
        assertNull(emg.getPieceAt(emg.makeCoordinate(8, 9)));
        
        assertFalse(emg.move(emg.makeCoordinate(10, 10), emg.makeCoordinate(20, 25)));
    }
    
    @Test
    void testEscapeGameBuilderOrtho() throws Exception
    {
        EscapeGameBuilder egb 
            = new EscapeGameBuilder(new File("config/SampleEscapeGameOrtho.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        assertTrue(emg.move(emg.makeCoordinate(15, 15), emg.makeCoordinate(15, 10)));
        assertEquals(emg.getPieceAt(emg.makeCoordinate(15, 10)).getName(), FROG);
        assertEquals(emg.getPieceAt(emg.makeCoordinate(15, 10)).getPlayer(), PLAYER2);
        assertNull(emg.getPieceAt(emg.makeCoordinate(15, 15)));
        
        assertTrue(emg.move(emg.makeCoordinate(8, 9), emg.makeCoordinate(15, 10)));
        assertEquals(emg.getPieceAt(emg.makeCoordinate(15, 10)).getName(), HORSE);
        assertEquals(emg.getPieceAt(emg.makeCoordinate(15, 10)).getPlayer(), PLAYER1);
        assertNull(emg.getPieceAt(emg.makeCoordinate(8, 9)));
        
        assertFalse(emg.move(emg.makeCoordinate(10, 10), emg.makeCoordinate(20, 25)));
    }
    
    @Test
    void testEscapeGameBuilderHex() throws Exception
    {
        EscapeGameBuilder egb 
            = new EscapeGameBuilder(new File("config/SampleEscapeGameHex.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        assertTrue(emg.move(emg.makeCoordinate(0, 0), emg.makeCoordinate(0, 4)));
        assertEquals(emg.getPieceAt(emg.makeCoordinate(0, 4)).getName(), FROG);
        assertEquals(emg.getPieceAt(emg.makeCoordinate(0, 4)).getPlayer(), PLAYER2);
        assertNull(emg.getPieceAt(emg.makeCoordinate(0, 0)));
        
        assertTrue(emg.move(emg.makeCoordinate(0, -2), emg.makeCoordinate(3, 4)));
        assertEquals(emg.getPieceAt(emg.makeCoordinate(3, 4)).getName(), HORSE);
        assertEquals(emg.getPieceAt(emg.makeCoordinate(3, 4)).getPlayer(), PLAYER1);
        assertNull(emg.getPieceAt(emg.makeCoordinate(0, -2)));
        
        assertFalse(emg.move(emg.makeCoordinate(10, 10), emg.makeCoordinate(20, 25)));
    }
    
    @Test
    void testValidityOfConfigFiles1() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameConfig1.xml"));
    	
    	
    	Exception exception = assertThrows(EscapeException.class, () -> {
    		EscapeGameManager emg = egb.makeGameManager();
	    });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void testValidityOfConfigFiles2() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameConfig2.xml"));
    	
    	
    	Exception exception = assertThrows(EscapeException.class, () -> {
    		EscapeGameManager emg = egb.makeGameManager();
	    });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void testValidityOfConfigFiles3() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameConfig3.xml"));
    	
    	
    	Exception exception = assertThrows(EscapeException.class, () -> {
    		EscapeGameManager emg = egb.makeGameManager();
	    });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void testValidityOfConfigFiles4() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameConfig4.xml"));
    	
    	
    	Exception exception = assertThrows(EscapeException.class, () -> {
    		EscapeGameManager emg = egb.makeGameManager();
	    });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void testValidityOfConfigFiles5() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameConfig5.xml"));
    	
    	
    	Exception exception = assertThrows(EscapeException.class, () -> {
    		EscapeGameManager emg = egb.makeGameManager();
	    });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
}
