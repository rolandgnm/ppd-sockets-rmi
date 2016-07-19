/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

/**
 *
 * @author Roland
 */
public class ControlPanelExtended extends JPanel {

    public ControlPanelExtended() {
        initComponents();
        
    }

    private void initComponents() {

        initChildren();

        formatThisContainer();

        formatAndAddChildren();

        setModeThrowDices();

    }

    public void setModeThrowDices() {
        tfWordGuess.setEnabled(false);
        btConfirmWord.setEnabled(true);
            btConfirmWord.setText("Jogar dados!");
        btRejectWord.setEnabled(false);
    }

    public void setModeNowPlaying() {
        tfWordGuess.setEnabled(true);
        btConfirmWord.setEnabled(true);
            btConfirmWord.setText("Enviar");
        btRejectWord.setEnabled(false);
    }

    public void setModeNowWaiting(){
        tfWordGuess.setEnabled(false);
        btConfirmWord.setEnabled(false);
        btRejectWord.setEnabled(false);
    }

    public void setModeNowConfirming(){
        tfWordGuess.setEnabled(false);
        btConfirmWord.setEnabled(true);
            btConfirmWord.setText("Confirmar");
        btRejectWord.setEnabled(true);
            btRejectWord.setText("Rejeitar");
    }


    public void addComponentListeners(KeyListener wordGuessListener,
                                       ActionListener btConfirmListener,
                                       ActionListener btRejectListener,
                                       ActionListener tfChatInputListener,
                                       ActionListener btSendMessageListener
        ) {

        //Todo implementar sub-classes e descomentar aqui:
        tfWordGuess.addKeyListener(wordGuessListener);
        btConfirmWord.addActionListener(btConfirmListener);
        btRejectWord.addActionListener(btRejectListener);
        tfChatInput.addActionListener(tfChatInputListener);
        btSendMessage.addActionListener(btSendMessageListener);


//        //Todo A ser deletado quando implementar Listeners
//        tfWordGuess.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                //todo resolver characteres possíveis na Controller
//                String letters = "abc";
////                tfWordGuess.getText();
//
//                if (!letters.contains(String.valueOf(e.getKeyChar()))) {
//                    e.setKeyChar(Character.MIN_VALUE);
//                }
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//
//            }
//        });
//
//        btConfirmWord.addActionListener(evt -> {
//            btConfirmWordActionPerformed(evt);
//            addMessageToChatConsole("## Confirmado ##");
//            //todo aplicar esse codigo ao virar de uma peça
//            });
//
//        btRejectWord.addActionListener(evt -> {
//            btRejectWordActionPerformed(evt);
//            addMessageToChatConsole("## Rejeitado ##");
//            });
//
//        tfChatInput.addActionListener(evt -> {
//            tfChatInputActionPerformed(evt);
//            addMessageToChatConsole(tfChatInput.getText());
//            tfChatInput.setText("");
//            });
//
//        btSendMessage.addActionListener(evt -> {
//            //todo implementar controller/conn
//            btSendMessageActionPerformed(evt);
//            addMessageToChatConsole(tfChatInput.getText());
//            tfChatInput.setText("");
//            });

    }


/*
 *  Getter and Setters
*/

    public String getChatMessageInput() {
        return tfChatInput.getText();
    }

    public void setPlayerThisScore(int thisScore) {
        this.lbMyScore.setText(Integer.toString(thisScore));
    }

    public void setPlayerOpponentScore(int opponentScore) {
        this.lbOpponentScore.setText(Integer.toString(opponentScore));
    }

    public void addMessageToChatConsole(String message) {
        this.taChatConsole.setText(taChatConsole.getText()
                + "\n" + message);
    }

    public JLabel getLbDices() {
        return lbDices;
    }

    public void setLbDices(JLabel lbDices) {
        this.lbDices = lbDices;
    }

