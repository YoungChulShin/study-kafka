# 설명
`아파치 카프카 애플리케이션 프로그래밍` 책에 있는 웹 페이지 이벤트 적재 파이프라인 실습을 합니다. 

# 환경 셋업
로컬 하둡,엘라스틱서치, 키바나 설치
```
brew install hadoop elasticsearch kibana
```

하둡 기본 파일 시스템 설정 - core-site.xml
```
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```

# 토픽 생성
```
./kafka-topics.sh --create --bootstrap-server localhost:19092 --replication-factor 2 --partitions 3 --topic select-color
```