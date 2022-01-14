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

    public Save(HashMap<String, Room> Rmap, ArrayList <Item> Imap, ArrayList <Enemy> Elist, Inventory Inv, Room CurRoom, Integer Health){
        rMap = Rmap;
        iMap = Imap;
        eList = Elist;
        inv = Inv;
  
        curRoom = CurRoom;
        health = Health;
      
    }
    public HashMap<String, Room> getRmap(){
        return rMap;
    }
    public ArrayList<Item> getImap(){
        return iMap;
    }
    public ArrayList<Enemy> getElist(){
        return eList;
    }
    public Inventory getInv(){
        return inv;
    }
    public Room getCuRoom(){
        return curRoom;
    }
    public int getHealth(){
        return health;
    }
  
}