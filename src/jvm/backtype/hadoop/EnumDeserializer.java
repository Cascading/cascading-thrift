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
    final Method findByValue;

    public EnumDeserializer(Class<TEnum> c) {
        try {
            Class[] args = new Class[] {Integer.TYPE};
            findByValue = c.getDeclaredMethod("findByValue", args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void open(InputStream in) throws IOException {
        inStream = new DataInputStream(in);
    }

    public TEnum deserialize(TEnum obj) throws IOException {
        try {
            int val = WritableUtils.readVInt(inStream);
            Object[] argVec = new Object[]{val};
            return (TEnum) findByValue.invoke(null, argVec);
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
    }

    public void close() throws IOException {
        inStream.close();
    }
}
