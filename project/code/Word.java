package code;

import java.math.BigInteger;

/*
 * Implementação de uma palavra na memória em bytes.
 */

public final class Word {
    private byte[] bytes;
    
    // uint32 to word
    public static Word fromInt(int value) {
        Word word = new Word();
        word.bytes = new byte[] {
                        (byte)(value >>> 24),
                        (byte)(value >>> 16),
                        (byte)(value >>> 8 ),
                        (byte)(value >>> 0 )
                  	 };
        return word;
    }
    
    // float to word
    public static Word fromFloat(float value) {
        int intBits = Float.floatToIntBits(value);
        return fromInt(intBits);
    }
    
    public int toInt() {
        return ((bytes[0] & 0xFF) << 24) | 
               ((bytes[1] & 0xFF) << 16) | 
               ((bytes[2] & 0xFF) << 8 ) | 
               ((bytes[3] & 0xFF) << 0 );
    }
    
    public float toFloat() {
        int intBits = this.toInt();
        return Float.intBitsToFloat(intBits);
    }
    
    public static String floatToHex(float f) {
        // change the float to raw integer bits(according to the OP's requirement)
        int intValue = Float.floatToRawIntBits(f);
        String bin = Integer.toBinaryString(intValue);
        BigInteger b = new BigInteger(bin, 2);
        return "0x" + b.toString(16);
    }

    public static String integerToHex(int i) {
        String bin = Integer.toBinaryString(i);
        BigInteger b = new BigInteger(bin, 2);
        return "0x" + b.toString(16);
    }

}