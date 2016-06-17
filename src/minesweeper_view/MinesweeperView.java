/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_view;

import java.util.HashMap;
import javafx.scene.control.Button;
import minesweeper_model.Box;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import minesweeper_model.Grid;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import minesweeper_controler.BoxControler;

/**
 *
 * @author Yoann
 */
public class MinesweeperView extends Application implements Observer {

    private minesweeper_model.Minesweeper minesweeperModel; // Modèle du jeu
    private static final double MIN_HEIGHT_BOX = 64.0; // hauteur d'une case en px
    private static final double MIN_WIDTH_BOX = 64.0; // largeur d'une case en px
    private final HashMap boxAndButton = new HashMap(); // Hashmap qui lie les boutons et les cases
    private Stage primaryStage; // Vue principale

    @Override
    public void start(Stage primaryStage) throws Exception {

        minesweeperModel = new minesweeper_model.Minesweeper(); // Initialise le modèle
        this.primaryStage = primaryStage;

        Grid g = minesweeperModel.getGrid(); // Grille du modèle
        int column = g.getHeight();
        int row = g.getWidth();

        GridPane gPane = new GridPane(); // permet de placer les diffrents boutons dans une grille
        BorderPane border = new BorderPane();

        for (int i = 0; i < row; i++) { // Pour toute la largeur de la grille
            for (int j = 0; j < column; j++) { // Pour toute la hauteur de la grille
                Box b = g.getBox(i, j);
                BoxControler boxControler = new BoxControler(minesweeperModel, this, b);

                try {
                    Button button = new Button(); // On crée un bouton
                    button.setMinHeight(MIN_HEIGHT_BOX);
                    button.setMinWidth(MIN_WIDTH_BOX);

                    gPane.add(button, i, j);
                    b.addObserver(this);
                    boxAndButton.put(b, button);

                    button.setOnMouseClicked(boxControler);
                } catch (Exception e) {
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

        if (b.getIsFlagged() == -1) { // Si il y a un drapeau sur la case
            but.setStyle("-fx-background-color: #E05353; -fx-background-image: url('/ressources/flag.png')"); // On l'affiche
        }
        if (b.getIsFlagged() == 1) {
            but.setStyle("");
        }

        if (b.getIsDiscovered() == 1) { // Si la case est découverte
            if (b.getIsFlagged() == -1) { // Si il y a un de drapeau, on applique le style adéquat
                switch (bDisplayText) {
                    case "NUMBER":
                        but.setStyle("-fx-background-color: #3465A4; -fx-background-image: url('/ressources/" + numberOfCloseMines + ".png')");
                        break;
                    case "EMPTY":
                        but.setStyle("-fx-background-color: #3465A4;");
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
        }
    }

    /**
     * Fonction qui affiche un message de fin de partie
     *
     * @param victory 'true' si on à gagné, 'false' sinon
     * @throws Exception
     */
    public void endGame(boolean victory) throws Exception {
        if (victory) { // Si on à gagné
            Alert end = new Alert(Alert.AlertType.INFORMATION, "VICTOIRE", ButtonType.YES, ButtonType.NO);
            end.setTitle("VICTOIRE");
            end.setHeaderText("Grille complétée avec succes");
            end.setContentText("Rejouer?");
            end.showAndWait();
            if (end.getResult() == ButtonType.YES) {
                this.start(primaryStage);
            } else {
                primaryStage.close();
            }
        } else {
            Alert end = new Alert(Alert.AlertType.ERROR, "GAME OVER", ButtonType.YES, ButtonType.NO);
            end.setTitle("GAME OVER");
            end.setHeaderText("Perdu, vous êtes tombé sur une mine");
            end.setContentText("Rejouer?");
            end.showAndWait();
            if (end.getResult() == ButtonType.YES) {
                this.start(primaryStage);
            } else {
                primaryStage.close();
            }
        }
    }
}
