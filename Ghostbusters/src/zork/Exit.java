package zork;

import java.io.Serializable;
/**
 * Exit
 */
public class Exit extends OpenableObject {
  private String direction;
  private String adjacentRoom;

/**
 * 
 * @param direction the direction of the exit as a string
 * @param adjacentRoom the room id corresponding with the direction as a string
 * @param isLocked weather or not the room is locked as a boolean
 * @param keyId the id of the key for this exit as a string
 * creates a new Exit and calls the parent class OpenableObject
 */
  public Exit(String direction, String adjacentRoom, boolean isLocked, String keyId) {
    super(isLocked, keyId);
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

/**
 * 
 * @param direction the direction of the exit as a string
 * @param adjacentRoom the room id corresponding with the direction as a string
 * @param isLocked weather or not the room is locked as a boolean
 * @param keyId the id of the key for this exit as a string
 * @param isOpen weather or not the exit is open as a boolean
 * creates a new Exit and calls the parent class OpenableObject
 */
  public Exit(String direction, String adjacentRoom, boolean isLocked, String keyId, Boolean isOpen) {
    super(isLocked, keyId, isOpen);
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

  /**
   * 
  * @param direction the direction of the exit as a string
  * @param adjacentRoom the room id corresponding with the direction as a string
   * creates a new Exit
   */
  public Exit(String direction, String adjacentRoom) {
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

  /**
   * 
   * @return Exit direction
   */
  public String getDirection() {
    return direction;
  }

  /**
   * 
   * @param direction the direction of the exit as a string
   * sets the Exits direction
   */
  public void setDirection(String direction) {
    this.direction = direction;
  }

  /**
   * 
   * @return Exits adjacent room
   */
  public String getAdjacentRoom() {
    return adjacentRoom;
  }
  /**
   * 
   * @param adjacentRoom the room id corresponding with the direction as a string
   * sets the Exits direction adjacent room
   */
  public void setAdjacentRoom(String adjacentRoom) {
    this.adjacentRoom = adjacentRoom;
  }

}