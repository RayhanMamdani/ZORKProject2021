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
     * Constructor that creates a new character object
     * @param id the character id from the json and it is a string
     * @param name the name from the json and it is a string
     * @param description the description from the json and it is a string
     * @param startingroom the starting room from the json and it is a string
     * @param dialogue the dialogue from the json and it is a string
     */
    public Character(String id, String name, String description, String startingroom, String dialogue){
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingroom=startingroom;
        this.dialogue=dialogue;

    }

    /**
     * Empty no argument constructor
     */
    public Character (){

        
    }

    /**
     * Accessor method that gets the id
     * @return returns the id
     */
    public String getId(){
        return id;
    }

        /**
     * Method that changes the isDead variable to true
     * Void so no return type
     */
    public void setisDead(){

        isDead = true;
    }
       /**
     * Acessor Method that changes the isDead variable
     @return returns the is dead variable
     */

    public boolean getisDead(){

        return isDead;
    }

         /**
     * Acessor Method that gets the name variable
     @return returns the name variable
     */

    public String getName(){
        return name;
    }

        /**
     * Acessor Method that gets the description variable
     @return returns the description variable
     */
    public String getDescription(){
        return description;
    }

        /**
     * Acessor Method that gets the startingroom variable
     @return returns the startingroom variable
     */
    public String getStartingroom(){
        return startingroom;
    }

        /**
     * Acessor Method that gets the dialogue variable
     @return returns the dialogue variable
     */
    public String getDialogue(){
        return dialogue;
    }

}
