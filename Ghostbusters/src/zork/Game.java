package zork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Game {

  private static final String SAVE_FILE = "src\\zork\\data\\data1.ser";
  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();
  public static ArrayList<Item> itemsMap = new ArrayList<Item>();
  public static ArrayList<Enemy> EnemiesList = new ArrayList<Enemy>();
  private Inventory inventory = new Inventory(100);
  private Parser parser;
  private Room currentRoom;
  private Integer yourHealth = 100;
  private boolean isFinished = false;

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("src\\zork\\data\\rooms.json");
      initNpcs("src\\zork\\data\\npc.json");
      initItems("src\\zork\\data\\items.json");
      initEnemies("src\\zork\\data\\enemies.json");

      currentRoom = roomMap.get("Bedroom");

    } catch (Exception e) {
      e.printStackTrace();
    }
    parser = new Parser();
  }

/**
 * Inits the NPCS and puts the NPCS in their corresponding room
 * (since it is void there is no return type.)
 * @param filePath // the file of path the npcs.json file
 * @throws IOException
 * @throws ParseException
 */
  private void initNpcs(String filePath) throws IOException, ParseException {
    Path path = Path.of(filePath);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonRooms = (JSONArray) json.get("npc");
    for (Object roomObj : jsonRooms) {
      String id = (String) ((JSONObject) roomObj).get("id");
      String name = (String) ((JSONObject) roomObj).get("name");
      String description = (String) ((JSONObject) roomObj).get("description");
      String startingRoom = (String) ((JSONObject) roomObj).get("startingroom");
      String dialogue = (String) ((JSONObject) roomObj).get("dialogue");

      roomMap.get(startingRoom).setCharacter(new Character(id, name, description, startingRoom, dialogue));
    }

  }

  /**
 * Inits the Rooms and puts the Rooms in their proper place
 * (since it is void there is no return type.)
 * @param filePath // the file of path the rooms.json file
 * @throws IOException
 * @throws ParseException
 */

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
        String keyId = "Key" + (String) ((JSONObject) exitObj).get("keyId");
        Boolean isLocked = (Boolean) ((JSONObject) exitObj).get("isLocked");
        Boolean isOpen = (Boolean) ((JSONObject) exitObj).get("isOpen");
        Exit exit = new Exit(direction, adjacentRoom, isLocked, keyId, isOpen);
        exits.add(exit);
      }
      room.setExits(exits);
      roomMap.put(roomId, room);
    }
  }

  /**
 * Inits the Items and adds it into the items array. 
 * (since it is void there is no return type.)
 * @param filePath // the file of path the items.json file
 * @throws IOException
 * @throws ParseException
 */
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
      int weight = (int) (long) ((JSONObject) itemsObj).get("weight");
      int damage = (int) (long) ((JSONObject) itemsObj).get("damage");
      boolean iskey = (boolean) ((JSONObject) itemsObj).get("iskey");
      boolean isWeapon = (boolean) ((JSONObject) itemsObj).get("isWeapon");
      boolean canHeal = (boolean) ((JSONObject) itemsObj).get("canHeal");
      boolean isOpenable = (boolean) ((JSONObject) itemsObj).get("isOpenable");
      // Item item = new Item(weight, name, isOpenable)

      Item item = new Item(weight, id, itemName, isOpenable, iskey, isWeapon, canHeal, startingRoom, startingItem,
          description,
          damage);
      itemsMap.add(item);
      // roomMap.get("Bedroom");
    }
  }

   /**
 * Inits the enemies and puts them into an array. 
 * (since it is void there is no return type.)
 * @param filePath // the file of path the enemies.json file
 * @throws IOException
 * @throws ParseException
 */

  private void initEnemies(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonItems = (JSONArray) json.get("characters");

    for (Object itemsObj : jsonItems) {

      String name = (String) ((JSONObject) itemsObj).get("name");
      String id = (String) ((JSONObject) itemsObj).get("id");
      String startingRoom = (String) ((JSONObject) itemsObj).get("startingroom");
      String description = (String) ((JSONObject) itemsObj).get("description");
      int difficultyLevel = (int) (long) ((JSONObject) itemsObj).get("difficultylevel");
      int damage = (int) (long) ((JSONObject) itemsObj).get("damage");
      // Item item = new Item(weight, name, isOpenable)

      Enemy Enemy = new Enemy(id, name, description, startingRoom, difficultyLevel, damage);
      // roomMap.get("Bedroom");
      EnemiesList.add(Enemy);
    }
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();

    if (currentRoom.hasNpc()) {
      System.out.println();
      System.out.println();
      System.out.println(currentRoom.getNpc().getDialogue());
    }

    while (!isFinished) {
      Command command;
      try {
        command = parser.getCommand();
        processCommand(command);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (!(yourHealth > 0)) {
        isFinished = true;
        System.out.println("You died. Better luck next time!");
      } else if (EnemiesList.get(0).getisDead()){
        isFinished = true;
        System.out.println("You win. You have killed Stay Puft");
      }

    }
    System.out.println("Thank you for playing. Goodbye!");
  }

  /**
   * Print out the opening message for the player.
   * Prints out the current room description, the items that are in the current room and the enemies that are in the current room. 
   * No return type since it is void
   */
  
  private void printWelcome() {
    System.out.println();
    System.out.println("Hello! Welcome to Ghostbusters. A new, innovative and totally original concept!");
    System.out.println();
    System.out.println("In this game, progress through the map fighting monsters while");
    System.out.println("finding new characters, weapons and items to help you throughout your journey!");
    System.out.println();
    System.out.println("Control your character by typing simple commands into the terminal. If you ever get stuck,");
    System.out.println("type 'help' to see a list of commands available to you.");
    System.out.println();
    System.out.println(currentRoom.longDescription());

    int numItems = numItems();
    int i = 0;
    ArrayList<Item> itemsMaptemp = new ArrayList<Item>();
    formatList(itemsMaptemp);
    while (i < numItems) {

      System.out.println(itemRoom(itemsMaptemp).getDescription());

      i++;

    }

    int numEnemies = numEnemies();
    int j = 0;
    ArrayList<Enemy> EnemiesListtemp = new ArrayList<Enemy>();
    formatListEnemies(EnemiesListtemp);
    while (j < numEnemies) {

      System.out.println(EnemyRoom(EnemiesListtemp).getDescription());

      j++;

    }

  }

  /**
   * Given a command, process (that is: execute) the command. It is void so there is no return type
   */
  private void processCommand(Command command) {
    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return;
    }

    String commandWord = command.getCommandWord();
    if (hastoFight(command) == false && !commandWord.equals("quit")) {
      return;
    }


    if (commandWord.equalsIgnoreCase("go"))
      goRoom(command);
    else if (commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("east")
        || commandWord.equalsIgnoreCase("south") || commandWord.equalsIgnoreCase("north") || commandWord.equals("down")
        || commandWord.equals("up")) {
      goRoom(command);
    } else if (commandWord.equalsIgnoreCase("sing")) {
      System.out.println("Your singing sucks.....");
    } else if (commandWord.equalsIgnoreCase("drive")) {
      teleport(command);
    } else if ((commandWord.equalsIgnoreCase("inventory"))) {
      showInventory();

    } else if (commandWord.equalsIgnoreCase("open")) {
      openItem(command);

    } else if (commandWord.equalsIgnoreCase("fight")) {
      fight(command);

    } else if (commandWord.equalsIgnoreCase("help")) {

      printHelp();

    } else if (commandWord.equalsIgnoreCase("heal")) {
      heal(command);

    } else if (commandWord.equalsIgnoreCase("storage")) {
      System.out
          .println("You are currently using " + inventory.getCurrentWeight() + "% of your backpack's total storage.");
      ArrayList<Item> currInventory = inventory.getInventory();
      for (Item item : currInventory) {
        System.out.println("Your " + item.getName() + " takes up " + item.getWeight() + "%.");
      }

    } else if (commandWord.equalsIgnoreCase("health")) {
      System.out.println("Your health is " + yourHealth);
    } else if (commandWord.equalsIgnoreCase("take")) {
      takeObj(command);

    } else if (commandWord.equalsIgnoreCase("drop")) {
      dropObj(command);

    } else if (commandWord.equalsIgnoreCase("unlock")) {
      unlock(command);

    } else if (commandWord.equalsIgnoreCase("save")) {
      save();

    } else if (commandWord.equalsIgnoreCase("load")) {

      load();
    } else if (commandWord.equalsIgnoreCase("look")) {
      System.out.println(currentRoom.longDescription());

      int numItems = numItems();
      int i = 0;
      ArrayList<Item> itemsMaptemp = new ArrayList<Item>();
      formatList(itemsMaptemp);
      while (i < numItems) {

        System.out.println(itemRoom(itemsMaptemp).getDescription());

        i++;

      }

    } else if (commandWord.equalsIgnoreCase("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        isFinished = true; // signal that we want to quit
    } else if (commandWord.equalsIgnoreCase("eat")) {
      System.out.println("Do you really think you should be eating at a time like this?");
    }
    return;
  }

  /**
   * This method is for the heal command. It updates the user's health based on the item they want to use for healing
   * 
   * @param command the command object
   * 
   * This is void so it doesn't return anything.
   */

  private void heal(Command command) {
    ArrayList<Item> currInventory = inventory.getInventory();
    if (!command.hasSecondWord()) {
      System.out.println("Heal with what?");
      if (currInventory.size() == 0) {
        System.out.println("You have no items in your inventory that you can use to heal with.");
      } else {
        System.out.println("You can heal with:");
        for (Item i : currInventory) {
          if (i.canHeal())
            System.out.println(i.getName());
        }
      }
      return;
    } else {
      String secondWord = command.getSecondWord();

      int index = getremoveObjIndex(secondWord, currInventory);
      if (index == -1 || !(currInventory.get(index).canHeal())) {
        System.out.println("You cannot use that item!");
        return;
      }

      yourHealth += currInventory.get(index).getDamage();
      System.out
          .println("You have gained " + currInventory.get(index).getDamage() + ". Your health is now " + yourHealth);
      currInventory.remove(index);
    }

  }

 /**
  * This method is for the unlock command. It unlocks locked doors if a user has the correct key and if they state which key they would like to unlock the door with
  * @param command // the command object
  * it is void so there is no return type
  */

  private void unlock(Command command) {
    boolean unlocked = false;
    String keyName = "";
    if (!command.hasSecondWord()) {
      System.out.println("Unlock with what?");
      return;
    } else if (command.getSecondWord().equalsIgnoreCase("key")) {

      System.out.println("Unlock with what key?");
      return;
    } else {
      String secondWord = command.getSecondWord();
      ArrayList<Item> currInventory = inventory.getInventory();
      int index = getremoveObjIndex(secondWord, currInventory);
      if (index == -1) {
        System.out.println("You don't have that item in your inventory!");
        return;
      }

      for (Exit exit : currentRoom.getExits()) {
        if (exit.getKeyId().equals(currInventory.get(index).getId())) {
          exit.setLocked(false);
          unlocked = true;
          keyName = currInventory.get(index).getName();
        }
      }

    }
    if (unlocked) {
      System.out.println("You unlocked a room with your " + keyName + "!");
    }

  }

  /**
   * This is a method to check if the user can use commands (they won't be able to if they haven't defeated the enemy in that room)
   * @param command the command object
   * @return returns false if they can't use any commands otherwise returns true
   */

  private boolean hastoFight(Command command) {
    String commandWord = command.getCommandWord();

    for (Enemy temp : EnemiesList) {
      if (currentRoom.getRoomName().equals(temp.getStartingroom())
          && !commandWord.equalsIgnoreCase("fight")) {
        System.out.println("You have to fight the " + temp.getName());
        return false;
      }

    }
    return true;
  }

  /**
   * This method is for the fight command. It runs the fight sequence for both the enemies and the players.
   * @param command // the command object
   * it is void so no return type
   */
  private void fight(Command command) {
    String itemName = command.getSecondWord();

    if (!command.hasSecondWord()) {
      System.out.println("Fight With What?");
      System.out.println("You can fight with: ");
      for (Item i : inventory.getInventory()) {
        if (i.isWeapon())
          System.out.println(i.getName());
      }
      System.out.print("Your Hands");
      System.out.println();
      return;
    }

    /**
     * 
     * }
     */
    if (itemName.equalsIgnoreCase("hands")) {
      ArrayList<Enemy> EnemiesListtemp = new ArrayList<Enemy>();
      formatListEnemies(EnemiesListtemp);
      Enemy currEnemy = EnemyRoom(EnemiesListtemp);
      if (currEnemy.getName() == null) {
        System.out.print("There are no enemies to fight!");
        System.out.println();
        return;
      }
      int enemyHealth = currEnemy.getDifficultylevel() - 5; // change the amount of health the enemy has
      currEnemy.setDifficultyLevel(enemyHealth);
      System.out.println("You hit the " + currEnemy.getName() + "!");
      if (currEnemy.getDifficultylevel() <= 0) {
        currEnemy.setisDead();
        EnemiesList = EnemiesListtemp;
        System.out.println("You killed the " + currEnemy.getName() + "!");
      } else {
        System.out.println("It's current health is " + currEnemy.getDifficultylevel());
      }
      if (currEnemy.getisDead() == false) {
        yourHealth -= currEnemy.getDamage();
        System.out.println("The " + currEnemy.getName() + " dealt " + currEnemy.getDamage()
            + " damage to you, you now have " + yourHealth + " health");
      }
    }

    else {
      ArrayList<Item> currInventory = inventory.getInventory();
      int index = getremoveObjIndex(itemName, currInventory);
      if (index == -1 && !itemName.equalsIgnoreCase("Hands")) {

        System.out.println("You don't have that item in your inventory!");
        return;
      } else if (!currInventory.get(index).isWeapon()) {
        System.out.println("That is not a weapon, silly!");
        return;
      }
      Item currWeapon = currInventory.get(index);

      ArrayList<Enemy> EnemiesListtemp = new ArrayList<Enemy>();
      formatListEnemies(EnemiesListtemp);
      Enemy currEnemy = EnemyRoom(EnemiesListtemp);
      if (currEnemy.getName() == null) {
        System.out.print("There are no enemies to fight!");
        System.out.println();
        return;
      }
      int damage = currEnemy.getDifficultylevel() - currWeapon.getDamage(); // change the amount of health the enemy has
      currEnemy.setDifficultyLevel(damage);
      System.out.println("You hit the " + currEnemy.getName() + "!");
      if (currEnemy.getDifficultylevel() <= 0) {
        currEnemy.setisDead();
        System.out.println("You killed the " + currEnemy.getName() + "!");
        EnemiesList = EnemiesListtemp; // might be an error
      } else {
        System.out.println("It's current health is " + currEnemy.getDifficultylevel());
      }
      if (currEnemy.getisDead() == false) {
        yourHealth -= currEnemy.getDamage();
        System.out.println("The " + currEnemy.getName() + " dealt " + currEnemy.getDamage()
            + " damage to you, you now have " + yourHealth + " health");
      }
    }

  }

  /**
   * This is the open item method. It makes sure that the user can open an item and they can take the object inside of it.
   * @param command // the command object
   * This is a void method so no return type
   */
  private void openItem(Command command) {
    if (!command.hasSecondWord()) {
      System.out.println("Open What?");
      return;
    }
    String currRoomName = currentRoom.getRoomName();
    String secondWord = command.getSecondWord().toLowerCase();
    Item itemToOpen = null;
    for (Item item : itemsMap) { 
      if (item.startingRoom() != null && item.startingRoom().equals(currRoomName)
          && secondWord.equals((item.getName().toLowerCase()))) {
        itemToOpen = item;
      }
    }
    if (itemToOpen == null) {
      System.out.println("The " + secondWord + " is not in this room");
      return;
    }
    if (!itemToOpen.isOpenable()) {
      System.out.println(itemToOpen.getName() + " is not openable.");
      return;
    }
    if (!itemToOpen.isLocked()) {
      itemToOpen.setOpen(true);
      System.out.println(itemToOpen.getName() + " is open.");
      return;
    }

  }

   /**
   * This is the drop item method. It takes the item that the user wants to drop and it puts in in the current room. It also gets rid of that weight from their inventory
   * @param command // the command object
   * This is a void method so no return type
   */

  private void dropObj(Command command) {
    ArrayList<Item> currInventory = inventory.getInventory();
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive.
      System.out.println("Drop What?");

      return;
    } else if (currInventory.size() == 0) {

      System.out.println("There is nothing to drop!");
    } else if (command.getSecondWord().equalsIgnoreCase("all")) {

      removeallItems(currInventory);
    } else if (command.hasSecondWord()) {

      String itemName = command.getSecondWord();
      int index = getremoveObjIndex(itemName, currInventory);

      if (index != -1) {
        Item temp = currInventory.remove(index);

        temp.setStartingRoom(currentRoom.getRoomName());
        inventory.minusCurrentWeight(temp.getWeight());
        itemsMap.add(temp);
        System.out.println("You have dropped the " + temp.getName());

      } else {

        System.out.println("You don't have that object in your inventory!");
      }

    }
  }

  /**
   * This gets the index of a specified itemName in the current inventory array
   * @param itemName // the name of the item we are looking for
   * @param currInventory // the current inventory
   * @return returns the index of the object or -1 if it cannot be found in the array
   */
  private int getremoveObjIndex(String itemName, ArrayList<Item> currInventory) {

    for (int i = 0; i < currInventory.size(); i++) {
      if (currInventory.get(i).getName().toLowerCase().replaceAll("\\s+", "").indexOf(itemName.toLowerCase()) >= 0) {
        return i;
      }

    }
    return -1;
  }

  /**
   * The method removeallItems removes all the items in the user's inventory
   * It also makes sure to subtract it from the user's current weight
   * @param currInventory the current inventory array list 
   * No return type since it is void
   */
  private void removeallItems(ArrayList<Item> currInventory) {
    for (int i = currInventory.size() - 1; i >= 0; i--) {

      Item temp = currInventory.remove(i);
      inventory.minusCurrentWeight(temp.getWeight());
      temp.setStartingRoom(currentRoom.getRoomName());
      itemsMap.add(temp);
      System.out.println("You have dropped the " + temp.getName());
    }

  }

  /**
   * A simple method that prints the user's inventory
   * No return type since it is void
   */
  private void showInventory() {

    printItems(inventory.getInventory());
  }


  /**
   * This method prints all of the items a user has in their inventory
   * @param itemsarr // The user's current inventory, the array is an item object array
   */
  private void printItems(ArrayList<Item> itemsarr) {
    for (Item i : itemsarr) {
      System.out.println(i.getName());

    }

    if (itemsarr.size() == 0) {
      System.out.println("You have no items in your inventory. ");
    }

  }

  /**
   * This method is for the take command, it allows user's to take items in a given room
   * @param command // the command object
   * It is void so it doesn't return anything
   */

  private void takeObj(Command command) {

    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive...
      System.out.println("Take What?");

      return;
    } else if (command.getSecondWord().equalsIgnoreCase("all")) {

      boolean noOpenableObjects = true;

      for (int i = 0; i < itemsMap.size(); i++) {
        Item item = itemsMap.get(i);

        if (item.getStartingItem() != null && parentIsValid(item, currentRoom)) {
          if (inventory.addItem(item)) {
            noOpenableObjects = false;
            inventory.setCurrentWeight(itemsMap.get(i).getWeight());
            System.out.println(item.getName() + " added!");

            itemsMap.remove(i); // could add arraylist to support multiple openables

          }

        }

      }

      int numItems = numItems();
      int j = 0;
      ArrayList<Item> itemsMaptemp = new ArrayList<Item>();
      formatList(itemsMaptemp);

      while (j < numItems) {

        Item temp = itemRoom(itemsMaptemp);
        if (inventory.addItem(temp)) {
          inventory.setCurrentWeight(temp.getWeight());
          System.out.println(temp.getName() + " " + "added!");
          itemsMap.remove(getremoveIndex(temp.getName()));
        }

        j++;

      }

      if (numItems == 0 && noOpenableObjects) {

        System.out.println("There are no items to take!");
      }

    } else if (command.hasSecondWord()) {

      String itemName = command.getSecondWord();

      int index = getremoveIndex(itemName);

      if (index != -1 && inventory.addItem(itemsMap.get(index))) {
        System.out.println("Item added!");
        inventory.setCurrentWeight(itemsMap.get(index).getWeight());
        itemsMap.remove(index);
      } else {

        System.out.println("You cannot add that item!");
      }
    }

  }

  private boolean parentIsValid(Item childItem, Room room) {
    String itemName = childItem.getStartingItem().toLowerCase();
    for (Item item : itemsMap) {
      if (itemName.equals((item.getName().toLowerCase()))
          && room.getRoomName().toLowerCase().equals(item.startingRoom().toLowerCase())) {
        return item.isOpen();
      }
    }
    return false;
  }

  /**
   * This is a method that returns the index of an item name in the items array
   * @param item // the item name that the user wants to use
   * @return returns the index of the item in the array, if it is not there it will return -1.
   */
  private int getremoveIndex(String item) {
    int index = -1;
    for (int i = 0; i < itemsMap.size(); i++) {

      if (itemsMap.get(i).getName().toLowerCase().replaceAll("\\s+", "")
          .indexOf(item.toLowerCase().replaceAll("\\s+", "")) >= 0
          && (itemsMap.get(i).startingRoom().equals(currentRoom.getRoomName())
              || (itemsMap.get(i).getStartingItem() != null && parentIsValid(itemsMap.get(i), currentRoom)))) {

        index = i;

      }

    }

    return index;
  }

  /**
   * This is a method used for teleporting between different locations (ie. driving). It takes in the command object and 
   * changes the current room to the room that the user would like to go to (note you can only drive from the Garage, Reception, Lobby and Concierge)
   * @param command // the command object
   */
  private void teleport(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive...
      System.out.println("Drive Where?");

      System.out.println("You can drive from the Garage, Reception, Lobby and Concierge");

      return;
    }
    String direction = command.getSecondWord();

    if (canTeleport(command)) {

      currentRoom = roomMap.get(format(direction));

      System.out.println(currentRoom.longDescription());

      if (currentRoom.hasNpc()) {
        System.out.println(currentRoom.getNpc().getDialogue()); // if there is a character print their dialog
      }

      int numItems = numItems();
      int i = 0;
      ArrayList<Item> itemsMaptemp = new ArrayList<Item>();
      formatList(itemsMaptemp);
      while (i < numItems) {

        System.out.println(itemRoom(itemsMaptemp).getDescription());

        i++;

      }

      int numEnemies = numEnemies();
      int j = 0;
      ArrayList<Enemy> EnemiesListtemp = new ArrayList<Enemy>();
      formatListEnemies(EnemiesListtemp);
      while (j < numEnemies) {

        System.out.println(EnemyRoom(EnemiesListtemp).getDescription());

        j++;

      }

    } else {

      System.out.println("You Cannot Drive Anywhere From Here!");
    }
  }

  /**
   * Thus method checks if a user can teleport you can only drive/teleport from the Garage, Reception, Lobby and Concierge)
   * @param command the command object
   * @return
   */
  private boolean canTeleport(Command command) {
    String direction = command.getSecondWord();
    return (currentRoom.getRoomName().equalsIgnoreCase("Garage")
        || currentRoom.getRoomName().equalsIgnoreCase("Reception")
        || currentRoom.getRoomName().equalsIgnoreCase("Abandoned House Lobby")
        || currentRoom.getRoomName().equalsIgnoreCase("Concierge"))
        && (direction.equalsIgnoreCase("Garage") || direction.equalsIgnoreCase("Reception")
            || direction.equalsIgnoreCase("Lobby") || direction.equalsIgnoreCase("Concierge"));
  }

  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {

    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    String commandWord = command.getCommandWord();
    String direction = command.getSecondWord();
    if (!command.hasSecondWord() && !(commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("east")
        || commandWord.equalsIgnoreCase("south") || commandWord.equalsIgnoreCase("north") || commandWord.equals("down")
        || commandWord.equals("up"))) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      return;
    }

    if (commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("east")
        || commandWord.equalsIgnoreCase("south") || commandWord.equalsIgnoreCase("north") || commandWord.equals("down")
        || commandWord.equals("up")) {
      direction = commandWord;
    }

    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction);

    ArrayList<Exit> currRoomExits = currentRoom.getExits();
    Exit chosenExit = null;
    for (Exit exit : currRoomExits) {
      if (exit.getDirection().equalsIgnoreCase(direction))
        chosenExit = exit;
    }

    if (chosenExit == null) {
      yourHealth -= 5;
      System.out.println("You walked into a wall and lost 5 hp! You now have " + yourHealth);
    } else if (chosenExit.isLocked()) {
      System.out.println("This room is locked... Maybe you need a key 🤔");
    } else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.longDescription());
      if (currentRoom.hasNpc()) {
        System.out.println(currentRoom.getNpc().getDialogue());
      }

      int numItems = numItems();
      int i = 0;
      ArrayList<Item> itemsMaptemp = new ArrayList<Item>();
      formatList(itemsMaptemp);
      while (i < numItems) {

        System.out.println(itemRoom(itemsMaptemp).getDescription());

        i++;

      }
      int numEnemies = numEnemies();
      int j = 0;
      ArrayList<Enemy> EnemiesListtemp = new ArrayList<Enemy>();
      formatListEnemies(EnemiesListtemp);
      while (j < numEnemies) {

        System.out.println(EnemyRoom(EnemiesListtemp).getDescription());

        j++;

      }
    }
  }

  /**
   * Copies all of the elements in the itemsMap into a temporary array list of items
   * @param itemsMaptemp // the empty array list that will have all of the copied elements at the end
   * This is a void method so it won't return anything
   */
  private void formatList(ArrayList<Item> itemsMaptemp) {
    for (Item i : itemsMap) {
      itemsMaptemp.add(i);
    }
  }

   /**
   * Copies all of the elements in the Enemies array into a temporary array list of enemies
   * @param itemsMaptemp // the empty array list that will have all of the copied elements at the end
   * This is a void method so it won't return anything
   */

  private void formatListEnemies(ArrayList<Enemy> EnemiesListtemp) {
    for (Enemy temp : EnemiesList) {
      EnemiesListtemp.add(temp);
    }
  }

  /**
   * Removes the item from the room after a user has picked it up
   * @param itemsMaptemp // the copied arraylist that has all the elements from the itemsmap array
   * @return returns an empty item object if the index is -1 other wise returns the object that it removed.
   */
  private Item itemRoom(ArrayList<Item> itemsMaptemp) {
    int counter = 0;
    int indexocc = -1;
    Item temp = new Item();
    for (int i = 0; i < itemsMaptemp.size(); i++) {

      if (itemsMaptemp.get(i).startingRoom() != null && itemsMaptemp.get(i).getDescription() != null
          && itemsMaptemp.get(i).startingRoom().equals(currentRoom.getRoomName())) {

        temp = itemsMaptemp.get(i);
        indexocc = i;
      }
    }
    if (indexocc == -1)
      return temp;
    return itemsMaptemp.remove(indexocc);

  }

    /**
   * Removes the enemy from the room after a user has killed them
   * @param itemsMaptemp // the copied arraylist that has all the elements from the enemies array
   * @return returns an empty item object if the index is -1 other wise returns the object that it removed.
   */
  private Enemy EnemyRoom(ArrayList<Enemy> EnemyListTemp) {
    int counter = 0;
    int indexocc = -1;
    Enemy temp = new Enemy();
    for (int i = 0; i < EnemyListTemp.size(); i++) {

      if (EnemyListTemp.get(i).getStartingroom() != null && EnemyListTemp.get(i).getDescription() != null
          && EnemyListTemp.get(i).getStartingroom().equals(currentRoom.getRoomName())) {

        temp = EnemyListTemp.get(i);
        indexocc = i;
      }
    }
    if (indexocc == -1)
      return temp;
    return EnemyListTemp.remove(indexocc);

  }

  /**
   * This is a method that is used to see how many items there are in a given room
   * @return // returns the number of items in the given room
   */
  private int numItems() {
    int counter = 0;
    Item temp = new Item();
    for (int i = 0; i < itemsMap.size(); i++) {

      if (itemsMap.get(i).startingRoom() != null
          && itemsMap.get(i).startingRoom().equalsIgnoreCase(currentRoom.getRoomName())
          && !itemsMap.get(i).isOpenable()) {

        counter++;
      }
    }

    return counter;

  }

  /**
   * This is a method that is used to see how many enemies there are in a given room
   * @return // returns the number of enemies in a given room
   */

  private int numEnemies() {
    int counter = 0;
    Item temp = new Item();
    for (int i = 0; i < EnemiesList.size(); i++) {

      if (EnemiesList.get(i).getStartingroom() != null
          && EnemiesList.get(i).getStartingroom().equals(currentRoom.getRoomName())) {

      
        counter++;
      }
    }

    return counter;

  }

  /**
   * This is a method that is used for teleporation, it formats the user's input so that way any input can be accepted
   * @return // returns the string after it has been formatted
   */
  private String format(String str) {

    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();

  }

  /**
   * This method saves the game, it saves where you are, how many enemies you've killed, how many items you have picked up etc.
   * Since it is void it doesn't return anything
   */
  public void save() {
    Save data = new Save(roomMap, itemsMap, EnemiesList, inventory, currentRoom, yourHealth);

    try {
      FileOutputStream fileOut = new FileOutputStream(SAVE_FILE);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(data);
      out.close();
      fileOut.close();
      System.out.println("Data saved in data.ser");
    } catch (IOException i) {
      i.printStackTrace();
    }
  }

    /**
   * This method loads the data of the last save.
   * Since it is void it doesn't return anything
   */

  public void load() {
    Save save = null;
    try {
      FileInputStream fileIn = new FileInputStream(SAVE_FILE);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      save = (Save) in.readObject();
      in.close();
      fileIn.close();
    } catch (InvalidClassException e) {
      e.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
      return;
    }
    roomMap = save.getRmap();
    itemsMap = save.getImap();
    EnemiesList = save.getElist();
    inventory = save.getInv();
    currentRoom = save.getCuRoom();
    yourHealth = save.getHealth();
    System.out.println("Game has succesfully loaded");
    System.out.println();
    System.out.println();
    System.out.println(currentRoom.longDescription());

    int numItems = numItems();
    int i = 0;
    ArrayList<Item> itemsMaptemp = new ArrayList<Item>();
    formatList(itemsMaptemp);
    while (i < numItems) {

      System.out.println(itemRoom(itemsMaptemp).getDescription());

      i++;

    }

  }

}
