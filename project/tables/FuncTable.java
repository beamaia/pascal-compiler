package tables;

import java.util.HashMap;
import tables.EntryInput;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class FuncTable extends HashMap<String, EntryFunc> {

    public boolean addVar(EntryFunc entry) {
        String s = entry.name;
        if ( containsKey(s) ) {
            return false;
        }

        put(s, entry);
        return true;
	}

    public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Function table:\n");
        
		// iterating hashmap<String, EntryInput>
        for (String key : keySet()) {
            f.format("%s", get(key).toString());
        }
		f.close();
		return sb.toString();
	}

}