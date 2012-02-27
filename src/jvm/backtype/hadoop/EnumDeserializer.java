package backtype.hadoop;

import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.thrift.TEnum;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/** User: sritchie Date: 2/8/12 Time: 9:50 PM */
public class EnumDeserializer implements Deserializer<TEnum> {
    private DataInputStream inStream;
    Class<TEnum> enumClass;

    public EnumDeserializer(Class<TEnum> c) {
        enumClass = c;
    }
    public void open(InputStream in) throws IOException {
        inStream = new DataInputStream(in);
    }

    public TEnum deserialize(TEnum obj) throws IOException {
        int val = WritableUtils.readVInt(inStream);

        Class[] args = new Class[] {Integer.TYPE};
        try {
            Method method = enumClass.getDeclaredMethod("findByValue", args);
            Object[] argVec = new Object[]{val};
            return (TEnum) method.invoke(null, argVec);
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
    }

    public void close() throws IOException {
        inStream.close();
    }
}
