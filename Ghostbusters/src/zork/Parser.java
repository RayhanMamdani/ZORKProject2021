package zork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.Serializable;
import java.lang.reflect.Array;

public class Parser {
  private CommandWords commands; // holds all valid command words
  private Scanner in;
  private ArrayList<String> commandIgnore = new ArrayList<String>(
      Arrays.asList("to", "the", "those", "these", "a", "an", "with", "on", "through", "in", "your", "yours", "his",
          "her", "hers", "our", "ours", "their", "of"));

  /**
   * 
   * Creates a new scanner to read what the user types in the terminal
   * Creates a new commandWords object
   */
  public Parser() {
    commands = new CommandWords();
    in = new Scanner(System.in);
  }

  /**
   * Bulk of the parser which holds what the user types and breaks it up into its
   * first word and the remaining words
   * Filters out any words from the commandIgnore array
   * 
   * @return returns the command object which stores 2 strings
   * @throws java.io.IOException
   */
  public Command getCommand() throws java.io.IOException {
    String inputLine = "";

    System.out.print("> ");

    inputLine = in.nextLine();
    ArrayList<String> words = new ArrayList<String>(Arrays.asList(inputLine.split(" ")));

    for (int i = words.size() - 1; i >= 0; i--) {
      for (String commandWordIgnore : commandIgnore) {
        if (commandWordIgnore.equalsIgnoreCase(words.get(i)))
          words.remove(i);
      }
    }
    String secondWord = null;
    if (words.size() > 1) {
      secondWord = "";
    }
    for (int i = 1; i < words.size(); i++) {
      secondWord += words.get(i);
    }

    if (words.size() != 0 && commands.isCommand(words.get(0)))
      return new Command(words.get(0), secondWord);
    else if (words.size() == 0) {
      return new Command(null, null);
    } else
      return new Command(null, secondWord);

  }

  /**
   * Method to show the commands, it's useful for the help command
   * It is void so it doesn't return anything
   */
  public void showCommands() {
    commands.showAll();
  }
}
