package backtype.hadoop;

import org.apache.hadoop.io.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/** User: sritchie Date: 2/8/12 Time: 9:50 PM */
public class EnumDeserializer<T extends Serializable> implements Deserializer<T> {
    private ObjectInputStream ois;

    public void open(InputStream in) throws IOException {
        ois = new ObjectInputStream(in) {
            @Override protected void readStreamHeader() {
                // no header
            }
        };
    }

    public T deserialize(T obj) throws IOException {
        try {
            // ignore passed-in object
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e.toString());
        }
    }

    public void close() throws IOException {
        ois.close();
    }
}
