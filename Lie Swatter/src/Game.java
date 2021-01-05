import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Set;

public class Game {
    /*
     * The object that will run the game
     * 
     * PLAYER_MAX - the maximum number of players 
     * PLAYER_MIN - the minimum number of players
     * 
     * QUESTIONS_PER_ROUND - the number of questions the players will see per round
     * ROUNDS_PER_GAME - the number of rounds in one game of Lie Swatter
     * 
     * POINTS_PER_QUESTION - the maximum number of points awarded to a player for
     * answering a question correctly 
     * MAX_TIME_PER_QUESTION - the maximum amount of time that can be spent on one question
     * 
     * players - an ArrayList to store the player objects 
     * questions - A map where the key is a category of questions, and value is a list of questions under that category 
     * rounds - an ArrayList to hold the questions that will be asked in each round
     */

    // player related constants
    public final int PLAYER_MAX = 5, PLAYER_MIN = 2;

    // game related constants
    private final int QUESTIONS_PER_ROUND = 7;

    private final int ROUNDS_PER_GAME = 3;
    // note to self, that the last round is all on one category

    // question related constants
    private final int POINTS_PER_QUESTION = 1000;
    private final double MAX_TIME_PER_QUESTION = 10; // seconds

    // lists of things (explained above), instantiating here as well
    private ArrayList<Player> players = new ArrayList<Player>();
    private TreeMap<String, ArrayList<Question>> questions = new TreeMap<String, ArrayList<Question>>();
    private ArrayList<ArrayList<Question>> rounds = new ArrayList<ArrayList<Question>>();

    // used for getting user input from the console
    private Scanner stdIn = new Scanner(System.in);

    /********** Constructor **********/
    public Game() {
    }

    /*********** Methods *************/
    public void makeGame(String[] qFiles) {
        // creates the questions
        readQuestions(qFiles);
        // makes rounds based on the questions
        makeRounds();
    }
    
    public void refreshGame() {
        // makes new rounds
        makeRounds();
        // resets the players, leaving the keys and name as is
        for(Player p : players) {
            p.setHasAnswered(false);
            p.setLastAnswer(false);
            p.setNumCorrect(0);
            p.setScore(0);
            p.setTimeToAnswer(0);
        }
        
    }

    /*********** Question loading *************/

