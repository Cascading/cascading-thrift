package backtype.hadoop;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;


public class ThriftDeserializer implements Deserializer<TBase> {

    private TBase prototype;
    private TIOStreamTransport transport;
    private TProtocol protocol;

    private DataInputStream dis;

    public ThriftDeserializer(Class<TBase> c) {
        try {
            prototype = c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not create " + c, e);
        }

    }

    public void open(InputStream in) throws IOException {
        dis = new DataInputStream(in);
        transport = new TIOStreamTransport(in);
        protocol = new TCompactProtocol(transport);
    }

    public TBase deserialize(TBase t) throws IOException {
        WritableUtils.readVInt(dis);
        TBase object = prototype.deepCopy();
        try {
          object.read(protocol);
        } catch (TException e) {
          throw new IOException(e.toString());
        }
        return object;
    }

    public void close() throws IOException {
        if (transport != null) {
           transport.close();
        }
    }

}
