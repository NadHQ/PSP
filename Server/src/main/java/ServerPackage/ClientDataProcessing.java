package ServerPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ClientDataProcessing {
    default void WriteCharToStream(String str, DataOutputStream outputStream) throws IOException {
        {
            outputStream.writeInt(str.length());
            for (int i = 0; i < str.length(); i++) {
                outputStream.writeChar(str.charAt(i));
            }
        }
    }
    public default String ReadCharToString(DataInputStream inputStream) throws IOException {
        Integer var1 = inputStream.readInt();
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < var1; i++) {
            str.append(inputStream.readChar());
        }
        String str1 = str.toString();
        return str1;
    }
}
