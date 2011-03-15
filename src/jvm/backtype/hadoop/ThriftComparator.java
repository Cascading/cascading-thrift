package backtype.hadoop;

import cascading.tuple.StreamComparator;
import cascading.tuple.hadoop.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;
import org.apache.thrift.TBase;


public class ThriftComparator implements StreamComparator<BufferedInputStream>, Comparator<TBase>, Serializable {
    public ThriftComparator(Class<TBase> type) {
        
    }

    public int compare(BufferedInputStream lhsStream, BufferedInputStream rhsStream) {
        try {
            //TODO: how can i get rid of creating new stream objects everytime?
            DataInputStream dleft = new DataInputStream(lhsStream);
            DataInputStream dRight = new DataInputStream(rhsStream);
            
            int lhsLen = WritableUtils.readVInt(dleft);
            byte[] lhs = lhsStream.getBuffer();
            int lhsPos = lhsStream.getPosition();

            int rhsLen = WritableUtils.readVInt(dRight);
            byte[] rhs = rhsStream.getBuffer();
            int rhsPos = rhsStream.getPosition();

            lhsStream.skip(lhsLen);
            rhsStream.skip(rhsLen);

            return WritableComparator.compareBytes( lhs, lhsPos, lhsLen, rhs, rhsPos, rhsLen );
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public int compare(TBase o1, TBase o2) {
        return o1.compareTo(o2);
    }

}
