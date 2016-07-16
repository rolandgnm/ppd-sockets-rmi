package br.viraletras.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Roland on 7/14/16.
 */
public interface GameController {

    void createStartUpWindow();
    void getPlayerNameAndConnectionDetails();
    void createConnection();

    void addListerners();
       void addListenersToControlPanel();
       void addListenerToBoard();



}
