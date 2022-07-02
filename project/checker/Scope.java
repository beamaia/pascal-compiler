package types;

public enum Scope {
    GLOBAL {
        public String toString() {
            return "global";
        }
    },

    FUNCTION {
        public String toString() {
            return "function";
        }
    }
}