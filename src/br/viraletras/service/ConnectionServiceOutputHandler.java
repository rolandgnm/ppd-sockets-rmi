package br.viraletras.service;

import br.viraletras.model.GameState;

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
}
