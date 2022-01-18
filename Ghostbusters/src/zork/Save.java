package zork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Save implements Serializable{
    private HashMap<String, Room> rMap = null;
    private ArrayList<Item> iMap = null;
    private ArrayList<Enemy> eList = null;
    private Inventory inv = null;
    private Room curRoom = null;
    private Integer health = 0;
    /**
     * Creates a new Save object
     * @param Rmap the current roomMap arrayList that we are saving
     * @param Imap the current itemMap arrayList that we are saving
     * @param Elist the current enemyList arrayList that we are saving
     * @param Inv the user's current inventory, it is an arrayList
     * @param CurRoom the user's current room, it is a Room object
     * @param Health the user's current health it is an integer
     */
    public Save(HashMap<String, Room> Rmap, ArrayList <Item> Imap, ArrayList <Enemy> Elist, Inventory Inv, Room CurRoom, Integer Health){
        rMap = Rmap;
        iMap = Imap;
        eList = Elist;
        inv = Inv;
  
        curRoom = CurRoom;
        health = Health;
      
    }

    /**
     * Accessor method that gets the room map
     * @return returns the arrayList rmap
     */
    public HashMap<String, Room> getRmap(){
        return rMap;
    }

      /**
     * Accessor method that gets the item map
     * @return returns the arrayList imap
     */
    public ArrayList<Item> getImap(){
        return iMap;
    }

      /**
     * Accessor method that gets the list of enemy alive
     * @return returns the enemy arrayList
     */
    public ArrayList<Enemy> getElist(){
        return eList;
    }

    /**
     * Accessor method that gets the list of inventory
     * @return returns the inventory arrayList
     */
    public Inventory getInv(){
        return inv;
    }

    /**
     * Accessor method that gets the currentRoom
     * @return returns the currentRoom object
     */
    public Room getCuRoom(){
        return curRoom;
    }

        /**
     * Accessor method that gets the health integer
     * @return returns the health integer
     */
    public int getHealth(){
        return health;
    }
  
}