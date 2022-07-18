## 49. 매개변수가 유효한지 검사하라

### 매개변수 검사를 하지 않았을 때의 문제

- 메서드가 수행되는 중간에 모호한 예외를 던지며 실패할 수 있다.
- 실패 원자성을 어기는 결과를 낳을 수 있다.



### public과 protected

```java
/**
* (현재 값 mod m) 값을 반환한다. 이 메서드는 
* 항상 음이 아닌 BigInteger를 반환한다는 점에서 remainder 메서드와 다르다.
*
* @param m 계수 (양수여야 한다.)
* @return 현재 값 mod m
* @throws ArithmeticException m이 0보다 작거나 같으면 발생한다.
*/
public BigInteger mod(BigInteger m) {
    if (m.signum() < 0)
        throw new ArithmeticException("계수(m)는 양수여야 합니다. " + m);
    ...
}
```

- public과 protected 메서드는 매개변수 값이 잘못됐을 때 던지는 예외를 문서화해야 한다.
- 예제에서는 메서드 설명을 클래수 수준에서 기술하고 있다.
- 클래스 수준 주석은 그 클래스의 모든 public 메서드에 적용되므로 각 메서드에 일일이 기술하는 것보다 훨씬 깔끔한 방법이다.



### java.util.Objects.requireNonNull

```java
this.startegy = Objects.requireNonNull(strategy, "전략");
```

- 유연하고 사용하기도 편하며 더이상 null 검사를 수동으로 하지 않아도 된다.
- 원하는 예외 메시지도 지정할 수 있다.
- 입력을 그대로 반환하므로 값을 사용하는 동시에 null 검사를 수행할 수 있다.



### 공개되지 않은 메서드

```java
private static void sort(long[] a, int offset, int length) {
    assert a != null;
    assert offset >= 0 && offset <= a.length;
    assert length >= 0 && length <= a.length - offset;
    ...
}
```

- 공개되지 않은 메서드라면 패키지 제작자는 메서드가 호출된느 상황을 통제할 수 있다.
- 유효한 값만이 메서드에 넘겨지는 것을 보증할 수 있다.
- public이 아닌 메서드라면 assert를 사용해 매개변수 유효성을 검증할 수 있다.



### assert

- 실패하면 AssertionError를 던진다.
- 런타임에 아무런효과도 아무런 성능 저하도 없다.



### 생성자

- 생성자는 나중에 쓰려고 저장하는 매개변수의 유효성을 검사하라는 원칙의 특수한 사례다.
- 생성자 매개변수의 유효성 검사는 클래스 불변식을 어기는 객체가 만들어지지 않게 하는 데 꼭 필요하다.



### 설계

- 메서드는 최대한 범용적을 ㅗ설계해야 한다.
- 메서드가 건네 받은 값으로 무언가 제대로 된 일을 할 수 있다면 매개변수 제약은 적을수록 좋다.



### 핵심

- 메서드나 생성자를 작성할 때면 그 매개변수에 어떤 제약이 있을지 생각해야 한다.
- 그 제약들을 문서화하고 명시적으로 검사해야 한다.