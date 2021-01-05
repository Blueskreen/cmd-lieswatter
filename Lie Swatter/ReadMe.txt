Lie Swatter - a text based Java version
Based on JackBox games Lie Swatter
Written by Casey Pyburn

Game Creation:
 - Running LieSwatter.java will create a Game object with questions based on text files passed in by LieSwatter.java.  The Game object will see each file as a different category, and creates the rounds accordingly.  Please note that running LieSwatter.java from the command line (i.e putting. java LieSwatter.java into cmd) has not been tested, and most likely will not work as intended since it depends on other classes in the project.
 - Each round will have unique questions, however in the rounds that follow, you may see the same question repeated.
 - The user will be prompted both at the beginning and at the end of each successive game if they wish to play more times, and it will also prompt regarding the other players to make sure that if there are changes they are accounted for.  Things such as the score are not maintained between games.

Game play:
 - There can be between 2 and 5 players, and they are assigned two letters on the keyboard to be used to designate their answers to questions as true or false.  
 - In order for the answer to be recorded and scored, they will have to press their key, followed by enter.  Multiple player answers can be read on one line, however in an actual multiplayer setting multiple keyboards would probably be advisable.
 - Once a player has answered a question that answer is final
 - as in the original, the first two rounds are made up of questions from any possible category, and the last is one one specific category, however the points are not doubled for the last round as in the original game.

Scoring:
 - By default each question is worth 1000 points, and there can be no more than 10 seconds spent on any given question.  The score is calculated by dividing the total possible points by the amount of time it took a player to answer.
 - If a player does not answer within 10 seconds they will be randomly assigned an answer, and have about a 50% chance to make 100 points.