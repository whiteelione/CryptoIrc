package IRCLibrary.SharedClasses;

/**
 *
 * @author Илья
 */
public class XOR {
    public static byte[] Encrypt(byte[] origin, byte[] key){
        byte[] result = new byte[origin.length];
        int keyIndex = 0;
        for(int i = 0; i < result.length; i++){
            if(keyIndex == key.length){
                keyIndex = 0;
            }
            result[i] = (byte) (origin[i] ^ key[keyIndex]);
            keyIndex++;
        }
        return result;
    }
}
