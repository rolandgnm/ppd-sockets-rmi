package br.viraletras.Socket.controller;

import br.viraletras.Socket.model.GameModel;
import br.viraletras.Socket.model.GameState;
import br.viraletras.Socket.model.Player;
import br.viraletras.Socket.service.ConnectionServiceOutputHandler;
import br.viraletras.Socket.service.ConnectionServiceImpl;
import br.viraletras.Socket.utils.Utils;
import br.viraletras.Socket.view.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Roland on 7/15/16.
 */
public class GameControllerImpl implements GameControllerInputHandler {

    // Variables declared at end of file.

    public GameControllerImpl() {
        gameModel = new GameModel(new Player(), new Player());
        viewCD = new ConnectionDetailsView();
        viewBoard = new BoardPanelExtended("A", new PieceMouseListener());
        viewControl = new ControlPanelExtended();
        gameWindow = new GameFrameExtended(viewBoard, viewControl);

        addListenersToViews();
        viewCD.setVisible(true);
    }

    public void addListenersToViews() {
        viewCD.addConnectionDetailsListener(new ConnectDetailsListener());
        gameWindow.addGameWindowListener(new GameWindowClosedListener());
        //TODO Board Listener set via constructor;
        viewControl.addComponentListeners(
                new WordGuessListener(),
                new ConfirmButtonListener(),
                new RejectButtonListener(),
                new ChatInputListener(),
                new ChatInputListener()
        );

    }


    private class ConnectDetailsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            IS_SERVER = viewCD.isServer();
            viewCD.setEnabled(false);
            gameModel.getPlayerThis().setName(
                    viewCD.getPlayerName().isEmpty() ?
                            (IS_SERVER ? "Server" : "Client") :
                            viewCD.getPlayerName()
            );
            String ip = viewCD.getIp().trim();
            int port = viewCD.getPort().trim().isEmpty() ? 0 : Integer.valueOf(viewCD.getPort());

            if (!establishConnection(ip, port)) {
                showDialog("Não foi possível conectar!");
                viewCD.setSetupButtonText("Conectar");
                viewCD.setEnabled(true);
                return;
            }

            gameWindow.setTitle("Vira Letras - " +
                    gameModel.getPlayerThis().getName());

            initGame();

        }
    }

    private class WordGuessListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            //TODO LISTENER DO WORDGUESS
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ConfirmButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (gameState) {
                case THROW_DICES:
                    viewControl.setModeNowWaiting();
                    viewControl.setMovesLabelVisible(false);
                    handleStartUpThrowDiceEvent();
                    break;


            }

        }
    }

    private void handleStartUpThrowDiceEvent() {
        int thisPlayerValue = gameModel.throwDices();
        gameModel.setThisPlayerStartUpDicesValue(thisPlayerValue);
        viewControl.setDicesText(String.valueOf(thisPlayerValue));

        if (IS_SERVER) {
            if (waitingForOpponentDicesValue())
                Utils.log("Esperando valor do dado do oponente");
            else serverHandleWhoStarts();

        } else {
            //If client just send dices value to server.
            serviceGame.sendStartUpDicesValue(thisPlayerValue);
        }


    }

    public void serverHandleWhoStarts() {
        if (IS_SERVER) {
            String starterPlayer, message;
            starterPlayer = gameModel.getStarterPlayer();
            String thisName = gameModel.getPlayerThis().getName();
            if (starterPlayer.equals(thisName)) {
                updateGameState(GameState.NOW_PLAYING);
                message = thisName + " começa jogando!";
                showDialog(message);
                new Thread(() -> {
                    serviceGame.sendShowDialog(message);
                }).start();
                serviceGame.updateGameState(GameState.NOW_WAITING);
                gameModel.throwDices();

            } else {
                message = gameModel.getPlayerOpponent().getName() + " começa jogando!";
                showDialog(message);
                new Thread(() -> {
                    serviceGame.sendShowDialog(message);
                }).start();
                updateGameState(GameState.NOW_WAITING);
                serviceGame.updateGameState(GameState.NOW_PLAYING);
            }
        }
    }

    public void updateGameState(GameState gameState) {
        switch (gameState) {
            case NOW_PLAYING:
                this.gameState = GameState.NOW_PLAYING;
                viewControl.updateGameState(GameState.NOW_PLAYING);
                gameModel.updateGameState(GameState.NOW_PLAYING);
                viewControl.setDicesText(String.valueOf(gameModel.throwDices()));

                break;
            case NOW_WAITING:
                this.gameState = GameState.NOW_WAITING;
                viewControl.updateGameState(GameState.NOW_WAITING);
                gameModel.updateGameState(GameState.NOW_WAITING);
                break;
            case NOW_CONFIRMING_GUESS_WORD:
                this.gameState = GameState.NOW_CONFIRMING_GUESS_WORD;
                viewControl.updateGameState(GameState.NOW_CONFIRMING_GUESS_WORD);
                gameModel.updateGameState(GameState.NOW_CONFIRMING_GUESS_WORD);
                break;
            case THROW_DICES:
                this.gameState = GameState.THROW_DICES;
                viewControl.updateGameState(GameState.THROW_DICES);
                gameModel.updateGameState(GameState.NOW_CONFIRMING_GUESS_WORD);
                break;

        }


    }

    @Override
    public void setOpponentStartUpDicesValue(int dicesValue) {
        int t, o;
        gameModel.setOpponentStartUpDicesValue(dicesValue);
        o = gameModel.getOpponentStartUpDicesValue();
        t = gameModel.getThisPlayerStartUpDicesValue();
        if (o > 0 && t > 0)
            serverHandleWhoStarts();
    }

    @Override
    public void showDialog(String s) {
        Utils.displayDialog(gameWindow, s);
    }

    private boolean waitingForOpponentDicesValue() {
        return gameModel.getOpponentStartUpDicesValue() <= 0;
    }

    private class RejectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ChatInputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!viewControl.getChatMessageInput().isEmpty()) {
                String msg = gameModel.getPlayerThis().getName() + ": " +
                        viewControl.getChatMessageInput();

                viewControl.addMessageToChatConsole(msg);
                viewControl.clearChatInputField();

                serviceChat.sendNewMessage(msg);
            }

        }
    }

    private class PieceMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //                setPieceShowAt( (int)
