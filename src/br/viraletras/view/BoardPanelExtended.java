/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.view;

//set  get/putClientProperty

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author Roland
 */
public class BoardPanelExtended extends javax.swing.JPanel {

//    private ArrayList<JLabel> pieceLabelList;
    private String propertyField = "pos";
    private final int[] WHITE = {255,255,255};
    private final int[] BLUE = {41, 128, 185};

    /**
     * Creates new form BoardLayout
     */
    public BoardPanelExtended(String[] letterList) {

        initComponents(letterList);

    }

    @SuppressWarnings("unchecked")
    private void initComponents(String[] letterStrings) {

        formatThisContainer();


        for (int i=0; i < 64; i++){
                //TODO: Refatorar colocando valor via Controller/GameModel.
//              Piece newPiece = new Piece(i, j,"A", Piece.State.SHOW);
                add(getNewPieceLabel(letterStrings[i], i));
        }

    }

    private void formatThisContainer() {
        setBackground(new java.awt.Color(BLUE[0], BLUE[1], BLUE[2]));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMaximumSize(new java.awt.Dimension(800, 800));
        setMinimumSize(new java.awt.Dimension(700, 700));
        setName("Vira Letras");
        setPreferredSize(new java.awt.Dimension(700, 700));
        setLayout(new java.awt.GridLayout(8, 8, 10, 10));
    }

    private JLabel getNewPieceLabel(String value, int pos) {
        JLabel pieceLabel = new JLabel();

        pieceLabel.setFont(new java.awt.Font("Lucida Grande", 0, 48));
        pieceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pieceLabel.setText(value);
        pieceLabel.setAutoscrolls(true);
        pieceLabel.setMaximumSize(new java.awt.Dimension(50, 50));
        pieceLabel.setMinimumSize(new java.awt.Dimension(50, 50));
        pieceLabel.setOpaque(true);
        pieceLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        pieceLabel.setSize(new java.awt.Dimension(65, 46));
        pieceLabel.putClientProperty(propertyField, pos);

        setPieceHidden(pieceLabel);

        pieceLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                setPieceShowAt( (int)
//                        ((JComponent) e.getSource())
//                            .getClientProperty(propertyField));
                //todo usar if pra desabilitar ou não labels
                if(true) setPiecesEnabled(false);
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
        });

        return pieceLabel;
    }



    public void setPieceHidden(JLabel jLabel) {
            jLabel.setBackground(new Color(WHITE[0], WHITE[1], WHITE[2]));
            jLabel.setForeground(new Color(WHITE[0], WHITE[1], WHITE[2]));
    }

    public void setPieceHiddenAt(int position) {
        JLabel piece = (JLabel) getComponent(position);
            piece.setBackground(new java.awt.Color(WHITE[0], WHITE[1], WHITE[2]));
            piece.setForeground(new java.awt.Color(WHITE[0], WHITE[1], WHITE[2]));
    }

    public void setPieceShowAt(int position) {
        JLabel piece = (JLabel) getComponent(position);
            piece.setBackground(new java.awt.Color(WHITE[0], WHITE[1], WHITE[2]));
            piece.setForeground(new java.awt.Color(BLUE[0], BLUE[1], BLUE[2]));
    }

    public void setPieceGoneAt(int position) {
        JLabel piece = (JLabel) getComponent(position);
            piece.setBackground(new java.awt.Color(BLUE[0], BLUE[1], BLUE[2]));
            piece.setForeground(new java.awt.Color(BLUE[0], BLUE[1], BLUE[2]));
    }

    public int getLabelIndex(JLabel jLabel) {
        return (int) jLabel.getClientProperty(propertyField);
    }

    public void setPiecesEnabled(boolean enabled) {
//todo: implementar se preciso
        Component[] comps = getComponents();
        for (Component comp:comps){
            comp.setEnabled(enabled);
        }

//        removeMouseListener(this)

    }





}
