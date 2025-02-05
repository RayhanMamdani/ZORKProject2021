package zork;

import java.util.ArrayList;
import java.io.Serializable;

public class Inventory implements Serializable {
  private ArrayList<Item> items;
  private int maxWeight;
  private int currentWeight;

  /**
   * 
   * @param maxWeight the maximum weight the user can hold as an int
   * creates a new Inventory
   */
  public Inventory(int maxWeight) {
    this.items = new ArrayList<Item>();
    this.maxWeight = maxWeight;
    this.currentWeight = 0;
  }
  /**
   * 
   * @return Inventory's max weight
   */
  public int getMaxWeight() {
    return maxWeight;
  }

  /**
   * 
   * @return Inventory's current weight
   */
  public int getCurrentWeight() {
    return currentWeight;
  }
  /**
   * 
   * @param w the amount of weight to be added as an int
   * adds the current weight of the inventory with the int param w
   */
  public void setCurrentWeight(int w){

    currentWeight+=w;
  }

  /**
   * 
   * @param w the amount of weight to subtract as an int
   * subtracts the int param w from the current weight of the inventory 
   */  
  public void minusCurrentWeight(int w){

    currentWeight-=w;
  }

  /**
   * 
   * @param item the Item to add
   * @return if the item was added to Items returns true. if not returns false
   */
  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight)
      return items.add(item);
    else {
      System.out.println("There is no room to add the "+item.getName());
      return false;
    }
  }
  /**
   * Accessor method that gets the items arrayList
   * @return returns the items arrayList
   * 
   */
  public ArrayList<Item> getInventory(){
    return items;

  }
 

}
