import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientAdmin {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Logger logger = LoggerFactory.getLogger(ClientAdmin.class);

    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");

    AdminClient admin = AdminClient.create(properties);

    logger.info("=====Get broker information");
    for (Node node : admin.describeCluster().nodes().get()) {
      logger.info("node: {}", node);
      ConfigResource configResource = new ConfigResource(Type.BROKER, node.idString());
      DescribeConfigsResult describeConfigsResult = admin.describeConfigs(Collections.singleton(configResource));
      describeConfigsResult.all().get().forEach((broker, config) -> {
        config.entries().forEach(configEntry -> {
          logger.info(configEntry.name() + " : " + configEntry.value());
        });
      });
    }

    logger.info("=====Get Topic information");
    String topicName = "test";
    Map<String, TopicDescription> stringTopicDescriptionMap = admin.describeTopics(
        Collections.singleton(topicName)).all().get();

    logger.info("{}", stringTopicDescriptionMap);

  }

}
