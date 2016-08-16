/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.RMI.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Roland
 */
public class Player {

    public int getStartUpDicesValue() {
        return startUpDiceValue;
    }

    public void setStartUpDicesValue(int startUpdiceValue) {
        this.startUpDiceValue = startUpdiceValue;
    }

    private int startUpDiceValue;
    private String name;
    private String ip;
    private int port;

    private int inGameScore;
    private int winScore;
    private ArrayList<String> formedWords;

    public Player() {

        this.name = "";
        generateLocalIp();
        this.port = 0;
        this.inGameScore = 0;
        this.winScore = 0;
        this.formedWords = new ArrayList<>();
        this.startUpDiceValue = 0;
    }

    public Player(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.inGameScore = 0;
        this.winScore = 0;
        this.formedWords = new ArrayList<>();
    }


    public void generateLocalIp() {
        String netInterface = null;
        try {
            netInterface = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String ip = netInterface.split("/")[1];
        this.ip = ip;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getInGameScore() {
        return inGameScore;
    }

    public void setInGameScores(int inGameScore) {
        this.inGameScore = inGameScore;
    }

    public int getWinScore() {
        return winScore;
    }

    public void setWinScore(int winScore) {
        this.winScore = winScore;
    }

    public ArrayList<String> getFormedWords() {
        return formedWords;
    }

    public void setFormedWords(ArrayList<String> formedWords) {
        this.formedWords = formedWords;
    }
    
    public void addFormedWord(String formedWord) {
        this.formedWords.add(formedWord);
    }

}
