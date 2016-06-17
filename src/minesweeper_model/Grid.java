/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Yoann
 */
public final class Grid {

    public static final int HEIGHT_GRID = 9; // Hauteur de la grille
    public static final int WIDTH_GRID = 9; // Largeur de la grille
    public static final int NB_MINES = 10; // Nombre de mines
    private final Box[][] grid; // Grille constituée de cases

    public Grid() {
        this.grid = new Box[HEIGHT_GRID][WIDTH_GRID];
        initGrid();
    }

    /**
     * Fonction d'initialisation de la grille. (Initialisation des cases, dépot
     * des mines, calcul des cases nombres)
     */
    public void initGrid() {
        for (int i = 0; i < WIDTH_GRID; i++) // Pour toute la largeur de la grille
        {
            for (int j = 0; j < HEIGHT_GRID; j++) // Pour toute la hauteur de la grille
            {
                this.grid[i][j] = new Box(i, j); // On initialise la case à "vide"
            }
        }
        Random rand = new Random();

        for (int i = 0; i < NB_MINES; i++) { // Pour toutes les mines à placer
            int randX = rand.nextInt(WIDTH_GRID); // On tire une position y au hasard
            int randY = rand.nextInt(HEIGHT_GRID); // On tire une position y au hasard
            if (!addMineInit(randX, randY)) { // Si l'ajout de mine à échoué
                i--;
            }
        }

        calculNumberBox(); // Calcul des cases "nombre"
    }

    /**
     * Fonction qui ajoute une mine sur la grille
     *
     * @param i position en largeur de la mine
     * @param j position en hauteur de la mine
     * @return un booléan à 'true' si l'ajout de mine réussi, 'false' sinon
     */
    public boolean addMineInit(int i, int j) {
        if (this.grid[i][j].getBoxType() == BoxType.EMPTY) { // Si la case ou déposé la mine est vide
            this.grid[i][j].setBoxType(BoxType.MINE); // On ajoute la mine
            return true;
        } else { // Si il y a déjà une mine dessus
            return false;
        }
    }

    /**
     * Fonction qui calcule pour chaque case le nombre a afficher dedans
     * (indique le nombre de mines sur les cases adjacentes)
     */
    public void calculNumberBox() {
        for (int i = 0; i < HEIGHT_GRID; i++) { // Pour toute la hauteur de la grille
            for (int j = 0; j < WIDTH_GRID; j++) { // Pour toute la largeur de la grille
                if (this.grid[i][j].getBoxType() == BoxType.MINE) { // Si la case est une mine
                    // On ajoute '1' a la valeur de chaque case adjacente 

                    incrementNumberBox(i - 1, j - 1);
                    incrementNumberBox(i, j - 1);
                    incrementNumberBox(i + 1, j - 1);

                    incrementNumberBox(i - 1, j);
                    incrementNumberBox(i + 1, j);

                    incrementNumberBox(i - 1, j + 1);
                    incrementNumberBox(i, j + 1);
                    incrementNumberBox(i + 1, j + 1);
                }
            }
        }
    }

    /**
     * Fonction qui ajoute '1' pour une case passée en parametre (avec les
     * positions sur la grille)
     *
     * @param x position en largeur
     * @param y position en hauteur
     */
    public void incrementNumberBox(int x, int y) {
        try {
            if ((this.grid[x][y].getBoxType() == BoxType.EMPTY) || (this.grid[x][y].getBoxType() == BoxType.NUMBER)) { // Si la case n'est pas une mine
                int actualValue = this.grid[x][y].getBoxValue(); // On récupère la valeur de la case
                this.grid[x][y].setBoxType(BoxType.NUMBER);
                this.grid[x][y].setBoxValue(actualValue + 1); // On incrémente la valeur '1'
            }
        } catch (Exception e) {
        }
    }

    /**
     * Fonction qui retourne une liste de cases adjacente à la case passé en
     * paramètre
     *
     * @param b case à analyser
     * @return une liste de cases
     */
    public ArrayList getNeighbour(Box b) {
        ArrayList neighb = new ArrayList();
        int i = b.getPosX();
        int j = b.getPosY();

        for (int k = i - 1; k <= i + 1; k++) { // Pour toutes les cases adjacentes (de i-1 à i+1)
            for (int l = j - 1; l <= j + 1; l++) { // Pour toutes les cases adjacentes (de j-1 à j+1)

                if ((k >= 0) && (k < HEIGHT_GRID)) { // Si la case adjacente est dans la grille
                    if ((l >= 0) && (l < WIDTH_GRID)) { // Si la case adjacente est dans la grille
                        if ((k != i) || (l != j)) { // Si ce n'est pas la case passé en paramètre
                            neighb.add(this.grid[k][l]); // On ajoute la case à la liste des voisins
                        }
                    }
                }
            }
        }
        return neighb;
    }

