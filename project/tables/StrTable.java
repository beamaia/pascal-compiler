package tables;

import java.util.HashMap;
import tables.EntryInput;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class StrTable extends HashMap<Integer, String> {

    public StrTable() {
        super();
    }
    
    public int add(String s) {
        int idx = this.size();
        this.put(idx, s);
        return idx;
    }
    
    public String get(int i) {
        return this.get(i);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format("Strings table:\n");
        for (int i = 0; i < this.size(); i++) {
            f.format("EntryInput %d -- %s\n", i, this.get(i));
        }
        f.close();
        return sb.toString();
    }
    

}
