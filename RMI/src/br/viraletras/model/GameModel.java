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
 * @author Roland
 */
public class GameModel {

    public static final String[] ORIGINAL_LETTER_SET = {
            "a", "a", "a", "a", "a", "a", "a",
            "e", "e", "e", "e", "e", "e", "e",
            "i", "i", "i", "i", "i", "i",
            "o", "o", "o", "o", "o", "o", "o",
            "u", "u", "u", "u", "b", "b",
            "c", "c", "d", "d", "f", "g", "h",
            "j", "l", "l", "l", "m", "m",
            "n", "n", "p", "p", "qu",
            "r", "r", "r", "s", "s", "s", "s",
            "t", "t", "v", "v", "x", "z"};

    private Player CURRENT_PLAYING; //Não precisa inicializar
    private Player playerThis;
    private Player playerOpponent;
    private int dices;
    private Piece[] randomPieceVector;
    private ArrayList<Piece> showingPieceList;
    private String wordGuessRegex;
    private String wordGuess;

    public GameModel(Player playerThis, Player playerOpponent) {
        this.playerThis = playerThis;
        this.playerOpponent = playerOpponent;
        newGame();
    }

    private void newGame() {
        this.CURRENT_PLAYING = null;
        this.dices = 0;
        this.randomPieceVector = new Piece[64];
        this.showingPieceList = new ArrayList<>();
        this.wordGuess = " ";
    }

    private void setupNewTurn() {
        this.dices = 0;
        showingPieceList = new ArrayList<>();
        wordGuessRegex = " ";
    }

    public void createRandomPieceVector() {
        ArrayList<String> randomLetterList = getRandomLetterVector();
        for (int i = 0; i < 64; i++) {
            this.randomPieceVector[i] = new Piece(i, randomLetterList.get(i), Piece.State.HIDDEN);
        }
    }

    public void createRandomPieceVector(String randomPiecesString) {
        String[] res = randomPiecesString.split(" ");
        for (int i = 0; i < 64; i++) {
            this.randomPieceVector[i] = new Piece(i, res[i], Piece.State.HIDDEN);
        }

    }

    private ArrayList<String> getRandomLetterVector() {
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
        this.dices = randInt;
        return this.dices;
    }

    public String[] getRandomPieceVector() {
        String[] res = new String[64];
        int i = 0;
        for (Piece piece : this.randomPieceVector) res[i++] = piece.toString();
        return res;
    }

    public String getRandomPieceVectorToString() {
        String res = "";
        for (Piece piece : this.randomPieceVector) res = res + piece.toString() + " ";
        return res.trim();
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

    public void clearCurrentPlaying() {
        this.setCurrentPlaying(null);
    }


    public boolean hasAvailableMove() {
        return dices > 0;
    }

    public boolean isPieceHiddenAt(int position) {
        return randomPieceVector[0].getState().equals(Piece.State.HIDDEN);
    }

    public void flipPieceAt(int position) {
        new Thread(() -> {
            showingPieceList.add(randomPieceVector[position]);
            randomPieceVector[position].setShow();
            if (wordGuessRegex == null) wordGuessRegex = " ";
            wordGuessRegex = wordGuessRegex.concat(randomPieceVector[position].getLetter());
            dices -= 1;
        }).start();

    }

    public void setThisPlayerStartUpDicesValue(int value) {
        getPlayerThis().setStartUpDicesValue(value);
    }

    public void setOpponentStartUpDicesValue(int value) {
        getPlayerOpponent().setStartUpDicesValue(value);
    }

    public int getOpponentStartUpDicesValue() {
        return getPlayerOpponent().getStartUpDicesValue();
    }

    public int getThisPlayerStartUpDicesValue() {
        return getPlayerThis().getStartUpDicesValue();
    }

    public String getStarterPlayer() {
        if (getPlayerThis().getStartUpDicesValue() > getPlayerOpponent().getStartUpDicesValue())
            return getPlayerThis().getName();
        else
            return getPlayerOpponent().getName();
    }

    public void updateGameState(GameState gameState) {
        switch (gameState) {
            case NOW_PLAYING:
                CURRENT_PLAYING = getPlayerThis();
                break;
            case NOW_WAITING:
                CURRENT_PLAYING = getPlayerOpponent();
                break;
            case NOW_CONFIRMING_GUESS_WORD:
                CURRENT_PLAYING = getPlayerOpponent();
                break;
            case THROW_DICES:
                CURRENT_PLAYING = getPlayerThis();
                break;
        }


    }


}
