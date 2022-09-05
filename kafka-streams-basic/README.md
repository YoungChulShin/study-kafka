# 저장소 설명
카프카 스트림즈 기본 공부

# 테스트 지원
## SimpleStreamApplication
stream_log 토픽생성
```
./kafka-topics.sh --create --bootstrap-server localhost:19092 --partitions 3 --topic stream_log
```

## StreamJoinApplication
KStream, KTable, JoinTable 코파티셔닝(=동일한 파티션 수, 동일한 파티셔닝)되어 있어야한다

address topic 생성
```
./kafka-topics.sh --create --topic address --partitions 3 --bootstrap-server localhost:19092
```

order topic 생성
```
./kafka-topics.sh --create --topic order --partitions 3 --bootstrap-server localhost:19092
```

order_join topic 생성
```
./kafka-topics.sh --create --topic order_join --partitions 3 --bootstrap-server localhost:19092
```