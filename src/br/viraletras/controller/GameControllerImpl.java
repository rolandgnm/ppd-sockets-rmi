package br.viraletras.controller;

import br.viraletras.model.GameModel;
import br.viraletras.view.BoardPanelExtended;
import br.viraletras.view.ControlPanelExtended;
import br.viraletras.view.GameFrameExtended;

import java.awt.event.*;

/**
 * Created by Roland on 7/15/16.
 */
//todo retirar abstract
public abstract class GameControllerImpl implements GameController {

    private GameModel gameModel;
    private GameFrameExtended gameWindow;
    private BoardPanelExtended viewBoard;
    private ControlPanelExtended viewControl;

    class WordGuessListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class ConfirmButtonListener  implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class RejectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class ChatInputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class PieceMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //                setPieceShowAt( (int)
//                        ((JComponent) e.getSource())
//                            .getClientProperty(propertyField));
                //todo usar if pra desabilitar ou não labels
                if(true) viewBoard.setPiecesEnabled(false);

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}
