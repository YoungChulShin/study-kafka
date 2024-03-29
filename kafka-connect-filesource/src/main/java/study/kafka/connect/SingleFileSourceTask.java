package study.kafka.connect;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleFileSourceTask extends SourceTask {

  private static Logger logger = LoggerFactory.getLogger(SingleFileSourceTask.class);

  private static String FILENAME_FIELD = "filename";
  private static String POSITION_FIELD = "position";

  private Map<String, String> fileNamePartition;
  private Map<String, Object> offset;
  private String topic;
  private String file;
  private long position = -1;

  @Override
  public String version() {
    return "1.0";
  }

  @Override
  public void start(Map<String, String> props) {
    try {
      SingleFileSourceConnectorConfig config = new SingleFileSourceConnectorConfig(props);

      topic = config.getString(SingleFileSourceConnectorConfig.TOPIC_NAME);
      file = config.getString(SingleFileSourceConnectorConfig.DIR_FILE_NAME);
      // [filename:test.txt]
      fileNamePartition = Collections.singletonMap(FILENAME_FIELD, file);
      // [position:11]
      offset = context.offsetStorageReader().offset(fileNamePartition);

      if (offset != null) {
        Object lastReadFileOffset = offset.get(POSITION_FIELD);
        if (lastReadFileOffset != null) {
          position = (Long) lastReadFileOffset;
        }
      } else {
        this.position = 0;
      }
    } catch (Exception ex) {
      throw new ConnectException(ex.getMessage(), ex);
    }
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    List<SourceRecord> results = new ArrayList<>();
    try {
      Thread.sleep(1000);

      List<String> lines = getLines(position);

      if (lines.size() > 0) {
        lines.forEach(line -> {
          Map<String, Long> sourceOffset = Collections.singletonMap(POSITION_FIELD, ++position);
          SourceRecord sourceRecord = new SourceRecord(
              fileNamePartition,
              sourceOffset,
              topic,
              Schema.STRING_SCHEMA,
              line);
          results.add(sourceRecord);
        });
      }
      return results;
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      throw new ConnectException(ex.getMessage(), ex);
    }
  }

  private List<String> getLines(long readline) throws Exception {
    BufferedReader reader = Files.newBufferedReader(Paths.get(file));
    return reader.lines().skip(readline).collect(Collectors.toList());
  }

  @Override
  public void stop() {

  }
}
