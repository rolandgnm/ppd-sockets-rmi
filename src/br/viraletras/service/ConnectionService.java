package br.viraletras.service;

/**
 * Created by Roland on 7/14/16.
 */
public interface ConnectionService {

    boolean tryConnectionAsClient();
    boolean waitForConnectionAsServer();
    boolean configureDatastreams();

    boolean startSocketListener();
        boolean notifyConnectionLost();





}
