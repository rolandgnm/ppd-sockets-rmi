/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.viraletras.model;

/**
 * @author Roland
 */
public class Piece {
    private int position;
    private String letter;
    private State state;

    public Piece(int position, String letter, State state) {
        this.position = position;
        this.letter = letter;
        this.state = state;

        this.setViewStateBy(this.state);


    }

    @Override
    public String toString() {
        return letter;
    }

    public void setViewStateBy(State state) {
        switch (state) {
            case HIDDEN:
                this.setHidden();
                break;
            case SHOW:
                this.setShow();
                break;
            case GONE:
                this.setGone();
                break;
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setHidden() {
        this.state = State.HIDDEN;
    }

    public void setShow() {
        this.state = State.SHOW;
    }

    public void setGone() {
        this.state = State.GONE;
    }


    public enum State {
        HIDDEN("HIDDEN", true),
        SHOW("SHOW", true),
        GONE("GONE", false);

        private String state;
        private boolean onBoard;

        private State(String name, boolean isOnBoard) {
            this.state = name;
            this.onBoard = isOnBoard;
        }

        @Override
        public String toString() {
            return "name=" + state + ", onBoard=" + onBoard;
        }
    }


}
