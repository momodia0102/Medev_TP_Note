# Jeu du Pendu — MEDEV (TP noté)

Implémentation en **Java** du jeu du pendu, réalisée en binôme dans le cadre du TP noté MEDEV.  
Le projet respecte les principes attendus : **séparation des responsabilités (moteur / modèle / IHM / données)**, **tests unitaires**, **automatisation Ant**, et **analyse qualité via SonarCloud**.

---

## 🧩 Fonctionnalités

### Modes de jeu
- **Mode 1 joueur** : mot choisi aléatoirement depuis un dictionnaire.
- **Mode 2 joueurs** : Joueur 1 saisit le mot secret, Joueur 2 devine.

### Règles implémentées
- Nombre d’erreurs **paramétrable** (ex. 6 ou 7 recommandé).
- Entrées **insensibles à la casse**.
- Refus des caractères invalides (uniquement lettres).
- Une lettre déjà proposée **ne pénalise pas** (pas de tentative perdue).
- Détection de **victoire** (mot entièrement révélé) et **défaite** (plus de tentatives).

---

## 🏗️ Architecture (packages)

Le code est structuré ainsi :

edu.centralenantes.Hangman
├── HangmanGame.java # Classe principale (main / orchestration)
├── data/
│ └── Dictionary.java # Chargement + sélection aléatoire des mots
├── engine/
│ └── GameEngine.java # Règles métier, transitions d'état
├── model/
│ └── GameState.java # Etat du jeu (mot, lettres, erreurs, statut)
└── ui/
├── ConsoleUI.java # IHM console (affichage + saisie)
└── HangmanDrawer.java # Affichage ASCII du pendu

📊 Qualité — SonarCloud

Le dépôt est relié à SonarCloud et l’analyse est déclenchée automatiquement via GitHub Actions :

à chaque push

et à chaque pull request

Les résultats (Quality Gate, issues, coverage new code, etc.) sont consultables dans l’interface SonarCloud.

🧑‍🤝‍🧑 Organisation du travail (Git)

Développement en branches (ex. UI, moha_1, moha_2, etc.)

Intégration via Pull Requests vers main

Répartition : UI/Données vs Moteur/Modèle + tests/automatisation et rapport

👥 Auteurs

Safa et Dia
