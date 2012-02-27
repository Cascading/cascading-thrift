package backtype.hadoop;

import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.serializer.Deserializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

/** User: sritchie Date: 2/8/12 Time: 9:50 PM */
public class EnumDeserializer<T extends Serializable> implements Deserializer<T> {
    private DataInputStream inStream;

    public void open(InputStream in) throws IOException {
        inStream = new DataInputStream(in);
    }

    public T deserialize(T obj) throws IOException {
        String klassName = WritableUtils.readString(inStream);
        int val = WritableUtils.readVInt(inStream);

        Class[] args = new Class[] {Integer.TYPE};
        try {
            Class klass = Class.forName(klassName);
            Method method = klass.getDeclaredMethod("findByValue", args);
            Object[] argVec = new Object[]{val};
            return (T) method.invoke(null, argVec);
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
    }

    public void close() throws IOException {
        inStream.close();
    }
}
