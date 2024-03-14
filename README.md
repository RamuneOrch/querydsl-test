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
public interface UserService {
    UserResponseDto createUser(SignUpRequestDto req);

    UserResponseDto getUser();

    UsersResponseDto getUsers();

    UserPageResponseDto getUserByPage(Pageable pageable);
}
```

### CustomException 정의 및 Spring AOP적용

#### 1. custom Exception 적용

```java
public class PageNotExistenceException extends
    NullPointerException {

    public PageNotExistenceException(String message){
        super(message);
    }
}

-------

private void validateList(List<UserResponseDto> userLists) {
    if (userLists.isEmpty()) {
    throw new PageNotExistenceException("해당 페이지는 존재하지 않습니다.");
    }
}
```

#### 2. spring aop 적용

```java

```

### QueryDSL을 사용하여 검색기능 만들기

#### 1. querydsl을 활용한 유저 전체 조회

```java
public UsersResponseDto getUsers() {
        QUser qUser = new QUser("u");
        List<UserResponseDto> users = queryFactory
            .select(Projections.constructor(UserResponseDto.class, qUser.id, qUser.username))
            .from(qUser)
            .fetch();
        return new UsersResponseDto(users);
    }
```

### Pageable을 사용하여 페이징 및 정렬 기능 만들기

#### 1. Pageable를 사용한 클라이언트 요청에 따른 DB의 정보 보내기

```java
public UserPageResponseDto getUserByPage(Pageable pageable) throws PageNotExistenceException {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;

        Page<User> users = userRepository.findAll(
            PageRequest.of(page, pageLimit, pageable.getSort())
        );
        Page<UserResponseDto> usersResponseDtos = users.map(UserResponseDto::new);
        int totalPage = usersResponseDtos.getTotalPages();
        List<UserResponseDto> userLists = usersResponseDtos.getContent();
        validateList(userLists);
        return new UserPageResponseDto(userLists, totalPage);
    }
```

```json

- http://localhost:8080/users/page?page=1&sort=username
    
{
    "message": "page 테스트 성공",
    "data": {
        "users": [
            {
                "id": 2,
                "username": "pocketmon1@email.com"
            },
            {
                "id": 13,
                "username": "pocketmon2@email.com"
            },
            {
                "id": 1,
                "username": "pocketmon3@email.com"
            }
        ],
        "size": 5
    }
}
```

### Controller 테스트 코드 작성하기
### Service 테스트 코드 작성하기
### Repository 테스트 코드 작성하기
### AWS EC2를 이용해 애플리케이션 .jar파일 배포
