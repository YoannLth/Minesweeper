/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_model;

import java.util.ArrayList;
import minesweeper_model.Observable;
import minesweeper_model.Observer;
/**
 *
 * @author Yoann
 */
public abstract class AbstractModel implements Observable{ 
    protected Grid grid = new Grid();   
    protected double result;
    protected String operateur = "", operande = "";
    private ArrayList<Observer> listObserver = new ArrayList<Observer>();   
    //Efface 
    public abstract void reset();

    //Effectue le calcul
    public abstract void calcul();

    //Affichage forcé du résultat
    public abstract void getResultat();

    //Définit l'opérateur de l'opération
    public abstract void setOperateur(String operateur);

    //Définit le nombre à utiliser pour l'opération
    public abstract void setNombre(String nbre) ;

    //Implémentation du pattern observer
    public void addObserver(Observer obs) {
      this.listObserver.add(obs);
    }

    public void notifyObserver(String str) {
      if(str.matches("^0[0-9]+"))
        str = str.substring(1, str.length());

      for(Observer obs : listObserver)
        obs.update(str);
    }

    public void removeObserver() {
      listObserver = new ArrayList<Observer>();
    }      
}
