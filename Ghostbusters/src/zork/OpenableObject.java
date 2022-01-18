package zork;

import java.io.Serializable;
public class OpenableObject implements Serializable {
  private Boolean isLocked; 
  private String keyId;
  private Boolean isOpen;

  /**
   * create a new OpenableObject
   */
  public OpenableObject() {
    this.isLocked = false;
    this.keyId = null;
    this.isOpen = false;
  }

  /**
   * 
   * @param isLocked weather or not the openable object is locked as a boolean
   * @param keyId the openable objects id as a string
   * @param isOpen weather or not the openable object is open as a boolean
   * create a new OpenableObject
   */
  public OpenableObject(boolean isLocked, String keyId, Boolean isOpen) {
    this.isLocked = isLocked;
    this.keyId = keyId;
    this.isOpen = isOpen;
  }
  /**
   * 
   * @param isLocked weather or not the openable object is locked as a boolean
   * @param keyId the openable objects id as a string
   * creates a new OpenableObject
   */
  public OpenableObject(boolean isLocked, String keyId) {
    this.isLocked = isLocked;
    this.keyId = keyId;
    this.isOpen = false;
  }
  /**
   * 
   * @return if the OpenableObject isLocked
   */
  public boolean isLocked() {
    return isLocked;
  }
  /**
   * 
   * @param isLocked as a boolean
   * sets weather or not the OpenableObject is locked
   */
  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }
  /**
   * 
   * @return the OpenableObject id
   */
  public String getKeyId() {
    return keyId;
  }
  /**
   * 
   * @return weather or not the OpenableObject is open
   */
  public boolean isOpen() {
    return isOpen;
  }
  /**
   * 
   * @param isOpen as a boolean
   * sets weather or not the OpenableObject is open
   */
  public void setOpen(boolean isOpen) {
    this.isOpen = isOpen;
  }
}
