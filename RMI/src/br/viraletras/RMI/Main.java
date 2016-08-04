package br.viraletras.RMI;

import br.viraletras.RMI.controller.GameControllerImpl;
import br.viraletras.RMI.view.GameFrameExtended;

import java.net.SocketException;

/**
 * Created by Roland on 7/16/16.
 */
public class Main {

    public static void main(String[] args) throws SocketException {
        setLookAndFeel();

        GameControllerImpl gameController = new GameControllerImpl();

    }

    public static void setLookAndFeel() {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx=0; idx<installedLookAndFeels.length; idx++)
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrameExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrameExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrameExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrameExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }



}
