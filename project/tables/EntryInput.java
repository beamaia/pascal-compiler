package tables;

import types.Type;

public class EntryInput {
    public String name;
    public int line;
    public Type type;
    public boolean isArray;
    
    /** 
    * Constructor for EntryInput.
    * @param name The name of the entry.
    * @param line The line number of the entry.
    * @param type The type of the entry.
    */
    public EntryInput(String name, int line, Type type, boolean isArray) {
        this.name = name;
        this.line = line;
        this.type = type;
        this.isArray = isArray;
    }
}