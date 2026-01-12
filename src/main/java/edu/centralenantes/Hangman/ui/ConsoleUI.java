/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.centralenantes.Hangman.ui;

/**
 *
 * @author dodi
 */
import edu.centralenantes.Hangman.engine.GameEngine;
import edu.centralenantes.Hangman.model.GameState;
import java.util.Scanner;
import java.util.Set;

/**
 * Interface console pour le jeu du pendu.
 * Gère uniquement les entrées/sorties, pas la logique métier.
 * 
 * @author MEDEV 2026
 * @version 1.0
 */
public class ConsoleUI {
    
    private final Scanner scanner;
    private final GameEngine engine;
    private HangmanDrawer drawer;
    
    public ConsoleUI(GameEngine engine) {
        this.scanner = new Scanner(System.in);
        this.engine = engine;
    }
    
    /**
     * Affiche le titre du jeu.
     */
    public void displayTitle() {
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║     JEU DU PENDU - MEDEV      ║");
        System.out.println("╚═══════════════════════════════╝\n");
    }
    
    /**
     * Affiche l'état actuel du jeu.
     */
    public void displayGameState() {
        GameState state = engine.getCurrentState();
        
        System.out.println("\n" + drawer.draw(state.getErrorCount()));
        System.out.println("\nMot à deviner : " + state.getMaskedWord());
        System.out.println("Erreurs restantes : " + state.getRemainingErrors() + 
                          "/" + state.getMaxErrors());
        
        Set<Character> proposed = state.getProposedLetters();
        if (!proposed.isEmpty()) {
            System.out.print("Lettres proposées : ");
            proposed.stream().sorted().forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }
    
    /**
     * Demande et valide une lettre au joueur.
     * 
     * @return la lettre validée
     */
    public char askForLetter() {
        while (true) {
            System.out.print("\nProposez une lettre : ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("⚠ Vous devez entrer une lettre.");
                continue;
            }
            
            if (input.length() > 1) {
                System.out.println("⚠ Entrez une seule lettre.");
                continue;
            }
            
            char letter = input.charAt(0);
            
            if (!Character.isLetter(letter)) {
                System.out.println("⚠ Caractère invalide. Entrez une lettre.");
                continue;
            }
            
            return letter;
        }
    }
    
    /**
     * Affiche le résultat d'une proposition.
     */
    public void displayGuessResult(char letter, boolean isCorrect, boolean wasAlreadyProposed) {
        if (wasAlreadyProposed) {
            System.out.println("ℹ Vous avez déjà proposé la lettre '" + 
                             Character.toUpperCase(letter) + "'");
        } else if (isCorrect) {
            System.out.println("✓ Bonne lettre !");
        } else {
            System.out.println("✗ Mauvaise lettre...");
        }
    }
    
    /**
     * Affiche le message de fin de partie.
     */
    public void displayEndGame() {
        GameState state = engine.getCurrentState();
        
        System.out.println("\n" + drawer.draw(state.getErrorCount()));
        
        if (engine.hasWon()) {
            System.out.println("\n╔═══════════════════════════════╗");
            System.out.println("║       🎉 VICTOIRE ! 🎉        ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.println("\nFélicitations ! Vous avez trouvé le mot : " + 
                             state.getSecretWord());
        } else {
            System.out.println("\n╔═══════════════════════════════╗");
            System.out.println("║         💀 DÉFAITE 💀         ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.println("\nLe mot était : " + state.getSecretWord());
        }
    }
    
    /**
     * Demande au joueur s'il veut rejouer.
     */
    public boolean askPlayAgain() {
        System.out.print("\nVoulez-vous rejouer ? (o/n) : ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("o") || input.equals("oui") || input.equals("y") || input.equals("yes");
    }
    
    /**
     * Demande au joueur un mot secret (mode 2 joueurs).
     */
    public String askForSecretWord() {
        while (true) {
            System.out.print("\nJoueur 1, entrez le mot secret : ");
            String word = scanner.nextLine().trim();
            
            if (word.isEmpty()) {
                System.out.println("⚠ Le mot ne peut pas être vide.");
                continue;
            }
            
            if (!word.matches("[a-zA-Z]+")) {
                System.out.println("⚠ Le mot doit contenir uniquement des lettres.");
                continue;
            }
            
            // Effacer l'écran (simulation)
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
            
            System.out.println("✓ Mot secret enregistré !");
            return word;
        }
    }
    
    /**
     * Demande le nombre maximal d'erreurs.
     */
    public int askForMaxErrors() {
        while (true) {
            System.out.print("Nombre d'erreurs autorisées (6 ou 7 recommandé) : ");
            String input = scanner.nextLine().trim();
            
            try {
                int maxErrors = Integer.parseInt(input);
                if (maxErrors <= 0) {
                    System.out.println("⚠ Le nombre doit être positif.");
                    continue;
                }
                if (maxErrors > 10) {
                    System.out.println("⚠ Maximum recommandé : 10");
                    continue;
                }
                return maxErrors;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrez un nombre valide.");
            }
        }
    }
    
    /**
     * Initialise le drawer avec le nombre d'erreurs.
     */
    public void initDrawer(int maxErrors) {
        this.drawer = new HangmanDrawer(maxErrors);
    }
    
    /**
     * Ferme le scanner.
     */
    public void close() {
        scanner.close();
    }
}

