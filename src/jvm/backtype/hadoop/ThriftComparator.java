package backtype.hadoop;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;

import java.io.Serializable;
import java.util.Comparator;


public class ThriftComparator implements Comparator, Serializable {

    public ThriftComparator(Class type) {
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
