/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_view;

import java.util.HashMap;
import javafx.scene.control.Button;
import minesweeper_model.Box;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import minesweeper_model.Grid;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper_model.BoxType;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author Yoann
 */
public class Minesweeper extends Application implements Observer {

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

        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();

        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();

        // création des bouton et placement dans la grille
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
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

                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getButton() == MouseButton.SECONDARY) {
                                if (b.getIsFlagged() == 0) {
                                    b.setFlagged(-1);
                                } else {
                                    b.setFlagged(1);
                                }
                            } else {
                                b.setDiscovered();
                                g.showEmptyBox(b);
                                //To begin, check if the case is hidden. If it's not, uselss to do the job.
                                if (b.getIsDiscovered() != 0) {

                                //if(event.getEventType() == MouseButton.PRIMARY)//Then on Left click
                                    //{
                                    if (b.getBoxType() == BoxType.MINE)//If it's a mine
                                    {
                                        g.showAll();//We put all the case visible
                                        try {
                                            endGame(false);//End of the game, defeat
                                        } catch (Exception ex) {
                                            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        return;
                                    }
                                    //We call the function to show all the neighborhood
                                    //this.c.showCases(c, new ArrayList<Case>());
                                    //If the game is finished
                                    if (g.isGameFinished()) {
                                        //Victory
                                        g.showAll();
                                        try {
                                            endGame(true);
                                        } catch (Exception ex) {
                                            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
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

        Scene scene = new Scene(border, Color.rgb(52, 101, 164));

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();

        g.toString();

    }

    @Override
    public void update(Observable o, Object arg) {
        Box b = (Box) o;
        Button but = (Button) boxAndButton.get(b);

        String bDisplayText = b.getBoxType().name();
        int numberOfCloseMines = b.getBoxValue();

        if (b.getIsFlagged() == -1) {
            but.setStyle("-fx-background-color: #E05353; -fx-background-image: url('/ressources/flag.png')");
        }
        if (b.getIsFlagged() == 1) {
            but.setStyle("");
        }

        if (b.getIsDiscovered() == 1) {
            if (b.getIsFlagged() == -1) {
                switch (bDisplayText) {
                    case "NUMBER":
                        but.setStyle("-fx-background-color: #53E080; -fx-background-image: url('/ressources/" + numberOfCloseMines + ".png')");
                        break;
                    case "EMPTY":
                        but.setStyle("-fx-background-color: #53E080;");
                        break;
                    case "MINE":
                        but.setStyle("-fx-background-color: #53E080; -fx-background-image: url('/ressources/mine.png')");
                        break;
                    default:
                        System.out.println("BUG switch");
                        break;
                }

            } else {
                switch (bDisplayText) {
                    case "NUMBER":
                        but.setStyle("-fx-background-color: #3465A4; -fx-background-image: url('/ressources/" + numberOfCloseMines + ".png')");
                        break;
                    case "EMPTY":
                        but.setStyle("-fx-background-color: #3465A4;");
                        break;
                    case "MINE":
                        but.setStyle("-fx-background-color: #3465A4; -fx-background-image: url('/ressources/mine.png')");
                        break;
                    default:
                        System.out.println("BUG switch");
                        break;
                }
            }

            //System.out.println("CLICK RECEIVED at [" + b.getPosX() + "][" + b.getPosY() + "]" );
        } else {
            //System.out.println("BUTTON EVER CLICKED");
        }

    }

    public void endGame(boolean victory) throws Exception {
        if (victory) {
            Alert end = new Alert(Alert.AlertType.INFORMATION, "YOU WIN!!!", ButtonType.YES, ButtonType.NO);
            end.setTitle("VICTORY");
            end.setHeaderText("Field is successfully cleared!");
            end.setContentText("Again?");
            end.showAndWait();
            if (end.getResult() == ButtonType.YES) {
                this.start(primaryStage);
            } else {
                primaryStage.close();
            }
        } else {
            Alert end = new Alert(Alert.AlertType.ERROR, "GAME OVER! YOU FIND A MINE!", ButtonType.YES, ButtonType.NO);
            end.setTitle("GAME OVER");
            end.setHeaderText("GAME OVER! YOU FIND A MINE!");
            end.setContentText("Retry?");
            end.showAndWait();
            if (end.getResult() == ButtonType.YES) {
                //minesweeperModel.getGrid().initGrid();
                this.start(primaryStage);
            } /*else if(end.getResult().getButtonData() == ButtonBar.ButtonData.OTHER)
             {
             this.gridGame = new Grid(this.gridGame.WIDTH_GRID, this.gridGame.HEIGHT_GRID, this.gridGame.MINE_NUMBER);
             paintGrid(border);
             }*/ else {
                primaryStage.close();
            }
        }
    }
}
