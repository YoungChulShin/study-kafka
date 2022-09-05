package study.kafka.streams;

import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class StreamFilterApplication {

  private static String APPLICATION_NAME = "streams-application";
  private static String BOOTSTRAP_SERVERS = "localhost:19092";
  private static String STREAM_LOG = "stream_log";
  private static String STREAM_LOG_COPY = "stream_log_filter";

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
    // 크기가 5보다 큰 것만 필터
    KStream<String, String> filterStream = streamLog.filter((k, v) -> v.length() > 5);
    // STREAM_LOG_COPY로 보낸다
    filterStream.to(STREAM_LOG_COPY);

    KafkaStreams streams = new KafkaStreams(builder.build(), properties);
    streams.start();
  }
}
