package zork;

import java.io.Serializable;
public class Item extends OpenableObject implements Serializable {
  private int weight;
  private int damage;
  private String name;
  private String startingroom;
  private String startingitem;
  private String description;
  private boolean isOpenable;
  private boolean isKey;



  public Item(int weight, String name, boolean isOpenable) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
  }

  public Item (){
    
  }

  
  public Item(int weight, String name, boolean isOpenable, boolean isKey, String startingroom, String startingitem, String description, int damage) {
    this.weight = weight;
    this.name = name;
    this.isOpenable = isOpenable;
    this.startingroom = startingroom;
    this.startingitem = startingitem;
    this.isKey = isKey;
    this.description = description;
    this.damage = damage;
    
  }

  public void open() {
    if (!isOpenable)
      System.out.println("The " + name + " cannot be used as a key.");

  }

  public int getWeight() {
    return weight;
  }

  public String getDescription(){

    return description;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
  public void setStartingRoom(String str){

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

  public String startingRoom(){

    return startingroom;
  }

  public void setOpenable(boolean isOpenable) {
    this.isOpenable = isOpenable;
  }

  public int getDamage(){
    return damage;
  }
  public String getStartingItem(){
    return startingitem;
  }

  

}
