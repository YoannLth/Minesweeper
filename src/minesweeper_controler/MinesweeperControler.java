/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_controler;

import minesweeper_model.AbstractModel;
/**
 *
 * @author Yoann
 */
public class MinesweeperControler extends AbstractControler{

    public MinesweeperControler(AbstractModel minsw) {
        super(minsw);
    }

    public void control() {
    //On notifie le modèle d'une action si le contrôle est bon
    //--------------------------------------------------------

    //Si l'opérateur est dans la liste
    if(this.listOperateur.contains(this.operateur)){
      //Si l'opérateur est = 
      if(this.operateur.equals("="))
        this.minsw.getResultat(); //On ordonne au modèle d'afficher le résultat
      //Sinon, on passe l'opérateur au modèle
      else
        this.minsw.setOperateur(this.operateur);
    }

    //Si le nombre est conforme
    if(this.nbre.matches("^[0-9.]+$"))
      this.minsw.setNombre(this.nbre);

    this.operateur = "";
    this.nbre = "";
    }
}
