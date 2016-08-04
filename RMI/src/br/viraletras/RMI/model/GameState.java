package br.viraletras.RMI.model;

/**
 * Created by Roland on 7/18/16.
 */
public enum GameState {
    THROW_DICES("GD"),
    NOW_PLAYING("GP"),
    NOW_WAITING("GW"),
    NOW_CONFIRMING_GUESS_WORD("GC");

    String code;

    GameState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
