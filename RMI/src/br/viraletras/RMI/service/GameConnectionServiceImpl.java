package br.viraletras.RMI.service;

import br.viraletras.RMI.controller.GameControllerImpl;
import br.viraletras.RMI.model.GameState;
import br.viraletras.RMI.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Created by Roland on 7/16/16.
 */
public class GameConnectionServiceImpl extends Thread implements GameConnectionService {

    private int timeoutInSeconds;

    //Server
    public GameConnectionServiceImpl(int port, GameControllerImpl controller) throws IOException {
        this.port = port;
        this.controller = controller;
        this.timeoutInSeconds = 30;

        //TODO SOCKET
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000 * timeoutInSeconds);
        serverSocket.setReuseAddress(true);

        Utils.log("Aguardando conexão...");
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            serverSocket.close();
            throw e;
        }
        socket.setKeepAlive(true);
        //Todo

        Utils.log("Conexão Estabelecida." + String.valueOf(port));
        ostream = new DataOutputStream(socket.getOutputStream());
        istream = new DataInputStream(socket.getInputStream());
        this.start();
    }

    //Client
    public GameConnectionServiceImpl(String ip, int port, GameControllerImpl controller) throws IOException {
        this.ip = ip;
        this.port = port;
        this.controller = controller;

        socket = new Socket(ip, port);
        Utils.log("Conexão Estabelecida.");
        socket.setKeepAlive(true);
        ostream = new DataOutputStream(socket.getOutputStream());
        istream = new DataInputStream(socket.getInputStream());
        this.start();
    }


    public void run() {
        while (true) {
            try {
                MRcv = istream.readUTF();
                handleInputMessage(MRcv);
            } catch (Exception e) {
                Utils.log(e.getMessage());
                //TODO informar usuários de problema e fechar tela.
            }
        }
    }

    void handleInputMessage(String msg) {
        Utils.log(msg);
        splitMsg = msg.split(Codes.SPLIT_SIGNAL.toString());
        String teste = splitMsg[0];
        Codes code = Codes.getByCode(teste);

                try {
        switch (code) {
            case CHAT_MESSAGE:
                    controller.newChatMessage(splitMsg[1]);
                break;
            case OPPONENT_NAME:
                controller.setOpponentName(splitMsg[1]);
                break;
            case CONNECTION_LOST:
                controller.notifyConnectionLost(splitMsg[1]);
                break;
            case BOARD_PIECES:
                controller.createBoardPiecesAndPopulate(splitMsg[1]);
                break;
            case FLIP_PIECE:
                controller.flipPieceAt(Integer.valueOf(splitMsg[1]));
                break;
            case NOW_PLAYING:
                controller.updateGameState(GameState.NOW_PLAYING);
                break;
            case NOW_WAITING:
                controller.updateGameState(GameState.NOW_WAITING);
                break;
            case NOW_CONFIRMING_GUESS_WORD:
                controller.updateGameState(GameState.NOW_CONFIRMING_GUESS_WORD);
                break;
            case THROW_DICES:
                controller.updateGameState(GameState.THROW_DICES);
                break;
            case STARTUP_DICES_VALUE:
                controller.setOpponentStartUpDicesValue(Integer.valueOf(splitMsg[1]));
                break;
            case SHOW_DIALOG:
                controller.showDialog(splitMsg[1]);
                break;
        }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

    }

    private void notifyPeer(Codes msgType, String content) {
        try {
            String msg = msgType.toString() +
                    Codes.SPLIT_SIGNAL +
                    content;
            Utils.log(msg);

            ostream.writeUTF(msg);
            ostream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOpponentName(String name) {
        notifyPeer(Codes.OPPONENT_NAME, name);
    }

    @Override
    public void newChatMessage(String chatMessage) {
        notifyPeer(Codes.CHAT_MESSAGE, chatMessage);
    }

    @Override
    public void notifyConnectionLost(String dialog) {
        notifyPeer(Codes.CONNECTION_LOST, dialog);
    }

    @Override
    public void createBoardPiecesAndPopulate(String randomPiecesString) {
        notifyPeer(Codes.BOARD_PIECES, randomPiecesString);
    }

    @Override
    public void flipPieceAt(int position) {
        notifyPeer(Codes.FLIP_PIECE, String.valueOf(position));
    }

    @Override
    public void updateGameState(GameState gameState) {
        switch (gameState) {
            case NOW_PLAYING:
                notifyPeer(Codes.NOW_PLAYING, "");
                break;
            case NOW_WAITING:
                notifyPeer(Codes.NOW_WAITING, "");
                break;
            case NOW_CONFIRMING_GUESS_WORD:
                notifyPeer(Codes.NOW_CONFIRMING_GUESS_WORD, "");
                break;
            case THROW_DICES:
                notifyPeer(Codes.THROW_DICES, "");
                break;
        }
    }

    @Override
    public void setOpponentStartUpDicesValue(int thisPlayerValue) {
        notifyPeer(Codes.STARTUP_DICES_VALUE, String.valueOf(thisPlayerValue));
    }

    @Override
    public void showDialog(String message) {
        notifyPeer(Codes.SHOW_DIALOG, message);
    }


    enum Codes {
        SPLIT_SIGNAL("#S#"),

        CHAT_MESSAGE("CM"),
        OPPONENT_NAME("ON"),
        CONNECTION_LOST("CL"),
        BOARD_PIECES("BP"),
        FLIP_PIECE("FP"),
        HIDE_PIECE("HP"),
        NOW_PLAYING("NP"),
        NOW_WAITING("NW"),
        NOW_CONFIRMING_GUESS_WORD("CW"),
        THROW_DICES("TD"),
        STARTUP_DICES_VALUE("SDV"),
        SHOW_DIALOG("SWD");

        String code;


        Codes(String code) {
            this.code = code;
        }

        public String toString() {
            return code;
        }

        public static Codes getByCode(String name) {
            for (Codes prop : values()) {
                if (prop.toString().equals(name)) {
                    return prop;
                }
            }
            throw new IllegalArgumentException(name + " is not a valid PropName");
        }
    }


    GameConnectionService controller;
    Codes code;
    static int port;
    static String ip = "";
    static String MRcv = "";
    String MSnd = "";
    ServerSocket serverSocket = null;
    Socket socket = null;
    static DataOutputStream ostream = null;
    DataInputStream istream = null;
    String[] splitMsg = {};

}
