package zork;
import java.io.Serializable;

public class Enemy implements Serializable {
    private String id;
    private String name;
    private String description;
    private String startingroom;
    private int difficultylevel;
    private int damage;
    private boolean isDead = false;


    /**
     * 
     * @param id
     * @param name
     * @param description
     * @param startingroom
     * @param difficultylevel
     * @param damage
     * constructs a new enemy
     */
    public Enemy(String id, String name, String description, String startingroom, int difficultylevel, int damage){
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingroom=startingroom;
        this.difficultylevel=difficultylevel;
        this.damage = damage;
    }
    /**
     * creates an empty enemy
     */
    public Enemy (){

        
    }
    /**
     * 
     * @return Enemy id
     */
    public String getId(){
        return id;
    }
    /**
     * 
     * @return Enemy damage
     */
    public int getDamage(){
        return damage;
    }
    /**
     * 
     * @param isDead
     * sets the attribute isDead
     */
    public void setIsDead(boolean isDead){

        this.isDead = isDead;
    }
    /**
     * 
     * @return Enemy isDead
     */
    public boolean getisDead(){

        return isDead;
    }
    /**
     * 
     * @return Enemy name
     */
    public String getName(){
        return name;
    }
    /**
     * 
     * @return Enemy description
     */
    public String getDescription(){
        return description;
    }
    /**
     * 
     * @return Enemy starting room
     */
    public String getStartingroom(){
        return startingroom;
    }
    /**
     * 
     * @return Enemy difficulty level
     */
    public int getDifficultylevel(){
        return difficultylevel;
    }
    /**
     * 
     * @param level
     * sets the difficulty level of this enemy
     */
    public void setDifficultyLevel(int level){
        difficultylevel = level;
    }
    
}
