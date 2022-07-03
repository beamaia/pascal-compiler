package tables;

import types.Type;
import java.lang.StringBuilder;
import java.util.Formatter;

public class EntryStr {
    String name;
    int line;


    /**
    * Constructor for EntryStr.
    * @param name The name of the entry.
    * @param line The line number of the entry.
     */
    public EntryStr (String name, int line) {
        this.name = name;
        this.line = line;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
        f.format("EntryStr %s -- name: %s, line: %d\n", name, name, line);
        f.close();
		return sb.toString();
    }
}