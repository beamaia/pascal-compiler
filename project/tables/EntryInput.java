package tables;

import types.Type;
import java.lang.StringBuilder;
import java.util.Formatter;

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
    * @param isArray Whether the entry is an array.
    */
    public EntryInput(String name, int line, Type type, boolean isArray) {
        this.name = name;
        this.line = line;
        this.type = type;
        this.isArray = isArray;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
        f.format("EntryInput %s -- name: %s, line: %d, type: %s, isArray: %s\n", name, name, line, type.toString(), isArray);
        f.close();
		return sb.toString();
    }
}