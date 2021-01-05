
public class Player implements Comparable<Player> {
    /*
     * Object for storing player data 
     * score - the points amassed by a player 
     * name - the name used to identify the player
     * keys - a char array used for getting and setting the keys the player uses to answer questions. The 0th will be the
     * true key, and the 1st will be the false key. 
     * timeToAnswer - the amount of time it took the player to answer the last question 
     * numCorrect - the number of questions the player got correct in any given game
     * hasAnswered - a boolean to track if a player has given an answer to a question
     * lastAnswer - a boolean to track the last answer given by the player
     */

    private int score;
    private String name;
    private char[] keys;
    private double timeToAnswer;
    private int numCorrect;
    private boolean hasAnswerd;
    private boolean lastAnswer;

    /********** Constructors **********/
    public Player(String n) {
        name = n;
        score = 0; // All players start with a score of 0
        numCorrect = 0;
        hasAnswerd = false;
    }

    public Player(String n, char[] k) {
        name = n;
        keys = k;
        score = 0; // All players start with a score of 0
        numCorrect = 0;
        hasAnswerd = false;
    }

    /*********** Helpers *************/
    public void addScore(int points) {
        // adds a given amount of points to the players score
        score = score + points;
    }

    @Override
    public int compareTo(Player other) {
        // compares players based on score, will be used for sorting the score board
        if (this.score > other.getScore())
            return -1;
        else if (this.score < other.getScore())
            return 1;

        return 0;
    }

    public String toString() {
        return name;
    }

    public void incrementCorrect() {
        numCorrect++;
    }

    /******** Getters & Setters *******/
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public char[] getKeys() {
        return keys;
    }

    public double getTimeToAnswer() {
        return timeToAnswer;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public boolean getHasAnswered() {
        return hasAnswerd;
    }

    public boolean getLastAnswer() {
        return lastAnswer;
    }

    public void setScore(int sIn) {
        score = sIn;
    }

    public void setName(String nIn) {
        name = nIn;
    }

    public void setKeys(char[] kIn) {
        keys = kIn;
    }

    public void setTimeToAnswer(double tta) {
        timeToAnswer = tta;
    }

    public void setNumCorrect(int ncIn) {
        numCorrect = ncIn;
    }

    public void setHasAnswered(boolean hIn) {
        hasAnswerd = hIn;
    }

    public void setLastAnswer(boolean hIn) {
        lastAnswer = hIn;
    }

}
