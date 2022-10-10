package study.kafka.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;

public class SingleFileSourceConnector extends SourceConnector {

  private Map<String, String> configProperties;

  @Override
  public String version() {
    return "1.0";
  }

  @Override
  public void start(Map<String, String> props) {
    this.configProperties = props;
    try {
      new SingleFileSourceConnectorConfig(props);
    } catch (Exception ex) {
      throw new ConnectException(ex.getMessage(), ex);
    }
  }

  @Override
  public Class<? extends Task> taskClass() {
    return SingleFileSourceTask.class;
  }

  // Task가 2개 이상일 경우, Task마다 서로다른 설정 값을 줄 때 사용
  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    List<Map<String, String>> taskConfigs = new ArrayList<>();
    Map<String, String> taskProps = new HashMap<>(configProperties);
    for (int i = 0; i < maxTasks; i++) {
      taskConfigs.add(taskProps);
    }

    return taskConfigs;
  }

  @Override
  public ConfigDef config() {
    return SingleFileSourceConnectorConfig.CONFIG;
  }

  // 커넥터가 종료될 때 필요한 로직
  @Override
  public void stop() {

  }
}
