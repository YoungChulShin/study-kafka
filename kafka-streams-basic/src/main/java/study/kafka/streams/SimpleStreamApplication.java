package study.kafka.streams;

import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class SimpleStreamApplication {

  private static String APPLICATION_NAME = "streams-application";
  private static String BOOTSTRAP_SERVERS = "localhost:19092";
  private static String STREAM_LOG = "stream_log";
  private static String STREAM_LOG_COPY = "stream_log_copy";

  public static void main(String[] args) {

    Properties properties = new Properties();
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    // StreamBuilder 생성
    StreamsBuilder builder = new StreamsBuilder();
    // STREAM_LOG 로 받아서
    KStream<String, String> streamLog = builder.stream(STREAM_LOG);
    // STREAM_LOG_COPY로 보낸다
    streamLog.to(STREAM_LOG_COPY);

//    try(KafkaStreams streams = new KafkaStreams(builder.build(), properties)) {
//      streams.start();
//    }

    KafkaStreams streams = new KafkaStreams(builder.build(), properties);
    streams.start();
  }
}
