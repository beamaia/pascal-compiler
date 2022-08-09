package tables;


import tables.EntryInput;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class VarTable{
    
    public List<EntryInput> table = new ArrayList<EntryInput>();

    public int size() {
        return table.size();
    }

    public int addVar(EntryInput e) {
        int idxAdded = table.size();
        table.add(e);
        return idxAdded;
    }

    public String getName(int i) {
        return table.get(i).name;
    }

    public int getLine(int i) {
        return table.get(i).line;
    }

    public Type getType(int i) {
        return table.get(i).type;
    }

    public Type getType(String name) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).name.equals(name)) {
                return table.get(i).type;
            }
        }
        return null;
    }


    public EntryInput getEntry(String name) {
        for(EntryInput entry : table) {
            if(entry.name.equals(name)) {
                return entry;
            }
        }
        return null;
    }

    public int getEntryId(String name) {
        for(int i = 0; i < table.size(); i++) {
            EntryInput entry = this.table.get(i);
            if(entry.name.equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public boolean contains(String s) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).name.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format("Variables table:\n");
        for (int i = 0; i < table.size(); i++) {
            f.format("Entry %d -- name: %s, line: %d, type: %s\n", i,
                     getName(i), getLine(i), getType(i).toString());
        }
        f.close();
        return sb.toString();
    }


}