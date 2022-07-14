package tables;

import java.util.List;
import java.util.ArrayList;

import tables.EntryInput;
import tables.EntryFunc;

import types.Type;

import java.lang.StringBuilder;
import java.util.Formatter;

public class FuncTable{

    public List<EntryFunc> table = new ArrayList<EntryFunc>();

    public int addFunc(EntryFunc e) {
        int idxAdded = table.size();
        table.add(e);
        return idxAdded;
    }
    
    public EntryFunc getFunc(String name) {
        for (int i= 0; i < table.size(); i++) {
            EntryFunc func = table.get(i);
            if (func.name == name) {
                return func;
            }
        }

        return null;
    }

    public EntryFunc getFunc(int idx) {
        if (table.size() > idx && idx >= 0) {
            return table.get(idx);
        }

        return null;
    }

    public int getEntryId(String name) {
        for(int i = 0; i < table.size(); i++) {
            EntryFunc entry = this.table.get(i);
            if(entry.name.equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public VarTable getVarTable(int idx) {
        return this.table.get(idx).variableTable;
    }
    

    public VarTable getVarTable(String name) {
        for (int i=0; i < table.size(); i++) {
            if (table.get(i).name == name)
                return this.table.get(i).variableTable;
        }
        return null;
    }
    

    public boolean funcContainsVar(String funcName, String varName) {
        EntryFunc func = this.getFunc(funcName);

        if (func!= null && func.name == funcName) {
            return func.containsKey(varName);
        }

        return false;
    }

    public EntryInput getFuncVar(String funcName, String varName) {
        EntryFunc func =  this.getFunc(funcName);
        EntryInput vars = func.getVar(varName);
        return vars;
    }

    public int addVarToFunc(String funcName, EntryInput entry) {
        EntryFunc func = this.getFunc(funcName);

        if (func != null) {
            return func.addVar(entry);
        }

        System.out.println(funcName + " doesnt have var " + entry.name);

        return -1;
    }

    public String getName(int idx) {
        return table.get(idx).name;
    }

    public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("Function table:\n");
        
        for (int i = 0; i < table.size(); i++) {
            f.format("%s\n", table.get(i).toString());
        }
		f.close();
		return sb.toString();
	}

}