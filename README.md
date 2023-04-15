# 상품 주문

- 상품 주문 토이프로젝트



# H2

### 접속 방법

- http://localhost:8080/h2-console
- jdbc url : jdbc:h2:mem:testdb



# Redis



### docker-compose 로 redis 실행

- docker 를 실행합니다.
- docker-compose 폴더에 있는 docker-compose-redis.yml 을 실행시킵니다.

```yaml
docker-compose -f ./docker-compose-redis.yml up -d
```



### 동작 확인

- redis-cli 로 접속해서 ping 명령어를 날리면 동작 확인이 가능합니다.

```shell
docker exec -it redis_main redis-cli


ping 명령어 입력
PONT 답변
```



### container 종료

```yaml
docker-compose -f ./docker-compose-redis.yml down
```