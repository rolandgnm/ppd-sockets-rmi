package br.viraletras.controller;

import br.viraletras.model.GameModel;
import br.viraletras.model.Player;
import br.viraletras.service.ConnectionService;
import br.viraletras.service.ConnectionServiceImpl;
import br.viraletras.utils.Utils;
import br.viraletras.view.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Roland on 7/15/16.
 */
//todo retirar abstract
public class GameControllerImpl implements GameController {

    boolean IS_SERVER = false;
    private int DEFAULT_PORT = 9999;
    private GameModel gameModel;
    private ConnectionDetailsView viewCD;
    private GameFrameExtended gameWindow;
    private BoardPanelExtended viewBoard;
    private ControlPanelExtended viewControl;
    private ConnectionService serviceGame;
    private ConnectionService serviceChat;

    public GameControllerImpl() {
        gameModel = new GameModel(new Player(), new Player());
        viewCD = new ConnectionDetailsView();
        viewBoard = new BoardPanelExtended("A", new PieceMouseListener());
        viewControl = new ControlPanelExtended();
        gameWindow = new GameFrameExtended(viewBoard, viewControl);

        viewCD.setVisible(true);
        viewCD.addConnectionDetailsListener(new ConnectDetailsListener());
    }


    class WordGuessListener implements KeyListener {
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

    class ConfirmButtonListener implements ActionListener {

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
            String msg = gameModel.getPlayerThis().getName() + ": " +
                    viewControl.getChatMessageInput();

            viewControl.addMessageToChatConsole(msg);
            viewControl.clearChatInputField();

            serviceChat.sendNewMessage(msg);

        }
    }

    class PieceMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //                setPieceShowAt( (int)
//                        ((JComponent) e.getSource())
//                            .getClientProperty(propertyField));
            //todo usar if pra desabilitar ou não labels
            if (true) viewBoard.setPiecesEnabled(false);

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


    class ConnectDetailsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewCD.setEnabled(false);
            gameModel.getPlayerThis().setName(
                    viewCD.getPlayerName().isEmpty() ?
                            "Player 1" :
                            viewCD.getPlayerName()
            );
            IS_SERVER = viewCD.isServer();
            String ip = viewCD.getIp().trim();
            int port = viewCD.getPort().trim().isEmpty() ? 0 : Integer.valueOf(viewCD.getPort());

            if (!establishConnection(ip, port)) {
                viewCD.displayErrorMessage("Não foi possível conectar!");
                viewCD.setSetupButtonText("Conectar");
                viewCD.setEnabled(true);
                return;
            }

            initGame();

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
                        e.printStackTrace();
                    }
                }))
                        .start();

                serviceGame = new ConnectionServiceImpl(
                        port > 0 ? port : DEFAULT_PORT,
                        this);


            } else {
                // Manda tentativa conexão do cliente do chat pra outra thread
                (new Thread(() -> {
                    try {
                        serviceChat = new ConnectionServiceImpl(
                                ip.isEmpty() ? "localhost" : ip,
                                port > 0 ? port + 1 : DEFAULT_PORT + 1,
                                this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }))
                        .start();

                viewCD.setSetupButtonText("Conectando...");
                serviceGame = new ConnectionServiceImpl(
                        ip.isEmpty() ? "localhost" : ip,
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
        viewCD.setVisible(false);
        gameWindow.setVisible();
        viewCD.dispose();

        serviceGame.sendThisPlayerName(gameModel.getPlayerThis().getName());
        viewControl.addComponentListeners(
                new WordGuessListener(),
                new ConfirmButtonListener(),
                new RejectButtonListener(),
                new ChatInputListener(),
                new ChatInputListener()
        );


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
}
