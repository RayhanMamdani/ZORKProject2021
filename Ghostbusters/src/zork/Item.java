package zork;

import java.io.Serializable;

public class Item extends OpenableObject implements Serializable {
  private int weight;
  private int damage;
  private String id;
  private String name;
  private String startingroom;
  private String startingitem;
  private String description;
  private boolean isOpenable;
  private boolean isWeapon;
  private boolean isKey;
  private boolean canHeal;

  /**
   * 
   * @param weight
   * @param name
   * @param isOpenable
   * create a new Item
   */
  public Item(int weight, String name, boolean isOpenable) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
  }
  /**
   * creates an empty item
   */
  public Item() {

  }
  /**
   * 
   * @param weight
   * @param id
   * @param name
   * @param isOpenable
   * @param isKey
   * @param isWeapon
   * @param canHeal
   * @param startingroom
   * @param startingitem
   * @param description
   * @param damage
   * creates a new Item
   */
  public Item(int weight, String id, String name, boolean isOpenable, boolean isKey, boolean isWeapon, boolean canHeal,
      String startingroom, String startingitem, String description, int damage) {

    this.id = id;
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
    this.startingroom = startingroom;
    this.startingitem = startingitem;
    this.isKey = isKey;
    this.isWeapon = isWeapon;
    this.canHeal = canHeal;
    this.description = description;
    this.damage = damage;
  }
 
  // public void open() {
  //   if (!isOpenable)
  //     System.out.println("The " + name + " cannot be used as a key.");

  // } i dont think we need this

/**
 * 
 * @return the items id
 */
  public String getId() {
    return id;
  }
  /**
   * 
   * @return if the item is a weapon
   */
  public boolean isWeapon() {
    return isWeapon;
  }
  /**
   * 
   * @return if the items can heal
   */
  public boolean canHeal(){
    return canHeal;
  }
  /**
   * 
   * @return the items weight
   */
  public int getWeight() {
    return weight;
  }
  /**
   * 
   * @return the items description
   */
  public String getDescription() {

    return description;
  }
  /**
   * 
   * @param weight
   * sets the items weight
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }
  /**
   * 
   * @param str
   * sets the items starting room
   */
  public void setStartingRoom(String str) {

    this.startingroom = str;
  }
  /**
   * 
   * @return the items name
   */
  public String getName() {
    return name;
  }
  /**
   * 
   * @param name
   * sets the items name
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * 
   * @return if the item is openable
   */
  public boolean isOpenable() {
    return isOpenable;
  }
  /**
   * 
   * @return the items starting room
   */
  public String startingRoom() {

    return startingroom;
  }
  /**
   * 
   * @param isOpenable
   * sets weather or not the item is openable
   */
  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }
  /**
   * 
   * @return the items damage
   */
  public int getDamage() {
    return damage;
  }
  /**
   * 
   * @return the items starting item
   * this will be null if the item starts in a room
   */
  public String getStartingItem() {
    return startingitem;
  }

}