//                        ((JComponent) e.getSource())
//                            .getClientProperty(propertyField));
            //todo usar if pra desabilitar ou não labels


            int position = (Integer) ((JLabel) e.getSource())
                    .getClientProperty(BoardPanelExtended.propertyField);
            flipPieceAt(position);

            if (gameState.equals(GameState.NOW_PLAYING))
                serviceGame.notifyPieceFlipped(position);


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

    private class GameWindowClosedListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            serviceGame.notifyConnectionLost(
                    gameModel.getPlayerThis().getName() +
                            " Fechou a janela! Fim de jogo!"
            );
            System.exit(0);
        }
    }

    private boolean establishConnection(String ip, int port) {
        try {
            if (IS_SERVER) {
                // Manda listener do SocketServer de chat pra outra thread
                (new Thread(() -> {
                    try {
                        serviceChat = new ConnectionServiceImpl(
                                port > 0 ? port + 1 : DEFAULT_PORT + 1,
                                this);
                    } catch (IOException e) {
                        e.getMessage();
                    }
                })).start();

                serviceGame = new ConnectionServiceImpl(
                        port > 0 ? port : DEFAULT_PORT,
                        this);


            } else {
                // Manda tentativa conexão do cliente do chat pra outra thread
                (new Thread(() -> {
                    try {
                        serviceChat = new ConnectionServiceImpl(
                                ip.isEmpty() ? LOCALHOST : ip,
                                port > 0 ? port + 1 : DEFAULT_PORT + 1,
                                this);
                    } catch (IOException e) {
                        e.getMessage();
                    }

                })).start();

                viewCD.setSetupButtonText("Conectando...");
                serviceGame = new ConnectionServiceImpl(
                        ip.isEmpty() ? LOCALHOST : ip,
                        port > 0 ? port : DEFAULT_PORT,
                        this);

            }
        } catch (IOException e1) {
            Utils.log(e1.toString());
            return false;
        }

        return true;
    }

    private void initGame() {
        gameModel.clearCurrentPlaying();
        viewCD.setVisible(false);
        viewCD.dispose();
        gameWindow.setVisible();
        updateGameState(GameState.THROW_DICES);
        serviceGame.sendThisPlayerName(gameModel.getPlayerThis().getName());

        if (IS_SERVER)
            (new Thread(() -> {
                gameModel.createRandomPieceVector();
                viewBoard.populatePieces(gameModel.getRandomPieceVector());
                serviceGame.sendPieces(gameModel.getRandomPieceVectorToString());

            })).start();

    }

    @Override
    public void newChatMessage(String s) {
        Utils.log(s);
        viewControl.addMessageToChatConsole(s);
    }

    @Override
    public void setOpponentName(String s) {
        gameModel.getPlayerOpponent().setName(s);
    }

    @Override
    public void notifyViewConnectionLost() {
        gameWindow.setVisible(false);
        gameWindow.dispose();
        showDialog("Conexão perdida!");
        System.exit(0);
    }

    @Override
    public void createBoardPiecesAndCallPopulate(String randomPiecesString) {
        (new Thread(() -> {
            gameModel.createRandomPieceVector(randomPiecesString);
            viewBoard.populatePieces(gameModel.getRandomPieceVector());
        })).start();

    }

    @Override
    public void flipPieceAt(int position) {
//        if(true)
        if (gameState.equals(GameState.NOW_PLAYING) && gameModel.hasAvailableMove() ||
                gameState.equals(GameState.NOW_WAITING)) {
            if (gameModel.isPieceHiddenAt(position)) {
                gameModel.flipPieceAt(position);
                viewBoard.setPieceShowAt(position);
            }
        }
    }

    private final String LOCALHOST = "localhost";
    boolean IS_SERVER = false;
    private GameState gameState;
    private final int DEFAULT_PORT = 9999;
    private GameModel gameModel;
    private ConnectionDetailsView viewCD;
    private GameFrameExtended gameWindow;
    private BoardPanelExtended viewBoard;
    private ControlPanelExtended viewControl;
    private ConnectionServiceOutputHandler serviceGame;
    private ConnectionServiceOutputHandler serviceChat;

}
