//Sarah Li, March 13, 2022

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Main {
  public static void main(String[] args) {
    
//import Wordle words
    
    String [] wordList = new String[2315];
    try {
      File wordsFile = new File("wordleWords.txt");
      Scanner file_input = new Scanner(wordsFile);
      int i = 0;
      while (file_input.hasNextLine()) {
        wordList[i] = file_input.nextLine();
        i++;
      } //adds each word as an element in the array wordList
      file_input.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

// greets user and explains instructions
    
    System.out.println("Welcome to the Wordle program :)");
    System.out.println("Your goal is to guess a 5 letter word in 6 tries or less. If a letter is in the word and in the correct position, it will turn green. If a letter is in the word but in the wrong position, it will turn yellow. If a letter is not in the word, it will turn grey.");

//initialize variables
    
    boolean restart = true; //used to determine whether user will play again or not
    double wins = 0, losses = 0, rounds = 0;
    int winsInt = 0, lossesInt = 0, roundsInt = 0, winrateInt = 0; //tracks wins, losses, rounds played, and win rate %
    do {
    String word = pickWord(wordList); //call method pickWord
    System.out.println("The random word has been generated. Please enter your guess:");
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_GREEN = "\u001B[42m"; //assigns colors
    char[] arrWord = word.toCharArray(); //places each character in the word into an element of an array
    int guesses = 0;
    for (int i = 0; i < 6; i++) { //gives user 6 guesses
      String userGuess = (userInput(wordList)); //assign method userInput to a String
      if (userGuess.equals(word)) {
        System.out.println(ANSI_GREEN + userGuess +     ANSI_RESET);
        i = 7;
        wins++;
        System.out.println("Correct! You won!"); //user wins and round ends if the user guesses correctly
        winsInt = (int) wins;
        lossesInt = (int) losses; //to convert double to int (gets rid of trailing zero)
        System.out.println("Your total number of wins is: " + winsInt);
        System.out.println("Your total number of losses is: " + lossesInt);
      }
      else {
        guesses = 5 - i; //otherwise, the guess is used up
        System.out.println(checkGuess(arrWord, userGuess)); //call method checkGuess
        if (guesses == 1) {
          System.out.println(guesses + " more guess"); //tells user they have 1 guess left
        }
        else if (guesses == 0) {
          losses++;
          System.out.println("The correct word was: " + word); //tells user the word if they do not guess it correctly within 6 guesses
        winsInt = (int) wins;
        lossesInt = (int) losses;
        System.out.println("Your total number of wins is: " + winsInt);
        System.out.println("Your total number of losses is: " + lossesInt); //tells user number of wins and losses
        }
        else {
          System.out.println(guesses + " more guesses"); //tells user how many guesses they have left
        }
      }
    }
    rounds++;
    double winrate;
    winrate = Math.round((wins/rounds) * 100); //calculate the percentage win rate
    winrateInt = (int) winrate;
    roundsInt = (int) rounds;
    System.out.println("Your total number of rounds played is: " + roundsInt);
    System.out.println("Your win rate is: " + winrateInt + "%"); //tells user total number of rounds played and win rate %
    System.out.println("Would you like to play again? Enter 'yes' to play again or any other input to stop playing.");
    if (playAgain() == false) { //if method playAgain returns false, user does not want to keep playing - program will end
      restart = false;
    }
    } while (restart == true); //all of the above code is run if user wants to keep playing
  }

//methods
  
  //method to pick a random Wordle word from list
  public static String pickWord(String [] wordList) {
    int wordIndex = (int)(Math.floor(Math.random() * 2314)); //generate random element in the array of Wordle words
    return wordList[wordIndex]; 
  }
  
  //method to collect and validate user input
  public static String userInput (String []wordList) {
    Scanner s = new Scanner(System.in);
    String guess = s.nextLine().toLowerCase(); //gets user's guess
    boolean valid = false;
    for (int i = 0; i < 2315; i++) {
      if (guess.equals(wordList[i])) {
        valid = true; //guess is valid if user's guess is equal to any of the words in the list
      }
    }
    while (valid == false) { //if guess is not valid, prompt user to input another guess (entering an invalid word does not use up any of the user's guesses)
      System.out.println("Invalid input, please try again:");
      guess = s.nextLine().toLowerCase();
      valid = false; //reset boolean to false
      for (int i = 0; i < 2315; i++) {
        if (guess.equals(wordList[i])) {
          valid = true; //checks for validity of user's guess again
        }
      }
    }
    return guess;
  }

  //method to check  if each letter in the guess is green, yellow, or grey
  public static String checkGuess (char [] arrWord, String userGuess) {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_GREEN = "\u001B[42m";
    final String ANSI_YELLOW = "\u001B[43m"; //assigning colors
    char[] arrGuess = userGuess.toCharArray(); //convert guess to a character array
    for (int i = 0; i < 5; i++) {
        if (arrGuess[i] == arrWord[i]) {
        System.out.print(ANSI_GREEN + arrGuess[i] +     ANSI_RESET); //if index i of word is the same as index i of guess, highlight green
      }
      else if (arrGuess[i] == arrWord[0] || arrGuess[i] == arrWord[1] || arrGuess[i] == arrWord[2] || arrGuess[i] == arrWord[3] || arrGuess[i] == arrWord[4]) {
        System.out.print(ANSI_YELLOW + arrGuess[i] + ANSI_RESET); //if index i of guess is in any index of word, highlight yellow
      }
      else {
        System.out.print(arrGuess[i]); //otherwise, don't highlight any color
      }
    }
    System.out.println("");
    return "";
  }

  //method to determine whether user wants to play again
  public static boolean playAgain () {
    Scanner s = new Scanner(System.in);
    String replay = s.next();
    if (replay.equals("yes")) {
      return true; //method returns true if user enters 'yes'
    }
    else {
      return false; //any other input will end the program
    }
  }
}
