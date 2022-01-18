package zork;
import java.io.Serializable;
public class Command {
  private String commandWord; 
  private String secondWord; 

    
  /**
   * @param firstWord the first word in the users command as a string
   * @param secondWord the words following the first word in the users command as a string
   * Create a command object. First and second word must be supplied, but either
   * one (or both) can be null. The command word should be null to indicate that
   * this was a command that is not recognized by this game.
   */
  public Command(String firstWord, String secondWord) {
    commandWord = firstWord;
    this.secondWord = secondWord;
  }

  /**
   * Return the command word (the first word) of this command. If the command was
   * not understood, the result is null.
   *
   @return returns the commandWord
   */
  public String getCommandWord() {
    return commandWord;
  }

  /**
   * Accessor Method
   @return returns the second word of this command. Returns null if there was no second
   * word.
   */
  public String getSecondWord() {
    return secondWord;
  }

  /**
   * Checks if the command was understood
   @return returns true if this command was not understood.
   */
  public boolean isUnknown() {
    return (commandWord == null);
  }

  /**
   * Checks if the command has a second word
   @return returns true if the command has a second word.
   */
  public boolean hasSecondWord() {
    return (secondWord != null && !secondWord.equals(""));
  }
}
