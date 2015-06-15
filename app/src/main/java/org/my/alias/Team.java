package org.my.alias;

import java.io.Serializable;

public class Team implements Serializable{
    int score;
    int steps;
    int number;

    public Team(int number){
        score = 0;
        steps = 0;
        this.number = number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getNumber() {
        return number;
    }


}
