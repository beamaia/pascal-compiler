package code;

public class Registers {
    public String name;
    public int value;

    public Registers() {
        this.name = "";
        this.value = -1;
    }

    public Registers(String name, int value) {
        this.name = name; // VAR NAME
        this.value = value; // REG VALUE
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }


    public class IntRegister extends Registers {
        public IntRegister(String name, int value) {
            super(name, value);
            char typeReg = name.charAt(0);

            if (typeReg != 'r') {
                throw new IllegalArgumentException("IntRegister name must start with 'r'");
            }

        }
    }

    public class FloatRegister extends Registers {
        public FloatRegister(String name, int value) {
            super(name, value);
            char typeReg = name.charAt(0);

            if (typeReg != 'f') {
                throw new IllegalArgumentException("FloatRegister name must start with 'f'");
            }

        }
    }

    public class TempRegister extends Registers {
        public TempRegister(String name, int value) {
            super(name, value);
            char typeReg = name.charAt(0);

            if (typeReg != 't') {
                throw new IllegalArgumentException("TempRegister name must start with 't'");
            }

        }
    }
}