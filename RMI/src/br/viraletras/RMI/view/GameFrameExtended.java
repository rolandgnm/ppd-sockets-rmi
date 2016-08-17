package br.viraletras.RMI.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.*;

/**
 * Main window of the Vira Letras Game application.
 */
public class GameFrameExtended extends JFrame {



    public GameFrameExtended(BoardPanelExtended boardPanel, ControlPanelExtended controlPanel) {
        this.boardPanel =  boardPanel;
        this.controlPanel = controlPanel;
        initComponents();
        pack();
        // Center in the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(new Point((screenSize.width - frameSize.width) / 2,
                              (screenSize.height - frameSize.width) / 2));
    }



    public void setVisible(){
            /* Create and display the form */
        SwingUtilities.invokeLater(() -> setVisible(true));

    }

    private void initComponents() {

//todo: Receber panels criados e prontos

        mainMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newGameMI = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        setTitle("Vira Letras");
        setMaximumSize(new java.awt.Dimension(1100, 700));
        setMinimumSize(new java.awt.Dimension(1100, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 750));

        //todo: avisar outro peer sobre tela fechada.
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        getContentPane().add(boardPanel);
        getContentPane().add(controlPanel);

        fileMenu.setMnemonic('A');
        fileMenu.setText("Arquivo");

        newGameMI.setMnemonic('N');
        newGameMI.setText("Novo Jogo");
        newGameMI.setToolTipText("Iniciar nova partida");

        /**
         *  TODO Colocar LISTENER VIA CONTROLLER
         */


        fileMenu.add(newGameMI);

        exitMenuItem.setMnemonic('F');
        exitMenuItem.setText("Fechar");
        exitMenuItem.setToolTipText("Abandonar a partida e fechar a janela");

        /**
         *  TODO Colocar LISTENER VIA CONTROLLER
         */
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);

        mainMenu.add(fileMenu);

        setJMenuBar(mainMenu);
    }

    public void addGameWindowListener(WindowAdapter wl, ActionListener newGameMIListener, ActionListener exitMIListener) {
        addWindowListener(wl);
        newGameMI.addActionListener(newGameMIListener);
        exitMenuItem.addActionListener(exitMIListener);

    }

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        System.exit(0);
    }                                            

    // Variables declaration - do not modify                     
    private JMenuItem newGameMI;
    private BoardPanelExtended boardPanel;
    private ControlPanelExtended controlPanel;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JMenuBar mainMenu;
//     End of variables declaration

}
