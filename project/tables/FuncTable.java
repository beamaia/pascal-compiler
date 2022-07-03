package tables;

import java.util.HashMap;
import tables.EntryInput;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class FuncTable extends HashMap<String, EntryFunc> {

    public boolean addFunc(EntryFunc entry) {
        String s = entry.name;
        if ( containsKey(s) ) {
            return false;
        }

        put(s, entry);
        return true;
	}

    public boolean funcContainsVar(String funcName, String varName) {
        EntryFunc func = get(funcName);
        return func.containsKey(varName);
    }

    public EntryInput getFuncVar(String funcName, String varName) {
        EntryFunc func = get(funcName);

        return func.getVar(varName);
    }

    public boolean addVarToFunc(String funcName, EntryInput entry) {
        EntryFunc func = get(funcName);
        return func.addVar(entry);
    }

    public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Function table:\n");
        
		// iterating hashmap<String, EntryInput>
        for (String key : keySet()) {
            f.format("%s\n", get(key).toString());
        }
		f.close();
		return sb.toString();
	}

}