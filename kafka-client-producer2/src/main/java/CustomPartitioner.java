import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

public class CustomPartitioner implements Partitioner {

  @Override
  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
      Cluster cluster) {
    if (keyBytes == null) {
      throw new InvalidRecordException("empty message key");
    }
    if (key.equals("Pangyo")) {
      return 0;
    }

    List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
    int numPartions = partitionInfos.size();
    return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartions;
  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> configs) {

  }
}
