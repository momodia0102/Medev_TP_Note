/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.centralenantes.Hangman.engine;
import edu.centralenantes.Hangman.model.GameState;

/**
 * Moteur du jeu du pendu.
 * Gère les règles et transitions d'état.
 * 
 * @author MEDEV 2026
 * @version 1.0
 */
public class GameEngine {
    
    private GameState currentState;
    
    /**
     * Démarre une nouvelle partie.
     * 
     * @param secretWord le mot à deviner
     * @param maxErrors le nombre maximal d'erreurs
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public void startNewGame(String secretWord, int maxErrors) {
        this.currentState = new GameState(secretWord, maxErrors);
    }
    
    /**
     * Propose une lettre.
     * 
     * @param letter la lettre à proposer
     * @return true si la lettre est dans le word, false sinon
     * @throws IllegalArgumentException si la lettre est invalide
     * @throws IllegalStateException si aucune partie n'est en cours
     */
    public boolean guessLetter(char letter) {
        if (currentState == null) {
            throw new IllegalStateException("Aucune partie en cours");
        }
        
        boolean wasAlreadyProposed = currentState.isLetterProposed(letter);
        
        GameState newState = currentState.proposeLettre(letter);
        
        // Si l'état n'a pas changé, la lettre était déjà proposée
        if (wasAlreadyProposed) {
            return currentState.getSecretWord().indexOf(Character.toUpperCase(letter)) >= 0;
        }
        
        boolean isCorrect = newState.getRemainingErrors() == currentState.getRemainingErrors();
        currentState = newState;
        
        return isCorrect;
    }
    
    /**
     * Retourne l'état actuel du jeu.
     * @return
     */
    public GameState getCurrentState() {
        return currentState;
    }
    
    /**
     * Vérifie si la partie est terminée.
     * @return
     */
    public boolean isGameOver() {
        return currentState != null && 
               currentState.getStatus() != GameState.Status.IN_PROGRESS;
    }
    
    /**
     * Vérifie si le joueur a gagné.
     * @return
     */
    public boolean hasWon() {
        return currentState != null && 
               currentState.getStatus() == GameState.Status.WON;
    }
    
    /**
     * Vérifie si le joueur a perdu.
     * @return
     */
    public boolean hasLost() {
        return currentState != null && 
               currentState.getStatus() == GameState.Status.LOST;
    }
}