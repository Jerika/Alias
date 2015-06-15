package org.my.alias;

import java.io.Serializable;

public class Pair implements Serializable {
    String word;
    boolean guess;

    public Pair(String word, boolean guess){
        this.word = word;
        this.guess = guess;
    }

    public String getWord() {
        return word;
    }
    public boolean isGuess() {
        return guess;
    }

    public void setGuess(boolean guess) {
        this.guess = guess;
    }
}
