package study.kafka;

import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class MetricStream {

  private static KafkaStreams streams;

  public static void main(String[] args) {

    Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    Properties properties = new Properties();
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "metric-stream-application");
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
    properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    StreamsBuilder builder = new StreamsBuilder();
    KStream<String, String> metrics = builder.stream("metric-all");
    KStream<String, String>[] metricBranch = metrics.branch(
        (key, value) -> MetricJsonUtils.getMetricName(value).equals("cpu"),
        (key, value) -> MetricJsonUtils.getMetricName(value).equals("memory"));
    metricBranch[0].to("metric-cpu");
    metricBranch[1].to("metric-memory");

    KStream<String, String> cpuFilterMetric =
        metricBranch[0].filter((key, value) -> MetricJsonUtils.getTotalCpuPercent(value) > 0.5);
    cpuFilterMetric.mapValues(MetricJsonUtils::getHostTimestamp).to("metric-cpu-alert");

    streams = new KafkaStreams(builder.build(), properties);
    streams.start();
  }

  private static class ShutdownThread extends Thread {

    @Override
    public void run() {
      streams.close();
    }
  }
}
