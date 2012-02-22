package backtype.hadoop;

import org.apache.hadoop.io.serializer.Serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/** User: sritchie Date: 2/8/12 Time: 9:49 PM */
public class EnumSerializer<T extends Serializable> implements Serializer<T> {
    private ObjectOutputStream oos;

    public void open(OutputStream out) throws IOException {
        oos = new ObjectOutputStream(out) {
            @Override protected void writeStreamHeader() {
                // no header
            }
        };
    }

    public void serialize(T obj) throws IOException {
        oos.reset(); // clear (class) back-references
        oos.writeObject(obj);
    }

    public void close() throws IOException {
        oos.close();
    }
}
