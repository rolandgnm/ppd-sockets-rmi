package br.viraletras.RMI.controller;

import br.viraletras.RMI.model.GameModel;
import br.viraletras.RMI.model.GameState;
import br.viraletras.RMI.model.Player;
import br.viraletras.RMI.service.GameConnectionService;
import br.viraletras.RMI.utils.Utils;
import br.viraletras.RMI.view.BoardPanelExtended;
import br.viraletras.RMI.view.ConnectionDetailsView;
import br.viraletras.RMI.view.ControlPanelExtended;
import br.viraletras.RMI.view.GameFrameExtended;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Roland on 7/15/16.
 */
public class GameControllerImpl implements GameConnectionService {
    private String thisNameNS;
    private GameConnectionService thisStub;
    private String registryIp;
    private Registry srvRegistry;
    private GameConnectionService peerStub;
    private String serverPeerNS;

    // Variables declared at end of file.

    public GameControllerImpl() {
        gameModel = new GameModel(new Player(), new Player());
        viewCD = new ConnectionDetailsView();
        viewBoard = new BoardPanelExtended("A", new PieceMouseListener());
        viewControl = new ControlPanelExtended();
        gameWindow = new GameFrameExtended(viewBoard, viewControl);

        addListenersToViews();

        //Próximo passo: Mostrar tela de detalhes -> Aguardar ConnectDetailsListener
        viewCD.setVisible(true);
    }

    public void addListenersToViews() {
        viewCD.addConnectionDetailsListener(new ConnectDetailsListener());
        viewCD.addWindowListener(new GameWindowClosedListener());
        gameWindow.addGameWindowListener(new GameWindowClosedListener());

        //Board Listener set via constructor;
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
            setConnectionDetailsAndTryToConnect();

        }
    }

    private void setConnectionDetailsAndTryToConnect() {
        viewCD.setEnabled(false);

        /**
         * Define se esta intância será servidor ou não
         * Se True: se registrará primeiro no NS
         * E tomará algumas decisões no jogo.
         */

        IS_SERVER = viewCD.cbIsServer();
        Utils.log("IS_SERVER: "+ String.valueOf(IS_SERVER));

        //Seta Nome local
        String thisName = viewCD.getPlayerName().trim().isEmpty() ?
                (IS_SERVER ? "Servidor" : "Cliente") :
                viewCD.getPlayerName().trim();
        gameModel.getPlayerThis().setName(thisName);

        if(!IS_SERVER){
            String opponentName = viewCD.getServerName().trim().isEmpty() ?
                    (IS_SERVER ? "Cliente" : "Servidor") :
                    viewCD.getServerName().trim();
            gameModel.getPlayerOpponent().setName(opponentName);
        }


        //Tenta Conectar
        if (!establishConnection()) {
            viewCD.setSetupButtonText("Conectar");
            viewCD.setEnabled(true);
            return;
        }

        gameWindow.setTitle("Vira Letras - " +
                gameModel.getPlayerThis().getName());

        /**
         * initGame() Retirado daqui para seguir a seguinte ordem:
         * 1) Server se registra
         * 2) Client localiza e pega stub
         * 3) Client invoca Server
         * 4) Server localiza e fecha o 2 way Bind
         * 5) Server invoca próxima view!
         */



    }

    private boolean establishConnection(/*Todo PRA DEPOIS: String ipToRegistry*/) {
//        registryIp = serverName.isEmpty() ? LOCALHOST : serverName;
        registryIp = LOCALHOST;

        try {
            if (IS_SERVER) {
                this.thisNameNS = "Server/" + gameModel.getPlayerThis().getName();
                
                /**
                 * Registra stub no rmi registry.
                 */
                createStubAndRegister();
                
                viewCD.setSetupButtonText("Esperando...");

                /**
                 * Em caso de sucesso já poderá responder a chamadas remotas.
                 * Prí
                 */


            } else {
                this.thisNameNS = "Client/" + gameModel.getPlayerThis().getName();
                this.serverPeerNS = "Server/" + gameModel.getPlayerOpponent().getName();
                viewCD.setSetupButtonText("Conectando...");

                locateStubAndBind();
                peerStub.test();

                /**
                 * Registra e invoca Stub pra fechar 2 way Bind
                 */
                createStubAndRegister();
                peerStub.establishTwoWayBind(gameModel.getPlayerThis().getName());

            }

        }
        catch (AlreadyBoundException abE){
            showDialog("\""+ gameModel.getPlayerThis().getName() + "\"" +
                " já registrado no servidor de nomes! Por favor, mude seu nome e tente novamente!");
            return false;
        }
        catch (UnknownHostException uhE) {
            showDialog("Oponente não encontrado!");
            Utils.log("!!! OPONENTE NÃO ENCONTRADO: " + this.serverPeerNS + " !!!");
            Utils.log(uhE.toString());
            return false;
        }
        catch (NotBoundException nbE) {
            showDialog("Oponente não encontrado!");
            Utils.log("!!! OPONENTE NÃO ENCONTRADO: " + this.serverPeerNS + " !!!");
            Utils.log(nbE.toString());
            return false;
        }
        catch (ExportException eeE){
            showDialog("Stub já registrado com o nome \"" + this.thisNameNS + "\"" );
            Utils.log("!!! STUB JÁ REGISTRADO: " + this.thisNameNS+ " !!!");
            Utils.log(eeE.toString());
            return false;
        }
        catch (Exception e) {
            showDialog("Falha na conexão remota!");
            Utils.log("!!! CONEXÃO NÃO ESTABELECIDA !!!");
            Utils.log(e.toString());
            return false;
        }

        return true;
    }

    private void locateStubAndBind() throws RemoteException, NotBoundException {
        /**
         *
         * todo: Receber STUB do server (Outbound)
         * todo: Registrar InBound e Invocar Server pra receber Stub
         *
         */

        locateRegistry();
        //TODO Receber Nome corretamente!!
        this.peerStub = (GameConnectionService) this.srvRegistry.lookup(this.serverPeerNS);

        Utils.log("### CONEXÃO CLIENTE -> SERVIDOR OK! ###");
    }

    private void createStubAndRegister() throws RemoteException, AlreadyBoundException {
        /**
         *    TODO: Criar stub
         *    TODO: Registrar no NS
         */
        locateRegistry();
        if(thisStub == null) {
            thisStub = (GameConnectionService) UnicastRemoteObject.exportObject(this, 0);
        }
        Utils.log("### Tentando registrar no NS:" +  this.thisNameNS);
        this.srvRegistry.bind(thisNameNS, thisStub);
        Utils.log("### SERVIDOR PRONTO PARA CONEXÃO ###\n" +
                "REGISTRO: " + thisNameNS);
    }

    private void locateRegistry() throws RemoteException {
        srvRegistry = LocateRegistry.getRegistry(registryIp);
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
//            serviceGame.setOpponentStartUpDicesValue(thisPlayerValue);
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
//                    serviceGame.showDialog(message);
                }).start();
