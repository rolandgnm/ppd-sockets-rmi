/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.RMI.model;

import br.viraletras.RMI.utils.Utils;

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
    private Boolean GAME_IN_PROGRESS;
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
        this.GAME_IN_PROGRESS = false;
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
        return randomPieceVector[position].getState().equals(Piece.State.HIDDEN);
    }

    public void flipPieceAt(int position) {
            showingPieceList.add(randomPieceVector[position]);
            randomPieceVector[position].setShow();
            if (wordGuessRegex == null) wordGuessRegex = " ";
            wordGuessRegex = wordGuessRegex.concat(randomPieceVector[position].getLetter());
            dices -= 1;
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


    public Boolean isGameInProgress() {
        return GAME_IN_PROGRESS;
    }

    //TODO Considerar esta flag quando resetar o jogo!
    public void setGameInProgress(Boolean GAME_IN_PROGRESS) {
        this.GAME_IN_PROGRESS = GAME_IN_PROGRESS;
    }

    public int evaluateScoresEarned(String wordGuess, Player playerOpponent) {
        /**
         * Considera que a entrada já foi tratada com apenas letras válidas.
         * todo 1) Tratamento ainda não feito
         * todo 2) Tratar caso do qu
         */
        int letterCount = 0;
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordGuess.split(" ")));

        for(String word :  words) {
            letterCount += word.length();
        }

    return letterCount;
    }

    public ArrayList<Integer> getGonePositionsList(String wordGuess) {
        ArrayList<Integer> positions = new ArrayList<>();
        String currentLetter = "";
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordGuess.split(" ")));
        int index;

        /**
         * Split por " "
         */
        for(String word : words) {

            /**
             * Iterate through the letters
             * Using type String to include chance of 'qu'
             */
            for(int idx = 0; idx < word.length(); idx++){
                if(word.substring(idx, idx + 1).equals("q")) {
                    currentLetter = word.substring(idx, idx + 2);
                    idx++;
                }
                 else
                    currentLetter = word.substring(idx, idx + 1);
                index = getIndexForGoneLetter(currentLetter);
                //TODO for DEBUG Purpose:
                    if(index == -1) Utils.log("RECEBI UM INDEX -1");

                positions.add(index);
            }
        }

        return positions;
    }

    public int getIndexForGoneLetter(String letter) {
        int index = -1;
        for(Piece currentPiece: showingPieceList) {
            if(currentPiece.getLetter().equals(letter)) {
                index = currentPiece.getPosition();
                showingPieceList.remove(currentPiece);
                break;
            }
        }
        return index;
    }

    public void setPieceGoneAt(int position) {
        randomPieceVector[position].setGone();
    }

    public ArrayList<Integer> getRemainingPositionsList() {
        /**
         * To be invoked after cleaning gone pieces out of showingPieceList.
         */
        ArrayList<Integer> positions = new ArrayList<>();

        for(Piece remainingPiece : showingPieceList) {
            positions.add(remainingPiece.getPosition());
        }

        return positions;
    }

    public void setPieceHiddenAt(int position) {
        randomPieceVector[position].setHidden();
    }

    public void clearShowingPiecesList() {
        showingPieceList.clear();
    }

    public int getGonePiecesCount() {
        return getPlayerOpponent().getInGameScore() + getPlayerThis().getInGameScore();
    }

    public int boardSize() {
        return randomPieceVector.length;
    }
}
