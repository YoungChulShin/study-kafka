package study.kafka.connect;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

public class SingleFileSourceConnectorConfig extends AbstractConfig {

  public static final String DIR_FILE_NAME = "file";
  public static final String DIR_FILE_NAME_DEFAULT_VALUE = "/tmp/kafka.txt";
  public static final String DIR_FILE_NAME_DOC = "읽을 파일 경로와 이름";

  public static final String TOPIC_NAME = "topic";
  public static final String TOPIC_NAME_DEFAULT_VALUE = "test";
  public static final String TOPIC_NAME_DOC = "보낼 토픽 이름";

  public static ConfigDef CONFIG = new ConfigDef()
      .define(
          DIR_FILE_NAME,
          Type.STRING,
          DIR_FILE_NAME_DEFAULT_VALUE,
          Importance.HIGH,
          DIR_FILE_NAME_DOC)
      .define(
          TOPIC_NAME,
          Type.STRING,
          TOPIC_NAME_DEFAULT_VALUE,
          Importance.HIGH,
          TOPIC_NAME_DOC);

  public SingleFileSourceConnectorConfig(Map<String, String> props) {
    super(CONFIG, props);
  }
}
