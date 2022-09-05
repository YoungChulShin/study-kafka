# 저장소 설명
서버 지표를 수집해서 카프카에 저장하는 실습

# 실행에 대한 설명
## 토픽 생성
토픽 생성
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

### 메트릭비트 설치 및 수정
메트릭비트 설치
```
// 메트릭비트 설치
brew install metricbeat

// 메트릭비트 정보 확인
brew info metricbeat
```

메트릭비트 수정
- 메트릭비트가 설치에 된 경로에 bin 폴더로 들어가면 바이너리가 있다
- `meatricbeat.yml` 파일을 생성해준다
   ```yaml
   metricbeat.modules:
     - module: system
       metricsets:
         - cpu
         - memory
      enabled: true
      period: 10s

   output.kafka:
     hosts: ["localhost:19092", "localhost:29092", "localhost:39092"]
     topic: 'metric-all'
   ```