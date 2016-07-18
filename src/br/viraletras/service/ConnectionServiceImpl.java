package br.viraletras.service;

import br.viraletras.controller.GameController;
import br.viraletras.controller.GameControllerImpl;
import br.viraletras.utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Roland on 7/16/16.
 */
public class ConnectionServiceImpl extends Thread implements ConnectionService {

    //Server
    public ConnectionServiceImpl(int port, GameControllerImpl controller) throws IOException {
        this.port = port;
        this.controller = controller;

        serverSocket = new ServerSocket(port);
        Utils.log("Aguardando conexão...");
        socket = serverSocket.accept();
        socket.setKeepAlive(true);
        Utils.log("Conexão Estabelecida." + String.valueOf(port));
        ostream = new DataOutputStream(socket.getOutputStream());
        istream = new DataInputStream(socket.getInputStream());
        this.start();
    }

    //Client
    public ConnectionServiceImpl(String ip, int port, GameControllerImpl controller) throws IOException {
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

        switch (code) {
            case CHAT_MESSAGE:
                controller.newChatMessage(splitMsg[1]);
                break;
            case OPPONENT_NAME:
                controller.setOpponentName(splitMsg[1]);
                break;
            case CONNECTION_LOST:
                controller.notifyViewConnectionLost();

        }

    }

    public void notifyPeer(Codes msgType, String content) {
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
    public void sendThisPlayerName(String name) {
        notifyPeer(Codes.OPPONENT_NAME, name);
    }

    @Override
    public void sendNewMessage(String text) {
        notifyPeer(Codes.CHAT_MESSAGE, text);
    }

    @Override
    public void notifyConnectionLost(String dialog) {
        notifyPeer(Codes.CONNECTION_LOST, dialog);
    }


    enum Codes {
        SPLIT_SIGNAL("#S#"),
        CHAT_MESSAGE("CM"),
        OPPONENT_NAME("ON"),
        CONNECTION_LOST("CL"),
        BOARD_PIECES("BP"),
        NOW_PLAYING("NP"),
        SHOW_PIECE("SP"),
        HIDE_PIECE("HP"),

        ;

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


    GameController controller;
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
