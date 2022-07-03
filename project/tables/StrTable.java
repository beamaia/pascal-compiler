package tables;

import java.util.HashMap;
import tables.EntryStr;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class StrTable extends HashMap<String, EntryStr> {

    public StrTable() {
        super();
    }
    
    public boolean addStr(EntryStr entry) {
        String s = entry.name;
		if ( containsKey(s) ) {
            return false;
        }
        put(s, entry);
        return true;
    }
    
    public String get(int i) {
        return this.get(i);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);

        f.format("Strings table:\n");
        for (String key : keySet()) {
            f.format("%s", get(key).toString());
        }
        f.close();
        return sb.toString();
    }
    

}
