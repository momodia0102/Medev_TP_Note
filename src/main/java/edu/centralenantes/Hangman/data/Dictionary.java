/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.centralenantes.Hangman.data;

/**
 *
 * @author dodi
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gère le chargement et la sélection de mots depuis un fichier externe.
 * 
 * @author MEDEV 2026
 * @version 1.0
 */
public class Dictionary {
    
    private final List<String> validWords;
    private final Random random;
    
    /**
     * Constructeur par défaut.
     */
    public Dictionary() {
        this.validWords = new ArrayList<>();
        this.random = new Random();
    }
    
    /**
     * Charge les mots depuis un fichier texte.
     * Un mot par ligne. Les mots invalides sont ignorés.
     * 
     * @param filePath chemin vers le fichier dictionnaire
     * @throws IOException si le fichier est introuvable ou illisible
     * @throws IllegalStateException si le fichier est vide ou ne contient aucun mot valide
     */
    public void loadFromFile(String filePath) throws IOException {
        validWords.clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();
                
                // Ignorer les lignes vides
                if (trimmedLine.isEmpty()) {
                    continue;
                }
                
                // Valider le mot (uniquement des lettres)
                if (isValidWord(trimmedLine)) {
                    validWords.add(trimmedLine.toUpperCase());
                } else {
                    System.err.println("Ligne " + lineNumber + " ignorée (mot invalide) : " + trimmedLine);
                }
            }
        }
        
        if (validWords.isEmpty()) {
            throw new IllegalStateException("Le dictionnaire ne contient aucun mot valide");
        }
        
        System.out.println("Dictionnaire chargé : " + validWords.size() + " mots valides");
    }
    
    /**
     * Vérifie si un mot est valide (uniquement des lettres).
     * @param word
     * @return
     */
    private boolean isValidWord(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        return word.matches("[a-zA-ZÀ-ÿ]+");
    }
    
    /**
     * Sélectionne un mot aléatoire du dictionnaire.
     * 
     * @return un mot aléatoire
     * @throws IllegalStateException si le dictionnaire est vide
     */
    public String getRandomWord() {
        if (validWords.isEmpty()) {
            throw new IllegalStateException("Le dictionnaire est vide. Chargez d'abord un fichier.");
        }
        
        int index = random.nextInt(validWords.size());
        return validWords.get(index);
    }
    
    /**
     * Retourne le nombre de mots valides dans le dictionnaire.
     * @return
     */
    public int getWordCount() {
        return validWords.size();
    }
    
    /**
     * Vérifie si le dictionnaire est vide.
     * @return
     */
    public boolean isEmpty() {
        return validWords.isEmpty();
    }
}