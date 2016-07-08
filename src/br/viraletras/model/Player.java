/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Roland
 */
public class Player {
    
    private String name;
    private String ip;
    private int port;
    private int inGameScore;
    private int winScore;
    private ArrayList<String> formedWords; 

    public Player(String name, String ip, int port, int inGameScore, int winScore, ArrayList<String> formedWords) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.inGameScore = inGameScore;
        this.winScore = winScore;
        this.formedWords = formedWords;
    }
    
    
    
    
    
}
