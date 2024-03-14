# 스프링 심화 코드 개선 과제

### Cotroller, Service 패키지 내 클래스 개선

#### 1. Controller Advice로 예외 공통화 처리하기

```java
@Slf4j(topic = "Exception")
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CommonResponse<String>> handleValidationException(
        NullPointerException e) {
        log.error("유저가 존재하지 않습니다.", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            CommonResponse.<String>builder()
                .message(e.getMessage())
                .build()
        );
    }
}
```

#### 2. Service 인터페이스와 구현체 분리하여 추상화하기

```java

```

### CustomException 정의 및 Spring AOP적용
### QueryDSL을 사용하여 검색기능 만들기
### Pageable을 사용하여 페이징 및 정렬 기능 만들기
### Controller 테스트 코드 작성하기
### Service 테스트 코드 작성하기
### Repository 테스트 코드 작성하기
### AWS EC2를 이용해 애플리케이션 .jar파일 배포
