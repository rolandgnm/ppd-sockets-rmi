package br.viraletras.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

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
        aboutMenuItem = new javax.swing.JMenuItem();
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

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");

        aboutMenuItem.setMnemonic('A');
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(aboutMenuItem);

        exitMenuItem.setMnemonic('E');
        exitMenuItem.setText("Exit");
        exitMenuItem.setToolTipText("Quit Team, Quit!");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenu.add(fileMenu);

        setJMenuBar(mainMenu);
    }

    public void addGameWindowListener(WindowAdapter wl) {
        addWindowListener(wl);
    }

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                              
        new About(this).setVisible(true);
    }                                             

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        System.exit(0);
    }                                            

    // Variables declaration - do not modify                     
    private JMenuItem aboutMenuItem;
    private BoardPanelExtended boardPanel;
    private ControlPanelExtended controlPanel;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JMenuBar mainMenu;
    // End of variables declaration                   

}
