package tables;

import types.Type;
import java.lang.StringBuilder;
import java.util.Formatter;

public class EntryFunc {
    public String name;
    public VarTable variableTable = new VarTable();
    public int line;
    public Type type;

    /** 
    * Constructor for EntryFunc.
    * @param name The name of the entry.
    * @param line The starting line number of the function entry.
    * @param type The return type of the function.
    */
    public EntryFunc(String name, int line, Type type) {
        this.name = name;
        this.line = line;
        this.type = type;
    }

    public boolean addVar(EntryInput entry) {
        int idx = this.variableTable.addVar(entry);
        return idx != -1;
    }

    public boolean containsKey(String key) {
        return variableTable.contains(key);

    }

    public EntryInput getVar(String name) {
        return variableTable.getEntry(name);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
        
        f.format("EntryFunc %s -- name: %s, starting line: %d, type: %s\n", name, name, line, type);
        f.format("-----------\n");
        f.format(variableTable.toString());
        f.close();

		return sb.toString();
    }
}
