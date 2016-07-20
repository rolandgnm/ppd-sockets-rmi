package br.viraletras.controller;

import br.viraletras.model.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Roland on 7/14/16.
 */

/* *
 * This interface concentrates methods invoked by inputs
 * from network service to be handled by game controller.
 */

public interface GameControllerInputHandler {
    void newChatMessage(String s);

    void setOpponentName(String s);

    void notifyViewConnectionLost();

    void createBoardPiecesAndCallPopulate(String randomPiecesString);

    void flipPieceAt(int position);

    void updateGameState(GameState gameState);

    void setOpponentStartUpDicesValue(int dicesValue);


//    void createLaunchWindow();
//    void getPlayerNameAndConnectionDetails();
//    void createConnection();
//
//    void addListerners();
//       void addListenersToControlPanel();
//       void addListenerToBoard();


}
