package br.viraletras.RMI.service;

import br.viraletras.RMI.model.GameState;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Roland on 7/14/16.
 */
public interface GameConnectionService extends Remote {

    void newChatMessage(String chatMessage) throws RemoteException;
    void setOpponentName(String name) throws RemoteException;
    void notifyConnectionLost(String msg) throws RemoteException;
    void createBoardPiecesAndPopulate(String randomPiecesString) throws RemoteException;
    void flipPieceAt(int position) throws RemoteException;
    void updateGameState(GameState gameState) throws RemoteException;
    void setOpponentStartUpDicesValue(int thisPlayerValue) throws RemoteException;
    void showDialog(String message) throws RemoteException;

    void test() throws RemoteException;

    void establishTwoWayBind(String peerName) throws RemoteException, NotBoundException;
}
