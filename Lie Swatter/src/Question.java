
public class Question {
    /*
     * Object for a specific question Stores value of the question, its answer and a
     * fact, if provided 
     * question - a true or false statement given to the players
     * fact - stores a fact about the question to be displayed after it is answered
     * category - stores what larger category the question falls under 
     * isTrue - the actual answer to the question: if it is true or false
     */
    private String question, fact, category;
    private boolean isTrue;

    /********** Constructors **********/
    public Question(String qIn, boolean tf) {
        question = qIn;
        isTrue = tf;
    }

    public Question(String qIn, boolean tf, String fIn, String cIn) {
        question = qIn;
        fact = fIn;
        isTrue = tf;
        category = cIn;
    }

    /*********** Helpers *************/
    public boolean checkQuestion(boolean ans) {
        // checks if the boolean passed in is the same as the answer to the question
        return isTrue == ans;
    }

    public String toString() {
        return "Q: " + question + "\nA: " + isTrue + "\nF: " + fact + "\n"; // "C: " + category +
    }

    /******** Getters & Setters *******/
    public String getQuestion() {
        return question;
    }

    public String getFact() {
        return fact;
    }

    public boolean getIsTrue() {
        return isTrue;
    }

    public String getCategory() {
        return category;
    }

    public void setQuestion(String q) {
        question = q;
    }

    public void setFact(String f) {
        fact = f;
    }

    public void setIsTrue(boolean iT) {
        isTrue = iT;
    }

    public void setCategory(String cIn) {
        category = cIn;
    }

}
