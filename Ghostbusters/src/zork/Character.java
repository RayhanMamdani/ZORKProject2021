package zork;
import java.io.Serializable;
public class Character implements Serializable {
    private String id;
    private String name;
    private String description;
    private String startingroom;

    private boolean isDead = false;
    private String dialogue;


    /**
     * 
     * @param id 
     * @param name
     * @param description
     * @param startingroom
     * @param dialogue
     * constructs a new Character
     */
    public Character(String id, String name, String description, String startingroom, String dialogue){
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingroom=startingroom;
        this.dialogue=dialogue;

    }
    /**
     * constructs and empty Character
     */
    public Character (){

        
    }
    /**
     * 
     * @return String
     * returns the characters id
     */
    public String getId(){
        return id;
    }

    /**
     * 
     * @return Character name
     */
    public String getName(){
        return name;
    }
    /**
     * 
     * @return Character description
     */
    public String getDescription(){
        return description;
    }
    /**
     * 
     * @return Character Starting room
     */
    public String getStartingroom(){
        return startingroom;
    }
    /**
     * 
     * @return Character dialogue
     */
    public String getDialogue(){
        return dialogue;
    }

}
