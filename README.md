# 상품 주문

- 상품 주문 토이프로젝트

# 프로젝트 시작 방법

### docker-compose 로 redis 실행

- docker 를 실행합니다.
- docker-compose 폴더에 있는 docker-compose-redis.yml 을 실행시킵니다.

```yaml
docker-compose -f ./docker-compose-redis.yml up -d
```

### redis 동작 확인

- redis-cli 로 접속해서 ping 명령어를 날리면 동작 확인이 가능합니다.

```shell
docker exec -it redis_main redis-cli


ping 명령어 입력
PONT 답변
```

### CoreApiApplication 실행

- 실행하면서 schema.sql 실행되면서 product_order, product 테이블이 생성되며, 데이터가 INSERT 됩니다.

### 상품 주문 API 호출

- 아래는 userId 1002 에 productId 10002 로 100개를 주문합니다. (이미 데이터가 들어가있습니다.)

```curl
curl --location --request POST 'localhost:8080/api/v1/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId" : "user1002",
    "productId" : "product1002",
    "quantity" : 100

}'
```

### 상품 주문 확인 API 호출

- 아래 API 를 호출하면 내가 주문한 내역을 확인할 수 있습니다.

```curl
curl --location --request GET 'localhost:8080/api/v1/orders' \
--header 'Content-Type: application/json' \
--data-raw ''
```

`혹시 product 에 대한 재고를 살펴보고 싶으면 H2 접속해서 select 쿼리 날려보면 됩니다.`

- http://localhost:8080/h2-console
- jdbc url : jdbc:h2:mem:testdb
