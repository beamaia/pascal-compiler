package tables;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class EntryArray extends EntryInput {
    public int start;
    public int end;
    
    /** 
    * Constructor for EntryInput.
    * @param name The name of the entry.
    * @param line The line number of the entry.
    * @param type The type of the entry.
    * @param isArray Whether the entry is an array.
    * @param start The start index of the array.
    * @param end The end index of the array.
    */
    public EntryArray(String name, int line, Type type, int start, int end) {
        super(name, line, type, true);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
        f.format("EntryInput %s -- name: %s, line: %d, type: %s, isArray: %s, start: %d, end: %d\n", name, name, line, type.toString(), isArray, start, end);
        f.close();
		return sb.toString();
    }
}