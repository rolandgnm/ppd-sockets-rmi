package br.viraletras.RMI.service;

import br.viraletras.RMI.model.GameState;

/**
 * Created by Roland on 7/14/16.
 */
public interface GameConnectionService {

    void newChatMessage(String chatMessage);
    void setOpponentName(String name);
    void notifyConnectionLost(String msg);
    void createBoardPiecesAndPopulate(String randomPiecesString);
    void flipPieceAt(int position);
    void updateGameState(GameState gameState);
    void setOpponentStartUpDicesValue(int thisPlayerValue);
    void showDialog(String message);
}
