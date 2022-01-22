## 05. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

#### 클래스가 의존하는 자원을 정적 유틸리티 클래스로 잘못 사용한 예

```java
public class SpellChecker {
  private static final Lexicon dictionary = ...;
  
  private SpellChecker() {} // 객체 생성 방지
  
  public static boolean isValid(String word) {...}
  public static List<String> suggestions(String typo) {...}
}
```



#### 클래스가 의존하는 자원을 싱글턴으로 잘못 사용한 예

```java
public class SpellChecker {
  private final Lexicon dictionary = ...;
  
  private SpellChecker(...) {}
  public static SpellChecker INSTANCE = new SpellChecker(...);
  
  public boolean isValid(String word) {...}
  public List<String> suggestions(String typo) {...}
}
```

- 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.
- 이 자원들은 클래스가 직접 만들게 해서도 안된다.



#### 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식

```java
public class SpellChecker {
  private final Lexicon dictionary;
  
  public SpellChecker(Lexicon dictionary){
    this.dictionary = Object.requireNonNull(dictionary);
  }
  
  public boolean isValid(String word) {...}
  public List<String> suggestions(String typo) {...}
}
```

- 의존 객체 주입의 한 형태로 유연성, 재사용성, 테스트 용이성을 높여준다.
- 하나의 자원만을 사용하지만, 자원이 몇 개든 의존 관계가 어떻든 상관없이 잘 작동한다.
- 불변을 보장하여 여러 클라이언트가 의존 객체들을 안심하고 공유 할 수 있게 한다.
- 의존 객체 주입은 생성자, 정적 팩터리, 빌더 모두에 똑같이 응용할 수 있다.



#### 생성자에 자원 팩터리를 넘겨주는 방식

- 팩터리란 호출할 때마다 특정 타입의 인스턴스를 반복해서 만들어주는 객체
- 팩터리 메서드 패턴을 구현한 방식
- 클라이언트는 자신이 명시한 타입의 하위타입이라면 무엇이든 생성할 수 있는 팩터리를 넘길 수 있도록 한정적 와일드 카드 타입을 사용해 팩터리의 타입 매개변수를 제한해야 한다.



#### 클라이언트가 제공한 팩터리가 생성한 타일들로 구성된 모자이크를 만드는 메서드 코드

```java
Mosaic create(Supplier<? extends Tile> tileFactory) {...}
```



#### 의존 객체 주입의 단점

- 의존 객체 주입이 유연성과 테스트 용이성을 개선해주지만, 의존성이 수천 개나 되는 큰 프로젝트에서는 코드를 어지럽게 만들기도 한다.
- Spring 같은 의존 객체 주입 프레임워크를 사용하면 이런 코드들을 정돈할 수 있다.

