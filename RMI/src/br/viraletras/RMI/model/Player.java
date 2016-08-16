/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.RMI.model;

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
        this.port = 0;
        this.inGameScore = 0;
        this.winScore = 0;
        this.formedWords = new ArrayList<>();
        this.startUpDiceValue = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInGameScore() {
        return inGameScore;
    }

    public void setInGameScores(int inGameScore) {
        this.inGameScore = inGameScore;
    }

}
