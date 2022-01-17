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
 * @param direction
 * @param adjacentRoom
 * @param isLocked
 * @param keyId
 * creates a new Exit and calls the parent class OpenableObject
 */
  public Exit(String direction, String adjacentRoom, boolean isLocked, String keyId) {
    super(isLocked, keyId);
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

/**
 * 
 * @param direction
 * @param adjacentRoom
 * @param isLocked
 * @param keyId
 * @param isOpen
 * creates a new Exit and calls the parent class OpenableObject
 */
  public Exit(String direction, String adjacentRoom, boolean isLocked, String keyId, Boolean isOpen) {
    super(isLocked, keyId, isOpen);
    this.direction = direction;
    this.adjacentRoom = adjacentRoom;
  }

  /**
   * 
   * @param direction
   * @param adjacentRoom
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
   * @param direction
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
   * @param adjacentRoom
   * sets the Exits direction adjacent room
   */
  public void setAdjacentRoom(String adjacentRoom) {
    this.adjacentRoom = adjacentRoom;
  }

}