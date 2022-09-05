package study.kafka.streams;

import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class StreamJoinApplication {

  private static final String APPLICATION_NAME = "order-join-application";
  private static final String BOOTSTRAP_SERVERS = "localhost:19092";
  private static final String ADDRESS_TABLE = "address";
  private static final String ORDER_STREAM = "order";
  private static final String ORDER_JOIN_STREAM = "order_join";

  public static void main(String[] args) {

    Properties properties = new Properties();
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    // 스트림 빌더를 만들고
    StreamsBuilder builder = new StreamsBuilder();
    // 테이블 생성
    KTable<String, String> addressTable = builder.table(ADDRESS_TABLE);
    // 스트림 생성
    KStream<String, String> orderStream = builder.stream(ORDER_STREAM);
    // 스트림과 테이블 조인
    // 조인 결과를 ORDER_JOIN_STREAM으로 보낸다
    orderStream
        .join(addressTable, (order, address) -> order + " send to " + address)
        .to(ORDER_JOIN_STREAM);

    KafkaStreams streams = new KafkaStreams(builder.build(), properties);
    streams.start();
  }
}
