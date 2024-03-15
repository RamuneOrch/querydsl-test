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

#### querydsl을 활용한 유저 전체 조회

- query로 조회할 때 entity의 필드를 전체가 아닌 조건부로 가져오기 위해 projection을 사용

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

- Page<T> : 게시판 형태의 페이징에서 사용
- Slice<T> : 스크롤이나 더보기 형태의 페이징에서 사용
- List<T> : 전체 목록보기 형태의 페이징에서 사용, 기본타입으로 count조회가 발생 x

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

```java
@Test
public void test1() throws Exception {
    //given
    String username = "spring@web";
    String password = "qwerQ1234!!";
    SignUpRequestDto signUpRequestDto = new SignUpRequestDto(username, password);

    // POST 요청 보내기
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequestDto)))
        .andExpect(status().isCreated());
}
```

### Service 테스트 코드 작성하기

```java
@Test
@DisplayName("pageable test")
void getUserByPage() {
    //given
    List<User> userList = Collections.singletonList(new User("test", "test@example.com"));
    Page<User> userPage = new PageImpl<>(userList);
    Pageable pageable = PageRequest.of(1, 3, Sort.by("id"));


    when(userRepository.findAll(
        PageRequest.of(0, 3, Sort.by("id"))
    )).thenReturn(userPage);

    //when
    UserPageResponseDto responseDto = userService.getUserByPage(pageable);

    //then
    assertEquals(1, responseDto.getSize());
    assertEquals(userList.size(), responseDto.getUsers().size());
}
```

### Repository 테스트 코드 작성하기

#### 트러블 슈팅

- main 폴더안에 querydsl을 위한 JPAQueryFactory bean 설정을 했음에도 불구하고 test 코드에서는 주입이 안되는 상황
- > test코드안에 따로 bean등록을 하고 @Import(TestConfig.class)로 등록을 하니 작동이되었다.
    main 폴더안의 모든 config 설정이 test코드에 적용되는 것은 아닌가 보다.

```java
@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryCustomImplTest {

    @Autowired
    UserRepositoryCustomImpl userRepositoryCustom;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    UserRepository userRepository;

    @Test
    void getUsers() {
        //given
        User user = new User();
        user.setUsername("song");
        user.setPassword("qwer1234!!@#");
        userRepository.save(user);
        //when
        List<UserResponseDto> users = userRepositoryCustom.getUsers();
        //then
        assertEquals(1, users.size());
    }
}
```
