/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.centralenantes.Hangman;


import edu.centralenantes.Hangman.data.Dictionary;
import edu.centralenantes.Hangman.engine.GameEngine;
import edu.centralenantes.Hangman.model.GameState;
import edu.centralenantes.Hangman.ui.ConsoleUI;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principale du jeu du pendu.
 * 
 * @author Mohamadou Dia
 * @version 1.0
 */
public class HangmanGame {
    
    private static final String DEFAULT_DICTIONARY = "dictionnaire.txt";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        ConsoleUI ui = new ConsoleUI(engine);
        Scanner scanner = new Scanner(System.in);
        
        ui.displayTitle();
        
        boolean playAgain = true;
        
        while (playAgain) {
            try {
                // Choix du mode de jeu
                int mode = chooseGameMode(scanner);
                
                // Choix du nombre d'erreurs
                int maxErrors = ui.askForMaxErrors();
                ui.initDrawer(maxErrors);
                
                String secretWord;
                
                if (mode == 1) {
                    // Mode 1 joueur : mot depuis le dictionnaire
                    secretWord = loadWordFromDictionary();
                } else {
                    // Mode 2 joueurs : mot saisi manuellement
                    secretWord = ui.askForSecretWord();
                }
                
                // Démarrer la partie
                engine.startNewGame(secretWord, maxErrors);
                
                // Boucle de jeu
                playGame(engine, ui);
                
                // Demander si le joueur veut rejouer
                playAgain = ui.askPlayAgain();
                
            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
                e.printStackTrace();
                playAgain = false;
            }
        }
        
        System.out.println("\nMerci d'avoir joué ! À bientôt.");
        ui.close();
        scanner.close();
    }
    
    /**
     * Demande au joueur de choisir le mode de jeu.
     * @param scanner
     * @return 
     */
    private static int chooseGameMode(Scanner scanner) {
        while (true) {
            System.out.println("Choisissez un mode de jeu :");
            System.out.println("  1 - Un joueur (mot aléatoire)");
            System.out.println("  2 - Deux joueurs (mot personnalisé)");
            System.out.print("Votre choix : ");
            
            String input = scanner.nextLine().trim();
            
            if (input.equals("1")) {
                return 1;
            } else if (input.equals("2")) {
                return 2;
            } else {
                System.out.println("⚠ Choix invalide. Entrez 1 ou 2.\n");
            }
        }
    }
    
    /**
     * Charge un mot depuis le dictionnaire.
     * @return
     * @throws IOException 
     */
    private static String loadWordFromDictionary() throws IOException {
        Dictionary dictionary = new Dictionary();
        
        try {
            dictionary.loadFromFile(DEFAULT_DICTIONARY);
            System.out.println("✓ Dictionnaire chargé avec succès !");
            return dictionary.getRandomWord();
        } catch (IOException e) {
            System.err.println("⚠ Erreur lors du chargement du dictionnaire : " + e.getMessage());
            System.err.println("Assurez-vous que le fichier '" + DEFAULT_DICTIONARY + 
                             "' existe dans le répertoire courant.");
            throw e;
        }
    }
    
    /**
     * Boucle principale du jeu.
     * @param engine
     * @param ui 
     */
    private static void playGame(GameEngine engine, ConsoleUI ui) {
        while (!engine.isGameOver()) {
            ui.displayGameState();
            
            char letter = ui.askForLetter();
            
            GameState stateBefore = engine.getCurrentState();
            boolean wasAlreadyProposed = stateBefore.isLetterProposed(letter);
            
            boolean isCorrect = engine.guessLetter(letter);
            
            ui.displayGuessResult(letter, isCorrect, wasAlreadyProposed);
        }
        
        ui.displayEndGame();
    }
}