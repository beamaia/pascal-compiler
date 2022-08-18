package code;

import java.util.ArrayList;
import java.util.List;

public class RegistersOrg {
    public List<Registers> intReg = new ArrayList<Registers>();
    public List<Registers> floatReg = new ArrayList<Registers>();
    public List<Registers> tempReg = new ArrayList<Registers>();

    int intRegAmount;
    int floatRegAmount;
    int tempRegAmount;
    
    public RegistersOrg() {
        this.intRegAmount = 0;
        this.floatRegAmount = 0;
        this.tempRegAmount = 0;
    }

    public int addIntReg(String var) {
        Registers reg = new Registers(var, intRegAmount);
        this.intReg.add(reg);
        this.intRegAmount++;

        return intRegAmount - 1;
    }

    public int addFloatReg(String var) {
        Registers reg = new Registers(var, floatRegAmount);
        this.floatReg.add(reg);
        this.floatRegAmount++;

        return floatRegAmount - 1;
    }

    public int addTempReg(String var) {
        Registers reg = new Registers(var, tempRegAmount);
        this.tempReg.add(reg);
        this.tempRegAmount++;

        return tempRegAmount - 1;
    }

    public int getIntRegAmount() {
        return this.intRegAmount;
    }

    public int getFloatRegAmount() {
        return this.floatRegAmount;
    }

    public int getTempRegAmount() {
        return this.tempRegAmount;
    }

    public int getIntReg(String regName) {

        for (Registers reg : this.intReg) {
            if (reg.getName().equals(regName)) {
                return reg.value;
            }
        }
        return -1;
        
    }

    public int getFloatReg(String regName) {

        for (Registers reg : this.floatReg) {
            if (reg.getName().equals(regName)) {
                return reg.value;
            }
        }
        return -1;
        
    }

    public int getTempReg(String regName) {

        for (Registers reg : this.tempReg) {
            if (reg.getName().equals(regName)) {
                return reg.value;
            }
        }
        return -1;
        
    }

    public boolean contains(String regName) {
        for (Registers reg : this.intReg) {
            if (reg.getName().equals(regName)) {
                return true;
            }
        }
        for (Registers reg : this.floatReg) {
            if (reg.getName().equals(regName)) {
                return true;
            }
        }
        for (Registers reg : this.tempReg) {
            if (reg.getName().equals(regName)) {
                return true;
            }
        }

        return false;
    }
}
