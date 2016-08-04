package br.viraletras.Socket.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roland on 7/17/16.
 */
public class Utils {
    public static void log(String s) {
        System.out.printf(s + "\n");
    }

    public static void displayDialog(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message);
    }

}
