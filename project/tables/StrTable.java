package tables;

import java.util.List;
import java.util.ArrayList;

import tables.EntryStr;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class StrTable{
	private List<EntryStr> table = new ArrayList<EntryStr>(); 

    public StrTable() {
        super();
    }

    public int size() {
        return table.size();
    }

    
    public int addStr(EntryStr entry) {
		int idxAdded = table.size();
		table.add(entry);
		return idxAdded;
    }
    
    public EntryStr getEntry(String name) {
        for(EntryStr entry : table) {
            if(entry.name.equals(name)) {
                return entry;
            }
        }
        return null;
    }

    public boolean contains(String s) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).name.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public int getEntryId(String name) {
        for(int i = 0; i < table.size(); i++) {
            EntryStr entry = this.table.get(i);
            if(entry.name.equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public String getText(int idx) {
        return table.get(idx).name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        
        f.format("Strings table:\n");
        for (int i = 0; i < table.size(); i++) {
            f.format("Entry %d -- name: %s, line: %d\n", i,
                     table.get(i).name, table.get(i).line);
        }

        f.close();
        return sb.toString();
    }
    
    /*public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);

        f.format("Strings table:\n");
        for (String key : keySet()) {
            f.format("%s", get(key).toString());
        }
        for (int i = 0; i < table.size(); i++) {
			f.format("%s\n", table[i].toString());
		}
        f.close();
        return sb.toString();
    }*/
}
