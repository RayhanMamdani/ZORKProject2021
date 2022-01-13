package zork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.Serializable;

public class Parser {
  private CommandWords commands; // holds all valid command words
  private Scanner in;
  private String commandIgnore = "to,the,those,these,a,an,with,on,through,in,your,yours,his,her,hers,our,ours,their";

  public Parser() {
    commands = new CommandWords();
    in = new Scanner(System.in);
  }

  public Command getCommand() throws java.io.IOException {
    String inputLine = "";

    System.out.print("> ");

    inputLine = in.nextLine();
    ArrayList<String> words = new ArrayList<String>(Arrays.asList(inputLine.split(" ")));
    

    for (int i = words.size()-1; i >= 0; i--) {
      if (commandIgnore.indexOf(words.get(i)) >= 0 && !(words.get(i).indexOf(",") >= 0))
        words.remove(i);
    }
    String secondWord = null;
      if (words.size()>1){
         secondWord = "";
      }
    for (int i = 1; i < words.size(); i++) {
      secondWord += words.get(i);
    }

    if (words.size() != 0 && commands.isCommand(words.get(0)))
    return new Command(words.get(0), secondWord);
    else if (words.size() == 0){
    return new Command(null, null);
    }else
    return new Command(null, secondWord);



    
  }

  public void showCommands() {
    commands.showAll();
  }
}
