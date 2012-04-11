package bytecode;

public class NumberConversion {
    // byte    8  bits      -127 to 127
	// short   16 bits      -32,768 to 32,767
    // int     32 bits      -2,147,483,648 to 2,147,483,647
    // float   64 bits      s*m*2^e, where s=-1 or 1, 0 <= m <= 224, -149 < e < 104
    public static void shortToByteArray(byte[] array, int index, short value) {
        array[index] = (byte) ((value >> 8) & 0x00FF);
        array[index + 1] = (byte) (value & 0x00FF);
    }
	public static short shortFromByteArray(byte[] array, int index) {
        return (short) (((array[index] << 8) & 0xFF00) | (array[index + 1] & 0x00FF));
    }
    public static void intToByteArray(byte[] array, int index, int value) {
    	array[index + 0] = (byte)(value >> 24 & 0x00FF);
    	array[index + 1] = (byte)(value >> 16 & 0x00FF);
    	array[index + 2] = (byte)(value >> 8 & 0x00FF);
    	array[index + 3] = (byte)(value & 0x00FF);
    }
    public static int intFromByteArray(byte array[], int index) {
    	return
    	  ((array[index + 0] & 0xFF) << 24)
        + ((array[index + 1] & 0xFF) << 16)
        + ((array[index + 2] & 0xFF) << 8)
        +  (array[index + 3] & 0xFF);
    }
    public static void floatToByteArray(byte[] array, int index, float value) {
        intToByteArray(array, index, Float.floatToIntBits(value));
    }
    public static float floatFromByteArray(byte array[], int index) {
         return Float.intBitsToFloat(intFromByteArray(array, index));
    }
}
