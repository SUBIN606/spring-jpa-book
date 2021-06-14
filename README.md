# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스
> [스프링 부트와 AWS로 혼자 구현하는 웹 서비스](http://www.yes24.com/Product/Goods/83849117) 책 예제를 따라 작성한 프로젝트
## 버전
- spring boot 2.5.0
- Gradle 7.0.2
- Java 11
## 책과 다르게 설정해야 하는 것들
### 1. application.yml
- `jdbc.initialize-schema: always`설정을 해주어야 자동으로 스프링 세션 테이블이 생성된다
- Spring Boot 2.4.0이상 버전을 사용할 경우, `spring.profiles.group`을 사용해 프로필을 포함한다
- `spring.jpa.hibernate.dialect`로 MySQL 문법 설정을 한다
- `datasource.url`에 `MODE=MySQL`을 추가해야 한다
```yaml
spring:
  session:
    store-type: jdbc
    jdbc.initialize-schema: always
  profiles:
    group:
      local:
        - oauth
  h2.console.enabled: true
  datasource:
    url: jdbc:h2:tcp://localhost/~/test;MODE=MySQL
    driver-class-name: org.h2.Driver
    username: sa
  jpa:
    # 콘솔에 쿼리 출력
    show-sql: true
    hibernate:
      ddl-auto: create-drop
      # MySQL 문법 적용
      dialect: org.hibernate.dialect.MySQL57Dialect
      storage-engine: innodb
```
   
### 2. application-auth.yml 
* 모두 `kebab-case`로 작성한다
* `redirect-url`는 큰 따옴표`"`를 붙여 작성한다
* `provider: [provider-name]`으로 provider 이름을 설정한 후, `provider`항목을 작성한다
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: 
            client-secret: 
            scope: profile,email
          naver:
            client-id: 
            client-secret: 
            client-name: Naver
            scope: name,email,profile_image
            redirect-uri: "{baseUrl}/{action}/oauth2/code/{registrationId}"
            authorization-grant-type: authorization_code
            provider: naver-provider
        provider:
          naver-provider:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response
```
### 3. 테스트 코드
`MockMvc`를 사용할 경우, `@SpringBootTest`에 아무런 설정을 하지 않는다. (기본 값이 Mock 환경으로 실행)
```java
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsApiControllerTest {
    
}
```