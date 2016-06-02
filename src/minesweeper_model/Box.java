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
public class Box {
    private int posX;
    private int posY;
    private BoxContent value;
    private boolean isDiscovered;
    
    public Box(int x, int y)
    {
        this.posX = x;
        this.posY = y;
        this.value = BoxContent.EMPTY;
    }
    
    public void setPosX(int x){
        this.posX = x;
    }
    
    public void setPosY(int y){
        this.posY = y;
    }
    
    public void setValue(BoxContent val){
        this.value = val;
    }
    
    public void setIsDiscovered(boolean isDisc){
        this.isDiscovered = isDisc;
    }
    
    
    
    
    public int getPosX(){
        return this.posX;
    }
    
    public int getPosY(){
        return this.posY;
    }
    
    public BoxContent getValue(){
        return this.value;
    }
    
    public boolean getIsDiscovered(){
        return this.isDiscovered;
    }
}
