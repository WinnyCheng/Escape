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
    	System.out.println("SquareBoard");
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameSquareBoard.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.addObserver(new EscapeGameObserver());
        
        // turn 1
        assertTrue(emg.move(emg.makeCoordinate(3, 7), emg.makeCoordinate(5, 9))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(3, 1), emg.makeCoordinate(4, 2))); // Player 2
        
        // turn 2
        // tries to move from a location where piece no exist, observer should print
        // "There is no piece currently on the given coordinate."
        assertFalse(emg.move(emg.makeCoordinate(8, 3), emg.makeCoordinate(4, 3))); // no piece
        assertTrue(emg.move(emg.makeCoordinate(4, 2), emg.makeCoordinate(4, 5))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(1, 9), emg.makeCoordinate(4, 9))); // Player 2
        
        // turn 3
        assertTrue(emg.move(emg.makeCoordinate(4, 5), emg.makeCoordinate(7, 5))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(4, 9), emg.makeCoordinate(6, 10))); // Player 2
        
        // turn 4
        assertTrue(emg.move(emg.makeCoordinate(7, 5), emg.makeCoordinate(7, 8))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(6, 10), emg.makeCoordinate(7, 8))); // Player 2
        
        // end of game, observer should print
        // "The game is already over. The winner is player 2!"
        assertFalse(emg.move(emg.makeCoordinate(5, 9), emg.makeCoordinate(5, 10)));
        
    }
    
    @Test
    void testEscapeGameBuilderOrtho() throws Exception
    {
    	System.out.println("OrthoBoard");
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameOrthoBoard.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        emg.addObserver(new EscapeGameObserver());
        
        // turn 1
        assertTrue(emg.move(emg.makeCoordinate(4, 1), emg.makeCoordinate(3, 1))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(1, 3), emg.makeCoordinate(1, 1))); // Player 2
        // player 2 tries to go again (not player2's turn), observer should print
        // "Wrong player: It is the other player's turn to make a move"
        assertFalse(emg.move(emg.makeCoordinate(2, 3), emg.makeCoordinate(3, 3))); // Player 2
        
        // turn 2
        // player 1 tries to move to blocked location, observer should print
        // "Cannot place piece on blocked coordinate."
        assertFalse(emg.move(emg.makeCoordinate(4, 2), emg.makeCoordinate(4, 3))); //Player 1
        assertTrue(emg.move(emg.makeCoordinate(4, 2), emg.makeCoordinate(3, 4))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(1, 4), emg.makeCoordinate(2, 4))); // Player 2
        
        // turn 3
        assertTrue(emg.move(emg.makeCoordinate(3, 4), emg.makeCoordinate(2, 4))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(2, 3), emg.makeCoordinate(2, 2))); // Player 2
        
        // turn 4
        assertTrue(emg.move(emg.makeCoordinate(2, 4), emg.makeCoordinate(4, 4))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(2, 1))); // Player 2
        
        // end of game, observer should print
        // "The game is already over. It's a tie."
        assertFalse(emg.move(emg.makeCoordinate(2, 1), emg.makeCoordinate(1, 1)));
        
    }
    
    @Test
    void testEscapeGameBuilderHex() throws Exception
    {
    	System.out.println("HexBoard");
        EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/EscapeGameHexBoard.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        
        // print same line twice since there's two observers
        emg.addObserver(new EscapeGameObserver());
        emg.addObserver(new EscapeGameObserver());
        
        // turn 1
        assertTrue(emg.move(emg.makeCoordinate(-2, 1), emg.makeCoordinate(0, 1))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(1, -1), emg.makeCoordinate(1, 1))); // Player 2
        
        // turn 2
        // player 1 tries to move to blocked location, observer should print
        // "Cannot move piece to new coordinate. Another one of your pieces currently occupies that space."
        assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(2, -1))); //Player 1
        assertTrue(emg.move(emg.makeCoordinate(1, -2), emg.makeCoordinate(1, 1))); // Player 1
        assertTrue(emg.move(emg.makeCoordinate(-3, 1), emg.makeCoordinate(-3, 2))); // Player 2
        
        // turn 3
        assertTrue(emg.move(emg.makeCoordinate(2, -1), emg.makeCoordinate(0, 0))); // Player 1
        
        // end of game, observer should print
        // "The game is already over. The winner is player 1!"
        assertFalse(emg.move(emg.makeCoordinate(-3, 2), emg.makeCoordinate(0, 2)));
    }
    
    @Test
    void invalidConfigNoDistNoFly() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/InvalidConfig_NoDistNoFly.xml"));
    	Exception exception = assertThrows(EscapeException.class, () -> { egb.makeGameManager(); });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void invalidConfigNoPieceType() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/InvalidConfig_NoPieceType.xml"));
    	Exception exception = assertThrows(EscapeException.class, () -> { egb.makeGameManager(); });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void invalidConfigBothDistFly() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/InvalidConfig_BothDistFly.xml"));
    	Exception exception = assertThrows(EscapeException.class, () -> { egb.makeGameManager(); });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void invalidConfigNoPieceName() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/InvalidConfig_NoPieceName.xml"));
    	Exception exception = assertThrows(EscapeException.class, () -> { egb.makeGameManager(); });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void invalidConfigNoPattern() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/InvalidConfig_NoPattern.xml"));
    	Exception exception = assertThrows(EscapeException.class, () -> { egb.makeGameManager(); });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
    @Test
    void invalidConfigBothRemovePointConflict() throws Exception
    {
    	EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/InvalidConfig_BothRemovePointConflict.xml"));
    	Exception exception = assertThrows(EscapeException.class, () -> { egb.makeGameManager(); });
	    assertTrue(exception.getMessage().contains("Configuration of Game is not valid."));
    }
    
}
