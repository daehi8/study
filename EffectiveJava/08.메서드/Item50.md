## 50. 적시에 방어적 복사본을 만들라.

### 방어적 프로그래밍

- 자바는 안전한 언어다. 네이티브 메서드를 사용하지 않으니 메모리 충돌 오류에서 안전하다.
- 그래도 **클라이언트가 불변식을 깨뜨리려 혈안이 되어있다고 가정하고 방어적으로 프로그래밍해야 한다.**
- 어떤 객체든 그 객체의 허락 없이는 외부에서 내부를 수정하는 일은 불가능하다. 하지만 주의를 기울이지 않으면 허락하는 경우가 생긴다.



### 예제1

```java
/ 코드 50-1 기간을 표현하는 클래스 - 불변식을 지키지 못했다. (302-305쪽)
public final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param  start 시작 시각
     * @param  end 종료 시각. 시작 시각보다 뒤여야 한다.
     * @throws IllegalArgumentException 시작 시각이 종료 시각보다 늦을 때 발생한다.
     * @throws NullPointerException start나 end가 null이면 발생한다.
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(
                    start + "가 " + end + "보다 늦다.");
        this.start = start;
        this.end   = end;
    }

    public Date start() {
        return start;
    }
    public Date end() {
        return end;
    }

    public String toString() {
        return start + " - " + end;
    }
```

- final로 선언을했고, setter도 없으니 불변처럼 보인다. 그래서 시작 시각이 종료 시각보다 늦으면 안된다는 불변식이 지켜질 것 같다. 하지만 `Date`는 가변이다.



### Date 예제1

```java
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
end.setYear(78); // p 내부 수정
```

- 자바 8이후로는 `Date`대신 불변인 `Instant`를 사용하면 된다. (혹은 `LocalDateTime` 또는 `ZonedDateTime`). 
- 문제는 이미 오래된 API에 `Date`가 많이 사용되어 있다.



### 해결방법

```java
public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());
    
    if (this.start.compareTo(this.end) > ) 
        throw new IllegalArgumentException(this.start + " after " + this.end);
}
```

- 외부 공격으로 부터 내부를 보호하려면 **생성자에서 받은 기본 매개변수 각각을 방어적으로 복사해야 한다.**
- 새로 작성한 생성자를 사용하면 방어할 수 있다.
- **매개변수의 유효성을 검사(아이템49)하기 전에 방어적 복사본을 만들고, 이 복사본으로 유효성을 검사한 점에 주목하자.** 반드시 이렇게 작성해야 한다.
- **멀티스레딩 환경이라면 원본 객체의 유효성을 검사한 후 검사본을 만드는 찰나의 순간에 다른 스레드가 원본 객체를 수정할 위험이 있기 때문**이다.
- **매개변수가 제3자에 의해 확장될 수 있는 타입이라면 방어적 복사본을 만들 때 clone을 사용해서는 안된다.** 
- `Date`는 `final`이 아니므로 `clone`이 `Date`가 정의한게 아닐 수도 있다. 즉, `clone`이 악의를 가진 하위 클래스의 인스턴스를 반환할 수도 있다. 예를 들어 이 하위 클래스는 `start`와 `end` 필드의 참조를 `private` 정적 리스트에 담아뒀다가 공격자에게 이 리스트에게 접근하는 길을 열어줄 수도 있다.



### Date 예제2

```java
// Period 인스턴스를 향한 두 번째 공격
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
p.end().setYear(78);
```

- 두 번째 공격을 막으려면 접근자가 **가변 필드의 방어적 복사본을 반환하면 된다.**



### 해결방법

```java
// 수정한 접근자 - 필드의 방어적 복사본 반환
public Date start() {
    return new Date(start.getTime());
}

public Date end() {
    return new Date(end.getTime());
}
```

- 이제 Period 자신 말고는 가변 필드에 접근할 방법이 없으니 모든 필드가 캡슐화되었다.
- 생성자와 달리 접근자 메서드에서는 방어적 복사에 `clone`을 사용해도 된다. 
- `Period`가 가지고 있는 `Date` 객체는 `java.util.Date`임이 확실하기 때문이다.



### 핵심

- 메서드든 생성자든 클라이언트가 제공한 객체의 참조를 내부의 자료구조에 보관해야 할 때면 항시 그 객체가 잠재적으로 변경될 수 있는지를 생각해야 한다. 확신할 수 없다면 복사본을 만들어 저장해야 한다.
- 길이가 1이상인 배열은 무조건 가변임을 잊지말자.
- 방어적 복사에는 성능 저하가 따르고 항상 쓸수 있는 것도 아니다. 방어적 복사를 생략할 때는 해당 매개변수가 반환값을 수정하지 말아야함을 명확히 문서화해야 한다.