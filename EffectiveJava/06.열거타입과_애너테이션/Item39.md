## 39. 명명 패턴보다 애너테이션을 사용하라

### 명명 패턴의 단점

- 오타가 나면 안된다.
- 올바른 프로그램 요소에서만 사용된다는 보증이 없다.
- 프로그램 요소를 매개변수로 전달할 마땅한 방법이 없다.



### 애너테이션

- 애너테이션 선언에 다는 애너테이션을 메타애너테이션이라 한다.
- @Retention(RetentionPolicy.RUNTIME) 메타애너테이션은 애너테이션이 런타임에도 유지되어야 한다는 표시다.
- @Target(ElementType.METHOD) 메타애너테이션은 애너테이션이 반드시 메서드 선언에서만 사용돼야 한다고 알려준다.
- 마커 애너테이션은 사용자가 이름에 오타를 내거나 메서드 선언 외의 프로그램 요소에 달면 컴파일 오류를 내준다.
- 마커 애너테이션이 클래스의 의미에 직접적인 영향을 주지는 않으며 추가 정보를 제공할 뿐이다.



### 매개변수를 하나 받는 애너테이션

```java
/**
 * 명시한 예외를 던저야만 성공하는 테스트 메서드 애너테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    Class<? extends Throwable> value();
}
```

- 이 애너테이션의 매개변수 타입은 `Class<? extend Throwable>`이다.
- 여기서 와일드카드 타입은 "Throwable을 확장한 클래스의 Class 객체"라는 뜻이며, 따라서 모든 예외 타입을 다 수용한다.



### 배열 매개변수를 받는 애너테이션

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    Class<? extends Throwable>[] value();
}
```

- 배열 매개변수를 받는 애너테이션용 문법은 아주 유연하다.
- 단일 원소 배열에 최적화했지만, 앞서의 @ExceptionTest들도 모두 수정 없이 수용한다.
- 원소가 여럿인 배열을 지정할 때는 다음과 같이 원소들을 중괄호로 감싸고 쉼표로 구분해주기만 하면 된다.



### 반복가능한 애너테이션

```java
// 반복 가능한 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
public @interface ExceptionTest {
    Class<? extends Throwable> value();
}

// 컨테이너 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTestContainer {
    ExceptionTest[] value();
}
```

- 자바 8에서는 여러 개의 값을 받는 애너테이션을 다른 방식으로도 만들 수 있다.
- 배열 매개변수를 사용하는 대신 애너테이션에 @Repeatable 메타애너테이션을 다는 방식이다.
- @Repeatable을 단 애너테이션은 하나의 프로그램 요소에 여러 번 달 수 있다.



### 핵심

- 애너테이션으로 할 수 있는 일을 명명 패턴으로 처리할 이유는 없다.
- 자바 프로그래머라면 예외 없이 자바가 제공하는 애너테이션 타입들은 사용해야 한다.