    /**
     * Fonction de découverte de cases vides (utilisation d'une file et d'un
     * tableau de marque, à la manière d'un parcours en largeur)
     *
     * @param b case cliquée
     */
    public void showEmptyBox(Box b) {
        if (b.getBoxType() == BoxType.EMPTY) { // Si la case est vide
            ArrayList toTreat = new ArrayList(); // File à traiter
            ArrayList everTreated = new ArrayList(); // File de cases déjà traités
            ArrayList neighbs;
            toTreat.add(b); // On ajoute la case à la file de traitement

            while (!toTreat.isEmpty()) { // Tant que la file n'est pas vide
                Box currentBox = (Box) toTreat.get(0); // On récupère le premier élément de la file
                everTreated.add(toTreat.get(0)); // On l'ajoute à la file de cases déjà traités
                toTreat.remove(0); // On défile
                neighbs = getNeighbour(currentBox); // On récupère les voisins de la case
                for (int i = 0; i < neighbs.size(); i++) { // Pour tout ces voisins
                    Box neighb = (Box) neighbs.get(i); // On prend le voisin
                    if ((neighb.getBoxType() == BoxType.EMPTY)) { // Si c'est une case vide
                        if ((toTreat.contains(neighb) == false) && (everTreated.contains(neighb) == false)) { // Si ce sommet n'est pas déjà traité ou à traiter
                            neighb.setDiscovered(); // On découvre la case
                            neighb.setFlagged(0); // On met le code drapeau à zéro
                            toTreat.add(neighb);  // On ajoute l'élement dans la file de traitement
                        }
                    }
                    if ((neighb.getBoxType() == BoxType.NUMBER)) { // Si c'est une case 'nombre'
                        neighb.setDiscovered(); // On découvre la case
                        neighb.setFlagged(0); // On met le code drapeau à zéro
                    }
                }
                neighbs.clear(); // On vide la liste des voisins         
            }
        }
    }

    /**
     * Fonction qui découvre toutes les cases de la grille
     */
    public void showAll() {
        for (int i = 0; i < HEIGHT_GRID; i++) {
            for (int j = 0; j < WIDTH_GRID; j++) {
                this.grid[i][j].setDiscoveredAndConservFlag();
            }
        }
    }

    /**
     *
     * @return la largeur de la grille
     */
    public int getWidth() {
        return Grid.WIDTH_GRID;
    }

    /**
     *
     * @return la hauteur de la grille
     */
    public int getHeight() {
        return Grid.HEIGHT_GRID;
    }

    /**
     *
     * @param i position horizontale de la case
     * @param j position verticale de la case
     * @return la case à la position [i][j]
     */
    public Box getBox(int i, int j) {
        return this.grid[i][j];
    }

    /**
     * Fonction qui affiche une matrice de la grille dans la console
     *
     * @return la matrice de la grille
     */
    @Override
    public String toString() {
        String line = "";
        for (int i = 0; i < WIDTH_GRID; i++) { // Pour toute la largeur de la grille
            for (int j = 0; j < HEIGHT_GRID; j++) { // Pour toute la hauteur de la grille
                line += this.grid[i][j].toString();
                System.out.print(this.grid[j][i].toString());
            }
            System.out.println(" ");
            line += "\n";
        }
        return line;
    }

    /**
     * Fonction qui parcours la grille pour détecter si une mine à été
     * découverte
     *
     * @return 'true' si le jeu est encore en cours, 'false' si une mine à été
     * découverte
     */
    public boolean isGameInProgress() {
        for (int i = 0; i < HEIGHT_GRID; i++) {
            for (int j = 0; j < WIDTH_GRID; j++) {
                if ((this.grid[i][j].getBoxType() != BoxType.MINE) && (this.grid[i][j].getIsDiscovered() == 0)) {
                    return false;
                }
            }
        }
        return true;
    }
}
