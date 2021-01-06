# cmd-lieswatter
A command line version of the jackbox game lie swatter. An extra credit assignment for COS 161.
This repo contains an Eclipse project for the game. 

General overview:
 - The questions are taken from text files which were provided by the instructor.
 - No stub code was provided.
 - The program reads the files for the questions and creates Question objects for each question provided.
 - The game can have between 2 and 5 players, and they are set up next. Each player has their own Player object
   associated with them which tracks their score and answers throughout a single game.
 - The keyboard is used for the players to answer questions, and what keys a player uses can be set tosomething 
   other than the defualts which are programmed in.
 - The score a player recives is based on correctness and how long it took them to answer the question.
 - If a player does not answer a question within the alloted time they are randomly given an answer, and
   if it ends up being correct points are still deducted for the elapsed time.
 - Afer all the rounds in a game are complete the winner is the player with the highest score.
 - The players may continue to play another game without exiting the program, and may also edit the players at the end of a game.

Probably the easiest way to run the game is through Eclipse.
