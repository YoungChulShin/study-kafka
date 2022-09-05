# 저장소 설명
서버 지표를 수집해서 카프카에 저장하는 실습

# 실행에 대한 설명
## 토픽 생성
```
// 전체 지표 저장 토픽
❯ ./kafka-topics.sh --create --bootstrap-server localhost:19092 --replication-factor 2 --partitions 3 --topic metric-all
// CPU 지표 저장 토픽
❯ ./kafka-topics.sh --create --bootstrap-server localhost:19092 --replication-factor 2 --partitions 3 --topic metric-cpu
// 메모리 지표 저장 토픽
❯ ./kafka-topics.sh --create --bootstrap-server localhost:19092 --replication-factor 2 --partitions 3 --topic metric-memory
// 비정상 CPU 지표 저장 토픽
❯ ./kafka-topics.sh --create --bootstrap-server localhost:19092 --replication-factor 2 --partitions 3 --topic metric-cpu-alert
```