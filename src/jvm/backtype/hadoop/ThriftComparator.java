package backtype.hadoop;

import cascading.tuple.StreamComparator;
import cascading.tuple.hadoop.BufferedInputStream;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;


public class ThriftComparator implements StreamComparator<BufferedInputStream>, Comparator, Serializable {
    public ThriftComparator(Class type) {
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

    public int compare(Object o1, Object o2) {
        if (o1 instanceof TEnum && o2 instanceof TEnum) {
            int v1 = ((TEnum) o1).getValue();
            int v2 = ((TEnum) o2).getValue();
            if(v1 < v2)
                return -1;
            else if(v2 < v1)
                return 1;
            return 0;

        }
        TBase t1 = (TBase) o1;
        TBase t2 = (TBase) o2;
        return t1.compareTo(t2);
    }

}
