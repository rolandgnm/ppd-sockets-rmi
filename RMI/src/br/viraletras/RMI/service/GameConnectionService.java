package br.viraletras.RMI.service;

import br.viraletras.RMI.model.GameState;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Roland on 7/14/16.
 */
public interface GameConnectionService extends Remote {

    void newChatMessage(String chatMessage) throws RemoteException;
    void setOpponentName(String name) throws RemoteException;
    void notifyConnectionLost(String msg) throws RemoteException;
    void createBoardPiecesAndPopulate(String randomPiecesString) throws RemoteException;
//    void flipPieceAt(int position) throws RemoteException;
    void updateGameState(GameState gameState) throws RemoteException;

    void showPieceAt(int position) throws RemoteException;

    void setOpponentStartUpDicesValue(int opponentPlayerValue) throws RemoteException;
    void showDialog(String message) throws RemoteException;

    void test() throws RemoteException;

    void establishTwoWayBind(String peerName) throws RemoteException, NotBoundException;

    void initGame() throws RemoteException;

    void setGameInProgress(boolean b) throws RemoteException;

    void setDicesForThisRound(int moves) throws RemoteException;

    void setCurrentGuessWord(String wordGuess) throws RemoteException;

    void updateBoard(ArrayList<Integer> positionsToSetGone, ArrayList<Integer> positionsToSetHidden) throws RemoteException;

    void updateScoreboard(int thisPlayerScoreValue, int opponentPlayerScoreValue) throws RemoteException;

    void switchCurrentPlayer() throws RemoteException;
}
