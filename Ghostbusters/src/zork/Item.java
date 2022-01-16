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

  public Item(int weight, String name, boolean isOpenable) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
  }

  public Item() {

  }

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

  public void open() {
    if (!isOpenable)
      System.out.println("The " + name + " cannot be used as a key.");

  }

  public String getId() {
    return id;
  }

  public boolean isWeapon() {
    return isWeapon;
  }

  public boolean canHeal(){
    return canHeal;
  }

  public int getWeight() {
    return weight;
  }

  public String getDescription() {

    return description;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setStartingRoom(String str) {

    this.startingroom = str;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isOpenable() {
    return isOpenable;
  }

  public String startingRoom() {

    return startingroom;
  }

  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public int getDamage() {
    return damage;
  }

  public String getStartingItem() {
    return startingitem;
  }

}
