# 저장소 설명
## 배경
대량의 요청이 서버로 한번에 들어오게 되면 서버의 지표가 올라가고, 심해질 경우 정상적으로 처리되어야하는 다른 요청까지 문제가 생기면서 장애가 발생할 수 있다. 

만약에 카프카를 이용해서 대량의 요청을 순차적으로 처리해도 된다면 일정 리소스만 카프카를 통한 요청 처리에 사용하고 나머지 요청은 정상적으로 처리할 수 있게된다. 이를 실습으로 테스트해보자. 

## 프로젝트 정보
1. docker-compose: kafka를 구동하는 docker-compose
2. jmeter: web request를 생성하는 jmeter test file
3. kafka-producer: kafka message를 전송하는 producer 서버
4. api-server: web request 또는 kafka 메시지를 처리하는 서버

## 테스트 정보
API 정보 
- url: localhost:8080/api/calculate 
- method: POST
- 기능: 처리에 약 1초 정도 되는 dummy api

테스트 방법
- 1,000번의 요청을 전달한다. 
- 처리 시간, 지표 정보를 확인한다. 

## case 1. 대량의 HTTP 요청
요청 방법
- jmeter를 이용해서 부하테스트 진행

jmeter 테스트 설정
- 사용자 수: 100명
- 요청 수: 10번

테스트 결과
- 시간: 약 2분 소요
- CPU 지표: 100%까지 상승
   - ![test_result](/images/01.jemter-test-result.jpg)

## case 2. Kafka를 이용한 대량의 요청 처리 - Single Record 처리
요청 방법
- producer는 `kafka-producer`를 이용해서 kafka에 1,000개의 데이터를 일시적으로 넣어준다. 

kafka 설정
- partition: 1개
- 토픽 생성
   ```
   ./kafka-topics.sh --bootstrap-server localhost:19092 --create --topic test-topic --partitions 1 --replication-factor 1
   ```

kafka consumer 설정
- single record를 처리하는 consumer를 설정한다. 
- property 설정
   ```yaml
   spring:
      kafka:
         consumer:
            bootstrap-servers: localhost:19092,localhost:29092,localhost:39092
         listener:
            type: single
            ack-mode: record
   ```

테스트 결과
- 시간: single thread로 처리되기 때문에 '개별 처리 시간' * '메시지 수' 만큼의 시간이 소요된다.
- CPU 지표: 10%를 지속적으로 유지
   - ![test_result](/images/02.kafka-single-result.png)
- 일정 리소스만 사용하면서 지속적으로 요청을 처리하고 싶을 때 사용



