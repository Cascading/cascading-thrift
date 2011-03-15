package backtype.hadoop;

import cascading.tuple.Comparison;
import java.util.Comparator;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.hadoop.io.serializer.Serialization;
import org.apache.hadoop.io.serializer.Serializer;
import org.apache.thrift.TBase;

/**
 * Adapted from http://issues.apache.org/jira/browse/MAPREDUCE-376
 */
public class ThriftSerialization implements Serialization<TBase>, Comparison<TBase> {

  public boolean accept(Class<?> c) {
    boolean ret = TBase.class.isAssignableFrom(c);
    return ret;
  }

  public Deserializer<TBase> getDeserializer(Class<TBase> c) {
    return new ThriftDeserializer(c);
  }

  public Serializer<TBase> getSerializer(Class<TBase> c) {
    return new ThriftSerializer();
  }

  public Comparator<TBase> getComparator(Class<TBase> type) {
    return new ThriftComparator(type);
  }
}
