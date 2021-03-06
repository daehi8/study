## 36. 비트 필드 대신 EnumSet을 사용하라

### 비트 필드

- 비트별 OR을 사용해 여러 상수를 하나의 집합으로 모을 수 있으며 이렇게 만들어진 집합

  ```java
  public class Text {
      public static final int BOLD = 1 << 0;
      public static final int ITALIC = 1 << 1;
      public static final int UNDERLINE = 1 << 2;
      public static final int STRIKETHROUGH = 1 << 3;
  
      public void applyStyles(int styles) {
          // ...
      }
  }
  ```

- 비트별 연산을 사용해 합집합과 교집합 같은 집합 연산을 효율적으로 수행할 수 있다.

  ```java
  text.applyStyles(BOLD | UNDERLINE); // BOLD | UNDERLINE은 3
  text.applyStyles(3);
  ```

- 정수 열거 상수의 단점을 그대로 지닌다.

- 비트 필드 값이 그대로 출력되면 단순한 정수 열거 상수를 출력할 때보다 해석하기가 훨씬 어렵다.

- 비트 필드 하나에 녹아 있는 모든 원소를 순회하기도 까다롭다.

- 최대 몇 비트가 필요한지를 API 작성 시 미리 예측하여 적절한 타입을 선택해야 한다.

- API를 수정하지 않고는 비트 수를 더 늘릴 수 없기 때문이다.



### EnumSet

- java.util 패키지의 EnumSet 클래스는 열거 타입 상수의 값으로 구성된 집합을 효과적으로 표현해준다.

  ```java
  public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
      Enum<?>[] universe = getUniverse(elementType);
      if (universe == null)
          throw new ClassCastException(elementType + " not an enum");
  
      if (universe.length <= 64)
          return new RegularEnumSet<>(elementType, universe);
      else
          return new JumboEnumSet<>(elementType, universe);
  }
  ```

- Set 인터페이스를 완벽히 구현하며 타입 안전하고 다른 어떤 Set 구현체와도 함께 사용할 수 있다.

- 내부는 비트 벡터로 구현되어 있다.

  ```java
  class RegularEnumSet<E extends Enum<E>> extends EnumSet<E> {
      private static final long serialVersionUID = 3411599620347842686L;
  
      private long elements = 0L;
  
      void addAll() {
          if (universe.length != 0)
              elements = -1L >>> -universe.length;
      }
  
      void complement() {
          if (universe.length != 0) {
              elements = ~elements;
              elements &= -1L >>> -universe.length;  // Mask unused bits
          }
      }
  
      // ...
  }
  ```

- 원소가 총 64개 이하라면 EnumSet 전체를 long 변수 하나로 표현하여 비트 필드에 비견되는 성능을 보여준다.

  ```java
  public boolean contains(Object e) {
      if (e == null)
          return false;
      Class<?> eClass = e.getClass();
      if (eClass != elementType && eClass.getSuperclass() != elementType)
          return false;
  
      return (elements & (1L << ((Enum<?>)e).ordinal())) != 0;
  }
  
  public boolean add(E e) {
      typeCheck(e);
  
      long oldElements = elements;
      elements |= (1L << ((Enum<?>)e).ordinal());
      return elements != oldElements;
  }
  
  public boolean remove(Object e) {
      if (e == null)
          return false;
      Class<?> eClass = e.getClass();
      if (eClass != elementType && eClass.getSuperclass() != elementType)
          return false;
  
      long oldElements = elements;
      elements &= ~(1L << ((Enum<?>)e).ordinal());
      return elements != oldElements;
  }
  ```

- removeAll과 retainAll 같은 대량 작업은 비트를 효율적으로 처리할 수 있는 산술 연산을 써서 구현했다.

- 난해한 작업을 EnumSet이 처리하기 때문에 비틀르 직접 다룰 때 겪는 오류를 겪지 않아도 된다.



### 핵심

- 열거할 수 있는 타입을 한데 모아 집합 형태로 사용한다고 해도 비트 필드를 사용할 이유는 없다.
- EnumSet 클래스가 비트 필드 수준의 명료함과 성능을 제공하고 열거 타입의 장점까지 선사하기 때문이다.
- 유일한 단점은 불변 EnumSet을 만들 수 없다는 것이다.