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

import javax.rmi.CORBA.Util;
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
import java.util.ArrayList;

/**
 * Created by Roland on 7/15/16.
 */
public class GameControllerImpl implements GameConnectionService {
    private final String LOCALHOST = "localhost";
    boolean IS_SERVER = false;
    private String NAME_SERVER_IP;
    private GameState gameState;
    private GameModel gameModel;
    private ConnectionDetailsView viewCD;
    private GameFrameExtended gameWindow;
    private BoardPanelExtended viewBoard;
    private ControlPanelExtended viewControl;

    private String thisNameNS;
    private GameConnectionService thisStub;
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

    @Override
    public void updateBoard(ArrayList<Integer> positionsToSetGone, ArrayList<Integer> positionsToSetHidden) {
        if (positionsToSetGone != null) {
            for (int position : positionsToSetGone) {
                viewBoard.setPieceGoneAt(position);
                gameModel.setPieceGoneAt(position);
            }

            for (int position : positionsToSetHidden) {
                viewBoard.setPieceHiddenAt(position);
                gameModel.setPieceHiddenAt(position);
            }
        }

    }

    /**
     * Connection Details View listener
     */
    private class ConnectDetailsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setConnectionDetailsAndTryToConnect();

        }
    }
    //END

    /**
     * Connection setup methods
     */
    private void setConnectionDetailsAndTryToConnect() {
        viewCD.setEnabled(false);

        /**
         * Define se esta intância será servidor ou não
         * Se True: se registrará primeiro no NS
         * E tomará algumas decisões no jogo.
         */

        IS_SERVER = viewCD.cbIsServer();
        Utils.log("IS_SERVER: " + String.valueOf(IS_SERVER));

        //Seta Nome local
        String thisName = viewCD.getPlayerName().trim().isEmpty() ?
                (IS_SERVER ? "Servidor" : "Cliente") :
                viewCD.getPlayerName().trim();
        gameModel.getPlayerThis().setName(thisName);

        if (!IS_SERVER) {
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

    private boolean establishConnection() {
        /*Todo PRA DEPOIS: String ipToRegistry*/
        NAME_SERVER_IP = viewCD.getIP().isEmpty() ? LOCALHOST : viewCD.getIP();

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

        } catch (AlreadyBoundException abE) {
            showDialog("\"" + gameModel.getPlayerThis().getName() + "\"" +
                    " já registrado no servidor de nomes! Por favor, mude seu nome e tente novamente!");
            return false;
        } catch (UnknownHostException uhE) {
            showDialog("Oponente não encontrado!");
            Utils.log("!!! OPONENTE NÃO ENCONTRADO: " + this.serverPeerNS + " !!!");
            Utils.log(uhE.toString());
            return false;
        } catch (NotBoundException nbE) {
            showDialog("Oponente não encontrado!");
            Utils.log("!!! OPONENTE NÃO ENCONTRADO: " + this.serverPeerNS + " !!!");
            Utils.log(nbE.toString());
            return false;
        } catch (ExportException eeE) {
            showDialog("Stub já registrado!");
            Utils.log("!!! STUB JÁ REGISTRADO: " + this.thisNameNS + " !!!");
            Utils.log(eeE.toString());
            return false;
        } catch (Exception e) {
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
        if (thisStub == null) {
            thisStub = (GameConnectionService) UnicastRemoteObject.exportObject(this, 0);
        }
        Utils.log("### Tentando registrar no NS:" + this.thisNameNS);
        this.srvRegistry.bind(thisNameNS, thisStub);
        Utils.log("### SERVIDOR PRONTO PARA CONEXÃO ###\n" +
                "REGISTRO: " + thisNameNS);
    }

    private void locateRegistry() throws RemoteException {
        srvRegistry = LocateRegistry.getRegistry(NAME_SERVER_IP);
    }
    //END

    private void handleStartUpThrowDiceEvent() {
        int thisPlayerValue = gameModel.throwDices();
        gameModel.setThisPlayerStartUpDicesValue(thisPlayerValue);
        viewControl.setDicesText(thisPlayerValue);
        newBroadcastChatMessage(gameModel.getPlayerThis().getName() +
                " tirou " +
                thisPlayerValue +
                " nos dados!");

        if (IS_SERVER) {
            if (waitingForOpponentDicesValue()) {
                String s = "### ESPERANDO DADOS DO OPONENETE ###";
                Utils.log(s);
                newChatMessage(s);
            } else
                try {
                    serverHandleWhoStarts();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

        } else {
            //If client just send dices value to server.
            try {

                peerStub.setOpponentStartUpDicesValue(thisPlayerValue);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


    }

    public void serverHandleWhoStarts() throws RemoteException {
        /**
         * todo tratar caso de empate!
         */
        String starterPlayer, message, thisName;

        gameModel.setGameInProgress(true);
        peerStub.setGameInProgress(true);

        if (IS_SERVER) {
            starterPlayer = gameModel.getStarterPlayer();
            thisName = gameModel.getPlayerThis().getName();
            message = starterPlayer + " começa jogando!";

            if (starterPlayer.equals(thisName)) {
                // TODO Trabalhar consistência do algoritmo
                updateGameState(GameState.THROW_DICES);
                peerStub.updateGameState(GameState.NOW_WAITING);

            } else {
                updateGameState(GameState.NOW_WAITING);
                peerStub.updateGameState(GameState.THROW_DICES);
            }
            setDicesForThisRound(0);
            peerStub.setDicesForThisRound(0);
            newBroadcastChatMessage(message);
        }
    }

    private class WordGuessListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            //TODO LISTENER DO WORDGUESS
            //                //todo resolver characteres possíveis na Controller
//                String letters = "abc";
////                tfWordGuess.getText();
//
//                if (!letters.contains(String.valueOf(e.getKeyChar()))) {
//                    e.setKeyChar(Character.MIN_VALUE);
//                }

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * CONFIRM BUTTON
     */
    private class ConfirmButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameModel.isGameInProgress()) {
                switch (gameState) {
                    case THROW_DICES:
                        handleNewGameRound();
                        break;
                    case NOW_PLAYING:
                        sendGuessWordToBeConfirmed();
                        break;
                    case NOW_CONFIRMING_GUESS_WORD:
                        handleWordGuessConfirmation(true);
                }
            } else {
                switch (gameState) {
                    case THROW_DICES:
                        updateGameState(GameState.NOW_WAITING);
                        handleStartUpThrowDiceEvent();
                        break;
                }
            }

        }
    }

    private void handleWordGuessConfirmation(boolean wasConfirmed) {
        ArrayList<Integer> positionsToSetGone, positionsToSetHidden;
        int scores = 0;

        if (wasConfirmed) {
            positionsToSetGone = gameModel.getGonePositionsList(viewControl.getWordGuess());
            positionsToSetHidden = gameModel.getRemainingPositionsList();

            scores = positionsToSetGone.size();

            try {
                //Atualiza tabuleiro dos 2 lados.
                updateBoard(positionsToSetGone, positionsToSetHidden);
                peerStub.updateBoard(positionsToSetGone, positionsToSetHidden);

                //Manda msg pros 2 com os pontos ganhos.
                newBroadcastChatMessage(
                        gameModel.getPlayerOpponent().getName() + " marcou " + scores + " pontos com a palavra: "
                                + viewControl.getWordGuess() + "!");

                //Atualiza Placar.
                updateScoreboard(gameModel.getPlayerThis().getInGameScore(),
                        gameModel.getPlayerOpponent().getInGameScore() + scores);
                peerStub.updateScoreboard(gameModel.getPlayerOpponent().getInGameScore(),
                        gameModel.getPlayerThis().getInGameScore());

                //Configura novo round

                switchCurrentPlayer();
                peerStub.switchCurrentPlayer();

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            try {
                ArrayList<Integer> empty = new ArrayList<>();
                positionsToSetHidden = gameModel.getRemainingPositionsList();

                updateBoard(empty, positionsToSetHidden);
                peerStub.updateBoard(empty, positionsToSetHidden);

                switchCurrentPlayer();
                peerStub.switchCurrentPlayer();

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    private void handleNewGameRound() {
        /**
         * Jogar dado, setar modelo
         * alimentar tela com valor do dado
         * permitir clique em peças do tabuleiro
         */
        int moves = gameModel.throwDices();
        viewControl.setDicesText(moves);
        try {
            peerStub.setDicesForThisRound(moves);
//            peerStub.updateGameState(GameState.NOW_WAITING);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updateGameState(GameState.NOW_PLAYING);
    }

    private boolean waitingForOpponentDicesValue() {
        return gameModel.getOpponentStartUpDicesValue() <= 0;
    }

    private class RejectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameModel.isGameInProgress()) {
                switch (gameState) {
                    case NOW_CONFIRMING_GUESS_WORD:
                        handleWordGuessConfirmation(false);
                        break;
                }
            }
        }
    }

    private class ChatInputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!viewControl.getChatMessageInput().trim().isEmpty()) {
                String msg = gameModel.getPlayerThis().getName() + ": " +
                        viewControl.getChatMessageInput();

                viewControl.addMessageToChatConsole(msg);
                viewControl.clearChatInputField();

                try {
                    peerStub.newChatMessage(msg);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private class PieceMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //todo usar if pra desabilitar ou não labels
            int position = (Integer) ((JLabel) e.getSource())
                    .getClientProperty(BoardPanelExtended.propertyField);
            Utils.log(String.valueOf(position));
            flipPieceAt(position);


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
            gameWindow.setVisible(false);
            try {
                peerStub.notifyConnectionLost(
                        gameModel.getPlayerThis().getName() +
                                " fechou a janela e a conexão foi perdida! Fim de jogo!");
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            unbindStub();
            System.exit(0);
        }
    }

    private void unbindStub() {
        try {
            Utils.log("### Tentativa de Unbind iniciada: ###");

            srvRegistry.unbind(thisNameNS);
            Utils.log("### " + thisNameNS + " unbound com sucesso ###");
        } catch (NotBoundException nbE) {
            Utils.log("!!! Registro já não existia. Unbind não realizado !!!");
        } catch (RemoteException e) {
            Utils.log(e.toString());
        } catch (NullPointerException e) {
            Utils.log("!!! Registro já não existia. Unbind não realizado !!!");
        }
    }

    @Override
    public void updateScoreboard(int thisPlayerScoreValue, int opponentPlayerScoreValue) {
        viewControl.setPlayerThisScore(thisPlayerScoreValue);
        gameModel.getPlayerThis().setInGameScores(thisPlayerScoreValue);

        viewControl.setPlayerOpponentScore(opponentPlayerScoreValue);
        gameModel.getPlayerOpponent().setInGameScores(opponentPlayerScoreValue);
    }

    @Override
    public void switchCurrentPlayer() {
        /**
         * Limpar campo de texto
         * *Avaliar se ainda há jogo
         * Trocar estado do jogo
         * Limpar Dados
         */
        viewControl.setWordGuess("");
        viewControl.setDicesText(0);

//        int count = gameModel.getGonePiecesCount();
        int count = 64;
        if (count >= gameModel.boardSize()) {
            handleGameEnd();
        } else {

            switch (gameState) {
                case NOW_CONFIRMING_GUESS_WORD:
                    updateGameState(GameState.THROW_DICES);
                case NOW_WAITING:
                    break;

            }
            gameModel.clearShowingPiecesList();
        }
    }

    private void handleGameEnd() {
        Integer thisPlayerScores = gameModel.getPlayerThis().getInGameScore(),
                opponentScores = gameModel.getPlayerOpponent().getInGameScore();
        String msg = "";

        updateGameState(GameState.NOW_WAITING);

        if (thisPlayerScores.equals(opponentScores)) {
            msg += "Deu empate!!\n";
        } else if (thisPlayerScores > opponentScores) {
            msg += "Você ganhou!\n";
        } else {
            msg += gameModel.getPlayerOpponent().getName() + " ganhou!\n";
        }

        msg += gameModel.getPlayerThis().getName() + " " +  thisPlayerScores + " x " +
                opponentScores + " " + gameModel.getPlayerOpponent().getName();

        showDialog(msg);
    }

    private void sendGuessWordToBeConfirmed() {
        /**
         * TODO: 01 Melhorar Validação
         * 1) Já mandar validado!
         *      apenas com letras a mostra no tabuleiro.
         */
        try {
            updateGameState(GameState.NOW_WAITING);
            peerStub.updateGameState(GameState.NOW_CONFIRMING_GUESS_WORD);
            peerStub.setCurrentGuessWord(viewControl.getWordGuess().trim());
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setCurrentGuessWord(String wordGuess) throws RemoteException {
        viewControl.setWordGuess(wordGuess);
    }


    @Override
    public void updateGameState(GameState gameState) {
        if (gameModel.isGameInProgress())
            viewControl.setMovesLabelText("movimentos");
        else
            viewControl.setMovesLabelText("Quem começa?");

        switch (gameState) {
            case NOW_PLAYING:
                this.gameState = GameState.NOW_PLAYING;
                viewControl.updateGameState(GameState.NOW_PLAYING);
                gameModel.updateGameState(GameState.NOW_PLAYING);
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
//                if(gameModel.isGameInProgress())
//                    viewControl.setMovesLabelVisible(false);
//                else
//                    viewControl.setMovesLabelVisible(true);


                gameModel.updateGameState(GameState.NOW_CONFIRMING_GUESS_WORD);
                break;

        }


    }

    @Override
    public void initGame() {
        gameModel.clearCurrentPlaying();
        viewCD.setVisible(false);
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
                newBroadcastChatMessage("DISPUTA DE DADOS DECIDIRÁ QUEM COMEÇA");

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void setGameInProgress(boolean b) throws RemoteException {
        gameModel.setGameInProgress(b);
    }

    @Override
    public void setDicesForThisRound(int moves) {
        gameModel.setDices(moves);
//        viewControl.setMovesLabelVisible(true);
        viewControl.setDicesText(moves);
    }

    private void newBroadcastChatMessage(String msg) {
        String s = "# → " + msg;
        newChatMessage(s);
        try {
            peerStub.newChatMessage(s);
        } catch (RemoteException e) {
            e.printStackTrace();
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
        unbindStub();
        System.exit(0);
    }

    @Override
    public void createBoardPiecesAndPopulate(String randomPiecesString) {
        gameModel.createRandomPieceVector(randomPiecesString);
        viewBoard.populatePieces(gameModel.getRandomPieceVector());
    }

    private void flipPieceAt(int position) {
        if (gameState.equals(GameState.NOW_PLAYING) && gameModel.hasAvailableMove()) {
            if (gameModel.isPieceHiddenAt(position)) {
                gameModel.flipPieceAt(position);
                viewBoard.setPieceShowAt(position);
                try {
                    peerStub.showPieceAt(position);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void showPieceAt(int position) {
        if (!gameState.equals(GameState.NOW_PLAYING)) {
            if (gameModel.isPieceHiddenAt(position)) {
                gameModel.flipPieceAt(position);
                viewBoard.setPieceShowAt(position);
            }
        }
    }

    @Override
    public void setOpponentStartUpDicesValue(int opponentPlayerValue) {
        int t, o;
        gameModel.setOpponentStartUpDicesValue(opponentPlayerValue);
        o = gameModel.getOpponentStartUpDicesValue();
        t = gameModel.getThisPlayerStartUpDicesValue();
        if (o > 0 && t > 0) {
            try {
                serverHandleWhoStarts();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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
        initGame();
        peerStub.initGame();

    }


}