    // method to load the questions from files passed in
    private void readQuestions(String[] filesIn) {
        // Scanner for the files passed in
        Scanner fileIn;
        // placeholders for the information from the scanner
        String currKey, nextQ, nextA, nextF;

        // a boolean placeholder used in the creation of the question
        boolean nextAB;

        for (int i = 0; i < filesIn.length; i++) {
            /*
             * assuming the files will be the names of .txt files, the key will be the name
             * of the file minus the file extension
             */
            currKey = filesIn[i].substring(0, filesIn[i].length() - 4);

            // create the next ArrayList of questions
            questions.put(currKey, new ArrayList<Question>());

            // for testing
            int qCount = 0;

            try {
                // start scanning the file
                fileIn = new Scanner(new File(filesIn[i]), "UTF-8");

                // while there is still data to be read
                while (fileIn.hasNext()) {
                    // the files are formatted with each value for a question on each line
                    nextQ = fileIn.nextLine();
                    nextA = fileIn.nextLine().toLowerCase(); // toLowerCase() for standardization
                    nextF = ""; // create a default value for the next fact
                    if (fileIn.hasNextLine())
                        // if there is a fact, and we're not at the end of the file
                        nextF = fileIn.nextLine();

                    // set a boolean value that can be used to create the question
                    if (nextA.equals("false"))
                        nextAB = false;
                    else
                        nextAB = true;

                    // add the question that was read to the ArrayList
                    questions.get(currKey).add(new Question(nextQ, nextAB, nextF, currKey));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // method to prepare the lists of questions to be used (fills rounds)
    private void makeRounds() {

        rounds.clear();// in case it was left filled from a prior game

        // ArrayList for the round to be added
        ArrayList<Question> nextRound;
        boolean isLastRound = false;

        for (int i = 0; i < ROUNDS_PER_GAME; i++) {

            /*
             * since the last round centers around one category, if it isn't the last round,
             * get a round with questions from randomly selected categories
             */

            if (i == ROUNDS_PER_GAME - 1)
                isLastRound = true;

            nextRound = getRoundQuestions(isLastRound);
            // add nextRound to rounds
            rounds.add(nextRound);
        }
    }

    private ArrayList<Question> getRoundQuestions(boolean isLastRound) {

        // the list to be returned
        ArrayList<Question> result = new ArrayList<Question>();
        // the current category for a question to be chose from
        ArrayList<Question> currentCategory = getRandomCategory();

        // select 7 questions
        int randomIndex;
        Question randomQ;
        for (int i = 0; i < QUESTIONS_PER_ROUND; i++) {
            // assuming a non-zero size for currentCategory...
            randomIndex = (int) (Math.random() * (currentCategory.size() - 1));
            randomQ = currentCategory.get(randomIndex);

            // if this question was already selected for this round, get another question
            while (result.contains(randomQ)) {
                randomIndex = (int) (Math.random() * currentCategory.size() - 1);
                randomQ = currentCategory.get(randomIndex);
            }

            // now that we know this question will be unique to this round, add it to result
            result.add(randomQ);

            // if it isn't the last round, change the category
            if (!isLastRound)
                currentCategory = getRandomCategory();
        }

        return result;
    }

    private ArrayList<Question> getRandomCategory() {
        // selects a random category from questions
        Set<String> keys = questions.keySet();
        int category = (int) (Math.random() * keys.size());

        // the ArrayList to return
        ArrayList<Question> result = new ArrayList<Question>();

        // Category "selected", get it from questions
        int current = 0;
        for (String key : keys) {
            if (current == category) {
                // Set the ArrayList for the current category
                result = questions.get(key);
                // exit the loop
                break;
            }
            current++;
        }

        return result;
    }

    /*********** Player Creation *************/

    // method to set up the list of players
    public void setPlayers() {
        System.out.println("Please input a number of players for this game: ");

        int numPlayers = getCheckedIntInput(PLAYER_MAX, PLAYER_MIN);

        // the default key configuration for all players
        char[] keysInUse = { 'q', 'w', 'r', 't', 'u', 'i', 'x', 'c', 'n', 'm' };
        // placeholder for the player name, and prompt answers
        String playerName;

        // for safety's sake...
        players.clear();

        for (int i = 0; i < numPlayers; i++) {
            System.out.println();
            System.out.println("Player " + (i + 1) + ", what is your name? ");
            // no need to check input on this since the name can be anything really
            playerName = stdIn.nextLine();

            /*
             * create a new player object with the name just passed in by the player set
             * their key to i*2 for the first (true) key (0*2= 0, 1*2=2 ...) set their key
             * to i*2+1 for the second (false) key (0*2+1=1, 1*2+1=2 ...)
             */
            players.add(new Player(playerName, (new char[] { keysInUse[(i * 2)], keysInUse[(i * 2 + 1)] })));

            System.out.println("You will use the following keys to answer questions:");
            System.out.println("True: " + players.get(i).getKeys()[0]);
            System.out.println("False: " + players.get(i).getKeys()[1]);
            
        }
        
        // asks if anyone would like to not use the default key configuration
        System.out.println("Would anyone like to change their key configuration? (yes/no)");
        if(stdIn.nextLine().equalsIgnoreCase("yes"))
            editKeys();
    }
    
    private void editKeys() {
        boolean changingKeys = true, canChangeKeys = true;
        char trueKey, falseKey;
        
        while(changingKeys) {
            displayKeys();
            // gets the player to change
            System.out.println("Please put in the number player who would like to change their keys");
            int playerToChange = getCheckedIntInput(players.size(), 1) - 1;

            // gets what keys they want to use 
            System.out.println("Type what key you want to be your true key and press enter: ");
            trueKey = getCheckedCharInput();
            System.out.println("Type what key you want to be your false key and press enter: ");
            falseKey = getCheckedCharInput();
            
            // makes sure they are not the same
            if(trueKey == falseKey) {
                System.out.println("True key and false key cannot be the same.  The configuration was not changed.  Please select different keys");
                canChangeKeys = false;
            }
            else {
                // makes sure the keys are not in use
                for(Player p : players) {
                    for(int j = 0; j < p.getKeys().length; j++) {
                        if(p.getKeys()[j] == trueKey) {
                            System.out.println("True key could not be set.  It is already in use.  The configuration was not changed.  Please select another key.");
                            canChangeKeys = false;
                        }
                        else if(p.getKeys()[j] == falseKey) {
                            System.out.println("False key could not be set.  It is already in use.  The configuration was not changed.  Please select another key.");
                            canChangeKeys = false;
                        }
                    }
                }
            }
            
            // if all the checking went through ok, set the new keys
            if(canChangeKeys) {
                players.get(playerToChange).setKeys(new char[] {trueKey, falseKey});
                System.out.println("The Current key confiruratio is now ");
                displayKeys();
            }
            
            // for the next time it loops, if canChangeKeys was set to false, reset it
            canChangeKeys = true;
            
            System.out.println("Do any players wish to change their keys? ");
            if(stdIn.nextLine().equalsIgnoreCase("no"))
                changingKeys = false;
        }
        
    }
    
    // method to check user input for chars and return the first value read from System.in
    private char getCheckedCharInput() {
        char result;
        String sResult = stdIn.nextLine();
        
        
        // if it's an empty string get another one
        while(sResult.length() < 1) {
            System.out.println("Please input a key and press enter");
            sResult = stdIn.nextLine();
        }
        
        result = sResult.charAt(0);
        
        return result;
    }
    
    private void displayKeys() {
        // print out the players and what keys they have assigned
        for(int i = 0; i < players.size(); i++) {
            System.out.println((i+1) +". " + players.get(i) + ": ");
            System.out.print("\tTrue: "+ players.get(i).getKeys()[0]);
            System.out.println("\tFalse: "+ players.get(i).getKeys()[1]);
        }
    }

    // method to check user input for ints, and return the value read
    private int getCheckedIntInput(int upperBound, int lowerBound) {
        //  gets the user input and converts the string to an int
        String sResult = stdIn.next();
        int result = lowerBound - 1;
        
        // tests to make sure the string was actually one or more digits
        if(sResult.matches("\\d+"))
            result = Integer.parseInt(sResult);
        
        // checks to make sure it is within the allowable range
        while (result > upperBound || result < lowerBound) {
            System.out.println("Please input a number between " + upperBound + " and " + lowerBound + "\n");
            sResult = stdIn.next();
            
            // makes sure digits were passed in
            while(!sResult.matches("\\d+")) {
                System.out.println("Please input a number between " + upperBound + " and " + lowerBound + "\n");
                sResult = stdIn.next();
            }
            
            result = Integer.parseInt(sResult);
        }
        
        
        // reset the scanner position so it doesn't break things after this method is called
        stdIn.nextLine(); 

        return result;
    }

    /*********** Game Execution *************/

    // playes MAX_ROUNDS_PER_GAME rounds (a complete game),
    public void playGame() {

        // placeholders:
        int roundCount = 1;
        String answer, qFact;

        // for keeping track of the time elapsed on a question
        long qTime, aTime;
        double timeElapsed;

        // placeholder for the current question
        Question currentQ;
        
        // for making things look nice
        System.out.println();
        
        // Run each round by displaying the questions, and prompting for input
        for (ArrayList<Question> round : rounds) {
            
            // print out round info
            System.out.println();
            System.out.println("Round " + roundCount);
            System.out.println(displayRoundCategories(round));
            System.out.println();
            
            pauseGame(2);
            
            // display a question
            for (int i = 0; i < QUESTIONS_PER_ROUND; i++) {
                // since a new question is being displayed, reset if they have answered
                resetPlayers();
                
                // placeholders
                currentQ = round.get(i);
                qFact = currentQ.getFact();

                System.out.println(displayQuestion(currentQ.getQuestion(), i));

                pauseGame(3);

                // Lets them know they can put in their answers
                System.out.println("What do you think?");

                // gets the start time of the question
                qTime = System.currentTimeMillis();

                // get answers until everyone has answered
                while (!checkPlayers() && ((System.currentTimeMillis()- qTime)/1000) <= MAX_TIME_PER_QUESTION) {
                    // gets the player answer
                    answer = stdIn.next().toLowerCase();
                    // gets the point in time when the player answered (roughly speaking)
                    aTime = System.currentTimeMillis();
                    // convert the time elapsed into a double in seconds
                    timeElapsed = (aTime - qTime) / 1000.0;
                    
                    // set the player information based on the char input
                    for(int j = 0; j < answer.length(); j++) {
                        setPlayerAnswer(answer.charAt(j), timeElapsed, currentQ);
                    }
                }
                
                // if anyone missed answering the question
                checkForUnanswered(currentQ);

                // Display the answer
                System.out.println("The answer is: " + currentQ.getIsTrue() + "!");
                // if the fact isn't blank, display that
                if (!qFact.equals(""))
                    System.out.println(qFact);

                pauseGame(4);

            }

            // display the scores
            System.out.println();
            System.out.println("That's the end of round " + roundCount);

            // sort players based on the compareTo in the class
            Collections.sort(players);
            for (Player p : players) {
                System.out.println(p + "'s score is: " + p.getScore() + ", with " + p.getNumCorrect() + " correct answers");
            }
            
            // pause the game so the players can admire their mastery of weird facts
            System.out.println("Enter a key and press enter to continue");
            answer = stdIn.next();

            roundCount++;
        }
        
        // declare a winner
        System.out.println();
        System.out.println(players.get(0) + " is the winner!");
        System.out.println();
    }
    
    // cycles through players and if anyone didn't answer makes a random answer
    private void checkForUnanswered(Question q) {
        // placeholder
        double chance;
        boolean hasNotAdmonished = false;
        // cycles through players
        for(Player p : players) {
            if(!p.getHasAnswered()) {
                chance = Math.random();
                if(chance < .5)
                    setPlayerAnswer(p.getKeys()[0], MAX_TIME_PER_QUESTION, q);
                else
                    setPlayerAnswer(p.getKeys()[1], MAX_TIME_PER_QUESTION, q);
                p.setHasAnswered(true);
                
                // lets the users know if they ran out of time
                if(!hasNotAdmonished) {
                    System.out.println("Times up!");
                    hasNotAdmonished = true;
                }
            }
        }
        
    }

    // method to set lastPlayerAnswer
    private void setPlayerAnswer(char answer, double seconds, Question q) {
        // cycle through the players and find who pressed the key

        // variable for tracking
        boolean foundPlayer = false;
        
        // only set the score if the answer within the time limit
        if(seconds <= MAX_TIME_PER_QUESTION) {
            // cycle through players to find who answered
            for (Player p : players) {
                // cycle through the array of keys assigned to the player
                for (int i = 0; i < p.getKeys().length; i++) {
                    // if the char passed in is in a players keys
                    if (p.getKeys()[i] == answer) {
                        // set the tracking variable
                        foundPlayer = true;
                        // if the player has not answered yet (your answers are final)
                        if (!p.getHasAnswered()) {
                            // set lastAnswer for that player
                            switch (i) {
                                case (0):
                                    p.setLastAnswer(true);
                                    break;
                                default:
                                    p.setLastAnswer(false);
                            }
    
                            // set the time it took them to answer the question
                            p.setTimeToAnswer(seconds);
                            // set the variable that says if they have answered this question
                            p.setHasAnswered(true);
                            // calculate the score to be added based on the variables previously set
                            p.addScore(calculateScore(p, q));
                        }
                        // exit the loop checking the player's keys
                        break;
                    }
    
                    // only cycle through the players as many times as needed
                    if (foundPlayer)
                        break;
                }
            }

        }

    }

    // method to calculate score assigned to a player after they have answered
    private int calculateScore(Player p, Question q) {
        // placeholders
        int result = 0;
        double playerTime = p.getTimeToAnswer();
        
        // you can only get the max points.
        if( playerTime < 1)
            playerTime = 1;
        
        // if they answered correctly
        if (p.getLastAnswer() == q.getIsTrue()) {
            // the score is calculated based on the points per question divided by the time it took them to answer 
            result = (int) (POINTS_PER_QUESTION / playerTime);
            p.incrementCorrect();
        }
        
        // if they answered incorrectly then result stays at 0
        
        return result;
    }

    // method to check if all the players have answered
    private boolean checkPlayers() {
        for (Player p : players) {
            if (!p.getHasAnswered())
                return false;
        }
        return true;
    }

    // sets hasAnswered for each player to false
    private void resetPlayers() {
        for (Player p : players) {
            p.setHasAnswered(false);
        }

    }

    // method to get a display string for a given question
    private String displayQuestion(String question, int num) {

        String result = "";

        switch (num) {
            case 0:
                result += "First ";
                break;
            case 1:
                result += "Second ";
                break;
            case 2:
                result += "Third ";
                break;
            case 3:
                result += "Fourth ";
                break;
            case 4:
                result += "Fifth ";
                break;
            case 5:
                result += "Sixith ";
                break;
            case 6:
                result += "Seventh ";
                break;
            default:
                result += "";
        }

        return result += "Question: " + question;

    }
    
    private String displayRoundCategories(ArrayList<Question> round) {
        String result = new String("The categories in this round are: ");
        ArrayList<String> categories = new ArrayList<String>();
        String currentCategory;
        
        // get the categories in this round, no duplicates
        for(Question q : round) {
            currentCategory = q.getCategory();
            if(!categories.contains(currentCategory))
                categories.add(currentCategory);
        }
        
        // add them to the return string
        for(String cat : categories) {
            result = result.concat(cat);
            result += ", ";
        }
        
        return result;
    }
    
    // methode to pause execution so the players can read output
    private void pauseGame(int seconds) {
        try {
            Thread.currentThread().sleep(seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
