/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.centralenantes.Hangman.ui;

/**
 *
 * @author dodi
 */

/**
 * Gère l'affichage ASCII du pendu.
 * 
 * @author MEDEV 2026
 * @version 1.0
 */
public class HangmanDrawer {
    
    private final String[] stages;
    
    /**
     * Constructeur avec un nombre d'étapes paramétrable.
     * 
     * @param maxErrors nombre maximal d'erreurs (généralement 6 ou 7)
     */
    public HangmanDrawer(int maxErrors) {
        this.stages = generateStages(maxErrors);
    }
    
    /**
     * Génère les étapes du dessin en fonction du nombre d'erreurs.
     */
    private String[] generateStages(int maxErrors) {
        if (maxErrors == 6) {
            return new String[] {
                // 0 erreurs
                "  +---+\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 1 erreur - tête
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 2 erreurs - corps
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 3 erreurs - bras gauche
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 4 erreurs - bras droit
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 5 erreurs - jambe gauche
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " /    |\n" +
                "      |\n" +
                "=========",
                
                // 6 erreurs - jambe droite (pendu complet)
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " / \\  |\n" +
                "      |\n" +
                "========="
            };
        } else if (maxErrors == 7) {
            return new String[] {
                // 0 erreurs
                "  +---+\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 1 erreur - tête
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 2 erreurs - corps
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 3 erreurs - bras gauche
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|   |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 4 erreurs - bras droit
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "=========",
                
                // 5 erreurs - torse
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "  |   |\n" +
                "      |\n" +
                "=========",
                
                // 6 erreurs - jambe gauche
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "  |   |\n" +
                " /    |\n" +
                "=========",
                
                // 7 erreurs - jambe droite (pendu complet)
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "  |   |\n" +
                " / \\  |\n" +
                "========="
            };
        } else {
            // Mode simplifié pour d'autres valeurs
            String[] simple = new String[maxErrors + 1];
            for (int i = 0; i <= maxErrors; i++) {
                simple[i] = "  +---+\n" +
                           "  |   |\n" +
                           "      |\n" +
                           "      |\n" +
                           "      |\n" +
                           "      |\n" +
                           "=========\n" +
                           "Erreurs: " + i + "/" + maxErrors;
            }
            return simple;
        }
    }
    
    /**
     * Retourne le dessin correspondant au nombre d'erreurs.
     * 
     * @param errorCount nombre d'erreurs commises
     * @return le dessin ASCII
     */
    public String draw(int errorCount) {
        if (errorCount < 0 || errorCount >= stages.length) {
            return stages[stages.length - 1];
        }
        return stages[errorCount];
    }
}