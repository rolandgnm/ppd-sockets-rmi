/* Anagram Game Application */

package com.toy.anagrams.lib;

/**
 * Interface defining logic for the Anagram Game application.
 */
public abstract class WordLibrary {
    /**
     * Constructor for subclasses.
     */
    protected WordLibrary() {
    }
    
    /** Getter for the default implementation of the WordLibrary.
     * @return some default implementation of WordLibrary
     */
    public static WordLibrary getDefault() {
        return StaticWordLibrary.DEFAULT;
    }

    /**
     * Gets the word at a given index.
     * @param idx index of required word
     * @return word at that index in its natural form
     */
    public abstract String getWord(int idx);

    /**
     * Gets the word at a given index in its scrambled form.
     * @param idx index of required word
     * @return word at that index in its scrambled form
     */
    public abstract String getScrambledWord(int idx);

    /**
     * Gets the number of words in the library.
     * @return the total number of plain/scrambled word pairs in the library
     */
    public abstract int getSize();

    /**
     * Checks whether a user's guess for a word at the given index is correct.
     * @param idx index of the word guessed
     * @param userGuess the user's guess for the actual word
     * @return true if the guess was correct; false otherwise
     */
    public abstract boolean isCorrect(int idx, String userGuess);
}
