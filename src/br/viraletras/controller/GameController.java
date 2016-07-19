package br.viraletras.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Roland on 7/14/16.
 */
public interface GameController {
    void newChatMessage(String s);

    void setOpponentName(String s);

    void notifyViewConnectionLost();

    void createBoardPiecesAndCallPopulate(String randomPiecesString);

    void flipPieceAt(int position);


//    void createLaunchWindow();
//    void getPlayerNameAndConnectionDetails();
//    void createConnection();
//
//    void addListerners();
//       void addListenersToControlPanel();
//       void addListenerToBoard();



}
