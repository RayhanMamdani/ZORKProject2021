package zork;

import java.util.ArrayList;
import java.io.Serializable;

public class Room implements Serializable{

  private String roomName;
  private String description;
  private ArrayList<Exit> exits;
  private Character npc;

  /**
   * Accessor method that gets the arrayList of exits
   * @return returns the arrayList of exit objects
   */
  public ArrayList<Exit> getExits() {
    return exits;
  }

  /**
   * Mutator method that changes the arrayList of exits to the given paramater
   * @param exits  the arrayList that we want to change it to
   * Doesn't return anything since void
   */
  public void setExits(ArrayList<Exit> exits) {
    this.exits = exits;
  }

  /**
   * Acessor method to get the NPC variable
   * @return returns the npc varuabke
   */
  public Character getNpc(){
    return npc;

  }

  /**
   * Mutator method that will change the npc variable
   * @param character it is a character objec and it is the variable we want to change the npc variable to
   */
  public void setCharacter(Character character){
    npc = character;
  }

  /**
   * Checks to see if a room has an npc
   * @return returns false if it doesn't returns true if it does
   */
  public boolean hasNpc(){
    return npc!=null;

  }

  /**
   * Create a room described "description". Initially, it has no exits.
   @param description is something like "a kitchen" or "an open court yard" and it is a string
   */
  public Room(String description) {
    this.description = description;
    exits = new ArrayList<Exit>();
  }

  /**
   * No argument constructor 
   * Sets the roomName to "DEFAULT ROOM"
   * Sets the description to "DEFAULT DESCRIPTION"
   * and It sets the exits array to a new array of exits
   */

  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    exits = new ArrayList<Exit>();
  }


  /**
   * Method that adds exit objects to the exit array
   * @param exit an exit object that will be added to the array
   * @throws Exception
   * Doesn't return anything as it is void
   */
  public void addExit(Exit exit) throws Exception {
    exits.add(exit);
  }

  /**
   * Return the description of the room (the one that was defined in the
   * constructor).
   */
  public String shortDescription() {
    return "Room: " + roomName + "\n\n" + description;
  }

  /**
   * Return a long description of this room, on the form: You are in the kitchen.
   * Exits: north west
   @return returns the description of a room properly formatted
   */
  public String longDescription() {

    return "Room: " + roomName + "\n\n" + description + "\n" + exitString();
  }

  /**
   * Return a string describing the room's exits, for example "Exits: north west
   * ".
   @return returns the string that states the room's exits
   */
  private String exitString() {
    String returnString = "Exits: ";
    for (Exit exit : exits) {
      returnString += exit.getDirection() + " ";
    }

    return returnString;
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction) {
    try {
      for (Exit exit : exits) {

        if (exit.getDirection().equalsIgnoreCase(direction)) {
          String adjacentRoom = exit.getAdjacentRoom();

          return Game.roomMap.get(adjacentRoom);
        }

      }
    } catch (IllegalArgumentException ex) {
      System.out.println(direction + " is not a valid direction.");
      return null;
    }

    System.out.println(direction + " is not a valid direction.");
    return null;
  }

 /**
  * Accessor variable to get the roomname variable
  * @return returns the roomName variable
  */
  public String getRoomName() {
    return roomName;
  }

  /**
   * Mutator method that changes the roomName variable
   * @param roomName the roomName that we want to change the roomName variable to it is a string
   * returns nothing as it is void
   */

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  /**
   * Accessor method that gets the description of a room
   * @return returns the description variable
   */

  public String getDescription() {
    return description;
  }
  /**
   * Mutator method to change the description of a room
   * @param description the description that we want to change to and it is a string
   * returns nothing as it is void
   */

  public void setDescription(String description) {
    this.description = description;
  }
}
