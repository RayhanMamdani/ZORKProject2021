package zork;

import java.util.ArrayList;
import java.io.Serializable;

public class Inventory implements Serializable {
  private ArrayList<Item> items;
  private int maxWeight;
  private int currentWeight;

  public Inventory(int maxWeight) {
    this.items = new ArrayList<Item>();
    this.maxWeight = maxWeight;
    this.currentWeight = 0;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public int getCurrentWeight() {
    return currentWeight;
  }

  public void setCurrentWeight(int w){

    currentWeight+=w;
  }

  
  public void minusCurrentWeight(int w){

    currentWeight-=w;
  }


  public boolean addItem(Item item) {
    if (item.getWeight() + currentWeight <= maxWeight)
      return items.add(item);
    else {
      System.out.println("There is no room to add the "+item.getName());
      return false;
    }
  }

  public ArrayList<Item> getInventory(){
    return items;

  }
 

}
