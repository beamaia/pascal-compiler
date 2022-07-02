package tables;

import types.Type;

public class EntryArray extends EntryInput {
    public int start;
    public int end;
    
    /** 
    * Constructor for EntryInput.
    * @param name The name of the entry.
    * @param line The line number of the entry.
    * @param type The type of the entry.
    */
    public EntryArray(String name, int line, Type type, int start, int end) {
        super(name, line, type, true);
        this.start = start;
        this.end = end;
    }
}