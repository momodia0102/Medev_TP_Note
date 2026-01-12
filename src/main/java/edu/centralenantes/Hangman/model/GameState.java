package edu.centralenantes.Hangman.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente l'état d'une partie de pendu.
 * Classe immuable pour garantir les invariants.
 *
 * @author Mohamadou Dia
 * @version 1.0
 */
public class GameState {

    /** États possibles d'une partie */
    public enum Status {
        IN_PROGRESS,
        WON,
        LOST
    }

    private final String secretWord;
    private final Set<Character> proposedLetters;
    private final int remainingErrors;
    private final int maxErrors;
    private final Status status; // ✅ CORRIGÉ

    /**
     * Constructeur pour initialiser une nouvelle partie.
     *
     * @param secretWord le mot à deviner (doit contenir uniquement des lettres)
     * @param maxErrors le nombre maximal d'erreurs autorisées
     * @throws IllegalArgumentException si le mot est invalide ou maxErrors <= 0
     */
    public GameState(String secretWord, int maxErrors) {
        if (secretWord == null || secretWord.isEmpty()) {
            throw new IllegalArgumentException("Le mot secret ne peut pas être vide");
        }
        if (maxErrors <= 0) {
            throw new IllegalArgumentException("Le nombre d'erreurs doit être positif");
        }
        if (!secretWord.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Le mot doit contenir uniquement des lettres");
        }

        this.secretWord = secretWord.toUpperCase();
        this.proposedLetters = new HashSet<>();
        this.remainingErrors = maxErrors;
        this.maxErrors = maxErrors;
        this.status = Status.IN_PROGRESS;
    }

    /**
     * Constructeur privé pour créer un nouvel état après une transition.
     */
    private GameState(String secretWord,
                      Set<Character> proposedLetters,
                      int remainingErrors,
                      int maxErrors,
                      Status status) {
        this.secretWord = secretWord;
        this.proposedLetters = new HashSet<>(proposedLetters);
        this.remainingErrors = remainingErrors;
        this.maxErrors = maxErrors;
        this.status = status;
    }

    /**
     * Propose une lettre et retourne le nouvel état.
     */
    public GameState proposeLettre(char letter) {
        if (status != Status.IN_PROGRESS) {
            throw new IllegalStateException("La partie est terminée");
        }

        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException("Caractère invalide : " + letter);
        }

        char upperLetter = Character.toUpperCase(letter);

        // Lettre déjà proposée : pas de changement d'état
        if (proposedLetters.contains(upperLetter)) {
            return this;
        }

        Set<Character> newProposed = new HashSet<>(proposedLetters);
        newProposed.add(upperLetter);

        boolean isCorrect = secretWord.indexOf(upperLetter) >= 0;
        int newRemainingErrors =
                isCorrect ? remainingErrors : remainingErrors - 1;

        Status newStatus =
                computeStatus(newProposed, newRemainingErrors);

        return new GameState(secretWord,
                             newProposed,
                             newRemainingErrors,
                             maxErrors,
                             newStatus);
    }

    /**
     * Calcule le statut de la partie.
     */
    private Status computeStatus(Set<Character> proposed, int errors) {
        if (errors <= 0) {
            return Status.LOST;
        }

        for (char c : secretWord.toCharArray()) {
            if (!proposed.contains(c)) {
                return Status.IN_PROGRESS;
            }
        }

        return Status.WON;
    }

    /**
     * Retourne le mot avec les lettres masquées.
     */
    public String getMaskedWord() {
        StringBuilder masked = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            masked.append(proposedLetters.contains(c) ? c : '_')
                  .append(' ');
        }
        return masked.toString().trim();
    }

    public boolean isLetterProposed(char letter) {
        return proposedLetters.contains(
                Character.toUpperCase(letter));
    }

    // Getters

    public String getSecretWord() {
        return secretWord;
    }

    public Set<Character> getProposedLetters() {
        return new HashSet<>(proposedLetters);
    }

    public int getRemainingErrors() {
        return remainingErrors;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public Status getStatus() {
        return status;
    }

    public int getErrorCount() {
        return maxErrors - remainingErrors;
    }
}
