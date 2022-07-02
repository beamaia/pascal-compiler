package tables;

import types.Type;
import java.lang.StringBuilder;
import java.util.Formatter;

public class EntryFunc {
    public String name;
    public VarTable variableTable = new VarTable();
    public int start_line, end_line;

    /** 
    * Constructor for EntryFunc.
    * @param name The name of the entry.
    * @param start_line The starting line number of the function entry.
    * @param end_line The ending line number of the function entry.
    */
    public EntryFunc(String name, int start_line, int end_line) {
        this.name = name;
        this.start_line = start_line;
        this.end_line = end_line;
    }

    public boolean addVar(EntryInput entry) {
        return this.variableTable.addVar(entry);
    }

    public boolean containsKey(String key) {
        return variableTable.containsKey(key);

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
        
        f.format("EntryFunc %s -- name: %s, starting line: %d, ending line: %d\n", name, name, start_line, end_line);
        f.format(variableTable.toString());
        f.close();

		return sb.toString();
    }
}
