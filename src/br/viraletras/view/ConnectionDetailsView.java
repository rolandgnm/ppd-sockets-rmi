/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.Book;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;

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
        this.setMinimumSize(new Dimension(200,250) );


        container.add(lbName);
        container.add(tfPlayerName);
        container.add(cbBeServer);
        container.add(lbIp);
        container.add(tfIp);
        container.add(lbPort);
        container.add(tfPort);
        container.add(lbConnMessage);
        container.add(btSetupConn);

        this.add(container);

        cbBeServer.addItemListener(e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            tfIp.setEnabled(!source.isSelected());
            try {
                String netInterface = InetAddress.getLocalHost().toString();
                String ip = netInterface.split("/")[1];
                tfIp.setText(ip);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
        });

        btSetupConn.putClientProperty("default", "Conectar");
    }


    public String getPlayerName() {
        return tfPlayerName.getText();
    }

    public String getIp() {
        return tfIp.getText();
    }

    public String getPort() {
        return tfPort.getText();
    }

    public boolean isServer() {
        return cbBeServer.isSelected();
    }


    public void addConnectionDetailsListener(ActionListener listenerConnDetailsButton) {
        btSetupConn.addActionListener(listenerConnDetailsButton);
    }

    public void setSetupButtonText(String text) {
        btSetupConn.setText(text);
    }

    public void enableSetupButton(Boolean enabled) {
        btSetupConn.setEnabled(enabled);
    }

    public void setLbConnMessage(String connMessage) {
        this.lbConnMessage.setText(connMessage);
    }

    public void displayErrorMessage(String errMessage){
        JOptionPane.showMessageDialog(this, errMessage);
    }



    private JLabel      lbName = new JLabel("Seu nome:");
    private JTextField  tfPlayerName = new JTextField(15);
    private JCheckBox   cbBeServer = new JCheckBox("Ser o servidor!");
    private JLabel      lbIp = new JLabel("IP destino (localhost):");
    private JTextField  tfIp = new JTextField(15);
    private JLabel      lbPort= new JLabel("Porta destino (9999):");
    private JTextField  tfPort = new JTextField(8);
    private JLabel      lbConnMessage = new JLabel(" ");
    private JButton     btSetupConn = new JButton("Conectar");
}
