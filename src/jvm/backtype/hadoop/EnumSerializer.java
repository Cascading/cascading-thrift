package backtype.hadoop;

import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.serializer.Serializer;
import org.apache.thrift.TEnum;

import java.io.*;

/** User: sritchie Date: 2/8/12 Time: 9:49 PM */
public class EnumSerializer implements Serializer<TEnum> {
    private DataOutputStream outStream;

    public void open(OutputStream out) throws IOException {
        outStream = new DataOutputStream(out);
    }

    public void serialize(TEnum obj) throws IOException {
        WritableUtils.writeVInt(outStream, obj.getValue());
    }

    public void close() throws IOException {
        outStream.close();
    }
}