    private void formatAndAddChildren() {
        scorePanel.setBackground(new java.awt.Color(149, 165, 166));
        scorePanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        scorePanel.setMinimumSize(new java.awt.Dimension(350, 50));
        scorePanel.setPreferredSize(new java.awt.Dimension(350, 50));

        lbMyScore.setBackground(new java.awt.Color(127, 140, 141));
        lbMyScore.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbMyScore.setForeground(new java.awt.Color(255, 255, 255));
        lbMyScore.setText("0");
        lbMyScore.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lbMyScore.setOpaque(true);
        scorePanel.add(lbMyScore);

        lbVersusSymbol.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbVersusSymbol.setForeground(new java.awt.Color(255, 255, 255));
        lbVersusSymbol.setText("x");
        scorePanel.add(lbVersusSymbol);

        lbOpponentScore.setBackground(new java.awt.Color(127, 140, 141));
        lbOpponentScore.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbOpponentScore.setForeground(new java.awt.Color(255, 255, 255));
        lbOpponentScore.setText("0");
        lbOpponentScore.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lbOpponentScore.setOpaque(true);
        scorePanel.add(lbOpponentScore);

        add(scorePanel);

        centralPanel.setBackground(new java.awt.Color(236, 240, 241));
        centralPanel.setMinimumSize(new java.awt.Dimension(350, 320));
        centralPanel.setRequestFocusEnabled(false);
        centralPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        dicesContainerPanel.setLayout(new BoxLayout(dicesContainerPanel, BoxLayout.Y_AXIS));

        diceNumbersPanel.setBackground(new java.awt.Color(243, 156, 18));
        diceNumbersPanel.setBorder(BorderFactory.createEmptyBorder(1, 15, 1, 15));
        diceNumbersPanel.setLayout(new java.awt.GridLayout(1, 0, 25, 0));

        lbDices.setFont(new java.awt.Font("Lucida Grande", 1, 48)); // NOI18N
        lbDices.setForeground(new java.awt.Color(255, 255, 255));
        lbDices.setHorizontalAlignment(SwingConstants.CENTER);
        lbDices.setText("12");
        lbDices.setHorizontalTextPosition(SwingConstants.CENTER);
        diceNumbersPanel.add(lbDices);


        dicesContainerPanel.add(diceNumbersPanel);

        movesLabelPanel.setBackground(new java.awt.Color(230, 126, 34));
        movesLabelPanel.setBorder(BorderFactory.createEmptyBorder(2, 1, 5, 1));

        jLabel1.setBackground(new java.awt.Color(230, 126, 34));
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("movimentos");
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setOpaque(true);

        GroupLayout movesLabelPanelLayout = new GroupLayout(movesLabelPanel);
        movesLabelPanel.setLayout(movesLabelPanelLayout);
        movesLabelPanelLayout.setHorizontalGroup(
            movesLabelPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
            .addGroup(movesLabelPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(movesLabelPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        movesLabelPanelLayout.setVerticalGroup(
            movesLabelPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(movesLabelPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(movesLabelPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        dicesContainerPanel.add(movesLabelPanel);

        centralPanel.add(dicesContainerPanel);

        tfWordGuess.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        tfWordGuess.setHorizontalAlignment(JTextField.CENTER);
        tfWordGuess.setMinimumSize(new java.awt.Dimension(340, 26));
        tfWordGuess.setName("tfWordGuess"); // NOI18N
        tfWordGuess.setPreferredSize(new java.awt.Dimension(340, 60));
        centralPanel.add(tfWordGuess);

        btConfirmWord.setBackground(new java.awt.Color(39, 174, 96));
        btConfirmWord.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        btConfirmWord.setForeground(new java.awt.Color(255, 255, 255));
        btConfirmWord.setText("Confirmar");
        btConfirmWord.setBorderPainted(false);
        btConfirmWord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btConfirmWord.setFocusCycleRoot(true);
        btConfirmWord.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btConfirmWord.setMaximumSize(new java.awt.Dimension(340, 48));
        btConfirmWord.setMinimumSize(new java.awt.Dimension(340, 48));
        btConfirmWord.setPreferredSize(new java.awt.Dimension(340, 48));
        centralPanel.add(btConfirmWord);

        btRejectWord.setBackground(new java.awt.Color(192, 57, 43));
        btRejectWord.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        btRejectWord.setForeground(new java.awt.Color(255, 255, 255));
        btRejectWord.setText("Rejeitar");
        btRejectWord.setBorderPainted(false);
        btRejectWord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btRejectWord.setFocusCycleRoot(true);
        btRejectWord.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btRejectWord.setMaximumSize(new java.awt.Dimension(340, 48));
        btRejectWord.setMinimumSize(new java.awt.Dimension(340, 48));
        btRejectWord.setPreferredSize(new java.awt.Dimension(340, 48));
        centralPanel.add(btRejectWord);
        add(centralPanel);


        chatPanel.setBackground(new java.awt.Color(184, 203, 205));
        chatPanel.setMaximumSize(new java.awt.Dimension(32767, 340));
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.PAGE_AXIS));

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setHorizontalScrollBar(null);

        taChatConsole.setEditable(false);
        taChatConsole.setLineWrap(true);
        taChatConsole.setRows(15);
        taChatConsole.setText("Eventos e mensagens aparecerão aqui!");
        taChatConsole.setMaximumSize(new java.awt.Dimension(500, 32767));
        taChatConsole.setMinimumSize(new java.awt.Dimension(350, 330));
        jScrollPane1.setViewportView(taChatConsole);

        chatPanel.add(jScrollPane1);

        tfChatInput.setText("digite a mensagem...");
        tfChatInput.setName("tfChatInput"); // NOI18N
        chatPanel.add(tfChatInput);

        btSendMessage.setBackground(new java.awt.Color(52, 152, 219));
        btSendMessage.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btSendMessage.setForeground(new java.awt.Color(255, 255, 255));
        btSendMessage.setText("Enviar");
        btSendMessage.setAlignmentX(0.5F);
        btSendMessage.setBorderPainted(false);
        btSendMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSendMessage.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btSendMessage.setMaximumSize(new java.awt.Dimension(1000, 29));
        chatPanel.add(btSendMessage);
        add(chatPanel);
    }

    private void formatThisContainer() {
        setMaximumSize(new java.awt.Dimension(400, 800));
        setMinimumSize(new java.awt.Dimension(350, 700));
        setPreferredSize(new java.awt.Dimension(350, 650));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    private void initChildren() {
        scorePanel = new JPanel();
        lbMyScore = new JLabel();
        lbVersusSymbol = new JLabel();
        lbOpponentScore = new JLabel();
        centralPanel = new JPanel();
        dicesContainerPanel = new JPanel();
        diceNumbersPanel = new JPanel();
        lbDices = new JLabel();
        movesLabelPanel = new JPanel();
        jLabel1 = new JLabel();
        tfWordGuess = new JFormattedTextField();
        btConfirmWord = new JButton();
        btRejectWord = new JButton();
        chatPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        taChatConsole = new JTextArea();
        tfChatInput = new JTextField();
        btSendMessage = new JButton();
    }

    public void clearChatInputField() {
        tfChatInput.setText("");
    }

    private JButton btConfirmWord;
    private JButton btRejectWord;
    private JButton btSendMessage;

    private JLabel jLabel1;
    private JLabel lbMyScore;
    private JLabel lbOpponentScore;
    private JLabel lbDices;
    private JLabel lbVersusSymbol;

    private JPanel centralPanel;
    private JPanel chatPanel;
    private JPanel diceNumbersPanel;
    private JPanel dicesContainerPanel;
    private JPanel movesLabelPanel;
    private JPanel scorePanel;

    private JScrollPane jScrollPane1;


    private JTextArea taChatConsole;
    private JTextField tfChatInput;
    private JFormattedTextField tfWordGuess;
}
