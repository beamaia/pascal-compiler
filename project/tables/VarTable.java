package tables;


import tables.EntryInput;

import java.util.HashMap;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class VarTable extends HashMap<String, EntryInput> {
    
	public boolean addVar(EntryInput entry) {
        String s = entry.name;
		if ( containsKey(s) ) {
            return false;
        }
        put(s, entry);
        return true;
	}

    public String getName(String s) {
        return get(s).name;
    }

    public int getLine(String s) {
        return get(s).line;
    }

    public Type getType(String s) {
        return get(s).type;
    }

    public boolean getIsArray(String s) {
        return get(s).isArray;
    }

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Variables table:\n");
		// iterating hashmap<String, EntryInput>
        for (String key : keySet()) {
            f.format("%s", get(key).toString());
        }
		f.close();
		return sb.toString();
	}

}