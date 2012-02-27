package backtype.hadoop;

import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.serializer.Serializer;
import org.apache.thrift.TEnum;

import java.io.*;

/** User: sritchie Date: 2/8/12 Time: 9:49 PM */
public class EnumSerializer<T extends Serializable> implements Serializer<T> {
    private DataOutputStream outStream;

    public void open(OutputStream out) throws IOException {
        outStream = new DataOutputStream(out);
    }

    public void serialize(T obj) throws IOException {
        TEnum enumObject = (TEnum) obj;
        WritableUtils.writeString(outStream, obj.getClass().getName());
        WritableUtils.writeVInt(outStream, enumObject.getValue());
    }

    public void close() throws IOException {
        outStream.close();
    }
}
