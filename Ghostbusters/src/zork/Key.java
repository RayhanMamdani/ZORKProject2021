package zork;

import java.io.Serializable;
public class Key extends Item {
  private String keyId;
  /**
   * 
   * @param keyId the id of the key as a string
   * @param keyName the name of the key as a string
   * @param weight the weight of the key as a string
   * creates a new key
   */
  public Key(String keyId, String keyName, int weight) {
    super(weight, keyName, false);
    this.keyId = keyId;
  }
  /**
   * @return the id of the key as a string
   */
  public String getKeyId() {
    return keyId;
  }
}