//                serviceGame.updateGameState(GameState.NOW_WAITING);
                gameModel.throwDices();

            } else {
                message = gameModel.getPlayerOpponent().getName() + " começa jogando!";
                showDialog(message);
                new Thread(() -> {
//                    serviceGame.showDialog(message);
                }).start();
                updateGameState(GameState.NOW_WAITING);
//                serviceGame.updateGameState(GameState.NOW_PLAYING);
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
    public void setOpponentStartUpDicesValue(int thisPlayerValue) {
        int t, o;
        gameModel.setOpponentStartUpDicesValue(thisPlayerValue);
        o = gameModel.getOpponentStartUpDicesValue();
        t = gameModel.getThisPlayerStartUpDicesValue();
        if (o > 0 && t > 0)
            serverHandleWhoStarts();
    }

    @Override
    public void showDialog(String message) {
        Utils.displayDialog(gameWindow, message);
    }

    @Override
    public void test() {
        Utils.log("### TESTE: CONEXÃO OK ###");
    }

    @Override
    public void establishTwoWayBind(String peerName) throws RemoteException, NotBoundException {
        /**
         * Método rodado apenas quando Checkbox de Servidor foi habilitado!
         */
        this.serverPeerNS = "Client/" + peerName;
        locateStubAndBind();
        peerStub.test();


        /**
         * Estabelecida conexão de 2 vias
         */
//        initGame();

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

//                serviceChat.newChatMessage(msg);
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

//            if (gameState.equals(GameState.NOW_PLAYING))
//                serviceGame.flipPieceAt(position);


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
            /** TODO Notificar via RMI
             * serviceGame.notifyConnectionLost(
                    gameModel.getPlayerThis().getName() +
                            " fechou a janela! Fim de jogo!"
            );*/
            unbindStub();
            System.exit(0);
        }
    }

    private void unbindStub() {
        try {
            Utils.log("### Tentativa de Unbind iniciada: ###");

            srvRegistry.unbind(thisNameNS);
            Utils.log("### " + thisNameNS + " unbound com sucesso ###");
        } catch (NotBoundException nbE){
            Utils.log("!!! Registro já não existia. Unbind não realizado !!!");
        } catch (RemoteException e) {
            Utils.log(e.toString());
        } catch (NullPointerException e) {
            Utils.log("!!! Registro já não existia. Unbind não realizado !!!");
        }
    }

    private void initGame() {
        gameModel.clearCurrentPlaying();
        viewCD.setVisible(false);
        viewCD.dispose();
        gameWindow.setVisible();
        updateGameState(GameState.THROW_DICES);
        try {
            peerStub.setOpponentName(gameModel.getPlayerThis().getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (IS_SERVER) {
            gameModel.createRandomPieceVector();
            viewBoard.populatePieces(gameModel.getRandomPieceVector());
            try {
                peerStub.createBoardPiecesAndPopulate(gameModel.getRandomPieceVectorToString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void newChatMessage(String chatMessage) {
        Utils.log(chatMessage);
        viewControl.addMessageToChatConsole(chatMessage);
    }

    @Override
    public void setOpponentName(String s) {
        gameModel.getPlayerOpponent().setName(s);
    }

    @Override
    public void notifyConnectionLost(String msg) {
        gameWindow.setVisible(false);
        gameWindow.dispose();
        showDialog(msg);
        System.exit(0);
    }

    @Override
    public void createBoardPiecesAndPopulate(String randomPiecesString) {
            gameModel.createRandomPieceVector(randomPiecesString);
            viewBoard.populatePieces(gameModel.getRandomPieceVector());
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
    private GameConnectionService serviceGame;
    private GameConnectionService serviceChat;

}
