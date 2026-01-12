/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.centralenantes.Hangman.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe GameState.
 * 
 * @author MEDEV 2026
 * @version 1.0
 */
public class GameStateTest {
    
    private GameState initialState;
    
    @BeforeEach
    public void setUp() {
        initialState = new GameState("JAVA", 6);
    }
    
    // Tests de création d'état
    
    @Test
    public void testCreateGameState_Valid() {
        assertNotNull(initialState);
        assertEquals("JAVA", initialState.getSecretWord());
        assertEquals(6, initialState.getMaxErrors());
        assertEquals(6, initialState.getRemainingErrors());
        assertEquals(GameState.Status.IN_PROGRESS, initialState.getStatus());
    }
    
    @Test
    public void testCreateGameState_NullWord() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(null, 6);
        });
    }
    
    @Test
    public void testCreateGameState_EmptyWord() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameState("", 6);
        });
    }
    
    @Test
    public void testCreateGameState_InvalidWord() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameState("JA123VA", 6);
        });
    }
    
    @Test
    public void testCreateGameState_NegativeMaxErrors() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameState("JAVA", -1);
        });
    }
    
    @Test
    public void testCreateGameState_ZeroMaxErrors() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameState("JAVA", 0);
        });
    }
    
    // Tests des transitions d'état
    
    @Test
    public void testProposeLettre_Correct() {
        GameState newState = initialState.proposeLettre('J');
        
        assertTrue(newState.getProposedLetters().contains('J'));
        assertEquals(6, newState.getRemainingErrors());
        assertEquals(GameState.Status.IN_PROGRESS, newState.getStatus());
    }
    
    @Test
    public void testProposeLettre_Incorrect() {
        GameState newState = initialState.proposeLettre('Z');
        
        assertTrue(newState.getProposedLetters().contains('Z'));
        assertEquals(5, newState.getRemainingErrors());
        assertEquals(GameState.Status.IN_PROGRESS, newState.getStatus());
    }
    
    @Test
    public void testProposeLettre_CaseInsensitive() {
        GameState newState = initialState.proposeLettre('j');
        
        assertTrue(newState.getProposedLetters().contains('J'));
        assertFalse(newState.getProposedLetters().contains('j'));
    }
    
    @Test
    public void testProposeLettre_AlreadyProposed() {
        GameState state1 = initialState.proposeLettre('J');
        GameState state2 = state1.proposeLettre('J');
        
        assertEquals(state1.getRemainingErrors(), state2.getRemainingErrors());
        assertEquals(state1.getProposedLetters().size(), state2.getProposedLetters().size());
    }
    
    @Test
    public void testProposeLettre_InvalidCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            initialState.proposeLettre('1');
        });
    }
    
    @Test
    public void testProposeLettre_GameAlreadyWon() {
        GameState state = initialState;
        state = state.proposeLettre('J');
        state = state.proposeLettre('A');
        state = state.proposeLettre('V');
        
        assertEquals(GameState.Status.WON, state.getStatus());
        
        assertThrows(IllegalStateException.class, () -> {
            state.proposeLettre('Z');
        });
    }
    
    // Tests des lettres répétées
    
    @Test
    public void testRepeatedLetters_AllRevealed() {
        GameState state = new GameState("BANANA", 6);
        state = state.proposeLettre('A');
        
        String masked = state.getMaskedWord();
        assertTrue(masked.contains("A _ _ A _ A") || 
                  masked.replaceAll(" ", "").equals("_A_A_A"));
    }
    
    @Test
    public void testRepeatedLetters_Count() {
        GameState state = new GameState("AAAAAA", 6);
        state = state.proposeLettre('A');
        
        assertEquals("A A A A A A", state.getMaskedWord());
        assertEquals(GameState.Status.WON, state.getStatus());
    }
    
    // Tests de victoire
    
    @Test
    public void testVictory_AllLettersFound() {
        GameState state = initialState;
        state = state.proposeLettre('J');
        state = state.proposeLettre('A');
        state = state.proposeLettre('V');
        
        assertEquals(GameState.Status.WON, state.getStatus());
        assertEquals(6, state.getRemainingErrors());
    }
    
    @Test
    public void testVictory_WithErrors() {
        GameState state = initialState;
        state = state.proposeLettre('Z'); // erreur
        state = state.proposeLettre('J');
        state = state.proposeLettre('X'); // erreur
        state = state.proposeLettre('A');
        state = state.proposeLettre('V');
        
        assertEquals(GameState.Status.WON, state.getStatus());
        assertEquals(4, state.getRemainingErrors());
    }
    
    // Tests de défaite
    
    @Test
    public void testDefeat_MaxErrorsReached() {
        GameState state = initialState;
        
        // 6 erreurs
        state = state.proposeLettre('Z');
        state = state.proposeLettre('X');
        state = state.proposeLettre('W');
        state = state.proposeLettre('Q');
        state = state.proposeLettre('B');
        state = state.proposeLettre('C');
        
        assertEquals(GameState.Status.LOST, state.getStatus());
        assertEquals(0, state.getRemainingErrors());
    }
    
    @Test
    public void testDefeat_OneLetterMissing() {
        GameState state = initialState;
        
        // Trouver J, A, V mais pas A final, puis perdre
        state = state.proposeLettre('J');
        state = state.proposeLettre('V');
        state = state.proposeLettre('Z');
        state = state.proposeLettre('X');
        state = state.proposeLettre('W');
        state = state.proposeLettre('Q');
        state = state.proposeLettre('B');
        state = state.proposeLettre('C');
        
        assertEquals(GameState.Status.LOST, state.getStatus());
    }
    
    // Tests du mot masqué
    
    @Test
    public void testMaskedWord_Initial() {
        String masked = initialState.getMaskedWord();
        assertEquals("_ _ _ _", masked);
    }
    
    @Test
    public void testMaskedWord_PartiallyRevealed() {
        GameState state = initialState.proposeLettre('J');
        String masked = state.getMaskedWord();
        assertEquals("J _ _ _", masked);
    }
    
    @Test
    public void testMaskedWord_FullyRevealed() {
        GameState state = initialState;
        state = state.proposeLettre('J');
        state = state.proposeLettre('A');
        state = state.proposeLettre('V');
        
        String masked = state.getMaskedWord();
        assertEquals("J A V A", masked);
    }
    
    // Tests des getters
    
    @Test
    public void testGetErrorCount() {
        GameState state = initialState;
        assertEquals(0, state.getErrorCount());
        
        state = state.proposeLettre('Z');
        assertEquals(1, state.getErrorCount());
        
        state = state.proposeLettre('X');
        assertEquals(2, state.getErrorCount());
    }
    
    @Test
    public void testGetProposedLetters_Immutability() {
        GameState state = initialState.proposeLettre('J');
        var letters = state.getProposedLetters();
        
        // Tenter de modifier le Set retourné ne doit pas affecter l'état
        letters.add('Z');
        
        assertFalse(state.getProposedLetters().contains('Z'));
        assertEquals(1, state.getProposedLetters().size());
    }
    
    // Tests de non-régression
    
    @Test
    public void testNonRegression_CompleteGame() {
        GameState state = new GameState("PENDU", 7);
        
        // Scénario complet d'une partie
        state = state.proposeLettre('E');
        assertEquals(1, state.getProposedLetters().size());
        assertEquals(7, state.getRemainingErrors());
        
        state = state.proposeLettre('A'); // erreur
        assertEquals(6, state.getRemainingErrors());
        
        state = state.proposeLettre('P');
        state = state.proposeLettre('N');
        state = state.proposeLettre('D');
        state = state.proposeLettre('U');
        
        assertEquals(GameState.Status.WON, state.getStatus());
        assertEquals("P E N D U", state.getMaskedWord());
    }
    
    @Test
    public void testNonRegression_VariableWordLength() {
        // Mot court
        GameState short1 = new GameState("OK", 6);
        short1 = short1.proposeLettre('O');
        short1 = short1.proposeLettre('K');
        assertEquals(GameState.Status.WON, short1.getStatus());
        
        // Mot long
        GameState long1 = new GameState("ANTICONSTITUTIONNELLEMENT", 10);
        assertNotNull(long1);
        assertEquals(25, long1.getSecretWord().length());
    }
}