package zork;

import java.io.Serializable;

public class CommandWords {
  // a constant array that holds all valid command words
  private static final String validCommands[] = { "north", "east", "south", "west", "quit", "look", "take", "open",
      "drop", "down", "go", "drive", "inventory", "fight", "load", "save", "help", "health", "storage", "up", "sing",
      "unlock","heal","damage"};

  /**
   * Check whether a given String is a valid command word. Return true if it is,
   * false if it isn't.
   **/
  public boolean isCommand(String aString) {
    for (String c : validCommands) {

      if (c.equalsIgnoreCase(aString))
        return true;
    }
    // if we get here, the string was not found in the commands
    return false;
  }

  /*
   * Print all valid commands to System.out.
   */
  public void showAll() {
    for (String c : validCommands) {
      System.out.print(c + "  ");
    }
    System.out.println();
  }

  /**
   * Acessor method that gets the array of valid commands
   * @return // returns the valid commands array
   */
  public String[] getCommands() {
    return validCommands;
  }
}
