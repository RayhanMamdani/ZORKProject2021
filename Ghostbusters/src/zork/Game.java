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
//line 304 might cause errors
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.Serializable;


public class Game implements Serializable{

  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();
  public static ArrayList <Item> itemsMap = new ArrayList<Item>();
  public static ArrayList <Enemy> EnemiesList = new ArrayList<Enemy>();
  private Inventory inventory = new Inventory(100);
  private Parser parser;
  private Room currentRoom;
  private int yourHealth = 100;
  private boolean isFinished = false;
  
  for (int i = 0; i<8; i++){

  }

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("src\\zork\\data\\rooms.json");
      initItems("src\\zork\\data\\items.json");
      initEnemies("src\\zork\\data\\enemies.json");
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
      int damage = (int) (long)((JSONObject) itemsObj).get("damage");
      boolean iskey = (boolean) ((JSONObject) itemsObj).get("iskey");
      boolean isOpenable = (boolean) ((JSONObject) itemsObj).get("isOpenable");
      //Item item = new Item(weight, name, isOpenable)

      Item item = new Item(weight, itemName,isOpenable,iskey,startingRoom, startingItem,description,damage);
      itemsMap.add(item);
    // roomMap.get("Bedroom");
    }
  }


  

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
      int difficultyLevel = (int) (long)((JSONObject) itemsObj).get("difficultylevel");
      int damage = (int) (long)((JSONObject) itemsObj).get("damage");
      //Item item = new Item(weight, name, isOpenable)

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

    boolean finished = false;
    while (!isFinished) {
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

    int numEnemies = numEnemies();
    int j = 0;
    ArrayList <Enemy> EnemiesListtemp = new ArrayList <Enemy>();
    formatListEnemies(EnemiesListtemp);
    while (j < numEnemies){

        System.out.println(EnemyRoom(EnemiesListtemp).getDescription());
     
        j++;
    
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
    if (hastoFight(command) == false){
      return false;
    }
  
    if (commandWord.equalsIgnoreCase("help"))
      printHelp();
    else if (commandWord.equalsIgnoreCase("go"))
      goRoom(command);
      else if (commandWord.equalsIgnoreCase("drive")){
        teleport(command);
    }else if ((commandWord.equalsIgnoreCase("inventory"))){
    showInventory();

    }else if (commandWord.equalsIgnoreCase("open")){
      openItem(command);


    }else if (commandWord.equalsIgnoreCase("fight")){
      fight(command);


    }else if (commandWord.equalsIgnoreCase("take")){ 
      takeObj(command);
      
    }else if (commandWord.equalsIgnoreCase("drop")){
      dropObj(command);

    }else if(commandWord.equalsIgnoreCase("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        isFinished = true; // signal that we want to quit
    } else if (commandWord.equalsIgnoreCase("eat")) {
      System.out.println("Do you really think you should be eating at a time like this?");
    }
    return false;
  }

  // implementations of user commands:

  private boolean hastoFight(Command command){
    String commandWord = command.getCommandWord();

    for(Enemy temp: EnemiesList){
      if (currentRoom.getRoomName().equals(temp.getStartingroom()) && !commandWord.equalsIgnoreCase("fight")){
        System.out.println("You have to fight the "+temp.getName());
        return false;
      }

    }
    return true;
    }
  
  

  private void fight(Command command) {
    String itemName = command.getSecondWord();
   
    if (!command.hasSecondWord()){
      System.out.println("Fight With What?");
      System.out.println("You can fight with: ");
      printArr(inventory.getInventory());
      System.out.print("Your Hands");
      System.out.println();
      return;
    }

/** 
   
    }
    */
        if (itemName.equalsIgnoreCase("hands")){     
          ArrayList <Enemy> EnemiesListtemp = new ArrayList <Enemy>();
          formatListEnemies(EnemiesListtemp);
          Enemy currEnemy = EnemyRoom(EnemiesListtemp);
          if (currEnemy.getName() == null){
            System.out.print("There are no enemies to fight!");
            System.out.println();
            return;
          }
      int enemyHealth = currEnemy.getDifficultylevel()-5; // change the amount of health the enemy has
      currEnemy.setDifficultyLevel(enemyHealth);
      System.out.println("You hit the "+currEnemy.getName()+"!");
      if (currEnemy.getDifficultylevel() <= 0){
        currEnemy.setisDead();
        EnemiesList = EnemiesListtemp;
        System.out.println("You killed the "+currEnemy.getName()+"!");
      }else{
        System.out.println("It's current health is "+currEnemy.getDifficultylevel());
      }
      if (currEnemy.getisDead() == false){
        yourHealth-=currEnemy.getDamage();
        if (yourHealth <= 0){
          System.out.println("The "+currEnemy.getName()+" delt "+currEnemy.getDamage()+" damage to you. "+"You have died! Game Over!");
          isFinished = true;
        }else{
        System.out.println("The "+currEnemy.getName()+" delt "+currEnemy.getDamage()+" damage to you, you now have "+yourHealth+" health");
        }
      }
    
    }else{
    ArrayList<Item> currInventory = inventory.getInventory();
    int index = getremoveObjIndex(itemName, currInventory);
    if (index == -1 && !itemName.equalsIgnoreCase("Hands")){
  
      System.out.println("You don't have that item in your inventory!");
return;
    }
    Item currWeapon = currInventory.get(index);

    ArrayList <Enemy> EnemiesListtemp = new ArrayList <Enemy>();
    formatListEnemies(EnemiesListtemp);
    Enemy currEnemy = EnemyRoom(EnemiesListtemp);
    if (currEnemy.getName() == null){
      System.out.print("There are no enemies to fight!");
      System.out.println();
      return;
    }
    int damage = currEnemy.getDifficultylevel()-currWeapon.getDamage(); // change the amount of health the enemy has
    currEnemy.setDifficultyLevel(damage);
    System.out.println("You hit the "+currEnemy.getName()+"!");
    if (currEnemy.getDifficultylevel() <= 0){
      currEnemy.setisDead();
      System.out.println("You killed the "+currEnemy.getName()+"!");
      EnemiesList = EnemiesListtemp; // might be an error
    }else{
      System.out.println("It's current health is "+currEnemy.getDifficultylevel());
    }
    if (currEnemy.getisDead() == false){
      yourHealth-=currEnemy.getDamage();
      if (yourHealth <= 0){
        System.out.println("The "+currEnemy.getName()+" delt "+currEnemy.getDamage()+" damage to you. "+"You have died! Game Over!");
        isFinished = true;
      }else{
      System.out.println("The "+currEnemy.getName()+" delt "+currEnemy.getDamage()+" damage to you, you now have "+yourHealth+" health");
      }
    }

    }

  }

  private void openItem(Command command) {
    if (!command.hasSecondWord()){
      System.out.println("Open What?");
      return;
    }
    String currRoomName = currentRoom.getRoomName();
    String secondWord = command.getSecondWord().toLowerCase();
    Item itemToOpen = null;
    for (Item item : itemsMap){ //could make more efficient with a for loop (or using break (but i hate using break))
      if (item.startingRoom()!=null && item.startingRoom().equals(currRoomName) && secondWord.equals((item.getName().toLowerCase()))){
        itemToOpen = item;
      }
    }
    if (itemToOpen == null){
      System.out.println("You cannot open " + secondWord);
      return;
    }
    if (!itemToOpen.isOpenable()){
      System.out.println(itemToOpen.getName()+" is not openable.");
      return;
    }
    if (!itemToOpen.isLocked()){
      itemToOpen.setOpen(true);
      System.out.println(itemToOpen.getName()+" is open.");
      return;
    }
    //IMPLEMENT IF THE ITEM IS LOCKED (CHECK IS THEY HAVE THE RIGHT KEY IN THEIR INVENTORY)
    // for (Item item : currInventory){
    //   if (item.isKey()){
        
    //   }
    // }
    

  }

  

  private void dropObj(Command command) {
    ArrayList<Item> currInventory = inventory.getInventory();
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive...
      System.out.println("Drop What?");
      
      return;
    }else if(currInventory.size() == 0){

      System.out.println("There is nothing to drop!");
    }else if (command.getSecondWord().equalsIgnoreCase("all")){
     
     removeallItems(currInventory);
    }else if (command.hasSecondWord()){

  String itemName = command.getSecondWord();
      int index = getremoveObjIndex(itemName, currInventory);

      if (index != -1){
        Item temp = currInventory.remove(index);

        temp.setStartingRoom(currentRoom.getRoomName());
        itemsMap.add(temp);
        System.out.println("You have dropped the "+temp.getName());

      }else{

        System.out.println("You don't have that object in your inventory!");
      }

    }
  }

  private int getremoveObjIndex(String itemName, ArrayList<Item> currInventory) {
  
    for (int i = 0; i < currInventory.size(); i++) {
      if (currInventory.get(i).getName().toLowerCase().indexOf(itemName.toLowerCase()) >= 0){
        return i;
      }
      i++;
    }
    return -1;
  }

  private void removeallItems(ArrayList<Item> currInventory) {
    for (int i = 0; i < currInventory.size(); i++) {
      
      Item temp = currInventory.remove(i);

      temp.setStartingRoom(currentRoom.getRoomName());
      itemsMap.add(temp);
      System.out.println("You have dropped the "+temp.getName());
    }

  }

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

      boolean noOpenableObjects = true;
      
      for (int i = 0; i<itemsMap.size(); i++){
        Item item = itemsMap.get(i);
        
        if (item.getStartingItem() != null && parentIsValid(item,currentRoom)){
          if (inventory.addItem(item)){
            noOpenableObjects=false;
            
            System.out.println(item.getName()+ " added!");  

            itemsMap.remove(item); //could add arraylist to support multiple openables
   
          }
          
        }
        
      }

      int numItems = numItems();
      int i = 0;
      ArrayList <Item> itemsMaptemp = new ArrayList <Item>();
      formatList(itemsMaptemp);


      while (i < numItems){
        
          Item temp = itemRoom(itemsMaptemp);
          if (inventory.addItem(temp)){
            System.out.println(temp.getName()+" "+"added!");
            itemsMap.remove(getremoveIndex(temp.getName()));
          }

          i++;
       
         
      
      }
     

      if (numItems == 0 && noOpenableObjects){

        System.out.println("There are no items to take!");
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

  private boolean parentIsValid(Item childItem,Room room) {
    String itemName = childItem.getStartingItem().toLowerCase();
    for (Item item : itemsMap) {
      if(itemName.equals((item.getName().toLowerCase()))&&room.getRoomName().toLowerCase().equals(item.startingRoom().toLowerCase())){
        return item.isOpen();
      }
    }
    return false;
  }

  private int getremoveIndex(String temp){
  int index = -1;
    for (int i = 0; i < itemsMap.size(); i++) {
      
      if (itemsMap.get(i).getName().toLowerCase().indexOf(temp.toLowerCase()) >= 0 && (itemsMap.get(i).startingRoom().equals(currentRoom.getRoomName()) || (itemsMap.get(i).getStartingItem()!=null&&parentIsValid(itemsMap.get(i), currentRoom)) )){

     index = i;

      }


  }

  return index;
  }
  private void teleport(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to drive...
      System.out.println("Drive Where?");
      
        System.out.println("You can drive from the Garage, Reception, Abandoned House Foyer and Concierge");

      
      return;
    }
    String direction = command.getSecondWord();

    if (canTeleport(command)){

    currentRoom = roomMap.get(format(direction));


    System.out.println(currentRoom.longDescription());
    
    int numItems = numItems();
    int i = 0;
    ArrayList <Item> itemsMaptemp = new ArrayList <Item>();
    formatList(itemsMaptemp);
    while (i < numItems){

        System.out.println(itemRoom(itemsMaptemp).getDescription());
     
        i++;
    
    }
   
    int numEnemies = numEnemies();
    int j = 0;
    ArrayList <Enemy> EnemiesListtemp = new ArrayList <Enemy>();
    formatListEnemies(EnemiesListtemp);
    while (j < numEnemies){

        System.out.println(EnemyRoom(EnemiesListtemp).getDescription());
     
        j++;
    
    }

  }else{

    System.out.println("You Cannot Drive Anywhere From Here!");
  }
}

private boolean canTeleport(Command command){
  String direction = command.getSecondWord();
    return (currentRoom.getRoomName().equalsIgnoreCase("Garage") || currentRoom.getRoomName().equalsIgnoreCase("Reception")|| currentRoom.getRoomName().equalsIgnoreCase("abandoned house") || currentRoom.getRoomName().equalsIgnoreCase("Concierge")) 
    && (direction.equalsIgnoreCase("Garage") || direction.equalsIgnoreCase("Reception")|| direction.equalsIgnoreCase("abandoned house") || direction.equalsIgnoreCase("Concierge"));
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
    int numEnemies = numEnemies();
    int j = 0;
    ArrayList <Enemy> EnemiesListtemp = new ArrayList <Enemy>();
    formatListEnemies(EnemiesListtemp);
    while (j < numEnemies){

        System.out.println(EnemyRoom(EnemiesListtemp).getDescription());
     
        j++;
    
    }
  }
  }


  private void formatList(ArrayList<Item> itemsMaptemp) {
    for (Item temp : itemsMap) {
      itemsMaptemp.add(temp);
    }
  }

  private void formatListEnemies(ArrayList<Enemy> EnemiesList2) {
    for (Enemy temp : EnemiesList) {
      EnemiesList2.add(temp);
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

  private Enemy EnemyRoom(ArrayList<Enemy> EnemyListTemp){
    int counter = 0;
    int indexocc = -1;
        Enemy temp = new Enemy();
      for (int i = 0; i < EnemyListTemp.size(); i++){
         
        if (EnemyListTemp.get(i).getStartingroom() != null && EnemyListTemp.get(i).getDescription() != null && EnemyListTemp.get(i).getStartingroom().equals(currentRoom.getRoomName())){
    
          temp = EnemyListTemp.get(i);
          indexocc = i;
        }
       }
      if (indexocc == -1)
      return temp;
       return EnemyListTemp.remove(indexocc);
  
      }

  private int numItems(){
    int counter = 0;
        Item temp = new Item();
      for (int i = 0; i < itemsMap.size(); i++){
         
        if (itemsMap.get(i).startingRoom() != null && itemsMap.get(i).startingRoom().equals(currentRoom.getRoomName())&&!itemsMap.get(i).isOpenable()){
    
          //temp = itemsMap.get(i);
          counter++;
        }
       }
    
       return counter;
    
      }

      private int numEnemies(){
        int counter = 0;
            Item temp = new Item();
          for (int i = 0; i < EnemiesList.size(); i++){
             
            if (EnemiesList.get(i).getStartingroom() != null && EnemiesList.get(i).getStartingroom().equals(currentRoom.getRoomName())){
        
              //temp = itemsMap.get(i);
              counter++;
            }
           }
        
           return counter;
        
          }

  

  private String format(String str){

    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();

  }
  public void save(){
    ArrayList<Object> data = new ArrayList<Object>();
    
    try{
      FileOutputStream fileOut = new FileOutputStream("data.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(data);
      out.close();
      fileOut.close();
      System.out.println("Data saved in data.ser")
    }catch(IOException i){
      i.printStackTrace();
    }
  }

  public void load(){
    ArrayList<Object> open = new ArrayList<Object>();
    try{
      FileInputStream fileIn = new FileInputStream("data.ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      open = (ArrayList<Object>)in.readObject();
      in.close();
      fileIn.close();
    }catch(InvalidClassException e){
      e.printStackTrace();
    }catch(IOException i){
      i.printStackTrace();
    }catch(ClassNotFoundException c){
      c.printStackTrace();
      return;
    }
    roomMap = (HashMap)open.get(0);
    itemsMap = (ArrayList<Item>)open.get(1);
    EnemiesList = (ArrayList<Enemy>)open.get(2);
    inventory = (Inventory)open.get(3);
    parser = (Parser)open.get(4);
    currentRoom = (Room)open.get(5);

    }
    /*public static HashMap<String, Room> roomMap = new HashMap<String, Room>();
    public static ArrayList <Item> itemsMap = new ArrayList<Item>();
    public static ArrayList <Enemy> EnemiesList = new ArrayList<Enemy>();
    private Inventory inventory = new Inventory(100);
    private Parser parser;
    private Room currentRoom;
    private int yourHealth = 100;
    private boolean isFinished = false;*/
    //obj name = (objcast)open.get(num);
  }

}
