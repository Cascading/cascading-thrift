package backtype.hadoop;

import cascading.tuple.Comparison;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.hadoop.io.serializer.Serialization;
import org.apache.hadoop.io.serializer.Serializer;
import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;

import java.util.Comparator;

/**
 * Adapted from http://issues.apache.org/jira/browse/MAPREDUCE-376
 */
public class ThriftSerialization implements Serialization, Comparison {

    public boolean isTBase(Class c) {
        return TBase.class.isAssignableFrom(c);
    }

    public boolean isTEnum(Class c) {
        return TEnum.class.isAssignableFrom(c);
    }

    public boolean accept(Class c) {
        return isTBase(c) || isTEnum(c);
    }

    public Serializer getSerializer(Class c) {
        return isTBase(c) ? new ThriftSerializer() : new EnumSerializer();
    }

    public Deserializer getDeserializer(Class c) {
        return isTBase(c) ? new ThriftDeserializer(c) : new EnumDeserializer();
    }

    public Comparator getComparator(Class type) {
        return new ThriftComparator(type);
    }
}
