package backtype.hadoop;

import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.serializer.Serializer;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ThriftSerializer implements Serializer<TBase> {
    private TIOStreamTransport transport;
    private TProtocol protocol;
    private ByteArrayOutputStream buffer;

    private OutputStream realOut;
    private DataOutputStream dos;

    public void open(OutputStream out) throws IOException {
        realOut = out;
        dos = new DataOutputStream(realOut);

        buffer = new ByteArrayOutputStream();
        transport = new TIOStreamTransport(buffer);
        protocol = new TCompactProtocol(transport);
    }

    public void serialize(TBase t) throws IOException {
        try {
            buffer.reset();
            t.write(protocol);

            WritableUtils.writeVInt(dos, buffer.size());
            buffer.writeTo(realOut);
        } catch (TException e) {
            throw new IOException(e);
        }
    }

    public void close() throws IOException {
        if(transport!=null) {
            transport.close();
        }
    }
}
