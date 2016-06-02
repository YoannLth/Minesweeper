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
public class Grid {
    public static final int HEIGHT_GRID = 8;
    public static final int WIDTH_GRID = 8;
    
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
        this.grid[0][0].setValue(BoxContent.MINE);
    }
}
