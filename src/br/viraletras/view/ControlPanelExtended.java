/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Roland
 */
public class ControlPanelExtended extends javax.swing.JPanel {

    public ControlPanelExtended() {
        initComponents();
        
    }

    private void initComponents() {

        initChildren();

        formatThisContainer();

        formatAndAddChildren();

        //TODO addComponentListeners(); Feito pelo Controller

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

    public void setDice1(int d1 ) {
        this.lbDice1.setText(Integer.toString(d1));
    }

    public void setDice2(int d2) {
        this.lbDice2.setText(Integer.toString(d2));
    }

    public void setEnabledWordGuessTextArea(boolean enabled) {
        this.tfWordGuess.setEnabled(enabled);
    }

    public void setEnabledCorfirmWordGuessButton(boolean enabled) {
        this.btConfirmWord.setEnabled(enabled);
    }

    public void setEnabledRejectWordGuessButton(boolean enabled) {
        this.btRejectWord.setEnabled(enabled);
    }

    public void addMessageToChatConsole(String message) {
        this.taChatConsole.setText(taChatConsole.getText()
                + "\n" + message);
    }

    public void formatWordGuessRegex(String permittedChars) {
//        tfWordGuess.add

    }

    private void formatAndAddChildren() {
        scorePanel.setBackground(new java.awt.Color(149, 165, 166));
        scorePanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        scorePanel.setMinimumSize(new java.awt.Dimension(350, 50));
        scorePanel.setPreferredSize(new java.awt.Dimension(350, 50));

        lbMyScore.setBackground(new java.awt.Color(127, 140, 141));
        lbMyScore.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbMyScore.setForeground(new java.awt.Color(255, 255, 255));
        lbMyScore.setText("64");
        lbMyScore.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lbMyScore.setOpaque(true);
        scorePanel.add(lbMyScore);

        lbVersusSymbol.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbVersusSymbol.setForeground(new java.awt.Color(255, 255, 255));
        lbVersusSymbol.setText("x");
        scorePanel.add(lbVersusSymbol);

        lbOpponentScore.setBackground(new java.awt.Color(127, 140, 141));
        lbOpponentScore.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        lbOpponentScore.setForeground(new java.awt.Color(255, 255, 255));
        lbOpponentScore.setText("64");
        lbOpponentScore.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lbOpponentScore.setOpaque(true);
        scorePanel.add(lbOpponentScore);

        add(scorePanel);

        centralPanel.setBackground(new java.awt.Color(236, 240, 241));
        centralPanel.setMinimumSize(new java.awt.Dimension(350, 320));
        centralPanel.setRequestFocusEnabled(false);
        centralPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        dicesContainerPanel.setLayout(new javax.swing.BoxLayout(dicesContainerPanel, javax.swing.BoxLayout.Y_AXIS));

        diceNumbersPanel.setBackground(new java.awt.Color(243, 156, 18));
        diceNumbersPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 15));
        diceNumbersPanel.setLayout(new java.awt.GridLayout(1, 0, 25, 0));

        lbDice1.setFont(new java.awt.Font("Lucida Grande", 1, 48)); // NOI18N
        lbDice1.setForeground(new java.awt.Color(255, 255, 255));
        lbDice1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDice1.setText("6");
        lbDice1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diceNumbersPanel.add(lbDice1);

        lbPlusSymbol.setFont(new java.awt.Font("Lucida Grande", 1, 48)); // NOI18N
        lbPlusSymbol.setForeground(new java.awt.Color(255, 255, 255));
        lbPlusSymbol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPlusSymbol.setText("+");
        lbPlusSymbol.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diceNumbersPanel.add(lbPlusSymbol);

        lbDice2.setFont(new java.awt.Font("Lucida Grande", 1, 48)); // NOI18N
        lbDice2.setForeground(new java.awt.Color(255, 255, 255));
        lbDice2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDice2.setText("6");
        lbDice2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diceNumbersPanel.add(lbDice2);

        dicesContainerPanel.add(diceNumbersPanel);

        movesLabelPanel.setBackground(new java.awt.Color(230, 126, 34));
        movesLabelPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 1, 5, 1));

        jLabel1.setBackground(new java.awt.Color(230, 126, 34));
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("movimentos");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout movesLabelPanelLayout = new javax.swing.GroupLayout(movesLabelPanel);
        movesLabelPanel.setLayout(movesLabelPanelLayout);
        movesLabelPanelLayout.setHorizontalGroup(
            movesLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
            .addGroup(movesLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(movesLabelPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        movesLabelPanelLayout.setVerticalGroup(
            movesLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(movesLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(movesLabelPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        dicesContainerPanel.add(movesLabelPanel);

        centralPanel.add(dicesContainerPanel);

        tfWordGuess.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        tfWordGuess.setHorizontalAlignment(javax.swing.JTextField.CENTER);
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
        chatPanel.setLayout(new javax.swing.BoxLayout(chatPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
    }

    private void initChildren() {
        scorePanel = new javax.swing.JPanel();
        lbMyScore = new javax.swing.JLabel();
        lbVersusSymbol = new javax.swing.JLabel();
        lbOpponentScore = new javax.swing.JLabel();
        centralPanel = new javax.swing.JPanel();
        dicesContainerPanel = new javax.swing.JPanel();
        diceNumbersPanel = new javax.swing.JPanel();
        lbDice1 = new javax.swing.JLabel();
        lbPlusSymbol = new javax.swing.JLabel();
        lbDice2 = new javax.swing.JLabel();
        movesLabelPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfWordGuess = new JFormattedTextField();
        btConfirmWord = new javax.swing.JButton();
        btRejectWord = new javax.swing.JButton();
        chatPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taChatConsole = new javax.swing.JTextArea();
        tfChatInput = new javax.swing.JTextField();
        btSendMessage = new javax.swing.JButton();
    }

    public void clearChatInputField() {
        tfChatInput.setText("");
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btConfirmWord;
    private javax.swing.JButton btRejectWord;
    private javax.swing.JButton btSendMessage;

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbDice1;
    private javax.swing.JLabel lbDice2;
    private javax.swing.JLabel lbMyScore;
    private javax.swing.JLabel lbOpponentScore;
    private javax.swing.JLabel lbPlusSymbol;
    private javax.swing.JLabel lbVersusSymbol;

    private javax.swing.JPanel centralPanel;
    private javax.swing.JPanel chatPanel;
    private javax.swing.JPanel diceNumbersPanel;
    private javax.swing.JPanel dicesContainerPanel;
    private javax.swing.JPanel movesLabelPanel;
    private javax.swing.JPanel scorePanel;

    private javax.swing.JScrollPane jScrollPane1;


    private javax.swing.JTextArea taChatConsole;
    private javax.swing.JTextField tfChatInput;
    private javax.swing.JFormattedTextField tfWordGuess;

    private String regex = "/[abc]/g";

    // End of variables declaration
}
