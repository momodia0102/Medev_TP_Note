package edu.centralenantes.Hangman.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de la classe GameState.
 *
 * Couvre :
 * - transitions d'état
 * - lettres répétées
 * - lettres invalides
 * - victoire / défaite
 * - non-régression des règles principales
 * @author Mohamadou dia and Safae Bouzidi
 * Conforme au TP MEDEV Janvier 2026.
 */
public class GameStateTest {

    private GameState initialState;

    /**
     *
     */
    @BeforeEach
    void setUp() {
        initialState = new GameState("JAVA", 6);
    }

    /* =======================
     * Création de l'état
     * ======================= */

    /**
     *
     */
    @Test
    void testCreateGameState_Valid() {
        assertEquals("JAVA", initialState.getSecretWord());
        assertEquals(6, initialState.getMaxErrors());
        assertEquals(6, initialState.getRemainingErrors());
        assertEquals(GameState.Status.IN_PROGRESS, initialState.getStatus());
    }

    /**
     *
     */
    @Test
    void testCreateGameState_InvalidWord() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameState("JA123", 6));
    }

    /**
     *
     */
    @Test
    void testCreateGameState_NullOrEmptyWord() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameState(null, 6));
        assertThrows(IllegalArgumentException.class,
                () -> new GameState("", 6));
    }

    /**
     *
     */
    @Test
    void testCreateGameState_InvalidMaxErrors() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameState("JAVA", 0));
        assertThrows(IllegalArgumentException.class,
                () -> new GameState("JAVA", -1));
    }

    /* =======================
     * Propositions de lettres
     * ======================= */

    /**
     *
     */
    @Test
    void testProposeLettre_Correct() {
        GameState state = initialState.proposeLettre('J');

        assertTrue(state.getProposedLetters().contains('J'));
        assertEquals(6, state.getRemainingErrors());
        assertEquals(GameState.Status.IN_PROGRESS, state.getStatus());
    }

    /**
     *
     */
    @Test
    void testProposeLettre_Incorrect() {
        GameState state = initialState.proposeLettre('Z');

        assertTrue(state.getProposedLetters().contains('Z'));
        assertEquals(5, state.getRemainingErrors());
    }

    /**
     *
     */
    @Test
    void testProposeLettre_CaseInsensitive() {
        GameState state = initialState.proposeLettre('j');

        assertTrue(state.getProposedLetters().contains('J'));
        assertFalse(state.getProposedLetters().contains('j'));
    }

    /**
     *
     */
    @Test
    void testProposeLettre_AlreadyProposed() {
        GameState state1 = initialState.proposeLettre('J');
        GameState state2 = state1.proposeLettre('J');

        assertEquals(state1.getRemainingErrors(), state2.getRemainingErrors());
        assertEquals(state1.getProposedLetters().size(),
                     state2.getProposedLetters().size());
    }

    /**
     *
     */
    @Test
    void testProposeLettre_InvalidCharacter() {
        assertThrows(IllegalArgumentException.class,
                () -> initialState.proposeLettre('1'));
    }

    /**
     *
     */
    @Test
    void testProposeLettre_GameAlreadyFinished() {
        GameState state = initialState
                .proposeLettre('J')
                .proposeLettre('A')
                .proposeLettre('V'); // victoire (A est répété)

        assertEquals(GameState.Status.WON, state.getStatus());

        assertThrows(IllegalStateException.class,
                () -> state.proposeLettre('Z'));
    }

    /* =======================
     * Lettres répétées
     * ======================= */

    /**
     *
     */
    @Test
    void testRepeatedLetters_AllOccurrencesRevealed() {
        GameState state = new GameState("BANANA", 6);
        state = state.proposeLettre('A');

        assertEquals("_ A _ A _ A", state.getMaskedWord());
    }

    /**
     *
     */
    @Test
    void testRepeatedLetters_AllSameLetter() {
        GameState state = new GameState("AAAAAA", 6);
        state = state.proposeLettre('A');

        assertEquals("A A A A A A", state.getMaskedWord());
        assertEquals(GameState.Status.WON, state.getStatus());
    }

    /* =======================
     * Victoire / Défaite
     * ======================= */

    /**
     *
     */
    @Test
    void testVictory_WithErrors() {
        GameState state = initialState
                .proposeLettre('Z')
                .proposeLettre('J')
                .proposeLettre('X')
                .proposeLettre('A')
                .proposeLettre('V');

        assertEquals(GameState.Status.WON, state.getStatus());
        assertEquals(4, state.getRemainingErrors());
    }

    /**
     *
     */
    @Test
    void testDefeat_MaxErrorsReached() {
        GameState state = initialState;

        state = state.proposeLettre('Z');
        state = state.proposeLettre('X');
        state = state.proposeLettre('W');
        state = state.proposeLettre('Q');
        state = state.proposeLettre('B');
        state = state.proposeLettre('C');

        assertEquals(GameState.Status.LOST, state.getStatus());
        assertEquals(0, state.getRemainingErrors());
    }

    /* =======================
     * Mot masqué
     * ======================= */

    /**
     *
     */
    @Test
    void testMaskedWord_Initial() {
        assertEquals("_ _ _ _", initialState.getMaskedWord());
    }

    /**
     *
     */
    @Test
    void testMaskedWord_Partial() {
        GameState state = initialState.proposeLettre('J');
        assertEquals("J _ _ _", state.getMaskedWord());
    }

    /**
     *
     */
    @Test
    void testMaskedWord_Full() {
        GameState state = initialState
                .proposeLettre('J')
                .proposeLettre('A')
                .proposeLettre('V');

        assertEquals("J A V A", state.getMaskedWord());
    }

    /* =======================
     * Immutabilité / getters
     * ======================= */

    /**
     *
     */
    @Test
    void testGetProposedLetters_Immutable() {
        GameState state = initialState.proposeLettre('J');
        Set<Character> letters = state.getProposedLetters();

        letters.add('Z'); // tentative de modification externe

        assertFalse(state.getProposedLetters().contains('Z'));
        assertEquals(1, state.getProposedLetters().size());
    }

    /**
     *
     */
    @Test
    void testGetErrorCount() {
        GameState state = initialState;
        assertEquals(0, state.getErrorCount());

        state = state.proposeLettre('Z');
        assertEquals(1, state.getErrorCount());
    }

    /* =======================
     * Non-régression
     * ======================= */

    /**
     *
     */
    @Test
    void testNonRegression_CompleteGameScenario() {
        GameState state = new GameState("PENDU", 7);

        state = state.proposeLettre('E');
        state = state.proposeLettre('A'); // erreur
        state = state.proposeLettre('P');
        state = state.proposeLettre('N');
        state = state.proposeLettre('D');
        state = state.proposeLettre('U');

        assertEquals(GameState.Status.WON, state.getStatus());
        assertEquals("P E N D U", state.getMaskedWord());
    }

    /**
     *
     */
    @Test
    void testNonRegression_VariableWordLength() {
        GameState shortWord = new GameState("OK", 6);
        shortWord = shortWord.proposeLettre('O').proposeLettre('K');

        assertEquals(GameState.Status.WON, shortWord.getStatus());

        GameState longWord =
                new GameState("ANTICONSTITUTIONNELLEMENT", 10);

        assertEquals(25, longWord.getSecretWord().length());
    }
}
