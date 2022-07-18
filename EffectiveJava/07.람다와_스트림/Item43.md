## 43. 람다보다는 메서드 참조를 사용하라

### 메서드 참조

```java
map.merge(key, 1, (count, incr) -> count + incr); // 람다
map.merge(key, 1, Integer::sum); // 메서드 참조
```

- 함수 객체를 람다보다 더 간결하게 만드는 방법
- 람다로 할 수 없는 일이라면 메서드 참조로도 할 수 없다.
- 람다로 구현했을 때 너무 길거나 복잡하다면 메서드 참조가 좋은 대안이 된다.
- 람다로 작성할 코드를 새로운 메서드에 담은 다음 람다 대신 그 메서드 참조를 사용하는 식이다.
- 메서드 참조에는 기능을 잘 드러내는 이름을 지어줄 수 있고 친절한 설명을 문서로 남길 수 있다.



### 람다가 메서드 참조보다 간결한 경우

```java
service.execute(GoshThisClassNameIsHumongous::action); // 메서드 참조
service.execute(() -> action()); // 람다
```

- 메서드와 람다가 같은 클래스에 있을 경우이다.



### 메서드 참조의 다섯 가지 유형

1. 정적 메서드 참조

   ```java
   Integer::parseInt
   str -> Integer.parseInt(str);
   ```

2. 한정적(인스턴스) 메서드 참조

   ```java
   Instant.now()::isAfter
   Instant then = Instant.now();
   t -> then.isAfter(t);
   ```

   - 수신 객체(참조 대상 인스턴스)를 특정하는 한정적 인스턴스 메서드 참조. 
   - 근본적으로 정적 참조와 비슷하다. 즉 함수 객체가 받는 인수와 참조되는 메서드가 받는 인수가 똑같다.

3. 비한정적(인스턴스) 메서드 참조

   ```java
   String::toLowerCase
   str -> str.toLowerCase()
   ```

   - 수신 객체를 특정하지 않는 인스턴스 메서드 참조. 
   - 함수 객체를 적용하는 시점에 수신 객체를 알려준다. 주로 스트림 파이프라인에서의 매핑과 필터 함수에 쓰인다.

4. 클래스 생성자

   ```java
   TreeMap<K,V>::new
   () -> new TreeMap<K,V>()
   ```

5. 배열 생성자

   ```java
   int[]::new
   len -> new int[len]
   ```



### 핵심

- 메서드 참조는 람다의 간단명료한 대안이 될 수 있다.
- 메서드 참조 쪽이 짧고 명확하다면 메서드 참조를 쓰고 그렇지 않을 때만 람다를 사용해야 한다.