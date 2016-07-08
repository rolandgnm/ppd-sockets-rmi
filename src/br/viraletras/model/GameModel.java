/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 *
 * @author Roland
 */
public class GameModel {
    private Player playerThis;
    private Player playerOpponent;
    private int [] dices;
    private ArrayList<ArrayList<String>> randomLetterMatrix;
    private String wordGuess;
    private static final String[] ORIGINAL_LETTER_SET = {
        "a","a","a","a","a","a","a",
        "e","e","e","e","e","e","e",
        "i","i","i","i","i","i",
        "o","o","o","o","o","o","o",
        "u","u","u","u","b","b",
        "c","c","d","d","f","g","h",
        "j","l","l","l","m","m",
        "n","n","p","p","qu",
        "r","r","r","s","s","s","s",
        "t","t","v","v","x","z"
    };

    public GameModel(Player playerThis, Player playerOpponent) {
        this.playerThis = playerThis;
        this.playerOpponent = playerOpponent;
        this.wordGuess = "";
        this.dices = new int[]{0, 0};
//        this.randomLetterMatrix = new ArrayList<String>(Arrays.asList(ORIGINAL_LETTER_SET));
        this.randomLetterMatrix = new ArrayList<ArrayList<String>>();
        createRandomLetterMatrix(getRandomLetterList());      
    }
    
    public void createRandomLetterMatrix(ArrayList<String> randomList){ 
        int i,j,count=0; 
            
        for(i=0; i<8; i++){
            for(j=0; i<8; j++){
                randomLetterMatrix.get(i)
                    .add(randomList.get(count++));
            }
   
        }
    }
    
    public ArrayList<String> getRandomLetterList(){
        ArrayList<String> randomLetterList = new ArrayList<String>(Arrays.asList(ORIGINAL_LETTER_SET));
        Collections.shuffle(randomLetterList, new Random(System.nanoTime()));
        return randomLetterList;
        
    }

    
    // Getter and Setters...
    public String getWordGuess() {
        return wordGuess;
    }

    public void setWordGuess(String wordGuess) {
        this.wordGuess = wordGuess;
    }
    
    public Player getPlayerThis() {
        return playerThis;
    }

    public void setPlayerThis(Player playerThis) {
        this.playerThis = playerThis;
    }

    public Player getPlayerOpponent() {
        return playerOpponent;
    }

    public void setPlayerOpponent(Player playerOpponent) {
        this.playerOpponent = playerOpponent;
    }

    public int[] getDices() {
        return dices;
    }

    public void setDices(int[] dices) {
        this.dices = dices;
    }

    public ArrayList<ArrayList<String>> getRandomLetterMatrix() {
        return randomLetterMatrix;
    }

    public void setRandomLetterMatrix(ArrayList<ArrayList<String>> randomLetterMatrix) {
        this.randomLetterMatrix = randomLetterMatrix;
    }
        
}
