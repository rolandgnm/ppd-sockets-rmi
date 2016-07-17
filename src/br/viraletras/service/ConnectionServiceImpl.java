package br.viraletras.service;

import br.viraletras.controller.GameController;
import br.viraletras.controller.GameControllerImpl;

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
        print("Aguardando conexão...");
        socket = serverSocket.accept();
        socket.setKeepAlive(true);
        print("Conexão Estabelecida." + String.valueOf(port));
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
        print("Conexão Estabelecida.");
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
                print(e.getMessage());
            }
        }
    }

    void handleInputMessage(String msg) {
        print(msg);
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
        }

    }

    @Override
    public void sendThisPlayerName(String name) {
        try {
            String msg = Codes.OPPONENT_NAME.toString() +
                    Codes.SPLIT_SIGNAL +
                    name;
            print(msg);

            ostream.writeUTF(msg);
            ostream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNewMessage(String text) {
        try {
            String msg = Codes.CHAT_MESSAGE.toString() +
                    Codes.SPLIT_SIGNAL.toString() +
                    text;
            print(msg);
            ostream.writeUTF(msg);
            ostream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void print(String s) {
        System.out.printf(s + "\n");
    }


    //    enum Codes {
//        SS  ("SPLIT_SIGNAL"),
//        CM  ("CHAT_MESSAGE"),
//        ON  ("OPPONENT_NAME"),
//        BP  ("BOARD_PIECES"),
//        NP  ("NOW_PLAYING"),
//        SP  ("SHOW_PIECE"),
//        HP  ("HIDE_PIECE"),
//        ;
//        String code;
//
//
//        Codes(String code) {
//            this.code = code;
//        }
//
//        public String toString() {
//            return code;
//        }
//    }
    enum Codes {
        SPLIT_SIGNAL("#S#"),
        CHAT_MESSAGE("CM"),
        OPPONENT_NAME("ON"),
        BOARD_PIECES("BP"),
        NOW_PLAYING("NP"),
        SHOW_PIECE("SP"),
        HIDE_PIECE("HP");

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
