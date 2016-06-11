/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_view;

import javafx.scene.image.Image;
import java.awt.event.MouseEvent;
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

/**
 *
 * @author Yoann
 */
public class Minesweeper extends Application{
    private minesweeper_model.Minesweeper minesweeperModel;
    private static final double MIN_HEIGHT_BOX = 64.0; // in px
    private static final double MIN_WIDTH_BOX = 64.0; // in px
    
    @Override
    public void start(Stage primaryStage) throws Exception {
                
        minesweeperModel = new minesweeper_model.Minesweeper();
        
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
                /*String bDisplayText = b.getBoxType().name();
                if(bDisplayText == "NUMBER"){
                    bDisplayText = String.valueOf(b.getBoxValue());
                }
                if(bDisplayText == "EMPTY"){
                    bDisplayText = "";
                }
                Text t = new Text(bDisplayText);
                
                t.setWrappingWidth(70);
                t.setFont(Font.font ("Verdana", 20));
                t.setFill(Color.WHITE);
                t.setTextAlignment(TextAlignment.CENTER);*/
                try {
                    String bDisplayText = b.getBoxType().name();
                    int numberOfCloseMines = b.getBoxValue();
                    
                    Button button = new Button();
                    button.setMinHeight(MIN_HEIGHT_BOX);
                    button.setMinWidth(MIN_WIDTH_BOX);
                    
                    switch(bDisplayText){
                        case "NUMBER" :
                            System.out.println(bDisplayText + " : " + numberOfCloseMines);
                            button.setStyle(" -fx-background-image: url('/ressources/" + numberOfCloseMines + ".png')");
                            break;
                        case "EMPTY" :
                            System.out.println("EMPTY");
                            //button.setStyle(" -fx-background-image: url('/ressources/flag.png')");
                            break;
                        case "MINE" :
                            System.out.println("EMPTY");
                            button.setStyle(" -fx-background-image: url('/ressources/mine.png')");
                            break;
                        default :
                            System.out.println("BUG switch");
                            break;
                    }
                    
                    //button.setStyle("-fx-background-color: #3465A4; -fx-background-image: url('/ressources/flag.png')");  CUSTOM Style

                    gPane.add(button, i, j);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*Image image = new Image("http://icons.iconarchive.com/icons/eponas-deeway/colobrush/128/heart-2-icon.png");
                ImageView pic = new ImageView();
                pic.setImage(image);
                Button button = new Button(bDisplayText, pic);
                gPane.add(button, i, j);*/
                
                
            }
            
            
        }
                        
        gPane.setGridLinesVisible(true);
        
        border.setCenter(gPane);
        
        Scene scene = new Scene(border, Color.rgb(52,101,164));
        
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
}