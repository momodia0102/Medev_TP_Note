/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.centralenantes.medev_tp_note;


/**
 * Représente un plateau de jeu rectangulaire.
 * Chaque case du plateau est représentée par un caractère.
 *
 * Exemple :
 * '.' : case vide
 * 'X' : pion ou élément joueur 1
 * 'O' : pion ou élément joueur 2
 */
public class PlateauDeJeu {

    private final int largeur;
    private final int hauteur;
    private final char[][] cases;

    /**
     * Crée un plateau vide de dimensions données.
     *
     * @param largeur nombre de colonnes
     * @param hauteur nombre de lignes
     */
    public PlateauDeJeu(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.cases = new char[hauteur][largeur];
        initialiser();
    }

    /**
     * Initialise le plateau avec des cases vides.
     */
    private void initialiser() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                cases[i][j] = '.';
            }
        }
    }

    /**
     * Place un symbole sur le plateau si la position est valide.
     *
     * @param ligne ligne du plateau
     * @param colonne colonne du plateau
     * @param symbole caractère à placer
     * @return true si le placement a réussi, false sinon
     */
    public boolean placer(int ligne, int colonne, char symbole) {
        if (!positionValide(ligne, colonne)) {
            return false;
        }
        if (cases[ligne][colonne] != '.') {
            return false;
        }
        cases[ligne][colonne] = symbole;
        return true;
    }

    /**
     * Vérifie si une position est dans les limites du plateau.
     *
     * @param ligne ligne
     * @param colonne colonne
     * @return true si valide
     */
    public boolean positionValide(int ligne, int colonne) {
        return ligne >= 0 && ligne < hauteur
            && colonne >= 0 && colonne < largeur;
    }

    /**
     * Affiche le plateau dans la console.
     */
    public void afficher() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                System.out.print(cases[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}

