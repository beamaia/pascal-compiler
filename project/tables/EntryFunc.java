package tables;

import types.Type;
import java.lang.StringBuilder;
import java.util.Formatter;

public class EntryFunc {
    public String name;
    public VarTable variableTable = new VarTable();
    public int line;

    /** 
    * Constructor for EntryFunc.
    * @param name The name of the entry.
    * @param line The starting line number of the function entry.
    */
    public EntryFunc(String name, int line) {
        this.name = name;
        this.line = line;
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
        
        f.format("EntryFunc %s -- name: %s, starting line: %d\n", name, name, line);
        f.format("-----------\n");
        f.format(variableTable.toString());
        f.close();

		return sb.toString();
    }
}
