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
public class Grid {
    public static final int HEIGHT_GRID = 9;
    public static final int WIDTH_GRID = 9;
    public static final int NB_MINES = 10;
    
    private Box[][] grid;
    
    public Grid()
    {
        this.grid = new Box[HEIGHT_GRID][WIDTH_GRID];
        initGrid();
    }
    
    public void initGrid()
    {
        for(int i=0; i<WIDTH_GRID; i++)
        {
            for(int j=0; j< HEIGHT_GRID; j++)
            {
                this.grid[i][j] = new Box(i,j);
            }
        }
        Random rand = new Random();
        
        for(int i=0 ; i<NB_MINES; i++){
            int  randX = rand.nextInt(HEIGHT_GRID);
            int  randY = rand.nextInt(WIDTH_GRID);
            if(addMineInit(randX, randY)){
                System.out.println("MINE");
            }
            else{
                i--;
            }
        }
        
        calculNumberBox();
    }
    
    public boolean addMineInit(int i, int j){
        if(this.grid[i][j].getBoxType() == BoxType.EMPTY){
            this.grid[i][j].setBoxType(BoxType.MINE);
            return true;
        }
        else{
            return false;
        }
    }
    
    public void calculNumberBox(){
        for(int i=0 ; i<HEIGHT_GRID; i++){
            for(int j=0; j<WIDTH_GRID; j++){
                if(this.grid[i][j].getBoxType() == BoxType.MINE){
                    setNumberBox(i-1, j-1);
                    setNumberBox(i, j-1);
                    setNumberBox(i+1, j-1);
                    
                    setNumberBox(i-1, j);
                    setNumberBox(i+1, j);
                    
                    setNumberBox(i-1, j+1);
                    setNumberBox(i, j+1);
                    setNumberBox(i+1, j+1);
                }
            }
        }
    }
    
    public void setNumberBox(int x, int y){
          try {
            if((this.grid[x][y].getBoxType() == BoxType.EMPTY) || (this.grid[x][y].getBoxType() == BoxType.NUMBER)){
                int actualValue = this.grid[x][y].getBoxValue();
                this.grid[x][y].setBoxType(BoxType.NUMBER);
                this.grid[x][y].setBoxValue(actualValue+1);
            }
          } catch (Exception e) {
            System.out.println("Index hors tableau" + e);
          }
    }
    
    public ArrayList getNeighbour(Box b){
        //System.out.println("OULOULOU");
        ArrayList neighb = new ArrayList();
        int i = b.getPosX();
        int j = b.getPosY();
        
        for(int k = i-1;k<=i+1;k++){
            for(int l = j-1;l<=j+1;l++){
                
                
                
                if((k>=0) && (k<HEIGHT_GRID)){
                    if((l>=0) && (l<WIDTH_GRID)){
                        //if(((k != i) || (l != j)) && ((k != i-1) && (l != j))){
                        if( (k != i) || (l != j) ){
                            if(  ((k == i-1) && (l==j-1)) || ((k == i+1) && (l==j-1)) || ((k == i-1) && (l==j+1) || ((k == i+1) && (l==j+1)) )  ){
                                System.out.println("DIAGONALE");
                            }
                            else{
                                neighb.add(this.grid[k][l]);
                                System.out.println("Ajout du voisin a la pos : ["+k+"]" + "["+l+"]");
                             
                            }
                        }
                    }
                    else{
                        //System.out.println("index incorrect");
                    }
                }
                else{
                    //System.out.println("index incorrect");
                }
            }
        }
        System.out.println("Nombre de voisins de la case ["+i+"]" + "["+j+"] : "+neighb.size());

        return neighb;
    }
    
    public void showEmptyBox(Box b){
        if(b.getBoxType() == BoxType.EMPTY){
            ArrayList toTreat = new ArrayList();
            ArrayList everTreated = new ArrayList();
            ArrayList neighbs;
            toTreat.add(b);

            while(toTreat.size() != 0){
               Box currentBox = (Box) toTreat.get(0);
               everTreated.add(toTreat.get(0));
               toTreat.remove(0);
               neighbs = getNeighbour(currentBox);
               for(int i=0; i<neighbs.size(); i++){
                   Box neighb = (Box) neighbs.get(i);
                   if (neighb.getBoxType() == BoxType.EMPTY){
                       if((toTreat.contains(neighb) == false) && (everTreated.contains(neighb) == false)){
                           neighb.setDiscovered();
                           System.out.println("ON AJOUTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                           toTreat.add(neighb);
                       }
                       else{
                           
                       }
                   }
               }
               neighbs.clear();           
            }
        }
    }
    
    public int getWidth(){
        return this.HEIGHT_GRID;
    }
    
    public int getHeight(){
        return this.WIDTH_GRID;
    }
    
    public Box getBox(int i, int j){
        return this.grid[i][j];
    }
}
