/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_model;

/**
 *
 * @author Yoann
 */
public class Minesweeper {

    private final Grid grid; // Grille du démineur

    public Minesweeper() {
        this.grid = new Grid();
    }

    /**
     *
     * @return la grille du jeu
     */
    public Grid getGrid() {
        return this.grid;
    }

}
