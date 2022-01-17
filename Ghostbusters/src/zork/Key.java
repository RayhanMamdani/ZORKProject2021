package zork;

import java.io.Serializable;
public class Key extends Item {
  private String keyId;
  /**
   * 
   * @param keyId
   * @param keyName
   * @param weight
   * creates a new key
   */
  public Key(String keyId, String keyName, int weight) {
    super(weight, keyName, false);
    this.keyId = keyId;
  }
  /**
   * @return the Keys id
   */
  public String getKeyId() {
    return keyId;
  }
}

