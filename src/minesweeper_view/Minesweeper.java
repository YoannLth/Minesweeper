/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_view;

import javafx.scene.image.Image;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import minesweeper_model.Box;
import minesweeper_model.Grid;
import javafx.scene.*;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper_model.Grid;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import minesweeper_model.BoxType;

/**
 *
 * @author Yoann
 */
public class Minesweeper extends Application implements Observer{
    private minesweeper_model.Minesweeper minesweeperModel;
    private static final double MIN_HEIGHT_BOX = 64.0; // in px
    private static final double MIN_WIDTH_BOX = 64.0; // in px
    private HashMap boxAndButton = new HashMap();
    
    Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
                
        minesweeperModel = new minesweeper_model.Minesweeper();
        this.primaryStage = primaryStage;
        
        Grid g = minesweeperModel.getGrid();
        int column = g.getHeight();
        int row = g.getWidth();
        
        System.out.println(row);
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();
        
        
        
        // création des bouton et placement dans la grille
        for (int i = 0; i<row; i++) {
            for (int j = 0; j<column; j++) {
                Box b = g.getBox(i, j);
                
                try {
                    String bDisplayText = b.getBoxType().name();
                    int numberOfCloseMines = b.getBoxValue();
                    
                    Button button = new Button();
                    button.setMinHeight(MIN_HEIGHT_BOX);
                    button.setMinWidth(MIN_WIDTH_BOX);
                    
                    gPane.add(button, i, j);
                    b.addObserver(this);
                    
                    boxAndButton.put(b, button);
                    
                    button.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            b.setDiscovered();
                            g.showEmptyBox(b);
                            //To begin, check if the case is hidden. If it's not, uselss to do the job.
                            if(b.getIsDiscovered() != 0)
                            {
                                //if(event.getEventType() == MouseButton.PRIMARY)//Then on Left click
                                //{
                                    if(b.getBoxType() == BoxType.MINE)//If it's a mine
                                    {
                                        g.showAll();//We put all the case visible
                                        endGame(false);//End of the game, defeat
                                        return;
                                    }
                                    //We call the function to show all the neighborhood
                                    //this.c.showCases(c, new ArrayList<Case>());
                                    //If the game is finished
                                    /*if(this.c.parent.isGameFinished())
                                    {
                                        //Victory
                                        c.parent.showAll();
                                        parent.endGame(true);
                                    }
                                }
                                else if(event.getButton() == MouseButton.SECONDARY)//Then on Right click
                                {
                                    //We invert the flag
                                    c.invertFlag();
                               }*/   
                            }
                            }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }  
            }
            
            
        }
                        
        gPane.setGridLinesVisible(true);
        
        border.setCenter(gPane);
        
        Scene scene = new Scene(border, Color.rgb(52,101,164));
        
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }


    @Override
    public void update(Observable o, Object arg) {
        Box b = (Box) o;
        Button but = (Button) boxAndButton.get(b);
        
        String bDisplayText = b.getBoxType().name();
        int numberOfCloseMines = b.getBoxValue();
        
        if(b.getIsDiscovered() == 1){
            switch(bDisplayText){
                case "NUMBER" :
                    but.setStyle("-fx-background-color: #3465A4; -fx-background-image: url('/ressources/" + numberOfCloseMines + ".png')");
                    break;
                case "EMPTY" :
                    but.setStyle("-fx-background-color: #3465A4;"); 
                    break;
                case "MINE" :
                    but.setStyle("-fx-background-color: #3465A4; -fx-background-image: url('/ressources/mine.png')");
                    break;
                default :
                    System.out.println("BUG switch");
                    break;
            }
        
            System.out.println("CLICK RECEIVED");
        }
        else{
            System.out.println("BUTTON EVER CLICKED");
        }
    }
    

    public void endGame(boolean victory){
        if(victory)
        {
            Alert end = new Alert(Alert.AlertType.INFORMATION, "YOU WIN!!!", ButtonType.YES, ButtonType.NO);
            end.setTitle("VICTORY");
            end.setHeaderText("Field is successfully cleared!");
            end.setContentText("Again?");
            end.showAndWait();
            if(end.getResult() == ButtonType.YES)
            {
                minesweeperModel.getGrid().initGrid();
            }
            else
            {
                primaryStage.close();
            }
        }
        else
        {
            Alert end = new Alert(Alert.AlertType.ERROR, "GAME OVER! YOU FIND A MINE!", ButtonType.YES, ButtonType.NO);
            end.setTitle("GAME OVER");
            end.setHeaderText("GAME OVER! YOU FIND A MINE!");
            end.setContentText("Retry?");
            end.showAndWait();
            if(end.getResult() == ButtonType.YES)
            {
                minesweeperModel.getGrid().initGrid();
            }
            /*else if(end.getResult().getButtonData() == ButtonBar.ButtonData.OTHER)
            {
                this.gridGame = new Grid(this.gridGame.WIDTH_GRID, this.gridGame.HEIGHT_GRID, this.gridGame.MINE_NUMBER);
                paintGrid(border);
            }*/
            else
            {
                primaryStage.close();
            }
        }          
    }
}