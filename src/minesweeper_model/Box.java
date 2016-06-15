/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Yoann
 */
public class Box extends java.util.Observable {
    private int posX;
    private int posY;
    private BoxType boxType;
    private int boxValue;
    private int isDiscovered;
    
    public Box(int x, int y)
    {
        this.posX = x;
        this.posY = y;
        this.boxType = BoxType.EMPTY;
        this.boxValue = 0;
        this.isDiscovered = 0;
    }
    
    public void setPosX(int x){
        this.posX = x;
    }
    
    public void setPosY(int y){
        this.posY = y;
    }
    
    public void setBoxType(BoxType bt){
        this.boxType = bt;
    }
    
    public void setDiscovered(){
        this.isDiscovered += 1;
        setChanged();
        notifyObservers();
    }
    
    public void setBoxValue(int bv){
        this.boxValue = bv;
    }  
 
    
    
    public int getPosX(){
        return this.posX;
    }
    
    public int getPosY(){
        return this.posY;
    }
    
    public BoxType getBoxType(){
        return this.boxType;
    }
    
    public int getIsDiscovered(){
        return this.isDiscovered;
    }
    
    public int getBoxValue(){
        return this.boxValue;
    }
}
