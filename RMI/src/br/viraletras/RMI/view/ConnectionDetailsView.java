/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.RMI.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Roland
 */
public class ConnectionDetailsView extends JFrame{

    public ConnectionDetailsView() {
        setTitle("Vira Letras");
        setResizable(false);
        // Center in the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(new Point((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.width) / 2));
        JPanel container = new JPanel();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(220,250) );


        container.add(lbName);
        container.add(tfPlayerName);
        container.add(cbBeServer);
        container.add(lbServerName);
        container.add(tfServerName);
        container.add(lbIP);
        container.add(tfIP);
        container.add(lbConnMessage);
        container.add(btSetupConn);

        this.add(container);

        cbBeServer.addItemListener(e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            tfServerName.setEnabled(!source.isSelected());
        });

        btSetupConn.putClientProperty("default", "Conectar");
    }


    public String getPlayerName() {
        return tfPlayerName.getText();
    }

    public String getServerName() {
        return tfServerName.getText();
    }

    public String getIP() {
        return tfIP.getText().trim();
    }

    public boolean cbIsServer() {
        return cbBeServer.isSelected();
    }


    public void addConnectionDetailsListener(ActionListener listenerConnDetailsButton) {
        btSetupConn.addActionListener(listenerConnDetailsButton);
    }

    public void setSetupButtonText(String text) {
        btSetupConn.setText(text);
    }




    private JLabel      lbName = new JLabel("Seu nome:");
    private JTextField  tfPlayerName = new JTextField(15);
    private JCheckBox   cbBeServer = new JCheckBox("Ser o servidor!");
    private JLabel      lbServerName = new JLabel("Nome do Servidor (Server):");
    private JTextField  tfServerName = new JTextField(15);
    private JLabel      lbIP = new JLabel("IP do Servidor de Nome(localhost):");
    private JTextField  tfIP = new JTextField(15);
    private JLabel      lbConnMessage = new JLabel(" ");
    private JButton     btSetupConn = new JButton("Conectar");
}
