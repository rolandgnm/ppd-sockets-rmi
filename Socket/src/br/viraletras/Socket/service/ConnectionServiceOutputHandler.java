package br.viraletras.Socket.service;

import br.viraletras.Socket.model.GameState;

/**
 * Created by Roland on 7/14/16.
 */
public interface ConnectionServiceOutputHandler {

    void sendThisPlayerName(String name);

    void sendNewMessage(String chatMessageInput);

    void notifyConnectionLost(String msg);

    void sendPieces(String randomPieceVectorToString);

    void notifyPieceFlipped(int position);

    void updateGameState(GameState nowWaiting);

    void sendStartUpDicesValue(int thisPlayerValue);


    void sendShowDialog(String message);
}
