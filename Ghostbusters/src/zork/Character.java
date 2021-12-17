package zork;

public class Character {
    private String id;
    private String name;
    private String description;
    private String startingroom;
    private int difficultylevel;

    public Character(String id, String name, String description, String startingroom, int difficultylevel){
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingroom=startingroom;
        this.difficultylevel=difficultylevel;
    }
    public String getId(){
        return id;
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
    
}
