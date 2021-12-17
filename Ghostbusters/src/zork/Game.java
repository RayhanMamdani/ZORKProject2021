package zork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Game {

  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();
  public static ArrayList <Item> itemsMap = new ArrayList<Item>();
  private Inventory inventory = new Inventory(100);
  private Parser parser;
  private Room currentRoom;
  private Item currentItem;

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("src\\zork\\data\\rooms.json");
      initItems("src\\zork\\data\\items.json");
      currentRoom = roomMap.get("Bedroom");
      

    } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }

  private void initRooms(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonRooms = (JSONArray) json.get("rooms");

    for (Object roomObj : jsonRooms) {
      Room room = new Room();
      String roomName = (String) ((JSONObject) roomObj).get("name");
      String roomId = (String) ((JSONObject) roomObj).get("id");
      String roomDescription = (String) ((JSONObject) roomObj).get("description");
      room.setDescription(roomDescription);
      room.setRoomName(roomName);

      JSONArray jsonExits = (JSONArray) ((JSONObject) roomObj).get("exits");
      ArrayList<Exit> exits = new ArrayList<Exit>();
      for (Object exitObj : jsonExits) {
        String direction = (String) ((JSONObject) exitObj).get("direction");
        String adjacentRoom = (String) ((JSONObject) exitObj).get("adjacentRoom");
        String keyId = (String) ((JSONObject) exitObj).get("keyId");
        Boolean isLocked = (Boolean) ((JSONObject) exitObj).get("isLocked");
        Boolean isOpen = (Boolean) ((JSONObject) exitObj).get("isOpen");
        Exit exit = new Exit(direction, adjacentRoom, isLocked, keyId, isOpen);
        exits.add(exit);
      }
      room.setExits(exits);
      roomMap.put(roomId, room);
    }
  }


  private void initItems(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonItems = (JSONArray) json.get("items");

    for (Object itemsObj : jsonItems) {
      
      String itemName = (String) ((JSONObject) itemsObj).get("name");
      String id = (String) ((JSONObject) itemsObj).get("id");
      String startingRoom = (String) ((JSONObject) itemsObj).get("startingroom");
      String startingItem = (String) ((JSONObject) itemsObj).get("startingitem");
      String description = (String) ((JSONObject) itemsObj).get("description");
      int weight = (int) (long)((JSONObject) itemsObj).get("weight");
      boolean iskey = (boolean) ((JSONObject) itemsObj).get("iskey");
      boolean isOpenable = (boolean) ((JSONObject) itemsObj).get("isOpenable");
      //Item item = new Item(weight, name, isOpenable)

      Item item = new Item(weight, itemName,isOpenable,iskey,startingRoom, startingItem,description);
      itemsMap.add(item);
    // roomMap.get("Bedroom");
    }
  }


  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();

    boolean finished = false;
    while (!finished) {
      Command command;
      try {
        command = parser.getCommand();
        finished = processCommand(command);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    System.out.println("Thank you for playing.  Good bye.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println("Welcome to Zork!");
    System.out.println("Zork is a new, incredibly boring adventure game.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.longDescription());
 

    int numItems = numItems();
    int i = 0;
    ArrayList <Item> itemsMaptemp = new ArrayList <Item>();
    formatList(itemsMaptemp);
    while (i < numItems){

        System.out.println(itemRoom(itemsMaptemp).getDescription());
     
        i++;
    
    }
    
    
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equalsIgnoreCase("help"))
      printHelp();
    else if (commandWord.equalsIgnoreCase("go"))
      goRoom(command);
      else if (commandWord.equalsIgnoreCase("drive")){
        teleport(command);
    }else if ((commandWord.equalsIgnoreCase("inventory"))){
    showInventory();
    }else if (commandWord.equalsIgnoreCase("take")){ 
      takeObj(command);
      
    }if (commandWord.equalsIgnoreCase("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equalsIgnoreCase("eat")) {
      System.out.println("Do you really think you should be eating at a time like this?");
    }
    return false;
  }

  // implementations of user commands:

  

  private void showInventory() {

    printArr(inventory.getInventory());
  }

  private void printArr(ArrayList<Item> arrayList){
   for (Item i : arrayList){
    System.out.println(i.getName());

   }

  }

  private void takeObj(Command command) {

    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive...
      System.out.println("Take What?");
      

      
      return;
    }else if (command.getSecondWord().equalsIgnoreCase("all")){
      int numItems = numItems();
      int i = 0;
      ArrayList <Item> itemsMaptemp = new ArrayList <Item>();
      formatList(itemsMaptemp);
      while (i < numItems){
        
          Item temp = itemRoom(itemsMaptemp);
          if (inventory.addItem(temp)){
            System.out.println("Item added!");
            itemsMap.remove(getremoveIndex(temp.getName()));
          }

          i++;
       
         
      
      }
    }else if (command.hasSecondWord()){

      String itemName = command.getSecondWord();
      int index = getremoveIndex(itemName);

     if (index != -1 && inventory.addItem(itemsMap.get(index))){
            System.out.println("Item added!");
            itemsMap.remove(index);
          }else{

            System.out.println("You cannot add that item!");
          }
    }

  }

  private int getremoveIndex(String temp){
  int index = -1;
    for (int i = 0; i < itemsMap.size(); i++) {
      
      if (itemsMap.get(i).getName().toLowerCase().indexOf(temp.toLowerCase()) >= 0 && itemsMap.get(i).startingRoom().equals(currentRoom.getRoomName())){

     index = i;

      }


  }

  return index;
  }
  private void teleport(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive...
      System.out.println("Drive Where?");
      
        System.out.println("You can drive from the Garage, Reception, Lobby and Concierge");

      
      return;
    }
    String direction = command.getSecondWord();

    if (canTeleport(command)){

    currentRoom = roomMap.get(format(direction));


    System.out.println(currentRoom.longDescription());

  }else{

    System.out.println("You Cannot Drive Anywhere From Here!");
  }
}

private boolean canTeleport(Command command){
  String direction = command.getSecondWord();
    return (currentRoom.getRoomName().equalsIgnoreCase("Garage") || currentRoom.getRoomName().equalsIgnoreCase("Reception")|| currentRoom.getRoomName().equalsIgnoreCase("Lobby") || direction.equalsIgnoreCase("Concierge")) 
    && (direction.equalsIgnoreCase("Garage") || direction.equalsIgnoreCase("Reception")|| direction.equalsIgnoreCase("Lobby") || direction.equalsIgnoreCase("Concierge"));
}

  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are lost. You are alone. You wander");
    System.out.println("around at Monash Uni, Peninsula Campus.");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      return;
    }

    String direction = command.getSecondWord();

    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction);

    if (nextRoom == null)
      System.out.println("There is no door!");
    else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.longDescription());

     
    int numItems = numItems();
    int i = 0;
    ArrayList <Item> itemsMaptemp = new ArrayList <Item>();
    formatList(itemsMaptemp);
    while (i < numItems){

        System.out.println(itemRoom(itemsMaptemp).getDescription());
     
        i++;
    
    }
  }
  }


  private void formatList(ArrayList<Item> itemsMaptemp) {
    for (Item temp : itemsMap) {
      itemsMaptemp.add(temp);
    }
  }

  
  private Item itemRoom(ArrayList<Item> itemsMaptemp){
int counter = 0;
int indexocc = -1;
    Item temp = new Item();
  for (int i = 0; i < itemsMaptemp.size(); i++){
     
    if (itemsMaptemp.get(i).startingRoom() != null && itemsMaptemp.get(i).getDescription() != null && itemsMaptemp.get(i).startingRoom().equals(currentRoom.getRoomName())){

      temp = itemsMaptemp.get(i);
      indexocc = i;
    }
   }
  if (indexocc == -1)
  return temp;
   return itemsMaptemp.remove(indexocc);

  }

  private int numItems(){
    int counter = 0;
        Item temp = new Item();
      for (int i = 0; i < itemsMap.size(); i++){
         
        if (itemsMap.get(i).startingRoom() != null && itemsMap.get(i).startingRoom().equals(currentRoom.getRoomName())){
    
          //temp = itemsMap.get(i);
          counter++;
        }
       }
    
       return counter;
    
      }

  

  private String format(String str){

    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();

  }


}
