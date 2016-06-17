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
public class Box extends java.util.Observable {

    private int posX; // position horizontale sur la grille
    private int posY; // position verticale sur la grille
    private BoxType boxType; // type de case (Mine, vide ou nombre)
    private int boxValue; // valeur de la case (lorsque c'est un nombre)
    private int isDiscovered; // variable qui définie si la case est découverte ou non (nombre de fois ou elle a été découverte)
    private int flag; // variable qui définie si il y a un drapeau sur la case ou non

    public Box(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.boxType = BoxType.EMPTY;
        this.boxValue = 0;
        this.isDiscovered = 0;
        this.flag = 0;
    }

    /**
     *
     * @param x position horizontale sur la grille
     */
    public void setPosX(int x) {
        this.posX = x;
    }

    /**
     *
     * @param y position verticale sur la grille
     */
    public void setPosY(int y) {
        this.posY = y;
    }

    /**
     *
     * @param bt type de case (Mine, vide ou nombre)
     */
    public void setBoxType(BoxType bt) {
        this.boxType = bt;
    }

    /**
     * Fonction qui définie si une case est découverte ou non. A chaque fois
     * qu'on la 'découvre', on incrémente de 1 (incrémentation utile dans
     * certains traitements)
     */
    public void setDiscovered() {
        this.isDiscovered += 1;
        this.flag = 0;
        setChanged();
        notifyObservers();
    }

    /**
     * Fonction qui définie si une case est découverte ou non
     *
     * @param i nombre de fois ou la case est découverte
     */
    public void setDiscovered(int i) {
        this.isDiscovered = i;
        this.flag = 0;
        setChanged();
        notifyObservers();
    }

    /**
     * Fonction qui définie si une case est découverte ou non et qui conserve
     * les drapeaux. A chaque fois qu'on la 'découvre', on incrémente de 1
     * (incrémentation utile dans certains traitements)
     */
    public void setDiscoveredAndConservFlag() {
        this.isDiscovered += 1;
        setChanged();
        notifyObservers();
    }

    /**
     *
     * @param bv valeur de la case (lorsque c'est un nombre)
     */
    public void setBoxValue(int bv) {
        this.boxValue = bv;
    }

    /**
     *
     * @param i indicateur de drapeau
     */
    public void setFlagged(int i) {
        this.flag = i;
        setChanged();
        notifyObservers();
    }

    /**
     *
     * @return le code drapeau de la case
     */
    public int getIsFlagged() {
        return this.flag;
    }

    /**
     *
     * @return la position horizontale de la case
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     *
     * @return la position verticale de la case
     */
    public int getPosY() {
        return this.posY;
    }

    /**
     *
     * @return le type de la case
     */
    public BoxType getBoxType() {
        return this.boxType;
    }

    /**
     *
     * @return le code de découverte de la case
     */
    public int getIsDiscovered() {
        return this.isDiscovered;
    }

    /**
     *
     * @return la valeur de la case
     */
    public int getBoxValue() {
        return this.boxValue;
    }

    /**
     *
     * @return une une mise en forme textuelle de la case pour la matrice de la
     * grille dans la console (Le type sur un char et la position sous la forme
     * [x][y]
     */
    @Override
    public String toString() {
        BoxType boxT = this.getBoxType();

        if (boxT == BoxType.MINE) {
            return " M [" + this.getPosX() + "][" + this.getPosY() + "]";
        }

        if (boxT == BoxType.EMPTY) {
            return "   [" + this.getPosX() + "][" + this.getPosY() + "]";
        } else {
            return " . [" + this.getPosX() + "][" + this.getPosY() + "]";
        }
    }
}
