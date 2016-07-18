/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author Roland
 */
public class GameModel {

    public static final String[] ORIGINAL_LETTER_SET = {
        "a","a","a","a","a","a","a",
        "e","e","e","e","e","e","e",
        "i","i","i","i","i","i",
        "o","o","o","o","o","o","o",
        "u","u","u","u","b","b",
        "c","c","d","d","f","g","h",
        "j","l","l","l","m","m",
        "n","n","p","p","qu",
        "r","r","r","s","s","s","s",
        "t","t","v","v","x","z"};

    private Player CURRENT_PLAYING; //Não precisa inicializar


    private Player playerThis;
    private Player playerOpponent;
    private int dices;
    private Piece[] randomPieceVector;
    private ArrayList<Piece> showingPieceList;
    private String wordGuess;

    private void initialize(Player playerThis, Player playerOpponent) {
        this.playerThis = playerThis;
        this.playerOpponent = playerOpponent;
        this.wordGuess = "";
        this.dices = 0;
        this.randomPieceVector = new Piece[64];
        this.showingPieceList = new ArrayList<>();
        
        createRandomPieceVector(getRandomLetterVector());
    }

    public GameModel(Player playerThis, Player playerOpponent) {
        initialize(playerThis,playerOpponent);
        createRandomPieceVector(getRandomLetterVector());
    }

    public GameModel(Player playerThis, Player playerOpponent, Piece[] pieces) {
        initialize(playerThis,playerOpponent);
        this.randomPieceVector = pieces;
    }


    public final void createRandomPieceVector(ArrayList<String> randomLetterList){

        for(int i=0; i<64; i++){
                this.randomPieceVector[i] = new Piece(i,randomLetterList.get(i), Piece.State.HIDDEN);
        }
    }


    public final ArrayList<String> getRandomLetterVector(){
        ArrayList<String> randomLetterList = new ArrayList<>(Arrays.asList(ORIGINAL_LETTER_SET));
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

    public int getDices() {
        return dices;
    }

    public void setDices(int dices) {
        this.dices = dices;
    }

    public int throwDices() {
        int randInt = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        this.dices =  randInt;
        return this.dices;
    }
    
    public Piece[] getRandomPieceVector() {
        return randomPieceVector;
    }

    public void setRandomPieceVector(Piece[] randomPieceVector) {
        this.randomPieceVector = randomPieceVector;
    }

    public Player getCurrentPlaying() {
        return CURRENT_PLAYING;
    }

    public void setCurrentPlaying(Player CURRENT_PLAYING) {
        this.CURRENT_PLAYING = CURRENT_PLAYING;
    }

    public ArrayList<Piece> getShowingPieceList() {
        return showingPieceList;
    }

    public void setShowingPieceList(ArrayList<Piece> showingPieceList) {
        this.showingPieceList = showingPieceList;
    }
    
    
}
