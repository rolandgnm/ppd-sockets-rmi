/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Roland
 */
public class LaunchView extends JFrame{

    private JLabel      lbNameFied = new JLabel("Seu nome:");
    private JTextField  tfPlayerName = new JTextField(15);

    private JLabel      lbIpField = new JLabel("IP destino (localhost):");
    private JTextField  tfIp = new JTextField(8);
    
    private JLabel      lbConnMessage = new JLabel();
    private JButton     btSetupConn = new JButton("Conectar");

    public LaunchView() {
        JPanel container = new JPanel();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,200);
        
        container.add(lbNameFied);
        container.add(tfPlayerName);
        container.add(lbIpField);
        container.add(tfIp);
        container.add(lbConnMessage);
        container.add(btSetupConn);
        
        this.add(container);
    }

    public String getPlayerName() {
        return tfPlayerName.getText();
    }

    public String getIp() {
        return tfIp.getText();
    }

    public void setLbConnMessage(String connMessage) {
        this.lbConnMessage.setText(connMessage);
    }
    
    public void addConnectionDetailsListener(ActionListener listenerConnDetailsButton) {
        btSetupConn.addActionListener(listenerConnDetailsButton);
    }
    
    public void displayErrorMessage(String errMessage){
        JOptionPane.showMessageDialog(this, errMessage);
    }
    
                        
    
    
    
    
    
}
