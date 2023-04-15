> 나중에 찾아보기 위해 정리한 파일입니다.

# Redis 관련 (설치)

### Redis docker 내려받기

- docker pull redis:alpine
    - alpine 은 용량이 적은 버전입니다.
- docker pull redis
    - lastest 버전으로 default tag 생겨서 파일을 내려받습니다.
- docker images 명령어를 치면 설치된 redis 이미지를 확인할 수 있습니다.

### Redis 구동 및 확인

- run --name redis_main -d -p 6379:6379 redis:alpine
    - name 은 container 명을 의미합니다. (redis_main)
    - -d 옵션은 백그라운드로 동작합니다.
    - -p 옵션은 host port 와 container port 맵핑합니다.
- 위와 같이 실행하고 docker-cli 를 통해 확인이 가능합니다.
    - docker exec -it redis_main redis-cli
      127.0.0.1:6379> ping
      PONG
      127.0.0.1:6379> keys *
      (empty array)

### container 종료 명령어

- docker stop [container id]

# Redis 명령어

### SortedSet 에서 우선순위가 가장 낮은 값 가져오기

- ZRANGE "\xac\xed\x00\x05t\x00\x0eissued_coupons" 0 0 WITHSCORES
  --> "1677895413694"

### SortedSet 에서 SCORE 가져오기

- ZSCORE "issued_coupons" "user1001"

### SortedSet 에서 특정 key 개수

- ZCARD "issued_coupons"
  --> 1000

### SortedSet 에서 특정 value 순위 (오름차순)

- ZRANK "issued_coupons" user001
  --> 5 (5번째)

### Redis 모니터링

- MONITOR 치면 OK 라고 나옴. redis 모니터링 가능

### SET

- SADD test testValue (저장 명령어)
- SMEMBERS test (value 조회)