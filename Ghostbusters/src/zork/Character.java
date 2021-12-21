package zork;

public class Character {
    private String id;
    private String name;
    private String description;
    private String startingroom;
    private int difficultylevel;
    private int damage;
    private boolean isDead = false;

    public Character(String id, String name, String description, String startingroom, int difficultylevel, int damage){
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingroom=startingroom;
        this.difficultylevel=difficultylevel;
        this.damage = damage;
    }

    public Character (){

        
    }
    public String getId(){
        return id;
    }
    public int getDamage(){
        return damage;
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
    public int getDifficultylevel(){
        return difficultylevel;
    }
    public void setDifficultyLevel(int level){
        difficultylevel = level;
    }
    
}
