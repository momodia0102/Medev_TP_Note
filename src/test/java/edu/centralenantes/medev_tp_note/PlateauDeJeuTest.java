/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.centralenantes.medev_tp_note;

import edu.centralenantes.Hangman.PlateauDeJeu;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author Mohamed
 */


public class PlateauDeJeuTest {

    @Test
    public void testPlacementValide() {
        PlateauDeJeu plateau = new PlateauDeJeu(5, 5);
        boolean resultat = plateau.placer(2, 2, 'X');
        assertTrue(resultat, "Le placement devrait être valide");
    }

    @Test
    public void testPlacementSurCaseOccupee() {
        PlateauDeJeu plateau = new PlateauDeJeu(5, 5);
        plateau.placer(1, 1, 'X');
        boolean resultat = plateau.placer(1, 1, 'O');
        assertFalse(resultat, "Impossible de placer sur une case occupée");
    }

    @Test
    public void testPlacementHorsPlateau() {
        PlateauDeJeu plateau = new PlateauDeJeu(5, 5);
        boolean resultat = plateau.placer(10, 0, 'X');
        assertFalse(resultat, "Impossible de placer hors du plateau");
    }
}
