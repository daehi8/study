## 35. ordinal 메서드 대신 인스턴스 필드를 사용하라

### ordinal 메서드

- 대부분읜 열거 타입 상수는 자연스럽게 하나의 정숫값에 대응된다.
- 모든 열거 타입은 해당 상수가 그 열거 타입에서 몇 번째 위치인지를 반환하는 ordinal이라는 메서드를 제공한다.
- 열거 타입 상수에 연결된 값은 ordinal 메서드로 얻지 말고 인스턴스 필드에 저장하는 것이 좋다.

```java
public enum Ensemble {
  SOLO, DUET, TRIO, QUARTET;
  
  public int numberOfMusicians() {
    return ordinal() + 1;
  }
}
```



### API 문서

- 대부분 프로그래머는 이 메서드를 쓸 일이 없다.
- 이 메서드는 EnumSet과 EnumMap 같이 열거 타입 기반의 범용 자료구조에 쓸 목적으로 설계되었다.

```java
public enum Ensemble {
  SOLO(1), DUET(2), TRIO(3), QUARTET(4);
  
  private final int numberOfMusicians;
  
  Ensemble(int size) {
    this.numberOfMusicians = size;
  }
  
  public int numberOfMusicians() {
    return numberOfMusicians;
  }
}
```





