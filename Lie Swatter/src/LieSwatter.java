import java.util.Scanner;

public class LieSwatter {

    public static void main(String[] args) {
        
        Game theGame = new Game();
        
        // files to read the questions from
        String[] files = {"Entertainment.txt",  "Food.txt", "History.txt", "World.txt"};
        
        boolean isPlaying = false;
        Scanner steve = new Scanner(System.in); // steve for old times sake (don't question it)
        
        // just to be polite
        System.out.println("Would you like to play a game of Lie Swatter? (yes/no) ");
        // answer = steve.nextLine();
        isPlaying = isYes(steve.nextLine());
        
        
        // if the user is playing, prepare a game
        if(isPlaying) {
            theGame.makeGame(files);
            theGame.setPlayers();
        }
        
        while(isPlaying) {
            
            // plays a game of Lie Swatter
            theGame.playGame();
            
            System.out.println();
            System.out.println("Would you like to play another game? (yes/no) ");
            
            isPlaying = isYes(steve.nextLine());
            
            // if they are going to play another game
            if(isPlaying) {
                System.out.print("Will this game include all the same players? ");
                // if anything other than a yes
                if(!isYes(steve.nextLine()))
                    theGame.setPlayers(); // calls method to set up the players
                
            }
            // calls a method to create new rounds, and clear data from the last game in the players list if it exists
            theGame.refreshGame();
        }
        
        System.out.println("Goodbye!");
        steve.close();
        
    }
    
    private static boolean isYes(String answer) {
        return answer.equalsIgnoreCase("yes");
    }

}
