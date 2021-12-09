package zork;

public class CommandWords {
    // a constant array that holds all valid command words
    private static final String validCommands[] = { "north", "east", "south", "west", "quit", "look", "take", "open",
            "close", "drop", "unlock", "cut", "down", "jump", "shoot", "use", "go" };

    /**
     * Check whether a given String is a valid command word. Return true if it is,
     * false if it isn't.
     **/
    public boolean isCommand(String aString) {
        for (String c : validCommands) {
            if (c.equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /*
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for (String c : validCommands) {
            System.out.print(c + "  ");
        }
        System.out.println();
    }
}
