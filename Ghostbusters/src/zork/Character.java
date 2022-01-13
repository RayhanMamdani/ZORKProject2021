package zork;
import java.io.Serializable;
public class Character {
    private String id;
    private String name;
    private String description;
    private String startingroom;

    private boolean isDead = false;
    private String dialogue;

    public Character(String id, String name, String description, String startingroom, String dialogue){
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingroom=startingroom;
        this.dialogue=dialogue;

    }

    public Character (){

        
    }
    public String getId(){
        return id;
    }

    public void setisDead(){

        isDead = true;
    }

    public boolean getisDead(){

        return isDead;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getStartingroom(){
        return startingroom;
    }
    public String getDialogue(){
        return dialogue;
    }

}
