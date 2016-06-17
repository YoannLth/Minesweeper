/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper_controler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import minesweeper_model.Box;
import minesweeper_model.BoxType;
import minesweeper_view.MinesweeperView;

/**
 *
 * @author Yoann
 */
public class BoxControler implements EventHandler<MouseEvent> {

    private final minesweeper_model.Minesweeper minesweeperModel; // Lien entre le modèle et le controlleur
    private final MinesweeperView principalView;  // lien entre le modèle et la vue
    private final Box box; // case de la grille

    public BoxControler(minesweeper_model.Minesweeper m, MinesweeperView pv, Box b) {
        this.minesweeperModel = m;
        this.principalView = pv;
        this.box = b;
    }

    /**
     * Méthode qui prend en paramètre un evenement sourie et qui execute le
     * traitement en fonction du clic
     *
     * @param event evenement reçu par la case
     */
    @Override
    public void handle(MouseEvent event) {

        if (event.getButton() == MouseButton.SECONDARY) { //Si l'evenement est un clic droit
            if ((box.getIsFlagged() == 0) || (box.getIsFlagged() == 1)) { //si il n'y jamais eu de drapeau sur la case
                box.setFlagged(-1);
            } else {
                box.setFlagged(1);
            }
        } 
        else // Si l'evenement est un clic gauche 
        {
            if (box.getIsDiscovered() == 0) { // Si la case n'est pas découverte
                box.setDiscovered(); // On la rend visible

                if (box.getBoxType() == BoxType.MINE)// Si la case est une mine (on à perdu)
                {
                    minesweeperModel.getGrid().showAll(); // On rend toutes les cases visibles
                    try {
                        principalView.endGame(false); // On lance la procédure de fin du jeu (défaite)
                    } catch (Exception ex) {
                        Logger.getLogger(MinesweeperView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    if (box.getBoxType() == BoxType.EMPTY) { // Si la case est vide
                        minesweeperModel.getGrid().showEmptyBox(box); // On révele les cases adjacentes (nombres et vides) visibles
                    }

                    if (minesweeperModel.getGrid().isGameInProgress()) { // Si la partie est gagnée
                        minesweeperModel.getGrid().showAll(); // On rend toutes les cases visibles
                        try {
                            principalView.endGame(true); // On lance la procédure de fin du jeu (victoire)
                        } catch (Exception ex) {
                            Logger.getLogger(MinesweeperView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
}
