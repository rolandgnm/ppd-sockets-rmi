package br.viraletras.service;

/**
 * Created by Roland on 7/14/16.
 */
public interface ConnectionService {
    void sendThisPlayerName(String name);


    void sendNewMessage(String chatMessageInput);
}