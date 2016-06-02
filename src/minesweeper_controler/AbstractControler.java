/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_controler;

import java.util.ArrayList;
import minesweeper_model.AbstractModel;
/**
 *
 * @author Yoann
 */
public abstract class AbstractControler {
  protected AbstractModel minsw;
  protected String operateur = "", nbre = "";
  protected ArrayList<String> listOperateur = new ArrayList<String>();

  public AbstractControler(AbstractModel cal){
    this.minsw = cal;
    //On définit la liste des opérateurs
    //Afin de s'assurer qu'ils sont corrects
    this.listOperateur.add("+");
    this.listOperateur.add("-");
    this.listOperateur.add("*");
    this.listOperateur.add("/");
    this.listOperateur.add("=");
   }
   
  //Définit l'opérateur
  public void setOperateur(String ope){
    this.operateur = ope;
    control();
  }
   
  //Définit le nombre
  public void setNombre(String nombre){
    this.nbre = nombre;
    control();
  }
   
  //Efface
  public void reset(){
    this.minsw.reset();
  }
   
  //Méthode de contrôle
  abstract void control();    
}
